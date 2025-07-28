package com.sb.main.repository;

import com.sb.main.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId AND o.user.userId = :userId")
    Order findByIdAndCustomerId(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    @Query("SELECT o FROM Order o WHERE o.orderDate >= :date")
    List<Order> findByOrderDateGreaterThanEqual(Date date);

    @Query("SELECT o FROM Order o WHERE  o.user.userId = :userId")
    List<Order> getAllOrderByUserId(@Param("userId") Integer userId);

    List<Order> findAllByUser_UserId(Integer userId);
}
