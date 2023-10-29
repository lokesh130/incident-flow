import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import Iconify from '../../../components/iconify';
import {getLineageTypesApiEndpoint} from '../../../utils/ApiUtils';
import {Placeholder} from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '280px !important',
});

export default function LineageTypeFilter({ onSelectLineageType }) {
  const [lineageTypes, setLineageTypes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedValue, setSelectedValue] = useState(null);

  useEffect(() => {
    setSelectedValue(null);
    handleLineageTypeChange('BACKWARD');  // Setting up default value
    setLoading(true);
    const apiEndpoint = getLineageTypesApiEndpoint()
    axios.get(apiEndpoint)
      .then(response => {
        setLineageTypes(response.data);
      })
      .catch(error => {
        console.error("Error fetching lineage types:", error);
      })
      .finally(() => {
        setLoading(false);
      });

  }, []);

  const handleLineageTypeChange = (newValue) => {
    setSelectedValue(newValue);
    onSelectLineageType(newValue);
  };

  return (
    <Autocomplete
      sx={{ width: 280 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={lineageTypes}
      value={selectedValue}
      getOptionLabel={(lineageType) => lineageType}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handleLineageTypeChange(newValue)} 
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.LINEAGE_TYPE_PLACEHOLDER}
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
