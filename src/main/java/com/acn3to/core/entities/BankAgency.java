package com.acn3to.core.entities;

import java.util.Date;

/**
 * Represents a bank agency with a unique agency ID, location, and additional details.
 */
public class BankAgency {
    private String agencyId;
    private double latitude;
    private double longitude;
    private String address;
    private String phoneNumber;
    private String managerName;
    private Date openingDate;
    private String status;

    /**
     * Constructs a BankAgency with the provided details.
     *
     * @param agencyId    the unique ID of the bank agency
     * @param latitude    the latitude of the bank agency location
     * @param longitude   the longitude of the bank agency location
     * @param address     the address of the bank agency
     * @param phoneNumber the contact phone number of the bank agency
     * @param managerName the name of the bank agency manager
     * @param openingDate the date the bank agency was opened
     * @param status      the current status of the bank agency (e.g., Open, Closed, Under Renovation)
     */
    public BankAgency(String agencyId, double latitude, double longitude, String address, String phoneNumber, String managerName, Date openingDate, String status) {
        this.agencyId = agencyId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.managerName = managerName;
        this.openingDate = openingDate;
        this.status = status;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankAgency{" +
                "agencyId='" + getAgencyId() + '\'' +
                ", latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                ", address='" + getAddress() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", managerName='" + getManagerName() + '\'' +
                ", openingDate=" + getOpeningDate() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
