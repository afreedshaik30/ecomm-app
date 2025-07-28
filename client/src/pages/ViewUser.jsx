import React, { useEffect, useState } from "react";
import api from "../api/api";
import { useParams } from "react-router-dom";

const ViewUser = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);

  useEffect(() => {
    api
      .get(`/customers/${id}`)
      .then((res) => setUser(res.data))
      .catch(() => alert("Failed to load user"));
  }, [id]);

  return (
    <div className="container">
      <h2>User Details</h2>
      {user ? (
        <>
          <p>
            <strong>Name:</strong> {user.firstName} {user.lastName}
          </p>
          <p>
            <strong>Email:</strong> {user.email}
          </p>
          <p>
            <strong>Phone:</strong> {user.phoneNumber}
          </p>
          <p>
            <strong>Status:</strong> {user.userAccountStatus}
          </p>
          <p>
            <strong>Role:</strong> {user.role}
          </p>

          {user.address?.length > 0 && (
            <>
              <h4>Address:</h4>
              <ul>
                {user.address.map((a) => (
                  <li key={a.addressID}>
                    {a.flatNo}, {a.street}, {a.city}, {a.state} - {a.zipCode}
                  </li>
                ))}
              </ul>
            </>
          )}
        </>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default ViewUser;
