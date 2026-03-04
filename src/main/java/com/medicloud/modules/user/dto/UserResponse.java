package com.medicloud.modules.user.dto;

import com.medicloud.modules.user.entity.Role;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private Long shopId;

    public UserResponse() {
    }

    public UserResponse(Long id, String name, String email, Role role, Long shopId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.shopId = shopId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}