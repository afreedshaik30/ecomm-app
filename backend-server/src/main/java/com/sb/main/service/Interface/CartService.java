package com.sb.main.service.Interface;

import com.sb.main.exception.CartException;
import com.sb.main.model.Cart;

public interface CartService {
    Cart addProductToCart(Integer userId, Integer productId) throws CartException;
    Cart increaseProductQuantity(Integer cartId,Integer productId) throws CartException;
    Cart decreaseProductQuantity(Integer cartId,Integer productId) throws CartException;
    void removeProductFromCart(Integer cartId,Integer productId) throws CartException;
    void removeAllProductFromCart(Integer cartId) throws CartException;
    Cart getAllCartProduct(Integer cartId)throws CartException;
}
