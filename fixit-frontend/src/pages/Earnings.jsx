import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  CircularProgress,
  Alert,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';
import {
  TrendingUp as TrendingUpIcon,
  CalendarToday as CalendarTodayIcon,
  AttachMoney as MoneyIcon,
} from '@mui/icons-material';
import { fetchEarnings } from '../store/actions/earningActions';

function Earnings() {
  const dispatch = useDispatch();
  const { completedServices, totalAmount, completedServicesCount, loading, error } = useSelector((state) => state.earnings);

  useEffect(() => {
    dispatch(fetchEarnings());
  }, [dispatch]);

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const formatCurrency = (amount) => {
    // Format as Rs. with comma separators
    if (typeof amount !== 'number') return amount;
    return `Rs. ${amount.toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Alert severity="error">{error}</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      {/* Styled Title */}
      <Typography variant="h3" sx={{ fontWeight: 700, mb: 3, letterSpacing: 1, color: '#fff', textAlign: 'center', textShadow: '0 2px 8px rgba(0,0,0,0.18)', fontFamily: 'Playfair Display, cursive' }}>
        Earnings Overview
      </Typography>

      {/* Summary Cards */}
      <Grid container spacing={3} sx={{ mb: 4 }}>
        <Grid item xs={12} md={4}>
          <Paper
            elevation={3}
            sx={{
              p: 3,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              bgcolor: '#e3f2fd',
              borderRadius: 4,
            }}
          >
            <MoneyIcon sx={{ fontSize: 40, color: '#1976d2', mb: 1 }} />
            <Typography variant="h6" color="text.secondary" gutterBottom>
              Total Earnings
            </Typography>
            <Typography variant="h4" component="div">
              {formatCurrency(totalAmount)}
            </Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} md={4}>
          <Paper
            elevation={3}
            sx={{
              p: 3,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              bgcolor: '#e8f5e9',
              borderRadius: 4,
            }}
          >
            <TrendingUpIcon sx={{ fontSize: 40, color: '#2e7d32', mb: 1 }} />
            <Typography variant="h6" color="text.secondary" gutterBottom>
              Completed Services
            </Typography>
            <Typography variant="h4" component="div">
              {completedServicesCount}
            </Typography>
          </Paper>
        </Grid>
        <Grid item xs={12} md={4}>
          <Paper
            elevation={3}
            sx={{
              p: 3,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              bgcolor: '#fff3e0',
              borderRadius: 4,
            }}
          >
            <CalendarTodayIcon sx={{ fontSize: 40, color: '#ed6c02', mb: 1 }} />
            <Typography variant="h6" color="text.secondary" gutterBottom>
              Average per Service
            </Typography>
            <Typography variant="h4" component="div">
              {formatCurrency(completedServicesCount > 0 ? totalAmount / completedServicesCount : 0)}
            </Typography>
          </Paper>
        </Grid>
      </Grid>

      {/* Earnings Table */}
      <TableContainer component={Paper} elevation={3} sx={{ borderRadius: 4 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Date</TableCell>
              <TableCell>Customer Name</TableCell>
              <TableCell>Contact</TableCell>
              <TableCell>Service</TableCell>
              <TableCell>Charge</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {completedServices.length === 0 ? (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  <Typography variant="body1" color="text.secondary">
                    No earnings data available
                  </Typography>
                </TableCell>
              </TableRow>
            ) : (
              completedServices.map((service) => (
                <TableRow key={service.id || service.date}>
                  <TableCell>{formatDate(service.date)}</TableCell>
                  <TableCell>{service.customerName}</TableCell>
                  <TableCell>{service.customerContact}</TableCell>
                  <TableCell>{service.serviceName}</TableCell>
                  <TableCell>{formatCurrency(service.serviceCharge)}</TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
}

export default Earnings; 