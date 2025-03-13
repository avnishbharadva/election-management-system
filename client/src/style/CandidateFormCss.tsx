import { Box, Divider, Typography } from "@mui/material";
import { styled } from "@mui/system";

export const DropzoneContainer = styled(Box)({
    width: "378px",
    height: "160px",
    border: "2px dashed #007bff",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    textAlign: "center",
    cursor: "pointer",
    borderRadius: "12px",
    backgroundColor: "#f0f8ff",
    transition: "all 0.3s ease-in-out",
    "&:hover": {
      backgroundColor: "#e0f0ff",
      borderColor: "#0056b3",
    },
  });
  
export const Section = styled(Box)({
<<<<<<< HEAD
    // marginBottom: "3.5rem",
=======
      gap: '2rem'
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
  });
  
export const Row = styled(Box)({
    display: "flex",
    alignItems: "center",
    gap: "1.5rem",
    marginBottom: "1rem",
  });
  
export  const DocumentContainer = styled(Box)({
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    gap: "2rem",
  });
  
export const FlexCenter = styled(Section)({
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    gap:'2rem'
  });
  
export const DividerStyle = styled(Divider)({
    marginBottom: "1rem",
  });

export const Title = styled(Typography)({
<<<<<<< HEAD
    color:'#003153'
})
=======
    color:'#003153',
});

export const Heading = styled(Typography)({
  color:'#003153',
  position: "sticky",
  top: 0,
  background: "white",
  padding: "16px",
  borderBottom: "1px solid #ccc",
  zIndex: 10,
  fontWeight: "bold",
  fontSize: "20px",
});

export const ModalFooter = styled(Box)({
  position: "sticky",
  bottom: 0,
  background: "white",
  padding: "16px",
  borderTop: "1px solid #ccc",
  zIndex: 10,
  display: "flex",
  justifyContent: "center",
  gap:'2rem'
});

export const FormContent = styled(Box)({
  flex: 1,
  overflowY: "auto",
  padding: "16px",
  maxHeight: "50vh", // Control scrolling area
});
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
