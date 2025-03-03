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
        </Button>
      </DialogActions>
    </Dialog>
  );
}
