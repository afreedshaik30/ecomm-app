import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

const AdminLogin = () => {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const authHeader = `Basic ${btoa(`${form.username}:${form.password}`)}`;
      const res = await api.post(
        "/auth/login",
        {},
        {
          headers: { Authorization: authHeader },
        }
      );

      if (res.data.role === "ROLE_ADMIN") {
        localStorage.setItem("jwtToken", res.headers.authorization);
        localStorage.setItem("adminid", res.data.id);
        localStorage.setItem("name", res.data.firstName || "Admin");
        alert("Admin login successful");
        navigate("/admin/admin");
      } else {
        alert("Access denied: Not an admin.");
      }
    } catch {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="container">
      <h2>Admin Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          name="username"
          placeholder="Email"
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default AdminLogin;
