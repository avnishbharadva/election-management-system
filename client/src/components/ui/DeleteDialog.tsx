import React from "react";
import { Dialog, DialogActions, DialogContent, DialogTitle, Button, Typography } from "@mui/material";
 
interface DeleteDialogProps {
  open: boolean;
  handleClose: () => void;
  handleDelete: () => void;
  title?: string;
  message?: string;
}
 
const DeleteDialog: React.FC<DeleteDialogProps> = ({ open, handleClose, handleDelete, title = "Confirm Delete", message = "Are you sure you want to delete this item?" }) => {
  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <Typography>{message}</Typography>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="secondary">
          Cancel
        </Button>
        <Button onClick={handleDelete} color="error" variant="contained">
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
};
export default DeleteDialog;