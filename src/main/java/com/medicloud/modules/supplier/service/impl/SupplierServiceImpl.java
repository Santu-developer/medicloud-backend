package com.medicloud.modules.supplier.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.common.exception.ResourceNotFoundException;
import com.medicloud.common.util.TenantUtil;
import com.medicloud.modules.supplier.dto.SupplierRequest;
import com.medicloud.modules.supplier.dto.SupplierResponse;
import com.medicloud.modules.supplier.entity.Supplier;
import com.medicloud.modules.supplier.mapper.SupplierMapper;
import com.medicloud.modules.supplier.repository.SupplierRepository;
import com.medicloud.modules.supplier.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

	private final SupplierRepository supplierRepository;
	private final TenantUtil tenantUtil;

	public SupplierServiceImpl(SupplierRepository supplierRepository, TenantUtil tenantUtil) {
		this.supplierRepository = supplierRepository;
		this.tenantUtil = tenantUtil;
	}

	@Override
	@Transactional
	public SupplierResponse addSupplier(SupplierRequest request) {

		Long shopId = tenantUtil.getCurrentShopId();

		Supplier supplier = new Supplier();
		supplier.setName(request.getName());
		supplier.setPhone(request.getPhone());
		supplier.setEmail(request.getEmail());
		supplier.setAddress(request.getAddress());
		supplier.setShopId(shopId);

		supplierRepository.save(supplier);

		return SupplierMapper.toResponse(supplier);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SupplierResponse> getAllSuppliers() {

		Long shopId = tenantUtil.getCurrentShopId();

		return supplierRepository.findByShopId(shopId)
				.stream()
				.map(SupplierMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteSupplier(Long id) {

		Long shopId = tenantUtil.getCurrentShopId();

		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

		if (!supplier.getShopId().equals(shopId)) {
			throw new BusinessException("Access denied: supplier does not belong to your shop");
		}

		supplierRepository.delete(supplier);
	}
}