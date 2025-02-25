import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import voterReducer from '../feature/voter/VoterSlice'
import  partyReducer from "../feature/party/partyslice"

const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    voter:voterReducer,
    party:partyReducer
}); 
export default rootReducer;




