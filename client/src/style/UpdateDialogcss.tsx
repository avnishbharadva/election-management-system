import { Box, Dialog, styled } from "@mui/material";
 
export const StyledDialog = styled(Dialog)(({ theme }) => ({
    "& .MuiDialogTitle-root": {
      fontWeight: "bold",
      backgroundColor: theme.palette.grey[200],
      padding: theme.spacing(2),
    },
    "& .MuiDialogContent-root": {
      padding: theme.spacing(3),
      marginTop: "calc(0* 16px)"
    },
    "& .MuiDialogActions-root": {
      padding: theme.spacing(2),
      backgroundColor: theme.palette.grey[100],
    },
  }));
 
export const ChangeBox = styled(Box)(({ theme }) => ({
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: theme.palette.grey[100],
    padding: theme.spacing(1.5),
    borderRadius: "8px",
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
    marginBottom: theme.spacing(1),
  }));
 
export const ImagePreview = styled("img")(({ theme }) => ({
    width: "120px",
    height: "60px",
    borderRadius: "6px",
    objectFit: "cover",
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.15)",
    border: `1px solid ${theme.palette.grey[300]}`,
  }));
 
 