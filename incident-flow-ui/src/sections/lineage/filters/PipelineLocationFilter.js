import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import Iconify from '../../../components/iconify';
import {getPipelineLocationsApiEndpoint} from '../../../utils/ApiUtils';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '280px !important',
});

export default function PipelineLocationFilter({selectedPipelineLocation, setSelectedPipelineLocation}) {
  const [pipelineLocations, setPipelineLocations] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setPipelineLocations([]);
    setSelectedPipelineLocation(null);
    handlePipelineTypeChange('WEB_SERVICE'); // Assigning default value
    setLoading(true);
    const apiEndpoint = getPipelineLocationsApiEndpoint();
    axios.get(apiEndpoint)
      .then(response => {
        setPipelineLocations(response.data);
      })
      .catch(error => {
        console.error("Error fetching pipeline types:", error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handlePipelineTypeChange = (newValue) => {
    setSelectedPipelineLocation(newValue);
  };

  return (
    <Autocomplete
      sx={{ width: 280 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={pipelineLocations}
      value={selectedPipelineLocation}
      getOptionLabel={(pipelineType) => pipelineType}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handlePipelineTypeChange(newValue)} 
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.PIPELINE_LOCATION_PLACEHOLDER}
          InputProps={{
            ...params.InputProps,
            startAdornment: (
              <InputAdornment position="start">
                <Iconify icon={'eva:search-fill'} sx={{ ml: 1, width: 20, height: 20, color: 'text.disabled' }} />
              </InputAdornment>
            ),
            endAdornment: (
              <>
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