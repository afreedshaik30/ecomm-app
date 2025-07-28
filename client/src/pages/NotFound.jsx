import React from "react";
import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div className="container" style={{ textAlign: "center", padding: "2rem" }}>
      <h2>404 - Page Not Found</h2>
      <p>The page you're looking for doesn't exist.</p>
      <Link to="/" style={{ color: "#007bff", textDecoration: "underline" }}>
        Go to Home
      </Link>
    </div>
  );
};

export default NotFound;
