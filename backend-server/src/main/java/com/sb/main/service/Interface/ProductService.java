package com.sb.main.service.Interface;

import com.sb.main.dto.ProductDTO;
import com.sb.main.exception.ProductException;
import com.sb.main.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product)throws ProductException;
    Product updateProduct(Integer productId, ProductDTO product)throws ProductException;
    List<Product> getProductByName(String name)throws ProductException;
    List<Product> getAllProduct(String keyword, String sortDirection, String sortBy)throws ProductException;
    List<Product> getProductByCategory(String category) throws ProductException;
    void removeProduct(Integer productId)throws ProductException;
    Product getSingleProduct(Integer productId);
}
