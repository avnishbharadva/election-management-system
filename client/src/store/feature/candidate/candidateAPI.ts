import { createAsyncThunk } from "@reduxjs/toolkit";
import { setCandidateNotFound } from "./candidateSlice";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";

export const fetchCandidates = createAsyncThunk(
  "candidates/fetchCandidates",
  async ({ page = 0, perPage = 10, sortBy = "candidateId", sortDir = "asc" }: { page?: number; perPage?: number; sortBy?: string; sortDir?: string }, { rejectWithValue }) => {
    try {
      const response = await axiosInstance.get(
        `/candidates?page=${page}&perPage=${perPage}&sortBy=${sortBy}&sortDir=${sortDir}`
      );
      if(response.status === 200){
        return response?.data?.data;
      }
      if(response.status === 404){
        return response.data.message;
      }
    } catch (error: any) {
      
      return rejectWithValue(error.response?.data || "Error fetching data");
    }
  }
);


export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
      const response = await axiosInstance.get(`/candidates/by-ssn/${candidateSSN}`);
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
      const response = await axiosInstance.post("/candidates", formData);
      if (response.status === 200) {
        return response.data;
      } 
    } catch (error: any) {
      if (error.response?.status === 400) {
        toast.error("Invalid input. Please check the form fields.");
      
      } else if (error.response?.status === 500) {
        toast.error("Server error. Please try again later.");
      } else if (error.response?.status === 403) {
        toast.error("Forbidden");
      }else {       
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
        `/candidates/${candidateId}`
      );
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

export const updateCandidateData = createAsyncThunk(
  "candidate/updateCandidateData",
  async (
    { candidateId, candidateData }: { candidateId: string; candidateData: Record<string, any> },
    { rejectWithValue }
  ) => {
    try {
      const response = await axiosInstance.put(
        `/candidates/${candidateId}`,
        candidateData, 
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      return response.data;
    } catch (error: any) {
      toast.error("Something Went Wrong!");
      return rejectWithValue(error.response?.data || error.message || "Error updating candidate");
    }
  }
);

export const deleteCandidateById = createAsyncThunk(
  "candidate/deleteCandidate",
  async (candidateId: number, { rejectWithValue }) => {
    try {
      const response = await axiosInstance.delete(
        `/candidates/${candidateId}`
      );
      toast.success("Candidate Deleted successfully!");     
      return response.data;
    } catch (error: any) { 
      toast.error("Something Went Wrong!");
      return rejectWithValue(error.response?.data || error.message || "Error deleting candidate");
    }
  }
);