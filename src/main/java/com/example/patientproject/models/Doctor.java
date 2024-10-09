package com.example.patientproject.models;

public class Doctor {
    private Integer doctorID;
    private String fullname;
    private String email;
    private Integer phone;
    private String address;
    private String specialty;

    public Doctor(String address, String email, String fullname, Integer doctorID, Integer phone, String specialty) {
        this.address = address;
        this.email = email;
        this.fullname = fullname;
        this.doctorID = doctorID;
        this.phone = phone;
        this.specialty = specialty;
    }

    public Doctor() {

    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
