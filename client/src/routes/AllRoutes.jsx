import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Cart from "../pages/Cart";
import Product from "../pages/Product";
import OrderDetails from "../pages/OrderDetails";
import Admin from "../pages/Admin";
import AdminLogin from "../pages/AdminLogin";
import RegisterAdmin from "../pages/RegisterAdmin";
import NotFound from "../pages/NotFound"; // FIX: previously from "components"
import ViewUser from "../pages/ViewUser"; // MISSING IMPORT
import EditUser from "../pages/EditUser"; // MISSING IMPORT
import AddProduct from "../pages/AddProduct"; // MISSING IMPORT
import EditProduct from "../pages/EditProduct"; // MISSING IMPORT
import Order from "../pages/Order"; // MISSING IMPORT
import AdminOrderDetails from "../pages/AdminOrderDetails"; // MISSING IMPORT
import Profile from "../pages/Profile"; // MISSING IMPORT
import { Privateroute, Privaterouteadmin } from "./ProtectedRoute";

const AllRoutes = () => (
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/login" element={<Login />} />
    <Route path="/register-user" element={<Register />} />
    <Route path="/product" element={<Product />} />
    <Route path="/admin-login" element={<AdminLogin />} />
    <Route path="/register-admin" element={<RegisterAdmin />} />
    
    {/* Public Access Pages (no auth required) */}
    <Route path="/admin/view-user/:id" element={<ViewUser />} />
    <Route path="/admin/edit-user/:id" element={<EditUser />} />
    <Route path="/user/edit/:id" element={<EditUser />} />
    <Route path="/admin/add-product" element={<AddProduct />} />
    <Route path="/admin/edit-product/:id" element={<EditProduct />} />
    <Route path="/user/order/:id" element={<Order />} />
    <Route path="/admin/order/:id" element={<AdminOrderDetails />} />

    {/* Protected USER routes */}
    <Route path="/user" element={<Privateroute />}>
      <Route path="cart" element={<Cart />} />
      <Route path="order-details" element={<OrderDetails />} />
      <Route path="profile/:id" element={<Profile />} />
    </Route>

    {/* Protected ADMIN routes */}
    <Route path="/admin" element={<Privaterouteadmin />}>
      <Route path="admin" element={<Admin />} />
    </Route>

    {/* Fallback for unmatched routes */}
    <Route path="*" element={<NotFound />} />
  </Routes>
);

export default AllRoutes;
