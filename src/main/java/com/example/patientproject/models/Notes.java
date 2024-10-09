package com.example.patientproject.models;

public class Notes {
    private int id;
    private int systemId;
    private int visitId;
    private int organId;
    private boolean checkValue;
    private String notes;

    public Notes(int id, int systemId, int visitId, int organId, boolean checkValue, String notes) {
        this.id = id;
        this.systemId = systemId;
        this.visitId = visitId;
        this.organId = organId;
        this.checkValue = checkValue;
        this.notes = notes;
    }

    public Notes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public boolean isCheckValue() {
        return checkValue;
    }

    public void setCheckValue(boolean checkValue) {
        this.checkValue = checkValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
