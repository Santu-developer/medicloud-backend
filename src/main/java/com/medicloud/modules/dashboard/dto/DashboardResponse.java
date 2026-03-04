package com.medicloud.modules.dashboard.dto;

import java.math.BigDecimal;

public class DashboardResponse {

    private BigDecimal todaySale;
    private BigDecimal totalSale;
    private BigDecimal totalProfit;
    private Long lowStockCount;

    public DashboardResponse() {
    }

    public DashboardResponse(BigDecimal todaySale, BigDecimal totalSale, BigDecimal totalProfit, Long lowStockCount) {
        this.todaySale = todaySale;
        this.totalSale = totalSale;
        this.totalProfit = totalProfit;
        this.lowStockCount = lowStockCount;
    }

    public BigDecimal getTodaySale() {
        return todaySale;
    }

    public void setTodaySale(BigDecimal todaySale) {
        this.todaySale = todaySale;
    }

    public BigDecimal getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(BigDecimal totalSale) {
        this.totalSale = totalSale;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(Long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    @Override
    public String toString() {
        return "DashboardResponse{" +
                "todaySale=" + todaySale +
                ", totalSale=" + totalSale +
                ", totalProfit=" + totalProfit +
                ", lowStockCount=" + lowStockCount +
                '}';
    }
}