
export const OncallTrackerHeading = {
    FLOW_VIEW: 'Flow View',
    LINEAGE_VIEW: 'Lineage View',
    PRIMARY: 'Primary',
    SECONDARY: 'Secondary',
    START_DATE: 'START_DATE',
    END_DATE: 'END_DATE'

  };

export const DataSourceFilterType = {
    PIPELINE: 'pipeline',
    DATASET: 'dataset',
  };

export const LineageType = {
    FORWARD: 'FORWARD',
    BACKWARD: 'BACKWARD',
};


export const VersionFilterType = {
    VERSION: 'version',
    PARTITION: 'partition',
    TIMESTAMP: 'timestamp',
};

export const DataStore = {
    GOOGLE_SHEET: 'GOOGLE_SHEET',
    HIVE: 'HIVE',
    HITMAN: 'HITMAN',
    FDP_TABLE: 'FDP_TABLE',
    FDP_DATASET: 'FDP_DATASET'
};

export const ApiEndpoints = {
    PIPELINE_VERSIONS: '/pipelines/{selectedPipeline}/versions',
    DATASET_VERSIONS: '/datasets/{selectedDatastore}/versions',
    DATASET_IDENTIFIERS: '/datasets/{selectedDatastore}/dataset-identifiers',
    DATASTORES: '/v1/datalink/datastores',
    PIPELINE_NAMES: '/pipelines/{selectedPipelineLocation}/names',
    DATASET_TAGS: `/datasets/tags`,
    LINEAGE_TYPES: '/v1/datalink/lineage-types',
    PIPELINE_LOCATION: '/pipelines/locations',
    LINEAGE: '/v1/datalink/lineage/{selectedLineageType}',
    FLOW_VIEW: '/v1/datalink/dependent-pipeline-dag',
    ACTIVE_ONCALL_GROUP: '/v1/oncall/active-oncall-group',
    ONCALL_GROUP_BY_DATE: '/v1/oncall/active-oncall-group/{date}',
    CURRENT_USERS: '/v1/oncall/current-users/{oncall_tracker_id}',
    HISTORICAL_USERS: '/v1/oncall/historical-users/{oncall_tracker_id}',
    ONCALL_SUGGESTIONS: '/v1/oncall/oncall-suggestions/{oncall_tracker_id}',
    ONCALL_TRACKERS: '/v1/oncall/oncall-trackers/{oncall_group_id}',
    ALL_ALERTS: '/v1/oncall/alerts',
    ALL_FOLLOWUPS: '/v1/oncall/followups',
    WATCH_EMAILS: '/v1/oncall/watch-emails',
    CONFIGURATIONS: '/v1/configurations',
    GET_FOLLOWUP_CRITERIA_API: '/v1/configurations/followup', 
    UPDATE_FOLLOWUP_CRITERIA_API: '/v1/configurations/followup/{expected_duration}'
};

export const FilterSubHeading = {
    FILTER_TYPE_SELECT_SUBHEADING: 'Select filter type:',
}

export const FilterHeading = {
    DATASET_FILTER: 'Dataset Filter',
    CONFIG_FILTER: 'Config Filter',
    PIPELINE_FILTER: 'Pipeline Filter',
    LINEAGE_FILTER: 'Lineage Filter',
    LEVEL_LIMIT_FILTER: 'Level Limit',
    TAG_FILTER: 'Tag Filter',
    VERSION_FILTER:'Version Filter',
    TIMESTAMP_FILTER: 'Timestamp Filter',
    PARTITION_FILTER: 'Partition Filter',
    UNKNOWN_FILTER: 'Unknown Filter'
}

export const Placeholder = {
    PIPELINE_LOCATION_PLACEHOLDER: 'Select Pipeline Location',
    PIPELINE_PLACEHOLDER: 'Select Pipeline',
    CONFIG_PLACEHOLDER: 'Select Config',
    DATASET_PLACEHOLDER: 'Select Dataset',
    DATASTORE_PLACEHOLDER: 'Select Datastore',
    TIMESTAMP_PLACEHOLDER: 'Select Timestamp',
    VERSION_PLACEHOLDER: 'Select Version',
    PARTITION_PLACEHOLDER: 'Select Partition',
    LINEAGE_TYPE_PLACEHOLDER: 'Select Lineage Type',
    LEVEL_LIMIT_PLACEHOLDER: 'Select Level Limit',
    TAGS_PLACEHOLDER: 'Select Tags',
}

export const ItemTypes = {
    ALERT: 'alert',
  };