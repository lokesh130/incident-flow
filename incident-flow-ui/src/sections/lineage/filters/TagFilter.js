import React, { useState, useEffect } from 'react';
import { styled } from '@mui/material/styles';
import { FormControl, InputLabel, Select, MenuItem, Chip, CircularProgress } from '@mui/material';
import axios from '../../../utils/CustomAxios';
import { getDatasetTagsApiEndpoint } from '../../../utils/ApiUtils';
import {Placeholder} from '../../../utils/Constants';

const StyledFormControl = styled(FormControl)({
  width: '280px',
});

const StyledSelect = styled(Select)({
  minHeight: '36px',
});

const StyledChip = styled(Chip)({
  margin: '2px',
});

const LoadingIcon = styled(CircularProgress)({
  position: 'absolute',
  right: '10px',
  top: '50%',
  transform: 'translateY(-50%)',
});

export default function TagFilter({ selectedTags, setSelectedTags }) {
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const apiEndpoint = getDatasetTagsApiEndpoint();
    axios
      .get(apiEndpoint)
      .then((response) => {
        setTags(response.data);
      })
      .catch((error) => {
        console.error('Error fetching tags:', error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleTagChange = (event) => {
    setSelectedTags(event.target.value);
  };

  return (
    <StyledFormControl variant="outlined">
      <InputLabel htmlFor="tag-filter">Select Tags</InputLabel>
      <StyledSelect
        id="tag-filter"
        multiple
        value={selectedTags}
        onChange={handleTagChange}
        label={Placeholder.TAGS_PLACEHOLDER}
        renderValue={(selected) => (
          <div style={{ display: 'flex', flexWrap: 'wrap', position: 'relative' }}>
            {selected.map((value) => (
              <StyledChip key={value} label={value} />
            ))}
            {loading && <LoadingIcon color="inherit" size={20} />}
          </div>
        )}
      >
        {tags.map((tag) => (
          <MenuItem key={tag} value={tag}>
            {tag}
          </MenuItem>
        ))}
      </StyledSelect>
    </StyledFormControl>
  );
}
