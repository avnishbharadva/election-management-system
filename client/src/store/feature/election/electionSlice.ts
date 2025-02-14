import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { addElection } from "./electionApi";
import { ElectionState } from "./types";

const initialState: ElectionState = {
    loading: false,
    error: null,
    success: false,
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
          state.error = action.payload;
        });
    },
  });
  
  export const { resetState } = electionSlice.actions;
  export default electionSlice.reducer;