package com.example.fixit;

public class ServiceProviderInfo {
    String name, services, contact, city, ratings;
    String profileImage;
    String email;


    public ServiceProviderInfo() {
    }

    public ServiceProviderInfo(String name, String service, String contact, String city, String ratings, String profileImage, String email) {
        this.name = name;
        this.services = service;
        this.contact = contact;
        this.city = city;
        this.ratings = ratings;
        this.profileImage = profileImage;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
