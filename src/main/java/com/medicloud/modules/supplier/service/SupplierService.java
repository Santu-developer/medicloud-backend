package com.medicloud.modules.supplier.service;

import com.medicloud.modules.supplier.dto.SupplierRequest;
import com.medicloud.modules.supplier.dto.SupplierResponse;

import java.util.List;

public interface SupplierService {

	SupplierResponse addSupplier(SupplierRequest request);

	List<SupplierResponse> getAllSuppliers();

	void deleteSupplier(Long id);
}