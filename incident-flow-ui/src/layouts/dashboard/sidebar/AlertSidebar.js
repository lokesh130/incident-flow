import React, { useState, useEffect } from 'react';
import { styled } from '@mui/system';
import {
  CircularProgress,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  TextField,
  Button,
} from '@mui/material';
import axios from '../../../utils/CustomAxios';
import { getAllAlerts } from '../../../utils/ApiUtils';
import AlertPopup from './AlertPopup'; // Import the AlertPopup component

const StyledAlertSidebar = styled('div')(({ theme, open }) => ({
  width: 300,
  height: '100%',
  position: 'fixed',
  right: open ? 0 : -300,
  backgroundColor: 'rgba(255, 160, 122, 0.4)', // Rose Pink with 0.8 opacity
  padding: '16px', // Add sideways padding
  transition: theme.transitions.create(['right'], {
    easing: theme.transitions.easing.easeInOut,
    duration: theme.transitions.duration.standard,
  }),
}));

const LoadingWrapper = styled('div')({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  height: '100%',
});

const LoadingIcon = styled(CircularProgress)({
  color: '#3f51b5', // You can change the color to fit your design
});


const AlertItem = styled('div')({
  marginBottom: '16px',
  padding: '8px',
  backgroundColor: '#FFB2B2',
  borderRadius: '4px',
  boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
  position: 'relative',
  cursor: 'pointer',
});


const AlertTitle = styled(Typography)({
  fontWeight: 'bold',
  marginBottom: '8px',
});

const AlertMessage = styled(Typography)({
  fontSize: '14px',
});

const AlertSidebar = ({ open }) => {
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedAlert, setSelectedAlert] = useState(null);

  const handleAlertDoubleClick = (alert) => {
    setSelectedAlert(alert);
  };

  const handleCloseAlertPopup = () => {
    setSelectedAlert(null);
  };

  const handleSend = (message) => {
    // Implement logic to send the message (e.g., API call)
    console.log(`Sending message: ${message}`);
  };
  
  useEffect(() => {
    const apiEndpoint = getAllAlerts();

    axios
      .get(apiEndpoint)
      .then((response) => {
        setAlerts(response.data);
      })
      .catch((error) => {
        console.error('Error fetching alerts:', error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  return (
    <StyledAlertSidebar open={open}>
    {loading ? (
      <LoadingWrapper>
        <LoadingIcon />
      </LoadingWrapper>
    ) : (
      alerts.map((alert) => (
        <AlertItem
          key={alert.id}
          onDoubleClick={() => handleAlertDoubleClick(alert)}
        >
          <AlertTitle variant="subtitle1">{alert.title}</AlertTitle>
          <AlertMessage variant="body2">{alert.message}</AlertMessage>
        </AlertItem>
      ))
    )}
    
    <AlertPopup
      followup={selectedAlert}
      onClose={handleCloseAlertPopup}
      onSend={handleSend}
    />
  </StyledAlertSidebar>
  );
};

export default AlertSidebar;
