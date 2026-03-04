package com.medicloud.superadmin.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.shop.entity.Shop;
import com.medicloud.modules.subscription.entity.Subscription;
import com.medicloud.superadmin.dto.AdminRequest;
import com.medicloud.superadmin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superadmin")
@Tag(name = "Super Admin", description = "Super-admin operations — login is public, other endpoints require SUPER_ADMIN role")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(
            summary = "Super Admin Login",
            description = "Authenticate a super-admin with email and password. Returns a JWT token."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Admin login successful — JWT token returned"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ApiResponse<String> login(@Valid @RequestBody AdminRequest dto) {
        return ApiResponse.success(
                adminService.login(dto.getEmail(), dto.getPassword()),
                "Admin login success"
        );
    }

    @GetMapping("/shops")
    @Operation(
            summary = "List All Shops",
            description = "Retrieve all registered shops in the platform. Requires SUPER_ADMIN authority."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Shops retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied — SUPER_ADMIN only")
    })
    public ApiResponse<List<Shop>> allShops() {
        return ApiResponse.success(adminService.getAllShops(), "All shops");
    }

    @GetMapping("/subscriptions")
    @Operation(
            summary = "List All Subscriptions",
            description = "Retrieve all subscriptions across all shops. Requires SUPER_ADMIN authority."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Subscriptions retrieved"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied — SUPER_ADMIN only")
    })
    public ApiResponse<List<Subscription>> allSubs() {
        return ApiResponse.success(adminService.getAllSubscriptions(), "All subscriptions");
    }
}