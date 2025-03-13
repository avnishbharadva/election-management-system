import { Box, styled } from "@mui/material";

export const ModelBox = styled(Box)({
<<<<<<< HEAD
     position: "absolute",
        top: "49%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "auto", 
        maxWidth: "auto", 
        backgroundColor: "white",
        borderRadius: "10px",
        // boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)", // Modern shadow
        // overflowY: "auto", // Allow vertical scrolling
        maxHeight: "80vh", // Prevent modal from exceeding the viewport height
        // padding: "1.5rem",
})

=======
        width: "auto",
        maxWidth: "90vw", // Limits modal width
        backgroundColor: "white",
        borderRadius: "10px",
        overflow: "hidden", // Prevent extra scrollbars
        maxHeight: "80vh", // Limits height
        padding: "2rem",
      });
      
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
