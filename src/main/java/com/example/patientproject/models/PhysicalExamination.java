package com.example.patientproject.models;

public class PhysicalExamination {
    private int examinationId;
    private String examinationName;

    public PhysicalExamination(int examinationId, String examinationName) {
        this.examinationId = examinationId;
        this.examinationName = examinationName;
    }

    public PhysicalExamination() {

    }

    public int getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
