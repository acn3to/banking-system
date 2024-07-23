package com.acn3to.core.repositories;

import com.acn3to.core.entities.Transaction;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository class for managing transactions in a MongoDB collection.
 * <p>
 * This class provides methods for saving, finding, and retrieving transactions from the MongoDB collection.
 * </p>
 */
public class TransactionRepository {
    private final MongoCollection<Document> transactionsCollection;

    /**
     * Constructs a TransactionRepository with a MongoDB connection.
     *
     * @param database the MongoDatabase instance to use
     */
    public TransactionRepository(MongoDatabase database) {
        this.transactionsCollection = database.getCollection("transactions");
    }

    /**
     * Saves a transaction to the MongoDB collection.
     *
     * @param transaction the Transaction object to save
     */
    public void save(Transaction transaction) {
        Document document = new Document("accountId", transaction.accountId())
                .append("date", transaction.date())
                .append("type", transaction.type())
                .append("amount", transaction.amount())
                .append("balanceAfterTransaction", transaction.balanceAfterTransaction());
        transactionsCollection.insertOne(document);
    }

    /**
     * Finds a transaction by its account ID and date.
     *
     * @param accountId the ID of the account involved in the transaction
     * @param date the date and time of the transaction
     * @return the Transaction object with the given account ID and date, or null if not found
     */
    public Transaction findByAccountIdAndDate(int accountId, Date date) {
        Document query = new Document("accountId", accountId)
                .append("date", date);
        Document document = transactionsCollection.find(query).first();
        if (document == null) {
            return null;
        }
        return new Transaction(
                document.getInteger("accountId"),
                document.getDate("date"),
                document.getString("type"),
                document.getDouble("amount"),
                document.getDouble("balanceAfterTransaction")
        );
    }

    /**
     * Finds all transactions for a given account ID.
     *
     * @param accountId the ID of the account
     * @return a list of all Transaction objects for the given account ID
     */
    public List<Transaction> findByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        Document query = new Document("accountId", accountId);
        for (Document doc : transactionsCollection.find(query)) {
            Transaction transaction = new Transaction(
                    doc.getInteger("accountId"),
                    doc.getDate("date"),
                    doc.getString("type"),
                    doc.getDouble("amount"),
                    doc.getDouble("balanceAfterTransaction")
            );
            transactions.add(transaction);
        }
        return transactions;
    }
}
