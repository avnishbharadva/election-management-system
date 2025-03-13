import React, { useState } from 'react';
import {
  Typography,
  IconButton,
  Menu,
  MenuItem,
  ListItemIcon,
  Box,
  Button,
} from '@mui/material';
import { Edit, Visibility } from '@mui/icons-material';
import ModelComponent from '../ui/ModelComponent';
import PartyForm from '../../Party/PartyForm';
import { usePartyListQuery } from '../../store/feature/party/partyAction';
import ViewParty from '../../Party/ViewParty';
// import { tableStyles } from '../../style/PartyStyle';
import Pagination from '../ui/Pagination';
import TableComponent from '../ui/TableComponent';
import { AddButtonContainer } from '../../style/PartyStyle';
 
interface Party {
  partyId: string;
  partyName: string;
  partyAbbreviation: string;
  image: string;
  partyFoundationYear: string;
  partyWebSite: string;
  headQuarters: string;
  partyFounderName: string;
  partySymbol: string;
  partyLeaderName: string;
}
 
const partyHeader = [
  { id: "partyName", label: "Party Name" },
  { id: "partyAbbreviation", label: "Abbreviation" },
  { id: "partyFoundationYear", label: "Foundation Year" },
  { id: "partyWebSite", label: "Website" },
  { id: "headQuarters", label: "Headquarters" },
  // { id: "partyFounderName", label: "Founder Name" },
  {id: "partyLeaderName", label: "Leader Name"},
  { id: "actions", label: "Actions" },
];
 
const AddParty = () => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const { data, isError, isLoading } = usePartyListQuery({});
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [selectedParty, setSelectedParty] = useState<Party | null>(null);
  const [action, setAction] = useState({
    register: false,
    edit: false,
    view: false,
  });
 
  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, party: Party) => {
    console.log(party)
    setAnchorEl(event.currentTarget);
    setSelectedParty(party);
  };
 
  const handleMenuClose = () => {
    setAnchorEl(null)
  };
 
  const handleAction = (actionType: string, party?: Party) => {
 
    console.log(actionType, party);
    switch (actionType) {
      case "register":
        setSelectedParty(null);
        setAction({ register: true, edit: false, view: false });
        handleMenuClose();
        break;
      case "edit":
        party &&setSelectedParty(party);
        setAction({ register: false, edit: true, view: false });
        handleMenuClose();
        break;
      case "view":
        party && setSelectedParty(party);
        setAction({ register: false, edit: false, view: true });
        handleMenuClose();
        break;
      default:
        console.log('default');
        break;
    }
   
  };
  console.log("Party data", data?.data);
 
  const totalElements = data?.data.length || 0;
  const visibleParties = data?.data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) || [];
 
  const partys = visibleParties.map((party: Party) => ({
    partyName: party.partyName,
    partyAbbreviation: party.partyAbbreviation,
    partyFoundationYear: party.partyFoundationYear,
    partyWebSite: party.partyWebSite,
    headQuarters: party.headQuarters,
    // partyFounderName: party.partyFounderName,
    partyLeaderName: party.partyLeaderName,
    actions: (
      <>
        <IconButton onClick={(e) => handleMenuClick(e, party)} color="primary">
          ...
        </IconButton>
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl) && selectedParty?.partyId === party?.partyId} // Adjust to party.id or party.partyId based on your data
          onClose={handleMenuClose}
        >
          <MenuItem onClick={() => handleAction("edit", party)}>
            <ListItemIcon>
              <Edit fontSize="small" />
            </ListItemIcon>
            Edit
          </MenuItem>
          <MenuItem onClick={() => handleAction("view", party)}>
            <ListItemIcon>
              <Visibility fontSize="small" />
            </ListItemIcon>
            View
          </MenuItem>
        </Menu>
      </>
    ),
  }));
 
  return (
    <Box>
      <AddButtonContainer>
        <Button onClick={() => handleAction('register')} variant="contained">
          Add Party
        </Button>
      </AddButtonContainer>
 
      <TableComponent
        headers={partyHeader}
        rows={partys}
        loading={isLoading}
        error={isError}
        noDataFound={<Typography>No party data available.</Typography>}
      >
        <Pagination
          totalElements={totalElements}
          setPage={setPage}
          page={page}
          setRowsPerPage={setRowsPerPage}
          rowsPerPage={rowsPerPage}
        />
      </TableComponent>
 
      <ModelComponent
        open={ action.register}
        handleClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
      >
        <PartyForm
          onClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
        />
      </ModelComponent>
      <ModelComponent
        open={ action.edit}
        handleClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
      >
        <PartyForm
          onClose={() => setAction((prevAction) => ({ ...prevAction, edit: false, register: false }))}
          party={selectedParty}
        />
      </ModelComponent>
      <ViewParty
        party={selectedParty}
        open={action.view}
        handleClose={() => setAction((prev) => ({ ...prev, view: false }))}
      />
    </Box>
  );
};
export default AddParty;







