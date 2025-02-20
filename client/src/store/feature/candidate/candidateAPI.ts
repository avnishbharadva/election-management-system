import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { setCandidateNotFound } from "./candidateSlice";

// Fetch all candidates initially
export const fetchCandidates = createAsyncThunk("candidate/fetchAll", async () => {
  const response = await axios.get("http://localhost:8082/api/candidate/getAllDetails"); // Replace with your API endpoint
  return response.data;
});

// Fetch candidate by SSN
export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
      const response = await axios.get(`http://localhost:8082/api/candidate/ssn/${candidateSSN}`);
    
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
        "http://localhost:8082/api/candidate/addCandidate",
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

export const fetchCandidateUpdateDetails = createAsyncThunk(
  "candidate/fetchCandidateUpdateDetails",
  async (candidateId: number, { dispatch, rejectWithValue }) => {
    try {
      const response = await axios.get(
        `http://localhost:8082/api/candidate/candidateId/${candidateId}`
      );
      return response.data;  // This will return the candidate's details including `candidateId`
    } catch (error: any) {
      if (error.response && error.response.status === 404) {
        dispatch(setCandidateNotFound(true)); // Set not found flag
        return rejectWithValue("No candidate found");
      }
      return rejectWithValue(error.message);
    }
  }
);


export const updateCandidateById = createAsyncThunk(
  "candidate/updateCandidate",
  async (
    { candidateId, candidateData }: { candidateId: number; candidateData: FormData },
    { rejectWithValue }
  ) => {
    try {
      const response = await axios.put(
        `http://localhost:8082/api/candidate/updateCandidate/${candidateId}`,
        candidateData, // ✅ Send JSON directly
        {
          headers: {
            "Content-Type": "application/json", // ✅ Ensure correct content type
          },
        }
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data || error.message || "Error updating candidate");
    }
  }
);