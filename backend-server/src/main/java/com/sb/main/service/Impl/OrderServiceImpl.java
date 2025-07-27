package com.sb.main.service.Impl;

import com.sb.main.dto.OrderDTO;
import com.sb.main.enums.OrderStatus;
import com.sb.main.exception.OrderException;
import com.sb.main.exception.UserException;
import com.sb.main.model.*;
import com.sb.main.repository.*;
import com.sb.main.service.Interface.OrderService;

import lombok.RequiredArgsConstructor;
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
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public OrderDTO placeOrder(Integer userId) throws OrderException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));

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

        OrderDTO orderData = new OrderDTO();
        orderData.setOrderId(newOrder.getOrderId());
        orderData.setOrderAmount(newOrder.getTotalAmount());
        orderData.setStatus(newOrder.getStatus().name());
        orderData.setPaymentStatus("PENDING");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderData.setOrderDate(newOrder.getOrderDate().format(formatter));        return orderData;
    }

    @Override
    public Order updateOrders(Integer orderId, OrderDTO orderDTO) throws OrderException {
        throw new UnsupportedOperationException("Update Order not implemented yet.");
    }

    @Override
    public Order getOrdersDetails(Integer orderId) throws OrderException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found in the database."));
    }

    @Override
    public List<Order> getAllUserOrder(Integer userId) throws OrderException {
        List<Order> orders = orderRepository.getAllOrderByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrderException("No orders found for the user in the database.");
        }
        return orders;
    }

    @Override
    public List<Order> viewAllOrders() throws OrderException {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new OrderException("No orders found in the database.");
        }
        return orders;
    }

    @Override
    public List<Order> viewAllOrderByDate(Date date) throws OrderException {
        List<Order> orders = orderRepository.findByOrderDateGreaterThanEqual(date);
        if (orders.isEmpty()) {
            throw new OrderException("No orders found for the given date.");
        }
        return orders;
    }

    @Override
    public void deleteOrders(Integer userId, Integer orderId) throws OrderException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order Not Found In Database"));

        if (!existingOrder.getUser().getUserId().equals(userId)) {
            throw new OrderException("You are not authorized to delete this order.");
        }

        orderRepository.delete(existingOrder);
    }
}