package com.sb.main.controller;

import com.sb.main.model.Cart;
import com.sb.main.service.Interface.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bmart/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/add-product")
    public ResponseEntity<Cart> addProductToCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        Cart cart = cartService.addProductToCart(userId, productId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/products/{cartId}")
    public ResponseEntity<Cart> getAllCartProducts(@PathVariable Integer cartId) {
        Cart products = cartService.getAllCartProduct(cartId);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/increase-products/{cartId}/{productId}")
    public ResponseEntity<Cart> increaseProductQuantity(
            @PathVariable Integer cartId,
            @PathVariable Integer productId

    ) {
        Cart cart = cartService.increaseProductQuantity(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/decrease-products/{cartId}/{productId}")
    public ResponseEntity<Cart> decreaseProductQuantity(
            @PathVariable Integer cartId,
            @PathVariable Integer productId

    ) {
        Cart cart = cartService.decreaseProductQuantity(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/remove-product/{cartId}/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Integer cartId, @PathVariable Integer productId) {
        cartService.removeProductFromCart(cartId, productId);
        String msg = "Product is removed from cart";
        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/empty-cart/{cartId}")
    public ResponseEntity<String> removeAllProductFromCart(@PathVariable Integer cartId) {
        cartService.removeAllProductFromCart(cartId);
        String msg = "All product are Removed From cart";
        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }
}
