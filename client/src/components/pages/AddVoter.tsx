import Model from "../ui/Model"
import { useState } from "react"
import VoterForm from "../ui/VoterForm"
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Popover, TextField, InputAdornment, Button, IconButton } from "@mui/material"
import SearchComponent from "../ui/SearchVoter"
import { searchVoters } from "../../api/voterApi/VoterApi"
import useQuery from "../../hooks/usequery";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from '@mui/icons-material/Visibility';
import CandidateForm from "../ui/CandidateForm"

const columns =[ "SSN", 'DMV' ,'FirstName', 'MiddleName', 'LastName', 'Gender','DOB','Email Id', 'Action'  ];

const AddVoter = () => {
  const [searchParams, setSearchParams] = useState({
    page: 0,
    size: 10,
    ssnNumber:""
  
  });
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null); // for Popover
  const [selectedVoter, setSelectedVoter] = useState(null); // Store selected voter
  const [formMode,setFormMode]= useState()
  const [isModelOpen,setIsModelOpen] = useState(false)
  
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const {data ,isLoading , error } = useQuery(searchVoters, searchParams)

  console.log(data, "lodiang" , isLoading , "err", error)

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

  const handleAction = (action:'Delete' | 'Edit' | 'View', voter: any) => {
    console.log(`Performing ${action} on voter`, voter);
    setSelectedVoter(voter);
        setFormMode(action);
        setIsModelOpen(true);
        setAnchorEl(null);
  };
 
  const handleClick = (event, voter) => {
    setAnchorEl(event.currentTarget);
    setSelectedVoter(voter); 
  };
 
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
          {data.length ==  0 && (searchParams?.ssnNumber) && (
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
              {data.length > 0 ? (
                data.map((voter:any) => (
                  <TableRow key={voter.ssnNumber}>
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
                <Box sx={{ padding: 1 }}>
                    <IconButton onClick={() => handleAction('Edit', selectedVoter)} color="primary">
                        <EditIcon />
                        <Model open={open} actionType="edit" handleOpen={handleOpen} handleClose={handleClose}>
                        <CandidateForm />
                      </Model>

                    </IconButton>
                    <IconButton onClick={() => handleAction('View', selectedVoter)} color="primary">
                        <VisibilityIcon />
                    </IconButton>
                    <IconButton onClick={() => handleAction('Delete', selectedVoter)} color="secondary">
                        <DeleteIcon />
                    </IconButton>
                </Box>
            </Popover>
    </>
  );
};
 
export default AddVoter;
 