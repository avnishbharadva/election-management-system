import { useEffect } from "react";
import {
  Box,
  TextField,
  Typography,
  Button,
  CircularProgress,
  Alert,
} from "@mui/material";
import { useForm, SubmitHandler } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { addElection, fetchElection, updateElectionById } from "../../store/feature/election/electionApi";
import { resetState } from "../../store/feature/election/electionSlice";
import { AppDispatch } from "../../store/app/store";
import { toast, ToastContainer } from "react-toastify";
import { Row, Section } from "../../style/CandidateFormCss";

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
  const { loading, error, success } = useSelector((state: any) => state.election);

  const { register, handleSubmit, reset, setValue } = useForm<FormValues>({
    defaultValues: {
      electionName: "",
      electionType: "State",
      electionDate: "",
      electionState: "New York",
      totalSeats: 1,
    },
  });

  // Populate form when editing
  useEffect(() => {
    if (selectedElection) {
      setValue("electionName", selectedElection.electionName);
      setValue("electionType", selectedElection.electionType);
      setValue("electionDate", selectedElection.electionDate);
      setValue("electionState", selectedElection.electionState);
      setValue("totalSeats", selectedElection.totalSeats);
    } else {
      reset();
    }
  }, [selectedElection, setValue, reset]);

  useEffect(() => {
    if (success) {
      toast.success(selectedElection ? "Election updated successfully!" : "Election added successfully!");
      closeModal();
      dispatch(fetchElection({ page: 1, perPage: 10, order: "desc" }));
      dispatch(resetState()); 
    }
  }, [success, dispatch, closeModal]);

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    if (selectedElection) {
      await dispatch(updateElectionById({ electionId: selectedElection.electionId, updatedElection: data }));
      closeModal();
      dispatch(fetchElection({ page: 0, perPage: 10, order: "desc" }));

    } else {
      await dispatch(addElection(data));
      closeModal();
      dispatch(fetchElection({ page: 0, perPage: 10, order: "desc" }));
    }
  };

  return (
    <Box sx={{ width: "400px", padding: "20px", backgroundColor: "#fff" }}>
      {loading && <CircularProgress />}
      {error && <Alert severity="error">{error}</Alert>}
      {success && <Alert severity="success">Election saved successfully!</Alert>}

      <Typography align="center" variant="h5" mb={3}>
        {selectedElection ? "Edit Election" : "Add Election"}
      </Typography>

      <form onSubmit={handleSubmit(onSubmit)}>
        <Box display="flex" flexDirection="column" gap={2}>
          <Section>
            <Row>
            <TextField fullWidth label="Election Name" {...register("electionName", { required: "Required" })} />
            <TextField fullWidth label="Election Type" defaultValue="State" InputProps={{ readOnly: true }} {...register("electionType")} />
            </Row>
          </Section>

          <Section>
            <Row>
            <TextField type="date" fullWidth label="Election Date" InputLabelProps={{ shrink: true }} {...register("electionDate", { required: "Required" })} />
            <TextField fullWidth label="State" defaultValue="New York" InputProps={{ readOnly: true }} {...register("electionState")} />
            </Row>  
          </Section>

          <Section sx={{width: "10.5rem"}}>
          <TextField 
            fullWidth 
            label="Total Seats" 
            type="number" 
            {...register("totalSeats", 
              { required: "Required", 
                min: { value: 1, message: "Seats must be at least 1" } 
              })
            } 
            />
          </Section>
          <Section sx={{display:'flex',alignItems:'center', justifyContent:'center'}}>
            <Button type="submit" variant="contained" sx={{ mt: 2, bgcolor: "#1976d2" , width:'10.5rem', }}>
              {selectedElection ? "Update Election" : "Add Election"}
            </Button>
          </Section>
        </Box>
      </form>
      <ToastContainer position="top-right" autoClose={3000} />
    </Box>
  );
};

export default ElectionForm;