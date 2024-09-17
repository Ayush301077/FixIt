package com.example.fixit;

public class CategorieslistItem {

    String serviceType;
    int categoryIcon;

    public CategorieslistItem(String serviceType, int categoryIcon) {
        this.serviceType = serviceType;
        this.categoryIcon = categoryIcon;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
