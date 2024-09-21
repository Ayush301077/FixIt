package com.example.fixit;

import java.io.Serializable;

public class ServiceProviderInfo implements Serializable {
    String name, services, contact, city;
    String profileImage;
    String email;


    public ServiceProviderInfo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ServiceProviderInfo that = (ServiceProviderInfo) obj;

        // Compare fields that uniquely identify the ServiceProviderInfo (e.g., email or contact)
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }


    public ServiceProviderInfo(String name, String service, String contact, String city, String profileImage, String email) {
        this.name = name;
        this.services = service;
        this.contact = contact;
        this.city = city;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
