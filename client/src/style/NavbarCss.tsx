import { AppBar, Button, Typography } from "@mui/material";
import { styled } from "@mui/system";

export const StyledAppBar = styled(AppBar)({
    backgroundColor: "#FFFFFF", 
    boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.4)", 
    color: "#000", 
  });
  
  
 export const StyledButton = styled(Button)({
    color: "#1C39BB", 
    fontWeight: "bold",
    "&:hover": {
      backgroundColor: "rgba(28, 57, 187, 0.1)", 
    },
  });
  export const ResponsiveTypography = styled(Typography)(({ theme }) => ({
    flexGrow: 1,
    fontSize: "1.5rem", 
    [theme.breakpoints.down("sm")]: {
      fontSize: "1rem",
    }
  }));