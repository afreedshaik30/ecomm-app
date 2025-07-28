import React, { useEffect, useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";

const Cart = () => {
  const navigate = useNavigate();
  const [cart, setCart] = useState({ cartItems: [], totalAmount: 0 });

  // Load cart on mount
  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = () => {
    api
      .get("/cart")
      .then((res) => setCart(res.data))
      .catch((err) => {
        console.error("Cart load failed", err);
        alert("Could not load cart");
      });
  };

  const increaseQty = (productId) => {
    api
      .put(`/cart/increase/${productId}`)
      .then(() => fetchCart())
      .catch((err) => console.error("Increase failed", err));
  };

  const decreaseQty = (productId) => {
    api
      .put(`/cart/decrease/${productId}`)
      .then(() => fetchCart())
      .catch((err) => alert("Minimum quantity reached"));
  };

  const removeProduct = (productId) => {
    api
      .delete(`/cart/remove/${productId}`)
      .then(() => fetchCart())
      .catch((err) => alert("Remove failed"));
  };

  const emptyCart = () => {
    api
      .delete("/cart/empty")
      .then(() => fetchCart())
      .catch((err) => alert("Cart is already empty"));
  };

  const placeOrder = () => {
    api
      .post("/orders/place")
      .then(() => {
        alert("Order placed!");
        fetchCart();
        navigate("/user/order-details");
      })
      .catch((err) => {
        console.error("Order failed", err);
        alert("Order could not be placed");
      });
  };

  return (
    <div className="container">
      <h2>Your Cart</h2>
      {cart.cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <>
          <div className="cart-grid">
            {cart.cartItems.map((item) => (
              <div className="cart-item" key={item.cartItemId}>
                <img src={item.product.imageUrl} alt={item.product.name} />
                <div>
                  <h4>{item.product.name}</h4>
                  <p>
                    ₹ {item.product.price} x {item.quantity}
                  </p>
                  <div>
                    <button onClick={() => decreaseQty(item.product.productId)}>
                      -
                    </button>
                    <span>{item.quantity}</span>
                    <button onClick={() => increaseQty(item.product.productId)}>
                      +
                    </button>
                  </div>
                  <button onClick={() => removeProduct(item.product.productId)}>
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="cart-summary">
            <h3>Total: ₹ {cart.totalAmount}</h3>
            <button onClick={placeOrder}>Place Order</button>
            <button onClick={emptyCart} style={{ backgroundColor: "red" }}>
              Empty Cart
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
