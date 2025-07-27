package com.sb.main.controller;

import com.sb.main.model.Payment;
import com.sb.main.service.Interface.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bmart/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/pay")
    public ResponseEntity<Payment> makePayment(@RequestParam Integer orderId, @RequestParam Integer userId
    ) {
        Payment payment = paymentService.makePayment(orderId, userId);
        return ResponseEntity.ok(payment);
    }
}
