import Model from "../ui/Model"
import { useState } from "react"
import VoterForm from "../ui/VoterForm"
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TextField, InputAdornment, IconButton, Popover } from "@mui/material"
// import SearchComponent from "../ui/Search"
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import useQuery from "../../hooks/usequery";
import { searchVoters } from "../../api/voterApi/VoterApi";


// const mockVoters = [
//   { ssn: "12345", name: "Steve Harrington", email: "steveharrington@example.com", party: "Democratic" },
//   { ssn: "67890", name: "Jane Hopper", email: "janehopper@example.com", party: "Republican" },
//   { ssn: "11223", name: "Mike Wheeler", email: "mikewheeler@example.com", party: "Independent" },
//   { ssn: "11213", name: "Will Byers", email: "willbyers@example.com", party: "Conservative" },

// ];

const columns =[ "SSN", 'DMV' ,'FirstName', 'MiddleName', 'LastName', 'Gender','DOB','Email Id', 'Action'  ];



const AddVoter = () => {
 
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null); // for Popover
  const [selectedVoter, setSelectedVoter] = useState(null); // Store selected voter
  const [actionType, setActionType] = useState<"view" | "edit">("edit");
  const [searchParams, setSearchParams] = useState({
    page: 0,
    size: 10,
    ssnNumber:undefined
 
  });
 
 
  const handleOpen = (type: "view" | "edit") => {
    setActionType(type);
    setOpen(true);
  };  const handleClose = () => setOpen(false);
 
  const {data ,isLoading , error } = useQuery(searchVoters, searchParams)
 
  console.log(data, "lodiang" , isLoading , "err", error)
 
  const handleAction = (action: "view" | "edit" | "delete", voter: any) => {
    console.log(`Performing ${action} on voter`, voter);
    // You can implement your edit, view, delete logic here
  };
 
  const handleClick = (event: React.MouseEvent, voter: any) => {
    setAnchorEl(event.currentTarget as HTMLElement);
    setSelectedVoter(voter); // Set the voter whose actions are clicked
  };
 
  const handleClosePopover = () => {
    setAnchorEl(null);
    setSelectedVoter(null); // Reset selected voter
  };
 
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setSearchParams(prev => ({
      ...prev,
      [id]: value,
    }));
  };
 
  const handleSearch = () => {
    setSearchParams(prev => ({ ...prev }));
  };

  return (
    <>
        <Box>
        {/* <Box className="search-container">
      <TextField
      type="number"
        id="ssnNumber"
        fullWidth
        variant="outlined"
        placeholder="Search by SSN..."
        value={searchParams.ssnNumber}

        onChange={handleSearchChange}
        className="search-input"
        onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
        InputProps={{   
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon className="search-icon" />
            </InputAdornment>
          ),
          endAdornment: searchParams && (
            <InputAdornment position="end">
              <IconButton onClick={()=>setSearchParams(prev => ({ ...prev, ssn: "" }))} edge="end">
                <CloseIcon className="clear-icon" />
              </IconButton>
            </InputAdornment>
          ),
        }}
      />
    </Box> */}
 <Box className="search-container">
      <TextField
      type="number"
        id="ssnNumber"
        fullWidth
        variant="outlined"
        placeholder="Search by SSN..."
        value={searchParams.ssnNumber}
        onChange={handleSearchChange}
        onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
        InputProps={{   
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon className="search-icon" />
            </InputAdornment>
          ),
          endAdornment: searchParams && (
            <InputAdornment position="end">
              <IconButton onClick={()=>setSearchParams(prev => ({ ...prev, ssn: "" }))} edge="end">
                <CloseIcon className="clear-icon" />
              </IconButton>
            </InputAdornment>
          ),
        }}
      />
    </Box>

        <Box>
        {data && (searchParams.ssnNumber) && (
          <Model open={handleOpen} handleClose={handleClose} >
            <VoterForm  />
          </Model>
        )}
      </Box>

      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table>
            <TableHead>
            <TableRow>
              {
            columns.map((col)=>{
              return <TableCell key={col}> <b>{col}</b></TableCell>
            })
          }
            </TableRow>
            </TableHead>
            <TableBody>
            {data  ? (
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
                          ...
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
          <IconButton onClick={() => handleAction('edit', selectedVoter)} color="primary">
          <Model  open={open} handleOpen={() => handleOpen("edit")} handleClose={handleClose} actionType="edit">
                  <VoterForm />
                </Model>
          </IconButton>
          <IconButton onClick={() => handleAction('view', selectedVoter)} color="primary">
          <Model  open={open} handleOpen={() => handleOpen("view")} handleClose={handleClose} actionType="view">
                  <VoterForm />
                </Model>
          </IconButton>
          {/* <IconButton onClick={() => handleAction('delete', selectedVoter)} color="secondary">
            <DeleteIcon />
          </IconButton> */}
        </Box>
      </Popover>
   </>    
  )
}

export default AddVoter