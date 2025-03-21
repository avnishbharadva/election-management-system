import { AppBar, Button } from "@mui/material";
import { styled } from "@mui/system";
// Styled Navbar Container
export const StyledAppBar = styled(AppBar)({
    backgroundColor: "#FFFFFF", 
    boxShadow: "0px 0px 2px rgba(0, 0, 0, 0.4)", 
    color: "#000", 
  });
  
  // Styled Button
 export const StyledButton = styled(Button)({
    color: "#1C39BB",
    fontWeight: "bold",
    "&:hover": {
      backgroundColor: "rgba(28, 57, 187, 0.1)",
    },
  });
