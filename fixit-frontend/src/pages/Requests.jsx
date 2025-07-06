import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Container,
  Typography,
  Box,
  Tabs,
  Tab,
  Card,
  CardContent,
  Grid,
  Chip,
  Button,
  CircularProgress,
  Alert,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Avatar,
} from '@mui/material';
import {
  Assignment as AssignmentIcon,
  CheckCircle as CheckCircleIcon,
  Cancel as CancelIcon,
  AccessTime as AccessTimeIcon,
  Delete as DeleteIcon,
  Payment as PaymentIcon,
} from '@mui/icons-material';
import { 
  fetchServiceRequests, 
  updateServiceRequest, 
  deleteServiceRequest,
  markCashPaid,
  confirmCashPayment 
} from '../store/actions/requestActions';
import PaymentComponent from '../components/PaymentComponent';

function TabPanel({ children, value, index }) {
  return (
    <div role="tabpanel" hidden={value !== index}>
      {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
    </div>
  );
}

function RequestCard({ request, onUpdateStatus, userRole }) {
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState('');
  const [openWithdrawDialog, setOpenWithdrawDialog] = useState(false);
  const [openCashPaymentDialog, setOpenCashPaymentDialog] = useState(false);
  const [openProviderActionDialog, setOpenProviderActionDialog] = useState(false);
  const [openCashConfirmationDialog, setOpenCashConfirmationDialog] = useState(false);
  const dispatch = useDispatch();

  const handleStatusUpdate = (status) => {
    setSelectedStatus(status);
    if(userRole !== 'CUSTOMER' && (status === 'ACCEPTED' || status === 'CANCELLED')) {
      setOpenProviderActionDialog(true);
    } else if (userRole !== 'CUSTOMER' && (status === 'COMPLETED' || status === 'CASH_PAYMENT_REJECTED')) {
      setOpenCashConfirmationDialog(true);
    }
    else {
      setOpenDialog(true);
    }
  };

  const confirmProviderAction = async () => {
    await onUpdateStatus(request.id, selectedStatus);
    setOpenProviderActionDialog(false);
  }

  const confirmCashConfirmation = async () => {
    await onUpdateStatus(request.id, selectedStatus);
    setOpenCashConfirmationDialog(false);
  };

  const confirmStatusUpdate = async () => {
    await onUpdateStatus(request.id, selectedStatus);
    setOpenDialog(false);
  };

  const handleWithdrawClick = () => {
    setOpenWithdrawDialog(true);
  };

  const confirmWithdraw = async () => {
    await dispatch(deleteServiceRequest(request.id));
    setOpenWithdrawDialog(false);
  };

  const handleCashPaymentClick = () => {
    setOpenCashPaymentDialog(true);
  };

  const confirmCashPayment = async () => {
    const result = await dispatch(markCashPaid(request.id));
    if (result.success) {
      setOpenCashPaymentDialog(false);
    }
  };

  const handleProviderPaymentConfirmation = async (confirmed) => {
    const status = confirmed ? 'COMPLETED' : 'CASH_PAYMENT_REJECTED';
    await onUpdateStatus(request.id, status);
  };

  const handlePaymentSuccess = async (response) => {
    try {
      // Update service request status to COMPLETED after successful payment
      await onUpdateStatus(request.id, 'COMPLETED');
      // You might want to show a success message here
    } catch (error) {
      console.error('Error updating status after payment:', error);
    }
  };

  const handlePaymentError = (error) => {
    console.error('Payment error:', error);
    // You might want to show an error message here
  };

  const getStatusLabel = (status) => {
    const statusMap = {
      PENDING: 'Pending',
      ACCEPTED: 'Accepted',
      COMPLETED: 'Completed',
      CANCELLED: 'Cancelled',
      CASH_PAID_PENDING_CONFIRMATION: 'Cash Payment Pending',
      CASH_PAYMENT_REJECTED: 'Payment Rejected',
    };
    return statusMap[status] || status;
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING':
        return 'warning';
      case 'ACCEPTED':
      case 'CASH_PAYMENT_REJECTED':
        return 'info';
      case 'COMPLETED':
        return 'success';
      case 'CANCELLED':
        return 'error';
      case 'CASH_PAID_PENDING_CONFIRMATION':
        return 'info';
      default:
        return 'default';
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'PENDING':
        return <AccessTimeIcon />;
      case 'ACCEPTED':
      case 'CASH_PAYMENT_REJECTED':
        return <AssignmentIcon />;
      case 'COMPLETED':
        return <CheckCircleIcon />;
      case 'CANCELLED':
        return <CancelIcon />;
      case 'CASH_PAID_PENDING_CONFIRMATION':
        return <PaymentIcon />;
      default:
        return null;
    }
  };

  const otherParty = userRole === 'CUSTOMER' ? request.provider : request.customer;

  return (
    <>
      <Card sx={{ mb: 2, background: '#F8F9FF', borderRadius: 4, boxShadow: 4, border: '1px solid #e0e0e0' }}>
        <CardContent>
          <Box
            sx={{
              display: 'flex',
              flexDirection: { xs: 'column', md: 'row' },
              alignItems: { xs: 'center', md: 'flex-start' },
              gap: 2,
            }}
          >
            {/* Avatar & Name */}
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                width: { xs: '100%', md: 120 },
                flexShrink: 0,
                mb: { xs: 2, md: 0 },
              }}
            >
              <Avatar
                src={otherParty.profileImagePath ? `http://localhost:8080/images/${otherParty.profileImagePath}` : 'http://localhost:8080/images/default.jpg'}
                alt={otherParty.name}
                sx={{ width: 80, height: 80, mb: 1 }}
              />
              <Typography variant="h6" align="center" noWrap sx={{ width: '100%' }}>
                {otherParty.name}
              </Typography>
            </Box>

            {/* Details, Status, Payment */}
            <Box
              sx={{
                display: 'flex',
                flexDirection: { xs: 'column', md: 'row' },
                alignItems: { xs: 'center', md: 'flex-start' },
                flexGrow: 1,
                minWidth: 0,
                width: '100%',
                gap: { xs: 0, md: 2 },
              }}
            >
              {/* Service Details */}
              <Box sx={{ width: { xs: '100%', md: '60%' }, textAlign: { xs: 'center', md: 'left' }, mb: { xs: 1, md: 0 } }}>
                <Typography variant="subtitle1" gutterBottom>
                  Service: {request.serviceName}
                </Typography>
                <Typography variant="body2" color="text.secondary" gutterBottom>
                  Charge: Rs. {request.serviceCharge}
                </Typography>
                <Typography variant="body2" color="text.secondary" gutterBottom>
                  Contact: {request.contact}
                </Typography>
                <Typography variant="body2" color="text.secondary" gutterBottom>
                  Date: {new Date(request.date).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary" gutterBottom sx={{ wordBreak: 'break-word', whiteSpace: 'pre-line' }}>
                  Address: {request.address}
                </Typography>
                {/* Status Chip for mobile only */}
                <Box sx={{ display: { xs: 'flex', md: 'none' }, justifyContent: 'center', mt: 2 }}>
                  <Chip
                    label={getStatusLabel(request.status)}
                    color={getStatusColor(request.status)}
                    icon={getStatusIcon(request.status)}
                    sx={{ fontWeight: 600, fontSize: 16, px: 2, py: 1 }}
                  />
                </Box>
              </Box>
              {/* Payment & Status (right side on desktop, below on mobile) */}
              <Box sx={{ width: { xs: '100%', md: '40%' }, display: 'flex', flexDirection: 'column', alignItems: { xs: 'center', md: 'flex-end' }, justifyContent: 'flex-start', gap: 2 }}>
                {/* Status Chip for desktop only */}
                <Box sx={{ display: { xs: 'none', md: 'flex' }, justifyContent: 'flex-end', width: '100%' }}>
                  <Chip
                    label={getStatusLabel(request.status)}
                    color={getStatusColor(request.status)}
                    icon={getStatusIcon(request.status)}
                    sx={{ fontWeight: 600, fontSize: 16, px: 2, py: 1 }}
                  />
                </Box>
                {/* Provider action buttons */}
                {userRole !== 'CUSTOMER' && request.status === 'PENDING' && (
                  <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, alignItems: 'center', width: { xs: '100%', md: 'auto' }, mt:2 }}>
                    <Button
                      variant="contained"
                      color="success"
                      size="medium"
                      sx={{ minWidth: 140, fontWeight: 600, borderRadius: 2 }}
                      onClick={() => handleStatusUpdate('ACCEPTED')}
                    >
                      Accept
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="medium"
                      sx={{ minWidth: 140, fontWeight: 600, borderRadius: 2 }}
                      onClick={() => handleStatusUpdate('CANCELLED')}
                    >
                      Busy
                    </Button>
                  </Box>
                )}
                {/* Provider cash payment confirmation buttons */}
                {userRole !== 'CUSTOMER' && request.status === 'CASH_PAID_PENDING_CONFIRMATION' && (
                  <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, alignItems: 'center', width: { xs: '100%', md: 'auto' }, mt:2 }}>
                    <Typography variant="subtitle2" color="text.secondary" align="center">
                      Customer has indicated they paid by cash.
                    </Typography>
                    <Button
                      variant="contained"
                      color="success"
                      size="medium"
                      sx={{ minWidth: 140, fontWeight: 600, borderRadius: 2 }}
                      onClick={() => handleStatusUpdate('COMPLETED')}
                    >
                      Confirm Payment
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="medium"
                      sx={{ minWidth: 140, fontWeight: 600, borderRadius: 2 }}
                      onClick={() => handleStatusUpdate('CASH_PAYMENT_REJECTED')}
                    >
                      Reject Payment
                    </Button>
                  </Box>
                )}
                {/* Payment Buttons */}
                {userRole === 'CUSTOMER' && request.status === 'ACCEPTED' && (
                  <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, alignItems: 'center', width: { xs: '100%', md: 'auto' } }}>
                    <Typography variant="subtitle2" color="text.secondary" sx={{ textAlign: 'center' }}>
                      Payment Method
                    </Typography>
                    <Button
                      variant="contained"
                      color="success"
                      size="medium"
                      sx={{ minWidth: 140, fontWeight: 600, borderRadius: 2 }}
                      onClick={handleCashPaymentClick}
                    >
                      Pay by Cash
                    </Button>
                    <PaymentComponent
                      amount={request.serviceCharge}
                      serviceRequestId={request.id}
                      onSuccess={handlePaymentSuccess}
                      onError={handlePaymentError}
                    />
                  </Box>
                )}
              </Box>
            </Box>
          </Box>
        </CardContent>
      </Card>

      {/* Status Update Dialog */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>Confirm Status Update</DialogTitle>
        <DialogContent>
          Are you sure you want to update the status to {selectedStatus}?
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)}>Cancel</Button>
          <Button onClick={confirmStatusUpdate} variant="contained" color="primary">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>

      {/* Provider Cash Confirmation Dialog */}
      <Dialog open={openCashConfirmationDialog} onClose={() => setOpenCashConfirmationDialog(false)}>
        <DialogTitle>Confirm Cash Payment</DialogTitle>
        <DialogContent>
          Are you sure you want to {selectedStatus === 'COMPLETED' ? 'confirm receipt of cash payment' : 'reject the cash payment'}?
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenCashConfirmationDialog(false)}>Cancel</Button>
          <Button onClick={confirmCashConfirmation} variant="contained" color="primary">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>

      {/* Provider Action Dialog */}
      <Dialog open={openProviderActionDialog} onClose={() => setOpenProviderActionDialog(false)}>
        <DialogTitle>Confirm Action</DialogTitle>
        <DialogContent>
          Are you sure you want to {selectedStatus === 'ACCEPTED' ? 'accept' : 'reject'} this service request?
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenProviderActionDialog(false)}>Cancel</Button>
          <Button onClick={confirmProviderAction} variant="contained" color="primary">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>

      {/* Withdraw Dialog */}
      <Dialog open={openWithdrawDialog} onClose={() => setOpenWithdrawDialog(false)}>
        <DialogTitle>Confirm Withdrawal</DialogTitle>
        <DialogContent>
          Are you sure you want to withdraw this service request? This action cannot be undone.
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenWithdrawDialog(false)}>Cancel</Button>
          <Button onClick={confirmWithdraw} variant="contained" color="error">
            Withdraw
          </Button>
        </DialogActions>
      </Dialog>

      {/* Cash Payment Confirmation Dialog */}
      <Dialog open={openCashPaymentDialog} onClose={() => setOpenCashPaymentDialog(false)}>
        <DialogTitle>Confirm Cash Payment</DialogTitle>
        <DialogContent>
          Have you paid the money through cash?
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenCashPaymentDialog(false)}>No</Button>
          <Button onClick={confirmCashPayment} variant="contained" color="primary">
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

