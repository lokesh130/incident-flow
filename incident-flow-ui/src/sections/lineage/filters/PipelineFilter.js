import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import Iconify from '../../../components/iconify';
import {getPipelineNamesApiEndpoint, getParams} from '../../../utils/ApiUtils';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '500px !important',
});

export default function PipelineFilter({ selectedPipelineLocation, selectedPipeline, setSelectedPipeline}) {
  const [pipelines, setPipelines] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setPipelines([]);
    setSelectedPipeline(null);
    if (selectedPipelineLocation) {
      setLoading(true);
      const apiEndpoint = getPipelineNamesApiEndpoint(selectedPipelineLocation);
      const params = getParams({
        limit: 10000,
        offset: 0
      });
      axios.get(apiEndpoint, {params})
      .then(response => {
        setPipelines(response.data);
      })
      .catch(error => {
        console.error("Error fetching pipelines:", error);
      })
      .finally(() => {
        setLoading(false);
      });
    }
  }, [selectedPipelineLocation]);

  const handlePipelineChange = (newValue) => {
    setSelectedPipeline(newValue);
  };

  return (
    <Autocomplete
      sx={{ width: 500 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={pipelines}
      value={selectedPipeline}
      getOptionLabel={(pipeline) => pipeline}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handlePipelineChange(newValue)}
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.PIPELINE_PLACEHOLDER}
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