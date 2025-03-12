import { AppBar, Toolbar, Typography, IconButton } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import MenuIcon from "@mui/icons-material/Menu"; // Toggle Button
import { StyledAppBar, StyledButton } from "../../style/NavbarCss";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

interface NavbarProps {
  handleDrawerToggle: () => void;
}

export default function Navbar({ handleDrawerToggle }: NavbarProps) {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    // toast.success("Signout Successfully");
    navigate("/");
  };

  return (
    <StyledAppBar position="fixed" sx={{ width: { xs: "100%", md: `calc(100% - 250px)` }, ml: { xs: 0, md: "250px" } }}>
      <Toolbar>
        {/* Toggle button for mobile */}
        <IconButton color="inherit" aria-label="open drawer" edge="start" sx={{ mr: 2, display: { md: "none" } }} onClick={handleDrawerToggle}>
          <MenuIcon />
        </IconButton>

        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Election Management System
        </Typography>
        <Typography variant="h4">
          <AccountCircleIcon />
        </Typography>
        <StyledButton onClick={logout}>Logout</StyledButton>
      </Toolbar>
    </StyledAppBar>
  );
}
