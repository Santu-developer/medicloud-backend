package com.medicloud.modules.subscription.repository;

import com.medicloud.modules.subscription.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}