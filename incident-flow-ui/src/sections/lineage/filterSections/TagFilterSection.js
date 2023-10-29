import React from 'react';
import {Stack, Typography } from '@mui/material';
import {TagFilter} from '../filters';
import {FilterHeading} from '../../../utils/Constants';

const TagFilterSection = ({selectedTags, setSelectedTags}) => {
  return (
    <div>
      <Typography variant="h6" gutterBottom>
        {FilterHeading.TAG_FILTER}
      </Typography>
      <Stack mb={2} direction="row" alignItems="flex-start" spacing={3}>
        <TagFilter 
          selectedTags={selectedTags}
          setSelectedTags={setSelectedTags} 
        />
      </Stack>
    </div>
  );
};

export default TagFilterSection;
