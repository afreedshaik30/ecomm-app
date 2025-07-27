package com.sb.main.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    private Integer orderId;
    private Product product;
    private Integer quantity;
}
