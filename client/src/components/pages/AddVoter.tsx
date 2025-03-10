<<<<<<< Updated upstream
import Model from "../ui/Model"
import { useState } from "react"
import VoterForm from "../ui/VoterForm"
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Popover, Button, TablePagination } from "@mui/material"
import SearchComponent from "../ui/SearchVoter"
import { searchVoters } from "../../store/feature/voter/VoterAPI"
import useQuery from "../../Hook/usequery"
import ViewVoter from '../ui/ViewVoter'
import EditIcon from '@mui/icons-material/Edit';
import VisibilityIcon from '@mui/icons-material/Visibility';
import DeleteIcon from '@mui/icons-material/Delete';

const columns =[ "Status", "SSN", 'DMV' ,'FirstName', 'MiddleName', 'LastName', 'Gender','DOB','Email Id', 'Action' ];

const AddVoter = () => {
  const [searchParams, setSearchParams] = useState({
    page: 0,
    size: 10,
    ssnNumber:""
  
  });
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null); // for Popover
  const [selectedVoter, setSelectedVoter] = useState<any>(null); // Store selected voter
  const [actionType, setActionType] = useState<"view" | "edit">("edit");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  
  const handleOpen = (type: "view" | "edit", voter: any) => {
    setActionType(type);
    setSelectedVoter(voter);
    setOpen(true);
  };  
 
  const handleClose = () => {
    setSelectedVoter(null);
    setOpen(false);
  };

  const handleSearchChange = (value: string) => {
    setSearchParams((prev) => ({
      ...prev,
      ssnNumber: value,
    }));
  };
  const handleClosePopover = () => {
    setAnchorEl(null);
    setSelectedVoter(null); // Reset selected voter
  };

  // const handleAction = (action: "view" | "edit" | "delete", voter: any) => {
  //   console.log(`Performing ${action} on voter`, voter);
  //   // You can implement your edit, view, delete logic here
  // };

 const handleClick = (event: React.MouseEvent, voter: any) => {
    setAnchorEl(event.currentTarget as HTMLElement);
    setSelectedVoter(voter); // Set the voter whose actions are clicked
  };

  const handleCloseDialog = () => {
    setIsDialogOpen(false);
    setSelectedVoter(null);
  };

  const handlePageChange = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
    setSearchParams((prev) => ({ ...prev, page: newPage }));
  };

  // const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   setSearchParams((prev) => ({ ...prev, size: parseInt(event.target.value, 10), page: 1 })); // Reset page
  // };
  const handleRowsPerPageChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSearchParams((prev) => ({
      ...prev,
      size: parseInt(event.target.value, 10),
      page: 0, // Reset page to 0
    }));
  };


  const {data ,isLoading , error } = useQuery(searchVoters, searchParams)

  const totalElements = data?.totalElements || 0 ;
  console.log("Data from Query", data);
  console.log("SearchParams:", searchParams);
 
  return (
    <>
<Box>
<Box className="search-container">

<SearchComponent 
        name="SSN Number"
        input={searchParams.ssnNumber} 
        length={9} 
        onChange={handleSearchChange} 
        onReload={()=>{  setSearchParams((prev) => ({
          ...prev,
          ssnNumber:"",
        }));}}
      />
    </Box>
        <Box>
          {data && (searchParams?.ssnNumber) && (
            <Model open={open} handleClose={handleClose}>
              <VoterForm />
            </Model>
          )}
        </Box>
        
 
        <TableContainer component={Paper} sx={{ marginTop: 2 }}>
          <Table sx={{ minWidth: "max-content", tableLayout: "auto", whiteSpace: "nowrap" }}>
            <TableHead>
              <TableRow>
                {columns.map((col)=>{
                  return <TableCell key={col} align="left"><b>{col}</b></TableCell>
                })}
               
              </TableRow>
            </TableHead>
            <TableBody>
              {data ? (
                data.map((voter:any) => (
                  
                  <TableRow key={voter.ssnNumber}>
                    <TableCell>{voter.statusId}</TableCell>
                    <TableCell>{voter.ssnNumber}</TableCell>
                    <TableCell>{voter.dmvNumber}</TableCell>
                    <TableCell>{voter.firstName}</TableCell>
                    <TableCell>{voter.middleName}</TableCell>
                    <TableCell>{voter.lastName}</TableCell>
                    <TableCell>{voter.gender}</TableCell>
                    <TableCell>{voter.dateOfBirth}</TableCell>
                    <TableCell>{voter.email}</TableCell>
                    <TableCell>
                      <IconButton onClick={(e) => handleClick(e, voter)} color="primary">
                        <Button variant="text">...</Button>
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={9} align="center">
                    No Voter Found
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>       
        </TableContainer>        
        <TablePagination
        component="div" // Use div for better styling control
        count={totalElements}
        page={searchParams.page}
        onPageChange={handlePageChange}
        rowsPerPage={searchParams.size}
        onRowsPerPageChange={handleRowsPerPageChange}
        rowsPerPageOptions={[5, 10, 25, 50, 100]} // More options
      />
      </Box>
      <Popover
        open={Boolean(anchorEl)}
        anchorEl={anchorEl}
        onClose={handleClosePopover}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      >
        
         <Box sx={{ padding: 1, display:'flex', flexDirection:'column' }}>
          <IconButton color="primary">
          <Model  open={open} handleClose={handleClose} actionType="edit" voter={selectedVoter} >
              <VoterForm />
          </Model> 
          </IconButton>

          <IconButton onClick={() => handleOpen('view', selectedVoter)} color='primary'>
          <Button variant="text" startIcon={<VisibilityIcon />} size='small'>View
          <ViewVoter open={open} handleClose={handleCloseDialog} voter={selectedVoter} handleOpen={setIsDialogOpen}/>
          </Button>
          </IconButton>

          {/* <IconButton color="primary" size='small' variant='text'>
            <DeleteIcon />Delete
          </IconButton> */}
         </Box>  
      </Popover>
      
    </>
  );
};
 
export default AddVoter;
=======
// import Model from "../ui/Model";
import React, { useState } from "react";
import VoterForm from "../../Voter/VoterForm";
import ModelComponent from "../ui/ModelComponent";
import {
    Box,
    Table,
    TableBody,
    TableCell,
    Typography,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton,
    Menu,
    MenuItem,
    ListItemIcon,
    TablePagination,
    Button,
} from "@mui/material";
import SearchComponent from "../ui/SearchComponent";
import ViewVoter from "../../Voter/ViewVoter";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { useSearchVotersQuery } from "../../store/feature/voter/VoterAction";
import { voterStyles } from "../../style/VoterStyleCss";
import PersonOffIcon from '@mui/icons-material/PersonOff';

const columns = [
    "Status",
    "SSN",
    "DMV",
    "FirstName",
    "MiddleName",
    "LastName",
    "Gender",
    "DOB",
    "Email Id",
    "Action",
];

const AddVoter = () => {
    const [searchParams, setSearchParams] = useState({
        page: 0,
        size: 10,
        ssnNumber: "",
    });
    const [open, setOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [selectedVoter, setSelectedVoter] = useState<any>(null);
    const [actionType, setActionType] = useState<"view" | "edit" | "add">("add");
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const menuOpen = Boolean(anchorEl);
    const [hasSearched, setHasSearched] = useState(false);

    const handleOpen = (type: "add" | "view" | "edit", voter: any) => {
        setActionType(type);
        setSelectedVoter(voter);
        setOpen(true);
    };

    const handleClose = () => {
        setSelectedVoter(null);
        setOpen(false);
    };

    const handleClick = (event: React.MouseEvent<HTMLElement>, voter: any) => {
        setAnchorEl(event.currentTarget);
        setSelectedVoter(voter);
    };

    const handleCloseDialog = () => {
        setIsDialogOpen(false);
        setSelectedVoter(null);
    };

    const handlePageChange = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setSearchParams((prev) => ({ ...prev, page: newPage, size: searchParams.size }));
    };

    const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchParams((prev) => ({
            ...prev,
            size: parseInt(event.target.value, 10),
            page: searchParams.page,
        }));
    };

    const handleSearchChange = (value: string) => {
        setSearchParams((prev) => ({
            ...prev,
            ssnNumber: value,
        }));
        setHasSearched(true);
        console.log("hasSearched:", true, "voters:", data?.data); // Debugging
    };

    const { data, isLoading, isError }: any = useSearchVotersQuery({
        page: searchParams.page,
        size: searchParams.size,
        ssnNumber: searchParams.ssnNumber,
    });
    console.log();

    const totalElements = data?.totalElements || 0;
    const voters = data?.data;

    return (
        <>
            <Box>
                <Box sx={voterStyles.searchContainer}>
                    <SearchComponent
                        name="SSN Number"
                        input={searchParams.ssnNumber}
                        length={9}
                        onChange={handleSearchChange}
                        onReload={() => {
                            setSearchParams((prev) => ({
                                ...prev,
                                ssnNumber: "",
                            }));
                            setHasSearched(false);
                        }}
                    />
                </Box>
                <TableContainer component={Paper} sx={voterStyles.tableContainer}>
                    <Table sx={voterStyles.table}>
                        <TableHead sx={voterStyles.tableHead}>
                            <TableRow>
                                {columns.map((col) => (
                                    <TableCell key={col} align="left">
                                        <b>{col}</b>
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {voters && voters.length > 0 ? (
                                voters.map((voter: any) => (
                                    <TableRow key={voter.ssnNumber}>
                                        <TableCell>{voter.statusId}</TableCell>
                                        <TableCell>{voter.ssnNumber}</TableCell>
                                        <TableCell>{voter.dmvNumber}</TableCell>
                                        <TableCell>{voter.firstName}</TableCell>
                                        <TableCell>{voter.middleName}</TableCell>
                                        <TableCell>{voter.lastName}</TableCell>
                                        <TableCell>{voter.gender}</TableCell>
                                        <TableCell>{voter.dateOfBirth}</TableCell>
                                        <TableCell>{voter.email}</TableCell>
                                        <TableCell>
                                            <IconButton onClick={(e) => handleClick(e, voter)} color="primary">
                                                ...
                                            </IconButton>
                                            <Menu
                                                id={`voter-menu-${voter.ssnNumber}`}
                                                anchorEl={anchorEl}
                                                open={menuOpen && selectedVoter?.ssnNumber === voter.ssnNumber}
                                            >
                                                <MenuItem onClick={() => handleOpen("view", voter)}>
                                                    <ListItemIcon>
                                                        <VisibilityIcon fontSize="small" />
                                                    </ListItemIcon>
                                                    View
                                                    <ViewVoter handleClose={handleCloseDialog} voter={selectedVoter} />
                                                </MenuItem>
                                            </Menu>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={10} align="center">
                                    <Box
                                    sx={{
                                      display: "flex",
                                      flexDirection: "column",
                                      justifyContent: "center",
                                      alignItems: "center",
                                      gap: 2,
                                      padding: 3,
                                      borderRadius: 2,
                                      backgroundColor: "#f9f9f9",
                                      boxShadow: 1,
                                    }}
                                    >
                                        <PersonOffIcon sx={{ fontSize: 48, color: "#b0bec5" }} />
                                        <Typography variant="h6" color="textSecondary">
                                          No Voter Found!
                                         </Typography>
                                                            {hasSearched && (
                                            <Box>
                                              
                                                <Button variant="contained" color="primary" onClick={() => handleOpen('add', null)}>
                                                    Add Voter
                                                </Button>
                                                <ModelComponent open={open} handleClose={handleClose}>
                                                    <VoterForm ssnNumber={searchParams.ssnNumber} />
                                                </ModelComponent>
                                            </Box>
                                        )}
                                        </Box>
                                    </TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    component="div"
                    count={totalElements}
                    page={searchParams.page}
                    onPageChange={handlePageChange}
                    rowsPerPage={searchParams.size}
                    onRowsPerPageChange={handleRowsPerPageChange}
                    rowsPerPageOptions={[5, 10, 25, 50, 100]}
                />
            </Box>
        </>
    );
};

export default AddVoter;














>>>>>>> Stashed changes
