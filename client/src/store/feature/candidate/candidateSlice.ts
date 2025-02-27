import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { addCandidate, fetchCandidateById, fetchCandidateBySSN, fetchCandidates, fetchCandidateUpdateDetails, updateCandidateById, updateCandidateData } from './candidateAPI';
import {  CandidateState } from './types';
 
  const initialState: CandidateState = {
    searchQuery: "",
    searchedSSN: "",
    allCandidates: [],
    filteredCandidate: null,
    notFound: false,
    loading: false,
    error: null,
    success: false,
    candidate:null
    
  };
 
const candidateSlice = createSlice({
    name: "candidate",
    initialState,
    reducers: {
      setSearchQuery: (state, action: PayloadAction<string>) => {
        state.searchQuery = action.payload;
        state.notFound = false; // Reset on new search
      },
      clearSearchQuery: (state) => {
        state.searchQuery = "";
        state.filteredCandidate = null;
        state.notFound = false;
      },
      setCandidateNotFound: (state, action: PayloadAction<boolean>) => {
        state.notFound = action.payload;
      },
      resetFilteredCandidate: (state) => {
        state.filteredCandidate = null;  // Reset to show full list
      },
      setSearchedSSN: (state, action: PayloadAction<string>) => {
        state.searchedSSN = action.payload;
      },
      resetState: (state) => {
        state.loading = false;
        state.success = false;
        state.error = null;
      },
    },
    extraReducers: (builder) => {
      builder
        // Fetch all candidates on mount
        .addCase(fetchCandidates.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(fetchCandidates.fulfilled, (state, action) => {
          state.allCandidates = action.payload;
          state.loading = false;
        })
        .addCase(fetchCandidates.rejected, (state, action) => {
          state.loading = false;
          state.error = action.error.message || "Error fetching candidates";
        })
 
        // Fetch candidate by SSN
        .addCase(fetchCandidateBySSN.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(fetchCandidateBySSN.fulfilled, (state, action) => {
          state.filteredCandidate = action.payload;
          state.notFound = false;
          state.loading = false;
          state.searchedSSN = state.searchQuery;
        })
        .addCase(fetchCandidateBySSN.rejected, (state, action) => {
          state.filteredCandidate = null;
          state.loading = false;
          if (action.payload === "No candidate found") {
            state.notFound = true;
          } else {
            state.error = action.payload as string;
          }
        })
        .addCase(addCandidate.pending, (state) => {
          state.loading = true;
          state.success = false;
          state.error = null;
        })
        .addCase(addCandidate.fulfilled, (state) => {
          state.loading = false;
          state.success = true;
          state.error = null;
        })
        .addCase(addCandidate.rejected, (state, action) => {
          state.loading = false;
          state.success = false;
          state.error = action.payload as string;
        })
        .addCase(fetchCandidateById.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(fetchCandidateById.fulfilled, (state, action) => {
          state.loading = false;
          state.candidate = action.payload;
          state.error = null;
          state.notFound = false;
        })
        .addCase(fetchCandidateById.rejected, (state, action) => {
          state.loading = false;
          state.error = action.payload as string || "Error fetching candidate";
        })
        .addCase(updateCandidateData.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(updateCandidateData.fulfilled, (state, action) => {
          state.loading = false;
          state.candidate = action.payload;
          state.error = null;
        })
        .addCase(updateCandidateData.rejected, (state, action) => {
          state.loading = false;
          state.error = action.payload as string || "Error updating candidate";
        })
    },
  });
 
  export const { setSearchedSSN,setSearchQuery, clearSearchQuery, setCandidateNotFound, resetState, resetFilteredCandidate } = candidateSlice.actions;
  export default candidateSlice.reducer;