package com.medicloud.modules.supplier.repository;

import com.medicloud.modules.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    List<Supplier> findByShopId(Long shopId);
}