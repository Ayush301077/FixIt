import { useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import {
  AppBar,
  Box,
  CssBaseline,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
  Avatar,
  Menu,
  MenuItem,
} from '@mui/material';
import {
  Menu as MenuIcon,
  Dashboard as DashboardIcon,
  Person as PersonIcon,
  Assignment as AssignmentIcon,
  AttachMoney as MoneyIcon,
  Favorite as FavoriteIcon,
  Logout as LogoutIcon,
} from '@mui/icons-material';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../store/actions/authActions';
import Navbar from './Navbar';

const drawerWidth = 240;

function Layout() {
  const [mobileOpen, setMobileOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const { user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleProfileMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    dispatch(logout());
    navigate('/');
  };

  const customerMenuItems = [
    { text: 'Dashboard', icon: <DashboardIcon />, path: '/dashboard' },
    { text: 'Requests', icon: <AssignmentIcon />, path: '/requests' },
    { text: 'Favourites', icon: <FavoriteIcon />, path: '/favourites' },
    { text: 'Profile', icon: <PersonIcon />, path: '/profile' },
  ];

  const providerMenuItems = [
    { text: 'Dashboard', icon: <DashboardIcon />, path: '/dashboard' },
    { text: 'Requests', icon: <AssignmentIcon />, path: '/requests' },
    { text: 'Earnings', icon: <MoneyIcon />, path: '/earnings' },
    { text: 'Profile', icon: <PersonIcon />, path: '/profile' },
  ];

  const currentMenuItems = user?.role?.toUpperCase() === 'CUSTOMER' ? customerMenuItems : providerMenuItems;

  const drawer = (
    <div>
      <Toolbar />
      <List>
        {currentMenuItems.map((item) => (
          <ListItem
            button
            key={item.path}
            onClick={() => navigate(item.path)}
          >
            <ListItemIcon>{item.icon}</ListItemIcon>
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
      </List>
    </div>
  );

  return (
    <div className="min-h-screen">
      <Navbar />
      <main className="lg:ml-64 min-h-screen">
        <div className="p-4 md:p-8">
          <Outlet />
        </div>
      </main>
    </div>
  );
}

export default Layout; 