package com.medicloud.modules.supplier.mapper;

import com.medicloud.modules.supplier.dto.SupplierResponse;
import com.medicloud.modules.supplier.entity.Supplier;

public class SupplierMapper {

    public static SupplierResponse toResponse(Supplier s){
        return new SupplierResponse(
                s.getId(),
                s.getName(),
                s.getPhone(),
                s.getEmail(),
                s.getAddress()
        );
    }
}