package com.example.patientproject.models;

import java.time.LocalDate;

public class VitalSign {
    private int id;
    private int visitId;
    private LocalDate date;
    private String bp;
    private int pulse;
    private int temperature;
    private String userName ;
    public VitalSign() {
    }

    public VitalSign(int id, int visitId, LocalDate date, String bp, int pulse, int temperature, String userName) {
        this.id = id;
        this.visitId = visitId;
        this.date = date;
        this.bp = bp;
        this.pulse = pulse;
        this.temperature = temperature;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
      this.bp = bp;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }
}
