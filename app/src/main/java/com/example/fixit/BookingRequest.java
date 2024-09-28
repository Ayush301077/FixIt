package com.example.fixit;

public class BookingRequest {
    private String userName;
    private String contact;
    private String area;
    private String city;
    private String service;
    private String bookingDate;
    String serviceProviderId;// Add the ID field
    private  String charges;


    public BookingRequest() {
    }

    public BookingRequest(String userName, String contact, String area, String city, String service, String bookingDate,String serviceProviderId, String charges) {
        this.userName = userName;
        this.contact = contact;
        this.area = area;
        this.city = city;
        this.service = service;
        this.bookingDate = bookingDate;
        this.serviceProviderId = serviceProviderId;
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

    public String getUserName() {
        return userName;
    }

    public String getContact() {
        return contact;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getService() {
        return service;
    }

    public String getBookingDate() {
        return bookingDate;
    }
}

