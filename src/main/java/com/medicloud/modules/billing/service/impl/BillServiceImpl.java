package com.medicloud.modules.billing.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.common.util.TenantUtil;
import com.medicloud.modules.billing.dto.BillRequest;
import com.medicloud.modules.billing.dto.BillResponse;
import com.medicloud.modules.billing.entity.Bill;
import com.medicloud.modules.billing.entity.BillItem;
import com.medicloud.modules.billing.repository.BillItemRepository;
import com.medicloud.modules.billing.repository.BillRepository;
import com.medicloud.modules.billing.service.BillService;
import com.medicloud.modules.medicine.entity.Medicine;
import com.medicloud.modules.medicine.repository.MedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private static final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final MedicineRepository medicineRepository;
    private final TenantUtil tenantUtil;

    public BillServiceImpl(BillRepository billRepository, BillItemRepository billItemRepository,
                           MedicineRepository medicineRepository, TenantUtil tenantUtil) {
        this.billRepository = billRepository;
        this.billItemRepository = billItemRepository;
        this.medicineRepository = medicineRepository;
        this.tenantUtil = tenantUtil;
    }

    @Override
    @Transactional
    public BillResponse createBill(BillRequest request) {

        Long shopId = tenantUtil.getCurrentShopId();
        BigDecimal total = BigDecimal.ZERO;

        Bill bill = new Bill();
        bill.setShopId(shopId);
        bill.setTotalAmount(BigDecimal.ZERO);
        billRepository.save(bill);

        List<BillItem> billItems = new ArrayList<>();
        List<Medicine> updatedMedicines = new ArrayList<>();

        for (BillRequest.BillItemRequest item : request.getItems()) {

            Medicine medicine = medicineRepository.findByIdAndShopId(item.getMedicineId(), shopId)
                    .orElseThrow(() -> new BusinessException("Medicine not found or does not belong to your shop"));

            if (medicine.getQuantity() < item.getQuantity()) {
                throw new BusinessException("Insufficient stock for " + medicine.getName()
                        + " (available: " + medicine.getQuantity() + ", requested: " + item.getQuantity() + ")");
            }

            medicine.setQuantity(medicine.getQuantity() - item.getQuantity());
            updatedMedicines.add(medicine);

            BigDecimal itemTotal = medicine.getSellingPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);

            BillItem billItem = new BillItem();
            billItem.setBillId(bill.getId());
            billItem.setMedicineId(medicine.getId());
            billItem.setMedicineName(medicine.getName());
            billItem.setQuantity(item.getQuantity());
            billItem.setPrice(medicine.getSellingPrice());
            billItem.setTotal(itemTotal);
            billItem.setShopId(shopId);
            billItems.add(billItem);
        }

        // Batch save all medicines and bill items
        medicineRepository.saveAll(updatedMedicines);
        billItemRepository.saveAll(billItems);

        bill.setTotalAmount(total);
        billRepository.save(bill);

        log.info("Bill created: id={}, shopId={}, total={}", bill.getId(), shopId, total);
        return new BillResponse(bill.getId(), total);
    }
}
