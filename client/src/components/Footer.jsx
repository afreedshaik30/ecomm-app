import React from "react";

const Footer = () => {
  return (
    <footer style={styles.footer}>
      <p>&copy; {new Date().getFullYear()} B-Mart. All rights reserved.</p>
    </footer>
  );
};

const styles = {
  footer: {
    backgroundColor: "#f4f4f4",
    padding: "1rem",
    textAlign: "center",
    marginTop: "2rem",
    borderTop: "1px solid #ddd",
    color: "#333",
  },
};

export default Footer;
