package com.acn3to.core.repositories;

import com.acn3to.core.entities.Account;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository {
    private final MongoCollection<Document> accountsCollection;

    public AccountRepository(MongoDatabase database) {
        this.accountsCollection = database.getCollection("accounts");
    }

    public void save(Account account) {
        Document document = new Document("accountId", account.getAccountId())
                .append("accountHolderName", account.getAccountHolderName())
                .append("accountType", account.getAccountType())
                .append("accountStatus", account.getAccountStatus())
                .append("creationDate", account.getCreationDate())
                .append("lastUpdatedDate", account.getLastUpdatedDate())
                .append("balance", account.getBalance());
        accountsCollection.replaceOne(eq("accountId", account.getAccountId()), document, new ReplaceOptions().upsert(true));
    }

    public Account findById(int accountId) {
        Document document = accountsCollection.find(eq("accountId", accountId)).first();
        if (document == null) {
            return null;
        }
        return new Account(
                document.getDouble("balance"),
                document.getInteger("accountId"),
                document.getString("accountHolderName"),
                document.getString("accountType"),
                document.getString("accountStatus"),
                document.getDate("creationDate")
        );
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        for (Document doc : accountsCollection.find()) {
            Account account = new Account(
                    doc.getDouble("balance"),
                    doc.getInteger("accountId"),
                    doc.getString("accountHolderName"),
                    doc.getString("accountType"),
                    doc.getString("accountStatus"),
                    doc.getDate("creationDate")
            );
            accounts.add(account);
        }
        return accounts;
    }
}
