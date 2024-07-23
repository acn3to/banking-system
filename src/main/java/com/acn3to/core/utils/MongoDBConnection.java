package com.acn3to.core.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Manages the MongoDB connection and provides access to the MongoDB database.
 * <p>
 * This class ensures a single MongoDB connection instance and provides access to the MongoDB database used for operations.
 * </p>
 */
public class MongoDBConnection {
    private static final String CONNECTION_STRING = System.getenv("MONGO_URI");
    private static final String DATABASE_NAME = "bank";
    private static MongoClient mongoClient = null;

    /**
     * Returns the MongoDatabase instance, creating the client if necessary.
     *
     * @return the MongoDatabase instance
     */
    public static synchronized MongoDatabase getDatabase() {
        if (mongoClient == null) {
            if (CONNECTION_STRING == null || CONNECTION_STRING.isEmpty()) {
                throw new RuntimeException("MONGO_URI environment variable not set.");
            }
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to MongoDB", e);
            }
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    /**
     * Closes the MongoClient connection.
     */
    public static synchronized void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
