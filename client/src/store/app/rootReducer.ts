import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer
}); 
export default rootReducer;




