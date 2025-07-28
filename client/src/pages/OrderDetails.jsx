import React, { useEffect, useState } from "react";
import api from "../api/api";

const OrderDetails = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  // Load all user orders on mount
  useEffect(() => {
    api
      .get("/orders")
      .then((res) => {
        const sorted = res.data.sort(
          (a, b) => new Date(b.orderDate) - new Date(a.orderDate)
        );
        setOrders(sorted);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Orders load failed", err);
        alert("Could not load orders");
        setLoading(false);
      });
  }, []);

  const cancelOrder = (orderId) => {
    api
      .delete(`/orders/${orderId}`)
      .then(() => {
        alert("Order cancelled");
        setOrders((prev) => prev.filter((o) => o.orderId !== orderId));
      })
      .catch((err) => alert("Cancel failed"));
  };

  return (
    <div className="container">
      <h2>Your Orders</h2>

      {loading ? (
        <p>Loading orders...</p>
      ) : orders.length === 0 ? (
        <p>You have no orders.</p>
      ) : (
        orders.map((order, index) => (
          <div className="order-card" key={order.orderId}>
            <h4>Order #{index + 1}</h4>
            <p>
              <strong>Order ID:</strong> {order.orderId}
            </p>
            <p>
              <strong>Status:</strong> {order.status}
            </p>
            <p>
              <strong>Date:</strong>{" "}
              {new Date(order.orderDate).toLocaleString()}
            </p>
            <p>
              <strong>Total Amount:</strong> ₹{order.totalAmount}
            </p>

            <div>
              <h5>Items:</h5>
              <ul>
                {order.orderItem.map((item) => (
                  <li key={item.orderItemId}>
                    {item.product.name} x {item.quantity}
                  </li>
                ))}
              </ul>
            </div>

            {order.status !== "SHIPPED" && (
              <div style={{ marginTop: "10px" }}>
                <button onClick={() => cancelOrder(order.orderId)}>
                  Cancel Order
                </button>
              </div>
            )}
          </div>
        ))
      )}
    </div>
  );
};

export default OrderDetails;
