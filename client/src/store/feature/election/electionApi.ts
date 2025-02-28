import { createAsyncThunk } from "@reduxjs/toolkit";
import { Election, FormValues } from "./types";
import axios from "axios";
import axiosInstance from "../../app/axiosInstance";

export const addElection = createAsyncThunk(
    "election/addElection",
    async (election: Election, { rejectWithValue }) => {
      try {
        const response = await axiosInstance.post("/elections/addElection", election);
        console.log(response)
        return response.data; // Return response data if needed
      } catch (error: any) {
        console.log(error)
        return rejectWithValue(error.response?.data?.message || "Failed to add election");
      }
    }
  );

  export const fetchElection = createAsyncThunk(
    "election/fetchElection",
    async ({ page, perPage, order }: { page: number; perPage: number; order: string }) => {
      const response = await axiosInstance.get(
        `/elections/sorted?order=${order}&page=${page}&size=${perPage}`
      );
      if (!response.data) throw new Error("Failed to fetch elections");
      return await response.data();
    }
  );
  

  export const updateElectionById = createAsyncThunk(
    "election/updateElection", 
    async ({ electionId, updatedElection }: { electionId: number, updatedElection: FormValues }, { rejectWithValue }) => {
      console.log(electionId, updatedElection);
      try{
        const response = await axiosInstance.put(`/elections/update/${electionId}`, updatedElection);
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
        const response = await axios.delete(`/elections/delete/${electionId}`);
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
    async () => {
      const response = await fetch(`http:/localhost:8082/api/elections/getAllElection`);
      if (!response.ok) throw new Error("Failed to fetch elections");
      return await response.json();
    }
  )

  