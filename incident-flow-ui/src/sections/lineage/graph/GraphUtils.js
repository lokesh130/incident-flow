import { LineageType } from "../../../utils/Constants"

export const getNodeBackgroundColor = (pipelineRunDTO) => {
    const color = 'lightpink';
  
    // if (pipelineRunDTO) {
    //   if (pipelineRunDTO.status === 'COMPLETED') {
    //     color = 'lightgreen';
    //   } else {
    //     color = '#ef5350';
    //   }
    // }
    return color;
  };

  export const constructHierarchy = (nodes, edges, selectedLineageType) => {
    if(selectedLineageType === LineageType.BACKWARD) {
      return constructHierarchySinksAtSameLevel(nodes, edges);
    }

    return constructHierarchySourceAtSameLevel(nodes, edges);
  }
  
  const constructHierarchyStandard = (nodes, edges) => {
    const nodeChildrenMap = new Map();
    edges.forEach((edge) => {
      if (!nodeChildrenMap.has(edge.source)) {
        nodeChildrenMap.set(edge.source, []);
      }
      nodeChildrenMap.get(edge.source).push(edge.target);
    });
  
    const levels = [];
    const visited = new Set();
  
    const constructLevel = (nodeId, level) => {
      if (!visited.has(nodeId)) {
        visited.add(nodeId);
        if (!levels[level]) {
          levels[level] = [];
        }
        levels[level].push(nodeId);
  
        const children = nodeChildrenMap.get(nodeId) || [];
        children.forEach((child) => constructLevel(child, level + 1));
      }
    };
  
    nodes.forEach((node) => {
      if (!edges.some((edge) => edge.target === node.id)) {
        constructLevel(node.id, 0);
      }
    });
  
    return levels;
  };
  
  const constructHierarchySinksAtSameLevel = (nodes, edges) => {
    const nodeParentsMap = new Map();
    const nodeOutgoingEdgesMap = new Map();
    const levels = [];
  
    // Populate node parents map and outgoing edges map
    edges.forEach((edge) => {
      if (!nodeParentsMap.has(edge.target)) {
        nodeParentsMap.set(edge.target, []);
      }
      nodeParentsMap.get(edge.target).push(edge.source);
  
      if (!nodeOutgoingEdgesMap.has(edge.source)) {
        nodeOutgoingEdgesMap.set(edge.source, 0);
      }
      nodeOutgoingEdgesMap.set(edge.source, nodeOutgoingEdgesMap.get(edge.source) + 1);
    });
  
    const queue = [];
  
    // Initialize the queue with nodes without outgoing edges
    nodes.forEach((node) => {
      if (!nodeOutgoingEdgesMap.has(node.id) || nodeOutgoingEdgesMap.get(node.id) === 0) {
        queue.push(node);
      }
    });
  
    while (queue.length > 0) {
      const level = [];
      const levelSize = queue.length;
  
      for (let i = 0; i < levelSize; i+=1) {
        const currentNode = queue.shift();
        level.push(currentNode.id);
  
        if (nodeParentsMap.has(currentNode.id)) {
          nodeParentsMap.get(currentNode.id).forEach((parentId) => {
            const parentNode = nodes.find((node) => node.id === parentId);
            nodeOutgoingEdgesMap.set(parentId, nodeOutgoingEdgesMap.get(parentId) - 1);
            if (nodeOutgoingEdgesMap.get(parentId) === 0) {
              queue.push(parentNode);
            }
          });
        }
      }
  
      levels.unshift(level); // Insert the level at the beginning to start from the bottom
    }
  
    return levels;
  };


  const constructHierarchySourceAtSameLevel = (nodes, edges) => {
    const nodeChildrenMap = new Map();
    const nodeIncomingEdgesMap = new Map();
    const levels = [];
  
    // Populate node children map and incoming edges map
    edges.forEach((edge) => {
      if (!nodeChildrenMap.has(edge.source)) {
        nodeChildrenMap.set(edge.source, []);
      }
      nodeChildrenMap.get(edge.source).push(edge.target);
  
      if (!nodeIncomingEdgesMap.has(edge.target)) {
        nodeIncomingEdgesMap.set(edge.target, 0);
      }
      nodeIncomingEdgesMap.set(edge.target, nodeIncomingEdgesMap.get(edge.target) + 1);
    });
  
    const queue = [];
  
    // Initialize the queue with nodes without incoming edges
    nodes.forEach((node) => {
      if (!nodeIncomingEdgesMap.has(node.id) || nodeIncomingEdgesMap.get(node.id) === 0) {
        queue.push(node);
      }
    });
  
    while (queue.length > 0) {
      const level = [];
      const levelSize = queue.length;
  
      for (let i = 0; i < levelSize; i+=1) {
        const currentNode = queue.shift();
        level.push(currentNode.id);
  
        if (nodeChildrenMap.has(currentNode.id)) {
          nodeChildrenMap.get(currentNode.id).forEach((childId) => {
            const childNode = nodes.find((node) => node.id === childId);
            nodeIncomingEdgesMap.set(childId, nodeIncomingEdgesMap.get(childId) - 1);
            if (nodeIncomingEdgesMap.get(childId) === 0) {
              queue.push(childNode);
            }
          });
        }
      }
  
      levels.push(level);
    }
  
    return levels;
  };
  
  
  
  export const positionHierarchy = (nodes, hierarchy) => {
    const positionedNodes = [];
  
    const levelHeight = 190;
    const nodeWidth = 160;
    const yOffset = 50;
  
    hierarchy.forEach((level, levelIndex) => {
      const numNodes = level.length;
      const y = levelIndex * levelHeight + yOffset;
  
      level.forEach((nodeId, nodeIndex) => {
        const node = nodes.find((node) => node.id === nodeId);
        const x =
          (window.innerWidth - numNodes * nodeWidth) / 2 + nodeIndex * nodeWidth;
  
        positionedNodes.push({
          ...node,
          position: { x, y },
        });
      });
    });
  
    return positionedNodes;
  };
  

