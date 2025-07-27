package com.sb.main.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}


//@Data
//@Entity
//@Table(name = "order_item")
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer orderItemId;
//
//    private Integer orderId;
//    private Product product;
//    private Integer quantity;
//}
