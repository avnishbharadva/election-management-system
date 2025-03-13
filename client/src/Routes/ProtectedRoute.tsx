import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
<<<<<<< HEAD
  const isAuthenticated = !!localStorage.getItem("token"); // Replace with your actual auth logic

  return isAuthenticated ? children : <Navigate to="/" />;
=======
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/" />;
  }

  return children;
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
};

export default ProtectedRoute;
