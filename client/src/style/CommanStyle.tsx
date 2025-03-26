import { Box, Button, DialogContent, DialogTitle, styled, Typography } from "@mui/material";
export const FlexBoxColumn = styled(Box)({
display:"flex",
justifyContent:"center",
alignItems:"center",
flexDirection:"column"
})

export const FlexBoxCenter = styled(Box)({
  display:"flex",
  justifyContent:"center",
  alignItems:"center",
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

 export const StyledBox = styled(Box)({
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
    padding: "10px",
    borderRadius: "8px",
  });

  export const ImageWarpper = styled(Box)({
    display: "flex", 
    flexDirection: "column", 
    alignItems: "center"
  })
  
  export const ImageBox = styled("img")({
    width: "150px",
    height: "150px",
    borderRadius: "8px",
    boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
  })

  export const ImageSign = styled("img")({
    width: "200px",
    height: "80px",
    borderRadius: "4px",
    boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
  })

  export const Title = styled(DialogTitle)({
    backgroundColor: "#1976d2",
    color: "white",
    textAlign: "center",
    fontWeight: "bold"
  })

  export const DialogWrapper = styled(DialogContent)({
    padding: "20px", 
    backgroundColor: "#f9f9f9"
  })

  export const DialogContainer = styled(Box)({
    display: "flex", 
    flexDirection: "column", 
    gap: "3rem"
  })
