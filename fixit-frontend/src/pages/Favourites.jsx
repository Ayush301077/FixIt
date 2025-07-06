import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Box,
  Grid,
  Card,
  CardMedia,
  CardContent,
  Button,
  List,
  ListItem,
  ListItemText,
  Paper,
} from '@mui/material';
import { Favorite } from '@mui/icons-material';
import { fetchFavourites, removeFavourite } from '../store/actions/favouriteActions';
import { selectFavourites, selectFavouritesLoading, selectFavouritesError } from '../store/selectors/favouriteSelectors';

function Favourites() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const favourites = useSelector(selectFavourites);
  const loading = useSelector(selectFavouritesLoading);
  const error = useSelector(selectFavouritesError);

  console.log("Favourites data in Favourites.jsx:", favourites);
  console.log("Loading state in Favourites.jsx:", loading);
  console.log("Error state in Favourites.jsx:", error);

  useEffect(() => {
    dispatch(fetchFavourites());
  }, [dispatch]);

  const handleRemoveFavourite = async (e, providerId) => {
    e.stopPropagation();
    await dispatch(removeFavourite(providerId));
    dispatch(fetchFavourites());
  };

  const handleCardClick = (providerId) => {
    navigate(`/provider/${providerId}`);
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h3" sx={{ fontWeight: 700, mb: 3, letterSpacing: 1, color: '#fff', textAlign: 'center', textShadow: '0 2px 8px rgba(0,0,0,0.18)', fontFamily: 'Playfair Display, cursive' }}>
        Your Favourite Service Providers
      </Typography>
      <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
        {loading ? (
          <Typography color="text.secondary">Loading favourites...</Typography>
        ) : error ? (
          <Typography color="error.main">Error: {error}</Typography>
        ) : favourites.length === 0 ? (
          <Typography color="text.secondary">You haven't added any favourite service providers yet.</Typography>
        ) : (
          <Grid container spacing={3}>
            {favourites.map((provider) => (
              <Grid item xs={12} sm={6} md={4} key={provider.id}>
                <Card 
                  sx={{ 
                    height: '100%', 
                    display: 'flex', 
                    flexDirection: 'column',
                    cursor: 'pointer',
                    background: '#DDDDDD',
                    borderRadius: 4,
                    transition: 'border 0.2s',
                    '&:hover': {
                      boxShadow: 6,
                      border: '1.5px solid #222',
                    },
                  }}
                  onClick={() => handleCardClick(provider.id)}
                >
                  <Box sx={{ display: 'flex', justifyContent: 'center', p: 2 }}>
                    <CardMedia
                      component="img"
                      sx={{
                        width: 100,
                        height: 100,
                        borderRadius: '50%',
                        objectFit: 'cover',
                      }}
                      image={provider.profileImagePath ? `http://localhost:8080/images/${provider.profileImagePath}` : 'http://localhost:8080/images/default.jpg'}
                      alt={provider.name}
                      onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = 'http://localhost:8080/images/default.jpg';
                      }}
                    />
                  </Box>
                  <CardContent sx={{ display: 'flex', flexDirection: 'column', flexGrow: 1 }}>
                    <Typography variant="h6">{provider.name}</Typography>
                    <Typography variant="body2" color="text.secondary">
                      {provider.area}, {provider.city}
                    </Typography>
                    <Box sx={{ mt: 1, flexGrow: 1 }}>
                      <Typography variant="subtitle2" color="text.secondary">Services:</Typography>
                      {provider.services && provider.services.length > 0 ? (
                        <List dense disablePadding>
                          {provider.services.map((service) => (
                            <ListItem key={service.id}>
                              <ListItemText primary={`${service.name} - Rs ${service.charge}`} />
                            </ListItem>
                          ))}
                        </List>
                      ) : (
                        <Typography variant="body2" color="text.secondary">
                          No services listed.
                        </Typography>
                      )}
                    </Box>
                    <Button
                      variant="contained"
                      color="secondary"
                      sx={{ mt: 'auto' }}
                      fullWidth
                      onClick={(e) => handleRemoveFavourite(e, provider.id)}
                      startIcon={<Favorite />}
                    >
                      Remove from Favourites
                    </Button>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        )}
      </Paper>
    </Container>
  );
}

export default Favourites; 