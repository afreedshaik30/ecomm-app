package com.sb.main.repository;

import com.sb.main.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Search products by name (case-insensitive, partial match)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :product, '%'))")
    List<Product> findByName(@Param("product") String name);

    // Search products by category name (case-insensitive, partial match)
    @Query("SELECT p FROM Product p WHERE LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Product> findByCategoryName(@Param("category") String category);

    // Dynamic query method - filter by name containing keyword, sorted
    List<Product> findAllByNameContainingIgnoreCase(String keyword, Sort sort);

    // Pagination
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