export const getDisplayNodes = (dataLinkNodeDTOList) => {
  const tagColors = {};

  const predefinedColors = [
    'red',
    'green',
    'blue',
    'black',
    'purple',
    'orange',
    'cyan',
    'magenta',
  ];

  const getColorForTag = (tagName) => {
    const colorIndex = tagName.length % predefinedColors.length;
    return predefinedColors[colorIndex];
  };

  const getTagsSection = (tags) => (
    <div
      style={{
        position: 'absolute',
        top: '-25px',
        right: '0px',
        display: 'flex',
        gap: '4px',
      }}
    >
      {tags.slice(0, 2).map((tag, index) => (
        <div
          key={index}
          style={{
            background: tagColors[tag] || getColorForTag(tag),
            color: 'white',
            padding: '2px 4px',
            borderRadius: '4px',
          }}
        >
          {tag}
        </div>
      ))}
      {tags.length > 2 && (
        <div
          style={{
            background: 'rgba(0, 0, 0, 0.5)',
            color: 'white',
            padding: '2px 4px',
            borderRadius: '4px',
          }}
        >
          ..
        </div>
      )}
    </div>
  );

  const getValidationSection = (validations) => {
    // Initialize counts
    let successCount = 0;
    let runningCount = 0;
    let failedCount = 0;
  
    // Count occurrences
    validations.forEach((validation) => {
      if (validation.validationStatus === 'SUCCESS' || validation.validationStatus === 'SOFT_SUCCESS') {
        successCount += 1;
      } else if (validation.validationStatus === 'RUNNING') {
        runningCount += 1;
      } else {
        failedCount += 1;
      }
    });
  
    return (
      <div style={{
        position: 'absolute',
        bottom: '-20px',  // Decreased height
        left: '-10px',
        display: 'flex',
        gap: '4px',
      }}>
        {/* Display counts with colorful backgrounds and black text color */}
        <div
          style={{
            background: 'lightgreen',
            color: 'black',  // Black text color
            padding: '2px 8px',  // Increased width
            borderRadius: '4px',
          }}
        >
          {successCount}
        </div>
        <div
          style={{
            background: 'red',
            color: 'black',  // Black text color
            padding: '2px 8px',  // Increased width
            borderRadius: '4px',
          }}
        >
          {failedCount}
        </div>
      </div>
    );
  };
    

  const getDatasetInfo = (node) => (
    <div>
      <strong>{node.datasetInfoDTO.datasetDTO.name}</strong>
      <br />
      {node.datasetInfoDTO.datasetVersionDTO.id}
      <br />
      {node.pipelineRunDTO !== null && `(${node.pipelineRunDTO.name})`}
    </div>
  );

  const getPipelineRunInfo = (node) => (
    <div>
      <strong>{node.pipelineRunDTO.name}</strong>
      <br />
      {node.pipelineRunDTO.id}
    </div>
  );

  return dataLinkNodeDTOList.map((node) => {
    return {
      id: node.id,
      isVisible: node.isVisible,
      data: node.isVisible
        ? {
            label: (
              <div
                style={{
                  position: 'relative',
                  maxWidth: '150px',
                  textAlign: 'center',
                  wordWrap: 'break-word',
                }}
              >
                {node.validationRunDTOs !== null && node.validationRunDTOs.length > 0 && (getValidationSection(node.validationRunDTOs))}
                {node.datasetInfoDTO !== null &&
                  node.datasetInfoDTO.datasetDTO.tags.length > 0 && (
                    getTagsSection(node.datasetInfoDTO.datasetDTO.tags)
                  )}
              
                {node.datasetInfoDTO !== null && getDatasetInfo(node)}
                {node.pipelineRunDTO !== null &&
                  node.datasetInfoDTO === null &&
                  getPipelineRunInfo(node)}
              </div>
            ),
          }
        : {},
      position: { x: 100, y: 100 },
      style: node.isVisible
        ? node.pipelineRunDTO !== null && node.datasetInfoDTO === null
          ? {
              background: 'lightblue',
            }
          : {
              background: getNodeBackgroundColor(node.pipelineRunDTO),
            }
        : {
            background: getNodeBackgroundColor(node.pipelineRunDTO),
            width: '30px',
            height: '30px',
            borderRadius: '50%',
          },
    };
  });
};


  export const getDisplayEdges = (dataLinkEdgeDTOList) => {
    return dataLinkEdgeDTOList.map((edge) => ({
      id: [edge.sourceNodeId, edge.targetNodeId].join('-'),
      source: edge.sourceNodeId,
      target: edge.targetNodeId,
    }));
  };
  