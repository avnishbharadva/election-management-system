import Box from "@mui/material/Box";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom"; // Import Outlet

export default function Layout() {
  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar />
      <Box sx={{ flexGrow: 1, marginLeft: "250px" }}>
        <Navbar />
        <Box sx={{ p: 3 }}>
          <Outlet /> {/* This will render child components like Cards, Candidates, etc. */}
        </Box>
      </Box>
    </Box>
  );
}
