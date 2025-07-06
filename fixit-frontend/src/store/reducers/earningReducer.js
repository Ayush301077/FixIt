import {
  FETCH_EARNINGS_REQUEST,
  FETCH_EARNINGS_SUCCESS,
  FETCH_EARNINGS_FAILURE,
} from '../actionTypes';

const initialState = {
  completedServices: [],
  totalAmount: 0,
  completedServicesCount: 0,
  loading: false,
  error: null,
};

const earningReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_EARNINGS_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    case FETCH_EARNINGS_SUCCESS:
      return {
        ...state,
        completedServices: action.payload.completedServices || [],
        totalAmount: action.payload.totalEarnings || 0,
        completedServicesCount: action.payload.completedServicesCount || 0,
        loading: false,
        error: null,
      };

    case FETCH_EARNINGS_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    default:
      return state;
  }
};

export default earningReducer;