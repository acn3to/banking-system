package com.acn3to.threads;

import com.acn3to.core.Account;
import com.acn3to.core.Bank;

import java.util.Random;

/**
 * Represents a thread that simulates a customer performing transactions
 * on a specific bank account.
 * <p>
 * This class extends Thread and simulates concurrent deposits and withdrawals
 * on an account. The transactions are performed randomly with varying amounts
 * and types (deposit or withdrawal). Each thread sleeps for a random duration
 * between transactions to simulate real-world activity.
 * </p>
 */
public class CustomerThread extends Thread {
    private final Bank bank;
    private final int accountId;
    private final Random random = new Random();

    /**
     * Constructs a CustomerThread for a specific account ID.
     *
     * @param bank the {@code Bank} instance that contains the account to perform transactions on
     * @param accountId the ID of the account on which transactions will be performed
     */
    public CustomerThread(Bank bank, int accountId) {
        this.bank = bank;
        this.accountId = accountId;
        setName("AccountThread-" + accountId);
    }

    @Override
    public void run() {
        Account account = bank.getAccount(accountId);
        if (account != null) {
            try {
                for (int i = 0; i < 10; i++) {
                    double amount = random.nextDouble() * 1000;
                    if (random.nextBoolean()) {
                        System.out.printf("%s - Account ID %d: Attempting deposit of %.2f%n",
                                Thread.currentThread().getName(), accountId, amount);
                        account.deposit(amount);
                    } else {
                        System.out.printf("%s - Account ID %d: Attempting withdrawal of %.2f%n",
                                Thread.currentThread().getName(), accountId, amount);
                        account.withdraw(amount);
                    }
                    Thread.sleep(random.nextInt(2000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
