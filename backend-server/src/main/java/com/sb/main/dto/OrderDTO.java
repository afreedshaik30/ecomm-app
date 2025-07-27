package com.sb.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String status;
    private String orderDate;
    private Double orderAmount;
    private String paymentStatus;
}
