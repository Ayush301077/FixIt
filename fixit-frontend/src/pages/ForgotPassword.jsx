import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import axiosInstance from '../utils/axiosConfig';
import forgotPasswordImage from '../assets/forgot_password_img.jpg'; // Ensure the path is correct
import bgImg from '../assets/bg_img.avif';

const ForgotPassword = () => {
  const [step, setStep] = useState(1); // 1: Email, 2: OTP, 3: Reset Password
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axiosInstance.post('/api/auth/forgot-password', { email });
      if (response.data.success) {
        toast.success(response.data.message);
        setStep(2);
      } else {
        toast.error(response.data.message);
      }
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to send OTP');
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyOtp = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axiosInstance.post('/api/auth/verify-otp', { email, otp });
      if (response.data.success) {
        toast.success(response.data.message);
        setStep(3);
      } else {
        toast.error(response.data.message);
      }
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to verify OTP');
    } finally {
      setLoading(false);
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    setLoading(true);
    if (newPassword !== confirmNewPassword) {
      toast.error('New password and confirm password do not match.');
      setLoading(false);
      return;
    }

    try {
      const response = await axiosInstance.post('/api/auth/reset-password', { email, newPassword, confirmNewPassword });
      if (response.data.success) {
        toast.success(response.data.message);
        navigate('/login');
      } else {
        toast.error(response.data.message);
      }
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to change password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center p-4"
      style={{
        backgroundImage: `url(${bgImg})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundAttachment: 'fixed',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <div className="w-full max-w-5xl flex flex-col md:flex-row rounded-lg shadow-lg overflow-hidden h-auto md:h-[70vh]">
        <div className="w-full md:w-1/2 bg-cover bg-center" style={{ backgroundImage: `url(${forgotPasswordImage})` }}>
          {/* Image section */}
        </div>
        <div className="w-full md:w-1/2 bg-white p-8 flex flex-col justify-center">
          <div className="max-w-md w-full mx-auto">
            <h2 className="text-center text-3xl font-extrabold text-gray-900 mb-6">
              Forgot Password
            </h2>

            {step === 1 && (
              <form className="space-y-6" onSubmit={handleSendOtp}>
                <div className="rounded-md shadow-sm">
                  <div>
                    <label htmlFor="email" className="sr-only">Email address</label>
                    <input
                      id="email"
                      name="email"
                      type="email"
                      autoComplete="email"
                      required
                      className="appearance-none rounded relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Enter your email address"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </div>
                </div>
                <div>
                  <button
                    type="submit"
                    disabled={loading}
                    className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    {loading ? 'Sending OTP...' : 'Send OTP'}
                  </button>
                </div>
                <div className="text-center mt-4">
                  <Link to="/login" className="font-medium text-indigo-600 hover:text-indigo-500 text-sm">
                    Back to Login
                  </Link>
                </div>
              </form>
            )}

            {step === 2 && (
              <form className="space-y-6" onSubmit={handleVerifyOtp}>
                <div className="rounded-md shadow-sm">
                  <div>
                    <label htmlFor="otp" className="sr-only">OTP</label>
                    <input
                      id="otp"
                      name="otp"
                      type="text"
                      maxLength="6"
                      required
                      className="appearance-none rounded relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Enter the OTP"
                      value={otp}
                      onChange={(e) => setOtp(e.target.value)}
                    />
                  </div>
                </div>
                <div>
                  <button
                    type="submit"
                    disabled={loading}
                    className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    {loading ? 'Verifying OTP...' : 'Verify OTP'}
                  </button>
                </div>
                <div className="text-center mt-4">
                  <Link to="/login" className="font-medium text-indigo-600 hover:text-indigo-500 text-sm">
                    Back to Login
                  </Link>
                </div>
              </form>
            )}

            {step === 3 && (
              <form className="space-y-6" onSubmit={handleChangePassword}>
                <div className="rounded-md shadow-sm space-y-4">
                  <div>
                    <label htmlFor="newPassword" className="sr-only">New Password</label>
                    <input
                      id="newPassword"
                      name="newPassword"
                      type="password"
                      autoComplete="new-password"
                      required
                      className="appearance-none rounded relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Enter new password"
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                    />
                  </div>
                  <div>
                    <label htmlFor="confirmNewPassword" className="sr-only">Confirm New Password</label>
                    <input
                      id="confirmNewPassword"
                      name="confirmNewPassword"
                      type="password"
                      autoComplete="new-password"
                      required
                      className="appearance-none rounded relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                      placeholder="Confirm new password"
                      value={confirmNewPassword}
                      onChange={(e) => setConfirmNewPassword(e.target.value)}
                    />
                  </div>
                </div>
                <div>
                  <button
                    type="submit"
                    disabled={loading}
                    className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    {loading ? 'Changing Password...' : 'Change Password'}
                  </button>
                </div>
                <div className="text-center mt-4">
                  <Link to="/login" className="font-medium text-indigo-600 hover:text-indigo-500 text-sm">
                    Back to Login
                  </Link>
                </div>
              </form>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
