package com.acn3to;

import java.util.Date;
import java.util.Random;

import com.acn3to.core.entities.Account;
import com.acn3to.core.entities.BankAgency;
import com.acn3to.core.services.AccountService;
import com.acn3to.core.services.BankService;
import com.acn3to.core.services.TransactionLogger;
import com.acn3to.core.repositories.AccountRepository;
import com.acn3to.core.repositories.BankAgencyRepository;
import com.acn3to.core.repositories.TransactionRepository;
import com.acn3to.core.utils.MongoDBConnection;
import com.acn3to.threads.CustomerThread;
import com.mongodb.client.MongoDatabase;

public class Main {
    private static final int NUMBER_OF_ACCOUNTS = 10;
    private static final int NUMBER_OF_BANK_AGENCIES = 5;
    private static final int NUMBER_OF_THREADS = 10;
    private static final int TRANSACTIONS_PER_THREAD = 10;

    // Coordinates for New York
    private static final double BASE_LATITUDE = 40.7128;
    private static final double BASE_LONGITUDE = -74.0060;
    private static final double RADIUS = 0.1;

    public static void main(String[] args) {
        MongoDatabase database = null;
        try {
            database = MongoDBConnection.getDatabase();
            Random random = new Random();

            AccountRepository accountRepository = new AccountRepository(database);
            TransactionRepository transactionRepository = new TransactionRepository(database);
            BankAgencyRepository bankAgencyRepository = new BankAgencyRepository(database);

            TransactionLogger transactionLogger = new TransactionLogger(transactionRepository);
            AccountService accountService = new AccountService(accountRepository, transactionLogger);
            BankService bankService = new BankService(accountRepository, bankAgencyRepository);

            for (int i = 0; i < NUMBER_OF_BANK_AGENCIES; i++) {
                String agencyId = "Agency-" + (i + 1);
                double randomLatitude = BASE_LATITUDE + (random.nextDouble() * 2 - 1) * RADIUS;
                double randomLongitude = BASE_LONGITUDE + (random.nextDouble() * 2 - 1) * RADIUS;
                BankAgency agency = new BankAgency(
                        agencyId,
                        randomLatitude,
                        randomLongitude,
                        "Address " + (i + 1),
                        "Phone " + (i + 1),
                        "Manager " + (i + 1),
                        new Date(),
                        "Open"
                );
                bankService.addBankAgency(agency);
            }

            for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
                Account account = new Account(
                        1000.0,
                        i + 1,
                        "Account Holder " + (i + 1),
                        "Savings",
                        "Active",
                        new Date()
                );
                bankService.addAccount(account);
            }

            Thread[] threads = new Thread[NUMBER_OF_THREADS];
            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                threads[i] = new CustomerThread((i % NUMBER_OF_ACCOUNTS) + 1, accountService, TRANSACTIONS_PER_THREAD);
                threads[i].start();
            }

            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                MongoDBConnection.close();
            }
        }
    }
}
