import { Box, styled } from "@mui/material";

export const ModelBox = styled(Box)({
        width: "auto",
        maxWidth: "90vw", // Limits modal width
        backgroundColor: "white",
        borderRadius: "10px",
        overflow: "hidden", // Prevent extra scrollbars
        maxHeight: "80vh", // Limits height
        padding: "2rem",
      });
      
   export  const ModalHeader = styled(Box)({
        backgroundColor: '#1976d2',
        color: 'white',
        textAlign: 'center',
        fontWeight: 'bold',
        padding: '16px',
        fontSize: '1.5rem',
      });