import React, { useState, useEffect } from 'react';
import OncallTrackerHeader from './OncallTrackerHeader';
import OncallTrackerPanel from './OncallTrackerPanel';

export default function OncallTrackerPage() {
  const [activeGroup, setActiveGroup] = useState(null);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [oncallTrackers, setOncallTrackers] = useState([]);

  return (
    <>
      <OncallTrackerHeader
        activeGroup={activeGroup}
        setActiveGroup={setActiveGroup}
        selectedDate={selectedDate}
        setSelectedDate={setSelectedDate}
      />
      
      <div style={{ margin: '25px', marginTop: '40px' }}> {/* Adjust the margin as needed */}
        <OncallTrackerPanel
          oncallTrackers={oncallTrackers}
          setOncallTrackers={setOncallTrackers}
          activeGroup={activeGroup}
        />  
      </div>
    </>
  );
}
