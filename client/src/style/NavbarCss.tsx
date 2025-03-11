// import { AppBar, Button } from "@mui/material";
// import { styled } from "@mui/system";
// // Styled Navbar Container
// export const StyledAppBar = styled(AppBar)({
//     backgroundColor: "#FFFFFF", // White background
//     boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.4)", // Subtle shadow
//     color: "#000", // Black text
//   });
  
//   // Styled Button
//  export const StyledButton = styled(Button)({
//     color: "#1C39BB", // Persian Blue (to match sidebar)
//     fontWeight: "bold",
//     "&:hover": {
//       backgroundColor: "rgba(28, 57, 187, 0.1)", // Light hover effect
//     },
//   });


import { AppBar, Button, IconButton } from "@mui/material";
import { styled } from "@mui/system";

// Styled Navbar Container
export const StyledAppBar = styled(AppBar)(({ theme }) => ({
  backgroundColor: "#FFFFFF", // White background
  boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.4)", // Subtle shadow
  color: "#000", // Black text
  width: "100%", // Full width initially
  [theme.breakpoints.up("md")]: {
    width: `calc(100% - 250px)`, // Adjust for sidebar on larger screens
    marginLeft: "250px", // Offset for sidebar
  },
}));

// Styled Logout Button
export const StyledButton = styled(Button)({
  color: "#1C39BB", // Persian Blue (to match sidebar)
  fontWeight: "bold",
  "&:hover": {
    backgroundColor: "rgba(28, 57, 187, 0.1)", // Light hover effect
  },
});

// Styled IconButton for Menu
export const MenuButton = styled(IconButton)(({ theme }) => ({
  marginRight: theme.spacing(2),
  color: "#000",
  [theme.breakpoints.up("md")]: {
    display: "none", // Hide menu button on larger screens
  },
}));
