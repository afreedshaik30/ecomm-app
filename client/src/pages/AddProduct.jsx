import React, { useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";

const AddProduct = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    description: "",
    price: "",
    imageUrl: "",
    category: "",
    quantity: "",
    isAvailable: true,
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({ ...form, [name]: type === "checkbox" ? checked : value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    api
      .post("/products/add", form)
      .then(() => {
        alert("Product added");
        navigate("/admin/admin");
      })
      .catch(() => alert("Add failed"));
  };

  return (
    <div className="container">
      <h2>Add Product</h2>
      <form onSubmit={handleSubmit}>
        <input
          name="name"
          placeholder="Name"
          onChange={handleChange}
          required
        />
        <input
          name="description"
          placeholder="Description"
          onChange={handleChange}
          required
        />
        <input
          name="price"
          placeholder="Price"
          type="number"
          onChange={handleChange}
          required
        />
        <input
          name="imageUrl"
          placeholder="Image URL"
          onChange={handleChange}
          required
        />
        <input
          name="category"
          placeholder="Category"
          onChange={handleChange}
          required
        />
        <input
          name="quantity"
          placeholder="Quantity"
          type="number"
          onChange={handleChange}
          required
        />
        <label>
          <input
            name="isAvailable"
            type="checkbox"
            checked={form.isAvailable}
            onChange={handleChange}
          />
          Available
        </label>
        <br />
        <button type="submit">Add Product</button>
      </form>
    </div>
  );
};

export default AddProduct;
