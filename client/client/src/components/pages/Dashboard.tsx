import React, { useState } from "react";

import { Box } from "@mui/material";
import Sidebar from "../ui/Sidebar";
import Navbar from "../ui/Navbar";
import Cards from "../ui/Cards";

export default function Dashboard() {
  const [isSidebarOpen, setSidebarOpen] = useState(true);

  const toggleSidebar = () => {
    console.log("hi")
    setSidebarOpen((prev) => !prev);
  };

  return (
    <Box display="flex">
      {isSidebarOpen && <Sidebar />}
      <Box flexGrow={1}>
        <Navbar toggleSidebar={toggleSidebar} />
        <Cards />
      </Box>
    </Box>
  );
}
