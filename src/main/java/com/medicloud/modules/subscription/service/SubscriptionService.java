package com.medicloud.modules.subscription.service;

public interface SubscriptionService {

	void activatePlan(Long shopId, Long planId);

	boolean isActive(Long shopId);
}