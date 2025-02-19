import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";


export interface Party {
    partyId: number;
    partyName: string;
    partyAbbreviation: string;
    partySymbol: string | null;
    partyFoundationYear: number;
    partyWebSite: string;
    headQuarters: string;
    founderName: string;
  }
  
  export interface PartyState {
    parties: Party[];
    status: 'idle' | 'loading' | 'succeeded' | 'failed';
    error: string | null;
  }


const api = axios.create({
    baseURL: "http://172.16.16.67:8081"
})


const partyGet = async()=>{
const response = await api.get('/api/party')
console.log(response)
return response?.data
}

    export const patythunk = createAsyncThunk(
        'voter/fetchVoters',
        async () => {
        const data = await partyGet();  
     
        return data;
        }
    )
    
    


    const partySlice = createSlice({
        name: 'party',
        initialState: {
          parties: [] as Party[],   
          status: 'idle',   
          error: null,
        } as PartyState, 
        reducers: {
          addParties: (state, action) => {
            action.payload.forEach((newParty:any) => {
                if (!state.parties.some((party) => party.partyId === newParty.partyId)) {
                  state.parties.push(newParty);
                }
          })
          },
        },
        extraReducers: (builder) => {
          builder
            .addCase(patythunk.pending, (state) => {
              state.status = 'loading';
            })
            .addCase(patythunk.fulfilled, (state, action) => {
              action.payload.forEach((newParty: Party) => {
                if (!state.parties.some((party) => party.partyId === newParty.partyId)) {
                  state.parties.push(newParty);
                }
              });
            })
            .addCase(patythunk.rejected, (state, action) => {
              state.status = 'failed';
              state.error = action.error.message ?? 'Unknown error';
            });
        },
      });
      
      export const { addParties } = partySlice.actions;
      export default partySlice.reducer;
