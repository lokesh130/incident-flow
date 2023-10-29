import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import Iconify from '../../../components/iconify';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '320px !important',
});

export default function TimestampFilter({ versionInfos, loading, selectedTimestamp, setSelectedTimestamp }) {
    const [timestamps, setTimestamps] = useState([]);

    useEffect(() => {
      if(versionInfos) {
        setTimestamps([...new Set(versionInfos.map((versionInfo) => versionInfo.timestamp))]);
        if(versionInfos.length > 0) {
          handleTimestampChange(versionInfos[0].timestamp); // setting up default value
        }
      }
    }, [versionInfos]);
    
    const getStatusForTimestamp = (timestamp) => {
      const versionInfo = versionInfos.find((info) => info.timestamp === timestamp);
      return versionInfo ? versionInfo.status : null;
    };

    const handleTimestampChange = (newValue) => {
      setSelectedTimestamp(newValue);
    };

    const getStatusIcon = () => {
      const status = getStatusForTimestamp(selectedTimestamp);
  
      if (status === 'COMPLETED' || status === 'SKIPPED' || status === 'PERSISTED' || status === 'VALIDATED') {
        return <Iconify icon={'eva:checkmark-circle-2-fill'} sx={{ color: 'green' }} />;
      } 
      
      if (status === 'FAILED' || status === 'CANCELLED' || status === 'PERSISTENCE_FAILED' || status === 'VALIDATION_FAILED') {
        return <Iconify icon={'eva:close-circle-fill'} sx={{ color: 'red' }} />;
      }

      if (status === 'STARTED') {
        return <Iconify icon={'eva:close-circle-fill'} sx={{ color: 'black' }} />;
      }
      
      return <></>;
    };
  
  

  return (
    <Autocomplete
      sx={{ width: 320 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={timestamps}
      value={selectedTimestamp} 
      getOptionLabel={(timestamp) => timestamp}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handleTimestampChange(newValue)}
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.TIMESTAMP_PLACEHOLDER}
          autoComplete="off" 
          InputProps={{
            ...params.InputProps,
            startAdornment: (
              <InputAdornment position="start">
                <Iconify icon={'eva:search-fill'} sx={{ ml: 1, width: 20, height: 20, color: 'text.disabled' }} />
              </InputAdornment>
            ),
            endAdornment: (
              <>
                {getStatusIcon()}
                {loading && <CircularProgress color="inherit" size={20} />}
                {params.InputProps.endAdornment}
              </>
            ),
          }}
        />
      )}
    />
  );
}
