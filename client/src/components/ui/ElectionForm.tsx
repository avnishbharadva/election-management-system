import { useEffect, useState } from "react";
import {
  Box,
  TextField,
  CircularProgress,
  Alert,
} from "@mui/material";
import { useForm, SubmitHandler } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { addElection, fetchElection, updateElectionById } from "../../store/feature/election/electionApi";
import { AppDispatch } from "../../store/app/store";
import { toast, ToastContainer } from "react-toastify";
import { Row, Section, Title } from "../../style/CandidateFormCss";
import UpdateDialog from "./UpdateDialog";
import CloseIcon from "@mui/icons-material/Close";
import { FormValues } from "../../store/feature/election/types";
import { CloseIconButton, ElectionButtonSection, ElectionFormContainer, ElectionFormWrapper, StyledButtonEle } from "../../style/ElectionCss";

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
      setOriginalData(null); 
    }
  }, [selectedElection, setValue, reset]);
  
  const handleClose = () => {
    closeModal();
  };

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    if (selectedElection) {
      const { electionId, ...dataWithoutId } = data; 
      setUpdatedData({ ...dataWithoutId, electionId: selectedElection.electionId });
  
      if (JSON.stringify(dataWithoutId) !== JSON.stringify(originalData)) {
        setOpenUpdateDialog(true);
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
      dispatch(fetchElection({ page: 0, perPage: 5, order: "desc" }));
      toast.success("Election updated successfully!");
      closeModal();
    }
    setOpenUpdateDialog(false);
  };
  
  return (
    <ElectionFormWrapper>
      {loading && <CircularProgress />}
      {error && <Alert severity="error">{error}</Alert>}
      <ElectionFormContainer>
          <Title variant="h5" gutterBottom mt="5px">
            {selectedElection ?  "EDIT ELECTION" : "ADD ELECTION"}
          </Title>
          <CloseIconButton onClick={handleClose}>
            <CloseIcon />
          </CloseIconButton>
        </ElectionFormContainer>
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
          <ElectionButtonSection>
            <StyledButtonEle type="submit" variant="contained">
              {selectedElection ? "Update Election" : "Add Election"}
            </StyledButtonEle>
            <StyledButtonEle variant="contained" onClick={handleClose}>
              Cancel
            </StyledButtonEle>
          </ElectionButtonSection>
        </Box>
      </form>
      <ToastContainer position="top-right" autoClose={3000} />   
      {selectedElection && (
        <UpdateDialog
          open={openUpdateDialog}
          title="Confirm Election Updates"
          originalData={originalData || {}} 
          updatedData={updatedData || {}}  
          handleClose={() => setOpenUpdateDialog(false)}
          handleConfirm={handleConfirmUpdate}
        />
      )}
    </ElectionFormWrapper>
  );
};

export default ElectionForm;
