package com.medicloud.modules.medicine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicineRequest {

    @NotBlank(message = "Medicine name is required")
    private String name;
    private String company;
    private String batchNo;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;

    @NotNull(message = "Selling price is required")
    @Positive(message = "Selling price must be positive")
    private BigDecimal sellingPrice;

    private LocalDate expiryDate;

    public MedicineRequest() {
    }

    public MedicineRequest(String name, String company, String batchNo, Integer quantity,
                           BigDecimal purchasePrice, BigDecimal sellingPrice, LocalDate expiryDate) {
        this.name = name;
        this.company = company;
        this.batchNo = batchNo;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "MedicineRequest{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}