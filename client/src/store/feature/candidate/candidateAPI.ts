// import { createAsyncThunk } from "@reduxjs/toolkit";
// import axios from "axios";
// import { Candidate } from "./types";



// export const fetchCandidateBySSN = createAsyncThunk<Candidate, string>(
//     "candidate/fetchBySSN",
//     async (candidateSSN, { rejectWithValue }) => {
//         try {
//             const response = await axios.get<Candidate>(
//                 `http://172.16.16.63:8082/api/candidate/ssn/${candidateSSN}`
//             );

//             return {
//                 ...response.data,
//                 dateOfBirth: response.data.dateOfBirth.toString(), // Convert Date to string
//             };
//         } catch (error: any) {
//             console.error("Error fetching candidate:", error);
//             return rejectWithValue(error.response?.data || "Failed to fetch candidate");
//         }
//     }
// );

import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { setCandidateNotFound } from "./candidateSlice";

// Fetch all candidates initially
export const fetchCandidates = createAsyncThunk("candidate/fetchAll", async () => {
  const response = await axios.get("http://172.16.16.63:8082/api/candidate/getAll"); // Replace with your API endpoint
  return response.data;
});

// Fetch candidate by SSN
export const fetchCandidateBySSN = createAsyncThunk(
  "candidate/fetchBySSN",
  async (candidateSSN: string, { dispatch, rejectWithValue }) => {
    try {
      const response = await axios.get(`http://172.16.16.63:8082/api/candidate/ssn/${candidateSSN}`);
      return response.data;
    } catch (error: any) {
      if (error.response && error.response.status === 404) {
        dispatch(setCandidateNotFound(true)); // ✅ Set not found flag
        return rejectWithValue("No candidate found");
      }
      return rejectWithValue(error.message);
    }
  }
);
