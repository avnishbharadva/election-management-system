import { createAsyncThunk } from "@reduxjs/toolkit";
import { Election, FormValues } from "./types";
// import axios from "axios";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";
 
export const addElection = createAsyncThunk(
    "election/addElection",
    async (election: Election, { rejectWithValue }) => {
      try {
        const response = await axiosInstance.post("/elections", election);
        if(response.status === 200){
          toast.success("Election Registerd SuccessFully!")
        }
        console.log(response)
        return response.data; // Return response data if needed
      } catch (error: any) {
        toast.error("Something went wrong ")
        return rejectWithValue(error.response?.data?.message || "Failed to add election");
      }
    }
  );
 
  export const fetchElection = createAsyncThunk(
  "election/fetchElection",async (
    { page = 0, perPage = 5, order = "desc" }: { page?: number; perPage?: number; order: string },
    { rejectWithValue }
  ) => {
    try {
      const response = await axiosInstance.get(`/elections/sorted?page=${page}&size=${perPage}&order=${order}`);
      console.log("sorted Election:", response.data);
 
      // Map response to only necessary data
      console.log("api"+response.data)
      return response.data
       
    } catch (error: any) {
      console.error("Error fetching elections:", error);
      return rejectWithValue(error.response?.data || error.message || "Failed to fetch elections");
    }
  }
);
 
  export const updateElectionById = createAsyncThunk(
    "election/updateElection",
    async ({ electionId, updatedElection }: { electionId: number, updatedElection: FormValues }, { rejectWithValue }) => {
      console.log(electionId, updatedElection);
      try{
        const response = await axiosInstance.put(`/elections/${electionId}`, updatedElection);
        console.log(response);
        return response.data;
      }catch(error:any){
        return rejectWithValue(error.response?.data?.message || "Failed to update election");
      }
    })
 
  export const deleteElectionById = createAsyncThunk(
    "election/deleteElection",
    async (electionId: number, { rejectWithValue }) => {
      try {
        const response = await axiosInstance.delete(`/elections/${electionId}`);
        console.log(response);
        return response.data;
      }
      catch (error: any) {
        return rejectWithValue(error.response?.data?.message || "Failed to delete election");
      }
    }
  );
 
  export const fetchAllElection = createAsyncThunk(
    "election/fetchAllElection",
    async (_, { rejectWithValue }) => {
      try {
        const response = await axiosInstance.get("/elections");
        return response.data;
      } catch (error: any) {
        console.error("Failed to fetch elections:", error);
        return rejectWithValue(error.response?.data || "Failed to fetch elections");
      }
    }
  );