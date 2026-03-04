package com.medicloud.modules.billing.dto;

import java.math.BigDecimal;

public class BillResponse {

    private Long billId;
    private BigDecimal totalAmount;

    public BillResponse() {
    }

    public BillResponse(Long billId, BigDecimal totalAmount) {
        this.billId = billId;
        this.totalAmount = totalAmount;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "BillResponse{" +
                "billId=" + billId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}