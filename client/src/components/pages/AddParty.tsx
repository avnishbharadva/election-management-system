


 
 
import React, { useState, useEffect } from 'react';
import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  Paper,
  TableBody,
  CircularProgress,
  Typography,
  IconButton,
  Menu,
  MenuItem,
  ListItemIcon,
} from '@mui/material';
import { Edit, Visibility } from '@mui/icons-material';
import Model from '../ui/Model';
import PartyForm from '../ui/PartyForm';
import { usePartyListQuery, useRegisterPartyMutation } from '../../store/feature/party/partyAction';
import ViewParty from '../ui/ViewParty';
 
 
interface Party {
  id: string;
  partyName: string;
  partyAbbreviation: string;
  image: string;
  partyFoundationYear: string;
  partyWebSite: string;
  headQuarters: string;
  founderName: string;
}

const tableHeaders = [
  "PartyName",
  "Abbreviation",
  "FoundationYear",
  "PartyWebsite",
  "HeadQuarters",
  "FounderName",
  "Actions"
];

const AddParty = () => {
  const { data, isError, isLoading, refetch } = usePartyListQuery({});
  const [addParty] = useRegisterPartyMutation();
  const [isModelOpen, setIsModelOpen] = useState(true);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const [selectedPartyId, setSelectedPartyId] = useState<string | null>(null);
  const [viewPartyOpen, setViewPartyOpen] = useState(false);
  const [selectedParty, setSelectedParty] = useState<Party | null>(null);
  const [editModel, setEditModel] = useState(false);
 
  const handleCloseModel = () => {
    setIsModelOpen(false);
    setSelectedParty(null);
  };
 
  const handleClick = (event: React.MouseEvent<HTMLElement>, party:any,partyId :string) => {
    setAnchorEl(event.currentTarget);
    setSelectedPartyId(partyId);
    setSelectedParty(party)
  };
 
  const handleClose = () => {
    setAnchorEl(null);
    setSelectedPartyId(null);
  };
 
  const handleView = () => {
    console.log("handleView selectedPartyId:", selectedPartyId);
 
    const party = data?.find((p: any) => p.partyId === selectedPartyId); // Change p.id to p.partyId
    if (party) {
      setSelectedParty(party);
      setViewPartyOpen(true);
    }
    handleClose();
  };
 
  const handleEditClose = () => {
    setSelectedParty(null);
    setEditModel(false);
  };

 
  const handleCloseViewParty = () => {
    setViewPartyOpen(false);
    setSelectedParty(null);
  };
 
 
  const handlePartySubmit = async (partyData: any, image: string | null) => {
    console.log("AddParty: Image parameter received:", image); // Debugging
    try {
      await addParty({ post: partyData, img: image }).unwrap();
      refetch();
    } catch (error) {
      console.error("Error adding party:", error);
      console.log("Error data", error.data);
    }
  };
 
  return (
    <>
      <Model
        open={isModelOpen}
        handleClose={handleCloseModel}
        actionType="add"
        handleOpen={() => setIsModelOpen(true)}
      >
        <PartyForm onPartySubmit={handlePartySubmit} />
      </Model>
      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
       
 
 
        <Table sx={{ minWidth: 'max-content', tableLayout: 'auto', whiteSpace: 'nowrap' }}>
                 <TableHead>
                     <TableRow>
                      {
                        tableHeaders.map((header) => (
                          <TableCell key={header}><b>{header}</b></TableCell>
                        ))
}
                      </TableRow>
                    </TableHead>
                <TableBody>
                  {isLoading ? (
                    <TableRow>
                         <TableCell colSpan={7} align="center">
                           <CircularProgress />
                         </TableCell>
                       </TableRow>
                  ) : isError ? (
                    <TableRow>
                         <TableCell colSpan={7} align="center">
                           <Typography color="error">Error loading data.</Typography>
                         </TableCell>
                       </TableRow>
                  ) : data && data.length > 0 ? (
                    data.map((party: any) => (
                      <TableRow key={party.id}>
                        <TableCell>{party.partyName}</TableCell>
                        <TableCell>{party.partyAbbreviation}</TableCell>
                        <TableCell>{party.partyFoundationYear}</TableCell>
                        <TableCell>{party.partyWebSite}</TableCell>
                        <TableCell>{party.headQuarters}</TableCell>
                        <TableCell>{party.founderName}</TableCell>
                        <TableCell>
                          <IconButton
                          aria-label="more"
                          aria-controls={`party-menu-${party.partyId}`}
                          aria-haspopup="true"
                          onClick={(event) => {
                            handleClick(event, party,party.partyId) }}
                          color="primary"
                        >
                          ...
                        </IconButton>
                        <Menu
                          id={`party-menu-${party.partyId}`}
                          anchorEl={anchorEl}
                          open={open && selectedPartyId === party.partyId}
                          onClose={handleClose}
                        >
                          <MenuItem >
                            <ListItemIcon>
                              <Edit fontSize="small" />
                            </ListItemIcon>
                            Edit
                            <Model open={editModel} handleClose={handleEditClose}>
                            <PartyForm party={selectedParty} />
                          </Model>

                          </MenuItem>
                          <MenuItem onClick={handleView}>
                            <ListItemIcon>
                              <Visibility fontSize="small" />
                            </ListItemIcon>
                            View
                          </MenuItem>
                        </Menu>
                      </TableCell>
                    </TableRow>
                  ))
                  ) : (
                    <TableRow>
                           <TableCell colSpan={7} align="center">
                             <Typography>No party data available.</Typography>
                           </TableCell>
                         </TableRow>
                  )}
                </TableBody>
              </Table>
                      </TableContainer>
              <ViewParty party={selectedParty} open={viewPartyOpen} handleClose={handleCloseViewParty} />
    </>
  );
};
 
export default AddParty;
 
 