package com.example.patientproject.models;

public class Status {
    private int id;
    private String name;
    private int physicalexaminationId;
    public Status() {
    }
    public Status(int id, String name, int physicalexaminationId) {
        this.id = id;
        this.name = name;
        this.physicalexaminationId = physicalexaminationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhysicalexaminationId() {
        return physicalexaminationId;
    }

    public void setPhysicalexaminationId(int physicalexaminationId) {
        this.physicalexaminationId = physicalexaminationId;
    }
}
