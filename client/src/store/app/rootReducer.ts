<<<<<<< HEAD
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import officerReducer from '../feature/officers/officerSlice'
import partyApi from '../feature/party/partyAction'; 
import voterApi from '../feature/voter/VoterAction'

=======
import { combineReducers } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
import officerReducer from '../feature/officers/officerSlice'
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    officer:officerReducer
}); 
export default rootReducer;




