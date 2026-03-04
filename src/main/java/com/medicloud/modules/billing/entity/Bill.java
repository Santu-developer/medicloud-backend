package com.medicloud.modules.billing.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills", indexes = {
		@Index(name = "idx_bill_shop_id", columnList = "shopId"),
		@Index(name = "idx_bill_created_at", columnList = "createdAt")
})
@EntityListeners(AuditingEntityListener.class)
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private Long shopId;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	public Bill() {
	}

	public Bill(Long id, BigDecimal totalAmount, Long shopId, LocalDateTime createdAt) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.shopId = shopId;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
		return "Bill{" +
				"id=" + id +
				", totalAmount=" + totalAmount +
				", shopId=" + shopId +
				'}';
	}
}