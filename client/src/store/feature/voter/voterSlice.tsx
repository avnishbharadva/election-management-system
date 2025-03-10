import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { searchVoters } from '../../../api/voterApi/VoterApi';





// const voterSlice = createSlice({
//     name: 'voter',
//     initialState :[],
//     reducers: {
//      addVoter(state, action:any) {
//         state.push(...action.payload)
//         },
//         removeVoter(state, action:any) {
            
//             state = state.filter((voter) => voter?.id !== action.payload?.id)
//         },
//         removeAllVoter(state) {
//         state = []
// }
// }
// })


// export const { addVoter,removeVoter,removeAllVoter } = voterSlice.actions
// export default voterSlice.reducer


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
  
  export const {  appendVoters, setCurrentPage } = voterSlice.actions;
  export default voterSlice.reducer;