package com.medicloud.modules.billing.repository;

import com.medicloud.modules.billing.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}