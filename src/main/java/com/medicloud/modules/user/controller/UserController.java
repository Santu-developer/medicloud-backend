package com.medicloud.modules.user.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.user.dto.UserRequest;
import com.medicloud.modules.user.dto.UserResponse;
import com.medicloud.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Create and retrieve users — requires OWNER or ADMIN authority")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Create User",
            description = "Create a new staff/admin user for the current shop (shopId is taken from JWT)."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error or email already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied — OWNER/ADMIN only")
    })
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.createUser(request), "User created"));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get User by ID",
            description = "Retrieve a user by ID. Only returns users belonging to the current shop."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User fetched"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ApiResponse<UserResponse> getUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        return ApiResponse.success(userService.getUser(id), "User fetched");
    }
}