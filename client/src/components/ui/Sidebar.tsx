import React, { useState } from "react";
import { Typography, IconButton, List, ListItem, ListItemText } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import {
  SidebarContainer,
  StyledListItemButton,
  StyledListItemIcon,
  StyledDrawer,
  HeaderContainer,
} from "../../style/SidebarCss";
import DashboardIcon from "@mui/icons-material/Dashboard";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import GroupsIcon from "@mui/icons-material/Groups";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import BallotIcon from "@mui/icons-material/Ballot";
import { Link, useLocation } from "react-router-dom";

export default function Sidebar() {
  const location = useLocation();
  const [drawerOpen, setDrawerOpen] = useState(false);

  const menuItems = [
    { text: "Dashboard", icon: <DashboardIcon />, path: "/dashboard" },
    { text: "Candidates", icon: <AccountBoxIcon />, path: "/dashboard/candidate" },
    { text: "Voters", icon: <HowToVoteIcon />, path: "/dashboard/voter" },
    { text: "Parties", icon: <GroupsIcon />, path: "/dashboard/party" },
    { text: "Elections", icon: <BallotIcon />, path: "/dashboard/election" },
  ];

  const toggleDrawer = () => setDrawerOpen(!drawerOpen);

  return (
    <>
      {/* Header for small screens */}
      <HeaderContainer>
        {/* <Typography variant="h6">App Name</Typography> */}
        <IconButton color="inherit" onClick={toggleDrawer}>
          {drawerOpen ? <CloseIcon /> : <MenuIcon />}
        </IconButton>
      </HeaderContainer>

      {/* Sidebar for larger screens */}
      <SidebarContainer>
        <List>
          {menuItems.map(({ text, icon, path }) => (
            <ListItem key={text} disablePadding component={Link} to={path}>
              <StyledListItemButton
                sx={{
                  backgroundColor: location.pathname === path ? "#1976d2" : "inherit",
                  "&:hover": {
                    backgroundColor: "#1565c0",
                  },
                }}
              >
                <StyledListItemIcon>{icon}</StyledListItemIcon>
                <ListItemText
                  primary={
                    <Typography variant="body2" sx={{ color: "#FFFFFF" }}>
                      {text}
                    </Typography>
                  }
                />
              </StyledListItemButton>
            </ListItem>
          ))}
        </List>
      </SidebarContainer>

      {/* Drawer for smaller screens */}
      <StyledDrawer open={drawerOpen} onClose={toggleDrawer} anchor="left">
        <List>
          {menuItems.map(({ text, icon, path }) => (
            <ListItem key={text} disablePadding component={Link} to={path}>
              <StyledListItemButton
                sx={{
                  backgroundColor: location.pathname === path ? "#1976d2" : "inherit",
                  "&:hover": {
                    backgroundColor: "#1565c0",
                  },
                }}
                onClick={toggleDrawer}
              >
                <StyledListItemIcon>{icon}</StyledListItemIcon>
                <ListItemText
                  primary={
                    <Typography variant="body2" sx={{ color: "#FFFFFF" }}>
                      {text}
                    </Typography>
                  }
                />
              </StyledListItemButton>
            </ListItem>
          ))}
        </List>
      </StyledDrawer>
    </>
  );
}
