import { useEffect, useState } from "react";
import {
  Box,
  TextField,
  Typography,
  Button,
  CircularProgress,
  Alert,
  IconButton,
} from "@mui/material";
import { useForm, SubmitHandler } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { addElection, fetchElection, updateElectionById } from "../../store/feature/election/electionApi";
import { AppDispatch } from "../../store/app/store";
import { toast, ToastContainer } from "react-toastify";
import { Row, Section, Title } from "../../style/CandidateFormCss";
import UpdateDialog from "./UpdateDialog";
import CloseIcon from "@mui/icons-material/Close";

type FormValues = {
  electionId: number;
  electionName: string;
  electionType: string;
  electionDate: string;
  electionState: string;
  totalSeats: number;
};

const ElectionForm = ({ selectedElection, closeModal }: any) => {
  const dispatch = useDispatch<AppDispatch>();
  const { loading, error } = useSelector((state: any) => state.election);
  
  const [openUpdateDialog, setOpenUpdateDialog] = useState(false);
  const [originalData, setOriginalData] = useState<FormValues | null>(null);
  const [updatedData, setUpdatedData] = useState<FormValues | null>(null);

  const { register, handleSubmit, reset, setValue } = useForm<FormValues>({
    defaultValues: {
      electionName: "",
      electionType: "State",
      electionDate: "",
      electionState: "New York",
      totalSeats: 1,
    },
  });

  useEffect(() => {
    if (selectedElection) {
      setValue("electionName", selectedElection.electionName);
      setValue("electionType", selectedElection.electionType);
      setValue("electionDate", selectedElection.electionDate);
      setValue("electionState", selectedElection.electionState);
      setValue("totalSeats", selectedElection.totalSeats);

      setOriginalData({ ...selectedElection });
    } else {
      reset();
    }
  }, [selectedElection, setValue, reset]);

  const handleClose = () => {
    closeModal();
  };

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    if (selectedElection) {
      setUpdatedData(data);

      // Check if data is changed before opening dialog
      if (JSON.stringify(data) !== JSON.stringify(originalData)) {
        setOpenUpdateDialog(true); // Open dialog before updating
      } else {
        toast.info("No changes detected");
      }
    } else {
      await dispatch(addElection(data));
      toast.success("Election added successfully!");
      closeModal();
      dispatch(fetchElection({ page: 0, perPage: 5, order: "desc" }));
    }
  };

  const handleConfirmUpdate = async () => {
    if (selectedElection && updatedData) {
      await dispatch(updateElectionById({ electionId: selectedElection.electionId, updatedElection: updatedData }));
      toast.success("Election updated successfully!");
      closeModal();
      dispatch(fetchElection({ page: 0, perPage: 5, order: "desc" }));
    }
    setOpenUpdateDialog(false); // Close the update confirmation dialog
  };

  return (
    <Box sx={{ width: "400px", padding: "20px", backgroundColor: "#fff" }}>

      {loading && <CircularProgress />}
      {error && <Alert severity="error">{error}</Alert>}
      <Box
          position="relative"
          display="flex"
          alignItems="center"
          justifyContent="center"
          marginBottom={"1rem"}
        >
          <Title variant="h5" gutterBottom mt="5px">
            {selectedElection ?  "EDIT ELECTION" : "ADD ELECTION"}
          </Title>
          <IconButton
            onClick={handleClose}
            sx={{ position: "absolute", right: '-1rem', top:'-2rem' }}
          >
            <CloseIcon />
          </IconButton>
        </Box>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Box display="flex" flexDirection="column" gap={2}>
          <Section>
            <Row>
              <TextField fullWidth label="Election Name" {...register("electionName", { required: "Required" })} InputLabelProps={{ shrink: true }}/>
              <TextField fullWidth label="Election Type" defaultValue="State" InputProps={{ readOnly: true }} {...register("electionType")} />
            </Row>
          </Section>

          <Section>
            <Row>
              <TextField type="date" fullWidth label="Election Date" InputLabelProps={{ shrink: true }} {...register("electionDate", { required: "Required" })} />
              <TextField fullWidth label="State" defaultValue="New York" InputProps={{ readOnly: true }} {...register("electionState")} />
            </Row>
          </Section>

          <Section sx={{ width: "10.5rem" }}>
            <TextField
              fullWidth
              label="Total Seats"
              InputLabelProps={{ shrink: true }}
              type="number"
              {...register("totalSeats", {
                required: "Required",
                min: { value: 1, message: "Seats must be at least 1" }
              })}
            />
          </Section>

          <Section sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' , gap:'1rem'}}>
            <Button type="submit" variant="contained" sx={{ mt: 2, bgcolor: "#1976d2", width: '10.5rem' }}>
              {selectedElection ? "Update Election" : "Add Election"}
            </Button>
            <Button variant="contained" onClick={handleClose} sx={{ mt: 2, bgcolor: "#1976d2", width: '10.5rem' }}>
              Cancel
            </Button>
          </Section>
        </Box>
      </form>

      <ToastContainer position="top-right" autoClose={3000} />
      
      {/* Update Confirmation Dialog */}
      {selectedElection && (
        <UpdateDialog 
          open={openUpdateDialog} 
          handleClose={() => setOpenUpdateDialog(false)} // Only close dialog
          handleConfirm={handleConfirmUpdate} 
          originalData={originalData!} // Ensure originalData is defined
          updatedData={updatedData!} // Ensure updatedData is defined
          ignoredKeys={["electionId"]} 
          title="Confirm Election Changes" 
        />
      )}
    </Box>
  );
};

export default ElectionForm;
