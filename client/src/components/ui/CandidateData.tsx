import React, { useEffect, useMemo, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  Button,
  IconButton,
  Menu,
  MenuItem,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  ListItemIcon,
  TablePagination,
  Box,
  Typography,
  CircularProgress
} from "@mui/material";
import { RootState, AppDispatch } from "../../store/app/store";
import { deleteCandidateById, fetchCandidateById, fetchCandidates } from "../../store/feature/candidate/candidateAPI";
import Model from "../ui/Model";
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import ViewCandidate from "./ViewCandidate";
import PersonOffIcon from '@mui/icons-material/PersonOff';
import { resetState, setPage, setPerPage , setSort} from "../../store/feature/candidate/candidateSlice";
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { BoxTableContainer } from "../../style/TableContainerCss";
import { Candidate, ModalData } from "../../store/feature/candidate/types";
import CandidateContainer from "./CandidateForm/CandidatePage";
import DeleteDialog from "./DeleteDialog";

const CandidateData = () => {
  const [openViewDialog, setOpenViewDialog] = useState(false);
  const [selectedCandidate, setSelectedCandidate] = useState<any | null>(null);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedCandidateId, setSelectedCandidateId] = useState<number | null>(null);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const dispatch = useDispatch<AppDispatch>();
  const { allCandidates = { candidates: [] }, filteredCandidate, loading, error, notFound, currentPage, totalRecords, perPage, sortBy, sortDir } = useSelector(
    (state: RootState) => state.candidate
  );
  const ITEM_HEIGHT = 48;
  const [modalData, setModalData] = useState<ModalData>({
    open: false,
    actionType: null,
    selectedCandidate: null,
  });

  const handleOpenModal = (actionType: "add" | "edit", candidate?: Candidate) => {
    if (actionType === "add") {
      dispatch(resetState());
    }
  
    setModalData({
      open: true,
      actionType,
      selectedCandidate: candidate || null,
    });
  };
  
  const handleCloseModal = () => {
    setModalData({ open: false, actionType: null, selectedCandidate: null });
  };

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
      setSelectedCandidate(data);
      setOpenViewDialog(true);
    } catch (error) {
      console.error("Error fetching candidate details:", error);
    }
    handleClose();
  };

  const handleCloseViewDialog = () => {
    setOpenViewDialog(false);
    setSelectedCandidate(null);
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

    const handleDeleteCandidate = async () => {
      if (selectedCandidateId) {
        try {
          await dispatch(deleteCandidateById(selectedCandidateId));
          dispatch(fetchCandidates({ page: currentPage, perPage }));
        } catch (error) {
          console.error("Error deleting candidate:", error);
        }
      }
      handleCloseDeleteDialog();
    };

  const handleOpenDeleteDialog = (candidateId: number) => {
    setSelectedCandidateId(candidateId);
    setOpenDeleteDialog(true);
  };

  const handleCloseDeleteDialog = () => {
    setSelectedCandidateId(null);
    setOpenDeleteDialog(false);
  };

  const handlePageChange = (_event: unknown, newPage: number) => {
    dispatch(setPage(newPage));
    dispatch(fetchCandidates({ page: newPage, perPage, sortBy, sortDir }));
  };
  
  const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newPerPage = parseInt(event.target.value, 10);
    dispatch(setPerPage(newPerPage));
    dispatch(setPage(0));
    dispatch(fetchCandidates({ page: 0, perPage: newPerPage, sortBy, sortDir }));
  };

  const handleSort = (column: string) => {
    const isAsc = sortBy === column && sortDir === "asc";
    const newOrder = isAsc ? "desc" : "asc";
    dispatch(setSort({ sortBy: column, sortDir: newOrder }));
    dispatch(fetchCandidates({ page: currentPage, perPage, sortBy: column, sortDir: newOrder }));
  };
  
  useEffect(() => {
    console.log("Fetching candidates with params:", { currentPage, perPage, sortBy, sortDir });
    dispatch(fetchCandidates({ page: currentPage, perPage, sortBy, sortDir }));
  }, [dispatch, currentPage, perPage, sortBy, sortDir]);
  
  
  // const candidatesToDisplay = useMemo(() => {
  //   if (filteredCandidate?.length > 0) {
  //     return filteredCandidate;
  //   }
  //   return Array.isArray(allCandidates) ? allCandidates : allCandidates.candidates || [];
  // }, [ filteredCandidate, allCandidates]);

  const candidatesToDisplay = useMemo(() => {
    if (filteredCandidate && filteredCandidate.length > 0) {
      return filteredCandidate;  // Show searched candidate
    } 
    if (allCandidates?.candidates?.length > 0) {
      return allCandidates.candidates; // Show all candidates if no search is performed
    }
    return [];  // No candidates found
  }, [filteredCandidate, allCandidates]);
  
  
  
