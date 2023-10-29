import React, { useState, useEffect } from 'react';
import { Stack, Typography, Button } from '@mui/material';
import { VersionFilter, TimestampFilter, PartitionFilter } from '../filters';
import { DataSourceFilterType, VersionFilterType, FilterHeading} from '../../../utils/Constants';
import { getVersionInfoApiEndpoint, getParams } from '../../../utils/ApiUtils';
import {convertToCamelCase} from '../../../utils/StringUtils';
import axios from '../../../utils/CustomAxios';

const VersionInfoFilterSection = ({
  selectedDatastore,
  selectedDataset,
  selectedPipeline,
  selectedVersion,
  setSelectedVersion,
  selectedPartition,
  setSelectedPartition,
  selectedTimestamp,
  setSelectedTimestamp,
  dataSourceFilterType
}) => {
    const [loading, setLoading] = useState(false);
    const [versionInfos, setVersionInfos] = useState([]);
    const [versionFilterType, setVersionFilterType] = useState(VersionFilterType.TIMESTAMP);

    const handleVersionFilterTypeChange = (type) => {
        setSelectedVersion(null);
        setSelectedPartition(null);
        setSelectedTimestamp(null);
        setVersionFilterType(type);
    };

    useEffect(() => {
      setVersionInfos([]);
      setSelectedVersion(null);
      setSelectedPartition(null);
      setSelectedTimestamp(null);
      setVersionFilterType(VersionFilterType.TIMESTAMP);
      if ((selectedDatastore && (selectedDataset)) || selectedPipeline) {
        setLoading(true);
        const apiEndPoint = getVersionInfoApiEndpoint(dataSourceFilterType, selectedPipeline, selectedDatastore);
        const params = getParams({
          limit: 100,
          offset: 0,
          dataset_name: selectedDataset,
        });
        
        axios.get(apiEndPoint, { params })
          .then(response => {
            setVersionInfos(response.data);
          })
          .catch(error => {
            console.error("Error fetching timestamps:", error);
          })
          .finally(() => {
            setLoading(false);
          });
      }
    }, [dataSourceFilterType, selectedDatastore, selectedDataset, selectedPipeline]);


    return (
      <div>
        <Typography variant="h6" gutterBottom>
          {(versionFilterType === VersionFilterType.VERSION) && (
              FilterHeading.VERSION_FILTER
          )}
          {(versionFilterType === VersionFilterType.TIMESTAMP) && (
              FilterHeading.TIMESTAMP_FILTER
          )}
          {(versionFilterType === VersionFilterType.PARTITION) && (
              FilterHeading.PARTITION_FILTER
          )}
        </Typography>
        <Stack mb={2} direction="row" alignItems="flex-start" spacing={3}>
          {(versionFilterType === VersionFilterType.VERSION) && (
            <VersionFilter
              versionInfos = {versionInfos}
              loading = {loading}
              selectedVersion={selectedVersion}
              setSelectedVersion={setSelectedVersion}
            />
          )}
          {(versionFilterType === VersionFilterType.TIMESTAMP) && (
            <TimestampFilter
              versionInfos = {versionInfos}
              loading = {loading}
              selectedTimestamp={selectedTimestamp}
              setSelectedTimestamp={setSelectedTimestamp}
            />
          )}
          {(versionFilterType === VersionFilterType.PARTITION) && (
            <PartitionFilter
              versionInfos = {versionInfos}
              loading = {loading}
              selectedPartition={selectedPartition}
              setSelectedPartition={setSelectedPartition}
            />
          )}
        </Stack>
        <Stack direction="row" alignItems="center" spacing={1}>
          <Typography variant="body2">Select filter type:</Typography>
          {(dataSourceFilterType !== DataSourceFilterType.PIPELINE) && (
            <Button
                variant="outlined"
                onClick={() => handleVersionFilterTypeChange(VersionFilterType.PARTITION)}
                disabled={versionFilterType === VersionFilterType.PARTITION}
            >
                {convertToCamelCase(VersionFilterType.PARTITION)}
            </Button>
           )}
           <Button
                variant="outlined"
                onClick={() => handleVersionFilterTypeChange(VersionFilterType.VERSION)}
                disabled={versionFilterType === VersionFilterType.VERSION}
            >
                {convertToCamelCase(VersionFilterType.VERSION)}
           </Button>
           <Button
                variant="outlined"
                onClick={() => handleVersionFilterTypeChange(VersionFilterType.TIMESTAMP)}
                disabled={versionFilterType === VersionFilterType.TIMESTAMP}
              >
                {convertToCamelCase(VersionFilterType.TIMESTAMP)}
           </Button>
        </Stack>
      </div>
    );
};

export default VersionInfoFilterSection;
