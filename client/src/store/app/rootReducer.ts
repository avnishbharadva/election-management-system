
import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import officerReducer from '../feature/officers/officerSlice'
import partyApi from '../feature/party/partyAction'; 
import voterApi from '../feature/voter/VoterAction'


const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    officer:officerReducer,
    [partyApi.reducerPath]: partyApi.reducer, 
    [voterApi.reducerPath]: voterApi.reducer
}); 
export default rootReducer;




