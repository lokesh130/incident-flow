import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { CircularProgress, Typography } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import { getAllFollowups } from '../../../utils/ApiUtils';
import FollowupPopup from './FollowupPopup';

const StyledFollowupSidebar = styled('div')(({ theme, open }) => ({
  width: 300,
  height: '100%',
  position: 'fixed',
  right: open ? 0 : -300,
  backgroundColor: 'rgba(135, 206, 250, 0.4)', // Sky Blue with 0.4 opacity
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

const FollowupItem = styled('div')({
  marginBottom: '16px',
  padding: '8px',
  backgroundColor: '#ADD8E6',
  borderRadius: '4px',
  boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
});

const FollowupTitle = styled(Typography)({
  fontWeight: 'bold',
  marginBottom: '8px',
});

const FollowupMessage = styled(Typography)({
  fontSize: '14px',
});

const FollowupSidebar = ({ open }) => {
  const [followups, setFollowups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedFollowup, setSelectedFollowup] = useState(null);

  useEffect(() => {
    const apiEndpoint = getAllFollowups();

    axios
      .get(apiEndpoint)
      .then((response) => {
        setFollowups(response.data);
      })
      .catch((error) => {
        console.error('Error fetching followups:', error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleDoubleClick = (followup) => {
    // Set the selected followup when double-clicked
    setSelectedFollowup(followup);
  };

  const handleClosePopup = () => {
    // Close the popup
    setSelectedFollowup(null);
  };

  const handleSend = (message) => {
    // Implement logic to send the message (e.g., API call)
    console.log(`Sending message: ${message}`);
  };

  return (
    <StyledFollowupSidebar open={open}>
      {loading ? (
        <LoadingWrapper>
          <LoadingIcon />
        </LoadingWrapper>
      ) : (
        followups.map((followup) => (
          <FollowupItem key={followup.id} onDoubleClick={() => handleDoubleClick(followup)}>
            <FollowupTitle variant="subtitle1">{followup.oncallTitle}</FollowupTitle>
            <FollowupMessage variant="body2">{followup.message}</FollowupMessage>
        </FollowupItem>
        ))
      )}

      {/* Render the FollowupPopup */}
      <FollowupPopup
        followup={selectedFollowup}
        onClose={handleClosePopup}
        onSend={handleSend}
      />
    </StyledFollowupSidebar>
  );
};

export default FollowupSidebar;
