import React, { useState, useEffect } from "react";
import api from "../api/api";
import { useParams, useNavigate } from "react-router-dom";

const EditProduct = () => {
  const { id } = useParams();
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

  useEffect(() => {
    api
      .get(`/products/${id}`)
      .then((res) => setForm(res.data))
      .catch(() => alert("Failed to load product"));
  }, [id]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({ ...form, [name]: type === "checkbox" ? checked : value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    api
      .put(`/products/update/${id}`, form)
      .then(() => {
        alert("Product updated");
        navigate("/admin/admin");
      })
      .catch(() => alert("Update failed"));
  };

  return (
    <div className="container">
      <h2>Edit Product</h2>
      <form onSubmit={handleSubmit}>
        <input name="name" value={form.name} onChange={handleChange} required />
        <input
          name="description"
          value={form.description}
          onChange={handleChange}
          required
        />
        <input
          name="price"
          value={form.price}
          type="number"
          onChange={handleChange}
          required
        />
        <input
          name="imageUrl"
          value={form.imageUrl}
          onChange={handleChange}
          required
        />
        <input
          name="category"
          value={form.category}
          onChange={handleChange}
          required
        />
        <input
          name="quantity"
          value={form.quantity}
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
        <button type="submit">Update Product</button>
      </form>
    </div>
  );
};

export default EditProduct;
