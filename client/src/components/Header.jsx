import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();

  const token = localStorage.getItem("jwtToken");
  const isAdmin = localStorage.getItem("adminid");
  const isUser = localStorage.getItem("userid");
  const name = localStorage.getItem("name");

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <header style={styles.header}>
      <Link to="/" style={styles.logo}>
        B-Mart
      </Link>

      <nav style={styles.nav}>
        <Link to="/products">Products</Link>

        {isUser && (
          <>
            <Link to="/user/cart">Cart</Link>
            <Link to={`/user/order-details`}>Orders</Link>
            <Link to={`/user/profile/${isUser}`}>Profile</Link>
          </>
        )}

        {isAdmin && <Link to="/admin/admin">Admin</Link>}

        {!token && (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register-user">Register</Link>
          </>
        )}
        <Link to="/register-admin">Admin Register</Link>

        {token && (
          <button onClick={handleLogout} style={styles.logout}>
            Logout ({name})
          </button>
        )}
      </nav>
    </header>
  );
};

const styles = {
  header: {
    padding: "1rem",
    backgroundColor: "#0077ff",
    color: "black",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  nav: {
    display: "flex",
    gap: "1rem",
    alignItems: "center",
  },
  logo: {
    fontWeight: "bold",
    fontSize: "1.5rem",
    textDecoration: "none",
    color: "#0077ff",
    backgroundColor: "black",
  },
  logout: {
    background: "yellow",
    color: "#0077ff",
    padding: "5px 10px",
    borderRadius: "4px",
    border: "none",
    cursor: "pointer",
  },
};

export default Header;
