import React from 'react';
import { Box, Typography, Button } from '@mui/material';

export const OncallTrackerPopup = ({ summary, suggestions, currentUsers, historicalUsers, onClose }) => {
  return (
    <div
      style={{
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '10px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)',
        overflowY: 'auto',
        maxHeight: '80vh',
      }}
    >
      <Box
        style={{
          display: 'flex',
        }}
      >
        {/* Vertical Section 1: Suggestions */}
        <Box style={{ flex: 1, paddingRight: '20px' }}>
          <div>
            <Typography variant="h6" sx={{ fontFamily: 'Roboto, sans-serif' }}>
              Current Summary
            </Typography>
            <Box maxHeight="300px" overflow="auto">
                <Box
                style={{
                  backgroundColor: 'MistyRose',
                  padding: '10px',
                  margin: '15px 0',
                  borderRadius: '5px',
                  position: 'relative', // Added to allow absolute positioning
                }}
              >
                  {summary}
              </Box>
            </Box>
          </div>

          <div>
            <Typography variant="h6" sx={{ fontFamily: 'Roboto, sans-serif' }}>
              Historical Summaries
            </Typography>
            <Box maxHeight="300px" overflow="auto">
              {suggestions.map((suggestion) => (
                <Box
                key={suggestion.id}
                style={{
                  backgroundColor: 'MistyRose',
                  padding: '10px',
                  margin: '15px 0',
                  borderRadius: '5px',
                  position: 'relative', // Added to allow absolute positioning
                }}
              >
                <Typography sx={{ fontFamily: 'Georgia, sans-serif' }}>{suggestion.suggestion}</Typography>
                <Typography
                  sx={{
                    position: 'absolute',
                    bottom: -8,
                    right: 0,
                    backgroundColor: '#ff9999',
                    borderRadius: '8px',
                    padding: '2px',
                    fontSize: '10px'
                  }}
                >
                  {suggestion.threadDate && suggestion.threadDate.split('T')[0]}
                </Typography>
              </Box>
              ))}
            </Box>
          </div>
        </Box>

        {/* Vertical Section 2: Current and Historical Users */}
        <Box style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
          {/* Horizontal Section 1: Current Users */}
          <div style={{ flex: 1, marginBottom: '20px' }}>
            <Typography variant="h6" sx={{ fontFamily: 'Roboto, sans-serif' }}>
              Current Users
            </Typography>
            <Box
                style={{
                backgroundColor: 'lightyellow',
                padding: '10px',
                margin: '5px 0',
                borderRadius: '5px',
                }}
            >
                {currentUsers.map((user) => (

                    <Typography sx={{ fontFamily: 'Georgia, sans-serif' }}>{user.userId}</Typography>
                
                ))}
            </Box>
          </div>

          {/* Horizontal Section 2: Historical Users */}
          <div style={{ flex: 1 }}>
            <Typography variant="h6" sx={{ fontFamily: 'Roboto, sans-serif' }}>
              Historical Users
            </Typography>
            <Box
                style={{
                backgroundColor: 'PapayaWhip',
                padding: '10px',
                margin: '5px 0',
                borderRadius: '5px',
                }}
            >
              {historicalUsers.map((user) => (
                  <Typography sx={{ fontFamily: 'Georgia, sans-serif' }}>{user.userId}</Typography>
              ))}
            </Box>
          </div>
        </Box>
      </Box>
      <br />
      <Button
        variant="contained"
        onClick={onClose}
        sx={{
          backgroundColor: 'gray',
          color: 'white',
          '&:hover': {
            backgroundColor: 'black',
          },
        }}
      >
        Close
      </Button>
    </div>
  );
};
