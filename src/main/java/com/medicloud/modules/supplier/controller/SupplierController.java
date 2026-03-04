package com.medicloud.modules.supplier.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.supplier.dto.SupplierRequest;
import com.medicloud.modules.supplier.dto.SupplierResponse;
import com.medicloud.modules.supplier.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@Tag(name = "Supplier Management", description = "CRUD operations for suppliers (tenant-scoped, DELETE requires OWNER role)")
public class SupplierController {

	private final SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@PostMapping
	@Operation(
			summary = "Add Supplier",
			description = "Add a new supplier to the current shop."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Supplier added"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error")
	})
	public ResponseEntity<ApiResponse<SupplierResponse>> add(@Valid @RequestBody SupplierRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(supplierService.addSupplier(request), "Supplier added"));
	}

	@GetMapping
	@Operation(
			summary = "List Suppliers",
			description = "Retrieve all suppliers for the authenticated user's shop."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Supplier list retrieved")
	})
	public ApiResponse<List<SupplierResponse>> list() {
		return ApiResponse.success(supplierService.getAllSuppliers(), "Supplier list");
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete Supplier",
			description = "Delete a supplier by ID. Requires OWNER authority. Enforces tenant isolation."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Supplier deleted"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Supplier not found"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied — OWNER only")
	})
	public ApiResponse<String> delete(
			@Parameter(description = "Supplier ID", required = true, example = "1")
			@PathVariable Long id) {
		supplierService.deleteSupplier(id);
		return ApiResponse.success("Deleted", "Supplier removed");
	}
}