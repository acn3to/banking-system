package com.acn3to.threads;

import com.acn3to.core.services.AccountService;

import java.util.Random;

public class CustomerThread extends Thread {
    private final int accountId;
    private final int transactionsPerThread;
    private final Random random = new Random();
    private final AccountService accountService;

    public CustomerThread(int accountId, AccountService accountService, int transactionsPerThread) {
        this.accountId = accountId;
        this.accountService = accountService;
        this.transactionsPerThread = transactionsPerThread;
        setName("AccountThread-" + accountId);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < transactionsPerThread; i++) {
                double amount = random.nextDouble() * 1000;
                boolean error = false;
                String transactionType;

                if (random.nextBoolean()) {
                    transactionType = "Deposit";
                    System.out.printf("%s - Account ID %d: Attempting deposit of %.2f%n",
                            Thread.currentThread().getName(), accountId, amount);
                    try {
                        accountService.deposit(accountId, amount);
                    } catch (Exception e) {
                        error = true;
                        System.out.printf("%s - Account ID %d: Deposit of %.2f failed%n",
                                Thread.currentThread().getName(), accountId, amount);
                    }
                } else {
                    transactionType = "Withdrawal";
                    System.out.printf("%s - Account ID %d: Attempting withdrawal of %.2f%n",
                            Thread.currentThread().getName(), accountId, amount);
                    try {
                        accountService.withdraw(accountId, amount);
                    } catch (Exception e) {
                        error = true;
                        System.out.printf("%s - Account ID %d: Withdrawal of %.2f failed%n",
                                Thread.currentThread().getName(), accountId, amount);
                    }
                }

                double balance = accountService.getAccountBalance(accountId);
                accountService.getTransactionLogger().logTransaction(accountId, transactionType, amount, balance, error);

                Thread.sleep(random.nextInt(2000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
