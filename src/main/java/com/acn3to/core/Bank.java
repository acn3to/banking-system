package com.acn3to.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages multiple bank accounts and provides methods to perform transactions
 * and access account details.
 * <p>
 * This class maintains a collection of accounts and allows transactions to be
 * performed concurrently. It provides methods to create new accounts and retrieve accounts by ID.
 * </p>
 */
public class Bank {
    private final Map<Integer, Account> accountsMap;
    private static int nextAccountId = 1;

    /**
     * Constructs a Bank with an empty set of accounts.
     */
    public Bank() {
        this.accountsMap = new HashMap<>();
    }

    /**
     * Adds a new account to the bank with a unique ID.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        int accountId = nextAccountId++;
        accountsMap.put(accountId, account);
        System.out.println("Created new account with ID: " + accountId);
    }

    /**
     * Retrieves the account associated with the given ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the account associated with the given ID, or null if not found
     */
    public Account getAccount(int accountId) {
        return accountsMap.get(accountId);
    }
}
