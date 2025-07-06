import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Paper, Box, Typography, Button, Grid, Card, CardMedia, CardContent, List, ListItem, ListItemText, TextField,InputAdornment } from '@mui/material';
import axios from 'axios';
import { useSelector, useDispatch } from 'react-redux';
import { addFavourite, removeFavourite, fetchFavourites } from '../store/actions/favouriteActions';
import { Favorite, FavoriteBorder } from '@mui/icons-material';
import { selectFavourites, selectFavouritesLoading, selectFavouritesError } from '../store/selectors/favouriteSelectors';
import { Search as SearchIcon } from '@mui/icons-material';


function CustomerDashboard() {
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);
  const favourites = useSelector(selectFavourites);
  const favouritesLoading = useSelector(selectFavouritesLoading);
  const favouritesError = useSelector(selectFavouritesError);
  const dispatch = useDispatch();

  // Simple slider images (empty for now)
  const sliderImages = [
    '/src/assets/img_1.jpg',
    '/src/assets/img_2.jpg',
    '/src/assets/img_3.jpg',
    '/src/assets/img_4.jpg',
    '/src/assets/img_5.jpg'
  ];
  const [currentSlide, setCurrentSlide] = useState(0);
  const [providers, setProviders] = useState([]);
  const [loadingProviders, setLoadingProviders] = useState(true);
  const [errorProviders, setErrorProviders] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [searchType, setSearchType] = useState('provider'); // 'provider' or 'service'

  useEffect(() => {
    const slideInterval = setInterval(() => {
      setCurrentSlide((prev) => (prev === sliderImages.length - 1 ? 0 : prev + 1));
    }, 3000); // Change slide every 3 seconds
  
    // Cleanup interval on component unmount
    return () => clearInterval(slideInterval);
  }, []); // Empty dependency array means this effect runs once on mount

  useEffect(() => {
    const fetchProviders = async () => {
      setLoadingProviders(true);
      try {
        const response = await axios.get('http://localhost:8080/api/users/providers', {
          withCredentials: true,
        });
        setProviders(response.data);
        setErrorProviders(null);
      } catch (err) {
        setErrorProviders('Failed to fetch providers');
      } finally {
        setLoadingProviders(false);
      }
    };
    fetchProviders();
    dispatch(fetchFavourites());
  }, [dispatch]);

  const handlePrev = () => {
    setCurrentSlide((prev) => (prev === 0 ? sliderImages.length - 1 : prev - 1));
  };
  const handleNext = () => {
    setCurrentSlide((prev) => (prev === sliderImages.length - 1 ? 0 : prev + 1));
  };

  const handleCardClick = (providerId) => {
    navigate(`/provider/${providerId}`);
  };

  const handleAddRemoveFavourite = async (e, providerId, isCurrentlyFavourite) => {
    e.stopPropagation(); // Prevent card click when clicking the favorite button
    if (isCurrentlyFavourite) {
      await dispatch(removeFavourite(providerId));
    } else {
      await dispatch(addFavourite(providerId));
    }
    dispatch(fetchFavourites());
  };

  const filteredProviders = providers.filter(provider => {
    if (!searchQuery) return true;
    
    if (searchType === 'provider') {
      return provider.name.toLowerCase().includes(searchQuery.toLowerCase());
    } else {
      // Search in services
      return provider.services?.some(service => 
        service.name.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }
  });


  return (
    <Container maxWidth="lg" sx={{ mb: 4 }}>
      {/* Slider */}
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
              transition: 'opacity 0.8s ease-in-out', // Add smooth transition
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

      {/*Search functionality */}
      <Paper elevation={4} sx={{ mb: 4, p: { xs: 2, md: 3 }, borderRadius: 3, background: 'rgba(255,255,255,0.85)', boxShadow: 3 }}>
        <Typography variant="h5" gutterBottom sx={{ fontWeight: 600 }}>Service Providers</Typography>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} md={8}>
            <Box sx={{ display: 'flex', gap: 2 }}>
              <TextField
                fullWidth
                variant="outlined"
                placeholder={`Search by ${searchType === 'provider' ? 'provider name' : 'service'}`}
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  ),
                  sx: { borderTopRightRadius: 0, borderBottomRightRadius: 0 }
                }}
                sx={{
                  background: '#fff',
                  borderTopRightRadius: 0,
                  borderBottomRightRadius: 0,
                  boxShadow: '0 1px 4px rgba(0,0,0,0.04)'
                }}
              />
              <Button
                variant={searchType === 'provider' ? 'contained' : 'outlined'}
                onClick={() => setSearchType('provider')}
                sx={{
                  borderRadius: 0,
                  borderTopRightRadius: 8,
                  borderBottomRightRadius: 8,
                  minWidth: 150,
                  fontWeight: 500,
                  boxShadow: searchType === 'provider' ? '0 2px 8px rgba(26,35,126,0.08)' : 'none',
                  background: searchType === 'provider' ? 'linear-gradient(90deg,#1976d2 60%,#534bae 100%)' : undefined,
                  color: searchType === 'provider' ? '#fff' : undefined
                }}
              >
                By Provider
              </Button>
              <Button
                variant={searchType === 'service' ? 'contained' : 'outlined'}
                onClick={() => setSearchType('service')}
                sx={{
                  borderRadius: 0,
                  borderTopRightRadius: 8,
                  borderBottomRightRadius: 8,
                  minWidth: 150,
                  fontWeight: 500,
                  boxShadow: searchType === 'service' ? '0 2px 8px rgba(26,35,126,0.08)' : 'none',
                  background: searchType === 'service' ? 'linear-gradient(90deg,#1976d2 60%,#534bae 100%)' : undefined,
                  color: searchType === 'service' ? '#fff' : undefined
                }}
              >
                By Service
              </Button>
            </Box>
          </Grid>
        </Grid>
      </Paper>

      {/* Provider Cards */}
      <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
        {loadingProviders ? (
          <Typography color="text.secondary">Loading providers...</Typography>
        ) : errorProviders ? (
          <Typography color="error.main">{errorProviders}</Typography>
        ) : filteredProviders.length === 0 ? (
          <Typography color="text.secondary">
            {searchQuery ? 'No providers found matching your search.' : 'No providers found.'}
          </Typography>
        ) : (
          <Grid container spacing={3}>
            {filteredProviders.map((provider) => {
              const isFavourite = favourites.some(fav => fav.id === provider.id);
              return (
                <Grid item xs={12} sm={6} md={4} key={provider.id}>
                  <Card 
                    sx={{ 
                      height: '100%', 
                      display: 'flex', 
                      flexDirection: 'column',
                      cursor: 'pointer',
                      background: '#EEEEFF',
                      borderRadius: 4,
                      boxShadow: 6,
                      border: '1px solid #e0e0e0',
                      '&:hover': {
                        boxShadow: 12,
                        borderColor: '#bdbdbd',
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
                        variant={isFavourite ? "contained" : "outlined"}
                        color="primary"
                        sx={{ mt: 'auto' }}
                        fullWidth
                        onClick={(e) => handleAddRemoveFavourite(e, provider.id, isFavourite)}
                        startIcon={isFavourite ? <Favorite /> : <FavoriteBorder />}
                      >
                        {isFavourite ? "Added to Favourites" : "Add to Favourites"}
                      </Button>
                    </CardContent>
                  </Card>
                </Grid>
              );
            })}
          </Grid>
        )}
      </Paper>
    </Container>
  );
}

export default CustomerDashboard; 