// Styled components
import { Card, Typography } from "@mui/material";
import { Box,styled } from "@mui/system";
import { BarChart } from "@mui/x-charts";

export const Container = styled(Box)({
    display: "flex",
    // minHeight: "100vh",
  });
  
export  const ContentWrapper = styled(Box)({
    flex: 1,
    padding: "1rem",
  });

export const CardContainer = styled(Box)({
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    gap: '3rem',
})


export const HeaderContainer = styled(Box)({
    display: "flex", 
    justifyContent: "space-between"
})

export const Content = styled(Typography)({
    fontWeight: "bold"
})
  
export  const CardsContainerLeft = styled(Box)({
    display: "grid",
    gridTemplateColumns: "1fr 1fr",
    gap: "16px",
    width: "45%",
  });
  
export const StyledCard = styled(Card)({
    width: "100%",
    height: 160,
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    textAlign: "center",
    borderRadius: "15px",
    boxShadow: "0px 6px 10px rgba(0,0,0,0.2)",
    transition: "0.3s",
    "&:hover": { transform: "scale(1.05)" },
  });
  
export const ChartContainer = styled(Box)({
    width: "50%",
    display: "flex",
    justifyContent: "center",
    alignItems:'center',
    backgroundColor:'white',
    borderRadius:'15px',
    boxShadow:'0px 6px 10px rgba(0,0,0,0.2)'
  });
  
export const Graph = styled(BarChart)({
    width:400,
    height:333
})