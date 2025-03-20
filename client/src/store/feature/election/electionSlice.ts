import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { addElection, deleteElectionById, fetchAllElection, fetchElection, updateElectionById } from "./electionApi";
import { ElectionState } from "./types";
 
const initialState: ElectionState = {
    loading: false,
    error: null,
    success: false,
    elections: [],
    currentPage: 0,
    totalPages:0,
    totalRecords:0,
    perPage:5,
    sortDir: "desc",
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
        state.perPage = action.payload;
      },
      setSort: (state, action) => {
        state.sortDir = action.payload.sortDir;
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
          state.currentPage= 0;
          state.loading = false;
          state.success = true;
        })
        .addCase(addElection.rejected, (state, action: PayloadAction<any>) => {
          state.loading = false;
          state.error = action.payload as string;
        })
 
        //Fetch Election
        .addCase(fetchElection.fulfilled, (state, action) => {
          state.elections = action.payload;  // Ensure `content` exists
          state.currentPage = action.payload.currentPage;
          state.totalPages = action.payload.totalPages;
          state.totalRecords = action.payload.totalRecords;
          state.perPage = action.payload.perPage;
          state.loading = false;
        })
        .addCase(fetchElection.pending, (state) => {
          state.loading = true;
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
          state.loading = false
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
          state.elections = action.payload;
        })
        .addCase(fetchAllElection.rejected, (state) => {
          state.loading = false;
        });
    },
  });
 
  export const { resetState , setPage, setPerPage} = electionSlice.actions;
  export default electionSlice.reducer;
 