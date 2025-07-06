import {
  FETCH_SERVICE_REQUESTS_REQUEST,
  FETCH_SERVICE_REQUESTS_SUCCESS,
  FETCH_SERVICE_REQUESTS_FAILURE,
  CREATE_SERVICE_REQUEST,
  CREATE_SERVICE_SUCCESS,
  CREATE_SERVICE_FAILURE,
  UPDATE_SERVICE_REQUEST,
  UPDATE_SERVICE_SUCCESS,
  UPDATE_SERVICE_FAILURE,
  DELETE_SERVICE_SUCCESS,
  MARK_CASH_PAID_REQUEST,
  MARK_CASH_PAID_SUCCESS,
  MARK_CASH_PAID_FAILURE,
  CONFIRM_CASH_PAYMENT_REQUEST,
  CONFIRM_CASH_PAYMENT_SUCCESS,
  CONFIRM_CASH_PAYMENT_FAILURE,
} from '../actionTypes';

const initialState = {
  requests: [],
  loading: false,
  error: null,
};

const requestReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_SERVICE_REQUESTS_REQUEST:
    case CREATE_SERVICE_REQUEST:
    case UPDATE_SERVICE_REQUEST:
    case MARK_CASH_PAID_REQUEST:
    case CONFIRM_CASH_PAYMENT_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    case FETCH_SERVICE_REQUESTS_SUCCESS:
      return {
        ...state,
        loading: false,
        requests: action.payload,
        error: null,
      };

    case CREATE_SERVICE_SUCCESS:
      return {
        ...state,
        loading: false,
        requests: [...state.requests, action.payload],
        error: null,
      };

    case UPDATE_SERVICE_SUCCESS:
    case MARK_CASH_PAID_SUCCESS:
    case CONFIRM_CASH_PAYMENT_SUCCESS:
      return {
        ...state,
        loading: false,
        requests: state.requests.map((request) =>
          request.id === action.payload.id ? action.payload : request
        ),
        error: null,
      };

    case FETCH_SERVICE_REQUESTS_FAILURE:
    case CREATE_SERVICE_FAILURE:
    case UPDATE_SERVICE_FAILURE:
    case MARK_CASH_PAID_FAILURE:
    case CONFIRM_CASH_PAYMENT_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    case DELETE_SERVICE_SUCCESS:
      return {
        ...state,
        loading: false,
        requests: state.requests.filter((request) => request.id !== action.payload),
        error: null,
      };

    default:
      return state;
  }
};

export default requestReducer; 