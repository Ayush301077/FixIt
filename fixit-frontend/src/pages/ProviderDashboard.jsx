import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Grid,
  Paper,
  Typography,
  Box,
  Card,
  CardContent,
  CardActionArea,
  CardMedia,
  Button,
} from '@mui/material';
import {
  Assignment as AssignmentIcon,
  AttachMoney as MoneyIcon,
  Person as PersonIcon,
} from '@mui/icons-material';
import { useEffect, useState } from 'react';
import { fetchServiceRequests } from '../store/actions/requestActions';
import { fetchEarnings } from '../store/actions/earningActions'; // Import fetchEarnings action

function ProviderDashboard() {
  const { user } = useSelector((state) => state.auth);
  const { requests } = useSelector((state) => state.requests);
  // Select totalAmount and completedServicesCount from the earnings state
  const { totalAmount, completedServicesCount } = useSelector((state) => state.earnings);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const dashboardItems = [
    {
      title: 'Service Requests',
      description: 'View and manage your service requests',
      icon: <AssignmentIcon sx={{ fontSize: 60 }} />,
      path: '/requests',
      color: '#1976d2',
    },
    {
      title: 'Earnings',
      description: 'Track your earnings and payment history',
      icon: <MoneyIcon sx={{ fontSize: 60 }} />,
      path: '/earnings',
      color: '#2e7d32',
    },
    {
      title: 'Profile',
      description: 'Update your profile information',
      icon: <PersonIcon sx={{ fontSize: 60 }} />,
      path: '/profile',
      color: '#ed6c02',
    },
  ];

  useEffect(() => {
    dispatch(fetchServiceRequests());
    dispatch(fetchEarnings()); // Fetch earnings when component mounts
  }, [dispatch]);

  const activeRequestsCount = requests.filter(
    (request) =>
      request.status === 'ACCEPTED' ||
      request.status === 'CASH_PAID_PENDING_CONFIRMATION' ||
      request.status === 'CASH_PAYMENT_REJECTED'
  ).length;

  // Helper function for currency formatting
  const formatCurrency = (amount) => {
    // Format as Rs. with comma separators
    if (typeof amount !== 'number') return amount;
    return `Rs. ${amount.toLocaleString('en-IN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
  };

  // Slider logic (copied from CustomerDashboard)
  const sliderImages = [
    '/src/assets/img_1.jpg',
    '/src/assets/img_2.jpg',
    '/src/assets/img_3.jpg',
    '/src/assets/img_4.jpg',
    '/src/assets/img_5.jpg'
  ];
  const [currentSlide, setCurrentSlide] = useState(0);
  useEffect(() => {
    const slideInterval = setInterval(() => {
      setCurrentSlide((prev) => (prev === sliderImages.length - 1 ? 0 : prev + 1));
    }, 3000);
    return () => clearInterval(slideInterval);
  }, []);
  const handlePrev = () => {
    setCurrentSlide((prev) => (prev === 0 ? sliderImages.length - 1 : prev - 1));
  };
  const handleNext = () => {
    setCurrentSlide((prev) => (prev === sliderImages.length - 1 ? 0 : prev + 1));
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      {/* Welcome Section */}
      <Paper
        elevation={3}
        sx={{
          p: 4,
          mb: 4,
          background: '#DDDDDD',
          color: '#111',
          borderRadius: 4,
          boxShadow: '0 4px 24px 0 rgba(25, 118, 210, 0.10)',
        }}
      >
        <Typography variant="h4" component="h1" gutterBottom sx={{ fontWeight: 700, letterSpacing: 1 }}>
          Welcome back, {user?.name || 'User'}!
        </Typography>
        <Typography variant="subtitle1" sx={{ fontSize: 20, opacity: 0.95 }}>
          {user?.role === 'PROVIDER'
            ? 'Manage your service requests and track your earnings'
            : 'Find and book services for your needs'}
        </Typography>
      </Paper>

      {/* Slider Section */}
      <Paper elevation={3} sx={{ p: 2, mb: 4, position: 'relative', overflow: 'hidden' }}>
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', height: 500, bgcolor: '#f5f5f5' }}>
          <Box
            component="img"
            src={sliderImages[currentSlide]}
            alt={`Slide ${currentSlide + 1}`}
            sx={{
              width: '100%',
              height: '100%',
              objectFit: 'cover',
              borderRadius: 2,
              transition: 'opacity 0.8s ease-in-out',
            }}
          />
        </Box>
        {/* Slider controls */}
        <Button 
          onClick={handlePrev} 
          sx={{ 
            position: 'absolute', 
            left: 16, 
            top: '50%', 
            transform: 'translateY(-50%)',
            bgcolor: 'rgba(255, 255, 255, 0.8)',
            '&:hover': {
              bgcolor: 'rgba(255, 255, 255, 0.9)',
            }
          }}
        >
          &lt;
        </Button>
        <Button 
          onClick={handleNext} 
          sx={{ 
            position: 'absolute', 
            right: 16, 
            top: '50%', 
            transform: 'translateY(-50%)',
            bgcolor: 'rgba(255, 255, 255, 0.8)',
            '&:hover': {
              bgcolor: 'rgba(255, 255, 255, 0.9)',
            }
          }}
        >
          &gt;
        </Button>
      </Paper>

      {/* Navigation Cards */}
      <Grid container spacing={3}>
        {dashboardItems.map((item) => (
          <Grid item xs={12} sm={6} md={4} key={item.title} sx={{ display: 'flex', justifyContent: 'center' }}>
            <Card
              sx={{
                width: 340,
                minWidth: 300,
                maxWidth: 360,
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                borderRadius: 4,
                boxShadow: 3,
                transition: 'transform 0.2s, box-shadow 0.2s, border 0.2s',
                border: '2px solid transparent',
                '&:hover': {
                  transform: 'translateY(-6px) scale(1.03)',
                  boxShadow: 8,
                  border: `2px solid ${item.color}`,
                },
              }}
            >
              <CardActionArea onClick={() => navigate(item.path)} sx={{ height: '100%' }}>
                <CardMedia
                  component="div"
                  sx={{
                    height: 150,
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    bgcolor: item.color,
                    color: 'white',
                    borderTopLeftRadius: 4,
                    borderTopRightRadius: 4,
                  }}
                >
                  {item.icon}
                </CardMedia>
                <CardContent sx={{ flexGrow: 1, textAlign: 'center' }}>
                  <Typography gutterBottom variant="h5" component="h2" sx={{ fontWeight: 600 }}>
                    {item.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {item.description}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Quick Stats Section */}
      <Box sx={{ mt: 4 }}>
        <Typography variant="h5" gutterBottom sx={{ fontWeight: 700, letterSpacing: 1, color: '#fff' }}>
          Quick Stats
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} sm={4}>
            <Paper
              elevation={2}
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                height: 140,
                bgcolor: '#e3f2fd',
                borderRadius: 3,
                boxShadow: 2,
              }}
            >
              <Box sx={{ width: 8, height: 80, bgcolor: '#1976d2', borderRadius: 2, mr: 2 }} />
              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="h6" color="text.secondary" gutterBottom sx={{ fontWeight: 600 }}>
                  Active Requests
                </Typography>
                <Typography variant="h3" component="div" sx={{ fontWeight: 700, color: '#1976d2' }}>
                  {activeRequestsCount}
                </Typography>
              </Box>
            </Paper>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Paper
              elevation={2}
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                height: 140,
                bgcolor: '#e8f5e9',
                borderRadius: 3,
                boxShadow: 2,
              }}
            >
              <Box sx={{ width: 8, height: 80, bgcolor: '#2e7d32', borderRadius: 2, mr: 2 }} />
              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="h6" color="text.secondary" gutterBottom sx={{ fontWeight: 600 }}>
                  Total Earnings
                </Typography>
                <Typography variant="h3" component="div" sx={{ fontWeight: 700, color: '#2e7d32' }}>
                  {formatCurrency(totalAmount)}
                </Typography>
              </Box>
            </Paper>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Paper
              elevation={2}
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                height: 140,
                bgcolor: '#fff3e0',
                borderRadius: 3,
                boxShadow: 2,
              }}
            >
              <Box sx={{ width: 8, height: 80, bgcolor: '#ed6c02', borderRadius: 2, mr: 2 }} />
              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="h6" color="text.secondary" gutterBottom sx={{ fontWeight: 600 }}>
                  Completed Services
                </Typography>
                <Typography variant="h3" component="div" sx={{ fontWeight: 700, color: '#ed6c02' }}>
                  {completedServicesCount}
                </Typography>
              </Box>
            </Paper>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
}

export default ProviderDashboard;