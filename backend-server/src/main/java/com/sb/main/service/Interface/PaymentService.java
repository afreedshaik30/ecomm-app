package com.sb.main.service.Interface;

import com.sb.main.exception.PaymentException;
import com.sb.main.model.Payment;

public interface PaymentService {
    Payment makePayment(Integer orderId, Integer userId) throws PaymentException;
}
