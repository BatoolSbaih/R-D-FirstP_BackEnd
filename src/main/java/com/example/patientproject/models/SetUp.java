package com.example.patientproject.models;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class SetUp {

    private Long id;
    private String type;
    private Integer price;
    private String name;
    private String currency;
    private String code;
    private Time timing;
    private Timestamp createdAt;    // تاريخ ووقت الإنشاء
    private String createdBy;       // المستخدم الذي أنشأ السجل
    private Timestamp updatedAt;    // تاريخ ووقت آخر تحديث
    private String updatedBy;       // المستخدم الذي قام بالتحديث
    private Timestamp deletedAt;    // تاريخ ووقت الحذف
    private String deletedBy;       // المستخدم الذي قام بالحذف

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Time getTiming() {
        return timing;
    }

    public void setTiming(Time timing) {
        this.timing = timing;
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

