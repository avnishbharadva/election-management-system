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
  open: boolean;
  handleClose: () => void;
  actionType: "add" | "edit";
  selectedCandidate?: any;
  children: React.ReactNode;
}

const Model: React.FC<ModelProps> = ({ open, handleClose, actionType, selectedCandidate, children }) => {
  const getButtonProps = () => {
    switch (actionType) {
      case "add":
        return { label: "Add New Candidate", icon: <PersonAddIcon /> };
      case "edit":
        return { label: "Edit Candidate", icon: <EditIcon /> };
      default:
        return { label: "Add New Candidate", icon: <PersonAddIcon /> };
    }
  };

  const { label, icon } = getButtonProps();

  return (
    <Modal keepMounted open={open} onClose={handleClose} aria-labelledby="modal-title">
      <Box sx={style}>
        <h2>{icon} {label}</h2>
        {React.isValidElement(children) && React.cloneElement(children, { handleClose, selectedCandidate } as any)}
      </Box>
    </Modal>
  );
};

export default Model;
