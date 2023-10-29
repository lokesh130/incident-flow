import React, { useState, useEffect } from 'react';
import { Box, Typography, Grid, styled, keyframes } from '@mui/material';
import { OncallTrackerPopup } from './OncallTrackerPopup';
import { getOncallTrackersApiEndpoint, getOncallSuggestionsApiEndpoint, getHistoricalUsersApiEndpoint, getCurrentUsersApiEndpoint } from '../utils/ApiUtils';
import axios from '../utils/CustomAxios';

const spinAnimation = keyframes`
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
`;

const fadeInOutAnimation = keyframes`
  0%, 100% { opacity: 0; }
  10%, 90% { opacity: 1; }
`;

const TicketBox = styled(Box)({
  margin: '10px',
  marginBottom: '20px',
  borderRadius: '5px',
  boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  transition: 'transform 0.3s ease-in-out',

  '&:hover': {
    transform: 'scale(1.03)',
  },
});

const ButtonContainer = styled(Box)({
  position: 'fixed',
  bottom: '20px',
  left: '235px',
});

const LoadingOverlay = styled(Box)({
  position: 'fixed',
  top: 0,
  left: 0,
  width: '100%',
  height: '100%',
  backgroundColor: 'rgba(0, 0, 0, 0.3)',
  zIndex: 9998,
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
});

const LoadingIcon = styled('div')({
  border: '4px solid rgba(255, 255, 255, 0.3)',
  borderRadius: '50%',
  borderTop: '4px solid #3498db',
  width: '40px',
  height: '40px',
  animation: `${spinAnimation} 1s linear infinite`,
});

const Notification = styled('div')({
  position: 'fixed',
  top: 20,
  right: 20,
  padding: '10px',
  backgroundColor: '#27ae60',
  color: '#fff',
  borderRadius: '5px',
  animation: `${fadeInOutAnimation} 3s ease-in-out`,
  zIndex: 9999,
});

