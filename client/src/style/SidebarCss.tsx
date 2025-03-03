import { ListItemButton, ListItemIcon } from "@mui/material";
import { styled } from "@mui/system";

export const SidebarContainer = styled("div")({
    width: 250,
    backgroundColor: "#003153", // Persian Blue
    color: "white",
    height: "100vh",
    position: "fixed",
    top: 0,
    left: 0,
    paddingTop: "64px", // Adjusted for Navbar height
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