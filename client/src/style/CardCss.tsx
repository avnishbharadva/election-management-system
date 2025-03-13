import { Card, Typography } from "@mui/material";
import { Box, styled } from "@mui/system";
import { BarChart } from "@mui/x-charts";

export const Container = styled(Box)({
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))",
  gridGap: "1rem",
  width: "100%",
  height: "100%",
  alignItems: "start", 
});

export const CardWrapper = styled(Box)({
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
  gap: "1rem",
  width: "100%",
});

export const ChartWrapper = styled(Box)({
  backgroundColor: "white",
  borderRadius: "15px",
  boxShadow: "0px 6px 10px rgba(0, 0, 0, 0.2)",
  padding: "1rem",
  height: "auto",
});

export const StyledCard = styled(Card)({
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
  height: "160px",
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
  height: "300px",
});
