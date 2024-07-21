package com.acn3to.core;

import java.util.Date;

/**
 * Represents a financial transaction.
 *
 * @param date the date and time of the transaction
 * @param type the type of the transaction (e.g., Deposit, Withdrawal)
 * @param amount the amount of money involved in the transaction
 * @param balanceAfterTransaction the balance of the account after the transaction
 */
public record Transaction(Date date, String type, double amount, double balanceAfterTransaction) {}
