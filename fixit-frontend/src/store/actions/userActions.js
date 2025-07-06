// actions/userActions.js
import axios from 'axios';
import {
  FETCH_USER_REQUEST,
  FETCH_USER_SUCCESS,
  FETCH_USER_FAILURE,
  UPDATE_PROFILE_REQUEST,
  UPDATE_PROFILE_SUCCESS,
  UPDATE_PROFILE_FAILURE,
  UPLOAD_PROFILE_IMAGE_REQUEST,
  UPLOAD_PROFILE_IMAGE_SUCCESS,
  UPLOAD_PROFILE_IMAGE_FAILURE,
} from '../actionTypes';

const API_URL = 'http://localhost:8080/api';

// Fetch user profile
export const fetchUserProfile = () => async (dispatch) => {
  try {
    dispatch({ type: FETCH_USER_REQUEST });
    const response = await axios.get(`${API_URL}/users/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    dispatch({
      type: FETCH_USER_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: FETCH_USER_FAILURE,
      payload: error.response?.data?.message || 'Failed to fetch profile',
    });
  }
};

// Update user profile (excluding image)
export const updateProfile = (profileData) => async (dispatch) => {
  try {
    dispatch({ type: UPDATE_PROFILE_REQUEST });
    const response = await axios.put(`${API_URL}/users/profile`, profileData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
    });
    dispatch({
      type: UPDATE_PROFILE_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: UPDATE_PROFILE_FAILURE,
      payload: error.response?.data?.message || 'Failed to update profile',
    });
  }
};

// Upload profile image (multipart/form-data)
export const uploadProfileImage = (formData) => async (dispatch) => {
  try {
    dispatch({ type: UPLOAD_PROFILE_IMAGE_REQUEST });
    const response = await axios.post('http://localhost:8080/api/profile/image', formData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'multipart/form-data',
      },
    });
    dispatch({
      type: UPLOAD_PROFILE_IMAGE_SUCCESS,
      payload: response.data,
    });
    return { payload: response.data };
  } catch (error) {
    dispatch({
      type: UPLOAD_PROFILE_IMAGE_FAILURE,
      payload: error.response?.data?.message || 'Failed to upload image',
    });
  }
};
