package com.medicloud.modules.shop.mapper;

import com.medicloud.modules.shop.dto.ShopResponse;
import com.medicloud.modules.shop.entity.Shop;

public class ShopMapper {

    public static ShopResponse toResponse(Shop shop){
        return new ShopResponse(
                shop.getId(),
                shop.getName(),
                shop.getEmail(),
                shop.getPhone(),
                shop.getAddress()
        );
    }
}