export default function OncallTrackerPanel({
  oncallTrackers,
  setOncallTrackers,
  activeGroup,
}) {
  const [loading, setLoading] = useState(false);
  const [selectedTracker, setSelectedTracker] = useState(null);
  const [showNotification, setShowNotification] = useState(false);

  useEffect(() => {
    setOncallTrackers([]);
    if (activeGroup) {
      const apiEndpoint = getOncallTrackersApiEndpoint(activeGroup.id);
      axios
        .get(apiEndpoint)
        .then((response) => {
          setOncallTrackers(response.data);
        })
        .catch((error) => {
          console.error('Error fetching oncall trackers:', error);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [activeGroup]);

  const activeTickets = oncallTrackers
    .filter((tracker) => tracker.status === 'ACTIVE')
    .sort((a, b) => a.priority.localeCompare(b.priority));

  const closedTickets = oncallTrackers
    .filter((tracker) => tracker.status === 'CLOSED')
    .sort((a, b) => a.priority.localeCompare(b.priority));

  const handleDoubleClick = async (oncallTracker) => {
    const oncallTrackerId = oncallTracker.id;
    const suggestionsApiEndpoint = getOncallSuggestionsApiEndpoint(
      oncallTrackerId
    );
    const historicalUsersApiEndpoint = getHistoricalUsersApiEndpoint(
      oncallTrackerId
    );
    const currentUsersApiEndpoint = getCurrentUsersApiEndpoint(oncallTrackerId);

    try {
      const [suggestionsResponse, historicalUsersResponse, currentUsersResponse] =
        await Promise.all([
          axios.get(suggestionsApiEndpoint),
          axios.get(historicalUsersApiEndpoint),
          axios.get(currentUsersApiEndpoint),
        ]);

      const suggestions = suggestionsResponse.data;
      const historicalUsers = historicalUsersResponse.data;
      const currentUsers = currentUsersResponse.data;

      setSelectedTracker({
        summary: oncallTracker.summary,
        suggestions,
        historicalUsers,
        currentUsers,
      });
    } catch (error) {
      console.error('Error fetching suggestions and users:', error);
    }
  };

  const handleClosePopup = () => {
    setSelectedTracker(null);
  };

  const handleSyncEmails = async () => {
    setLoading(true);
    try {
      await axios.post('/v1/oncall/sync-emails');
      setShowNotification(true);
      setTimeout(() => {
        setShowNotification(false);
        
        const apiEndpoint = getOncallTrackersApiEndpoint(activeGroup.id);
        axios
          .get(apiEndpoint)
          .then((response) => {
            setOncallTrackers(response.data);
          })
          .catch((error) => {
            console.error('Error fetching oncall trackers:', error);
          })

      }, 3000);
    } catch (error) {
      console.error('Error syncing emails:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSyncFollowups = async () => {
    setLoading(true);
    try {
      // Update the API endpoint based on your backend implementation
      await axios.post('/v1/oncall/sync-followups');
      setShowNotification(true);
      setTimeout(() => {
        setShowNotification(false);
      }, 3000);
    } catch (error) {
      console.error('Error syncing followups:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSyncAlerts = async () => {
    console.log("Syncing Alerts");
  };
  

  return (
    <Grid container spacing={2}>
      <Grid item xs={6}>
        <Typography variant="h6" style={{ textAlign: 'center', margin: '0 auto' }}>
          Active Tickets
        </Typography>
        {activeTickets.map((ticket) => (
          <TicketBox
            key={ticket.id}
            onDoubleClick={() => handleDoubleClick(ticket)}
          >
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <div style={{ flex: '60%', backgroundColor: 'lightblue', padding: '10px' }}>
                <Typography variant="h6" sx={{ fontFamily: 'HeadingFont' }}>{ticket.title}</Typography>
                <Typography>{ticket.description}</Typography>
              </div>
              {ticket.status === 'CLOSED' && (
                <div style={{ flex: '10%', backgroundColor: 'lightblue', padding: '10px', display: 'flex', alignItems: 'center' }}>
                  <Typography
                    onClick={() => window.open(ticket.rcaDoc, '_blank')}
                    style={{ color: 'blue', textDecoration: 'underline', cursor: 'pointer' }}
                  >
                    RCA DOC
                  </Typography>
                </div>
              )}
              <div style={{ flex: '20%', backgroundColor: 'lightSteelBlue', padding: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 'small', fontFamily: 'Arial, sans-serif' }}>
                <Typography>{ticket.oncallStatus}</Typography>
              </div>
              <div style={{ flex: '10%', backgroundColor: 'grey', padding: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Typography>{ticket.priority}</Typography>
              </div>
            </div>
          </TicketBox>
        ))}
      </Grid>

      <Grid item xs={6}>
        <Typography variant="h6" style={{ textAlign: 'center', margin: '0 auto' }}>
          Closed Tickets
        </Typography>
        {closedTickets.map((ticket) => (
          <TicketBox
            key={ticket.id}
            onDoubleClick={() => handleDoubleClick(ticket)}
          >
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <div style={{ flex: '60%', backgroundColor: 'lightgreen', padding: '10px' }}>
                <Typography variant="h6" sx={{ fontFamily: 'HeadingFont' }}>{ticket.title}</Typography>
                <Typography>{ticket.description}</Typography>
              </div>
              {ticket.status === 'CLOSED' && (
                <div style={{ flex: '10%', backgroundColor: 'lightgreen', padding: '10px', display: 'flex', alignItems: 'center' }}>
                  <Typography
                    onClick={() => window.open(ticket.rcaDoc, '_blank')}
                    style={{ color: 'blue', textDecoration: 'underline', cursor: 'pointer' }}
                  >
                    RCA DOC
                  </Typography>
                </div>
              )}
              <div style={{ flex: '20%', backgroundColor: 'paleGreen', padding: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 'small', fontFamily: 'Arial, sans-serif' }}>
                <Typography>{ticket.oncallStatus}</Typography>
              </div>
              <div style={{ flex: '10%', backgroundColor: 'grey', padding: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Typography>{ticket.priority}</Typography>
              </div>
            </div>
          </TicketBox>
        ))}
      </Grid>

      {selectedTracker && (
        <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.3)', zIndex: 9999 }}>
          <OncallTrackerPopup
            summary={selectedTracker.summary}
            suggestions={selectedTracker.suggestions}
            currentUsers={selectedTracker.currentUsers}
            historicalUsers={selectedTracker.historicalUsers}
            onClose={handleClosePopup}
          />
        </div>
      )}

      {loading && (
        <LoadingOverlay>
          <LoadingIcon />
        </LoadingOverlay>
      )}

      {showNotification && (
        <Notification>
          Emails Sync Successful
        </Notification>
      )}

      {showNotification && (
            <Notification>
              Followups Sync Successful
            </Notification>
          )}

      <ButtonContainer>
        <button
          onClick={handleSyncEmails}
          style={{
            padding: '10px',
            backgroundColor: '#8b008b',
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          }}
        >
          Sync Emails
        </button>

        <button
        onClick={handleSyncFollowups}
        style={{
          marginLeft: '10px',
          padding: '10px',
          backgroundColor: '#3498db',
          color: '#fff',
          border: 'none',
          borderRadius: '5px',
          cursor: 'pointer',
        }}
      >
        Sync Followups
      </button>

      <button
        onClick={handleSyncAlerts}
        style={{
          marginLeft: '10px',
          padding: '10px',
          backgroundColor: 'red',
          color: '#fff',
          border: 'none',
          borderRadius: '5px',
          cursor: 'pointer',
        }}
      >
        Sync Alerts
      </button>
      </ButtonContainer>
    </Grid>
  );
}