// import React, { useState } from 'react';
// import {
//   TableContainer,
//   Table,
//   TableHead,
//   TableRow,
//   TableCell,
//   Paper,
//   TableBody,
//   CircularProgress,
//   Typography,
//   IconButton,
//   Menu,
//   MenuItem,
//   ListItemIcon,
//   Box,
//   Button,
//   // TablePagination,
// } from '@mui/material';
// import { Edit, Visibility } from '@mui/icons-material';
// // import Model from '../ui/Model';
// import Model from '../ui/Model';
// import PartyForm from '../../Party/PartyForm';
// import { usePartyListQuery, useRegisterPartyMutation } from '../../store/feature/party/partyAction';
// import ViewParty from '../../Party/ViewParty';
// import { AddButtonContainer, TableCellSticky, TableMain, TableContainerMain } from '../../style/PartyStyle'; // Import table styles
// //  const PartyForm = React.lazy(()=>import '../../Party/PartyForm';)
// import Pagination from '../ui/Pagination';
// interface Party {
//   id: string;
//   partyName: string;
//   partyAbbreviation: string;
//   image: string;
//   partyFoundationYear: string;
//   partyWebSite: string;
//   headQuarters: string;
//   founderName: string;
// }

// const AddParty = () => {
//   const [page, setPage] = useState(0);
//   const [rowsPerPage, setRowsPerPage] = useState(5);
//   const { data, isError, isLoading, refetch } = usePartyListQuery({});
//   const [addParty] = useRegisterPartyMutation();
//   const [isModelOpen, setIsModelOpen] = useState(false);
//   const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
//   const [editModel, setEditModel] = useState(false);
//   const open = Boolean(anchorEl);
//   const [selectedPartyId, setSelectedPartyId] = useState<string | null>(null);
//   const [viewPartyOpen, setViewPartyOpen] = useState(false);
//   const [selectedParty, setSelectedParty] = useState<Party | null>(null);

//   const handleCloseModel = () => {
//     setIsModelOpen(false); 
//     setSelectedParty(null);
//   };
 
//   const handleClick = (event: React.MouseEvent<HTMLElement>, party: any) => {
//     setAnchorEl(event.currentTarget);
//     setSelectedPartyId(party.partyId);
//     setSelectedParty(party);
//   };
 
//   const handleClose = () => {
//     setAnchorEl(null);
//     setSelectedPartyId(null);
//   };
 
//   const handleView = () => {
//     const party = data?.find((p: any) => p.partyId === selectedPartyId);
//     if (party) {
//       setSelectedParty(party);
//       setViewPartyOpen(true);
//     }
//     handleClose();
//   };

//   const handleEdit = () => {
//     if (selectedParty) {
//       setSelectedParty(selectedParty);
//       setEditModel(true);
//     }
//     handleClose();
//   };
 
//   const handleCloseViewParty = () => {
//     setViewPartyOpen(false);
//     setSelectedParty(null);
//   };
 
//   const handlePartySubmit = async (partyData: any, image: string | null) => {
//     try {
//       await addParty({ post: partyData, img: image }).unwrap();
//       refetch();
//     } catch (error) {
//       console.error("Error adding party:", error);
//     }
//   };

//   const handleCloseEditModel = (data) => {

//     setEditModel(false);
//     setSelectedParty(null);
// }
//  console.log("Party data",data?.data);
//   const totalElements =data?.length || 0;
//   console.log(totalElements);

