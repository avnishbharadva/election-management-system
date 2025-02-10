import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import React from 'react';

const style = {
    position: "absolute",
    top: "55%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "auto", // Adjust to ensure responsiveness
    maxWidth: "auto", // Ensure it doesn't exceed a certain width
    bgcolor: "background.paper",
    borderRadius: "10px",
    boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)", // Modern shadow
    overflowY: "auto", // Allow vertical scrolling
    maxHeight: "80vh", // Prevent modal from exceeding the viewport height
    padding: "2rem",
};

interface ModelProps {
  children: React.ReactNode; // Accept child components
}

const Model: React.FC<ModelProps> = ({ children }) => {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div>
      <Button onClick={handleOpen}>Add</Button>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Box sx={style}>
          
          <Box sx={{ mt: 2 }}>
          
            {children}
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default Model;
