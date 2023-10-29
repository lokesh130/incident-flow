import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress, Typography } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import Iconify from '../../../components/iconify';
import {getDatasetIdentifiersApiEndpoint, getParams} from '../../../utils/ApiUtils';
import { Placeholder } from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '860px !important'
});

export default function DatasetFilter({ selectedDatastore, selectedDataset, setSelectedDataset}) {
  const [datasetIdentifiers, setDatasetIdentifiers] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setDatasetIdentifiers([]);
    setSelectedDataset(null);

    if (selectedDatastore) {
      setLoading(true);
      const apiEndpoint = getDatasetIdentifiersApiEndpoint(selectedDatastore);
      const params = getParams({
        limit: 10000,
        offset: 0
      });

      axios.get(apiEndpoint, {params})
      .then(response => {
        setDatasetIdentifiers([...new Set(response.data.map((datasetIdentifierDTO) => `${datasetIdentifierDTO.datasetName} (${datasetIdentifierDTO.datasetConfigId})`))]);
      })
      .catch(error => {
        console.error("Error fetching datasets:", error);
      })
      .finally(() => {
        setLoading(false);
      });
    }
  }, [selectedDatastore]);

  function getFirstWord(inputString) {
    const words = inputString.split(' ');
  
    if (words.length > 0) {
      return words[0]; 
    } 
      return null;
  }

  function getStringInsideBrackets(input) {
    const match = input.match(/\(([^)]+)\)/);
    if (match) {
      return match[1]; 
    }
    return null;
  }
  
  const handleDatasetChange = (newValue) => {
    if(newValue === null) {
      setSelectedDataset(null);
    }
    else {
      setSelectedDataset(getFirstWord(newValue));
    }
  };
  
  return (
    <Autocomplete
      sx={{ width: 900 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={datasetIdentifiers}
      value={selectedDataset}
      getOptionLabel={(dataset) => dataset}
      isOptionEqualToValue={(option, value) => getFirstWord(option) === value}
      onChange={(event, newValue) => handleDatasetChange(newValue)}
      renderOption={(props, option) => {
        const datasetName = getFirstWord(option);
        const configName = getStringInsideBrackets(option);

        return (
        <li {...props}>
          <Typography variant="body1" component="div">
            <strong>{datasetName}</strong>
            <br />
            <small>{configName}</small>
          </Typography>
        </li>
      )}}
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.DATASET_PLACEHOLDER}
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
