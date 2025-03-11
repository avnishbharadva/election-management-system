import React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import { ModelBox } from "../../style/ModelCss";

interface ModelProps {
  open: boolean;
  handleClose: () => void;
  actionType: "add" | "edit";
  candidate?: any;
  children: React.ReactNode;
}

const Model: React.FC<ModelProps> = ({
  open,
  handleClose,
  actionType,
  candidate,
  children,
}) => {
  return (
    <Modal
      keepMounted
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-title"
      BackdropProps={{
        onClick: (e) => e.stopPropagation(), // Prevent closing modal on backdrop click
      }}
    >
      <ModelBox>
        {/* Close Icon */}
        <Box
          sx={{
            position: "absolute",
            top: "10px",
            right: "10px",
          }}
        >
          
        </Box>

        {/* Modal Content */}
        {/* <h2>{actionType === "add" ? "Add Candidate" : "Edit Candidate"}</h2> */}
        {React.isValidElement(children) &&
          React.cloneElement(children, { handleClose, candidate })}
      </ModelBox>
    </Modal>
  );
};

export default Model;
