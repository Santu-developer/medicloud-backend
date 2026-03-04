package com.medicloud.modules.subscription.controller;

import com.medicloud.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription", description = "Subscription management and health check")
public class SubscriptionController {

	@GetMapping("/health")
	@SecurityRequirements
	@Operation(
			summary = "Health Check",
			description = "Public health-check endpoint for the subscription module."
	)
	public ApiResponse<String> health() {
		return ApiResponse.success(null, "subscription module ok");
	}
}

