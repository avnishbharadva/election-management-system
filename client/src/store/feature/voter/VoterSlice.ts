import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { searchVoters } from './VoterAPI';
// /import {searchVoters} from './VoterAPI';



// Async thunk to fetch voters
export const fetchVoters = createAsyncThunk(
    'voter/fetchVoters',
    async ({ page }) => {
      const voters = await searchVoters( page);
      return { voters,  page };
    }
  );
  
  const voterSlice = createSlice({
    name: 'voter',
    initialState: {
      voters: [],
      loading: false,
      error: null,
      currentPage: 1,
      query: '',
    },
    reducers: {
    addVoter(state, action:any) {   
        state.voters.push(...action.payload)
    }
    },
    extraReducers: (builder) => {
      builder
        .addCase(fetchVoters.pending, (state) => {
          state.loading = true;
        })
        .addCase(fetchVoters.fulfilled, (state, action) => {
          state.loading = false;
          console.log(action.payload)
         // state.addVoter(action.payload.voters); // Append new voters
        })
        .addCase(fetchVoters.rejected, (state, action) => {
          state.loading = false;
        //  state.error = action.error.message;
        });
    },
  });
  
  export const { addVoter } = voterSlice.actions;
  export default voterSlice.reducer;