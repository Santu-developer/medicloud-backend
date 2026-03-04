package com.medicloud.modules.medicine.service.impl;

import com.medicloud.common.util.TenantUtil;
import com.medicloud.modules.medicine.dto.MedicineRequest;
import com.medicloud.modules.medicine.dto.MedicineResponse;
import com.medicloud.modules.medicine.entity.Medicine;
import com.medicloud.modules.medicine.mapper.MedicineMapper;
import com.medicloud.modules.medicine.repository.MedicineRepository;
import com.medicloud.modules.medicine.service.MedicineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final TenantUtil tenantUtil;

    public MedicineServiceImpl(MedicineRepository medicineRepository, TenantUtil tenantUtil) {
        this.medicineRepository = medicineRepository;
        this.tenantUtil = tenantUtil;
    }

    @Override
    @Transactional
    public MedicineResponse addMedicine(MedicineRequest request) {

        Long shopId = tenantUtil.getCurrentShopId();

        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setCompany(request.getCompany());
        medicine.setBatchNo(request.getBatchNo());
        medicine.setQuantity(request.getQuantity());
        medicine.setPurchasePrice(request.getPurchasePrice());
        medicine.setSellingPrice(request.getSellingPrice());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setShopId(shopId);

        medicineRepository.save(medicine);

        return MedicineMapper.toResponse(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineResponse> getAllMedicines() {

        Long shopId = tenantUtil.getCurrentShopId();

        return medicineRepository.findByShopId(shopId)
                .stream()
                .map(MedicineMapper::toResponse)
                .collect(Collectors.toList());
    }
}