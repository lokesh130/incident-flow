import React, { useState } from 'react';
import { Button } from '@mui/material';
import { DataStore } from '../../../utils/Constants';
import { formatDuration } from '../../../utils/StringUtils';

const NodeDetails = ({ node, handleClosePopup }) => {
  const [isNodeDetailsOpen, setIsNodeDetailsOpen] = useState(node.datasetInfoDTO);
  const [isPipelineDetailsOpen, setIsPipelineDetailsOpen] = useState(
    !(node.datasetInfoDTO && node.pipelineRunDTO)
  );
  const [isValidationDetailsOpen, setIsValidationDetailsOpen] = useState(false);

  const toggleNodeDetails = () => {
    setIsNodeDetailsOpen(!isNodeDetailsOpen);
  };

  const togglePipelineDetails = () => {
    setIsPipelineDetailsOpen(!isPipelineDetailsOpen);
  };

  const toggleValidationDetails = () => {
    setIsValidationDetailsOpen(!isValidationDetailsOpen);
  };


  const renderNodeDetails = () => {
    if (!node.datasetInfoDTO) return null;

    return (
      <div>
        {renderToggleButton('Node Details', toggleNodeDetails)}
        {isNodeDetailsOpen && (
          <div>
            <p><strong>Name:</strong> {node.datasetInfoDTO.datasetDTO.name}</p>
            <p><strong>Dataset Id:</strong> {node.datasetInfoDTO.datasetDTO.id}</p>
            {(node.datasetInfoDTO.datasetVersionDTO.id !== null) && (
              <p><strong>Version Id:</strong> {node.datasetInfoDTO.datasetVersionDTO.id}</p>
            )}
            <p><strong>Tags:</strong> {node.datasetInfoDTO.datasetDTO.tags.join(', ')}</p>
            <p><strong>URL:</strong> <a href={node.url} target="_blank" rel="noopener noreferrer">Click Here</a></p>
            {renderDataStoreDetails(node.datasetInfoDTO.datasetDTO.config)}
          </div>
        )}
      </div>
    );
  };

  const renderPipelineDetails = () => {
    return (
      <div>
        {renderToggleButton('Pipeline Details', togglePipelineDetails)}
        {isPipelineDetailsOpen && (
          <div>
            <p><strong>Pipeline Name:</strong> {node.pipelineRunDTO.name}</p>
            <p><strong>Pipeline Run Id:</strong> {node.pipelineRunDTO.id}</p>
            <p><strong>Pipeline Run Status:</strong> {node.pipelineRunDTO.status}</p>
            <p><strong>Pipeline Run Timestamp:</strong> {node.pipelineRunDTO.runTimeStamp.replace('T', ' ')}</p>
            <p><strong>Pipeline Run Duration:</strong> {formatDuration(node.pipelineRunDTO.runDuration)}</p>
          </div>
        )}
      </div>
    );
  };

  const renderValidationDetails = () => {
    return (
      <div>
        {renderToggleButton('Validation Details', toggleValidationDetails)}
        {isValidationDetailsOpen && (
          <div>
            {node.validationRunDTOs.map((validation, index) => (
              <div key={index}>
                <h4>Validation {index + 1} Data:</h4>
                <p><strong>Validation Name:</strong> {validation.validationName}</p>
                <p><strong>Validation Group:</strong> {validation.validationGroupName}</p>
                <p><strong>Validation Status:</strong> {validation.validationStatus}</p>
                <p><strong>Validation Id:</strong> {validation.validationId}</p>
                <p><strong>Validation Group Id:</strong> {validation.validationGroupId}</p>
                <p><strong>Validation Audit Id:</strong> {validation.validationAuditId}</p>
                <p><strong>Validation Group Audit Id:</strong> {validation.validationGroupAuditId}</p>
                {validation.message && (<p><strong>Message:</strong> {validation.message}</p>)}
                <p><strong>Is Primary Run:</strong> {validation.isPrimaryRun.toString()}</p>
                {validation.errorReportUrl && (
                  <p><strong>Error Report:</strong> <a href={validation.errorReportUrl} target="_blank" rel="noopener noreferrer">Link</a></p>
                )}
                <br />
              </div>
            ))}
          </div>
        )}
      </div>
    );
  };

  const renderDataStoreDetails = (config) => {
    if (config.dataStore === DataStore.HIVE) {
      return renderHiveDetails(config);
    } 
    
    if (config.dataStore === DataStore.HITMAN) {
      return renderHitmanDetails(config);
    } 
    
    if (config.dataStore === DataStore.FDP_DATASET) {
      return renderFdpDatasetDetails(config);
    }
    
    if (config.dataStore === DataStore.FDP_TABLE) {
      return renderFdpTableDetails(config);
    }
    
    if (config.dataStore === DataStore.GOOGLE_SHEET) {
      return renderGoogleSheetDetails(config);
    }

    return null;
  };

  const renderGoogleSheetDetails = (config) => (
    <>
      <p><strong>Sheet Id:</strong> {config.sheetIdentifier.spreadSheetId}</p>
      <p><strong>Tab Name:</strong> {config.sheetIdentifier.sheetName}</p>
      <p><strong>Template Id:</strong> {config.templateId}</p>
    </>
  );

  const renderHiveDetails = (config) => (
    <>
      <p><strong>Database Name:</strong> {config.databaseName}</p>
      <p><strong>Table Name:</strong> {config.tableName}</p>
      <p><strong>PartitionId:</strong> {node.datasetInfoDTO.datasetVersionDTO.config.version}</p>
    </>
  );

  const renderHitmanDetails = (config) => (
    <>
      <p><strong>Team:</strong> {config.team}</p>
      <p><strong>Usecase:</strong> {config.usecase}</p>
      <p><strong>View Name:</strong> {config.name}</p>
      <p><strong>Version:</strong> {config.version}</p>
      <p><strong>PartitionId:</strong> {node.datasetInfoDTO.datasetVersionDTO.config.version}</p>
    </>
  );

  const renderFdpDatasetDetails = (config) => (
    <>
      <p><strong>DatasetName:</strong> {config.datasetName}</p>
      <p><strong>Company:</strong> {config.identifier.company}</p>
      <p><strong>Organisation:</strong> {config.identifier.organisation}</p>
      <p><strong>Namespace:</strong> {config.identifier.namespace}</p>
      <p><strong>SchemaVersion:</strong> {config.schemaVersion}</p>
      <p><strong>PartitionId:</strong> {node.datasetInfoDTO.datasetVersionDTO.executionId}</p>
      <p><strong>Location:</strong> {node.datasetInfoDTO.datasetVersionDTO.config.location}</p>
    </>
  );

  const renderFdpTableDetails = (config) => (
    <>
      <p><strong>Table Name:</strong> {config.tableName}</p>
      <p><strong>Organisation:</strong> {config.identifier.organisation}</p>
      <p><strong>Namespace:</strong> {config.identifier.namespace}</p>
      <p><strong>ExecutionId:</strong> {node.datasetInfoDTO.datasetVersionDTO.config.executionId}</p>
    </>
  );

  const renderToggleButton = (title, onClick) => (
    <button
      onClick={onClick}
      style={{
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)',
        border: 'none',
        padding: '0 10px',
        borderRadius: '5px',
        cursor: 'pointer',
      }}
    >
      <h3>{title}</h3>
    </button>
  );

  return (
    <div
      style={{
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '10px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)',
        overflowY: 'auto', // Add this line
        maxHeight: '80vh', // You can adjust this value based on your layout
  
      }}
    >
      {renderNodeDetails()}
      <br />
      {node.pipelineRunDTO && renderPipelineDetails()}
      <br />
      {node.validationRunDTOs && node.validationRunDTOs.length > 0 && renderValidationDetails()}
      <br />
      <Button
        variant="contained"
        onClick={handleClosePopup}
        sx={{
          backgroundColor: 'gray',
          color: 'white',
          '&:hover': {
            backgroundColor: 'black',
          },
        }}
      >
        Close
      </Button>
    </div>
  );
};

export default NodeDetails;
