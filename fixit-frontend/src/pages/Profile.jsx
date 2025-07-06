import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Container,
  Typography,
  Box,
  Paper,
  TextField,
  Button,
  Grid,
  Avatar,
  CircularProgress,
  Alert,
  IconButton,
  Divider,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
} from '@mui/material';
import {
  Edit as EditIcon,
  Save as SaveIcon,
  Cancel as CancelIcon,
  PhotoCamera as PhotoCameraIcon,
  Add as AddIcon,
  Delete as DeleteIcon,
} from '@mui/icons-material';
import { updateProfile, uploadProfileImage, fetchUserProfile } from '../store/actions/userActions';

const PLACEHOLDER_IMAGE_URL = 'http://localhost:8080/images/default.jpg';

function Profile() {
  const dispatch = useDispatch();
  const { user, loading, error } = useSelector((state) => state.user);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    contact: '',
    area: '',
    city: '',
  });
  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);

  const [editingServices, setEditingServices] = useState([]);
  const [newServiceName, setNewServiceName] = useState('');
  const [newServiceCharge, setNewServiceCharge] = useState('');

  useEffect(() => {
    dispatch(fetchUserProfile());
  }, [dispatch]);

  useEffect(() => {
    if (user) {
      console.log('Profile.jsx: User object from Redux state:', user);
      console.log('Profile.jsx: user.profileImagePath:', user.profileImagePath);
      setFormData({
        name: user.name || '',
        email: user.email || '',
        contact: user.contact || '',
        area: user.area || '',
        city: user.city || '',
      });
      // Set imagePreview to the absolute URL or null if no path
      const imageUrl = user.profileImagePath ? `http://localhost:8080/images/${user.profileImagePath}` : null;
      setImagePreview(imageUrl);
      console.log('Profile.jsx: Image preview URL set to:', imageUrl);

      // Initialize services for provider
      if (user.role === 'PROVIDER' && user.services) {
        setEditingServices(user.services);
      } else {
        setEditingServices([]);
      }
    }
  }, [user]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
        console.log('Profile.jsx: New image preview set from local file:', reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleImageError = () => {
    // Fallback to a placeholder image if the profile image fails to load
    setImagePreview(PLACEHOLDER_IMAGE_URL);
    console.error('Profile.jsx: Error loading profile image, falling back to placeholder.');
  };

  const handleAddService = () => {
    if (newServiceName.trim() && newServiceCharge.trim()) {
      const newService = {
        // Assign a temporary ID for new services until saved to DB
        // This is important for keys in lists. For existing services, ID will come from backend.
        id: Date.now(),
        name: newServiceName.trim(),
        charge: parseFloat(newServiceCharge.trim()),
      };
      setEditingServices((prevServices) => [...prevServices, newService]);
      setNewServiceName('');
      setNewServiceCharge('');
    }
  };

  const handleRemoveService = (idToRemove) => {
    setEditingServices((prevServices) =>
      prevServices.filter((service) => service.id !== idToRemove)
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (imageFile) {
        const formData = new FormData();
        formData.append('file', imageFile);
        const response = await dispatch(uploadProfileImage(formData));
        if (response.payload.success) {
          const newImagePath = `http://localhost:8080/images/${response.payload.imagePath}`;
          setImagePreview(newImagePath);
          console.log('Profile.jsx: Image uploaded successfully. New image preview URL:', newImagePath);
          await dispatch(fetchUserProfile());
        } else {
          console.error('Profile.jsx: Error uploading image:', response.payload.error);
          // handleImageError will be called by Avatar onError if newImagePath fails to load
        }
      }
      
      const profileDataToSave = { ...formData };
      // Only include services if the user is a PROVIDER
      if (user.role === 'PROVIDER') {
          profileDataToSave.services = editingServices;
      }

      await dispatch(updateProfile(profileDataToSave));
      await dispatch(fetchUserProfile());
      setIsEditing(false);
      setImagePreview(null);
      setImageFile(null);
    } catch (error) {
      console.error('Profile.jsx: Error updating profile:', error);
    }
  };

  const handleCancel = () => {
    setIsEditing(false);
    setFormData({
      name: user.name || '',
      email: user.email || '',
      contact: user.contact || '',
      area: user.area || '',
      city: user.city || '',
    });
    // Reset imagePreview to the absolute URL or null if no path, then Avatar handles placeholder
    const imageUrl = user.profileImagePath ? `http://localhost:8080/images/${user.profileImagePath}` : null;
    setImagePreview(imageUrl);
    setImageFile(null);
    // Reset services on cancel
    if (user.role === 'PROVIDER' && user.services) {
        setEditingServices(user.services);
    } else {
        setEditingServices([]);
    }
    console.log('Profile.jsx: Edit cancelled. Image preview reset to:', imageUrl);
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
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Alert severity="error">{error}</Alert>
      </Container>
    );
  }

  console.log('Profile.jsx: Current imagePreview in render:', imagePreview);
  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      {/* Styled Title */}
      <Typography variant="h3" sx={{ fontWeight: 700, mb: 3, letterSpacing: 1, color: '#fff', textAlign: 'center', textShadow: '0 2px 8px rgba(0,0,0,0.18)', fontFamily: 'Playfair Display, cursive' }}>
        Profile
      </Typography>
      <Paper elevation={3} sx={{ p: 4, borderRadius: 4 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 4 }}>
          <Box sx={{ flexGrow: 1 }} />
          {!isEditing ? (
            <Button
              variant="contained"
              startIcon={<EditIcon />}
              onClick={() => setIsEditing(true)}
            >
              Edit Profile
            </Button>
          ) : (
            <Box>
              <Button
                variant="contained"
                color="primary"
                startIcon={<SaveIcon />}
                onClick={handleSubmit}
                sx={{ mr: 1 }}
              >
                Save
              </Button>
              <Button
                variant="outlined"
                color="error"
                startIcon={<CancelIcon />}
                onClick={handleCancel}
              >
                Cancel
              </Button>
            </Box>
          )}
        </Box>

        <Grid container spacing={4}>
          {/* Profile Image Section */}
          <Grid item xs={12} md={4}>
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <Avatar
                src={imagePreview || PLACEHOLDER_IMAGE_URL}
                sx={{
                  width: 200,
                  height: 200,
                  mb: 2,
                  border: '2px solid #e0e0e0',
                  boxShadow: '0 0 0 1.5px #111',
                }}
                onError={handleImageError}
              />
              {isEditing && (
                <Button
                  variant="outlined"
                  component="label"
                  startIcon={<PhotoCameraIcon />}
                >
                  Upload Photo
                  <input
                    type="file"
                    hidden
                    accept="image/*"
                    onChange={handleImageChange}
                  />
                </Button>
              )}
            </Box>
          </Grid>

          {/* Profile Information Section */}
          <Grid item xs={12} md={8}>
            <form onSubmit={handleSubmit}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="Name"
                    name="name"
                    value={formData.name}
                    onChange={handleInputChange}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="Email"
                    name="email"
                    type="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    disabled
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="Contact"
                    name="contact"
                    value={formData.contact}
                    onChange={handleInputChange}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="Area"
                    name="area"
                    value={formData.area}
                    onChange={handleInputChange}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="City"
                    name="city"
                    value={formData.city}
                    onChange={handleInputChange}
                    disabled={!isEditing}
                  />
                </Grid>
              </Grid>
            </form>
          </Grid>
        </Grid>

        {/* Services Section (Provider Only) */}
        {user?.role === 'PROVIDER' && (
          <Box sx={{ mt: 4 }}>
            <Typography variant='h6' gutterBottom>
              Services Offered
            </Typography>
            {isEditing ? (
              <Box>
                <List>
                  {editingServices.map((service, index) => (
                    <ListItem key={service.id || `new-${index}`}>
                      <ListItemText
                        primary={`${service.name} - Rs ${service.charge}`}
                      />
                      <ListItemSecondaryAction>
                        <IconButton
                          edge='end'
                          aria-label='delete'
                          onClick={() => handleRemoveService(service.id || `new-${index}`)}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  ))}
                </List>
                <Grid container spacing={2} alignItems='center'>
                  <Grid item xs={6}>
                    <TextField
                      fullWidth
                      label='New Service Name'
                      value={newServiceName}
                      onChange={(e) => setNewServiceName(e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={4}>
                    <TextField
                      fullWidth
                      label='Charge (Rs)'
                      type='number'
                      value={newServiceCharge}
                      onChange={(e) => setNewServiceCharge(e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={2}>
                    <Button
                      variant='contained'
                      color='primary'
                      onClick={handleAddService}
                      startIcon={<AddIcon />}
                      fullWidth
                    >
                      Add
                    </Button>
                  </Grid>
                </Grid>
              </Box>
            ) : (
              <List>
                {user.services && user.services.length > 0 ? (
                  user.services.map((service) => (
                    <ListItem key={service.id}>
                      <ListItemText primary={`${service.name} - Rs ${service.charge}`} />
                    </ListItem>
                  ))
                ) : (
                  <Typography variant='body2' color='text.secondary'>
                    No services added.
                  </Typography>
                )}
              </List>
            )}
          </Box>
        )}

        <Divider sx={{ my: 4 }} />

        {/* Additional Information */}
        <Box>
          <Typography variant="h6" gutterBottom>
            Account Information
          </Typography>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <Typography variant="subtitle2" color="text.secondary">
                Account Type
              </Typography>
              <Typography variant="body1">
                {user?.role === 'PROVIDER' ? 'Service Provider' : 'Customer'}
              </Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography variant="subtitle2" color="text.secondary">
                Member Since
              </Typography>
              <Typography variant="body1">
                {new Date(user?.createdAt).toLocaleDateString()}
              </Typography>
            </Grid>
          </Grid>
        </Box>
      </Paper>
    </Container>
  );
}

export default Profile; 