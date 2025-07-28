package com.sb.main.service.Impl;

import com.sb.main.enums.OrderStatus;
import com.sb.main.enums.PaymentMethod;
import com.sb.main.enums.PaymentStatus;
import com.sb.main.exception.PaymentException;
import com.sb.main.exception.UserException;
import com.sb.main.model.Order;
import com.sb.main.model.Payment;
import com.sb.main.model.User;
import com.sb.main.repository.OrderRepository;
import com.sb.main.repository.PaymentRepository;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Payment makePayment(Integer orderId, Integer userId) {
        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found in the database."));

        // Fetch order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PaymentException("Order not found in the database."));

        // Create and populate Payment
        Payment payment = new Payment();
        payment.setPaymentAmount(order.getTotalAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(PaymentMethod.UPI); // Replace with actual method in real app
        payment.setPaymentStatus(PaymentStatus.SUCCESSFUL); // Simulated success
        payment.setUser(user);
        payment.setOrder(order);

        // Save payment
        paymentRepository.save(payment);

        // Update order with payment
        order.setPayment(payment);
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        // Link payment to user
        user.getPayments().add(payment);
        userRepository.save(user);

        return payment;
    }
}
