package com.sb.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product Name is Mandatory")
    private String name;
    @NotBlank(message = "Product Image is Mandatory")
    private String imageUrl;
    @Size(min = 5, max = 30)
    @NotBlank(message = "Product Description is Mandatory")
    private String description;
    @NotBlank(message = "Product Price is Mandatory")
    private Double price;
    @NotBlank(message = "Product Category is Mandatory")
    private String category;

}
