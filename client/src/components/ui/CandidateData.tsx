// import  { useEffect } from "react";
// import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, TableSortLabel, TextField } from "@mui/material";
// import { useDispatch, useSelector } from "react-redux";
// import { AppDispatch } from "../store/app/store"; // Fixed incorrect import from redux-toolkit/query
// import { fetchCandidates } from "../store/feature/candidate/candidateAPI";

// export default function CandidateData() {
//   const dispatch = useDispatch<AppDispatch>();
//   const { allCandidates, filteredCandidate, loading, error } = useSelector(
//     (state: any) => state.candidate
//   );

//   // Fetch all candidates when the component mounts
//   useEffect(() => {
//     dispatch(fetchCandidates());
//   }, [dispatch]);

//   // Display searched candidate or all candidates
//   const candidatesToDisplay = filteredCandidate ? [filteredCandidate] : allCandidates;

//   return (
//     <>
//        {/* Candidate Table */}
//       <TableContainer component={Paper} sx={{ marginTop: 2, width: "80%" }}>
//         <Table>
//           <TableHead>
//             <TableRow>
//               <TableCell><b>SSN</b></TableCell>
//               <TableCell><b>First Name</b></TableCell>
//               <TableCell><b>Middle Name</b></TableCell>
//               <TableCell><b>Last Name</b></TableCell>
//               <TableCell><b>Date of Birth</b></TableCell>
//               <TableCell><b>Email</b></TableCell>
//               <TableCell><b>Gender</b></TableCell>
//               <TableCell><b>Marital Status</b></TableCell>
//               <TableCell><b>Spouse Name</b></TableCell>
//               <TableCell><b>No. of Children</b></TableCell>
//               <TableCell><b>Election</b></TableCell>
//               <TableCell><b>Party Name</b></TableCell>
//               <TableCell><b>Residential Address</b></TableCell>
//               <TableCell><b>Mailing Address</b></TableCell>
//               <TableCell><b>Bank Details</b></TableCell>
//               <TableCell>Action</TableCell>
//             </TableRow>
//           </TableHead>
//           <TableBody>
//             {loading ? (
//               <TableRow>
//                 <TableCell colSpan={6} align="center">Loading...</TableCell>
//               </TableRow>
//             ) : error ? (
//               <TableRow>
//                 <TableCell colSpan={6} align="center">{error}</TableCell>
//               </TableRow>
//             ) : candidatesToDisplay.length > 0 ? (
//               candidatesToDisplay.map((candidate) => (
//                 <TableRow key={candidate.candidateSSN}>
//                   <TableCell>{candidate.candidateSSN}</TableCell>
//                   <TableCell>{`${candidate.candidateName.firstName}`}</TableCell>
//                   <TableCell>{`${candidate.candidateName.middleName || ""}`}</TableCell>
//                   <TableCell>{`${candidate.candidateName.lastName}`}</TableCell>
//                   <TableCell>{candidate.dateOfBirth}</TableCell>
//                   <TableCell>{candidate.candidateEmail}</TableCell>
//                   <TableCell>{candidate.gender}</TableCell>
//                   <TableCell>{candidate.maritialStatus}</TableCell>
//                   <TableCell>{candidate.noOfChildren || "N/A"}</TableCell>
//                   <TableCell>{candidate.spouseName || "N/A"}</TableCell>
//                   <TableCell>{candidate.electionId}</TableCell>
//                   <TableCell>{candidate.partyId}</TableCell>
//                   <TableCell>{`${candidate.residentialAddress.street}`} {`${candidate.residentialAddress.city}`} {`${candidate.residentialAddress.zipcode}`}</TableCell>
//                   <TableCell>{`${candidate.residentialAddress.street}`} {`${candidate.residentialAddress.city}`} {`${candidate.residentialAddress.zipcode}`}</TableCell>
//                   <TableCell>{`${candidate.bankDetails.bankName}`} {`${candidate.bankDetails.bankAddress}`}</TableCell>
//                   <TableCell>
//                     <Button>Edit</Button>
//                     <Button>Delete</Button>
//                   </TableCell>
//                 </TableRow>
//               ))
//             ) : (
//               <TableRow>
//                 <TableCell colSpan={6} align="center">No Candidates Found!</TableCell>
//               </TableRow>
//             )}
//           </TableBody>
//         </Table>
//       </TableContainer>
//     </>
//   );
// }


