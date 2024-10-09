package com.example.patientproject.models;

import java.sql.Timestamp;

public class DetailsTest {
    private Long id;            // Unique identifier for the DetailsTest
    private Integer visitId;      // Foreign key referencing visit
    private Long testId;       // Foreign key referencing test
    private Long orderId;      // Foreign key referencing patientOrder
    private String name;       // Name field
    private Integer dose;       // Dose field
    private String route;      // Route field
    private String frequency;   // Frequency field
    private String unit;       // Unit field
    private String Q1anwar;    // Additional string field
    private Timestamp createdAt; // Date and time of creation
    private String createdBy;    // User who created the record
    private Timestamp updatedAt; // Date and time of last update
    private String updatedBy;    // User who updated the record
    private Timestamp deletedAt; // Date and time of deletion
    private String deletedBy;    // User who deleted the record

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVisitId() {
        return Math.toIntExact(visitId);
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public int getTestId() {
        return Math.toIntExact(testId);
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public int getOrderId() {
        return Math.toIntExact(orderId);
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQ1anwar() {
        return Q1anwar;
    }

    public void setQ1anwar(String Q1anwar) {
        this.Q1anwar = Q1anwar;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
