<<<<<<< Updated upstream
import { useState } from "react";
import CandidateForm from "../ui/CandidateForm";
import Model from "../ui/Model";
import SearchComponent from "../ui/SearchVoter";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Box } from "@mui/material";
=======
// import SearchComponent from "../ui/SearchCandidate";
// import CandidateData from "../ui/CandidateData";
// import { FlexBoxColumn } from "../../style/CommanStyle";
>>>>>>> Stashed changes

const mockCandidates = [
  { ssn: "12345", name: "John Doe", email: "johndoe@example.com", party: "Democratic" },
  { ssn: "67890", name: "Jane Smith", email: "janesmith@example.com", party: "Republican" },
  { ssn: "11223", name: "Mike Johnson", email: "mikejohnson@example.com", party: "Independent" },
];

<<<<<<< Updated upstream
const AddCandidate = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const filteredCandidates = mockCandidates.filter((candidate) =>
    candidate.ssn.includes(searchQuery)
  );

  return (
    <>
      <Box sx={{display:'flex', alignItems:'center', justifyContent:'center', flexDirection:'column'}}>
        {/* Search Bar */}
        <SearchComponent searchQuery={searchQuery} setSearchQuery={setSearchQuery} />

        <Box>
        {/* Add Candidate Button (only if no match is found) */}
        {filteredCandidates.length === 0 && searchQuery && (
        <Model open={handleOpen} handleClose={handleClose} >
            <CandidateForm  />
        </Model>
        )}
        </Box>


        {/* Candidate Table */}
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
            {filteredCandidates.length > 0 ? (
                filteredCandidates.map((candidate) => (
                <TableRow key={candidate.ssn}>
                    <TableCell>{candidate.ssn}</TableCell>
                    <TableCell>{candidate.name}</TableCell>
                    <TableCell>{candidate.email}</TableCell>
                    <TableCell>{candidate.party}</TableCell>
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
  );
};
=======
// const AddCandidate = () => {
//   return (
//     <FlexBoxColumn>
//       <SearchComponent />
//           <CandidateData/>
//     </FlexBoxColumn>
//   );
// };
>>>>>>> Stashed changes

// export default AddCandidate;
