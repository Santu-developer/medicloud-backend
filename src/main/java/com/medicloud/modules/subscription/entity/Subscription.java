package com.medicloud.modules.subscription.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions", indexes = {
		@Index(name = "idx_subscription_shop_id", columnList = "shopId")
})
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long shopId;

	@Column(nullable = false)
	private Long planId;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@Column(nullable = false)
	private String status;

	public Subscription() {
	}

	public Subscription(Long id, Long shopId, Long planId, LocalDateTime startDate, LocalDateTime endDate, String status) {
		this.id = id;
		this.shopId = shopId;
		this.planId = planId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Subscription{" +
				"id=" + id +
				", shopId=" + shopId +
				", status='" + status + '\'' +
				'}';
	}
}