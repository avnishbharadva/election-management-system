import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { addElection, deleteElectionById, fetchAllElection, fetchElection, updateElectionById } from "./electionApi";
import { ElectionState } from "./types";

const initialState: ElectionState = {
    loading: false,
    error: null,
    success: false,
    election:[],
    elections: [],
    currentPage: 0,
    rowsPerPage: 5,
    totalElements: 0,
  };

  const electionSlice = createSlice({
    name: "election",
    initialState,
    reducers: {
      resetState: (state) => {
        state.loading = false;
        state.error = null;
        state.success = false;
      },
      setPage: (state, action) => {
        state.currentPage = action.payload;
      },
      setPerPage: (state, action) => {
        state.rowsPerPage = action.payload;
      },
    },
    extraReducers: (builder) => {
      builder
        .addCase(addElection.pending, (state) => {
          state.loading = true;
          state.error = null;
          state.success = false;
        })
        .addCase(addElection.fulfilled, (state) => {
          state.loading = false;
          state.success = true;
        })
        .addCase(addElection.rejected, (state, action: PayloadAction<any>) => {
          state.loading = false;
          state.error = action.payload as string;
        })

        //Fetch Election
        .addCase(fetchElection.pending, (state) => {
          state.loading = true;
        })
        .addCase(fetchElection.fulfilled, (state, action) => {
          state.loading = false;
          state.election = action.payload.content; // ✅ Ensure this is correctly assigned
          state.totalElements = action.payload.totalElements;
        })
        .addCase(fetchElection.rejected, (state) => {
          state.loading = false;
        })
        //Update Election
        .addCase(updateElectionById.pending,(state) =>{
          state.loading = true;
          state.error = null;
          state.success = false;
        })
        .addCase(updateElectionById.fulfilled,(state: any, action) =>{
          state.loading = false;
          state.success = true;
            // Find and update the election in the array
          const index = state.election.findIndex((e: any) => e.id === action.payload.id);
          if (index !== -1) {
            state.election[index] = action.payload;
          }
        })
        .addCase(updateElectionById.rejected, (state, action: PayloadAction<any>) => {
          state.loading = false;
          state.error = action.payload;
        })
        .addCase(deleteElectionById.pending, (state) =>{
          state.loading = true;
          state.error = null;
          state.success = false;
        })
        .addCase(deleteElectionById.fulfilled, (state,  action: PayloadAction<any>)=>{
          if (state.election) {
            state.election = state.election.filter((election: any) => election.id !== action.payload);
          }
          state.loading = false;
          state.success = true;
        })
        .addCase(deleteElectionById.rejected, (state,  action: PayloadAction<any>)=>{
          state.loading = false;
          state.error = action.payload;
        })
        .addCase(fetchAllElection.pending, (state) =>{
          state.loading = true;
        })
        .addCase(fetchAllElection.fulfilled, (state, action) => {
          state.loading = false;
          state.election = action.payload;
        })
        .addCase(fetchAllElection.rejected, (state) => {
          state.loading = false;
        });
    },
  });
  
  export const { resetState , setPage, setPerPage} = electionSlice.actions;
  export default electionSlice.reducer;