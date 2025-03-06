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
    // marginBottom: "3.5rem",
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
    color:'#003153'
})