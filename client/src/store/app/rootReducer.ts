import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import officerReducer from '../feature/officers/officerSlice'
const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    officer:officerReducer
}); 
export default rootReducer;




