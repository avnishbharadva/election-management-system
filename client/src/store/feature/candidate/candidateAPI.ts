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
      
        return response.data;
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
      console.log(error)
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
        toast.success(response.data.message)
        return response.data;
      } 
    } catch (error: any) {
      if (error.response?.status === 404) {
        toast.error(error.response.data.message);
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
      );
      if (response.status === 200) {
        toast.success(response.data.message)
        return response.data;
      } 
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
      toast.success(response.data.message);     
      return response.data;
    } catch (error: any) { 
      toast.error(error.data.response.message);
      return rejectWithValue(error.response?.data || error.message || "Error deleting candidate");
    }
  }
);