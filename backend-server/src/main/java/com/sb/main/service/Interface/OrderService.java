package com.sb.main.service.Interface;

import com.sb.main.dto.OrderDTO;
import com.sb.main.exception.OrderException;
import com.sb.main.model.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Integer userId) throws OrderException;
    Order updateOrders(Integer orderId, OrderDTO orderDTO) throws OrderException;
    Order getOrdersDetails(Integer orderId) throws OrderException;
    List<Order> getAllUserOrder(Integer userId) throws OrderException;
    List<Order> viewAllOrders() throws OrderException;
    List<Order> viewAllOrderByDate(Date date) throws OrderException;
    void deleteOrders(Integer userId, Integer orderId) throws OrderException;
}