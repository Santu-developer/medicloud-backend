package com.medicloud.modules.dashboard.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.dashboard.dto.DashboardResponse;
import com.medicloud.modules.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Shop dashboard with today's sale, total sale, profit & low-stock count")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping
	@Operation(
			summary = "Get Dashboard",
			description = "Retrieve dashboard metrics for the authenticated user's shop: today's sale, total sale, estimated profit, and low-stock count."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dashboard data retrieved")
	})
	public ApiResponse<DashboardResponse> dashboard() {
		return ApiResponse.success(dashboardService.getDashboard(), "Dashboard data");
	}
}