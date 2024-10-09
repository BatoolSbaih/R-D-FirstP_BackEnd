package com.example.patientproject.models;

public class PhysicalNotes {
    private int id;
    private int examinationId;
    private int statusId;
    private int visitId;
    private String notes;
    private boolean checkValue;

    public PhysicalNotes(int id, int examinationId, int statusId, int visitId, String notes, boolean checkValue) {
        this.id = id;
        this.examinationId = examinationId;
        this.statusId = statusId;
        this.visitId = visitId;
        this.notes = notes;
        this.checkValue = checkValue;
    }

    public PhysicalNotes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isCheckValue() {
        return checkValue;
    }

    public void setCheckValue(boolean checkValue) {
        this.checkValue = checkValue;
    }
}
