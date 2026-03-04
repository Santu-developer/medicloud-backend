package com.medicloud.modules.auth.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.auth.dto.LoginRequest;
import com.medicloud.modules.auth.dto.RegisterRequest;
import com.medicloud.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User login and registration — public endpoints (no JWT required)")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	@SecurityRequirements // removes global JWT requirement for this endpoint
	@Operation(
			summary = "User Login",
			description = "Authenticate a user with email and password. Returns a JWT token on success."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful — JWT token returned"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid email or password"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error")
	})
	public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
		String token = authService.login(request);
		return ApiResponse.success(token, "Login success");
	}

	@PostMapping("/register")
	@SecurityRequirements
	@Operation(
			summary = "Register User",
			description = "Register a new user (OWNER role) for an existing shop."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered successfully"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error or email already exists")
	})
	public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(null, "User registered successfully"));
	}
}
