import axios from 'axios';
import {
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  LOGOUT,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  REGISTER_FAILURE,
  SET_TOKEN,
} from '../actionTypes';

const API_URL = 'http://localhost:8080/api';

// Configure axios defaults
axios.defaults.headers.common['Content-Type'] = 'application/json';

// Login Actions
export const loginRequest = () => ({
  type: LOGIN_REQUEST,
});

export const loginSuccess = (user, token) => ({
  type: LOGIN_SUCCESS,
  payload: { user, token },
});

export const loginFailure = (error) => ({
  type: LOGIN_FAILURE,
  payload: error,
});

export const setToken = (token) => ({
  type: SET_TOKEN,
  payload: token,
});

export const login = (email, password) => async (dispatch) => {
  try {
    dispatch(loginRequest());
    const response = await axios.post(`${API_URL}/auth/login`, {
      email: email,
      password: password
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    const { token, user } = response.data;
    
    // Store token in localStorage
    localStorage.setItem('token', token);
    
    // Update axios default headers
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    
    dispatch(loginSuccess(user, token));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Login failed';
    dispatch(loginFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Register Actions
export const registerRequest = () => ({
  type: REGISTER_REQUEST,
});

export const registerSuccess = (user, token) => ({
  type: REGISTER_SUCCESS,
  payload: { user, token },
});

export const registerFailure = (error) => ({
  type: REGISTER_FAILURE,
  payload: error,
});

export const register = (userData) => async (dispatch) => {
  try {
    dispatch(registerRequest());
    const response = await axios.post(`${API_URL}/auth/register`, userData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    const { token, user } = response.data;
    
    // Store token in localStorage
    localStorage.setItem('token', token);
    
    // Update axios default headers
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    
    dispatch(registerSuccess(user, token));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Registration failed';
    dispatch(registerFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Logout Action
export const logout = () => {
  localStorage.removeItem('token');
  delete axios.defaults.headers.common['Authorization'];
  return {
    type: LOGOUT
  };
};

// Check authentication status on app load
export const checkAuth = () => async (dispatch) => {
  const token = localStorage.getItem('token');

  if (token) {
    dispatch(loginRequest()); // Dispatch request to set loading to true
    try {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const response = await axios.get(`${API_URL}/auth/verify`);
      if (response.data.success && response.data.user) {
        dispatch(loginSuccess(response.data.user, token));
      } else {
        dispatch(logout());
      }
    } catch (error) {
      dispatch(logout());
    }
  } else {
    dispatch(logout()); // Ensure user state is cleared if no token
  }
};