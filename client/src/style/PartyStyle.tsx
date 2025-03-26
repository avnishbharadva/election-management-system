import { Box, TableCell, Table, IconButton } from "@mui/material";
import { styled } from "@mui/system";

//AddParty.tsx
export const AddButtonContainer = styled(Box)({
     display: 'flex',
     justifyContent: 'flex-end',
     marginBottom: '20px',
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
     gap: '16px', 
     flex: 1, 
     overflowY: "auto", 
     maxHeight: "60vh"
})

export const Row = styled(Box)({
  marginTop:'10px', 
  display: 'flex',
  flexDirection: 'row',
  gap: '16px',
 flexWrap: 'wrap',
'res': {
    flex: '1 1 calc(50% - 8px)', 
    minWidth: '250px',
  },
  '@media (max-width: 600px)': { 
    flexDirection: 'column', 
    'res': {
      flexBasis: '100%',
      minWidth: 'auto', 
    },
  },
})

export const GridContainer = styled(Box)({
  marginTop:'10px',
  display: 'grid',
  gridTemplateColumns: ' 1fr',
  gap: '16px',
'@media (min-width: 600px)': { 
    gridTemplateColumns: '1fr 1fr', 
  },
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
'@media (max-width: 400px)': { 
  flexDirection: 'column', 
  alignItems: 'center', 
  gap: '8px', 
},
})

export const CloseIconButton = styled(IconButton)({
    position: "absolute", 
    right: '0rem', 
    top:'-1rem'
})

export const PartyFormContainer = styled(Box)({
  position:"relative",
  display:"flex",
  alignItems:"center",
  justifyContent:"center",
  marginBottom:"1rem"
})

