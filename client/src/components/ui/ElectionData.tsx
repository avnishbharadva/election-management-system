import * as React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  TablePagination,
  Box,
  CircularProgress,
  Menu,
  MenuItem,
  ListItemIcon,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/app/store";
import { fetchElection, deleteElectionById } from "../../store/feature/election/electionApi";
import { setPage, setPerPage } from "../../store/feature/election/electionSlice";
import { toast, ToastContainer } from "react-toastify";

const ElectionData = ({ handleOpenModel }: any) => {
  const dispatch = useDispatch<AppDispatch>();
  const { election = [], loading, currentPage, rowsPerPage, totalElements } = useSelector(
    (state: RootState) => state.election
  );

  // Menu state
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [selectedElection, setSelectedElection] = React.useState<any>(null);

  // Fetch elections on mount & pagination change
  React.useEffect(() => {
    dispatch(fetchElection({ page: currentPage, perPage: rowsPerPage, order: "desc" }));
  }, [dispatch, currentPage, rowsPerPage]);

  // Open menu
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, electionData: any) => {
    setAnchorEl(event.currentTarget);
    setSelectedElection(electionData); // Save selected election data
  };

  // Close menu
  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  // Edit button: Pass selected election data to modal
  const handleEditClick = () => {
    if (selectedElection) {
      console.log("Editing election:", election);
      handleOpenModel(selectedElection); // Pass correct data to modal
    }
    handleMenuClose();
  };

  // Delete function
  const handleDeleteClick = async () => {
    if (selectedElection?.electionId) {
      await dispatch(deleteElectionById(selectedElection.electionId));
      dispatch(fetchElection({ page: currentPage, perPage: rowsPerPage, order: "desc" }));
      toast.success("Election deleted successfully");
    }
    handleMenuClose();
  };

  return (
    <TableContainer component={Paper} sx={{ marginTop: 2, width: "100%", boxShadow: "0px 4px 10px rgba(128, 128, 128, 0.5)" }}>
      {loading ? (
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: 200 }}>
          <CircularProgress />
        </Box>
      ) : (
        <>
          <Table>
            <TableHead>
              <TableRow sx={{ backgroundColor: "#f5f5f5" }}>
                <TableCell><b>Election Name</b></TableCell>
                <TableCell><b>Election Type</b></TableCell>
                <TableCell><b>Election Date</b></TableCell>
                <TableCell><b>Election State</b></TableCell>
                <TableCell><b>Total Seats</b></TableCell>
                <TableCell><b>Actions</b></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {election.map((election: any, index: number) => (
                <TableRow key={election?.electionId ?? `election-${index}`}>
                  <TableCell>{election?.electionName}</TableCell>
                  <TableCell>{election?.electionType}</TableCell>
                  <TableCell>{new Date(election?.electionDate).toLocaleDateString("en-GB")}</TableCell>
                  <TableCell>{election?.electionState}</TableCell>
                  <TableCell>{election?.totalSeats}</TableCell>
                  <TableCell>
                    <IconButton
                      aria-label="more"
                      onClick={(event) => handleMenuClick(event, election)}
                    >
                      <MoreHorizIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          {/* Menu Component */}
          <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleMenuClose}
            PaperProps={{
              style: {
                maxHeight: 48 * 4.5,
                width: "15ch",
              },
            }}
          >
            <MenuItem onClick={handleEditClick}>
              <ListItemIcon>
                <EditIcon />
              </ListItemIcon>
              Edit
            </MenuItem>
            <MenuItem onClick={handleDeleteClick}>
              <ListItemIcon>
                <DeleteIcon />
              </ListItemIcon>
              Delete
            </MenuItem>
          </Menu>

          <TablePagination
            rowsPerPageOptions={[2, 5, 10, 25]}
            component="div"
            count={totalElements}
            rowsPerPage={rowsPerPage}
            page={currentPage}
            onPageChange={(_event, newPage) => dispatch(setPage(newPage))}
            onRowsPerPageChange={(event) => dispatch(setPerPage(parseInt(event.target.value, 10)))}
          />
          <ToastContainer position="top-right" autoClose={3000} />
        </>
      )}
    </TableContainer>
  );
};

export default ElectionData;


