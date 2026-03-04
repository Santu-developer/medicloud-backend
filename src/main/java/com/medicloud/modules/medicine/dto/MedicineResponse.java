package com.medicloud.modules.medicine.dto;

import java.math.BigDecimal;

public class MedicineResponse {

    private Long id;
    private String name;
    private String company;
    private String batchNo;
    private Integer quantity;
    private BigDecimal sellingPrice;

    public MedicineResponse() {
    }

    public MedicineResponse(Long id, String name, String company, String batchNo, Integer quantity, BigDecimal sellingPrice) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.batchNo = batchNo;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "MedicineResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}