import React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import { ModelBox } from "../../style/ModelCss";
 
interface ModalProps {
  open: boolean;
  handleClose: () => void;
  children: React.ReactNode;
}
 
const Model: React.FC<ModalProps> = ({
  open,
  handleClose,
  children,
}) => {
 
  return (
    <Modal keepMounted open={open} aria-labelledby="modal-title" >
      <ModelBox>
        {/* Close Icon */}
        <Box
          sx={{
            position: "absolute",
            top: "17px",
            right: "19px",
            zIndex: "99"
          }}
        >
          <IconButton onClick={handleClose}>
            <CloseIcon />
          </IconButton>
        </Box>
        {React.isValidElement(children) &&
          React.cloneElement(children, { handleClose } as any)}
      </ModelBox>
    </Modal>
  );
};
 
export default Model;
 




