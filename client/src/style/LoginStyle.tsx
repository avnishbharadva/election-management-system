<<<<<<< HEAD
import { Box, styled } from "@mui/material";
=======
import { Box, styled, Typography } from "@mui/material";
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486

export const LoginBox = styled(Box)({
  position: "relative",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
<<<<<<< HEAD
=======
  flexWrap: "wrap", 
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
  width: "100%",
  height: "100vh",
});

export const LoginImg = styled("img")({
  width: "100%",
  height: "100vh",
<<<<<<< HEAD
=======
  objectFit: "cover",
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
=======
  zIndex: 1,
  padding: "40px",
  margin: "20px",
  flex: "1 1 400px", 
  maxWidth: "400px", 
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
});

export const LoginForm = styled("form")({
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  flexDirection: "column",
<<<<<<< HEAD
  width: "400px",
  padding: "40px",
=======
  width: "100%",
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
});

export const LoginField = styled(Box)({
  margin: "10px",
<<<<<<< HEAD
  width: "320px",
=======
  width: "100%",
  maxWidth: "320px",
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
  backgroundColor: "#fff",
  borderRadius: "5px",
});

export const ErrorMsg = styled("p")({
  color: "red",
  fontSize: "0.875rem",
  marginTop: "5px",
});
<<<<<<< HEAD
// const StyledTextField = styled(TextField)(({ theme }) => ({
//   marginTop: "20px",
//   backgroundColor: "#fff",
//   borderRadius: "5px",
// }));
=======
export const LoginTitle = styled(Typography)({
  marginBottom: "8px", 
  fontWeight: "bold",
  color:"var(--titleColor)",
  fontSize:"20px"
});
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
