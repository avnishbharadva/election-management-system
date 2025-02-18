import React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import {StyledButton} from '../../style/CommanStyle';
const style = {
  position: "absolute",
  top: "55%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "auto",
  maxWidth: "auto",
  bgcolor: "background.paper",
  borderRadius: "10px",
  boxShadow: "0px 4px 20px rgba(0, 0, 0, 0.1)",
  overflowY: "auto",
  maxHeight: "80vh",
  padding: "2rem",
};

interface ModelProps {
  children: React.ReactNode;
  open: boolean;
  handleClose: () => void;
  handleOpen: () => void;
  actionType: "add" | "edit";
  selectedCandidate?: any;
}

const Model: React.FC<ModelProps> = ({ children, open, handleClose, handleOpen, actionType }) => {
  const getButtonProps = () => {
    switch (actionType) {
      case "add":
        return { label: "Add", icon: <PersonAddIcon /> };
      case "edit":
        return { label: "Edit", icon: <EditIcon /> };
      default:
        return { label: "Add", icon: <PersonAddIcon /> };
    }
  };

  const { label, icon } = getButtonProps();

  return (
    <>
      {/* Dynamic Action Button */}
      <StyledButton variant="contained" startIcon={icon} onClick={handleOpen}>
        {label}
      </StyledButton>

      {/* Modal */}
      <Modal keepMounted open={open} onClose={handleClose} aria-labelledby="modal-title">
        <Box sx={style}>
          <Box sx={{ mt: 2 }}>{children}</Box>
        </Box>
      </Modal>
    </>
  );
};

export default Model;
