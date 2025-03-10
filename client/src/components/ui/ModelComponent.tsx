 
import CloseIcon from "@mui/icons-material/Close";
import { Modal, Box, IconButton } from "@mui/material";
import { PropsWithChildren } from "react";
import { ModelBox } from "../../style/ModelCss";

 
type MOdelProps = PropsWithChildren<{
  open: boolean
  handleClose: () => void
}>
 
const ModelComponent = ({ handleClose, children, open }: MOdelProps) => {
  return (
    <Modal keepMounted open={open} aria-labelledby="modal-title" BackdropProps={{
      onClick: (e) => e.stopPropagation(),
    }}>
      <ModelBox>
        {/* Close Icon */}
        <Box
          sx={{
            position: "absolute",
            top: "10px",
            right: "10px",
            zIndex:99,
          }}
        >
          <IconButton onClick={handleClose}>
            <CloseIcon />
          </IconButton>
        </Box>
 
        {/* Modal Content */}
        <h2>
          {children}
        </h2>
 
      </ModelBox>
    </Modal>
  );
 
 
}
export default ModelComponent;
 