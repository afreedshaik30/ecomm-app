import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

const Login = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", password: "" });

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

      const isAdmin = res.data.role === "ROLE_ADMIN";
      localStorage.setItem("jwtToken", res.headers.authorization);
      localStorage.setItem(isAdmin ? "adminid" : "userid", res.data.id);
      localStorage.setItem("name", res.data.firstName || "User");

      alert("Login Successful!");
      navigate(isAdmin ? "/admin/admin" : "/");
    } catch (err) {
      alert("Invalid credentials!");
      console.error(err);
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          name="username"
          type="text"
          placeholder="Email / Username"
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

export default Login;
