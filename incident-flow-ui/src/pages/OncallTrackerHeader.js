import React, { useState, useEffect } from 'react';
import { Box, Typography, Divider, styled } from '@mui/material';
import EventIcon from '@mui/icons-material/Event';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { OncallTrackerHeading } from '../utils/Constants';
import axios from '../utils/CustomAxios';
import { getActiveOncallGroupApiEndpoint, getOncallGroupByDateApiEndpoint } from '../utils/ApiUtils';

const HeadingTypography = styled(Typography)(({ theme }) => ({
  letterSpacing: '0.1em',
  fontWeight: 'bold',
  fontFamily: 'Montserrat, sans-serif',
  color: '#8b008b',
  textTransform: 'uppercase',
  textAlign: 'center',
  padding: '0.2em 5em',
  borderRadius: '5px',
  '&:hover': {
    backgroundColor: '#FFC2D6', // Change to the desired hover color
  },
}));

const ValueTypography = styled(Typography)(({ theme }) => ({
  fontSize: '0.9rem',
  color: '#FF66B2',  // Pink color
}));

export default function OncallTrackerHeader({activeGroup, setActiveGroup, selectedDate, setSelectedDate}) {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if(selectedDate) {
      const formattedDate = selectedDate.toISOString().slice(0, 16);
      const apiEndpoint = getOncallGroupByDateApiEndpoint(formattedDate);
      axios
        .get(apiEndpoint)
        .then((response) => {
          setActiveGroup(response.data);
        })
        .catch((error) => {
          console.error('Error fetching oncall data:', error);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [selectedDate]);

  return (
    <div style={{backgroundColor: '#FFD3E0'}}>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'space-around',
          padding: 1,
          margin:0,
          borderRadius: '10px', // Adjust the value for the bulge effect
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', // Adjust the values for the shadow effect        
        }}
      >
        <div>
          <HeadingTypography>
            {OncallTrackerHeading.PRIMARY}
            <ValueTypography>
              {activeGroup?.primaryUser?.name || '-'}
            </ValueTypography>
          </HeadingTypography>
        </div>
        <div>
          <HeadingTypography>
            {OncallTrackerHeading.SECONDARY}
            <ValueTypography>
              {activeGroup?.secondaryUser?.name || '-'}
            </ValueTypography>
          </HeadingTypography>
        </div>
        <div>
          <HeadingTypography>
            {OncallTrackerHeading.START_DATE}
            <ValueTypography>
              {activeGroup?.startDate || '-'}
            </ValueTypography>
          </HeadingTypography>
        </div>
        <div>
          <HeadingTypography>
            {OncallTrackerHeading.END_DATE}
            <ValueTypography>
              {activeGroup?.endDate || '-'}
            </ValueTypography>
          </HeadingTypography>
        </div>
        <div>
          <DatePicker
            showMonthDropdown
            showYearDropdown
            dropdownMode="select"
            selected={selectedDate}
            onChange={(date) => setSelectedDate(date)}
            customInput={
              <button type="button" style={{
                border: 'none',
                background: 'none',
                cursor: 'pointer',
              }}>
                <EventIcon style={{ fontSize: 40 }}/>
              </button>
            }
          />
        </div>
      </Box>
    </div>
  );
}
