import React from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import GroupsIcon from '@mui/icons-material/Groups';
import HowToVoteIcon from '@mui/icons-material/HowToVote';
import BallotIcon from '@mui/icons-material/Ballot';
import '../style/Sidebar.css';
//import PersonIcon from '@mui/icons-material/Person';
import { ListItemButton, ListItemIcon } from "@mui/material";
const Sidebar: React.FC = () => {
  return (
    <Box className='sidebar-container'>
      <List>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon >
                 <AccountBoxIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText primary="Candidate" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon >
                 <HowToVoteIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText primary="Voter" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon >
                 <GroupsIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText primary="Party" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon >
                 <BallotIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText primary="Election" />
            </ListItemButton>
          </ListItem>
      </List>
    </Box>
  );
};

export default Sidebar;
