package com.sb.main.controller;

import com.sb.main.dto.ProductDTO;
import com.sb.main.model.Product;
import com.sb.main.service.Interface.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmart/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/product-by-name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "price") String sortBy
    ) {
        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return new ResponseEntity<>(productService.getAllProductsPaginated(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.getProductByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Integer productId) {
        return new ResponseEntity<>(productService.getSingleProduct(productId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductDTO updatedProduct) {
        return new ResponseEntity<>(productService.updateProduct(productId, updatedProduct), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Integer productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully.", HttpStatus.OK);
    }
}
