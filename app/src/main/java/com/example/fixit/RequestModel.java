package com.example.fixit;

public class RequestModel {

    String userName, contact, area, city, service, bookingDate;
    String serviceProviderId, charges;  // Add the ID field


    public RequestModel(String userName, String contact, String area, String city, String service, String bookingDate,String serviceProviderId, String charges) {
        this.userName = userName;
        this.contact = contact;
        this.area = area;
        this.city = city;
        this.service = service;
        this.bookingDate = bookingDate;
        this.serviceProviderId =serviceProviderId;
        this.charges = charges;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
