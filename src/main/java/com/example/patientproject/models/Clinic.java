package com.example.patientproject.models;

public class Clinic {

    private Integer id;
    private String name;
    private int phone;
    private String address;
    private String formalEmail;

    // Default constructor
    public Clinic() {
    }

    // Constructor without id (used when creating a new Clinic)
    public Clinic(String address, String formalEmail, String name, int phone) {
        this.address = address;
        this.formalEmail = formalEmail;
        this.name = name;
        this.phone = phone;
    }

    // Full constructor (used when id is known, like for updating)
    public Clinic(Integer id, String address, String formalEmail, String name, int phone) {
        this.id = id;
        this.address = address;
        this.formalEmail = formalEmail;
        this.name = name;
        this.phone = phone;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFormalEmail() {
        return formalEmail;
    }

    public void setFormalEmail(String formalEmail) {
        this.formalEmail = formalEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
