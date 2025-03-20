
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
import Pagination from '../ui/Pagination'
import { FormData } from '../../store/feature/voter/type';
import { SearchContainer } from '../../style/VoterStyleCss';


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
  const [selectedVoter, setSelectedVoter] = useState<FormData | null>(null);
  const [action, setAction] = useState({
    register: false,
    edit: false,
    view: false,
  });

  const { data, isLoading, isError } = useSearchVotersQuery({
    page: searchParams.page,
    size: searchParams.size,
    ssnNumber:searchParams.ssnNumber 
  });

  const handleSearchChange = (value: string) => {
    setSearchParams((prev) => ({
      ...prev,
      ssnNumber: value,
      page: 0, 
    }));
   
  };

  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, voter:FormData) => {
    setAnchorEl(event.currentTarget);
    setSelectedVoter(voter);
  };
  

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleAction = (actionType: string, voter?: FormData) => {
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
        break;
    }
  };
  const totalElements = data?.totalElements || 0;
  const voters = (data?.data || []).map((voter: FormData) => ({
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
         
          }}
        />
      </SearchContainer>  

      <TableComponent
        headers={voterHeader}
        rows={voters}
        loading={isLoading}
        error={isError}
        noDataFound={
          <Box sx={{ display: 'flex', alignItems: 'center',flexDirection:"column", gap: 2 }}>
            <PersonOffIcon sx={{ fontSize: 48, color: '#b0bec5' }} />
            <Typography variant="h6" color="textSecondary">
              No Voter Found!
            </Typography>
            <Button variant="contained" color="primary" onClick={() => handleAction('register')}>
              Add Voter
            </Button>
          </Box>
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
ssnNumber= {searchParams.ssnNumber}
          onClose={() => setAction((prev) => ({ ...prev, register: false }))}
         
        />
      </Model>
      <Model
        open={action.edit}
        handleClose={() => setAction((prev) => ({ ...prev, edit: false }))}
      >
        <VoterForm
          onClose={() => setAction((prev) => ({ ...prev, edit: false }))}
          updateVoterSsnNumber={selectedVoter?.ssnNumber}
        />
      </Model>

     {selectedVoter?.ssnNumber && <ViewVoter
        ssnNumber={selectedVoter?.ssnNumber}
        open={action.view}
        handleClose={() => setAction((prev) => ({ ...prev, view: false }))}
      />}
    </Box>
  );
};

export default AddVoter;