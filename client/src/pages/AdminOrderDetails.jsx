import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/api";

const AdminOrderDetails = () => {
  const { id } = useParams();
  const [order, setOrder] = useState(null);

  useEffect(() => {
    api
      .get(`/orders/${id}`)
      .then((res) => setOrder(res.data))
      .catch(() => alert("Order not found"));
  }, [id]);

  return (
    <div className="container">
      <h2>Admin: Order Detail</h2>
      {order ? (
        <>
          <p>
            <strong>Order ID:</strong> {order.orderId}
          </p>
          <p>
            <strong>User ID:</strong> {order.customer.userId}
          </p>
          <p>
            <strong>User Name:</strong> {order.customer.firstName}{" "}
            {order.customer.lastName}
          </p>
          <p>
            <strong>Status:</strong> {order.status}
          </p>
          <p>
            <strong>Date:</strong> {new Date(order.orderDate).toLocaleString()}
          </p>
          <p>
            <strong>Amount:</strong> ₹{order.totalAmount}
          </p>

          <h4>Items:</h4>
          <ul>
            {order.orderItem.map((item) => (
              <li key={item.orderItemId}>
                {item.product.name} × {item.quantity} @ ₹{item.product.price}
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

export default AdminOrderDetails;
