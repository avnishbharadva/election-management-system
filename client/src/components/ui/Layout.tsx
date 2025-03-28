import { useState } from "react";
import Box from "@mui/material/Box";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom";
import BreadCrumbs from "./BreadCrumbs";

export default function Layout() {
  const [mobileOpen, setMobileOpen] = useState(false);

  // Toggle function for sidebar
  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  return (
    <Box sx={{ display: "flex", height: "100vh" }}>
      {/* Sidebar */}
      <Sidebar mobileOpen={mobileOpen} handleDrawerToggle={handleDrawerToggle} />
      
      {/* Main content area */}
      <Box sx={{ flexGrow: 1, marginLeft: { xs: 0, md: "250px" }, overflowY: "auto" }}>
        <Navbar handleDrawerToggle={handleDrawerToggle} />
        <Box sx={{ p: 3 }}>
          <Box sx={{ marginTop: "2.5rem" }}>
            <BreadCrumbs />
          </Box>
          <Outlet />
        </Box>
      </Box>
    </Box>
  );
}
