import { createAsyncThunk } from "@reduxjs/toolkit";
import { Election } from "./types";
import axios from "axios";

export const addElection = createAsyncThunk(
    "election/addElection",
    async (election: Election, { rejectWithValue }) => {
      try {
        const response = await axios.post("http://localhost:8081/api/elections/addElection", election);
        console.log(response)
        return response.data; // Return response data if needed
      } catch (error: any) {
        console.log(error)
        return rejectWithValue(error.response?.data?.message || "Failed to add election");
      }
    }
  );

//   export const fetchElection = createAsyncThunk("candidate/fetchAll", async () => {
//     const response = await axios.get("http://172.16.16.69:8082/api/elections/sorted"); 
//     console.log(response)
//     return response.data;
//   });