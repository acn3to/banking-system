package com.acn3to;

import com.acn3to.core.Account;
import com.acn3to.core.Bank;
import com.acn3to.threads.CustomerThread;

/**
 * The main entry point for the banking system application.
 * This class initializes the banking system by creating a {@link Bank}
 * instance and adding several {@link Account} objects to it. It then
 * starts multiple {@link CustomerThread}s to simulate concurrent transactions.
 * The main method is responsible for setting up and running the application.
 */
public class Main {
    /**
     * The main method that starts the banking system application.
     * <ul>
     *   <li>Initializes a {@link Bank} instance.</li>
     *   <li>Creates and adds 5 {@link Account} objects to the bank.</li>
     *   <li>Starts 5 {@link CustomerThread}s to perform transactions concurrently.</li>
     *   <li>Waits for all threads to complete their execution.</li>
     * </ul>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Bank bank = new Bank();

        for (int i = 0; i < 5; i++) {
            Account account = new Account(1000, i + 1);
            bank.addAccount(account);
        }

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new CustomerThread(bank, i + 1);
            threads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
