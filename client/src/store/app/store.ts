import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import partyApi from "../feature/party/partyAction";
import voterApi from '../feature/voter/VoterAction'



const store = configureStore({
    reducer: {
        rootReducer,
        [partyApi.reducerPath]: partyApi.reducer,
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