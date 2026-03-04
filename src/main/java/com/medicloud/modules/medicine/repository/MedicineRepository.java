package com.medicloud.modules.medicine.repository;

import com.medicloud.modules.medicine.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByShopId(Long shopId);

    Page<Medicine> findByShopId(Long shopId, Pageable pageable);

    Optional<Medicine> findByIdAndShopId(Long id, Long shopId);

    @Query("SELECT COUNT(m) FROM Medicine m WHERE m.shopId = :shopId AND m.quantity < 10")
    Long getLowStockCount(@Param("shopId") Long shopId);
}