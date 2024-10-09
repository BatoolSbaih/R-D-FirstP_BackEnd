package com.example.patientproject.models;

public class Organ {
    private int id;
    private String name;
    private int systemId;

    public Organ(int id, String name, int systemId) {
        this.id = id;
        this.name = name;
        this.systemId = systemId;
    }
    public Organ() {

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

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }
}
