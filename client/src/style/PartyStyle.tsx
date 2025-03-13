import { Box, TableCell, Table } from "@mui/material";
import { styled } from "@mui/system";

//AddParty.tsx
export const AddButtonContainer = styled(Box)({
     display: 'flex',
     justifyContent: 'flex-end',
     marginBottom: 2,
})

export const TableContainerMain = styled(Box)({
   marginTop: 2,
   overflow: 'auto',
   maxHeight: '461px',
})

export const TableMain = styled(Table)({
  minWidth: 'max-content',
  tableLayout: 'auto',
  whiteSpace: 'nowrap',
})

export const TableCellSticky = styled(TableCell)({
  position: 'sticky',
  top: 0,
  background: 'white',
  zIndex: 1,
})


//Partyform.tsx

export const MainHead = styled(Box)({
     marginTop:'20px',
})

export const Form = styled(Box)({
   margin: '20px',
   zindex: 10,
})

export const Container = styled(Box)({
     display: 'flex',
     flexDirection: 'column',
     gap: 2,
     flex: 1, 
     overflowY: "auto", 
     maxHeight: "60vh"
})

export const Row = styled(Box)({
  marginTop:'15px',
  display: 'flex',
  flexDirection: 'row',
  gap: 9
})

export const GridContainer = styled(Box)({
  marginTop:'15px',
  display: 'grid',
  gridTemplateColumns: '1fr 1fr',
  gap: '10px',
})

export const SubmitButtonContainer = styled(Box)({
     display: 'flex',
     alignContent: 'center',
     justifyContent: 'center',
     flexDirection: 'row',
     position: "sticky",
     bottom: 0,
     background: "white",
     padding: "16px",
     borderTop: "1px solid #ccc",
     zIndex: 10,
     gap:'2rem',
})
