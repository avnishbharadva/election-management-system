import React from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import GroupsIcon from '@mui/icons-material/Groups';
import HowToVoteIcon from '@mui/icons-material/HowToVote';
import BallotIcon from '@mui/icons-material/Ballot';
import '../../style/Sidebar.css';
//import PersonIcon from '@mui/icons-material/Person';
import { ListItemButton, ListItemIcon, Typography } from "@mui/material";
import { Link } from "react-router-dom";
const Sidebar: React.FC = () => {
  return (
    <Box className='sidebar-container'>
      <List>
      <ListItem disablePadding button component={Link} to="/dashboard/cards">
            <ListItemButton>
              <ListItemIcon >
                 <AccountBoxIcon className="sidebar-icon"/>
              </ListItemIcon>
             <ListItemText primary={<Typography variant="body2" style={{ color: '#FFFFFF' }}>Dashboard</Typography>} /> 
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding button component={Link} to="/dashboard/candidate">
            <ListItemButton>
              <ListItemIcon >
                 <AccountBoxIcon className="sidebar-icon"/>
              </ListItemIcon>
             <ListItemText primary={<Typography variant="body2" style={{ color: '#FFFFFF' }}>Candidates</Typography>} /> 
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding component={Link} to="/dashboard/voter">
            <ListItemButton>
              <ListItemIcon >
                 <HowToVoteIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText primary={<Typography variant="body2" style={{ color: '#FFFFFF' }}>Voters</Typography>} />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding component={Link} to="/dashboard/party">
            <ListItemButton>
              <ListItemIcon >
                 <GroupsIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText  primary={<Typography variant="body2" style={{ color: '#FFFFFF' }}>Party</Typography>}/>
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding component={Link} to="/dashboard/election">
            <ListItemButton>
              <ListItemIcon >
                 <BallotIcon className="sidebar-icon"/>
              </ListItemIcon>
              <ListItemText disableTypography primary={<Typography variant="body2" style={{ color: '#FFFFFF' }}>Election</Typography>}></ListItemText>
            </ListItemButton>
          </ListItem>
      </List>
    </Box>
  );
};

export default Sidebar;
