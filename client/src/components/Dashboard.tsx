import React from "react";
import Box from "@mui/material/Box";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import Cards from "./Cards";
import '../style/Dashboard.css';

const App: React.FC = () => {
  return (
    <Box className= "dashboard-wrapper">
      <Navbar />
      <Box className="dashboard-container">
        <Sidebar />
        <Box className="dashboard-content">
          <Cards/>
        </Box>
      </Box>
    </Box>
  );
};

export default App;
