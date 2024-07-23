package com.acn3to.core.repositories;

import com.acn3to.core.entities.Account;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository class for managing accounts in a MongoDB collection.
 * <p>
 * This class provides methods for saving, finding, and deleting accounts in the MongoDB collection.
 * </p>
 */
public class AccountRepository {
    private final MongoCollection<Document> collection;

    /**
     * Constructs an AccountRepository with a MongoDB connection.
     *
     * @param database the MongoDatabase instance to use
     */
    public AccountRepository(MongoDatabase database) {
        this.collection = database.getCollection("accounts");
    }

    /**
     * Saves an account to the MongoDB collection.
     *
     * @param account the Account object to save
     */
    public void save(Account account) {
        Document query = new Document("accountId", account.getAccountId());
        Document doc = Document.parse(accountToJson(account).toString());
        collection.replaceOne(query, doc, new ReplaceOptions().upsert(true));
    }

    /**
     * Finds an account by its ID.
     *
     * @param accountId the ID of the account to find
     * @return the Account object with the given ID, or null if not found
     */
    public Account findById(int accountId) {
        Document query = new Document("accountId", accountId);
        Document doc = collection.find(query).first();
        return doc != null ? documentToAccount(doc) : null;
    }

    /**
     * Finds all accounts in the MongoDB collection.
     *
     * @return a list of all Account objects
     */
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        for (Document doc : collection.find()) {
            accounts.add(documentToAccount(doc));
        }
        return accounts;
    }

    /**
     * Converts an Account object to a JsonObject.
     *
     * @param account the Account object
     * @return the JsonObject representation of the Account
     */
    private JsonObject accountToJson(Account account) {
        JsonObject json = new JsonObject();
        json.addProperty("accountId", account.getAccountId());
        json.addProperty("accountHolderName", account.getAccountHolderName());
        json.addProperty("accountType", account.getAccountType());
        json.addProperty("accountStatus", account.getAccountStatus());
        json.addProperty("creationDate", account.getCreationDate().toInstant().toString());
        json.addProperty("lastUpdatedDate", account.getLastUpdatedDate().toInstant().toString());
        json.addProperty("balance", account.getBalance());
        return json;
    }

    /**
     * Converts a Document from MongoDB to an Account object.
     *
     * @param doc the MongoDB Document
     * @return the Account object
     */
    private Account documentToAccount(Document doc) {
        int accountId = doc.getInteger("accountId");
        String accountHolderName = doc.getString("accountHolderName");
        String accountType = doc.getString("accountType");
        String accountStatus = doc.getString("accountStatus");
        Date creationDate = doc.getDate("creationDate");
        Date lastUpdatedDate = doc.getDate("lastUpdatedDate");
        double balance = doc.getDouble("balance");
        return new Account(balance, accountId, accountHolderName, accountType, accountStatus, creationDate);
    }
}
