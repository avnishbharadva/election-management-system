import React from "react";
import {
  Box,
  FormControl,
  FormHelperText,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
  Button,
  CircularProgress,
  Alert,
} from "@mui/material";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { addElection } from "../../store/feature/election/electionApi";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { Form } from "../../style/CommanStyle";

type FormValues = {
  election_name: string;
  election_type: string;
  election_date: string;
  state: string;
  total_seats: number;
  status: string;
};

const ElectionForm = () => {
  const dispatch = useDispatch()
  const { loading, error, success } = useSelector((state: any) => state.election);
  const { register, handleSubmit, reset,control } = useForm<FormValues>({
    defaultValues: {
      election_name: "",
      election_type: "State",
      election_date: "",
      state: "New York",
      total_seats: 0,
      status: "Upcoming",
    },
  });

  const onSubmit: SubmitHandler<FormValues> = (data) => {
    dispatch(addElection(data));
  };

  const handleReset = () => {
    reset();
    dispatch(resetState());
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        // height: "100vh",
        // backgroundColor: "#f5f5f5",
      }}
    >
      <Box
        sx={{
          width: "400px",
          padding: "20px",
          borderRadius: "8px",
          // boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.2)",
          backgroundColor: "#fff",
        }}
      > {loading && <CircularProgress />}
      {error && <Alert severity="error">{error}</Alert>}
      {success && <Alert severity="success">Election added successfully!</Alert>}
        <Typography align="center" variant="h5" mb={3}>
          Add Election
        </Typography>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Box display="flex" flexDirection="column" gap={2}>
            <Box display="flex" gap={2}>
              <TextField
                fullWidth
                label="Election Name"
                {...register("election_name", {
                  required: "Election name is required",
                  minLength: {
                    value: 3,
                    message: "Election name must be at least 3 characters",
                  },
                })}
                error={!!error?.election_name}
                helperText={error?.election_name?.message}
              />

              <TextField
                fullWidth
                label="Election Type"
                value="State"
                InputProps={{ readOnly: true }}
                {...register("election_type")}
              />
            </Box>

            <Box display="flex" gap={2}>
              <TextField
                type="date"
                fullWidth
                InputLabelProps={{ shrink: true }}
                label="Election Date"
                {...register("election_date", {
                  required: "Election date is required",
                  validate: (value) =>
                    new Date(value) > new Date() || "Election date must be in the future",
                })}
                error={!!error?.election_date}
                helperText={error?.election_date?.message}
              />

              <TextField
                fullWidth
                label="State"
                value="New York"
                InputProps={{ readOnly: true }}
                {...register("state")}
              />
            </Box>

            <Box display="flex" gap={2}>
              <TextField
                fullWidth
                label="Total Seats"
                type="number"
                {...register("total_seats", {
                  required: "Total seats are required",
                  min: {
                    value: 1,
                    message: "Total seats must be at least 1",
                  },
                })}
                error={!!error?.total_seats}
                helperText={error?.total_seats?.message}
              />

              {/* <TextField
                fullWidth
                label="Status"
                {...register("status", {
                  required: "Status is required",
                  minLength: {
                    value: 3,
                    message: "Status must be at least 3 characters",
                  },
                })}
                error={!!error.status}
                helperText={error?.status?.message}
              /> */}
            </Box>

            <Button
              type="submit"
              variant="contained"
              fullWidth
              sx={{
                mt: 2,
                bgcolor: "#1976d2",
                color: "#fff",
                "&:hover": {
                  bgcolor: "#1565c0",
                },
              }}
              // onClick={handleReset}
            >
              Submit
            </Button>
          </Box>
        </Form>
      </Box>
    </Box>
  );
};

export default ElectionForm;
