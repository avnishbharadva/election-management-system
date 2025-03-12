import { AppBar, Button } from "@mui/material";
import { styled } from "@mui/system";
// Styled Navbar Container
export const StyledAppBar = styled(AppBar)({
    backgroundColor: "#FFFFFF", // White background
    boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.4)", // Subtle shadow
    color: "#000", // Black text
  });
  
  // Styled Button
 export const StyledButton = styled(Button)({
    color: "#1C39BB", // Persian Blue (to match sidebar)
    fontWeight: "bold",
    "&:hover": {
      backgroundColor: "rgba(28, 57, 187, 0.1)", // Light hover effect
    },
  });
