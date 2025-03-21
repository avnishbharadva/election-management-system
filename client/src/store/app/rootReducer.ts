import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import officerReducer from '../feature/officers/officerSlice'
import voterApi from '../feature/voter/VoterAction'
import partyApi from '../feature/party/partyAction'; 

const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    officer:officerReducer,
    [partyApi.reducerPath]: partyApi.reducer, // Add partyApi reducer
    [voterApi.reducerPath]: voterApi.reducer
}); 
export default rootReducer;




