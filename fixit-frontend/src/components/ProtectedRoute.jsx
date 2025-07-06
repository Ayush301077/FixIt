import { Navigate, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { CircularProgress, Box } from '@mui/material';

const ProtectedRoute = ({ children, requiredRole }) => {
  const { isAuthenticated, loading, user } = useSelector((state) => state.auth);
  const location = useLocation();

  if (loading) {
    return (
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          minHeight: '100vh',
        }}
      >
        <CircularProgress />
      </Box>
    );
  }

  if (!isAuthenticated) {
    // Redirect to landing page but save the attempted url
    return <Navigate to="/" state={{ from: location }} replace />;
  }

  // Check if the user is trying to access the Earnings page and is not a provider
  if (location.pathname === '/earnings' && user?.role?.toUpperCase() !== 'PROVIDER') {
    return <Navigate to="/dashboard" replace />;
  }

  // Role-based protection
  if (requiredRole && user?.role?.toUpperCase() !== requiredRole.toUpperCase()) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

export default ProtectedRoute; 