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
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import { RootState } from "@reduxjs/toolkit/query";
import { AppDispatch } from "../../store/app/store";
import { fetchCandidates } from "../../store/feature/candidate/candidateAPI";
import Model from "./Model";
import CandidateForm from "./CandidateForm";


const CandidateData = () => {
    const [open, setOpen] = useState(false);

    const handleOpen= () => setOpen(true);
    const handleClose = () => setOpen(false);
  const dispatch = useDispatch<AppDispatch>();
  const { allCandidates, filteredCandidate, loading, error, notFound } = useSelector(
    (state: RootState) => state.candidate
  );

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    dispatch(fetchCandidates());
  }, [dispatch]);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  // Determine candidates to display
  const candidatesToDisplay = filteredCandidate ? [filteredCandidate] : allCandidates;

  return (
    // <Paper sx={{ width: "100%", overflow: "hidden" }}>
    //   <TableContainer sx={{ maxHeight: 440 }}>
    //     <Table stickyHeader>
    //       <TableHead>
    //         <TableRow>
    //           <TableCell><b>SSN</b></TableCell>
    //           <TableCell><b>Name</b></TableCell>
    //           <TableCell><b>Email</b></TableCell>
    //           <TableCell><b>Party</b></TableCell>
    //           <TableCell><b>State</b></TableCell>
    //           <TableCell><b>Spouse Name</b></TableCell>
    //         </TableRow>
    //       </TableHead>
    //       <TableBody>
    //         {loading ? (
    //           <TableRow>
    //             <TableCell colSpan={6} align="center">Loading...</TableCell>
    //           </TableRow>
    //         ) : error ? (
    //           <TableRow>
    //             <TableCell colSpan={6} align="center">{error}</TableCell>
    //           </TableRow>
    //         ) : notFound ? (
    //           <TableRow>
    //             <TableCell colSpan={6} align="center">No Candidate Found!</TableCell>
    //           </TableRow>
    //         ) : candidatesToDisplay.length > 0 ? (
    //           candidatesToDisplay
    //             .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
    //             .map((candidate) => (
    //               <TableRow key={candidate.candidateSSN}>
    //                 <TableCell>{candidate.candidateSSN}</TableCell>
    //                 <TableCell>{`${candidate.candidateName.firstName} ${candidate.candidateName.middleName || ""} ${candidate.candidateName.lastName}`}</TableCell>
    //                 <TableCell>{candidate.candidateEmail}</TableCell>
    //                 <TableCell>{candidate.partyId}</TableCell>
    //                 <TableCell>{candidate.stateName}</TableCell>
    //                 <TableCell>{candidate.spouseName || "N/A"}</TableCell>
    //               </TableRow>
    //             ))
    //         ) : (
    //           <TableRow>
    //             <TableCell colSpan={6} align="center">No Candidates Found!</TableCell>
    //           </TableRow>
    //         )}
    //       </TableBody>
    //     </Table>
    //   </TableContainer>
    //   {!filteredCandidate && !notFound && (
    //     <TablePagination
    //       rowsPerPageOptions={[10, 25, 100]}
    //       component="div"
    //       count={allCandidates.length}
    //       rowsPerPage={rowsPerPage}
    //       page={page}
    //       onPageChange={handleChangePage}
    //       onRowsPerPageChange={handleChangeRowsPerPage}
    //     />
    //   )}
    // </Paper>

    <TableContainer component={Paper} sx={{ marginTop: 2, width: "90%" , boxShadow:'0px 4px 10px rgba(128, 128, 128, 0.5)'}}>
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
                <TableCell colSpan={6} align="center">Loading...</TableCell>
              </TableRow>
            ) : error ? (
              <TableRow>
                <TableCell colSpan={6} align="center">{error}</TableCell>
              </TableRow>
            ) : notFound ? (
              <TableRow  style={{display:'flex', justifyContent:'center', flexDirection:'column', alignItems:'center'}}>
               <TableCell colSpan={6} align="center">No Candidate Found!</TableCell>
                {notFound && (
                    <Model open={open} handleClose={handleClose}>
                    <CandidateForm/>
                    </Model>
                )}
              </TableRow>
            ) : candidatesToDisplay.length > 0 ? (
              candidatesToDisplay.map((candidate) => (
                <TableRow key={candidate.candidateSSN} style={{textWrap:'nowrap'}}>
                  <TableCell>{candidate.candidateSSN}</TableCell>
                  <TableCell>{`${candidate.candidateName.firstName}`}</TableCell>
                  <TableCell>{`${candidate.candidateName.middleName || ""}`}</TableCell>
                  <TableCell>{`${candidate.candidateName.lastName}`}</TableCell>
                  <TableCell>{candidate.candidateEmail}</TableCell>
                  <TableCell>{candidate.gender}</TableCell>
                  <TableCell>{candidate.electionId}</TableCell>
                  <TableCell>{candidate.partyId}</TableCell>
                  <TableCell>
                    <Button>Edit</Button>
                    <Button>Delete</Button>
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
      </TableContainer>
  );
};

export default CandidateData;

