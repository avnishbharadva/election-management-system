import { combineReducers, configureStore } from "@reduxjs/toolkit";
import candidateReducer from '../feature/candidate/candidateSlice'
import electionReducer from '../feature/election/electionSlice'
<<<<<<< Updated upstream
import voterReducer from '../feature/voter/VoterSlice'
import  partyReducer from "../feature/party/partyslice"
=======
import officerReducer from '../feature/officers/officerSlice'
import partyApi from '../feature/party/partyAction'; 
import voterApi from '../feature/voter/VoterAction'
>>>>>>> Stashed changes

const rootReducer = combineReducers({     
    candidate:candidateReducer,
    election: electionReducer,
    voter:voterReducer,
    party:partyReducer
}); 
export default rootReducer;




