import React, { useState } from 'react';
import {
  Typography,
  IconButton,
  Menu,
  MenuItem,
  ListItemIcon,
  Paper,
  Button,
} from '@mui/material';
import { Edit, Visibility, Delete } from '@mui/icons-material';
import ModelComponent from '../ui/ModelComponent';
import PartyForm from '../../Party/PartyForm';
import { usePartyListQuery, useDeletePartyMutation } from '../../store/feature/party/partyAction';
import ViewParty from '../../Party/ViewParty';
import Pagination from '../ui/Pagination';
import TableComponent from '../ui/TableComponent';
import { AddButtonContainer } from '../../style/PartyStyle';
import DeleteDialog from '../ui/DeleteDialog';
 
interface Party {
  partyId: string;
  partyName: string;
  partyAbbreviation: string;
  party: string;
  partyFoundationYear: string;
  partyWebSite: string;
  headQuarters: string;
  partyFounderName: string;
  partySymbol: string;
  partyLeaderName: string;
}
 
const partyHeader = [
  { id: "partyName", label: "Party Name" },
  { id: "partyAbbreviation", label: "Abbreviation" },
  { id: "partyFoundationYear", label: "Foundation Year" },
  { id: "partyWebSite", label: "Website" },
  { id: "headQuarters", label: "Headquarters" },
  // { id: "partyFounderName", label: "Founder Name" },
  {id: "partyLeaderName", label: "Leader Name"},
  { id: "actions", label: "Actions" },
];
 
const AddParty = () => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { data, isError, isLoading } = usePartyListQuery({});
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedParty, setSelectedParty] = useState<Party | null>(null);
  const [action, setAction] = useState({
    register: false,
    edit: false,
    view: false,
  });
  const [deleteParty] = useDeletePartyMutation();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [partyToDeleteId, setPartyToDeleteId] = useState<string | null>(null);
 
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, party: Party) => {
    console.log(party)
    setAnchorEl(event.currentTarget);
    setSelectedParty(party);
  };
 
  const handleMenuClose = () => {
    setAnchorEl(null)
  };
 
  const handleAction = (actionType: string, party?: Party) => {
 
    console.log(actionType, party);
    switch (actionType) {
      case "register":
        setSelectedParty(null);
        setAction({ register: true, edit: false, view: false });
        handleMenuClose();
        break;
      case "edit":
        party &&setSelectedParty(party);
        setAction({ register: false, edit: true, view: false });
        handleMenuClose();
        break;
      case "view":
        party && setSelectedParty(party);
        setAction({ register: false, edit: false, view: true });
        handleMenuClose();
        break;
      case 'delete':
        if (party) {
          setPartyToDeleteId(party.partyId);
          setDeleteDialogOpen(true);
          handleMenuClose();
        }
    break;
      default:
        console.log('default');
        break;
    }
   
  };

  const handleDeleteConfirm = async () => {
    if (partyToDeleteId) {
      await deleteParty(partyToDeleteId);
      setDeleteDialogOpen(false);
      setPartyToDeleteId(null);
    }
  };

  const handleDeleteCancel = () => {
    setDeleteDialogOpen(false);
    setPartyToDeleteId(null);
  };

  console.log("Party data", data?.data);
 
  const totalElements = data?.data.length || 0;
  const visibleParties = data?.data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) || [];
 
  const partys = visibleParties.map((party: Party) => ({
    partyName: party.partyName,
    partyAbbreviation: party.partyAbbreviation,
    partyFoundationYear: party.partyFoundationYear,
    partyWebSite: party.partyWebSite,
    headQuarters: party.headQuarters,
    partyLeaderName: party.partyLeaderName,
    actions: (
      <>
        <IconButton onClick={(e) => handleMenuClick(e, party)} color="primary">
          ...
        </IconButton>
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl) && selectedParty?.partyId === party?.partyId} 
          onClose={handleMenuClose}
        >
          <MenuItem onClick={() => handleAction("edit", party)}>
            <ListItemIcon>
              <Edit fontSize="small" />
            </ListItemIcon>
            Edit
          </MenuItem>
          <MenuItem onClick={() => handleAction("view", party)}>
            <ListItemIcon>
              <Visibility fontSize="small" />
            </ListItemIcon>
            View
          </MenuItem>
          <MenuItem onClick={() => handleAction("delete", party)}> 
              <ListItemIcon>
                  <Delete fontSize="small" />
              </ListItemIcon>
              Delete
          </MenuItem>
        </Menu>
        
      </>
    ),
  }));


 
  return (
    <Paper>
      <AddButtonContainer>
        <Button onClick={() => handleAction('register')} variant="contained">
          Add Party
        </Button>
      </AddButtonContainer>
 
      <TableComponent
        headers={partyHeader}
        rows={partys}
        loading={isLoading}
        error={isError}
        noDataFound={<Typography>No party data available.</Typography>}
      >
        <Pagination
          totalElements={totalElements}
          setPage={setPage}
          page={page}
          setRowsPerPage={setRowsPerPage}
          rowsPerPage={rowsPerPage}
        />
      </TableComponent>
 
      <ModelComponent
        open={ action.register}
        handleClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
      >
        <PartyForm
          onClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
        />
      </ModelComponent>
      <ModelComponent
        open={ action.edit}
        handleClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
      >
        <PartyForm
          onClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
          party={selectedParty}
        />
      </ModelComponent>
      <ViewParty
        party={selectedParty}
        open={action.view}
        handleClose={() => setAction((prev) => ({ ...prev, view: false }))}
      />
       <DeleteDialog
        open={deleteDialogOpen}
        handleClose={handleDeleteCancel}
        handleDelete={handleDeleteConfirm}
        title="Delete Party"
        message={`Are you sure you want to delete ${selectedParty?.partyName}?`}
      />
    </Paper>
  );
};
export default AddParty;



