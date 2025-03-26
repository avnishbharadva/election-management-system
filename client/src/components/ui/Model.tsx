import React from "react";
import Modal from "@mui/material/Modal";
import { ModelBox } from "../../style/ModelCss";
 
interface ModalProps {
  open: boolean;
  handleclose: () => void;
  children: React.ReactNode;
}
 
const Model: React.FC<ModalProps> = ({ open, handleclose, children }) => {
  return (
    <Modal
      keepMounted
      open={open}
      aria-labelledby="modal-title"
      BackdropProps={{
        onClick: (e) => e.stopPropagation(),
      }}
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <ModelBox>
        {React.isValidElement(children) &&
          React.cloneElement(children, { handleclose } as any)}
      </ModelBox>
    </Modal>
  );
};
 
export default Model;