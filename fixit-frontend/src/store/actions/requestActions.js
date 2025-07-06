import axiosInstance from '../../utils/axiosConfig';
import {
  FETCH_SERVICE_REQUESTS_REQUEST,
  FETCH_SERVICE_REQUESTS_SUCCESS,
  FETCH_SERVICE_REQUESTS_FAILURE,
  UPDATE_SERVICE_REQUEST,
  UPDATE_SERVICE_SUCCESS,
  UPDATE_SERVICE_FAILURE,
  CREATE_SERVICE_REQUEST,
  CREATE_SERVICE_SUCCESS,
  CREATE_SERVICE_FAILURE,
  DELETE_SERVICE_REQUEST,
  DELETE_SERVICE_SUCCESS,
  DELETE_SERVICE_FAILURE,
  MARK_CASH_PAID_REQUEST,
  MARK_CASH_PAID_SUCCESS,
  MARK_CASH_PAID_FAILURE,
  CONFIRM_CASH_PAYMENT_REQUEST,
  CONFIRM_CASH_PAYMENT_SUCCESS,
  CONFIRM_CASH_PAYMENT_FAILURE,
} from '../actionTypes';

// Fetch Service Requests
export const fetchServiceRequestsRequest = () => ({
  type: FETCH_SERVICE_REQUESTS_REQUEST,
});

export const fetchServiceRequestsSuccess = (requests) => ({
  type: FETCH_SERVICE_REQUESTS_SUCCESS,
  payload: requests,
});

export const fetchServiceRequestsFailure = (error) => ({
  type: FETCH_SERVICE_REQUESTS_FAILURE,
  payload: error,
});

export const fetchServiceRequests = () => async (dispatch, getState) => {
  try {
    dispatch(fetchServiceRequestsRequest());
    const { user } = getState().auth;
    const endpoint = user.role === 'CUSTOMER' ? '/api/service-requests/customer' : '/api/service-requests/provider';
    const response = await axiosInstance.get(endpoint);
    dispatch(fetchServiceRequestsSuccess(response.data));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to fetch service requests';
    dispatch(fetchServiceRequestsFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Create Service Request
export const createServiceRequestRequest = () => ({
  type: CREATE_SERVICE_REQUEST,
});

export const createServiceRequestSuccess = (request) => ({
  type: CREATE_SERVICE_SUCCESS,
  payload: request,
});

export const createServiceRequestFailure = (error) => ({
  type: CREATE_SERVICE_FAILURE,
  payload: error,
});

export const createServiceRequest = (requestData) => async (dispatch) => {
  console.log('Received requestData in createServiceRequest thunk:', requestData);
  try {
    const url = '/api/service-requests';
    console.log(`Attempting to make POST request to: ${axiosInstance.defaults.baseURL}${url} with data:`, requestData);
    dispatch(createServiceRequestRequest());
    const response = await axiosInstance.post(url, requestData);
    console.log('API call successful, response:', response.data);
    dispatch(createServiceRequestSuccess(response.data));
    return { success: true };
  } catch (error) {
    console.error('Error during API call to create service request:', error.response?.data || error.message);
    const errorMessage = error.response?.data?.message || 'Failed to create service request';
    dispatch(createServiceRequestFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Update Service Request
export const updateServiceRequestRequest = () => ({
  type: UPDATE_SERVICE_REQUEST,
});

export const updateServiceRequestSuccess = (request) => ({
  type: UPDATE_SERVICE_SUCCESS,
  payload: request,
});

export const updateServiceRequestFailure = (error) => ({
  type: UPDATE_SERVICE_FAILURE,
  payload: error,
});

export const updateServiceRequest = (requestId, status) => async (dispatch) => {
  try {
    dispatch(updateServiceRequestRequest());
    const response = await axiosInstance.patch(`/api/service-requests/${requestId}/status`, null, {
      params: { status },
    });
    dispatch(updateServiceRequestSuccess(response.data));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to update service request';
    dispatch(updateServiceRequestFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Delete Service Request
export const deleteServiceRequestRequest = () => ({
  type: DELETE_SERVICE_REQUEST,
});

export const deleteServiceRequestSuccess = (requestId) => ({
  type: DELETE_SERVICE_SUCCESS,
  payload: requestId,
});

export const deleteServiceRequestFailure = (error) => ({
  type: DELETE_SERVICE_FAILURE,
  payload: error,
});

export const deleteServiceRequest = (requestId) => async (dispatch) => {
  try {
    dispatch(deleteServiceRequestRequest());
    await axiosInstance.delete(`/api/service-requests/${requestId}`);
    dispatch(deleteServiceRequestSuccess(requestId));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to delete service request';
    dispatch(deleteServiceRequestFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Mark Cash Paid (Customer side)
export const markCashPaidRequest = () => ({
  type: MARK_CASH_PAID_REQUEST,
});

export const markCashPaidSuccess = (request) => ({
  type: MARK_CASH_PAID_SUCCESS,
  payload: request,
});

export const markCashPaidFailure = (error) => ({
  type: MARK_CASH_PAID_FAILURE,
  payload: error,
});

export const markCashPaid = (requestId) => async (dispatch) => {
  try {
    dispatch(markCashPaidRequest());
    const response = await axiosInstance.patch(`/api/service-requests/${requestId}/status?status=CASH_PAID_PENDING_CONFIRMATION`);
    dispatch(markCashPaidSuccess(response.data));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to mark cash as paid';
    dispatch(markCashPaidFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
};

// Confirm Cash Payment (Provider side)
export const confirmCashPaymentRequest = () => ({
  type: CONFIRM_CASH_PAYMENT_REQUEST,
});

export const confirmCashPaymentSuccess = (request) => ({
  type: CONFIRM_CASH_PAYMENT_SUCCESS,
  payload: request,
});

export const confirmCashPaymentFailure = (error) => ({
  type: CONFIRM_CASH_PAYMENT_FAILURE,
  payload: error,
});

export const confirmCashPayment = (requestId, status) => async (dispatch) => {
  try {
    dispatch(confirmCashPaymentRequest());
    const response = await axiosInstance.patch(`/api/service-requests/${requestId}/status?status=${status}`);
    dispatch(confirmCashPaymentSuccess(response.data));
    return { success: true };
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to confirm cash payment';
    dispatch(confirmCashPaymentFailure(errorMessage));
    return {
      success: false,
      error: errorMessage,
    };
  }
}; 