import React from 'react';
import { Stack, Typography, Button } from '@mui/material';
import { DatastoreFilter, DatasetFilter, PipelineLocationFilter, PipelineFilter} from '../filters';
import {DataSourceFilterType, FilterSubHeading, FilterHeading} from '../../../utils/Constants';
import {convertToCamelCase} from '../../../utils/StringUtils';

const DatasetFilterSection = ({
  selectedDatastore,
  setSelectedDatastore,
  selectedPipelineLocation,
  setSelectedPipelineLocation,
  selectedPipeline,
  setSelectedPipeline,
  selectedDataset,
  setSelectedDataset,
  dataSourceFilterType,
  setDataSourceFilterType,
  isFlowView
}) => {

  const handleFilterTypeChange = (type) => {
    setSelectedPipeline(null);
    setSelectedPipelineLocation(null);
    setSelectedDataset(null);
    setSelectedDatastore(null);
    setDataSourceFilterType(type);
  };

  return (
    <>
      <Typography variant="h6" gutterBottom>
        {
          dataSourceFilterType === DataSourceFilterType.DATASET ?  FilterHeading.DATASET_FILTER: 
          dataSourceFilterType === DataSourceFilterType.PIPELINE ? FilterHeading.PIPELINE_FILTER: FilterHeading.UNKNOWN_FILTER
        }
      </Typography>
      <Stack mb={2} direction="row" alignItems="flex-start" spacing={3}>
        {dataSourceFilterType === DataSourceFilterType.DATASET && (
          <>
            <DatastoreFilter 
              selectedDatastore={selectedDatastore}
              setSelectedDatastore={setSelectedDatastore} 
            />
            <DatasetFilter
              selectedDatastore={selectedDatastore}
              selectedDataset={selectedDataset}
              setSelectedDataset={setSelectedDataset}
            />
          </>
        )}
        {dataSourceFilterType === DataSourceFilterType.PIPELINE && (
          <>
            <PipelineLocationFilter
              selectedPipelineLocation={selectedPipelineLocation}
              setSelectedPipelineLocation={setSelectedPipelineLocation}
            />
            <PipelineFilter
              selectedPipelineLocation={selectedPipelineLocation}
              selectedPipeline ={selectedPipeline}
              setSelectedPipeline={setSelectedPipeline}
            />
          </>
        )}
      </Stack>
      <Stack direction="row" alignItems="center" spacing={1}>
        <Typography variant="body2">{FilterSubHeading.FILTER_TYPE_SELECT_SUBHEADING}</Typography>
        <Button
          variant="outlined"
          onClick={() => handleFilterTypeChange(DataSourceFilterType.DATASET)}
          disabled={dataSourceFilterType === DataSourceFilterType.DATASET}
        >
          {convertToCamelCase(DataSourceFilterType.DATASET)}
        </Button>
        <Button
          variant="outlined"
          onClick={() => handleFilterTypeChange(DataSourceFilterType.PIPELINE)}
          disabled={dataSourceFilterType === DataSourceFilterType.PIPELINE}
          style={{ display: isFlowView ? 'none' : 'inline-block' }}
        >
          {convertToCamelCase(DataSourceFilterType.PIPELINE)}
        </Button>
      </Stack>
    </>
  );
};

export default DatasetFilterSection;
