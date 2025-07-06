import { useSelector } from 'react-redux';
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
import { useState, useEffect } from 'react';
import axios from 'axios';
import CustomerDashboard from './CustomerDashboard';
import ProviderDashboard from './ProviderDashboard';

function Dashboard() {
  const { user } = useSelector((state) => state.auth);

  if (user?.role && user.role.toString().toUpperCase() === 'CUSTOMER') {
    return <CustomerDashboard />;
  } else {
    return <ProviderDashboard />;
  }
}

export default Dashboard; 