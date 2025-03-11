import { ListItemButton, ListItemIcon, Drawer, IconButton, Box } from "@mui/material";
import { styled } from "@mui/system";

export const SidebarContainer = styled("div")(({ theme }) => ({
  width: "250px",
  backgroundColor: "#003153", // Persian Blue
  color: "white",
  height: "100vh",
  position: "fixed",
  top: 0,
  left: 0,
  display: "flex",
  flexDirection: "column",
  paddingTop: "64px", // Adjusted for Navbar height
  overflowY: "auto", // Allow scrolling if content exceeds viewport height
  [theme.breakpoints.down("md")]: {
    display: "none", // Hide sidebar on smaller screens
  },
}));

// Drawer styles for small screens
export const StyledDrawer = styled(Drawer)({
  "& .MuiDrawer-paper": {
    width: "250px",
    backgroundColor: "#003153",
    color: "white",
  },
});

// Styled List Item Button
export const StyledListItemButton = styled(ListItemButton)({
  "&:hover": {
    backgroundColor: "rgba(255, 255, 255, 0.2)",
  },
});

// Styled List Item Icon
export const StyledListItemIcon = styled(ListItemIcon)({
  color: "white",
});

// Header Container
export const HeaderContainer = styled(Box)(({ theme }) => ({
  height: "64px",
  backgroundColor: "#003153",
  color: "white",
  display: "flex",
  alignItems: "center",
  justifyContent: "space-between",
  padding: "0 16px",
  [theme.breakpoints.up("md")]: {
    display: "none", // Hide header burger menu on larger screens
  },
}));
