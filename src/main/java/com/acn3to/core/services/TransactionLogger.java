package com.acn3to.core.services;

import com.acn3to.core.entities.Transaction;
import com.acn3to.core.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Logs transactions to a MongoDB collection using a repository.
 * <p>
 * This class stores transaction details in a MongoDB collection for persistent record-keeping.
 * </p>
 */
public class TransactionLogger {
    private final TransactionRepository transactionRepository;

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
    public synchronized void logTransaction(int accountId, String transactionType, double amount, double newBalance, boolean error) {
        BigDecimal formattedAmount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal formattedBalance = BigDecimal.valueOf(newBalance).setScale(2, RoundingMode.HALF_UP);

        Transaction transaction = new Transaction(
                accountId,
                new Date(),
                transactionType,
                formattedAmount.doubleValue(),
                formattedBalance.doubleValue()
        );

        transactionRepository.save(transaction);
    }
}
