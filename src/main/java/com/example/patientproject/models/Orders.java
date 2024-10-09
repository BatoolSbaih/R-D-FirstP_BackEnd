package com.example.patientproject.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Orders {

    private Long id;
    private String type;
    private String name;
    private String code;// Primary key
    private Timestamp createdAt;     // Date and time of creation
    private String createdBy;        // User who created the record
    private Timestamp updatedAt;     // Date and time of last update
    private String updatedBy;        // User who updated the record
    private Timestamp deletedAt;     // Date and time of deletion
    private String deletedBy;        // User who deleted the record
    private Long visiteId;           // Foreign key for visit
    private Long setupId;            // Foreign key for setup
    private String status;           // Status of the order
    private String priority;         // Priority of the order
    private Timestamp RequstDate;
    // Getters and Setters

    public Timestamp getRequstDate() {
        return RequstDate;
    }

    public void setRequstDate(Timestamp requstDate) {
        RequstDate = requstDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVisiteId() {
        return visiteId;
    }

    public void setVisiteId(Long visiteId) {
        this.visiteId = visiteId;
    }

    public Long getSetupId() {
        return setupId;
    }

    public void setSetupId(Long setupId) {
        this.setupId = setupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
