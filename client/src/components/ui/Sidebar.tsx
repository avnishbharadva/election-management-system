import { Drawer, List, ListItem, ListItemText, Typography } from "@mui/material";
import { SidebarContainer, StyledListItemButton, StyledListItemIcon } from "../../style/SidebarCss";
import { Link, useLocation } from "react-router-dom";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import GroupsIcon from "@mui/icons-material/Groups";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import BallotIcon from "@mui/icons-material/Ballot";
import DashboardIcon from "@mui/icons-material/Dashboard";

interface SidebarProps {
  mobileOpen: boolean;
  handleDrawerToggle: () => void;
}

export default function Sidebar({ mobileOpen, handleDrawerToggle }: SidebarProps) {
  const location = useLocation();

  const menuItems = [
    { text: "Dashboard", icon: <DashboardIcon />, path: "/dashboard" },
    { text: "Candidates", icon: <AccountBoxIcon />, path: "/dashboard/candidates" },
    { text: "Voters", icon: <HowToVoteIcon />, path: "/dashboard/voters" },
    { text: "Parties", icon: <GroupsIcon />, path: "/dashboard/parties" },
    { text: "Elections", icon: <BallotIcon />, path: "/dashboard/elections" },
  ];

  const drawerContent = (
    <List>
      {menuItems.map(({ text, icon, path }) => (
        <ListItem key={text} disablePadding component={Link} to={path} onClick={handleDrawerToggle}>
          <StyledListItemButton
            sx={{
              backgroundColor: location.pathname === path ? "#1976d2" : "inherit",
              "&:hover": { backgroundColor: "#1565c0" },
            }}
          >
            <StyledListItemIcon>{icon}</StyledListItemIcon>
            <ListItemText
              primary={<Typography variant="body2" sx={{ color: "#FFFFFF" }}>{text}</Typography>}
            />
          </StyledListItemButton>
        </ListItem>
      ))}
    </List>
  );

  return (
    <>
      {/* Permanent sidebar for large screens */}
      <SidebarContainer sx={{ display: { xs: "none", md: "block" } }}>
        {drawerContent}
      </SidebarContainer>

      {/* Drawer for small screens */}
      <Drawer
        anchor="left"
        open={mobileOpen}
        onClose={handleDrawerToggle}
        sx={{ display: { xs: "block", md: "none" } }}
      >
        <SidebarContainer>{drawerContent}</SidebarContainer>
      </Drawer>
    </>
  );
}
