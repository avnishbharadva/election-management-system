// import { configureStore } from '@reduxjs/toolkit';
// import rootReducer from './rootReducer';


// const store = configureStore({
//     reducer: rootReducer 
// });

// export type RootState = ReturnType<typeof store.getState>;
// export type AppDispatch = typeof store.dispatch;

// export default store;

import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import partyApi from '../feature/party/partyAction'; // Adjust the import path if needed

const store = configureStore({
  reducer: {
    ...rootReducer, // Spread your rootReducer
    [partyApi.reducerPath]: partyApi.reducer, // Add partyApi reducer
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(partyApi.middleware), // Add partyApi middleware
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;