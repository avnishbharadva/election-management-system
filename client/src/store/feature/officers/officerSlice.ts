import { createSlice } from "@reduxjs/toolkit";
import { OfficerState } from "./types";
import { officerLogin } from "./officerApi";

const initialState: OfficerState = {
    success: false,
    loading: false,
    error: ''
}

const officerSlice = createSlice({
    name: "officer",
    initialState,
    reducers: {
        resetState: (state) => {
            state.loading = false;
            state.error = null;
            state.success = false;
        },
    },
    extraReducers: (builder) => {
        builder.addCase(officerLogin.pending, (state) => {
            state.loading = true;
            state.success = false;
            state.error = null;
        })
            .addCase(officerLogin.fulfilled, (state) => {
                state.loading = false;
                state.success = true;
                state.error = null;
            })
            .addCase(officerLogin.rejected, (state, action) => {
                state.loading = false;
                state.success = false;
                state.error = action.payload as string;
            })
    }
})
export const { resetState } = officerSlice.actions;
export default officerSlice.reducer;