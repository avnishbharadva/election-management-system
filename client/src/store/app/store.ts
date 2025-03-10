<<<<<<< Updated upstream
// import { configureStore } from '@reduxjs/toolkit';
// import rootReducer from './rootReducer';

=======
import { configureStore} from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import partyApi from '../feature/party/partyAction'; 
import voterApi from '../feature/voter/VoterAction'
>>>>>>> Stashed changes

// const store = configureStore({
//     reducer: rootReducer 
// });

// export type RootState = ReturnType<typeof store.getState>;
// export type AppDispatch = typeof store.dispatch;

// export default store;

import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import partyApi from '../feature/party/partyAction'; 

const store = configureStore({
  reducer: {
    ...rootReducer, // Spread your rootReducer
    [partyApi.reducerPath]: partyApi.reducer, // Add partyApi reducer
<<<<<<< Updated upstream
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(partyApi.middleware), // Add partyApi middleware
=======
    [voterApi.reducerPath]: voterApi.reducer

  },

    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware()
          .concat(partyApi.middleware)
          .concat(voterApi.middleware),

>>>>>>> Stashed changes
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;