package com.acn3to.core.entities;

import java.util.Date;

/**
 * Represents a bank account with a balance, a unique account ID, and other attributes.
 * Provides thread-safe access to the account balance and includes additional details
 * for better tracking and management.
 */
public class Account {
    private final int accountId;
    private String accountHolderName;
    private String accountType;
    private String accountStatus;
    private final Date creationDate;
    private Date lastUpdatedDate;
    private double balance;
    private final Object lock = new Object();

    /**
     * Constructs an Account with an initial balance, unique account ID, and additional details.
     *
     * @param initialBalance   the initial balance of the account
     * @param accountId        the unique ID of the account
     * @param accountHolderName the name of the account holder
     * @param accountType      the type of the account (e.g., Checking, Savings)
     * @param accountStatus    the status of the account (e.g., Active, Inactive, Closed)
     * @param creationDate     the creation date of the account
     */
    public Account(double initialBalance, int accountId, String accountHolderName, String accountType, String accountStatus, Date creationDate) {
        this.balance = initialBalance;
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.creationDate = creationDate;
        this.lastUpdatedDate = new Date();
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        updateLastUpdatedDate();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        updateLastUpdatedDate();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
        updateLastUpdatedDate();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }


    private void updateLastUpdatedDate() {
        this.lastUpdatedDate = new Date();
    }

    public double getBalance() {
        synchronized (lock) {
            return this.balance;
        }
    }

    public void setBalance(double balance) {
        synchronized (lock) {
            this.balance = balance;
            updateLastUpdatedDate();
        }
    }

    public Object getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + getAccountId() +
                ", accountHolderName='" + getAccountHolderName() + '\'' +
                ", accountType='" + getAccountType() + '\'' +
                ", accountStatus='" + getAccountStatus() + '\'' +
                ", creationDate=" + getCreationDate() +
                ", lastUpdatedDate=" + getLastUpdatedDate() +
                ", balance=" + getBalance() +
                ", lock=" + getLock() +
                '}';
    }
}
