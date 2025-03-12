import { Box, styled, Typography } from "@mui/material";

export const LoginBox = styled(Box)({
  position: "relative",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  flexWrap: "wrap", // Allow elements to wrap naturally
  width: "100%",
  height: "100vh",
  // border:"1px solid black"
});

export const LoginImg = styled("img")({
  width: "100%",
  height: "100vh",
  objectFit: "cover",
  position: "absolute",
  top: 0,
  left: 0,
  zIndex: 0,
});

export const FormCard = styled(Box)({
  borderRadius: "16px",
  // height:"500px",
  background: "rgba(255, 255, 255)",
  boxShadow: "0 4px 30px rgba(0, 0, 0, 0.1)",
  backdropFilter: "blur(5px)",
  border: "1px solid rgba(255, 255, 255, 0.3)",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  flexDirection: "column",
  zIndex: 1,
  padding: "40px",
  margin: "20px",
  flex: "1 1 400px", // Flex-grow, flex-shrink, and base size
  maxWidth: "400px", // Prevents expanding too wide
});

export const LoginForm = styled("form")({
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  flexDirection: "column",
  width: "100%",
});

export const LoginField = styled(Box)({
  margin: "10px",
  width: "100%",
  maxWidth: "320px",
  backgroundColor: "#fff",
  borderRadius: "5px",
});

export const ErrorMsg = styled("p")({
  color: "red",
  fontSize: "0.875rem",
  marginTop: "5px",
});
export const LoginTitle = styled(Typography)({
  marginBottom: "8px", // Equivalent to 1 spacing unit
  fontWeight: "bold",
  color:"var(--mui)",
  fontSize:"20px"
});