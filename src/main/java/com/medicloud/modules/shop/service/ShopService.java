package com.medicloud.modules.shop.service;

import com.medicloud.modules.shop.dto.ShopRequest;
import com.medicloud.modules.shop.dto.ShopResponse;

public interface ShopService {

	ShopResponse createShop(ShopRequest request);

	ShopResponse getShop(Long id);
}