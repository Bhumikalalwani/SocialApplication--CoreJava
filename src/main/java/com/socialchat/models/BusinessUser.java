package com.socialchat.models;

public class BusinessUser extends User {
    private String businessName;

    public BusinessUser(String id, String username, String displayName, String businessName) {
        super(id, username, displayName);
        this.businessName = businessName;
    }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
}
