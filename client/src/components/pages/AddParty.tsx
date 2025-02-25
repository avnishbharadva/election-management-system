
// import { TableContainer, Table, TableHead, TableRow, TableCell, Paper } from '@mui/material'
// import Model from '../ui/Model'
// import PartyForm from '../ui/PartyForm'
// // import Sidebar from '../ui/Sidebar'
// import { usePartyListQuery } from '../../store/feature/party/partyAction';

// const AddParty = () => {
//   const {data ,isError ,isLoading } = usePartyListQuery({})

//   return (
//    <>
//    <Model open={true} handleClose={() => {}} actionType="add" handleOpen={() => {}}>
//     <PartyForm/>
//    </Model>
//    <TableContainer component={Paper} sx={{marginTop: 2}}>
//     <Table sx={{ minWidth: "max-content", tableLayout: "auto", whiteSpace: "nowrap" }}>
//       <TableHead>
//         <TableRow>
//           <TableCell><b>PartyName</b></TableCell>
//           <TableCell><b>PartyAbbreviation</b></TableCell>
//           <TableCell><b>PartySymbol</b></TableCell>
//          <TableCell><b>PartyFoundationYear</b></TableCell> 
//          <TableCell><b>PartyWebSite</b></TableCell> 
//          <TableCell><b>HeadQuarters</b></TableCell> 
//          <TableCell><b>FounderName</b></TableCell> 

//         </TableRow>
//       </TableHead>
//     </Table>
//    </TableContainer>
//    </>
//   )
// }

// export default AddParty





import React, { useState } from 'react';
import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, TableBody, CircularProgress, Typography } from '@mui/material';
import Model from '../ui/Model';
import PartyForm from '../ui/PartyForm';
import { usePartyListQuery } from '../../store/feature/party/partyAction';

const AddParty = () => {
  const { data, isError, isLoading } = usePartyListQuery({});
  const [isModelOpen, setIsModelOpen] = useState(true); // Control Model open state

  const handleCloseModel = () => {
    setIsModelOpen(false);
  };

  return (
    <>
      <Model open={isModelOpen} handleClose={handleCloseModel} actionType="add" handleOpen={() => setIsModelOpen(true)}>
        <PartyForm />
      </Model>
      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table sx={{ minWidth: "max-content", tableLayout: "auto", whiteSpace: "nowrap" }}>
          <TableHead>
            <TableRow>
              <TableCell><b>PartyName</b></TableCell>
              <TableCell><b>PartyAbbreviation</b></TableCell>
              <TableCell><b>PartySymbol</b></TableCell>
              <TableCell><b>PartyFoundationYear</b></TableCell>
              <TableCell><b>PartyWebSite</b></TableCell>
              <TableCell><b>HeadQuarters</b></TableCell>
              <TableCell><b>FounderName</b></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {isLoading ? (
              <TableRow>
                <TableCell colSpan={7} align="center">
                  <CircularProgress />
                </TableCell>
              </TableRow>
            ) : isError ? (
              <TableRow>
                <TableCell colSpan={7} align="center">
                  <Typography color="error">Error loading data.</Typography>
                </TableCell>
              </TableRow>
            ) : data && data.length > 0 ? (
              data.map((party: any) => (
                <TableRow key={party.id}>
                  <TableCell>{party.partyName}</TableCell>
                  <TableCell>{party.partyAbbreviation}</TableCell>
                  <TableCell>{party.partySymbol}</TableCell>
                  <TableCell>{party.partyFoundationYear}</TableCell>
                  <TableCell>{party.partyWebSite}</TableCell>
                  <TableCell>{party.headQuarters}</TableCell>
                  <TableCell>{party.founderName}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={7} align="center">
                  <Typography>No party data available.</Typography>
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default AddParty;