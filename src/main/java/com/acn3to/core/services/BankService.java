package com.acn3to.core.services;

import com.acn3to.core.entities.Account;
import com.acn3to.core.entities.BankAgency;
import com.acn3to.core.repositories.AccountRepository;
import com.acn3to.core.repositories.BankAgencyRepository;

import java.util.List;

/**
 * Service class for managing bank accounts and bank agencies.
 */
public class BankService {
    private final AccountRepository accountRepository;
    private final BankAgencyRepository bankAgencyRepository;

    /**
     * Constructs a BankService with the given repositories.
     *
     * @param accountRepository    the repository for managing accounts
     * @param bankAgencyRepository the repository for managing bank agencies
     */
    public BankService(AccountRepository accountRepository, BankAgencyRepository bankAgencyRepository) {
        this.accountRepository = accountRepository;
        this.bankAgencyRepository = bankAgencyRepository;
    }

    /**
     * Adds a new account to the bank.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        if (account != null) {
            accountRepository.save(account);
        }
    }

    /**
     * Retrieves the account associated with the given ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the account associated with the given ID, or null if not found
     */
    public Account getAccount(int accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Retrieves all accounts.
     *
     * @return a list of all accounts
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Adds a new bank agency.
     *
     * @param agency the bank agency to add
     */
    public void addBankAgency(BankAgency agency) {
        if (agency != null) {
            bankAgencyRepository.save(agency);
        }
    }

    /**
     * Retrieves the bank agency associated with the given ID.
     *
     * @param agencyId the ID of the bank agency to retrieve
     * @return the bank agency associated with the given ID, or null if not found
     */
    public BankAgency getBankAgency(String agencyId) {
        return bankAgencyRepository.findById(agencyId);
    }

    /**
     * Retrieves all bank agencies.
     *
     * @return a list of all bank agencies
     */
    public List<BankAgency> getAllBankAgencies() {
        return bankAgencyRepository.findAll();
    }
}
