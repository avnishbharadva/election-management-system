import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Box, Button, Modal } from "@mui/material";
import { AppDispatch } from "../../store/app/store";
import { RootState } from "@reduxjs/toolkit/query";
import { fetchCandidates } from "../../store/feature/candidate/candidateAPI";
import SearchComponent from "../ui/Search";
import Model from "../ui/Model";
import CandidateForm from "../ui/CandidateForm";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import CandidateData from "../ui/CandidateData";


const AddCandidate = () => {
  const [open, setOpen] = useState(false);

    const handleOpen= () => setOpen(true);
    const handleClose = () => setOpen(false);
  const dispatch = useDispatch<AppDispatch>();
  const { allCandidates, filteredCandidate, loading, error, notFound } = useSelector(
    (state: any) => state.candidate
  );

  // Fetch all candidates when the component mounts
  useEffect(() => {
    dispatch(fetchCandidates());
  }, [dispatch]);

  // Display searched candidate or all candidates
  const candidatesToDisplay = filteredCandidate ? [filteredCandidate] : allCandidates;

  return (
    <Box sx={{ display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column" }}>
    {/* Search Bar */}
    {/* <Box sx={{ width: "80%", display: "flex", justifyContent: "flex-end", marginBottom: 3 }}> */}
      <SearchComponent />
      {/* Show "Add Candidate" Button If Not Found */}
    {/* {notFound && (
      <Model open={open} handleClose={handleClose}>
        <CandidateForm/>
      </Model>
    )} */}
    {/* </Box> */}


      {/* Candidate Table */}
      {/* <TableContainer component={Paper} sx={{ marginTop: 2, width: "80%" }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell><b>SSN</b></TableCell>
              <TableCell><b>Name</b></TableCell>
              <TableCell><b>Email</b></TableCell>
              <TableCell><b>Party</b></TableCell>
              <TableCell><b>State</b></TableCell>
              <TableCell><b>Spouse Name</b></TableCell>
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
            ) : candidatesToDisplay.length > 0 ? (
              candidatesToDisplay.map((candidate) => (
                <TableRow key={candidate.candidateSSN}>
                  <TableCell>{candidate.candidateSSN}</TableCell>
                  <TableCell>{`${candidate.candidateName.firstName} ${candidate.candidateName.middleName || ""} ${candidate.candidateName.lastName}`}</TableCell>
                  <TableCell>{candidate.candidateEmail}</TableCell>
                  <TableCell>{candidate.partyId}</TableCell>
                  <TableCell>{candidate.stateName}</TableCell>
                  <TableCell>{candidate.spouseName || "N/A"}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={6} align="center">No Candidates Found!</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer> */}
      <CandidateData/>
    </Box>
  );
};

export default AddCandidate;
