package com.example.fixit;

import java.io.Serializable;

public class RecentEarningsModel implements Serializable {
    String date, customer, service, contact, earned;

    public RecentEarningsModel() {
    }

    public RecentEarningsModel(String date, String customer, String service, String contact, String earned) {
        this.date = date;
        this.customer = customer;
        this.service = service;
        this.contact = contact;
        this.earned = earned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEarned() {
        return earned;
    }

    public void setEarned(String earned) {
        this.earned = earned;
    }
}
