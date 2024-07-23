package com.acn3to.core.repositories;

import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;

/**
 * Repository class for storing transactions in a MongoDB collection.
 * <p>
 * This class provides methods for saving transactions to the MongoDB collection.
 * </p>
 */
public class TransactionRepository {
    private final MongoCollection<Document> collection;

    /**
     * Constructs a TransactionRepository with a MongoDB connection.
     *
     * @param database the MongoDatabase instance to use
     */
    public TransactionRepository(MongoDatabase database) {
        this.collection = database.getCollection("transactions");
    }

    /**
     * Saves a transaction to the MongoDB collection.
     *
     * @param transactionJson the transaction details as a JsonObject
     */
    public void save(JsonObject transactionJson) {
        Document doc = Document.parse(transactionJson.toString());
        collection.insertOne(doc);
    }
}
