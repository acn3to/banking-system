package com.acn3to.core.repositories;

import com.acn3to.core.entities.BankAgency;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing bank agencies in a MongoDB collection.
 * <p>
 * This class provides methods for saving, finding, and retrieving bank agencies from the MongoDB collection.
 * </p>
 */
public class BankAgencyRepository {
    private final MongoCollection<Document> bankAgenciesCollection;

    /**
     * Constructs a BankAgencyRepository with a MongoDB connection.
     *
     * @param database the MongoDatabase instance to use
     */
    public BankAgencyRepository(MongoDatabase database) {
        this.bankAgenciesCollection = database.getCollection("bankAgencies");
    }

    /**
     * Saves a bank agency to the MongoDB collection.
     *
     * @param bankAgency the BankAgency object to save
     */
    public void save(BankAgency bankAgency) {
        Document document = new Document("agencyId", bankAgency.getAgencyId())
                .append("latitude", bankAgency.getLatitude())
                .append("longitude", bankAgency.getLongitude())
                .append("address", bankAgency.getAddress())
                .append("phoneNumber", bankAgency.getPhoneNumber())
                .append("managerName", bankAgency.getManagerName())
                .append("openingDate", bankAgency.getOpeningDate())
                .append("status", bankAgency.getStatus());
        bankAgenciesCollection.insertOne(document);
    }

    /**
     * Finds a bank agency by its ID.
     *
     * @param agencyId the ID of the bank agency to find
     * @return the BankAgency object with the given ID, or null if not found
     */
    public BankAgency findById(String agencyId) {
        Document document = bankAgenciesCollection.find(eq("agencyId", agencyId)).first();
        if (document == null) {
            return null;
        }
        return new BankAgency(
                document.getString("agencyId"),
                document.getDouble("latitude"),
                document.getDouble("longitude"),
                document.getString("address"),
                document.getString("phoneNumber"),
                document.getString("managerName"),
                document.getDate("openingDate"),
                document.getString("status")
        );
    }

    /**
     * Finds all bank agencies in the MongoDB collection.
     *
     * @return a list of all BankAgency objects
     */
    public List<BankAgency> findAll() {
        List<BankAgency> agencies = new ArrayList<>();
        for (Document doc : bankAgenciesCollection.find()) {
            BankAgency agency = new BankAgency(
                    doc.getString("agencyId"),
                    doc.getDouble("latitude"),
                    doc.getDouble("longitude"),
                    doc.getString("address"),
                    doc.getString("phoneNumber"),
                    doc.getString("managerName"),
                    doc.getDate("openingDate"),
                    doc.getString("status")
            );
            agencies.add(agency);
        }
        return agencies;
    }
}
