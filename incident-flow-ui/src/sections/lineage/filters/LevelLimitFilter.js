import React, { useState, useEffect } from 'react';
import { TextField } from '@mui/material';
import {Placeholder} from '../../../utils/Constants';

export default function LevelLimitFilter({ onSelectLevelLimit }) {
  const [levelLimit, setLevelLimit] = useState('');
  
  useEffect(() => {
    handleLevelLimitChange(2); // Assigning default value
  }, []);

  const handleLevelLimitChange = (newValue) => {
    
    // Validate that the value is a positive number or an empty string
    if (/^\d*$/.test(newValue)) {
      setLevelLimit(newValue);
      onSelectLevelLimit(newValue);
    }
  };

  return (
    <TextField
      sx={{ width: 280 }}
      placeholder={Placeholder.LEVEL_LIMIT_PLACEHOLDER}
      value={levelLimit}
      onChange={(event) => handleLevelLimitChange(event.target.value)} 
      type="text"
    />
  );
}
