package com.acn3to.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a bank account with methods to deposit and withdraw money.
 * <p>
 * This class provides synchronized methods to ensure thread safety when performing
 * deposit and withdrawal operations. Each transaction is logged with a timestamp
 * to provide a record of account activity.
 * </p>
 */
public class Account {
    private double balance;
    private final Object lock = new Object();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());

    /**
     * Constructs an Account with the specified initial balance.
     *
     * @param initialBalance the initial balance of the account
     */
    public Account(double initialBalance) {
        this.balance = initialBalance;
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
            System.out.printf("%s - Account ID: %d - Deposited: %.2f. New balance: %.2f%n",
                    dateFormat.format(new Date()), hashCode(), amount, getBalance());
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
                System.out.printf("%s - Account ID: %d - Withdrawn: %.2f. New balance: %.2f%n",
                        dateFormat.format(new Date()), hashCode(), amount, getBalance());
            } else {
                System.out.printf("%s - Account ID: %d - Withdrawal failed: %.2f. Insufficient funds. Current balance: %.2f%n",
                        dateFormat.format(new Date()), hashCode(), amount, getBalance());
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
}
