import { Table,TableBody,TableCell,TableHead,TableRow,IconButton,TablePagination,Box,CircularProgress,Menu,MenuItem,ListItemIcon} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/app/store";
import { fetchElection, deleteElectionById } from "../../store/feature/election/electionApi";
import { setPage, setPerPage } from "../../store/feature/election/electionSlice";
import { toast, ToastContainer } from "react-toastify";
import {useEffect, useState} from 'react';
import { BoxTableContainer } from "../../style/TableContainerCss";
import DeleteDialog from "./DeleteDialog";

const ElectionData = ({ handleOpenModel }: any) => {
  const dispatch = useDispatch<AppDispatch>();
  const { elections, loading, currentPage, perPage, totalRecords } = useSelector(
    (state: RootState) => state.election
  );
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedElection, setSelectedElection] = useState<any>(null);
  const [openDeleteDialog, setOpenDeleteDialog] = useState<boolean>(false);

  useEffect(() => {
    if(!loading){
      dispatch(fetchElection({ page: currentPage, perPage: perPage, order: "desc" }));
    }
  }, [dispatch, currentPage, perPage]);
  
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, electionData: any) => {
    setAnchorEl(event.currentTarget);
    setSelectedElection(electionData); 
  };

  // Close menu
  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  // Edit button: Pass selected election data to modal
  const handleEditClick = () => {
    if (selectedElection) {
      console.log("Editing election:", elections);
      handleOpenModel(selectedElection); // Pass correct data to modal
    }
    handleMenuClose();
  };

  // Delete function
   const handleOpenDeleteDialog = (electionId: number) => {
      setSelectedElection(electionId);
      setOpenDeleteDialog(true);
    };
    
    const handleCloseDeleteDialog = () => {
      setOpenDeleteDialog(false);
      setSelectedElection(null);
    };
    
    const handleDeleteClick = async () => {
      if (selectedElection?.electionId) {
        try {
          await dispatch(deleteElectionById(selectedElection.electionId)).unwrap();
          toast.success("Election deleted successfully");
          dispatch(fetchElection({ page: 0, perPage: 5, order: "desc" }));
        } catch (error) {
          toast.error("Failed to delete election");
        }
      }
      handleCloseDeleteDialog();
      handleMenuClose();
    };

  return (
    <>
    <BoxTableContainer sx={{ marginTop: 2, width: "100%", boxShadow: "0px 4px 10px rgba(128, 128, 128, 0.5)" }}>
      {loading ? (
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: 200 }}>
          <CircularProgress />
        </Box>
      ) : (
        <>
          <Table stickyHeader>
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
                {elections?.elections?.length > 0 ? (
                  elections?.elections?.map((election: any, index: number) => (
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
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={6} align="center" sx={{ 
                      fontSize: "16px", 
                      fontWeight: "bold", 
                      color: "#757575", 
                      padding: "20px",
                      backgroundColor: "#f9f9f9"
                    }}>
                      No elections available.
                    </TableCell>
                  </TableRow>
                )}
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
            <MenuItem onClick={() => handleOpenDeleteDialog(selectedElection)}>
              <ListItemIcon>
                <DeleteIcon />
              </ListItemIcon>
              Delete
            </MenuItem>
          </Menu>

          <TablePagination
            sx={{
              position: "sticky", 
              bottom: 0, 
              backgroundColor: "white", 
              zIndex: 10,
            }}
            rowsPerPageOptions={[2, 5, 10, 25]}
            component="div"
            count={totalRecords}
            rowsPerPage={perPage}
            page={currentPage}
            onPageChange={(_event, newPage) => dispatch(setPage(newPage))}
            onRowsPerPageChange={(event) => dispatch(setPerPage(parseInt(event.target.value, 10)))}
          />
          <ToastContainer position="top-right" autoClose={3000} />
        </>
      )}
    </BoxTableContainer>
    <DeleteDialog
        open={openDeleteDialog}
        handleClose={handleCloseDeleteDialog}
        handleDelete={handleDeleteClick}
        title="Delete Election"
        message="Are you sure you want to delete this election? This action cannot be undone."
    />
    </>
  );
};

export default ElectionData;


