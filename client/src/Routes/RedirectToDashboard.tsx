import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const RedirectToDashboard = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      navigate("/dashboard"); 
    }
  }, [token, navigate]);

  return null;
};

export default RedirectToDashboard;
