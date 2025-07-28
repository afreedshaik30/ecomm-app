package com.sb.main.controller;

import com.sb.main.model.Cart;
import com.sb.main.model.User;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bmart/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Integer productId) {
        User user = getCurrentUser();
        Cart cart = cartService.addProductToCart(user.getUserId(), productId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Cart> getAllCartProducts() {
        User user = getCurrentUser();
        Cart cart = cartService.getAllCartProduct(user.getCart().getCartId());
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/increase/{productId}")
    public ResponseEntity<Cart> increaseProductQuantity(@PathVariable Integer productId) {
        User user = getCurrentUser();
        Cart cart = cartService.increaseProductQuantity(user.getCart().getCartId(), productId);
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/decrease/{productId}")
    public ResponseEntity<Cart> decreaseProductQuantity(@PathVariable Integer productId) {
        User user = getCurrentUser();
        Cart cart = cartService.decreaseProductQuantity(user.getCart().getCartId(), productId);
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Integer productId) {
        User user = getCurrentUser();
        cartService.removeProductFromCart(user.getCart().getCartId(), productId);
        return ResponseEntity.ok("Product is removed from cart");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/empty")
    public ResponseEntity<String> removeAllProductFromCart() {
        User user = getCurrentUser();
        cartService.removeAllProductFromCart(user.getCart().getCartId());
        return ResponseEntity.ok("All products are removed from cart");
    }
}
