package com.medicloud.modules.medicine.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.medicine.dto.MedicineRequest;
import com.medicloud.modules.medicine.dto.MedicineResponse;
import com.medicloud.modules.medicine.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
@Tag(name = "Medicine / Inventory", description = "Add and list medicines for the current shop (tenant-scoped)")
public class MedicineController {

	private final MedicineService medicineService;

	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@PostMapping
	@Operation(
			summary = "Add Medicine",
			description = "Add a new medicine to the current shop's inventory."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Medicine added"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error")
	})
	public ResponseEntity<ApiResponse<MedicineResponse>> addMedicine(@Valid @RequestBody MedicineRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(medicineService.addMedicine(request), "Medicine added"));
	}

	@GetMapping
	@Operation(
			summary = "List All Medicines",
			description = "Retrieve all medicines for the authenticated user's shop."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medicine list retrieved")
	})
	public ApiResponse<List<MedicineResponse>> getAll() {
		return ApiResponse.success(medicineService.getAllMedicines(), "Medicine list");
	}
}