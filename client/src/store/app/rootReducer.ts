import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import voterReducer from '../feature/voter/voterSlice'
import  partyReducer from "../feature/formFields/partyslice"

const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    voter:voterReducer,
    party:partyReducer
}); 
export default rootReducer;




