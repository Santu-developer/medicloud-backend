package com.medicloud.modules.shop.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.shop.dto.ShopRequest;
import com.medicloud.modules.shop.dto.ShopResponse;
import com.medicloud.modules.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
@Tag(name = "Shop Management", description = "Create and retrieve medical shops")
public class ShopController {

	private final ShopService shopService;

	public ShopController(ShopService shopService) {
		this.shopService = shopService;
	}

	@PostMapping
	@SecurityRequirements // POST /shops is public (onboarding)
	@Operation(
			summary = "Create Shop",
			description = "Register a new medical shop. This is a public endpoint used during onboarding."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Shop created"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error")
	})
	public ResponseEntity<ApiResponse<ShopResponse>> createShop(@Valid @RequestBody ShopRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(shopService.createShop(request), "Shop created successfully"));
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get Shop by ID",
			description = "Retrieve shop details by ID. Requires authentication."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Shop fetched"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Shop not found")
	})
	public ApiResponse<ShopResponse> getShop(
			@Parameter(description = "Shop ID", required = true, example = "1")
			@PathVariable Long id) {
		return ApiResponse.success(shopService.getShop(id), "Shop fetched");
	}
}