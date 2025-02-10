import { Typography } from "@mui/material";
import {SidebarContainer} from '../../style/SidebarCss';
import {StyledListItemButton} from '../../style/SidebarCss';
import {StyledListItemIcon} from '../../style/SidebarCss';
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import GroupsIcon from "@mui/icons-material/Groups";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import BallotIcon from "@mui/icons-material/Ballot";
import DashboardIcon from '@mui/icons-material/Dashboard';
import { Link } from "react-router-dom";

// Styled Sidebar Container


export default function Sidebar() {
  return (
    <SidebarContainer>
      <List>
        {[{text: "Dashboard", icon: <DashboardIcon />, path: "/dashboard/cards"},
          { text: "Candidates", icon: <AccountBoxIcon />, path: "/dashboard/candidate" },
          { text: "Voters", icon: <HowToVoteIcon />, path: "/dashboard/voter" },
          { text: "Party", icon: <GroupsIcon />, path: "/dashboard/party" },
          { text: "Election", icon: <BallotIcon />, path: "/dashboard/election" },
        ].map(({ text, icon,path }) => (
          <ListItem key={text} disablePadding component={Link} to={path}>
            <StyledListItemButton>
              <StyledListItemIcon>{icon}</StyledListItemIcon>
              <ListItemText
                primary={<Typography variant="body2" sx={{ color: "#FFFFFF" }}>{text}</Typography>}
              />
            </StyledListItemButton>
          </ListItem>
        ))}
      </List>
    </SidebarContainer>
  );
}