import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  Box,
  Button,
  IconButton,
  Menu,
  MenuItem,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions
} from "@mui/material";
import { RootState, AppDispatch } from "../../store/app/store";
import { fetchCandidates, fetchCandidateUpdateDetails } from "../../store/feature/candidate/candidateAPI";
import Model from "./Model";
import CandidateForm from "./CandidateForm";
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { StyledButton } from "../../style/CommanStyle";
import { IFormInput } from "../../store/feature/candidate/types";

const CandidateData = () => {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const dispatch = useDispatch<AppDispatch>();
  const { allCandidates, filteredCandidate, loading, error, notFound } = useSelector(
    (state: RootState) => state.candidate
  );

  // Menu State
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
   // Ensure this import is correct
  
  const [selectedCandidate, setSelectedCandidate] = useState<IFormInput | null>(null);
  const [openDialog, setOpenDialog] = useState(false);
  
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  // Handle View Candidate
  const handleView = async (candidateId: number) => {
    try {
      const data = await dispatch(fetchCandidateUpdateDetails(candidateId)).unwrap();
      console.log("Fetched Candidate Data:", data); // Debugging
      setSelectedCandidate(data);
      setOpenDialog(true);
    } catch (error) {
      console.error("Error fetching candidate details:", error);
    }
    handleMenuClose();
  };
  

  // Handle Dialog Close
  const handleDialogClose = () => {
    setOpenDialog(false);
    setSelectedCandidate(null);
  };

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    dispatch(fetchCandidates());
  }, [dispatch]);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const candidatesToDisplay = filteredCandidate ? [filteredCandidate] : allCandidates;

  return (
    <TableContainer component={Paper} sx={{ marginTop: 2, width: "90%", boxShadow: '0px 4px 10px rgba(128, 128, 128, 0.5)' }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell><b>SSN</b></TableCell>
            <TableCell><b>First Name</b></TableCell>
            <TableCell><b>Middle Name</b></TableCell>
            <TableCell><b>Last Name</b></TableCell>
            <TableCell><b>Email</b></TableCell>
            <TableCell><b>Gender</b></TableCell>
            <TableCell><b>Election</b></TableCell>
            <TableCell><b>Party Name</b></TableCell>
            <TableCell><b>Action</b></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {loading ? (
            <TableRow>
              <TableCell colSpan={9} align="center">Loading...</TableCell>
            </TableRow>
          ) : error ? (
            <TableRow>
              <TableCell colSpan={9} align="center">{error}</TableCell>
            </TableRow>
          ) : notFound ? (
            <TableRow>
              <TableCell colSpan={9} align="center">
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', flexDirection: 'column' }}>
                  No Candidate Found!
                  {notFound && (
                    <Model open={open} actionType="add" handleOpen={handleOpen} handleClose={handleClose}>
                      <CandidateForm />
                    </Model>
                  )}
                </Box>
              </TableCell>
            </TableRow>
          ) : candidatesToDisplay.length > 0 ? (
            candidatesToDisplay.map((candidate) => (
              <TableRow key={candidate?.candidateId} style={{ textWrap: 'nowrap' }}>
                <TableCell>{candidate?.candidateSSN}</TableCell>
                <TableCell>{candidate?.candidateName?.firstName}</TableCell>
                <TableCell>{candidate?.candidateName?.middleName || ""}</TableCell>
                <TableCell>{candidate?.candidateName?.lastName}</TableCell>
                <TableCell>{candidate?.candidateEmail}</TableCell>
                <TableCell>{candidate?.gender}</TableCell>
                <TableCell>{candidate?.electionName}</TableCell>
                <TableCell>{candidate?.partyName}</TableCell>
                <TableCell>
                  <IconButton onClick={handleMenuClick}>
                    <MoreHorizIcon />
                  </IconButton>
                  <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                    <MenuItem onClick={() => handleView(candidate?.candidateId)}>View</MenuItem>
                    <MenuItem>
                      <Model open={open} actionType="edit" handleOpen={handleOpen} handleClose={handleClose}>
                        <CandidateForm />
                      </Model>
                    </MenuItem>
                    <MenuItem onClick={handleMenuClose}>
                      <StyledButton variant="contained" startIcon={<VisibilityIcon />}>Delete</StyledButton>
                    </MenuItem>
                  </Menu>
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={6} align="center">No Candidates Found!</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
      {!filteredCandidate && !notFound && (
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={allCandidates.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      )}

      {/* Candidate View Dialog */}
      <Dialog open={openDialog} onClose={handleDialogClose} fullWidth maxWidth="sm">
  <DialogTitle>Candidate Details</DialogTitle>
  <DialogContent>
  {selectedCandidate ? (
    <>
      <p><b>ID:</b> {selectedCandidate?.candidateId}</p>
      <p><b>SSN:</b> {selectedCandidate?.candidateSSN}</p>
      <p><b>Name:</b> {selectedCandidate?.candidateName?.firstName} {selectedCandidate?.candidateName?.middleName || ""} {selectedCandidate?.candidateName?.lastName}</p>
      <p><b>Email:</b> {selectedCandidate?.candidateEmail}</p>
      <p><b>Gender:</b> {selectedCandidate?.gender}</p>
      <p><b>Date of Birth:</b> {selectedCandidate?.dateOfBirth}</p>
      <p><b>Marital Status:</b> {selectedCandidate?.maritialStatus}</p>
      <p><b>Spouse Name:</b> {selectedCandidate?.spouseName}</p>
      <p><b>Number of Children:</b> {selectedCandidate?.noOfChildren}</p>
      <p><b>State:</b> {selectedCandidate?.stateName}</p>

      {/* Mailing Address */}
      <p><b>Mailing Address:</b></p>
      <p>{selectedCandidate?.mailingAddress?.street}, {selectedCandidate?.mailingAddress?.city}, {selectedCandidate?.mailingAddress?.zipcode}</p>

      {/* Residential Address */}
      <p><b>Residential Address:</b></p>
      <p>{selectedCandidate?.residentialAddress?.street}, {selectedCandidate?.residentialAddress?.city}, {selectedCandidate?.residentialAddress?.zipcode}</p>

      {/* Bank Details */}
      <p><b>Bank Details:</b></p>
      <p><b>Bank Name:</b> {selectedCandidate?.bankDetails?.bankName}</p>
      <p><b>Bank Address:</b>{selectedCandidate?.bankDetails?.bankAddress}</p>

      {/* Image Rendering */}
      {selectedCandidate?.candidateImage && (
        <p>
          <b>Candidate Image:</b> <img src={`data:image/png;base64,${selectedCandidate?.candidateImage}`} alt="Candidate" width="100" />
        </p>
      )}
      {selectedCandidate?.candidateSignature && (
        <p>
          <b>Candidate Signature:</b> <img src={`data:image/png;base64,${selectedCandidate?.candidateSignature}`} alt="Signature" width="100" />
        </p>
      )}
    </>
  ) : (
    <p>Loading...</p>
  )}
</DialogContent>
  <DialogActions>
    <Button onClick={handleDialogClose}>Close</Button>
  </DialogActions>
</Dialog>
    </TableContainer>
  );
};

export default CandidateData;

