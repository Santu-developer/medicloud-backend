package com.medicloud.modules.billing.repository;

import com.medicloud.modules.billing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.shopId = :shopId")
    BigDecimal getTotalSale(@Param("shopId") Long shopId);

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.shopId = :shopId AND b.createdAt >= :startOfDay")
    BigDecimal getTodaySale(@Param("shopId") Long shopId, @Param("startOfDay") LocalDateTime startOfDay);
}