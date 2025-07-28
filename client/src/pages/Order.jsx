import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/api";

const Order = () => {
  const { id } = useParams();
  const [order, setOrder] = useState(null);

  useEffect(() => {
    api
      .get(`/orders/${id}`)
      .then((res) => setOrder(res.data))
      .catch(() => alert("Failed to load order"));
  }, [id]);

  return (
    <div className="container">
      <h2>Order Details</h2>
      {order ? (
        <>
          <p>
            <strong>Order ID:</strong> {order.orderId}
          </p>
          <p>
            <strong>Status:</strong> {order.status}
          </p>
          <p>
            <strong>Total Amount:</strong> ₹{order.totalAmount}
          </p>
          <p>
            <strong>Date:</strong> {new Date(order.orderDate).toLocaleString()}
          </p>

          <h4>Items:</h4>
          <ul>
            {order.orderItem.map((item) => (
              <li key={item.orderItemId}>
                {item.product.name} × {item.quantity} - ₹{item.product.price}
              </li>
            ))}
          </ul>
        </>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Order;
