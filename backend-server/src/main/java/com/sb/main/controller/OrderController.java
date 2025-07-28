package com.sb.main.controller;

import com.sb.main.dto.OrderDTO;
import com.sb.main.model.Order;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bmart/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    // Get current logged-in user
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Place an order for the current user
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder() {
        User user = getCurrentUser();
        OrderDTO order = orderService.placeOrder(user.getUserId());
        return ResponseEntity.ok(order);
    }

    /**
     * Get a single order's details (User must own it or be admin)
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Integer orderId) {
        Order order = orderService.getOrdersDetails(orderId);
        return ResponseEntity.ok(order);
    }

    /**
     * Get all orders for the currently logged-in user
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForUser() {
        User user = getCurrentUser();
        List<Order> orders = orderService.getAllUserOrder(user.getUserId());
        return ResponseEntity.ok(orders);
    }

    /**
     * Admin: Get all orders
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.viewAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Admin: Get all orders after a given date
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Order>> getOrdersByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Order> orders = orderService.viewAllOrderByDate(date);
        return ResponseEntity.ok(orders);
    }

    /**
     * Delete an order (only owner can delete)
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
        User user = getCurrentUser();
        orderService.deleteOrders(user.getUserId(), orderId);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
