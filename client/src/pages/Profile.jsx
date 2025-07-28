import React, { useState, useEffect } from "react";
import api from "../api/api";

const Profile = () => {
  const [profile, setProfile] = useState(null);
  const [address, setAddress] = useState(null);
  const [newAddress, setNewAddress] = useState({
    flatNo: "",
    street: "",
    city: "",
    state: "",
    zipCode: "",
  });
  const [editAddress, setEditAddress] = useState(null);
  const [showPassForm, setShowPassForm] = useState(false);
  const [password, setPassword] = useState("");

  useEffect(() => {
    api
      .get("/customers/me")
      .then((res) => {
        setProfile(res.data);
        const latest = res.data.address?.at(-1);
        setAddress(latest);
      })
      .catch((err) => console.error("Profile load failed", err));
  }, []);

  const handleAddressSubmit = (e) => {
    e.preventDefault();
    api
      .post("/address", newAddress)
      .then(() => {
        alert("Address saved");
        window.location.reload();
      })
      .catch((err) => alert("Error adding address"));
  };

  const handleUpdateAddress = (e) => {
    e.preventDefault();
    api
      .put(`/address/${editAddress.addressID}`, editAddress)
      .then(() => {
        alert("Address updated");
        window.location.reload();
      })
      .catch((err) => alert("Update failed"));
  };

  const handleDeleteAddress = () => {
    if (address) {
      api
        .delete(`/address/${address.addressID}`)
        .then(() => {
          alert("Address deleted");
          window.location.reload();
        })
        .catch((err) => alert("Delete failed"));
    }
  };

  const updatePassword = () => {
    api
      .put("/customers/update-password", { newPassword: password })
      .then(() => {
        alert("Password updated");
        setShowPassForm(false);
        setPassword("");
      })
      .catch((err) => alert("Password update failed"));
  };

  return (
    <div className="container">
      <h2>My Profile</h2>
      {profile && (
        <>
          <p>
            <strong>Name:</strong> {profile.firstName} {profile.lastName}
          </p>
          <p>
            <strong>Email:</strong> {profile.email}
          </p>
          <p>
            <strong>Phone:</strong> {profile.phoneNumber}
          </p>
          <p>
            <strong>Status:</strong> {profile.userAccountStatus}
          </p>
        </>
      )}

      <hr />

      <h3>Address</h3>
      {address ? (
        <>
          <p>
            <strong>Flat:</strong> {address.flatNo}
          </p>
          <p>
            <strong>Street:</strong> {address.street}
          </p>
          <p>
            <strong>City:</strong> {address.city}
          </p>
          <p>
            <strong>State:</strong> {address.state}
          </p>
          <p>
            <strong>Zip:</strong> {address.zipCode}
          </p>
          <button onClick={() => setEditAddress(address)}>
            Update Address
          </button>
          <button onClick={handleDeleteAddress} style={{ background: "red" }}>
            Delete Address
          </button>
        </>
      ) : (
        <form onSubmit={handleAddressSubmit}>
          <input
            type="text"
            name="flatNo"
            placeholder="Flat No"
            onChange={(e) =>
              setNewAddress({ ...newAddress, flatNo: e.target.value })
            }
          />
          <input
            type="text"
            name="street"
            placeholder="Street"
            onChange={(e) =>
              setNewAddress({ ...newAddress, street: e.target.value })
            }
          />
          <input
            type="text"
            name="city"
            placeholder="City"
            onChange={(e) =>
              setNewAddress({ ...newAddress, city: e.target.value })
            }
          />
          <input
            type="text"
            name="state"
            placeholder="State"
            onChange={(e) =>
              setNewAddress({ ...newAddress, state: e.target.value })
            }
          />
          <input
            type="text"
            name="zipCode"
            placeholder="Zip Code"
            onChange={(e) =>
              setNewAddress({ ...newAddress, zipCode: e.target.value })
            }
          />
          <button type="submit">Add Address</button>
        </form>
      )}

      {editAddress && (
        <form onSubmit={handleUpdateAddress}>
          <h4>Update Address</h4>
          {["flatNo", "street", "city", "state", "zipCode"].map((key) => (
            <input
              key={key}
              name={key}
              value={editAddress[key]}
              onChange={(e) =>
                setEditAddress({ ...editAddress, [key]: e.target.value })
              }
            />
          ))}
          <button type="submit">Save</button>
          <button type="button" onClick={() => setEditAddress(null)}>
            Cancel
          </button>
        </form>
      )}

      <hr />

      <h3>Password</h3>
      {showPassForm ? (
        <>
          <input
            type="password"
            placeholder="New Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button onClick={updatePassword}>Update</button>
          <button onClick={() => setShowPassForm(false)}>Cancel</button>
        </>
      ) : (
        <button onClick={() => setShowPassForm(true)}>Change Password</button>
      )}
    </div>
  );
};

export default Profile;
