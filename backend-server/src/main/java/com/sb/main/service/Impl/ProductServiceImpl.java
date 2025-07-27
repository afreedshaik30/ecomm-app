package com.sb.main.service.Impl;

import com.sb.main.repository.ProductRepository;
import com.sb.main.dto.ProductDTO;
import com.sb.main.exception.ProductException;
import com.sb.main.model.Product;
import com.sb.main.service.Interface.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(@Valid ProductDTO product) throws ProductException {
        if (product == null)
            throw new ProductException("Product Can not be Null");
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductDTO newProduct) throws ProductException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductException("Product with ID " + productId + " not found.");
        }
        Product existingProduct = product.get();

        // Update the existing product's properties with the new data
        System.out.println("before");
        existingProduct.setName(newProduct.getName());
        existingProduct.setImageUrl(newProduct.getImageUrl());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setDescription(newProduct.getDescription());
        System.out.println("after");
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public List<Product> getProductByName(String name) throws ProductException {
        List<Product> existProductByName = productRepository.findByName(name);
        if (existProductByName.isEmpty()) {
            throw new ProductException("Product Not found with name " + name);
        }
        return existProductByName;
    }

    @Override
    public List<Product> getAllProduct(String keyword, String sortDirection, String sortBy) throws ProductException {

        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,sortBy);

        List<Product> products;

        if (keyword != null) {

            products = productRepository.findAllByNameContainingIgnoreCase(keyword, sort);
        } else {
            products = productRepository.findAll(sort);
        }
        if (products.isEmpty()) {
            throw new ProductException("Product List Empty");
        }

        return products;
    }

    @Override
    public List<Product> getProductByCategory(String category) throws ProductException {
        List<Product> products = productRepository.findByCategoryName(category);
        if (products.isEmpty()) {
            throw new ProductException("No products found in category: " + category);
        }
        return products;
    }


    @Override
    public void removeProduct(Integer productId) throws ProductException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found."));

        productRepository.delete(existingProduct);
    }

    @Override
    public Product getSingleProduct(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
    }
    @Override
    public Page<Product> getAllProductsPaginated(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }
}
