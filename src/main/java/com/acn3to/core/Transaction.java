package com.acn3to.core;

import java.util.Date;

/**
 * Represents a transaction in a bank account.
 */
public record Transaction(Date date, String type, double amount, double balanceAfterTransaction) {
    /**
     * Constructs a Transaction with the specified details.
     *
     * @param date                    the date of the transaction
     * @param type                    the type of the transaction (Deposit or Withdraw)
     * @param amount                  the amount of money involved in the transaction
     * @param balanceAfterTransaction the account balance after the transaction
     */
    public Transaction {
    }
}
