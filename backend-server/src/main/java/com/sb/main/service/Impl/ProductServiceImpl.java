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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(@Valid ProductDTO productDto) {
        if (productDto == null) {
            throw new ProductException("Product cannot be null");
        }

        Product product = convertToEntity(productDto);
        product.setAvailable(true); // Set default availability

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductDTO newProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found."));

        // Update product fields
        existingProduct.setName(newProduct.getName());
        existingProduct.setImageUrl(newProduct.getImageUrl());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setDescription(newProduct.getDescription());

        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductException("Product not found with name: " + name);
        }
        return products;
    }

    @Override
    public List<Product> getAllProduct(String keyword, String sortDirection, String sortBy) {
        Sort sort = Sort.by(
                sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortBy
        );

        List<Product> products = (keyword != null)
                ? productRepository.findAllByNameContainingIgnoreCase(keyword, sort)
                : productRepository.findAll(sort);

        if (products.isEmpty()) {
            throw new ProductException("Product list is empty.");
        }

        return products;
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        if (products.isEmpty()) {
            throw new ProductException("No products found in category: " + category);
        }
        return products;
    }

    @Override
    public void removeProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found."));

        productRepository.delete(product);
    }

    @Override
    public Product getSingleProduct(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found."));
    }

    @Override
    public Page<Product> getAllProductsPaginated(String keyword, Pageable pageable) {
        return (keyword != null && !keyword.isBlank())
                ? productRepository.findByNameContainingIgnoreCase(keyword, pageable)
                : productRepository.findAll(pageable);
    }

    // ====================
    // Utility Method
    // ====================
    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        return product;
    }
}
