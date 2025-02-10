import { Box, styled } from "@mui/material";

export const ModelBox = styled(Box)({
     position: "absolute",
        top: "49%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "auto", 
        maxWidth: "auto", 
        backgroundColor: "white",
        borderRadius: "10px",
        // boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)", // Modern shadow
        overflowY: "auto", // Allow vertical scrolling
        maxHeight: "80vh", // Prevent modal from exceeding the viewport height
        padding: "2rem",
})

