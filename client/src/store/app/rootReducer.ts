import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import voterReducer from '../feature/voter/voterSlice'

const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    voter:voterReducer,
   
}); 
export default rootReducer;




