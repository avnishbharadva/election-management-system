import { Table, TableCell, TableContainer, Box, styled } from "@mui/material";

export const TableContainerMain = styled(TableContainer)({
    marginTop:2,
    overflow: 'auto',
    maxHeight: '461px',
    boxShadow: '2px 2px 5px rgba(0, 0, 0, 0.2)',
})

export const TableMain = styled(Table)({
  minWidth: 'max-content',
  tableLayout: 'auto',
  whiteSpace: 'nowrap',
  width:'100%',
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
  maxWidth: '150px',
})