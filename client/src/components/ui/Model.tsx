import React from "react";
import Modal from "@mui/material/Modal";
import { ModelBox } from "../../style/ModelCss";

interface ModalProps {
  open: boolean;
  handleClose: () => void;
  children: React.ReactNode;
}

const Model: React.FC<ModalProps> = ({ open, handleClose, children }) => {
  return (
    <Modal
      keepMounted
      open={open}
      aria-labelledby="modal-title"
      BackdropProps={{
        onClick: (e) => e.stopPropagation(), // Prevent closing modal on backdrop click
      }}
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <ModelBox>
        {React.isValidElement(children) &&
          React.cloneElement(children, { handleClose } as any)}
      </ModelBox>
    </Modal>
  );
};

export default Model;
