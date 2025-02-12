import Model from "../ui/Model"
import { useState } from "react"
import VoterForm from "../ui/VoterForm"
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material"
import SearchComponent from "../ui/Search"

const mockVoters = [
  { ssn: "12345", name: "Steve Harrington", email: "steveharrington@example.com", party: "Democratic" },
  { ssn: "67890", name: "Jane Hopper", email: "janehopper@example.com", party: "Republican" },
  { ssn: "11223", name: "Mike Wheeler", email: "mikewheeler@example.com", party: "Independent" },
  { ssn: "11213", name: "Will Byers", email: "willbyers@example.com", party: "Conservative" },

];

const AddVoter = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [open, setOpen] = useState(false);
  
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const filteredVoters = mockVoters.filter((voter) =>
    voter.ssn.includes(searchQuery)
  )

  return (
    <>
        <Box>
          <SearchComponent searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
        <Box>
        {filteredVoters.length === 0 && searchQuery && (
          <Model open={handleOpen} handleClose={handleClose} >
            <VoterForm  />
          </Model>
        )}
      </Box>

      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table>
            <TableHead>
            <TableRow>
                <TableCell><b>SSN</b></TableCell>
                <TableCell><b>Name</b></TableCell>
                <TableCell><b>Email</b></TableCell>
                <TableCell><b>Party</b></TableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {filteredVoters.length > 0 ? (
                filteredVoters.map((voter) => (
                <TableRow key={voter.ssn}>
                    <TableCell>{voter.ssn}</TableCell>
                    <TableCell>{voter.name}</TableCell>
                    <TableCell>{voter.email}</TableCell>
                    <TableCell>{voter.party}</TableCell>
                </TableRow>
                ))
            ) : (
                <TableRow>
                <TableCell colSpan={4} align="center">
                    No Candidate Found
                </TableCell>
                </TableRow>
            )}
            </TableBody>
        </Table>
        </TableContainer>
    </Box> 
   </>    
  )
}

export default AddVoter