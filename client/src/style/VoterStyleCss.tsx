import { Card, CardContent, Divider, Typography} from "@mui/material";
import {Box, TableContainer, Table, TableCell} from '@mui/material';
import { Title } from '../style/CandidateFormCss';
import { styled } from "@mui/system";
 
export const HeaderStyles = styled(Box)({
  margin: '20px',
})
 
export const Head = styled(Title)({
     color:'#003153',
     position: "sticky",
     top: 0,
     background: "white",
     padding: "16px",
     borderBottom: "1px solid #ccc",
     zIndex: 10,
     fontWeight: "bold",
     fontSize: "20px",
})
 
export const Container = styled(Box)({
     display: 'flex',
     flexDirection: 'column',
 
     flex: 1,
     overflowY: "auto",
     maxHeight: "60vh"
})
 
export const FormRow = styled(Box)({    
  display: 'flex',
  alignItems: 'center',
  gap: '1rem',
  marginTop:'20px',
})
 
export const VotingInfo = styled(Box)({
     marginTop: '20px',
})
 
export const FormRowCenter = styled(Box)({
  display: 'flex',
  alignItems: 'center',
  gap: '1rem',
  justifyContent: 'center',
  marginTop: '20px',
})
 
export const FormRowGap = styled(Box)({
    display: 'flex',
    alignItems: 'center',
    gap: '2.5rem',
})
 
export const AddressField = styled(Box)({
     marginTop: '20px',
})
 
export const FormRowWide = styled(Box)({
  display: 'grid',
  gap: '1rem', 
  gridTemplateColumns: 'repeat(3, 1fr)',
  
})
 
export const FormControlLabel = styled(Box)({
     marginTop: '20px',
})
 
export const DividerStyle = styled(Divider)({
   marginTop: '10px',
  marginBottom: '10px',
})
 
export const UploadDocuments = styled(Box)({
  marginTop: '20px',
})
 
export const FormRowCenterGap2 = styled(Box)({
     display: 'flex',
     alignItems: 'center',
     justifyContent: 'center',
     gap: '2rem',
     marginTop: '20px',
     position: 'sticky',
     bottom:0,
     borderTop: '1px solid #ccc',
     zindex:10,
     background: "white",
})
 
export const FormRowCenterGap = styled(Box)({
     display: 'flex',
     alignItems: 'center',
     justifyContent: 'center',
     gap: '2rem',
     marginTop: '20px',
})
 
 

 
export const SearchContainer = styled(Box)({
    display: 'grid',
    gridTemplateColumns: '1fr auto',
    alignItems: 'center',
    gap: 2,
})
 
export const TableContainerMain = styled(TableContainer)({
    marginTop: 2,
    overflow: 'auto',
    maxHeight: '461px',
})
 
export const TableMain = styled(Table)({
  minWidth: 'max-content',
  tableLayout: 'auto',
  whiteSpace: 'nowrap',
 
  
})
 
export const TableHead = styled(Box)({
  position: 'sticky',
  top: 0,
  background: 'white',
  zIndex: 1,
})
 
export const StyledTableDetails = styled(Box)({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
  gap: '16px',
  padding: '24px',
  borderRadius: '16px', 
  backgroundColor: '#f9f9f9',  
});
 
export const TableCellSticky = styled(TableCell)({
  position: 'sticky',
  top: 0,
  background: 'white',
  zIndex: 1,
})
 
  
//ViewVoter
 
export const BoxContainer = styled(Box)({
  display: "flex",
  flexDirection: "column",
  gap: 3,
});
 
export const CardContainer = styled(Card)({
  boxShadow: '2px 2px 5px rgba(0, 0, 0, 0.2)',
  borderRadius: 2
});
 
export const MainContainer = styled(Box)({
  fontWeight: "bold",
  color: "#1976d2",
})
 
export const StyleDivider = styled(Divider)({
  marginBottom: 2,
})
 
export const ImageContent = styled(CardContent)({
  display: "flex",
  flexDirection: "row",
  gap: 3,
  justifyContent: "center",
  alignItems: "center",
})
 
export const TypographyStyle = styled(Box)({
  textAlign: "center",
  color: "#757575"
})
 
export const BoldTypography = styled(Typography)`
  font-weight: bold;
`;

export const CenterBox= styled(Box)({
  display: 'flex',
  justifyContent: 'center',
})