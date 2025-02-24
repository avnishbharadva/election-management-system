import { Box, styled } from "@mui/material";

export const LoginBox = styled(Box)({
  position: "relative",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  width: "100%",
  height: "100vh",
});

export const LoginImg = styled("img")({
  width: "100%",
  height: "100vh",
  position: "absolute",
  top: 0,
  left: 0,
  zIndex: 0,
});

export const FormCard = styled(Box)({
  borderRadius: "16px",
  background: "rgba(255, 255, 255)",
  boxShadow: "0 4px 30px rgba(0, 0, 0, 0.1)",
  backdropFilter: "blur(5px)",
  border: "1px solid rgba(255, 255, 255, 0.3)",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  flexDirection: "column",
});

export const LoginForm = styled("form")({
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  flexDirection: "column",
  width: "400px",
  padding: "40px",
});

export const LoginField = styled(Box)({
  margin: "10px",
  width: "320px",
});

export const ErrorMsg = styled("p")({
  color: "red",
  fontSize: "0.875rem",
  marginTop: "5px",
});