import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { setCandidateNotFound } from "./candidateSlice";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";

// Fetch all candidates initially
export const fetchCandidates = createAsyncThunk(
  "candidate/fetchAll",
  async (_, { rejectWithValue }) => { 
    try {
      const response = await axiosInstance.get("/candidate/getAllDetails");
      return response.data;
    } catch (error: any) {
      console.log(error);
      return rejectWithValue(error.response?.data?.message || "Failed to fetch candidates");
    }
  }
);


// Fetch candidate by SSN
export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
      const response = await axiosInstance.get(`/candidate/ssn/${candidateSSN}`);
      return response.data;
    } catch (error: any) {
      if (error.response && error.response.status === 404) {
        dispatch(setCandidateNotFound(true)); 
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
      const response = await axiosInstance.post("/candidate/addCandidate", formData);

      // Check response status
      if (response.status === 200) {
        // Success status
        toast.success("Candidate added successfully!");
        return response.data;
      } else {
        // Handle unexpected success statuses
        toast.warning("Candidate added, but with warnings. Please verify.");
        return response.data;
      }
    } catch (error: any) {
      // Check for specific status codes
      if (error.response?.status === 400) {
        toast.error("Invalid input. Please check the form fields.");
      } else if (error.response?.status === 409) {
        toast.error("Candidate already exists.");
      } else if (error.response?.status === 500) {
        toast.error("Server error. Please try again later.");
      } else {
        // Generic error message
        toast.error("Failed to add candidate. Please try again.");
      }

      return rejectWithValue(error.response?.data || "Error occurred while adding candidate");
    }
  }
);


export const fetchCandidateById = createAsyncThunk(
  "candidate/fetchCandidateById",
  async (candidateId: number, { dispatch, rejectWithValue }) => {
    try {
      
      const response = await axiosInstance.get(
        `/candidate/candidateId/${candidateId}`
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


export const updateCandidateData = createAsyncThunk(
  "candidate/updateCandidateData",
  async (
    { candidateId, candidateData }: { candidateId: string; candidateData: FormData },
    { rejectWithValue }
    
  ) => {
    try {
      const response = await axiosInstance.put(
        `/candidate/updateCandidate/${candidateId}`,
        candidateData, 
        {
          headers: {
          "Content-Type": "multipart/form-data",
          },
          
        }
      );
      toast.success("Candidate Updated successfully!");
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data || error.message || "Error updating candidate");
    }
  }
);