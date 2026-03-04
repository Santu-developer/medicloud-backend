package com.medicloud.modules.medicine.mapper;

import com.medicloud.modules.medicine.dto.MedicineResponse;
import com.medicloud.modules.medicine.entity.Medicine;

public class MedicineMapper {

    public static MedicineResponse toResponse(Medicine medicine){
        return new MedicineResponse(
                medicine.getId(),
                medicine.getName(),
                medicine.getCompany(),
                medicine.getBatchNo(),
                medicine.getQuantity(),
                medicine.getSellingPrice()
        );
    }
}