package com.medicloud.modules.subscription.repository;

import com.medicloud.modules.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long>{

    Optional<Subscription> findByShopId(Long shopId);
}