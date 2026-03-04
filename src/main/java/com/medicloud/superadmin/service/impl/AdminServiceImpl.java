package com.medicloud.superadmin.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.modules.shop.entity.Shop;
import com.medicloud.modules.shop.repository.ShopRepository;
import com.medicloud.modules.subscription.entity.Subscription;
import com.medicloud.modules.subscription.repository.SubscriptionRepository;
import com.medicloud.security.jwt.JwtService;
import com.medicloud.superadmin.repository.AdminRepository;
import com.medicloud.superadmin.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final AdminRepository adminRepo;
	private final ShopRepository shopRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AdminServiceImpl(AdminRepository adminRepo, ShopRepository shopRepository,
							SubscriptionRepository subscriptionRepository,
							AuthenticationManager authenticationManager,
							JwtService jwtService) {
		this.adminRepo = adminRepo;
		this.shopRepository = shopRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public String login(String email, String password) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
		);

		// Authentication succeeded — generate JWT
		log.info("Admin logged in: {}", email);
		return jwtService.generateToken(email, null, "SUPER_ADMIN");
	}

	@Override
	public List<Shop> getAllShops() {
		return shopRepository.findAll();
	}

	@Override
	public List<Subscription> getAllSubscriptions() {
		return subscriptionRepository.findAll();
	}
}