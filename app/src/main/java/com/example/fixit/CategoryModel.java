package com.example.fixit;

public class CategoryModel {

    String service;
    int serviceicon;

    public CategoryModel(String service, int serviceicon) {
        this.service = service;
        this.serviceicon = serviceicon;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getServiceicon() {
        return serviceicon;
    }

    public void setServiceicon(int serviceicon) {
        this.serviceicon = serviceicon;
    }
}
