package com.medicloud.modules.dashboard.service.impl;

import com.medicloud.common.util.TenantUtil;
import com.medicloud.modules.billing.repository.BillRepository;
import com.medicloud.modules.dashboard.dto.DashboardResponse;
import com.medicloud.modules.dashboard.service.DashboardService;
import com.medicloud.modules.medicine.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardServiceImpl implements DashboardService {

	private final BillRepository billRepository;
	private final MedicineRepository medicineRepository;
	private final TenantUtil tenantUtil;

	public DashboardServiceImpl(BillRepository billRepository, MedicineRepository medicineRepository, TenantUtil tenantUtil) {
		this.billRepository = billRepository;
		this.medicineRepository = medicineRepository;
		this.tenantUtil = tenantUtil;
	}

	@Override
	@Transactional(readOnly = true)
	public DashboardResponse getDashboard() {

		Long shopId = tenantUtil.getCurrentShopId();

		BigDecimal totalSale = billRepository.getTotalSale(shopId);
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		BigDecimal todaySale = billRepository.getTodaySale(shopId, startOfDay);
		Long lowStock = medicineRepository.getLowStockCount(shopId);

		// Profit margin estimate (20%) — replace with actual purchase-price-based calculation when available
		BigDecimal profit = totalSale.multiply(BigDecimal.valueOf(0.20)).setScale(2, RoundingMode.HALF_UP);

		return new DashboardResponse(todaySale, totalSale, profit, lowStock);
	}
}