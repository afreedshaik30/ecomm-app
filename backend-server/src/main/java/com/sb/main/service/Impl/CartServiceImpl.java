package com.sb.main.service.Impl;

import com.sb.main.exception.CartException;
import com.sb.main.exception.ProductException;
import com.sb.main.exception.UserException;
import com.sb.main.model.Cart;
import com.sb.main.model.CartItem;
import com.sb.main.model.Product;
import com.sb.main.model.User;
import com.sb.main.repository.CartItemRepository;
import com.sb.main.repository.CartRepository;
import com.sb.main.repository.ProductRepository;
import com.sb.main.repository.UserRepository;
import com.sb.main.service.Interface.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart addProductToCart(Integer userId, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not available"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            throw new CartException("Product already in cart. Increase quantity.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);

        cart.setTotalAmount(calculateTotal(cart.getCartItems()));
        userRepository.save(user);

        return user.getCart();
    }

    @Override
    public Cart increaseProductQuantity(Integer userId, Integer productId) {
        Cart cart = getUserCart(userId);
        CartItem item = findCartItem(cart, productId);
        item.setQuantity(item.getQuantity() + 1);
        cart.setTotalAmount(calculateTotal(cart.getCartItems()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart decreaseProductQuantity(Integer userId, Integer productId) {
        Cart cart = getUserCart(userId);
        CartItem item = findCartItem(cart, productId);

        if (item.getQuantity() == 1) {
            throw new CartException("Minimum quantity reached");
        }
        item.setQuantity(item.getQuantity() - 1);
        cart.setTotalAmount(calculateTotal(cart.getCartItems()));
        return cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Integer cartId, Integer productId) {
        cartItemRepository.removeProductFromCart(cartId, productId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));
        cart.setTotalAmount(calculateTotal(cart.getCartItems()));
        cartRepository.save(cart);
    }

    @Override
    public void removeAllProductFromCart(Integer cartId) {
        cartItemRepository.removeAllProductFromCart(cartId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));
        cart.setTotalAmount(0.0);
        cartRepository.save(cart);
    }

    @Override
    public Cart getAllCartProduct(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));
        if (cart.getCartItems().isEmpty()) {
            throw new CartException("Cart is empty");
        }
        return cart;
    }

    // ================= Utility Methods =================

    private Cart getUserCart(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            throw new CartException("Cart not found");
        }
        return cart;
    }

    private CartItem findCartItem(Cart cart, Integer productId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartException("Product not found in cart"));
    }

    private double calculateTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}