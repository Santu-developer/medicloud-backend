package com.medicloud.modules.shop.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateShopRequest {
	@NotBlank
	private String name;

	public CreateShopRequest() {
	}

	public CreateShopRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CreateShopRequest{" +
				"name='" + name + '\'' +
				'}';
	}
}
