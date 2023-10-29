import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import Iconify from '../../../components/iconify';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '280px !important',
});

export default function PartitionFilter({ versionInfos, loading, selectedPartition, setSelectedPartition }) {
  const [partitions, setPartitions] = useState([]);

  useEffect(() => {
    if(versionInfos) {
        setPartitions([...new Set(versionInfos.map((versionInfo) => versionInfo.partition_id))]);
        if(versionInfos.length > 0) {
          handlePartitionChange(versionInfos[0].partition_id); // setting up default value
        }
    }
  }, [versionInfos]);

  const getStatusForPartition = (partitionId) => {
    const versionInfo = versionInfos.find((info) => info.partition_id === partitionId);
    return versionInfo ? versionInfo.status : null;
  };

  const handlePartitionChange = (newValue) => {
    setSelectedPartition(newValue);
  };

  const getStatusIcon = () => {
    const status = getStatusForPartition(selectedPartition);

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
      sx={{ width: 280 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={partitions}
      value={selectedPartition}
      getOptionLabel={(partition) => partition}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handlePartitionChange(newValue)}
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.PARTITION_PLACEHOLDER}
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
