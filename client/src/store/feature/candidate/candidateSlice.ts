import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { addCandidate, fetchCandidateBySSN, fetchCandidates, fetchCandidateUpdateDetails, updateCandidateById } from './candidateAPI';
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
    candidates: [],
    
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
        .addCase(fetchCandidateUpdateDetails.fulfilled, (state, action) => {
          state.candidates = action.payload; // Store candidate data in candidateData
        })
        .addCase(fetchCandidateUpdateDetails.rejected, (state, action) => {
          state.error = action.payload as string; // Handle error
        })
 
        // Update candidate
        .addCase(updateCandidateById.pending, (state) => {
          state.loading = true;
          state.success = false;
        })
        .addCase(updateCandidateById.fulfilled, (state, action) => {
          state.success = true;
          state.allCandidates = state.allCandidates.map((candidate) =>
            candidate.candidateId === action.payload.candidateId ? action.payload : candidate
          );
          state.loading = false;
        })
        .addCase(updateCandidateById.rejected, (state, action) => {
          state.loading = false;
          state.success = false;
          state.error = action.payload as string;
        });
    },
  });
 
  export const { setSearchedSSN,setSearchQuery, clearSearchQuery, setCandidateNotFound, resetState, resetFilteredCandidate } = candidateSlice.actions;
  export default candidateSlice.reducer;