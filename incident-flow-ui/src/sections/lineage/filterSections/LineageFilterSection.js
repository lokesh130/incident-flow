import React, {useEffect, useState } from 'react';
import {Stack, Typography } from '@mui/material';
import { LineageTypeFilter, LevelLimitFilter } from '../filters';
import {FilterHeading} from '../../../utils/Constants';

const LineageFilterSection = ({
  setSelectedLineageType,
  setSelectedLevelLimit,
  selectedPipeline
}) => {

  return (
    <div>
      <Stack mb={2} direction="row" alignItems="flex-start" spacing={3}>
        <div>
          <Typography variant="h6" gutterBottom>
            {FilterHeading.LINEAGE_FILTER}
          </Typography>
          {
          (selectedPipeline === null) ?<LineageTypeFilter onSelectLineageType={setSelectedLineageType} /> 
              :<LineageTypeFilter onSelectLineageType={setSelectedLineageType} /> 
          }
        </div>
        <div>
          <Typography variant="h6" gutterBottom>
              {FilterHeading.LEVEL_LIMIT_FILTER}
          </Typography>
          <LevelLimitFilter onSelectLevelLimit={setSelectedLevelLimit} />
        </div>
      </Stack>
    </div>
  );
};

export default LineageFilterSection;
