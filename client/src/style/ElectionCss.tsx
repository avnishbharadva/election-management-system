import { Box, Button, IconButton, styled } from "@mui/material";

export const FlexBox = styled(Box)({
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "flex-end",
  });


export const ElectionFormWrapper = styled(Box)({
    width: "400px", 
    padding: "20px", 
    backgroundColor: "#fff"
})

export const ElectionFormContainer = styled(Box)({
    position:"relative",
    display:"flex",
    alignItems:"center",
    justifyContent:"center",
    marginBottom:"1rem"
})

export const StyledButtonEle = styled(Button)({
    marginTop: '2px', 
    bgcolor: "#1976d2", 
    width: '10.5rem'
}) 

export const ElectionButtonSection = styled(Box)({
    display: 'flex', 
    alignItems: 'center', 
    justifyContent: 'center' , 
    gap:'1rem'
})

export const CloseIconButton = styled(IconButton)({
    position: "absolute", 
    right: '-1rem', 
    top:'-2rem'
})

