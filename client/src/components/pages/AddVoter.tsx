<<<<<<< HEAD
import React, { useState } from 'react';
import {
  Box,
  IconButton,
  Menu,
  MenuItem,
  ListItemIcon,
  Typography,
  Button,
} from '@mui/material';
import { Edit, Visibility } from '@mui/icons-material';
import PersonOffIcon from '@mui/icons-material/PersonOff';
import Model from '../ui/Model';
import SearchComponent from '../ui/SearchComponent';
import VoterForm from '../../Voter/VoterForm';
import ViewVoter from '../../Voter/ViewVoter';
import { useSearchVotersQuery } from '../../store/feature/voter/VoterAction';
import TableComponent from '../ui/TableComponent';
import Pagination from '../ui/Pagination';
import { Voter } from '../../store/feature/voter/type';
import { SearchContainer, StyledTableDetails } from '../../style/VoterStyleCss';
 
const voterHeader = [
  { id: 'status', label: 'Status' },
  { id: 'ssn', label: 'SSN' },
  { id: 'dmv', label: 'DMV' },
  { id: 'firstName', label: 'First Name' },
  { id: 'middleName', label: 'Middle Name' },
  { id: 'lastName', label: 'Last Name' },
  { id: 'gender', label: 'Gender' },
  { id: 'dob', label: 'DOB' },
  { id: 'emailId', label: 'Email Id' },
  { id: 'action', label: 'Action' },
];
 
const AddVoter = () => {
  const [searchParams, setSearchParams] = useState({
    page: 0,
    size: 10,
    ssnNumber: '',
  });
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedVoter, setSelectedVoter] = useState<Voter | null>(null);
  const [hasSearched, setHasSearched] = useState(false);
  const [action, setAction] = useState({
    register: false,
    edit: false,
    view: false,
  });
 
  const { data, isLoading, isError } = useSearchVotersQuery({
    page: searchParams.page,
    size: searchParams.size,
    ssnNumber: hasSearched ? searchParams.ssnNumber : '', // Only send SSN when searched
  });
 
  const handleSearchChange = (value: string) => {
    setSearchParams((prev) => ({
      ...prev,
      ssnNumber: value,
      page: 0, // Reset to first page on search change
    }));
    if (value.length === 9) {
      setHasSearched(true);
    } else {
      setHasSearched(false);
    }
  };
 
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, voter: Voter) => {
    setAnchorEl(event.currentTarget);
    setSelectedVoter(voter);
  };
 
  const handleMenuClose = () => {
    setAnchorEl(null);
  };
 
  const handleAction = (actionType: string, voter?: Voter) => {
    switch (actionType) {
      case 'register':
        setSelectedVoter(null);
        setAction({ register: true, edit: false, view: false });
        handleMenuClose();
        break;
      case 'edit':
        voter && setSelectedVoter(voter);
        setAction({ register: false, edit: true, view: false });
        handleMenuClose();
        break;
      case 'view':
        voter && setSelectedVoter(voter);
        setAction({ register: false, edit: false, view: true });
        handleMenuClose();
        break;
      default:
        console.log('default');
        break;
    }
  };
 
  const totalElements = data?.totalElements || 0;
  const voters = (data?.data || []).map((voter: Voter) => ({
    status: voter.status,
    ssn: voter.ssnNumber,
    dmv: voter.dmvNumber,
    firstName: voter.firstName,
    middleName: voter.middleName,
    lastName: voter.lastName,
    gender: voter.gender,
    dob: voter.dateOfBirth,
    emailId: voter.email,
    action: (
      <>
        <IconButton onClick={(e) => handleMenuClick(e, voter)} color="primary">
          ...
        </IconButton>
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl) && selectedVoter?.voterId === voter?.voterId}
          onClose={handleMenuClose}
        >
          <MenuItem onClick={() => handleAction('edit', voter)}>
            <ListItemIcon>
              <Edit fontSize="small" />
            </ListItemIcon>
            Edit
          </MenuItem>
          <MenuItem onClick={() => handleAction('view', voter)}>
            <ListItemIcon>
              <Visibility fontSize="small" />
            </ListItemIcon>
            View
          </MenuItem>
        </Menu>
      </>
    ),
  }));
 
  return (
    <Box>
      <SearchContainer>
        <SearchComponent
          name="SSN Number"
          input={searchParams.ssnNumber}
          length={9}
          onChange={handleSearchChange}
          onReload={() => {
            setSearchParams((prev) => ({
              ...prev,
              ssnNumber: '',
              page: 0,
            }));
            setHasSearched(false);
          }}
        />
      </SearchContainer>  
 
      <TableComponent
        headers={voterHeader}
        rows={voters}
        loading={isLoading}
        error={isError}
        noDataFound={

          <StyledTableDetails >
            <PersonOffIcon sx={{ fontSize: 48, color: '#b0bec5' }} />
            <Typography variant="h6" color="textSecondary">
              No Voter Found!
            </Typography>
            <Button variant="contained" color="primary" onClick={() => handleAction('register')}>
              Add Voter
            </Button>
          </StyledTableDetails>
            
        }
      >
        <Pagination
          totalElements={totalElements}
          setPage={(newPage) => setSearchParams((prev) => ({ ...prev, page: newPage }))}
          page={searchParams.page}
          setRowsPerPage={(newSize) => setSearchParams((prev) => ({ ...prev, size: newSize }))}
          rowsPerPage={searchParams.size}
        />
      </TableComponent>
 
      <Model
        open={action.register}
        handleClose={() => setAction((prev) => ({ ...prev, register: false }))}
      >
        <VoterForm
          onClose={() => setAction((prev) => ({ ...prev, register: false }))}
          ssnNumber={searchParams.ssnNumber}
        />
      </Model>
      <Model
        open={action.edit}
        handleClose={() => setAction((prev) => ({ ...prev, edit: false }))}
      >
        <VoterForm
          onClose={() => setAction((prev) => ({ ...prev, edit: false }))}
          voter={selectedVoter}
        />
      </Model>
      <ViewVoter
        voter={selectedVoter}
        open={action.view}
        handleClose={() => setAction((prev) => ({ ...prev, view: false }))}
      />
    </Box>
  );
};
 
export default AddVoter;













=======
import Model from "../ui/Model"


const AddVoter = () => {
  return (
    
        <Model>
          <>dummy</>
        </Model>
  )
}

export default AddVoter
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
