package com.example.patientproject.models;

public class Patient {


private Integer patientID;
private String fullName;

private String email;

private String phoneNumber;

private Integer age;

private String address;

private String notes;

private String fileNumber;

private String medicalHistory;

    public Patient() {

    }

    public Patient(String address, Integer age, Integer allergies, String email, String fileNumber, String fullName, String medicalHistory, String notes, String phoneNumber) {
        this.address = address;
        this.age = age;

        this.email = email;
        this.fileNumber = fileNumber;
        this.fullName = fullName;

        this.medicalHistory = medicalHistory;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }
}

// Getters and Setters
