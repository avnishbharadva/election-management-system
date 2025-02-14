import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { setCandidateNotFound } from "./candidateSlice";

// Fetch all candidates initially
export const fetchCandidates = createAsyncThunk("candidate/fetchAll", async () => {
  const response = await axios.get("http://172.16.16.69:8082/api/candidate/getAll"); // Replace with your API endpoint
  return response.data;
});

// Fetch candidate by SSN
export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
      const response = await axios.get(`http://172.16.16.69:8082/api/candidate/ssn/${candidateSSN}`);
      return response.data;
    } catch (error: any) {
      if (error.response && error.response.status === 404) {
        dispatch(setCandidateNotFound(true)); //  Set not found flag
        return rejectWithValue("No candidate found");
      }
      return rejectWithValue(error.message);
    }
  }
);
export const addCandidate = createAsyncThunk(
  "candidate/addCandidate",
  async (formData: FormData, { rejectWithValue }) => {
    try {
      const response = await axios.post(
        "http://172.16.16.69:8082/api/candidate/addCandidate",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response.data);
    }
  }
);