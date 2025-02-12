import { Box, TextField, Typography } from "@mui/material";
import { Form, StyledButton } from "../../style/CommanStyle";
import { useForm } from "react-hook-form";
import { ErrorMsg } from "../../style/LoginStyle";
type FormValues = {
    election_name: string;
    election_type: string;
    election_date: string;
    state: string;
    total_seats: number;
    status: string;
  };
  const resolver: Resolver<FormValues> = async (values) => {
    const errors: Record<string, object> = {};
  
    if (!values.election_name) {
      errors.election_name = {
        type: "required",
        message: "Election Name is required.",
      };
    }
  
    if (!values.election_type) {
      errors.election_type = {
        type: "required",
        message: "Election Type is required.",
      };
    }
  
    if (!values.election_date) {
      errors.election_date = {
        type: "required",
        message: "Election Date is required.",
      };
    }
  
    if (!values.state) {
      errors.state = {
        type: "required",
        message: "State is required.",
      };
    }
  
    if (!values.total_seats || values.total_seats <= 0) {
      errors.total_seats = {
        type: "required",
        message: "Total Seats must be greater than zero.",
      };
    }
  
    if (!values.status) {
      errors.status = {
        type: "required",
        message: "Status is required.",
      };
    }
  
    return {
      values: Object.keys(errors).length === 0 ? values : {},
      errors,
    };
  };
  
const ElectionForm = () => {
    const {
        register,
        handleSubmit,
        formState: { errors },
      } = useForm<FormValues>({ resolver });
    
      const onSubmit = handleSubmit((data: FormValues) => {
        console.log(data);
      });
  return (
    <Box>
      <Typography align="center" variant="h5" mb="15px">Add Election</Typography>
      <Form onSubmit={onSubmit}>
      <Box display="flex" flexDirection="column" gap={2}>
        <Box display="flex" flexDirection="row" gap={2}>
            <Box>
            <TextField fullWidth label="Election Name" {...register("election_name", { required: true })}/>{errors?.election_name && (
                <ErrorMsg>{errors.election_name.message}</ErrorMsg>
              )}</Box>
              <Box>
            <TextField fullWidth label="Election Type"  {...register("election_type", { required: true })}/>
            {errors?.election_type && (
                <ErrorMsg>{errors.election_type.message}</ErrorMsg>
              )}

            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
            <TextField type="date" sx={{width:"224px"}} InputLabelProps={{ shrink: true }} fullWidth label="Election Date"  {...register("election_date", { required: true })}/>
            {errors?.election_date && (
                <ErrorMsg>{errors.election_date.message}</ErrorMsg>
              )}</Box>
            <Box>
            <TextField fullWidth label="State" {...register("state", { required: true })}/>
            {errors?.state && <ErrorMsg>{errors.state.message}</ErrorMsg>}</Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
            <TextField fullWidth label="Total Seats" {...register("total_seats", { required: true })}/> {errors?.total_seats && (
                <ErrorMsg>{errors.total_seats.message}</ErrorMsg>
              )}</Box>
            <Box>
            <TextField fullWidth label="Status" {...register("status", { required: true })}/> {errors?.status && <ErrorMsg>{errors.status.message}</ErrorMsg>}</Box>
          </Box>
         
         
          <Box display="flex" alignContent="center" justifyContent="center" flexDirection="row" gap={2}> <StyledButton type="submit" variant="contained">
      Submit
    </StyledButton></Box>
          </Box>
      </Form>
    </Box>
  );
};

export default ElectionForm;
