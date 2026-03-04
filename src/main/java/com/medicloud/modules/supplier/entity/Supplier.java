package com.medicloud.modules.supplier.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers", indexes = {
		@Index(name = "idx_supplier_shop_id", columnList = "shopId")
})
@EntityListeners(AuditingEntityListener.class)
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String phone;
	private String email;
	private String address;

	@Column(nullable = false)
	private Long shopId;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	public Supplier() {
	}

	public Supplier(Long id, String name, String phone, String email, String address, Long shopId, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "Supplier{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}