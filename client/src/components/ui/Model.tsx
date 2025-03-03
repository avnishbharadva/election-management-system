import React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import { StyledButton } from "../../style/CommanStyle";
import { ModelBox } from "../../style/ModelCss";
import { Backdrop } from "@mui/material";

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
  const getButtonProps = () => {
    switch (actionType) {
      case "add":
        return { };
      case "edit":
        return { actionType:"edit"};
      default:
        return { };
    }
  };

  const { label, icon } = getButtonProps();

  return (
    <Modal keepMounted  open={open}  aria-labelledby="modal-title" BackdropProps={{
      onClick: (e) => e.stopPropagation(), 
    }}>
      <ModelBox>
        {/* Close Icon */}
        <Box
          sx={{
            position: "absolute",
            top: "10px",
            right: "10px",
          }}
        >
          <IconButton onClick={handleClose}>
            <CloseIcon />
          </IconButton>
        </Box>

        {/* Modal Content */}
        <h2>
          {icon} {label}
        </h2>
        {React.isValidElement(children) &&
          React.cloneElement(children, { handleClose, candidate } as any)}
      </ModelBox>
    </Modal>
  );
};

export default Model;
