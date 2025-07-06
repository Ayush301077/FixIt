import axiosInstance from '../../utils/axiosConfig';
import {
  FETCH_EARNINGS_REQUEST,
  FETCH_EARNINGS_SUCCESS,
  FETCH_EARNINGS_FAILURE,
} from '../actionTypes';

// Fetch Earnings
export const fetchEarningsRequest = () => ({
  type: FETCH_EARNINGS_REQUEST,
});

export const fetchEarningsSuccess = (data) => ({
  type: FETCH_EARNINGS_SUCCESS,
  payload: {
    completedServices: data.completedServices,
    totalEarnings: data.totalEarnings,
    completedServicesCount: data.completedServicesCount
  },
});

export const fetchEarningsFailure = (error) => ({
  type: FETCH_EARNINGS_FAILURE,
  payload: error,
});

export const fetchEarnings = () => async (dispatch) => {
  try {
    dispatch(fetchEarningsRequest());
    const response = await axiosInstance.get('/api/earnings');
    dispatch(fetchEarningsSuccess(response.data));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to fetch earnings';
    dispatch(fetchEarningsFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};