package com.medicloud.modules.billing.controller;

import com.medicloud.common.response.ApiResponse;
import com.medicloud.modules.billing.dto.BillRequest;
import com.medicloud.modules.billing.dto.BillResponse;
import com.medicloud.modules.billing.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
@Tag(name = "Billing", description = "Create bills — deducts stock and calculates total (tenant-scoped)")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create Bill",
            description = "Create a new bill with medicine items. Automatically deducts stock and calculates total amount."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Bill created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error, medicine not found, or insufficient stock")
    })
    public ResponseEntity<ApiResponse<BillResponse>> createBill(@Valid @RequestBody BillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(billService.createBill(request), "Bill created"));
    }
}
