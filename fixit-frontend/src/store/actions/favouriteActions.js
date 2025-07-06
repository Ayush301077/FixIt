import axios from 'axios';
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

const API_URL = 'http://localhost:8080/api';

export const addFavourite = (providerId) => async (dispatch) => {
  try {
    dispatch({ type: ADD_FAVOURITE_REQUEST });
    await axios.post(`${API_URL}/favourites/add/${providerId}`, null, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    dispatch({ type: ADD_FAVOURITE_SUCCESS, payload: providerId });
    return { success: true };
  } catch (error) {
    dispatch({
      type: ADD_FAVOURITE_FAILURE,
      payload: error.response?.data?.message || 'Failed to add favourite',
    });
    return { success: false, error: error.response?.data?.message || 'Failed to add favourite' };
  }
};

export const removeFavourite = (providerId) => async (dispatch) => {
  try {
    dispatch({ type: REMOVE_FAVOURITE_REQUEST });
    await axios.delete(`${API_URL}/favourites/remove/${providerId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    dispatch({ type: REMOVE_FAVOURITE_SUCCESS, payload: providerId });
    return { success: true };
  } catch (error) {
    dispatch({
      type: REMOVE_FAVOURITE_FAILURE,
      payload: error.response?.data?.message || 'Failed to remove favourite',
    });
    return { success: false, error: error.response?.data?.message || 'Failed to remove favourite' };
  }
};

export const fetchFavourites = () => async (dispatch) => {
  try {
    dispatch({ type: FETCH_FAVOURITES_REQUEST });
    const response = await axios.get(`${API_URL}/favourites`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    console.log("API Response data for favourites:", response.data);
    dispatch({ type: FETCH_FAVOURITES_SUCCESS, payload: response.data });
  } catch (error) {
    console.error("Error fetching favourites:", error);
    dispatch({
      type: FETCH_FAVOURITES_FAILURE,
      payload: error.response?.data?.message || 'Failed to fetch favourites',
    });
  }
}; 