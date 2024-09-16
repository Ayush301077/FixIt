package com.example.fixit;

public class ServiceProviderInfo {
    String name, service, contact, location, ratings;
    int profilephoto;

    public ServiceProviderInfo(String name, String service, String contact, String location, String ratings, int profilephoto) {
        this.name = name;
        this.service = service;
        this.contact = contact;
        this.location = location;
        this.ratings = ratings;
        this.profilephoto = profilephoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public int getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(int profilephoto) {
        this.profilephoto = profilephoto;
    }
}
