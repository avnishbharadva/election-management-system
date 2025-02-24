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
