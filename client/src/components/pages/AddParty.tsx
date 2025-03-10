import React, { useState } from 'react';
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
  Box,
  Button,
  // TablePagination,
} from '@mui/material';
import { Edit, Visibility } from '@mui/icons-material';
// import Model from '../ui/Model';
import ModelComponent from '../ui/ModelComponent';
import PartyForm from '../../Party/PartyForm';
import { usePartyListQuery, useRegisterPartyMutation } from '../../store/feature/party/partyAction';
import ViewParty from '../../Party/ViewParty';
import { tableStyles } from '../../style/PartyStyle'; // Import table styles
//  const PartyForm = React.lazy(()=>import '../../Party/PartyForm';)
import Pagination from '../ui/Pagination';
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

const AddParty = () => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { data, isError, isLoading, refetch } = usePartyListQuery({});
  const [addParty] = useRegisterPartyMutation();
  const [isModelOpen, setIsModelOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [editModel, setEditModel] = useState(false);
  const open = Boolean(anchorEl);
  const [selectedPartyId, setSelectedPartyId] = useState<string | null>(null);
  const [viewPartyOpen, setViewPartyOpen] = useState(false);
  const [selectedParty, setSelectedParty] = useState<Party | null>(null);

  const handleCloseModel = () => {
    setIsModelOpen(false); 
    setSelectedParty(null);
  };
 
  const handleClick = (event: React.MouseEvent<HTMLElement>, party: any) => {
    setAnchorEl(event.currentTarget);
    setSelectedPartyId(party.partyId);
    setSelectedParty(party);
  };
 
  const handleClose = () => {
    setAnchorEl(null);
    setSelectedPartyId(null);
  };
 
  const handleView = () => {
    const party = data?.find((p: any) => p.partyId === selectedPartyId);
    if (party) {
      setSelectedParty(party);
      setViewPartyOpen(true);
    }
    handleClose();
  };

  const handleEdit = () => {
    if (selectedParty) {
      setSelectedParty(selectedParty);
      setEditModel(true);
    }
    handleClose();
  };
 
  const handleCloseViewParty = () => {
    setViewPartyOpen(false);
    setSelectedParty(null);
  };
 
  const handlePartySubmit = async (partyData: any, image: string | null) => {
    try {
      await addParty({ post: partyData, img: image }).unwrap();
      refetch();
    } catch (error) {
      console.error("Error adding party:", error);
    }
  };

  const handleCloseEditModel = () => {

    setEditModel(false);
    setSelectedParty(null);
}
 
  const totalElements =data?.length || 0;
  console.log(totalElements);

  const visibleParties = data?.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) || [];
 
  return (
    <Box>
      <Box sx={tableStyles.addButtonContainer}>
      <Button onClick={() => setIsModelOpen(true)} variant='contained'>Add Party</Button>
        <ModelComponent
          open={isModelOpen}
          handleClose={handleCloseModel}
          onClose={handleCloseModel}
       
        >
          <PartyForm onPartySubmit={handlePartySubmit} />
        </ModelComponent>
      </Box>

      <TableContainer component={Paper} sx={tableStyles.tableContainer}>
        <Table sx={tableStyles.table}>
          <TableHead>
            <TableRow>
              <TableCell sx={tableStyles.tableCellSticky}><b>PartyName</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>Abbreviation</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>FoundationYear</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>PartyWebsite</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>HeadQuarters</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>FounderName</b></TableCell>
              <TableCell sx={tableStyles.tableCellSticky}><b>Actions</b></TableCell>
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
            ) : visibleParties.length > 0 ? (
              visibleParties.map((party: any) => (
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
                      onClick={(event) => handleClick(event, party) }
                      color="primary"
                    >
                      ...
                    </IconButton>
                    <Menu
                      id={`party-menu-${party.partyId}`}
                      anchorEl={anchorEl}
                      open={open && selectedPartyId === party.partyId}
                      // onClose={handleClose}
                    >
                      <MenuItem onClick={handleEdit}>
                        <ListItemIcon>
                          <Edit fontSize='small' />
                        </ListItemIcon>
                        Edit
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
     
      {/* <TablePagination
        rowsPerPageOptions={[5, 10, 25]}
        component="div"
        count={data?.length || 0}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      /> */}


      <Pagination 
      totalElements={totalElements}
      setPage={setPage}
      page={page}
      setRowsPerPage={setRowsPerPage}
      rowsPerPage={rowsPerPage}
      />



       <ModelComponent open={editModel} handleClose={() => setEditModel(false)} >
      <PartyForm
      onClose={handleCloseEditModel}
        onPartySubmit={handlePartySubmit}
        actionType="edit"
        party={selectedParty}
      />
    </ModelComponent>
      <ViewParty party={selectedParty} open={viewPartyOpen} handleClose={handleCloseViewParty} />
    </Box>
  );
};
 
export default AddParty;