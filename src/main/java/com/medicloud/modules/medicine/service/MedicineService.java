package com.medicloud.modules.medicine.service;

import com.medicloud.modules.medicine.dto.MedicineRequest;
import com.medicloud.modules.medicine.dto.MedicineResponse;

import java.util.List;

public interface MedicineService {

	MedicineResponse addMedicine(MedicineRequest request);

	List<MedicineResponse> getAllMedicines();
}