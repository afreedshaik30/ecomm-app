import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

const RegisterAdmin = () => {
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const secretKey = "208T1A0560";

    try {
      await api.post(`/bmart/admin?secretKey=${secretKey}`, form);
      alert("Admin registered successfully!");
      navigate("/admin-login");
    } catch (err) {
      console.error("Error:", err.response?.data || err.message);
      alert(
        "Admin registration failed: " +
          (err.response?.data?.message || "Check console")
      );
    }
  };

  return (
    <div className="container">
      <h2>Register Admin</h2>
      <form
        onSubmit={handleSubmit}
        style={{
          display: "flex",
          flexDirection: "column",
          gap: "10px",
          maxWidth: "400px",
          margin: "1rem auto",
        }}
      >
        <input
          name="fullName"
          placeholder="Full Name"
          onChange={handleChange}
          required
        />
        <input
          name="phoneNumber"
          placeholder="Phone Number"
          onChange={handleChange}
          required
        />
        <input
          name="email"
          type="email"
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
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegisterAdmin;
