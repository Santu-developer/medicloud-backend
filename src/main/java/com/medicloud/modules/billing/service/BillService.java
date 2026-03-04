package com.medicloud.modules.billing.service;

import com.medicloud.modules.billing.dto.BillRequest;
import com.medicloud.modules.billing.dto.BillResponse;

public interface BillService {

    BillResponse createBill(BillRequest request);
}