function Requests() {
  const [tabValue, setTabValue] = useState(0);
  const dispatch = useDispatch();
  const { requests, loading, error } = useSelector((state) => state.requests);
  const { user } = useSelector((state) => state.auth);

  useEffect(() => {
    dispatch(fetchServiceRequests());
  }, [dispatch]);

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
  };

  const handleStatusUpdate = async (requestId, status) => {
    await dispatch(updateServiceRequest(requestId, status));
  };

  const filteredRequests = requests.filter((request) => {
    switch (tabValue) {
      case 0:
        return request.status === 'PENDING';
      case 1:
        return request.status === 'ACCEPTED' || 
               request.status === 'CASH_PAID_PENDING_CONFIRMATION';
      case 2:
        return request.status === 'COMPLETED';
      default:
        return true;
    }
  });

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
      {/* Main Heading */}
      <Typography variant="h3" sx={{ fontWeight: 700, mb: 3, letterSpacing: 1, color: '#fff', textAlign: 'center', textShadow: '0 2px 8px rgba(0,0,0,0.18)', fontFamily: 'Playfair Display, cursive' }}>
        Service Requests
      </Typography>

      {/* Tabs */}
      <Box sx={{ display: 'flex', justifyContent: 'center', mb: 3 }}>
        <Tabs
          value={tabValue}
          onChange={handleTabChange}
          variant="standard"
          TabIndicatorProps={{ style: { display: 'none' } }}
          sx={{
            background: 'rgba(255,255,255,0.7)',
            borderRadius: 3,
            boxShadow: 1,
            p: 0.5,
            minHeight: 48,
            '& .MuiTab-root': {
              fontWeight: 600,
              fontSize: { xs: 14, sm: 15, md: 18 },
              borderRadius: 2,
              minWidth: { xs: 80, sm: 100, md: 140 },
              minHeight: 36,
              mx: { xs: 0.5, md: 1 },
              color: '#555',
              transition: 'background 0.2s',
              px: { xs: 1, md: 2 },
            },
            '& .MuiTab-root.Mui-selected': {
              background: 'linear-gradient(90deg, #1976d2 60%, #534bae 100%)',
              color: '#fff !important',
              textShadow: '0 0 4px rgba(0, 0, 0, 0.5)',
            },
          }}
        >
          <Tab label="Pending" />
          <Tab label="Accepted" />
          <Tab label="Completed" />
        </Tabs>
      </Box>

      <TabPanel value={tabValue} index={0}>
        {filteredRequests.length === 0 ? (
          <Typography sx={{ fontSize: 22, fontWeight: 600, color: '#fff', textAlign: 'center', my: 4 }}>
            No requests available in this section.
          </Typography>
        ) : (
          filteredRequests.map((request) => (
            <RequestCard
              key={request.id}
              request={request}
              onUpdateStatus={handleStatusUpdate}
              userRole={user.role}
            />
          ))
        )}
      </TabPanel>

      <TabPanel value={tabValue} index={1}>
        {filteredRequests.length === 0 ? (
          <Typography sx={{ fontSize: 22, fontWeight: 600, color: '#fff', textAlign: 'center', my: 4 }}>
            No requests available in this section.
          </Typography>
        ) : (
          filteredRequests.map((request) => (
            <RequestCard
              key={request.id}
              request={request}
              onUpdateStatus={handleStatusUpdate}
              userRole={user.role}
            />
          ))
        )}
      </TabPanel>

      <TabPanel value={tabValue} index={2}>
        {filteredRequests.length === 0 ? (
          <Typography sx={{ fontSize: 22, fontWeight: 600, color: '#fff', textAlign: 'center', my: 4 }}>
            No requests available in this section.
          </Typography>
        ) : (
          filteredRequests.map((request) => (
            <RequestCard
              key={request.id}
              request={request}
              onUpdateStatus={handleStatusUpdate}
              userRole={user.role}
            />
          ))
        )}
      </TabPanel>
    </Container>
  );
}

export default Requests; 