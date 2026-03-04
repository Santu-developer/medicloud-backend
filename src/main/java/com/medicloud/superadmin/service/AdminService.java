package com.medicloud.superadmin.service;

import com.medicloud.modules.shop.entity.Shop;
import com.medicloud.modules.subscription.entity.Subscription;

import java.util.List;

public interface AdminService {
	String login(String email,String password);

	List<Shop> getAllShops();

	List<Subscription> getAllSubscriptions();
}

