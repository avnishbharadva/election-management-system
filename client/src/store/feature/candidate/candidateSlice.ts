import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { addCandidate, deleteCandidateById, fetchCandidateById, fetchCandidateBySSN, fetchCandidates, updateCandidateData } from './candidateAPI';
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
    candidate:null,
    currentPage: 0,
    totalPages:0,
    totalRecords:0,
    perPage:5,
    sortBy: "candidateId",
    sortDir: "asc",
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
      filterCandidateBySSN: (state, action: PayloadAction<string>) => {
        state.filteredCandidate = state.allCandidates.filter(
          (candidate) => candidate.ssn === action.payload
        );
      },
      resetFilteredCandidate: (state) => {
        state.filteredCandidate = null;  // Reset to show full list
      },
      setSearchedSSN: (state, action: PayloadAction<string>) => {
        state.searchedSSN = action.payload;
      },
      setPage: (state, action) => {
        state.currentPage = action.payload;
      },
      setPerPage: (state, action) => {
        state.perPage = action.payload;
      },
      setSort: (state, action) => {
        state.sortBy = action.payload.sortBy;
        state.sortDir = action.payload.sortDir;
      },
      clearCandidate(state) {
        state.candidate = null; 
      },
      resetState: (state) => {
        state.loading = false;
        state.success = false;
        state.error = null;
        state.candidate = null
      },
    },
    extraReducers: (builder) => {
      builder
        .addCase(fetchCandidates.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(fetchCandidates.fulfilled, (state, action) => {
          state.allCandidates = action.payload.data.content;
          state.currentPage = action.payload.data.number;
          state.totalPages = action.payload.data.totalPages;
          state.totalRecords = action.payload.data.totalElements;
          state.perPage = action.payload.data.size;
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
          if (action.payload) {
            state.filteredCandidate = [action.payload.data]; // Ensure it's an array
          } else {
            state.filteredCandidate = [];
          }          
          state.notFound = !action.payload;
          state.loading = false;
          state.searchedSSN = state.searchQuery; // Make sure searched SSN is stored
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
          state.candidate = action.payload.data;
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
        //Delete candidate
        .addCase(deleteCandidateById.pending, (state) =>{
          state.loading = true;
          state.success = false;
          state.error = null;
        })
        .addCase(deleteCandidateById.fulfilled, (state)=>{
          state.loading = false;
          state.success = true;
        })
        .addCase(deleteCandidateById.rejected, (state, action)=>{
          state.loading = false;
          state.success = false;
          state.error = action.payload as string;
        }) 
    },
  });

  export const { clearCandidate,setSearchedSSN,setSearchQuery, clearSearchQuery, setCandidateNotFound, resetState, resetFilteredCandidate, setPage,setPerPage, filterCandidateBySSN , setSort} = candidateSlice.actions;
  export default candidateSlice.reducer;