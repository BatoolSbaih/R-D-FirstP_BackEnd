package com.example.patientproject.models;

import java.time.LocalDate;
import java.time.LocalTime;
public class Visits {

    private Integer visitID;
    private int patientID;
    private String fileNumber;
    private int doctorID;
    private int clinicID;
    private LocalDate date;
    private LocalTime time;

    // Parameterized constructor


    public Visits(Integer visitID, int patientID, String fileNumber, int doctorID, int clinicID, LocalDate date, LocalTime time) {
        this.visitID = visitID;
        this.patientID = patientID;
        this.fileNumber = fileNumber;
        this.doctorID = doctorID;
        this.clinicID = clinicID;
        this.date = date;
        this.time = time;
    }

    public Visits() {

    }

    public Integer getVisitID() {

        return visitID;
    }

    public void setVisitID(Integer visitID) {
        this.visitID = visitID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int clinicID) {
        this.clinicID = clinicID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Visits{" +
                "visitID=" + visitID +
                ", patientID=" + patientID +
                ", fileNumber='" + fileNumber + '\'' +
                ", doctorId=" + doctorID +
                ", clinicID=" + clinicID +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
