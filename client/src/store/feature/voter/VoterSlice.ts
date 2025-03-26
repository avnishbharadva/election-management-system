import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { searchVoters } from './VoterAPI';

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
        })
        .addCase(fetchVoters.rejected, (state, action) => {
          state.loading = false;
        });
    },
  });
  
  export const { addVoter } = voterSlice.actions;
  export default voterSlice.reducer;