package com.medicloud.modules.shop.service.impl;

import com.medicloud.common.exception.ResourceNotFoundException;
import com.medicloud.modules.shop.dto.ShopRequest;
import com.medicloud.modules.shop.dto.ShopResponse;
import com.medicloud.modules.shop.entity.Shop;
import com.medicloud.modules.shop.mapper.ShopMapper;
import com.medicloud.modules.shop.repository.ShopRepository;
import com.medicloud.modules.shop.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl implements ShopService {

	private final ShopRepository shopRepository;

	public ShopServiceImpl(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	@Override
	@Transactional
	public ShopResponse createShop(ShopRequest request) {

		Shop shop = new Shop();
		shop.setName(request.getName());
		shop.setEmail(request.getEmail());
		shop.setPhone(request.getPhone());
		shop.setAddress(request.getAddress());

		shopRepository.save(shop);

		return ShopMapper.toResponse(shop);
	}

	@Override
	@Transactional(readOnly = true)
	public ShopResponse getShop(Long id) {

		Shop shop = shopRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shop not found"));

		return ShopMapper.toResponse(shop);
	}
}