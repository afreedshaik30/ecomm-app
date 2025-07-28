package com.sb.main.controller;

import com.sb.main.model.Payment;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bmart/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/pay")
    public ResponseEntity<Payment> makePayment(@RequestParam Integer orderId) {
        Payment payment = paymentService.makePayment(orderId, getCurrentUser().getUserId());
        return ResponseEntity.ok(payment);
    }
}
