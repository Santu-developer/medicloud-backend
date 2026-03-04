package com.medicloud.modules.subscription.dto;

public class SubscriptionResponse {
	private Long id;
	private String planCode;

	public SubscriptionResponse() {
	}

	public SubscriptionResponse(Long id, String planCode) {
		this.id = id;
		this.planCode = planCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	@Override
	public String toString() {
		return "SubscriptionResponse{" +
				"id=" + id +
				", planCode='" + planCode + '\'' +
				'}';
	}
}
