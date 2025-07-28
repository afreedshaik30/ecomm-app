import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

const Register = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post("/customers", form);
      alert("Registration successful!");
      navigate("/login");
    } catch (err) {
      alert("Registration failed!");
      console.error(err);
    }
  };

  return (
    <div className="container">
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
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
        <input
          name="firstName"
          type="text"
          placeholder="First Name"
          onChange={handleChange}
          required
        />
        <input
          name="lastName"
          type="text"
          placeholder="Last Name"
          onChange={handleChange}
          required
        />
        <input
          name="phoneNumber"
          type="text"
          placeholder="Phone Number"
          onChange={handleChange}
          required
        />
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
