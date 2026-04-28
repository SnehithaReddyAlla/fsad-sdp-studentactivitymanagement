package com.klef.fsad.sdp.dto;

public class ContactDTO 
{
    private String name;
    private String email;
    private String contact;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}