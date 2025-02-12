import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
import React from 'react';
import { ModelBox } from '../../style/ModelCss';



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
        <ModelBox >
          
          <Box>
          
            {children}
          </Box>
        </ModelBox>
      </Modal>
    </div>
  );
};

export default Model;
