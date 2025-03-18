import { Box, Button, styled, Typography } from "@mui/material";
export const FlexBoxColumn = styled(Box)({
display:"flex",
justifyContent:"center",
alignItems:"center",
flexDirection:"column"
})



  export const CommanBox = styled(Box)({
    display:"flex",
    minHeight:"100vh"
  })

  export const ContentBox = styled(Box)({
    flex:1,
    padding:"1rem"
  })

 export const StyledButton = styled(Button)({
    
    padding: "0.8rem 2rem",
    textTransform: "none",
    background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
    color: "white",
    "&:hover": {
      background: "linear-gradient(90deg, #007BFF, #0056b3)",
    },
  });

  export const BreadCrumbsName = styled(Typography)({
    color: "#002F57",
    fontWeight: "bold",
    textTransform: "capitalize",
    padding: "6px",
  })
