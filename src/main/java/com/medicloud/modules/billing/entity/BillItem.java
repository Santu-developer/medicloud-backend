package com.medicloud.modules.billing.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_items", indexes = {
		@Index(name = "idx_bill_item_bill_id", columnList = "billId"),
		@Index(name = "idx_bill_item_shop_id", columnList = "shopId")
})
public class BillItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long medicineId;

	@Column(nullable = false)
	private String medicineName;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal total;

	@Column(nullable = false)
	private Long billId;

	@Column(nullable = false)
	private Long shopId;

	public BillItem() {
	}

	public BillItem(Long id, Long medicineId, String medicineName, Integer quantity,
	                BigDecimal price, BigDecimal total, Long billId, Long shopId) {
		this.id = id;
		this.medicineId = medicineId;
		this.medicineName = medicineName;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
		this.billId = billId;
		this.shopId = shopId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Override
	public String toString() {
		return "BillItem{" +
				"id=" + id +
				", medicineId=" + medicineId +
				", medicineName='" + medicineName + '\'' +
				", quantity=" + quantity +
				'}';
	}
}