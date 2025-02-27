import { Toolbar, Typography } from "@mui/material";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { StyledAppBar } from "../../style/NavbarCss";
import { StyledButton } from "../../style/NavbarCss";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
export default function Navbar() {
  const navigate = useNavigate()
  const logout = ()=>{
    localStorage.removeItem('token')
    toast.success("Signout Successfully");
    navigate("/");
  }
  return (
    <StyledAppBar  position="fixed" sx={{ width: `calc(100% - 250px)`, ml: "250px" }}>
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Election Management System
        </Typography>
        <Typography variant="h4">
          <AccountCircleIcon/>
        </Typography>
        <StyledButton onClick={logout}>Logout</StyledButton>
      </Toolbar>
    </StyledAppBar>
  );
}
