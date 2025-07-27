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
    public Payment makePayment(Integer orderId, Integer userId) throws PaymentException {

            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException("User not found in the database."));

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new PaymentException("Order not found in the database."));

            Payment payment = new Payment();
            payment.setPaymentAmount(order.getTotalAmount());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMethod(PaymentMethod.UPI); // You may replace with actual selected method
            payment.setPaymentStatus(PaymentStatus.SUCCESSFUL); // Assume payment success here
            payment.setUser(existingUser);
            payment.setOrder(order);

            // Save payment first
            paymentRepository.save(payment);

            // Link payment to order and update status
            order.setPayment(payment);
            order.setStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);

            // Link payment to user
            existingUser.getPayments().add(payment);
            userRepository.save(existingUser);

            return payment;
        }
    }
