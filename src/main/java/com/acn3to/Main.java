package com.acn3to;

import com.acn3to.core.Account;
import com.acn3to.core.Bank;
import com.acn3to.threads.CustomerThread;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        for (int i = 0; i < 5; i++) {
            Account account = new Account(1000);
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