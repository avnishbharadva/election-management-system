import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import voterApi from '../feature/voter/VoterAction'
import partyApi from '../feature/party/partyAction'; 

const store = configureStore({
    reducer: {
        ...rootReducer, // Spread your rootReducer
        [partyApi.reducerPath]: partyApi.reducer, // Add partyApi reducer
        [voterApi.reducerPath]: voterApi.reducer
    
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware()
            .concat(partyApi.middleware)
            .concat(voterApi.middleware),
  

});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;