import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import voterApi from '../feature/voter/VoterAction'
import partyApi from '../feature/party/partyAction'; 


const store = configureStore({
    reducer:     rootReducer,
    
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware()
            .concat(partyApi.middleware)
            .concat(voterApi.middleware),
  

});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;