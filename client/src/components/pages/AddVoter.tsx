// import Model from "../ui/Model";
import React, { useState } from "react";
import VoterForm from "../../Voter/VoterForm";
import ModelComponent from "../ui/ModelComponent";
import {
    Box,
    Table,
    TableBody,
    TableCell,
    Typography,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton,
    Menu,
    MenuItem,
    ListItemIcon,
    TablePagination,
    Button,
} from "@mui/material";
import SearchComponent from "../ui/SearchComponent";
import ViewVoter from "../../Voter/ViewVoter";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { useSearchVotersQuery } from "../../store/feature/voter/VoterAction";
import { voterStyles } from "../../style/VoterStyleCss";
import PersonOffIcon from '@mui/icons-material/PersonOff';

const columns = [
    "Status",
    "SSN",
    "DMV",
    "FirstName",
    "MiddleName",
    "LastName",
    "Gender",
    "DOB",
    "Email Id",
    "Action",
];

const AddVoter = () => {
    const [searchParams, setSearchParams] = useState({
        page: 0,
        size: 10,
        ssnNumber: "",
    });
    const [open, setOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [selectedVoter, setSelectedVoter] = useState<any>(null);
    const [actionType, setActionType] = useState<"view" | "edit" | "add">("add");
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const menuOpen = Boolean(anchorEl);
    const [hasSearched, setHasSearched] = useState(false);

    const handleOpen = (type: "add" | "view" | "edit", voter: any) => {
        setActionType(type);
        setSelectedVoter(voter);
        setOpen(true);
    };

    const handleClose = () => {
        setSelectedVoter(null);
        setOpen(false);
    };

    const handleClick = (event: React.MouseEvent<HTMLElement>, voter: any) => {
        setAnchorEl(event.currentTarget);
        setSelectedVoter(voter);
    };

    const handleCloseDialog = () => {
        setIsDialogOpen(false);
        setSelectedVoter(null);
    };

    const handlePageChange = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setSearchParams((prev) => ({ ...prev, page: newPage, size: searchParams.size }));
    };

    const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchParams((prev) => ({
            ...prev,
            size: parseInt(event.target.value, 10),
            page: searchParams.page,
        }));
    };

    const handleSearchChange = (value: string) => {
        setSearchParams((prev) => ({
            ...prev,
            ssnNumber: value,
        }));
        setHasSearched(true);
        console.log("hasSearched:", true, "voters:", data?.data); // Debugging
    };

    const { data, isLoading, isError }: any = useSearchVotersQuery({
        page: searchParams.page,
        size: searchParams.size,
        ssnNumber: searchParams.ssnNumber,
    });
    console.log();

    const totalElements = data?.totalElements || 0;
    const voters = data?.data;

    return (
        <>
            <Box>
                <Box sx={voterStyles.searchContainer}>
                    <SearchComponent
                        name="SSN Number"
                        input={searchParams.ssnNumber}
                        length={9}
                        onChange={handleSearchChange}
                        onReload={() => {
                            setSearchParams((prev) => ({
                                ...prev,
                                ssnNumber: "",
                            }));
                            setHasSearched(false);
                        }}
                    />
                </Box>
                <TableContainer component={Paper} sx={voterStyles.tableContainer}>
                    <Table sx={voterStyles.table}>
                        <TableHead sx={voterStyles.tableHead}>
                            <TableRow>
                                {columns.map((col) => (
                                    <TableCell key={col} align="left">
                                        <b>{col}</b>
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {voters && voters.length > 0 ? (
                                voters.map((voter: any) => (
                                    <TableRow key={voter.ssnNumber}>
                                        <TableCell>{voter.statusId}</TableCell>
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
                                            <Menu
                                                id={`voter-menu-${voter.ssnNumber}`}
                                                anchorEl={anchorEl}
                                                open={menuOpen && selectedVoter?.ssnNumber === voter.ssnNumber}
                                            >
                                                <MenuItem onClick={() => handleOpen("view", voter)}>
                                                    <ListItemIcon>
                                                        <VisibilityIcon fontSize="small" />
                                                    </ListItemIcon>
                                                    View
                                                    <ViewVoter handleClose={handleCloseDialog} voter={selectedVoter} />
                                                </MenuItem>
                                            </Menu>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                <TableRow>
                                    <TableCell colSpan={10} align="center">
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
                                          No Voter Found!
                                         </Typography>
                                                            {hasSearched && (
                                            <Box>
                                              
                                                <Button variant="contained" color="primary" onClick={() => handleOpen('add', null)}>
                                                    Add Voter
                                                </Button>
                                                <ModelComponent open={open} handleClose={handleClose}>
                                                    <VoterForm ssnNumber={searchParams.ssnNumber} />
                                                </ModelComponent>
                                            </Box>
                                        )}
                                        </Box>
                                    </TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    component="div"
                    count={totalElements}
                    page={searchParams.page}
                    onPageChange={handlePageChange}
                    rowsPerPage={searchParams.size}
                    onRowsPerPageChange={handleRowsPerPageChange}
                    rowsPerPageOptions={[5, 10, 25, 50, 100]}
                />
            </Box>
        </>
    );
};

export default AddVoter;














