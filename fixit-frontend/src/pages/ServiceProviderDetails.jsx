import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import {
  Container,
  Paper,
  Box,
  Typography,
  Button,
  Grid,
  Avatar,
  List,
  ListItem,
  ListItemText,
  Divider,
  IconButton,
  Dialog,
} from '@mui/material';
import { Favorite, FavoriteBorder } from '@mui/icons-material';
import axios from 'axios';
import { addFavourite, removeFavourite, fetchFavourites } from '../store/actions/favouriteActions';
import { selectFavourites } from '../store/selectors/favouriteSelectors';
import BookingForm from '../components/BookingForm';
import { createServiceRequest } from '../store/actions/requestActions';

function ServiceProviderDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const favourites = useSelector(selectFavourites);
  const [provider, setProvider] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [bookingDialogOpen, setBookingDialogOpen] = useState(false);
  const [selectedService, setSelectedService] = useState(null);

  useEffect(() => {
    const fetchProviderDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/providers/${id}`, {
          withCredentials: true,
        });
        setProvider(response.data);
        setError(null);
      } catch (err) {
        setError('Failed to fetch provider details');
      } finally {
        setLoading(false);
      }
    };

    fetchProviderDetails();
    dispatch(fetchFavourites());
  }, [id, dispatch]);

  const isFavourite = favourites.some(fav => fav.id === parseInt(id));

  const handleAddRemoveFavourite = async () => {
    if (isFavourite) {
      await dispatch(removeFavourite(id));
    } else {
      await dispatch(addFavourite(id));
    }
    dispatch(fetchFavourites());
  };

  const handleBookService = (service) => {
    setSelectedService(service);
    setBookingDialogOpen(true);
  };

  const handleBookingSubmit = async (bookingData) => {
    try {
      // Format the date and time for the backend
      const formattedData = {
        ...bookingData,
        date: bookingData.date.toISOString().split('T')[0], // Format as YYYY-MM-DD
        time: bookingData.time.toTimeString().split(' ')[0], // Format as HH:MM:SS
      };
      console.log('Submitting booking data:', formattedData);
      await dispatch(createServiceRequest(formattedData));
      setBookingDialogOpen(false);
      navigate('/requests');
    } catch (error) {
      console.error('Failed to create booking:', error);
    }
  };

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography>Loading...</Typography>
      </Container>
    );
  }

  if (error || !provider) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography color="error">{error || 'Provider not found'}</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        {/* Provider Info (Avatar, Name, Favourite Button) */}
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mb: 4 }}>
          <Avatar
            src={provider.profileImagePath ? `http://localhost:8080/images/${provider.profileImagePath}` : 'http://localhost:8080/images/default.jpg'}
            alt={provider.name}
            sx={{ width: 120, height: 120, mb: 2 }}
          />
          <Typography variant="h4" gutterBottom>
            {provider.name}
          </Typography>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <IconButton onClick={handleAddRemoveFavourite} color="primary">
              {isFavourite ? <Favorite /> : <FavoriteBorder />}
            </IconButton>
            <Typography variant="body1" color="primary" sx={{ ml: 1 }}>
              {isFavourite ? 'Remove from Favourites' : 'Add to Favourites'}
            </Typography>
          </Box>
        </Box>

        <Divider sx={{ my: 3 }} />

        {/* Contact Information and Services Offered Sections */}
        <Grid container spacing={4} justifyContent="center">
          <Grid item xs={12} md={5}>
            <Typography variant="h6" gutterBottom>
              Contact Information
            </Typography>
            <List sx={{ width: '100%' }}>
              <ListItem>
                <ListItemText
                  primary="Phone"
                  secondary={provider.contact || 'Not provided'}
                />
              </ListItem>
              <ListItem>
                <ListItemText
                  primary="Email"
                  secondary={provider.email || 'Not provided'}
                />
              </ListItem>
              <ListItem>
                <ListItemText
                  primary="Address"
                  secondary={provider.address || (provider.area || provider.city ? `${provider.area || ''}${provider.area && provider.city ? ', ' : ''}${provider.city || ''}` : 'Not provided')}
                />
              </ListItem>
            </List>
          </Grid>

          <Grid item xs={12} md={5}>
            <Typography variant="h6" gutterBottom sx={{ marginLeft: '55px' }}>
              Services Offered
            </Typography>
            <List sx={{ width: '100%' }}>
              {provider.services && provider.services.length > 0 ? (
                provider.services.map((service) => (
                  <ListItem key={service.id} sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap' }}>
                    <ListItemText
                      primary={service.name}
                      secondary={`Rs. ${service.charge}`}
                      sx={{ flexGrow: 1, pr: 2, minWidth: '150px' }}
                    />
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleBookService(service)}
                    >
                      Book Now
                    </Button>
                  </ListItem>
                ))
              ) : (
                <Typography color="text.secondary">
                  No services listed.
                </Typography>
              )}
            </List>
          </Grid>
        </Grid>
      </Paper>

      {/* Booking Dialog */}
      <Dialog
        open={bookingDialogOpen}
        onClose={() => setBookingDialogOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        {selectedService && (
          <BookingForm
            provider={provider}
            service={selectedService}
            onClose={() => setBookingDialogOpen(false)}
            onSubmit={handleBookingSubmit}
          />
        )}
      </Dialog>
    </Container>
  );
}

export default ServiceProviderDetails;
