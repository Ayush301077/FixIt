import axios from 'axios';
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

const API_URL = 'http://localhost:8080/api';

// Action to fetch all services for the authenticated provider
export const fetchServices = () => async (dispatch) => {
  dispatch({ type: FETCH_SERVICES_REQUEST });
  try {
    const token = localStorage.getItem('token');
    const response = await axios.get(`${API_URL}/services`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    dispatch({ type: FETCH_SERVICES_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({
      type: FETCH_SERVICES_FAILURE,
      payload: error.response?.data?.error || 'Failed to fetch services',
    });
  }
};

// Action to add a new service
export const addService = (serviceData) => async (dispatch) => {
  dispatch({ type: ADD_SERVICE_REQUEST });
  try {
    const token = localStorage.getItem('token');
    const response = await axios.post(`${API_URL}/services`, serviceData, {
      headers: { Authorization: `Bearer ${token}` },
    });
    dispatch({ type: ADD_SERVICE_SUCCESS, payload: response.data });
    return { success: true, payload: response.data };
  } catch (error) {
    const errorMessage = error.response?.data?.error || 'Failed to add service';
    dispatch({ type: ADD_SERVICE_FAILURE, payload: errorMessage });
    return { success: false, error: errorMessage };
  }
};

// Action to update an existing service
export const updateService = (serviceId, serviceData) => async (dispatch) => {
  dispatch({ type: UPDATE_SERVICE_REQUEST });
  try {
    const token = localStorage.getItem('token');
    const response = await axios.put(`${API_URL}/services/${serviceId}`, serviceData, {
      headers: { Authorization: `Bearer ${token}` },
    });
    dispatch({ type: UPDATE_SERVICE_SUCCESS, payload: response.data });
    return { success: true, payload: response.data };
  } catch (error) {
    const errorMessage = error.response?.data?.error || 'Failed to update service';
    dispatch({ type: UPDATE_SERVICE_FAILURE, payload: errorMessage });
    return { success: false, error: errorMessage };
  }
};

// Action to delete a service
export const deleteService = (serviceId) => async (dispatch) => {
  dispatch({ type: DELETE_SERVICE_REQUEST });
  try {
    const token = localStorage.getItem('token');
    await axios.delete(`${API_URL}/services/${serviceId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    dispatch({ type: DELETE_SERVICE_SUCCESS, payload: serviceId });
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.error || 'Failed to delete service';
    dispatch({ type: DELETE_SERVICE_FAILURE, payload: errorMessage });
    return { success: false, error: errorMessage };
  }
}; 