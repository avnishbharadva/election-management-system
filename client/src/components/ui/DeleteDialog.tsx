<<<<<<< HEAD
import { useState } from "react";
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from "@mui/material";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { deleteCandidateById, fetchCandidates } from "../../store/feature/candidate/candidateAPI";
import { AppDispatch } from "../../store/app/store";

interface DeleteCandidateDialogProps {
  open: boolean;
  handleClose: () => void;
  candidateId: number;
}

export default function DeleteCandidateDialog({ open, handleClose, candidateId }: DeleteCandidateDialogProps) {
  const dispatch = useDispatch<AppDispatch>();
  const [loading, setLoading] = useState(false);

  const handleDeleteCandidate = async () => {
    setLoading(true);
    try {
      await dispatch(deleteCandidateById(candidateId)).unwrap();
      dispatch(fetchCandidates({ page: 0, perPage: 5 }));
      // toast.success("Candidate deleted successfully");
      handleClose();
    } catch (error) {
      console.error("Error deleting candidate:", error);
      dispatch(fetchCandidates({ page: 0, perPage: 5 }));
      handleClose();
      toast.error("Failed to delete candidate");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Confirm Deletion</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Are you sure you want to delete this candidate? This action cannot be undone.
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="secondary" disabled={loading}>
          Cancel
        </Button>
        <Button onClick={handleDeleteCandidate} color="error" disabled={loading}>
          {loading ? "Deleting..." : "Delete"}
=======
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
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
        </Button>
      </DialogActions>
    </Dialog>
  );
<<<<<<< HEAD
}
=======
};

export default DeleteDialog;
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
