import {
  FETCH_SERVICES_REQUEST,
  FETCH_SERVICES_SUCCESS,
  FETCH_SERVICES_FAILURE,
  ADD_SERVICE_REQUEST,
  ADD_SERVICE_SUCCESS,
  ADD_SERVICE_FAILURE,
  UPDATE_SERVICE_REQUEST,
  UPDATE_SERVICE_SUCCESS,
  UPDATE_SERVICE_FAILURE,
  DELETE_SERVICE_REQUEST,
  DELETE_SERVICE_SUCCESS,
  DELETE_SERVICE_FAILURE,
} from '../actionTypes';

const initialState = {
  services: [],
  loading: false,
  error: null,
};

const serviceReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_SERVICES_REQUEST:
    case ADD_SERVICE_REQUEST:
    case UPDATE_SERVICE_REQUEST:
    case DELETE_SERVICE_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    case FETCH_SERVICES_SUCCESS:
      return {
        ...state,
        loading: false,
        services: action.payload,
        error: null,
      };

    case ADD_SERVICE_SUCCESS:
      return {
        ...state,
        loading: false,
        services: [...state.services, action.payload],
        error: null,
      };

    case UPDATE_SERVICE_SUCCESS:
      return {
        ...state,
        loading: false,
        services: state.services.map((service) =>
          service.id === action.payload.id ? action.payload : service
        ),
        error: null,
      };

    case DELETE_SERVICE_SUCCESS:
      return {
        ...state,
        loading: false,
        services: state.services.filter((service) => service.id !== action.payload),
        error: null,
      };

    case FETCH_SERVICES_FAILURE:
    case ADD_SERVICE_FAILURE:
    case UPDATE_SERVICE_FAILURE:
    case DELETE_SERVICE_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    default:
      return state;
  }
};

export default serviceReducer; 