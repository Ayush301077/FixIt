package com.example.fixit;

public class ServiceProviderInfo {
    String name, services, contact, city, ratings;
    String profile_image;

    public ServiceProviderInfo() {
    }

    public ServiceProviderInfo(String name, String service, String contact, String city, String ratings, String profile_image) {
        this.name = name;
        this.services = service;
        this.contact = contact;
        this.city = city;
        this.ratings = ratings;
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String service) {
        this.services = service;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
