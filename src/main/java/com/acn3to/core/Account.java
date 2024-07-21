package com.acn3to.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a bank account with balance management and transaction logging.
 * <p>
 * This class provides methods to deposit and withdraw funds in a thread-safe manner.
 * It also logs each transaction to a JSON file for persistent record-keeping.
 * </p>
 */
public class Account {
    private double balance;
    private final Object lock = new Object();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String transactionHistoryFile;

    /**
     * Constructs an Account with the specified initial balance and a file to log transactions.
     *
     * @param initialBalance the initial balance of the account
     * @param accountId      the ID of the account
     */
    public Account(double initialBalance, int accountId) {
        this.balance = initialBalance;
        this.transactionHistoryFile = "transaction_history_" + accountId + ".json";
    }

    /**
     * Deposits the specified amount into this account.
     * <p>
     * This method is synchronized to ensure that deposits are thread-safe and
     * the account balance remains consistent.
     * </p>
     *
     * @param amount the amount of money to deposit
     */
    public void deposit(double amount) {
        synchronized (lock) {
            balance += amount;
            logTransaction("Deposit", amount);
        }
    }

    /**
     * Withdraws the specified amount from this account.
     * <p>
     * This method is synchronized to ensure that withdrawals are thread-safe and
     * the account balance remains consistent. If there are insufficient funds,
     * the withdrawal will not be processed.
     * </p>
     *
     * @param amount the amount of money to withdraw
     */
    public void withdraw(double amount) {
        synchronized (lock) {
            if (amount <= getBalance()) {
                balance -= amount;
                logTransaction("Withdrawal", amount);
            } else {
                logTransaction("Withdrawal failed", amount, true);
            }
        }
    }

    /**
     * Returns the current balance of this account.
     *
     * @return the current balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Logs a transaction to the JSON file.
     *
     * @param transactionType the type of transaction ("Deposit" or "Withdrawal")
     * @param amount          the amount of money involved in the transaction
     */
    private void logTransaction(String transactionType, double amount) {
        logTransaction(transactionType, amount, false);
    }

    /**
     * Logs a transaction to the JSON file with additional error indication.
     *
     * @param transactionType the type of transaction ("Deposit" or "Withdrawal")
     * @param amount          the amount of money involved in the transaction
     * @param error           true if the transaction failed, false otherwise
     */
    private void logTransaction(String transactionType, double amount, boolean error) {
        Transaction transaction = new Transaction(
                new Date(),
                transactionType,
                amount,
                getBalance()
        );

        JsonObject transactionJson = new JsonObject();
        transactionJson.addProperty("date", dateFormat.format(transaction.date()));
        transactionJson.addProperty("type", transaction.type());
        transactionJson.addProperty("amount", Double.parseDouble(String.format(Locale.US, "%.2f", transaction.amount())));
        transactionJson.addProperty("balance_after_transaction", Double.parseDouble(String.format(Locale.US, "%.2f", transaction.balanceAfterTransaction())));
        transactionJson.addProperty("error", error);

        try {
            JsonArray transactions = readTransactionHistory();
            transactions.add(transactionJson);

            try (FileWriter writer = new FileWriter(transactionHistoryFile)) {
                gson.toJson(transactions, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the current transaction history from the JSON file.
     *
     * @return a JsonArray of transactions
     * @throws IOException if an I/O error occurs
     */
    private JsonArray readTransactionHistory() throws IOException {
        File file = new File(transactionHistoryFile);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, JsonArray.class);
            }
        }
        return new JsonArray();
    }
}
