package com.medicloud.infrastructure.external.razorpay;

import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    public String createPayment(Double amount){

        // Future: Razorpay API integration
        return "PAYMENT_CREATED_FOR_" + amount;
    }
}