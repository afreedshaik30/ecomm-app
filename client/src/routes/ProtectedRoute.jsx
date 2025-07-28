import { Navigate, Outlet } from "react-router-dom";

export const Privateroute = () => {
  const isAuth =
    localStorage.getItem("jwtToken") && localStorage.getItem("userid");
  return isAuth ? <Outlet /> : <Navigate to="/login" />;
};

export const Privaterouteadmin = () => {
  const isAdminAuth =
    localStorage.getItem("jwtToken") && localStorage.getItem("adminid");
  return isAdminAuth ? <Outlet /> : <Navigate to="/admin-login" />;
};