//   const visibleParties =  Array.isArray(data) ?data?.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage) : [];
//   if (!Array.isArray(data) && data !== undefined && data !== null) {
//     console.error("Party data is not an array:", data);
//     // Display an error message to the user if needed
// }
//   return (
//     <Box>
//       <AddButtonContainer>
//       <Button onClick={() => setIsModelOpen(true)} variant='contained'>Add Party</Button>
//         <Model
//           open={isModelOpen}
//           handleClose={handleCloseModel}
//           onClose={handleCloseModel}
       
//         >
//           <PartyForm onPartySubmit={handlePartySubmit} />
//         </Model>
//       </AddButtonContainer>

//       <Paper>
//         <TableContainerMain>
//         <TableMain>
//           <TableHead>
//             <TableRow>
//               <TableCellSticky><b>PartyName</b></TableCellSticky>
//               <TableCellSticky><b>Abbreviation</b></TableCellSticky>
//               <TableCellSticky><b>FoundationYear</b></TableCellSticky>
//               <TableCellSticky><b>PartyWebsite</b></TableCellSticky>
//               <TableCellSticky><b>HeadQuarters</b></TableCellSticky>
//               <TableCellSticky><b>FounderName</b></TableCellSticky>
//               <TableCellSticky><b>Actions</b></TableCellSticky>
//             </TableRow>
//           </TableHead>
//           <TableBody>
//             {isLoading ? (
//               <TableRow>
//                 <TableCell colSpan={7} align="center">
//                   <CircularProgress />
//                 </TableCell>
//               </TableRow>
//             ) : isError ? (
//               <TableRow>
//                 <TableCell colSpan={7} align="center">
//                   <Typography color="error">Error loading data.</Typography>
//                 </TableCell>
//               </TableRow>
//             ) : visibleParties.length > 0 ? (
//               visibleParties.map((party: any) => (
//                 <TableRow key={party.id}>
//                   <TableCell>{party.partyName}</TableCell>
//                   <TableCell>{party.partyAbbreviation}</TableCell>
//                   <TableCell>{party.partyFoundationYear}</TableCell>
//                   <TableCell>{party.partyWebSite}</TableCell>
//                   <TableCell>{party.headQuarters}</TableCell>
//                   <TableCell>{party.founderName}</TableCell>
//                   <TableCell>
//                     <IconButton
//                       aria-label="more"
//                       aria-controls={`party-menu-${party.partyId}`}
//                       aria-haspopup="true"
//                       onClick={(event) => handleClick(event, party) }
//                       color="primary"
//                     >
//                       ...
//                     </IconButton>
//                     <Menu
//                       id={`party-menu-${party.partyId}`}
//                       anchorEl={anchorEl}
//                       open={open && selectedPartyId === party.partyId}
//                       // onClose={handleClose}
//                     >
//                       <MenuItem onClick={handleEdit}>
//                         <ListItemIcon>
//                           <Edit fontSize='small' />
//                         </ListItemIcon>
//                         Edit
//                       </MenuItem>

//                       <MenuItem onClick={handleView}>
//                         <ListItemIcon>
//                           <Visibility fontSize="small" />
//                         </ListItemIcon>
//                         View
//                       </MenuItem>
//                     </Menu>
//                   </TableCell>
//                 </TableRow>
//               ))
//             ) : (
//               <TableRow>
//                 <TableCell colSpan={7} align="center">
//                   <Typography>No party data available.</Typography>
//                 </TableCell>
//               </TableRow>
//             )}
//           </TableBody>
//         </TableMain>
//       </TableContainerMain>
//       </Paper>
     
//       {/* <TablePagination
//         rowsPerPageOptions={[5, 10, 25]}
//         component="div"
//         count={data?.length || 0}
//         rowsPerPage={rowsPerPage}
//         page={page}
//         onPageChange={handleChangePage}
//         onRowsPerPageChange={handleChangeRowsPerPage}
//       /> */}


//       <Pagination 
//       totalElements={totalElements}
//       setPage={setPage}
//       page={page}
//       setRowsPerPage={setRowsPerPage}
//       rowsPerPage={rowsPerPage}
//       />



//        <Model open={editModel} handleClose={() => setEditModel(false)} >
//       <PartyForm
//       onClose={handleCloseEditModel}
//         onPartySubmit={handlePartySubmit}
//         actionType="edit"
//         party={selectedParty}
//       />
//     </Model>
//       <ViewParty party={selectedParty} open={viewPartyOpen} handleClose={handleCloseViewParty} />
//     </Box>
//   );
// };
 
// export default AddParty;