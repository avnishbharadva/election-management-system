import Model from "../ui/Model";
import React, { useState } from "react";
import VoterForm from "../ui/VoterForm";
import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Menu,
  MenuItem,
  ListItemIcon,
  TablePagination,
} from "@mui/material";
import SearchComponent from "../ui/SearchVoter";
import ViewVoter from "../ui/ViewVoter";
import EditIcon from "@mui/icons-material/Edit";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { useSearchVotersQuery } from "../../store/feature/voter/VoterAction";
import { voterStyles } from "../../style/VoterStyleCss";

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
  const [actionType, setActionType] = useState<"view" | "edit">("edit");
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const menuOpen = Boolean(anchorEl);
  const [editModel, setEditModel] = useState(false);
  const [editOpen, setEditOpen] = useState(false);
  const [viewOpen, setViewOpen] = useState(false);
  const [hasSearched, setHasSearched] = useState(false); 

  
  const handleOpen = (type: "view" | "edit", voter: any) => {
    if (type === "edit") {
      setEditOpen(true);
      setViewOpen(false); // Ensure view is closed
    }
    setActionType(type);
    setSelectedVoter(voter);
    setOpen(true);
  };
  const handleEditClose = () => {
    setSelectedVoter(null);t
    setEditOpen(false);
  };

  const handleClose = () => {
    setSelectedVoter(null);
    setOpen(false);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
    setSelectedVoter(null); 
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
    setSearchParams((prev) => ({ ...prev, page: newPage,size: searchParams.size }));
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
    setHasSearched(true); // Indicate that a search has started
  };

  const { data, isLoading, isError }: any = useSearchVotersQuery({
    page: searchParams.page,
    size: searchParams.size,
    ssnNumber: searchParams.ssnNumber,
  });
  const totalElements = data?.totalElements || 0;

  const voters = data?.data || [];

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
              setHasSearched(false); // Reset search state on reload
            }}
          />
        </Box>
        <Box>
          {hasSearched && (searchParams.ssnNumber === "" || voters.length === 0) && (
            <Model open={open} handleClose={handleClose}>
              <VoterForm />
            </Model>
          )}
        </Box>
        <TableContainer component={Paper} sx={voterStyles.tableContainer}> 
           <Table sx={voterStyles.table}> 
             <TableHead sx={voterStyles.tableHead}> 
               <TableRow>
                 {columns.map((col) => {
                   return (
                     <TableCell key={col} align="left">
                       <b>{col}</b>
                     </TableCell>
                   );
                 })}
               </TableRow>
             </TableHead>
             <TableBody>
               {voters ? (
                 voters.map((voter: any) => (
                   <TableRow key={voter.ssnNumber}>
                     <TableCell >{voter.statusId}</TableCell>
                     <TableCell >{voter.ssnNumber}</TableCell>
                     <TableCell >{voter.dmvNumber}</TableCell>
                     <TableCell >{voter.firstName}</TableCell>
                     <TableCell >{voter.middleName}</TableCell>
                     <TableCell >{voter.lastName}</TableCell>
                     <TableCell >{voter.gender}</TableCell>
                     <TableCell >{voter.dateOfBirth}</TableCell>
                     <TableCell >{voter.email}</TableCell>
                     <TableCell>
                       <IconButton onClick={(e) => handleClick(e, voter)} color="primary">
                         ...
                       </IconButton>
                       <Menu
                         id={`voter-menu-${voter.ssnNumber}`}
                         anchorEl={anchorEl}
                         open={menuOpen && selectedVoter?.ssnNumber === voter.ssnNumber}
                         onClose={handleCloseMenu}
                       >
                         <MenuItem>
                           <ListItemIcon>
                             <EditIcon fontSize="small" />
                           </ListItemIcon>
                           Edit
                           <Model open={editModel} handleClose={handleEditClose}>
                             <VoterForm voter={selectedVoter} />
                           </Model>
                         </MenuItem>
                         <MenuItem onClick={() => handleOpen("view", voter)}>
                           <ListItemIcon>
                             <VisibilityIcon fontSize="small" />
                           </ListItemIcon>
                           View
                           <ViewVoter open={open} handleClose={handleCloseDialog} voter={selectedVoter} handleOpen={setIsDialogOpen} />
                         </MenuItem>
                       </Menu>
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
