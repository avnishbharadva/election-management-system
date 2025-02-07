import { Toolbar, Typography } from "@mui/material";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { StyledAppBar } from "../../style/NavbarCss";
import { StyledButton } from "../../style/NavbarCss";
export default function Navbar() {
  return (
    <StyledAppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Election Management System
        </Typography>
        <Typography variant="h4">
          <AccountCircleIcon/>
        </Typography>
        <StyledButton>Logout</StyledButton>
      </Toolbar>
    </StyledAppBar>
  );
}
