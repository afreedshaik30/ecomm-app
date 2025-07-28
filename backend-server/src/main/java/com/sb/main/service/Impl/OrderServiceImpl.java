package com.sb.main.service.Impl;

import com.sb.main.dto.OrderDTO;
import com.sb.main.enums.OrderStatus;
import com.sb.main.exception.OrderException;
import com.sb.main.exception.UserException;
import com.sb.main.model.*;
import com.sb.main.repository.*;
import com.sb.main.service.Interface.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public OrderDTO placeOrder(Integer userId) throws OrderException {
        User existingUser = getUserById(userId);

        Cart userCart = existingUser.getCart();
        if (userCart == null || userCart.getCartItems().isEmpty()) {
            throw new OrderException("Add items to the cart first.");
        }

        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setUser(existingUser);
        newOrder.setTotalAmount(userCart.getTotalAmount());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : userCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }

        newOrder.setOrderItem(orderItems);
        orderRepository.save(newOrder);

        cartItemRepository.removeAllProductFromCart(userCart.getCartId());
        userCart.setTotalAmount(0.0);
        cartRepository.save(userCart);

        return convertToOrderDTO(newOrder);
    }

    @Override
    public Order updateOrders(Integer orderId, OrderDTO orderDTO) {
        throw new UnsupportedOperationException("Update Order not implemented yet.");
    }

    @Override
    public Order getOrdersDetails(Integer orderId) {
        Order order = getOrderById(orderId);
        User currentUser = getCurrentUser();

        if (isAdmin(currentUser) || order.getUser().getUserId().equals(currentUser.getUserId())) {
            return order;
        }

        throw new AccessDeniedException("You are not authorized to access this order.");
    }

    @Override
    public List<Order> getAllUserOrder(Integer userId) {
        User currentUser = getCurrentUser();

        if (!currentUser.getUserId().equals(userId) && !isAdmin(currentUser)) {
            throw new AccessDeniedException("You are not authorized to access this user's orders.");
        }

        return orderRepository.findAllByUser_UserId(userId);
    }

    @Override
    public List<Order> viewAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new OrderException("No orders found in the database.");
        }
        return orders;
    }

    @Override
    public List<Order> viewAllOrderByDate(Date date) {
        List<Order> orders = orderRepository.findByOrderDateGreaterThanEqual(date);
        if (orders.isEmpty()) {
            throw new OrderException("No orders found for the given date.");
        }
        return orders;
    }

    @Override
    public void deleteOrders(Integer userId, Integer orderId) {
        Order order = getOrderById(orderId);
        User currentUser = getCurrentUser();

        if (!order.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You cannot delete another user's order.");
        }

        orderRepository.delete(order);
    }

    // ====================
    // Utility Methods
    // ====================

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Logged-in user not found."));
    }

    private boolean isAdmin(User user) {
        return user.getRole().name().equals("ROLE_ADMIN");
    }

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));
    }

    private Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setPaymentStatus("PENDING");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setOrderDate(order.getOrderDate().format(formatter));
        return dto;
    }
}
