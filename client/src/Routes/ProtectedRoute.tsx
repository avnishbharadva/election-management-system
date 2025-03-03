import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
  const isAuthenticated = !!localStorage.getItem("token"); // Replace with your actual auth logic

  return isAuthenticated ? children : <Navigate to="/" />;
};

export default ProtectedRoute;
