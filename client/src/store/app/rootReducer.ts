import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
const rootReducer = combineReducers({     
    candidate:candidateReducer
}); 
export default rootReducer;




