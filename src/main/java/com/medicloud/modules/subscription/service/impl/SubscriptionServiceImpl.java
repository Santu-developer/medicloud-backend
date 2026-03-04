package com.medicloud.modules.subscription.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.common.exception.ResourceNotFoundException;
import com.medicloud.modules.subscription.entity.Plan;
import com.medicloud.modules.subscription.entity.Subscription;
import com.medicloud.modules.subscription.repository.PlanRepository;
import com.medicloud.modules.subscription.repository.SubscriptionRepository;
import com.medicloud.modules.subscription.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	private final SubscriptionRepository subscriptionRepository;
	private final PlanRepository planRepository;

	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.planRepository = planRepository;
	}

	@Override
	@Transactional
	public void activatePlan(Long shopId, Long planId) {

		Plan plan = planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

		Optional<Subscription> existing = subscriptionRepository.findByShopId(shopId);

		if (existing.isPresent()) {
			// Renew existing subscription
			Subscription sub = existing.get();
			LocalDateTime startFrom = sub.getEndDate().isAfter(LocalDateTime.now())
					? sub.getEndDate()
					: LocalDateTime.now();
			sub.setPlanId(planId);
			sub.setStartDate(startFrom);
			sub.setEndDate(startFrom.plusDays(plan.getDurationDays()));
			sub.setStatus("ACTIVE");
			subscriptionRepository.save(sub);
			log.info("Subscription renewed for shopId={}, planId={}", shopId, planId);
		} else {
			Subscription sub = new Subscription();
			sub.setShopId(shopId);
			sub.setPlanId(planId);
			sub.setStartDate(LocalDateTime.now());
			sub.setEndDate(LocalDateTime.now().plusDays(plan.getDurationDays()));
			sub.setStatus("ACTIVE");
			subscriptionRepository.save(sub);
			log.info("Subscription activated for shopId={}, planId={}", shopId, planId);
		}
	}

	@Override
	@Transactional
	public boolean isActive(Long shopId) {

		var sub = subscriptionRepository.findByShopId(shopId).orElse(null);

		if (sub == null) return false;

		if (sub.getEndDate().isBefore(LocalDateTime.now())) {
			sub.setStatus("EXPIRED");
			subscriptionRepository.save(sub);
			return false;
		}

		return "ACTIVE".equals(sub.getStatus());
	}
}