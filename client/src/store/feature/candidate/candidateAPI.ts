import { createAsyncThunk } from "@reduxjs/toolkit";
<<<<<<< HEAD
import axios from "axios";
=======
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
import { setCandidateNotFound } from "./candidateSlice";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";

// Fetch all candidates initially
export const fetchCandidates = createAsyncThunk(
  "candidates/fetchCandidates",
  async ({ page = 0, perPage = 10, sortBy = "candidateId", sortDir = "asc" }: { page?: number; perPage?: number; sortBy?: string; sortDir?: string }, { rejectWithValue }) => {
    try {
      const response = await axiosInstance.get(
<<<<<<< HEAD
        `/candidate/paged?page=${page}&perPage=${perPage}&sortBy=${sortBy}&sortDir=${sortDir}`
      );
      console.log(response.data)
      return response.data;
    } catch (error: any) {
=======
        `/api/candidates?page=${page}&perPage=${perPage}&sortBy=${sortBy}&sortDir=${sortDir}`
      );
      if(response.status === 200){

        return response.data;
      }
      if(response.status === 404){
        console.log(response.data)
        console.log(response.data.message)
        return response.data.message;
      }
    } catch (error: any) {
      
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
      return rejectWithValue(error.response?.data || "Error fetching data");
    }
  }
);


// Fetch candidate by SSN
export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
<<<<<<< HEAD
      const response = await axiosInstance.get(`/candidate/ssn/${candidateSSN}`);
=======
      const response = await axiosInstance.get(`/api/candidates/by-ssn/${candidateSSN}`);
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
      const response = await axiosInstance.post("/candidate/addCandidate", formData);

      // Check response status
      if (response.status === 200) {
        // Success status
        toast.success("Candidate added successfully!");
        toast.success("Registration Mail Sent successFully!")
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
=======
      const response = await axiosInstance.post("/api/candidates", formData);
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
        
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
        `/candidate/candidateId/${candidateId}`
=======
        `api/candidates/${candidateId}`
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
        `/candidate/updateCandidate/${candidateId}`,
        candidateData, 
       
      );
      toast.success("Candidate Updated successfully!");
=======
        `api/candidates/${candidateId}`,
        candidateData, 
       
      );
      
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
        `http://localhost:8082/api/candidate/delete/${candidateId}`
=======
        `/api/candidates/${candidateId}`
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
      );
      toast.success("Candidate Deleted successfully!");
      
      return response.data;
    } catch (error: any) { 
<<<<<<< HEAD
=======
      toast.error("Something Went Wrong!");

>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
      return rejectWithValue(error.response?.data || error.message || "Error deleting candidate");
    }
  }
);