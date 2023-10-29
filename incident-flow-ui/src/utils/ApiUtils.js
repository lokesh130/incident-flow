import {DataSourceFilterType, ApiEndpoints} from './Constants';

export function getPipelineLocationsApiEndpoint() {
    return ApiEndpoints.PIPELINE_LOCATION;
}

export function getLineageTypesApiEndpoint() {
    return ApiEndpoints.LINEAGE_TYPES;
}

export function getVersionInfoApiEndpoint(dataSourceFilterType, selectedPipeline, selectedDatastore) {
    if (dataSourceFilterType === DataSourceFilterType.PIPELINE) {
        return ApiEndpoints.PIPELINE_VERSIONS.replace('{selectedPipeline}', selectedPipeline);
    } 
    return ApiEndpoints.DATASET_VERSIONS.replace('{selectedDatastore}', selectedDatastore);
}

export function getDatasetConfigsApiEndpoint(selectedDatastore) {
    return ApiEndpoints.DATASET_CONFIGS.replace('{selectedDatastore}', selectedDatastore);
}

export function getDatastoresApiEndpoint() {
    return ApiEndpoints.DATASTORES;
}

export function getDatasetTagsApiEndpoint() {
    return ApiEndpoints.DATASET_TAGS;
}

export function getDatasetIdentifiersApiEndpoint(selectedDatastore) {
    return ApiEndpoints.DATASET_IDENTIFIERS.replace('{selectedDatastore}', selectedDatastore);
}

export function getPipelineNamesApiEndpoint(selectedPipelineLocation) {
    return ApiEndpoints.PIPELINE_NAMES.replace('{selectedPipelineLocation}', selectedPipelineLocation);
}

export function getLineageApiEndpoint(selectedLineageType, isFlowView) {
    if(isFlowView) {
        return ApiEndpoints.FLOW_VIEW;
    }
    return ApiEndpoints.LINEAGE.replace('{selectedLineageType}', selectedLineageType);
}


export function getActiveOncallGroupApiEndpoint() {
    return ApiEndpoints.ACTIVE_ONCALL_GROUP;
}

export function getOncallGroupByDateApiEndpoint(date) {
    return ApiEndpoints.ONCALL_GROUP_BY_DATE.replace('{date}', date);
}

export function getCurrentUsersApiEndpoint(oncallTrackerId) {
    return ApiEndpoints.CURRENT_USERS.replace('{oncall_tracker_id}', oncallTrackerId);
}

export function getHistoricalUsersApiEndpoint(oncallTrackerId) {
    return ApiEndpoints.HISTORICAL_USERS.replace('{oncall_tracker_id}', oncallTrackerId);
}

export function getOncallSuggestionsApiEndpoint(oncallTrackerId) {
    return ApiEndpoints.ONCALL_SUGGESTIONS.replace('{oncall_tracker_id}', oncallTrackerId);
}

export function getOncallTrackersApiEndpoint(oncallGroupId) {
    return ApiEndpoints.ONCALL_TRACKERS.replace('{oncall_group_id}', oncallGroupId);
}

export function getAllAlerts() {
    return ApiEndpoints.ALL_ALERTS;
}

export function getAllFollowups() {
    return ApiEndpoints.ALL_FOLLOWUPS;
}

export function getWatchEmailsApiEndpoint() {
    return ApiEndpoints.WATCH_EMAILS;
}
  
export function getConfigurationsApiEndpoint() {
    return ApiEndpoints.CONFIGURATIONS;
}

export function getFollowupConfigApiEndpoint() {
    return ApiEndpoints.GET_FOLLOWUP_CRITERIA_API;
}

export function updateFollowupConfigApiEndpoint(expectedDuration) {
    return ApiEndpoints.UPDATE_FOLLOWUP_CRITERIA_API.replace('{expected_duration}', expectedDuration);
}

export function getParams(paramObj) {
  const params = {};

  Object.keys(paramObj).forEach((key) => {
    if (paramObj[key] !== null && paramObj[key] !== undefined) {
      params[key] = paramObj[key];
    }
  });

  return params;
}
  