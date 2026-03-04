package com.medicloud.modules.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SupplierRequest {

    @NotBlank(message = "Supplier name is required")
    private String name;
    private String phone;

    @Email(message = "Invalid email format")
    private String email;
    private String address;

    public SupplierRequest() {
    }

    public SupplierRequest(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    @Override
    public String toString() {
        return "SupplierRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}