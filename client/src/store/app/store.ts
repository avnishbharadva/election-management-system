<<<<<<< HEAD
import { configureStore} from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import partyApi from '../feature/party/partyAction'; 
import voterApi from '../feature/voter/VoterAction'

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

=======
import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';


const store = configureStore({
    reducer: rootReducer 
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;