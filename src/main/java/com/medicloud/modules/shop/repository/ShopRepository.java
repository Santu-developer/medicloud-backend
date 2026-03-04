package com.medicloud.modules.shop.repository;

import com.medicloud.modules.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}