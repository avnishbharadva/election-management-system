import { Card, Typography } from "@mui/material";
import { Box, styled } from "@mui/system";
import { BarChart } from "@mui/x-charts";

export const Container = styled(Box)(({ theme }) => ({
  display: "flex",
  width: "100%",
  height: "100%",

  
  [theme.breakpoints.down("md")]: {
    flexDirection: "column",
    // justifyContent: "center",
  },
}));


export const CardWrapper = styled(Box)(({ theme }) => ({
  display: "grid",
  
  gap: "1rem",
  width: "100%",

  
  [theme.breakpoints.up("md")]: {
    gridTemplateColumns: "repeat(2, 1fr)",
    width: "50%" 
  },
}));

export const ChartWrapper = styled(Box)(({ theme }) => ({
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  marginTop: "1rem", 
  backgroundColor: "white",
  borderRadius: "15px",
  boxShadow: "0px 6px 10px rgba(0, 0, 0, 0.2)",

  
  [theme.breakpoints.up("md")]: {
    marginTop: 0, 
    marginLeft: "1rem", 
    width: "50%",
    height: "auto",
  },
}));

export const StyledCard = styled(Card)({
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
  height: 160,
  textAlign: "center",
  borderRadius: "15px",
  boxShadow: "0px 6px 10px rgba(0, 0, 0, 0.2)",
  transition: "0.3s",
  
});

export const Content = styled(Typography)({
  fontWeight: "bold",
});

export const Graph = styled(BarChart)({
  width: "100%",
  height: 333,
});
