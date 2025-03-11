// import { Toolbar, Typography } from "@mui/material";
// import AccountCircleIcon from '@mui/icons-material/AccountCircle';
// import { StyledAppBar } from "../../style/NavbarCss";
// import { StyledButton } from "../../style/NavbarCss";
// import { useNavigate } from "react-router-dom";
// import { toast } from "react-toastify";
// export default function Navbar() {
//   const navigate = useNavigate()
//   const logout = ()=>{
//     localStorage.removeItem('token')
//     toast.success("Signout Successfully");
//     navigate("/");
//   }
//   return (
//     <StyledAppBar  position="fixed" sx={{ width: `calc(100% - 250px)`, ml: "250px" }}>
//       <Toolbar>
//         <Typography variant="h6" sx={{ flexGrow: 1 }}>
//           Election Management System
//         </Typography>
//         <Typography variant="h4">
//           <AccountCircleIcon/>
//         </Typography>
//         <StyledButton onClick={logout}>Logout</StyledButton>
//       </Toolbar>
//     </StyledAppBar>
//   );
// }
import React from "react";
import { Toolbar, Typography, IconButton } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import MenuIcon from "@mui/icons-material/Menu";
import { StyledAppBar, StyledButton, MenuButton } from "../../style/NavbarCss";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export default function Navbar({ toggleSidebar }: { toggleSidebar: () => void }) {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    toast.success("Signout Successfully");
    navigate("/");
  };

  return (
    <StyledAppBar position="fixed">
      <Toolbar>
        {/* Menu Icon for smaller screens */}
        <MenuButton edge="start" onClick={toggleSidebar}>
          <MenuIcon />
        </MenuButton>

        {/* Application Title */}
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Election Management System
        </Typography>

        {/* Profile Icon */}
        <IconButton>
          <AccountCircleIcon fontSize="large" />
        </IconButton>

        {/* Logout Button */}
        <StyledButton onClick={logout}>Logout</StyledButton>
      </Toolbar>
    </StyledAppBar>
  );
}
