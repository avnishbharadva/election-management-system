import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import "../../style/Navbar.css";

const Navbar: React.FC = () => {
  return (
    <AppBar position="sticky" className="navbar-container">
      <Toolbar>
      <Typography variant="h6" sx={{ flexGrow:1  }}>
         State Officer
        </Typography>
      <Typography variant="h6" sx={{display:"flex",  flexGrow: 8 }}>
          Election Management System
        </Typography>
       
        <Button
          variant="contained"
          sx={{
            padding: "0.6rem 1.4rem",
            borderRadius: "5px",
            background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
            color: "white",
            textTransform: "none",
            transition: "all 0.3s ease-in",
            "&:hover": {
              background: "linear-gradient(90deg, #007BFF, #0056b3)",
            },
          }}
        >
          Logout
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
