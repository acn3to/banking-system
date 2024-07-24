package com.acn3to.core.services;

import com.acn3to.core.entities.Account;
import com.acn3to.core.repositories.AccountRepository;

/**
 * Provides services for performing operations on bank accounts, such as deposits and withdrawals.
 */
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionLogger transactionLogger;

    /**
     * Constructs an AccountService with specified AccountRepository and TransactionLogger.
     *
     * @param accountRepository the repository used for account data operations
     * @param transactionLogger the logger used for logging transactions
     */
    public AccountService(AccountRepository accountRepository, TransactionLogger transactionLogger) {
        this.accountRepository = accountRepository;
        this.transactionLogger = transactionLogger;
    }

    /**
     * Deposits a specified amount into the given account.
     * Logs the transaction using the TransactionLogger.
     *
     * @param accountId the ID of the account to deposit into
     * @param amount    the amount to deposit
     */
    public synchronized void deposit(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }

    /**
     * Withdraws a specified amount from the given account if sufficient balance exists.
     * Logs the transaction using the TransactionLogger.
     *
     * @param accountId the ID of the account to withdraw from
     * @param amount    the amount to withdraw
     */
    public synchronized void withdraw(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        if (account != null) {
            if (amount <= account.getBalance()) {
                double newBalance = account.getBalance() - amount;
                account.setBalance(newBalance);
                accountRepository.save(account);
            }
        }
    }

    /**
     * Retrieves the balance of a specified account.
     *
     * @param accountId the ID of the account
     * @return the balance of the account, or 0 if not found
     */
    public double getAccountBalance(int accountId) {
        Account account = accountRepository.findById(accountId);
        return account != null ? account.getBalance() : 0;
    }

    /**
     * Retrieves the TransactionLogger instance used by the service.
     *
     * @return the TransactionLogger instance
     */
    public TransactionLogger getTransactionLogger() {
        return transactionLogger;
    }
}
