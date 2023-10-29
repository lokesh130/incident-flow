import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import Iconify from '../../../components/iconify';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '280px !important',
});

export default function VersionFilter({ versionInfos, loading, selectedVersion, setSelectedVersion }) {
  const [versions, setVersions] = useState([]);

  useEffect(() => {
    if(versionInfos) {
      setVersions([...new Set(versionInfos.map((versionInfo) => String(versionInfo.version_id)))]);
      if(versionInfos.length > 0) {
        handleVersionChange(String(versionInfos[0].version_id)); // setting up default value
      }
    }
  }, [versionInfos]);

  const getStatusForVersion = (versionId) => {
    const versionInfo = versionInfos.find((info) => String(info.version_id) === versionId);
    return versionInfo ? versionInfo.status : null;
  };

  const handleVersionChange = (newValue) => {
    setSelectedVersion(newValue);
  };

  const getStatusIcon = () => {
    const status = getStatusForVersion(selectedVersion);
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
      options={versions}
      value={selectedVersion}
      getOptionLabel={(version) => version}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handleVersionChange(newValue)}
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.VERSION_PLACEHOLDER}
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
