package com.sb.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotBlank(message = "Product name is Mandatory")
    private String name;

    @NotBlank(message = "Product Image is Mandatory")
    private String imageUrl;

    private boolean isAvailable=true;

    @NotBlank(message = "Product description is Mandatory")
    private String description;

    @NotBlank(message = "Product Price is Mandatory")
    private Double price;

    @NotBlank(message = "Product Category is Mandatory")
    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<OrderItem> orderItem= new ArrayList<>();

//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
//    private List<Review> reviews= new ArrayList<>();
}