console.log("Redux State - allCandidates:", allCandidates);
console.log("Redux State - filteredCandidate:", filteredCandidate);
console.log("Redux State - candidatesToDisplay:", candidatesToDisplay);
console.log("Redux State - totalRecords:", totalRecords);

  const renderSortableColumn = (label: string, field: string) => (
    <Box sx={{ display: "flex", alignItems: "center", gap: "4px" }}>
      <b>{label}</b>
      <Box sx={{ display: "flex", flexDirection: "column", lineHeight: 0.8 }}>
      <ArrowDropUpIcon sx={{ fontSize: 18, cursor: "pointer", marginBottom:'-4px' }} onClick={() => handleSort(field)} />
      <ArrowDropDownIcon sx={{ fontSize: 18, cursor: "pointer", marginTop:'-4px' }} onClick={() => handleSort(field)} />
      </Box>
    </Box>
  );
  return (
    <>
      <BoxTableContainer>
        <Table stickyHeader>
          <TableHead>
            <TableRow sx={{ backgroundColor: "#f5f5f5" }}>
              <TableCell>{renderSortableColumn("SSN", "candidateSSN")}</TableCell>
              <TableCell>{renderSortableColumn("First Name", "candidateName.firstName")}</TableCell>
              <TableCell>{renderSortableColumn("Middle Name", "candidateName.middleName")}</TableCell>
              <TableCell>{renderSortableColumn("Last Name", "candidateName.lastName")}</TableCell>
              <TableCell>{renderSortableColumn("Email", "candidateEmail")}</TableCell>
              <TableCell>{renderSortableColumn("Gender", "gender")}</TableCell>
              <TableCell>
                <Box sx={{ display: "flex", alignItems: "center", gap: "4px" }}>
                  <b>Election</b>
                </Box>
              </TableCell>
              <TableCell>
                <Box sx={{ display: "flex", alignItems: "center", gap: "4px" }}>
                  <b>Party Name</b>
                </Box>
              </TableCell>
              <TableCell><b>Action</b></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={9} align="center">
                  Loading...
                  <CircularProgress size={24} />
                </TableCell>
              </TableRow>
            ) : error ? (
              <TableRow>
                <TableCell colSpan={9} align="center">No candidates Found !</TableCell>
              </TableRow>
            ) : notFound ? (
              <TableRow>
                <TableCell colSpan={9} align="center">
                  <Box
                    sx={{
                      display: "flex",
                      flexDirection: "column",
                      justifyContent: "center",
                      alignItems: "center",
                      gap: 2,
                      padding: 3,
                      borderRadius: 2,
                      backgroundColor: "#f9f9f9",
                      boxShadow: 1,
                    }}
                  >
                    <PersonOffIcon sx={{ fontSize: 48, color: "#b0bec5" }} />
                    <Typography variant="h6" color="textSecondary">
                      No Candidate Found!
                    </Typography>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => handleOpenModal("add")}
                      sx={{
                        textTransform: "none",
                        borderRadius: 3,
                        paddingX: 3,
                        paddingY: 1,
                      }}
                    >
                      + Add Candidate
                    </Button>
                  </Box>
                </TableCell>
              </TableRow>
            ) : (
              candidatesToDisplay.map((candidate: any) => (
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
                            width: "15ch",
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
                      <MenuItem onClick={()=>handleOpenDeleteDialog(candidate.candidateId)}>
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
          <Model open={modalData.open} handleClose={handleCloseModal}>
            <CandidateContainer
              handleClose={handleCloseModal} 
              selectedCandidate={modalData.selectedCandidate}
              actionType={modalData.actionType}
            />
        </Model>
        <TablePagination
          sx={{
            position: "sticky", 
            bottom: 0, 
            backgroundColor: "white", 
            zIndex: 10,
          }}
          component="div"
          count={totalRecords}
          page={currentPage}
          rowsPerPage={perPage}
          onPageChange={handlePageChange}
          onRowsPerPageChange={handleRowsPerPageChange}
          rowsPerPageOptions={[5, 10, 20]}
        />
      </BoxTableContainer>
        <ViewCandidate
          open={openViewDialog}
          handleClose={handleCloseViewDialog}
          selectedCandidate={selectedCandidate}
        />
        <DeleteDialog
          open={openDeleteDialog}
          handleClose={handleCloseDeleteDialog}
          handleDelete={handleDeleteCandidate}
          title="Delete Candidate"
          message="Are you sure you want to delete this candidate? This action cannot be undone."
        />
    </>
  );
};

export default CandidateData;