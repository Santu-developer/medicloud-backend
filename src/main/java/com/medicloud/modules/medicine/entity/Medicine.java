package com.medicloud.modules.medicine.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicines", indexes = {
		@Index(name = "idx_medicine_shop_id", columnList = "shopId"),
		@Index(name = "idx_medicine_expiry", columnList = "expiryDate")
})
@EntityListeners(AuditingEntityListener.class)
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String company;

	private String batchNo;

	@Column(nullable = false)
	private Integer quantity;

	@Column(precision = 10, scale = 2)
	private BigDecimal purchasePrice;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal sellingPrice;

	private LocalDate expiryDate;

	@Column(nullable = false)
	private Long shopId;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	public Medicine() {
	}

	public Medicine(Long id, String name, String company, String batchNo, Integer quantity,
	                BigDecimal purchasePrice, BigDecimal sellingPrice, LocalDate expiryDate,
	                Long shopId, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.company = company;
		this.batchNo = batchNo;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		this.expiryDate = expiryDate;
		this.shopId = shopId;
		this.createdAt = createdAt;
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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Medicine{" +
				"id=" + id +
				", name='" + name + '\'' +
				", company='" + company + '\'' +
				", batchNo='" + batchNo + '\'' +
				'}';
	}
}