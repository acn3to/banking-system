package com.acn3to.core.services;

import com.acn3to.core.entities.Transaction;
import com.acn3to.core.repositories.TransactionRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Logs transactions to a MongoDB collection using a repository.
 * <p>
 * This class formats transaction details and stores them in a MongoDB collection for persistent record-keeping.
 * </p>
 */
public class TransactionLogger {
    private final TransactionRepository transactionRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Constructs a TransactionLogger with a specified TransactionRepository.
     *
     * @param transactionRepository the repository used for storing transactions
     */
    public TransactionLogger(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Logs a transaction with the specified details.
     *
     * @param accountId         the ID of the account involved in the transaction
     * @param transactionType  the type of transaction ("Deposit" or "Withdrawal")
     * @param amount           the amount of money involved in the transaction
     * @param newBalance       the balance after the transaction
     * @param error            indicates if the transaction failed
     */
    public void logTransaction(int accountId, String transactionType, double amount, double newBalance, boolean error) {
        Transaction transaction = new Transaction(
                accountId,
                new Date(),
                transactionType,
                amount,
                newBalance
        );

        JsonObject transactionJson = new JsonObject();
        transactionJson.addProperty("account_id", transaction.accountId());
        transactionJson.addProperty("date", dateFormat.format(transaction.date()));
        transactionJson.addProperty("type", transaction.type());
        transactionJson.addProperty("amount", Double.parseDouble(String.format(Locale.US, "%.2f", transaction.amount())));
        transactionJson.addProperty("balance_after_transaction", Double.parseDouble(String.format(Locale.US, "%.2f", transaction.balanceAfterTransaction())));
        transactionJson.addProperty("error", error);

        transactionRepository.save(transactionJson);
    }
}
