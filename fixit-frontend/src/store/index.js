import { configureStore } from '@reduxjs/toolkit';
import authReducer from './reducers/authReducer';
import userReducer from './reducers/userReducer';
import requestReducer from './reducers/requestReducer';
import earningReducer from './reducers/earningReducer';
import serviceReducer from './reducers/serviceReducer';
import favouriteReducer from './reducers/favouriteReducer';

const store = configureStore({
  reducer: {
    auth: authReducer,
    user: userReducer,
    requests: requestReducer,
    earnings: earningReducer,
    services: serviceReducer,
    favourites: favouriteReducer,
    
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export default store; 