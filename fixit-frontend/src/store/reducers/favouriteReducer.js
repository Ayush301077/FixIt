import {
  ADD_FAVOURITE_REQUEST,
  ADD_FAVOURITE_SUCCESS,
  ADD_FAVOURITE_FAILURE,
  REMOVE_FAVOURITE_REQUEST,
  REMOVE_FAVOURITE_SUCCESS,
  REMOVE_FAVOURITE_FAILURE,
  FETCH_FAVOURITES_REQUEST,
  FETCH_FAVOURITES_SUCCESS,
  FETCH_FAVOURITES_FAILURE,
} from '../actionTypes';

const initialState = {
  favourites: [],
  loading: false,
  error: null,
};

const favouriteReducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_FAVOURITE_REQUEST:
    case REMOVE_FAVOURITE_REQUEST:
    case FETCH_FAVOURITES_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    case ADD_FAVOURITE_SUCCESS:
      return {
        ...state,
        loading: false,
        // Assuming payload is the providerId that was added
        // You might want to re-fetch the list or add the provider object if available
        // For now, we'll assume the add was successful and a re-fetch will occur
        error: null,
      };

    case REMOVE_FAVOURITE_SUCCESS:
      return {
        ...state,
        loading: false,
        favourites: state.favourites.filter(
          (provider) => provider.id !== action.payload
        ),
        error: null,
      };

    case FETCH_FAVOURITES_SUCCESS:
      return {
        ...state,
        loading: false,
        favourites: action.payload,
        error: null,
      };

    case ADD_FAVOURITE_FAILURE:
    case REMOVE_FAVOURITE_FAILURE:
    case FETCH_FAVOURITES_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    default:
      return state;
  }
};

export default favouriteReducer; 