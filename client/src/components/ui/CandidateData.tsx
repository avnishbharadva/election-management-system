


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
  DialogActions,
  ListItemIcon
} from "@mui/material";
import { RootState, AppDispatch } from "../../store/app/store";
import { fetchCandidateById, fetchCandidates, fetchCandidateUpdateDetails } from '../../store/feature/candidate/candidateAPI';
import Model from "./Model";
import CandidateForm from "./CandidateForm";
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { StyledButton } from "../../style/CommanStyle";
import { IFormInput } from "../../store/feature/candidate/types";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { ToastContainer } from "react-toastify";

const CandidateData = () => {
  const [open, setOpen] = useState(false);
  const [selectedCandidate, setSelectedCandidate] = useState<any | null>(null);
  const dispatch = useDispatch<AppDispatch>();
  const { allCandidates, filteredCandidate, loading, error, notFound,candidate } = useSelector(
    (state: RootState) => state.candidate
  );
  const ITEM_HEIGHT = 48;
  const [modalData, setModalData] = useState<{ open: boolean; actionType: "add" | "edit"; candidate?: any }>({
    open: false,
    actionType: "add",
    candidate: null,
  });

  const handleOpenModal = (actionType: "add" | "edit", candidate?: any) => {
    setModalData({ open: true, actionType, candidate: candidate || null });
  };

  const handleCloseModal = () => {
    setModalData({ open: false, actionType: "add", candidate: null });
  };

  const handleOpenDialog = (candidate: any) => {
    setSelectedCandidate(candidate);
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
    setSelectedCandidate(null);
  };

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedCandidateId, setSelectedCandidateId] = useState<number | null>(null);

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>, candidateId: number) => {
    setAnchorEl(event.currentTarget);
    setSelectedCandidateId(candidateId);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setSelectedCandidateId(null);
  };

  const handleView = async (candidateId: number) => {
    try {
      const data = await dispatch(fetchCandidateById(candidateId)).unwrap();
      handleOpenDialog(data);
    } catch (error) {
      console.error("Error fetching candidate details:", error);
    }
    handleClose();
  };

  const handleEditCandidate = async (candidateId: number) => {
    try {
      const data = await dispatch(fetchCandidateById(candidateId)).unwrap();
      handleOpenModal("edit", data);
    } catch (error) {
      console.error("Error fetching candidate details:", error);
    }
    handleClose();
  };

  useEffect(() => {
    dispatch(fetchCandidates());
  }, [dispatch]);

  return (<>
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
                No Candidate Found!
                <Button variant="contained" onClick={() => handleOpenModal("add")}>
                  Add Candidate
                </Button>
              </TableCell>
            </TableRow>
          ) : (
            (filteredCandidate || allCandidates).map((candidate) => (
              <TableRow key={candidate?.candidateId}>
                <TableCell>{candidate?.candidateSSN}</TableCell>
                <TableCell>{candidate?.candidateName?.firstName}</TableCell>
                <TableCell>{candidate?.candidateName?.middleName || ""}</TableCell>
                <TableCell>{candidate?.candidateName?.lastName}</TableCell>
                <TableCell>{candidate?.candidateEmail}</TableCell>
                <TableCell>{candidate?.gender}</TableCell>
                <TableCell>{candidate?.electionName}</TableCell>
                <TableCell>{candidate?.partyName}</TableCell>
                <TableCell>
                  <IconButton
                    aria-label="more"
                    onClick={(event) => handleClick(event, candidate.candidateId)}
                  >
                    <MoreHorizIcon />
                  </IconButton>
                  <Menu
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl) && selectedCandidateId === candidate.candidateId}
                    onClose={handleClose}
                    slotProps={{
                      paper: {
                        style: {
                          maxHeight: ITEM_HEIGHT * 4.5,
                          width: "20ch",
                        },
                      },
                    }}
                  >
                    <MenuItem onClick={() => handleView(candidate.candidateId)}>
                      <ListItemIcon>
                        <VisibilityIcon />
                      </ListItemIcon>
                      View
                    </MenuItem>
                    <MenuItem onClick={() => handleEditCandidate(candidate.candidateId)}>
                      <ListItemIcon>
                        <EditIcon />
                      </ListItemIcon>
                      Edit
                    </MenuItem>
                    <MenuItem>
                      <ListItemIcon>
                        <DeleteIcon />
                      </ListItemIcon>
                      Delete
                    </MenuItem>
                  </Menu>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>

      <Model open={modalData.open} handleClose={handleCloseModal} actionType={modalData.actionType} selectedCandidate={modalData.candidate}>
        <CandidateForm handleClose={handleCloseModal} selectedCandidate={modalData.candidate} />
      </Model>

      {/* Candidate View Dialog */}
      <Dialog open={open} onClose={handleCloseDialog} fullWidth maxWidth="sm">
        <DialogTitle>Candidate Details</DialogTitle>
        <DialogContent>
        {selectedCandidate ? (
    <>
      <p><b>ID:</b> {selectedCandidate?.candidate?.candidateId}</p>
      <p><b>SSN:</b> {selectedCandidate?.candidate?.candidateSSN}</p>
      <p><b>Name:</b> {selectedCandidate?.candidate?.candidateName?.firstName} {selectedCandidate?.candidateName?.middleName || ""} {selectedCandidate?.candidateName?.lastName}</p>
      <p><b>Email:</b> {selectedCandidate?.candidate?.candidateEmail}</p>
      <p><b>Gender:</b> {selectedCandidate?.candidate?.gender}</p>
      <p><b>Date of Birth:</b> {selectedCandidate?.candidate?.dateOfBirth}</p>
      <p><b>Marital Status:</b> {selectedCandidate?.candidate?.maritialStatus}</p>
      <p><b>Spouse Name:</b> {selectedCandidate?.candidate?.spouseName}</p>
      <p><b>Number of Children:</b> {selectedCandidate?.candidate?.noOfChildren}</p>
      <p><b>State:</b> {selectedCandidate?.candidate?.stateName}</p>

      {/* Mailing Address */}
      <p><b>Mailing Address:</b></p>
      <p>{selectedCandidate?.candidate?.mailingAddress?.street}, {selectedCandidate?.candidate?.mailingAddress?.city}, {selectedCandidate?.candidate?.mailingAddress?.zipcode}</p>

      {/* Residential Address */}
      <p><b>Residential Address:</b></p>
      <p>{selectedCandidate?.candidate?.residentialAddress?.street}, {selectedCandidate?.candidate?.residentialAddress?.city}, {selectedCandidate?.candidate?.residentialAddress?.zipcode}</p>

      {/* Bank Details */}
      <p><b>Bank Details:</b></p>
      <p><b>Bank Name:</b> {selectedCandidate?.candidate?.bankDetails?.bankName}</p>
      <p><b>Bank Address:</b>{selectedCandidate?.candidate?.bankDetails?.bankAddress}</p>

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
          <Button onClick={handleCloseDialog}>Close</Button>
        </DialogActions>
      </Dialog>
    </TableContainer>
    </>
  );
};

export default CandidateData;
