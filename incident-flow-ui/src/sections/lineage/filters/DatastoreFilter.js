import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { Autocomplete, InputAdornment, Popper, TextField, CircularProgress } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import Iconify from '../../../components/iconify';
import {getDatastoresApiEndpoint} from '../../../utils/ApiUtils';
import { Placeholder } from '../../../utils/Constants';

const StyledPopper = styled((props) => <Popper placement="bottom-start" {...props} />)({
  width: '280px !important',
});

export default function DatastoreFilter({selectedDatastore, setSelectedDatastore}) {
  const [datastores, setDatastores] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setDatastores([]);
    setSelectedDatastore(null);
    setLoading(true);
    const apiEndpoint = getDatastoresApiEndpoint();
    axios.get(apiEndpoint)
      .then(response => {
        setDatastores(response.data);
      })
      .catch(error => {
        console.error("Error fetching datastores:", error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleDatastoreChange = (newValue) => {
    setSelectedDatastore(newValue);
  };

  return (
    <Autocomplete
      sx={{ width: 280 }}
      autoHighlight
      popupIcon={null}
      PopperComponent={StyledPopper}
      options={datastores}
      value={selectedDatastore}
      getOptionLabel={(datastore) => datastore}
      isOptionEqualToValue={(option, value) => option === value}
      onChange={(event, newValue) => handleDatastoreChange(newValue)} 
      renderInput={(params) => (
        <TextField
          {...params}
          placeholder={Placeholder.DATASTORE_PLACEHOLDER}
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
