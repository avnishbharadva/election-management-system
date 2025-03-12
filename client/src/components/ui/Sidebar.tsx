// import { Typography } from "@mui/material";
// import { SidebarContainer } from "../../style/SidebarCss";
// import { StyledListItemButton } from "../../style/SidebarCss";
// import { StyledListItemIcon } from "../../style/SidebarCss";
// import List from "@mui/material/List";
// import ListItem from "@mui/material/ListItem";
// import ListItemText from "@mui/material/ListItemText";
// import AccountBoxIcon from "@mui/icons-material/AccountBox";
// import GroupsIcon from "@mui/icons-material/Groups";
// import HowToVoteIcon from "@mui/icons-material/HowToVote";
// import BallotIcon from "@mui/icons-material/Ballot";
// import DashboardIcon from "@mui/icons-material/Dashboard";
// import { Link, useLocation } from "react-router-dom";

// export default function Sidebar() {
//   const location = useLocation(); 
//   const menuItems = [
//     { text: "Dashboard", icon: <DashboardIcon />, path: "/dashboard" },
//     { text: "Candidates", icon: <AccountBoxIcon />, path: "/candidate" },
//     { text: "Voters", icon: <HowToVoteIcon />, path: "/voter" },
//     { text: "Parties", icon: <GroupsIcon />, path: "/party" },
//     { text: "Elections", icon: <BallotIcon />, path: "/election" },
//   ];

//   return (
//     <SidebarContainer>
//       <List>
//         {menuItems.map(({ text, icon, path }) => (
//           <ListItem key={text} disablePadding component={Link} to={path}>
//             <StyledListItemButton
//               sx={{
//                 backgroundColor: location.pathname === path ? "#1976d2" : "inherit",
//                 "&:hover": {
//                   backgroundColor: "#1565c0",
//                 },
//               }}
//             >
//               <StyledListItemIcon>{icon}</StyledListItemIcon>
//               <ListItemText
//                 primary={
//                   <Typography
//                     variant="body2"
//                     sx={{ color: "#FFFFFF" }}
//                   >
//                     {text}
//                   </Typography>
//                 }
//               />
//             </StyledListItemButton>
//           </ListItem>
//         ))}
//       </List>
//     </SidebarContainer>
//   );
// }
import { Typography } from "@mui/material";
import { SidebarContainer } from "../../style/SidebarCss";
import { StyledListItemButton } from "../../style/SidebarCss";
import { StyledListItemIcon } from "../../style/SidebarCss";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import GroupsIcon from "@mui/icons-material/Groups";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import BallotIcon from "@mui/icons-material/Ballot";
import DashboardIcon from "@mui/icons-material/Dashboard";
import { Link, useLocation } from "react-router-dom";

export default function Sidebar() {
  const location = useLocation();
  const menuItems = [
    { text: "Dashboard", icon: <DashboardIcon />, path: "/app/dashboard" },
    { text: "Candidates", icon: <AccountBoxIcon />, path: "/app/candidate" },
    { text: "Voters", icon: <HowToVoteIcon />, path: "/app/voter" },
    { text: "Parties", icon: <GroupsIcon />, path: "/app/party" },
    { text: "Elections", icon: <BallotIcon />, path: "/app/election" },
  ];

  return (
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
                  <Typography
                    variant="body2"
                    sx={{ color: "#FFFFFF" }}
                  >
                    {text}
                  </Typography>
                }
              />
            </StyledListItemButton>
          </ListItem>
        ))}
      </List>
    </SidebarContainer>
  );
}
