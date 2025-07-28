import React, { useEffect, useState } from "react";
import api from "../api/api";

const Admin = () => {
  const [users, setUsers] = useState([]);
  const [orders, setOrders] = useState([]);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchAllData();
  }, []);

  const fetchAllData = async () => {
    try {
      const [userRes, orderRes, productRes] = await Promise.all([
        api.get("/customers/all"),
        api.get("/orders/all"),
        api.get("/products/all"),
      ]);

      setUsers(userRes.data);
      setOrders(orderRes.data);
      setProducts(productRes.data);
    } catch (err) {
      console.error("Admin fetch error", err);
      alert("Admin data failed to load");
    }
  };

  const toggleUserStatus = (userId, isActive) => {
    const url = isActive
      ? `/admin/deactivate/${userId}`
      : `/admin/reactivate/${userId}`;
    api[isActive ? "delete" : "put"](url)
      .then(() => {
        alert(isActive ? "User deactivated" : "User reactivated");
        fetchAllData();
      })
      .catch(() => alert("Operation failed"));
  };

  const deleteProduct = (productId) => {
    api
      .delete(`/products/${productId}`)
      .then(() => {
        alert("Product deleted");
        fetchAllData();
      })
      .catch(() => alert("Delete failed"));
  };

  return (
    <div className="container">
      <h2>Admin Dashboard</h2>

      {/* Users Section */}
      <section>
        <h3>All Users</h3>
        {users.length === 0 ? (
          <p>No users found.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Email</th>
                <th>Status</th>
                <th>Role</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {users.map((u) => (
                <tr key={u.userId}>
                  <td>{u.email}</td>
                  <td>{u.userAccountStatus}</td>
                  <td>{u.role}</td>
                  <td>
                    {u.role !== "ROLE_ADMIN" && (
                      <button
                        onClick={() =>
                          toggleUserStatus(
                            u.userId,
                            u.userAccountStatus === "ACTIVE"
                          )
                        }
                      >
                        {u.userAccountStatus === "ACTIVE"
                          ? "Deactivate"
                          : "Reactivate"}
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>

      {/* Orders Section */}
      <section>
        <h3>All Orders</h3>
        {orders.length === 0 ? (
          <p>No orders found.</p>
        ) : (
          <ul>
            {orders.map((o) => (
              <li key={o.orderId}>
                #{o.orderId} - ₹{o.totalAmount} - {o.status} -{" "}
                {new Date(o.orderDate).toLocaleString()}
              </li>
            ))}
          </ul>
        )}
      </section>

      {/* Products Section */}
      <section>
        <h3>All Products</h3>
        <div className="product-grid">
          {products.map((p) => (
            <div key={p.productId} className="product-card">
              <img src={p.imageUrl} alt={p.name} />
              <h4>{p.name}</h4>
              <p>₹{p.price}</p>
              <p>{p.category}</p>
              <button onClick={() => deleteProduct(p.productId)}>Delete</button>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default Admin;
