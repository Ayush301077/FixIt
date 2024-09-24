package com.example.fixit;

public class RequestModel {

    String from, contact, area, city, service, bookdate;
    String serviceProviderId;  // Add the ID field


    public RequestModel(String from, String contact, String area, String city, String service, String bookdate,String serviceProviderId) {
        this.from = from;
        this.contact = contact;
        this.area = area;
        this.city = city;
        this.service = service;
        this.bookdate = bookdate;
        this.serviceProviderId =serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
