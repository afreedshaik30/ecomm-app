import React, { useEffect, useState } from "react";
import api from "../api/api";

const Product = () => {
  const [products, setProducts] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [category, setCategory] = useState("All");
  const [priceOrder, setPriceOrder] = useState("All");
  const [search, setSearch] = useState("");

  const userId = localStorage.getItem("userid");

  // Fetch all products
  useEffect(() => {
    api
      .get("/products/all")
      .then((res) => {
        setProducts(res.data);
        setFiltered(res.data);
      })
      .catch((err) => console.error("Product fetch failed:", err));
  }, []);

  // Filtering logic
  useEffect(() => {
    let result = [...products];

    if (category !== "All") {
      result = result.filter(
        (p) => p.category.toLowerCase() === category.toLowerCase()
      );
    }

    if (priceOrder === "LowToHigh") {
      result.sort((a, b) => a.price - b.price);
    } else if (priceOrder === "HighToLow") {
      result.sort((a, b) => b.price - a.price);
    }

    if (search.trim()) {
      result = result.filter((p) =>
        p.name.toLowerCase().includes(search.toLowerCase())
      );
    }

    setFiltered(result);
  }, [category, priceOrder, search, products]);

  const addToCart = (productId) => {
    if (!userId) {
      alert("Please login to add products to cart.");
      return;
    }

    api
      .post(`/cart/add-product/${productId}`)
      .then((res) => {
        alert("Added to cart!");
      })
      .catch((err) => {
        console.error("Add to cart failed", err);
        alert("Failed to add to cart.");
      });
  };

  return (
    <div className="container">
      <h2>Product List</h2>

      {/* Filters */}
      <div style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          <option>All</option>
          <option>Fruits</option>
          <option>Vegetables</option>
          <option>Electronics</option>
          <option>Gadgets</option>
        </select>

        <select
          value={priceOrder}
          onChange={(e) => setPriceOrder(e.target.value)}
        >
          <option>All</option>
          <option value="LowToHigh">Low to High</option>
          <option value="HighToLow">High to Low</option>
        </select>

        <input
          type="text"
          placeholder="Search by name"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {/* Product Cards */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))",
          gap: "1.5rem",
        }}
      >
        {filtered.length === 0 ? (
          <h3>No products found</h3>
        ) : (
          filtered.map((p) => (
            <div
              key={p.productId}
              style={{
                border: "1px solid #ccc",
                padding: "1rem",
                borderRadius: "8px",
                background: "#fff",
              }}
            >
              <img
                src={p.imageUrl}
                alt={p.name}
                style={{ width: "100%", height: "150px", objectFit: "cover" }}
              />
              <h3>{p.name}</h3>
              <p>{p.description.slice(0, 50)}...</p>
              <p>
                <strong>₹ {p.price}</strong>
              </p>
              <p>
                <em>{p.category}</em>
              </p>
              <button onClick={() => addToCart(p.productId)}>
                Add to Cart
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Product;
