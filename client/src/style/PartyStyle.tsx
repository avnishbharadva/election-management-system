import { Box, styled } from "@mui/material";



// partyForm

export const Dropzone = styled(Box)({
    flex: "1",
    display: "flex",
    flexDirection: "column",
    alignItems: "center", 
    padding: "40px",
   
    borderRadius: "2px", 
    border: "2px dashed #007bff" ,
     
    backgroundColor: "#f0f8ff", 
    color: "#bdbdbd",
    outline: "none",
    transition: "border .24s ease-in-out",
    "&:hover": {
        backgroundColor: "#e0f0ff",
        borderColor: "#0056b3",
      },
});
export const Imagepreview = styled(Box)({
    display: "flex",
    flexDirection: "column",
    alignItems: "center", 
    justifyContent:"center"
})