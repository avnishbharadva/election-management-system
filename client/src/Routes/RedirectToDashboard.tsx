import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const RedirectToDashboard = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      navigate("/dashboard"); // Redirect to dashboard if token exists
    }
  }, [token, navigate]);

  return null; // Renders nothing, just handles redirection
};

export default RedirectToDashboard;
