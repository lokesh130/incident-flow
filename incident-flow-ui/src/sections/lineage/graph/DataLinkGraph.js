import React, { useState, useEffect} from 'react';
import ReactFlow, {
    useNodesState,
    useEdgesState,
    ConnectionLineType,
    Controls,
    Background
  } from "react-flow-renderer";
import CircularProgress from '@mui/material/CircularProgress';
import { getLineageApiEndpoint, getParams } from '../../../utils/ApiUtils';
import {constructHierarchy, positionHierarchy, getDisplayNodes, getDisplayEdges} from './GraphUtils';
import NodeDetails from './NodeDetails';
import axios from '../../../utils/CustomAxios';

const DataLinkGraph = ({ refreshCounter, selectedDatastore, selectedDataset, selectedVersion, selectedTimestamp, selectedPartition, selectedLineageType, selectedLevelLimit, selectedPipelineLocation, selectedPipeline, dataSourceFilterType, selectedTags, isFlowView}) => {
   const [nodes, setNodes, onNodesChange] = useNodesState([]);
   const [edges, setEdges, onEdgesChange] = useEdgesState([]);
   const [responseData, setResponseData] = useState(null);
   const [selectedNode, setSelectedNode] = useState(null);
   const [isLoading, setIsLoading] = useState(false);
  
  useEffect(() => {
    setNodes([]);
    setEdges([]);
    if ((selectedDatastore || selectedPipelineLocation) && (selectedDataset || selectedPipeline)&& (selectedPartition || selectedVersion || selectedTimestamp) && (isFlowView || selectedLineageType)) {
        setIsLoading(true);  
        const apiEndpoint = getLineageApiEndpoint(selectedLineageType, isFlowView);
        const params = getParams({
          data_source_type: dataSourceFilterType,
          data_store: selectedDatastore,
          pipeline_name: selectedPipeline,
          dataset_name: selectedDataset,
          level_limit: selectedLevelLimit,
          tags: selectedTags,
          version_id: selectedVersion,
          timestamp: selectedTimestamp,
          partition_id: selectedPartition
        });

      axios.get(apiEndpoint, { params })
        .then(response => {
          setResponseData(response.data);

          const displayNodes = getDisplayNodes(response.data.dataLinkNodeDTOList);
          const displayEdges = getDisplayEdges(response.data.dataLinkEdgeDTOList);

          const hierarchy = constructHierarchy(displayNodes, displayEdges, selectedLineageType);
          const positionedNodes = positionHierarchy(displayNodes, hierarchy);

          setNodes(positionedNodes);
          setEdges(displayEdges);
        })
        .catch(error => {
          console.error('Error fetching graph data:', error);
          setNodes([]);
          setEdges([]);
        })
        .finally(() => {
            setIsLoading(false);
        });
    }
  }, [refreshCounter]);

  const handleNodeDoubleClick = (event, node) => {
    setSelectedNode(node);
  };
  
  return (
    <div style={{ position: 'relative', height: '500px', border: '1px solid #ccc', marginTop: '20px' }}>
    {isLoading ? (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
          <CircularProgress />
        </div>
      ) : (
        <ReactFlow
        nodes={nodes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        fitView
        attributionPosition="bottom-left"
        connectionLineType={ConnectionLineType.SmoothStep}
        onNodeDoubleClick={handleNodeDoubleClick}
        >
        <Controls />
        <Background color="#888" gap={16} />
        </ReactFlow>
    )}
    {selectedNode && (
    <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.3)', zIndex: 9999 }}>
    {
      (() => {
        const node = responseData.dataLinkNodeDTOList.find((node) => node.id === selectedNode.id);
        return (
          <NodeDetails node={node} handleClosePopup={() => setSelectedNode(null)} />
        );
      })()
    }
    </div>
    )}
    </div>
  );
};

export default DataLinkGraph;
