package com.flipkart.fdsg.planning.ip.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;


public class Constants {

    public static final String APPLICATION_CLASS_DYNAMIC_ARGS = "JobDriver.applicationClass.dynamic.args";
    public static final String JOB_SPECIFIC_PROPERTIES = "job_specific_properties";
    public static final String HDFS_REPORT_PATH = "HDFS_REPORT_PATH";
    public static final String UPLOAD_ID = "upload-id";
    public static final String SELL_PLAN_IDS = "sell-plan-ids";
    public static final String DATA_SET_ID = "dataSetId";
    public static final String CHARSET_UTF8 = StandardCharsets.UTF_8.name();
    public static final int MAX_PAGE_SIZE = 10000;
    public static final String UPLOAD_EXECUTOR_SERVICE = "UPLOAD-EXECUTOR-SERVICE";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatterBuilder DATE_FORMATTER_BUILDER =  new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("[yyyyMMdd]"+"[yyyy-MM-dd]"));
    public static final String BASIC = "Basic";
    public static final String BUSINESS_WEEK_PATTERN = "yyyy'w'ww";
    public static final DateTimeFormatter BUSINESS_WEEK_FORMATTER = DateTimeFormatter.ofPattern(BUSINESS_WEEK_PATTERN);
    public static final String YEAR_WEEK_PATTERN = "yyyyww";
    public static final DateTimeFormatter YEAR_WEEK_FORMATTER = DateTimeFormatter.ofPattern(YEAR_WEEK_PATTERN);
    public static final Integer BULK_DOCUMENT_SIZE_FROM_ES = 500;
    public static final String TEST = "test";
    public static final String TEST_VERSION_COLUMN = "refresh_id";
    public static final Long TEST_VERSION_ID_1 = 1L;
    public static final String PRODUCTION = "production";
    public static final String STRING_JOIN_DELIMITER = ",";
    public static final String APP_PLATFORM = "App";
    public static final Integer DAY_START_HOUR = 0;
    public static final Integer DAY_END_HOUR = 24;
    public static final String EQUALS = "=";
    public static final String QUATATION_MARK = "\"";
    public static final String KEY_VALUE = "%s=%s";
    public static final String FORWARD_SLASH = "/";
    public static final String OPENING_CIRCULAR_BRACE = "(";
    public static final String CLOSING_CIRCULAR_BRACE = ")";
    public static final String DOT = ".";
    public static final String AND = "and";
    public static final String DOT_SPLITTER = "\\.";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String PIPE = "|";
    public static final String AMPERSAND = "&";
    public static final String COLON=":";
    public static final String CSV_FILE_EXTENSION = ".csv";
    public static final String DASH = "-";
    public static final String X_AUTHENTICATED_USER = "X-Authenticated-User";
    public static final String X_CLIENT_SECRET = "X-Client-Secret";
    public static final String X_CLIENT_ID = "X-Client-Id";
    public static final String HTTP_URL_PREFIX = "http://";
    public static final String QUERY = "query";
    public static final String NEW_LINE = "\n";
    public static final String TAB = "\t";
    public static final Character SINGLE_QUOTE = '\'';
    public static final String DATE = "date";
    public static final String GOOGLE_SPREADSHEET_PREFIX = "https://docs.google.com/spreadsheets/d/";
    public static final String STRUCT = "STRUCT";
    public static final String CROSS = "x";
    public static final String ASTERISK = "*";
    public static final String AUTHN_CLIENT_ID="authN-clientId";
    public static final String AUTHN_CLIENT_SECRET_KEY="authN-clientSecret";
    public static final String AUTHN_URL="authN-url";
    public static final String AUTHN_MAX_CONNECTION="authN-maxConnection";
    public static final String AUTHN_CONNECTION_TIMEOUT="authN-connectTimeOut";
    public static final String AUTHN_READ_TIMEOUT="authN-readTimeOut";
    public static final String ID = "ID";
    public static final String USER = "user";
    public static final String IS_VALID = "is_valid";
    public static final String VALID_QUANTITY = "valid_quantity";
    public static final String INVALID_QUANTITY = "invalid_quantity";
    public static final String INVALID_PERCENT = "invalid_percent";
    public static final String INVALID_QUANTITY_PERCENTAGE_EXPRESSION = "invalid_quantity*100/(invalid_quantity+valid_quantity)";
    public static final String INTEGER = "INTEGER";
    public static final String DOUBLE = "DOUBLE";
    public static final String LONG = "LONG";
    public static final String BU = "BU";
    public static final String SC_CAPS = "SC";
    public static final String FT = "FT";
    public static final String MONTH = "MONTH";
    public static final String DAY = "day";
    public static final String DAY_CAPS = "DAY";
    public static final String PARAMETER = "PARAMETER";
    public static final String DAYVALUE = "DAYVALUE";
    public static final String VALUE = "VALUE";
    public static final String MP = "MP";
    public static final String GROCERY_SC_NAME = "Grocery";
    public static final String HYPERLOCAL_SC_NAME = "Hyperlocal";
    public static final String FORECAST_MODEL_OUTPUT_TYPE_RAW = "RAW";
    public static final String COMPANY = "fkint";
    public static final String ORG = "planning";
    public static final String NA = "NA";
    public static final String TRIGGER_DSP_STEP_NAME = "TRIGGER_DSP";
    public static final String DATA_LINK_NODE_ID_TEMPLATE = "%s_%s";
    public static final String COMMA_NEWLINE_DELIMITER = ",\n";

    public static class ValidationConstants {
        public static final String VALKYRIE = "VALKYRIE";
        public static final String PSP = "PSP";
    }

    public static class ElasticsearchFields {
        public static final String FSN = "fsn";
        public static final String ASSIGNED_FC = "assignedfc";
        public static final String WEEK = "week";
        public static final String FORECAST = "forecast";
        public static final String OVERRIDE = "override";
        public static final String OVERRIDE_REASON = "override_reason";
        public static final String BUSINESS_UNIT = "analytic_business_unit";
        public static final String CATEGORY = "analytic_category";
        public static final String SUB_CATEGORY = "analytic_sub_category";
        public static final String SUPER_CATEGORY = "analytic_super_category";
        public static final String VERTICAL = "analytic_vertical";
        public static final String BRAND = "brand";
        public static final String IS_LARGE = "is_large";
        public static final String IS_ALPHA = "is_alpha";
        public static final String EIGHT_WEEK_FORECAST = "eight_week_forecast";
        public static final String FIFTEEN_WEEK_FORECAST = "fifteen_week_forecast";
        public static final String FOUR_WEEK_FORECAST = "four_week_forecast";
        public static final String PRODUCT_TITLE = "product_title";
        public static final String PRICE = "price";
        public static final String PRICE_PERCENTILE = "price_percentile";
        public static final String SYSTEM_FORECAST = "system_forecast";
        public static final String OPS_FORECAST = "ops_forecast";

        public static final String KEY_TYPE = "key_type";
        public static final String PLUS_VISITS = "plus_visits";
        public static final String DAY = "day";
        public static final String HOUR = "hour";
        public static final String START_HOUR = "start_hour";
        public static final String END_HOUR = "end_hour";
        public static final String FT = "ft";
        public static final String BU = "bu";
        public static final String SC = "sc";
        public static final String ALPHA = "alpha";
        public static final String MPFBF = "mp-fbf";
        public static final String NFBF = "nfbf";
        public static final String ALITE = "alite";
        public static final String NA = "NA";
    }

    public static final String PLAN_ID = "plan-id";
    public static final String SCENARIO_ID = "scenario-id";
    public static final String EMAIL_IDS = "email-ids";
    public static final String PLAN_TYPE = "plan-type";
    public static final String LOCATION = "location";
    public static final String DIMENSION_LEVEL = "dimension-level";
    public static final String LEVEL_VALUES = "level-values";
    public static final String PLAN_RUN_DAG_URL_TEMPLATE = "%s/v1/hitman_client/run_id/%d/dag_details";

    public static final String TOTAL = "Total";
    public static final String DAY_NUMBER = "Day %d";
    public static final String DAY_ACRONYM = "D%d";
    public static final String FT_DELIMITER_IN_METRIC = "-";
    public static final String DFU_EVENT_CALENDAR_DATASET = "dfu_event_calendar_dataset";
    public static final String HIVE = "HIVE";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final Integer ZERO = 0;
    public static final Integer THREE_HUNDRED = 300;
    public static final Integer HUNDRED = 100;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final String NULL = "null";
    public static final String HYPHEN = "-";

    public static final Integer SECOND_FOR_END_OF_HOUR = 59;
    public static final Integer MINUTE_FOR_END_OF_HOUR = 59;
    public static final Integer HOUR_FOR_END_OF_DAY = 23;

    public static final String OMNITURE_FTP_CLIENT = "OMNITURE-FTP-CLIENT";
    public static final String FTP_SUPPLY_CHAIN = "supply_chain";
    public static final String FTP_IPC_GROCERY = "ipp_ftp";

    public static final String RUN_DATE = "run_date";
    public static final String DATE_FORMAT = "date_format";
    public static final String FTP_JOB_IDENTIFIER = "job_id";
    public static final String FTP_INPUT_IDENTIFIER = "input_id";

    public static final String D42_ARCHIVAL_ACCOUNT = "archival";
    public static final String D42_SHARED_ACTIVE_ACCOUNT = "sharedActive";

    public static final String REQUEST_ID = "request-id";
    public static final String CALLBACK_URL = "callback-url";
    public static final String DCP_HOST = "dcp-host";
    public static final String INPUT_DATABASE = "input-database";
    public static final String INPUT_FACT = "input-fact";
    public static final String INPUT_REFRESH_ID = "input-refresh_id";
    public static final String OUTPUT_DATABASE = "output-database";
    public static final String OUTPUT_FACT = "output-fact";

    public static final String BATCH_ID = "batch_id";
    public static final String START_WEEK = "start_week";
    public static final String END_WEEK = "end_week";

    public static final String SC = "SC";

    public static final String ENSEMBLE = "ENSEMBLE";
    public static final String SELL_PLAN_NATIONAL = "SELL_PLAN_NATIONAL";
    public static final String SELL_PLAN_REGIONAL = "SELL_PLAN_REGIONAL";

    public static final DateTimeFormatter FTP_FILE_MODIFICATION_FORMATTER = DateTimeFormatter.ofPattern("SSS yyyyMMddHHmmss");


    public static final String FACT_NAME = "FACT_NAME";
    public static final String PARTITION_ID = "PARTITION_ID";
    public static final String DB_NAME = "DB_NAME";
    public static final String PARTITION="refresh_id";
    public static final String BIG_INT="bigint";

    public static final String IPP_DB = "ipp_etl";

    public static final String MODEL_NAME = "model_name";
    public static final String RUN_EXTERNAL_ID="RUN_EXTERNAL_ID";

    public static final String MAU_FILE_NAME_SUFFIX_FORMAT = "E. dd MMM. yyyy";
    public static final String IPC_SCHEMA_PREFIX_COLUMNS = "Forecast_W";

    public static final String EMPTY_STRING = "";
    public static final String UNDERSCORE = "_";
    public static final String DOUBLE_UNDERSCORE = "__";
    public static final String SUCCESS = "SUCCESS";

    public static final Long LONG_ONE = 1L;
    public static final Long LONG_ZERO = 0L;

    public static final String HITMAN_TABLE_FORMAT = "%s__%s__%s__%s__%s";
    public static final String HITMAN = "hitman";
    public static final String HITMAN_IDENTIFIER_FORMAT = "%s:%s:%s:%s:%s";
    public static final String GRAVITY_QUERY_DATASET_NAME = "gravity_query_dataset";
    public static final String HITMAN_PROD = "hitman_prod";
    public static final String HITMAN_STAGE = "hitman_stage";

    public static final String D42_DIRECTORY_STRUCTURE = "%s/%s/%s";
    public static final String FC_ZONE_MAPPING_TABLE = "demand_planning_prod.fc_business_zone_mapping";
    public static final String IPC_GROCERY = "IPC_GROCERY";
    public static final String GROCERY_SELL_PLAN = "GROCERY_SELL_PLAN";
    public static final int DAYS_IN_MONTH = 31;
    public static final String DAYS = "days";
    public static final String BAU_DAY_TYPE = "BAU";

    public static final String FLIPKART_MARKETPLACE = "Flipkart";
    public static final String ACCOUNT_IDENTIFIER = "accountIdentifier";

    public static final String UNION = "union";
    public static final String UNION_ALL_SEPARATOR = " union all ";

    public static final String PLANNING_DEMAND_D42_BUCKET = "fk-p-planning-demand";
    public static final String D42_STORAGE_PATH_FORMAT = "%s/%s";
    public static final String D42_REGION = "asia-south1";

    public static class SpreadSheet {

        public static class Config {
            public static final String CONFIG_BASE_PATH = "/google-sheet-templates";

            public static final String FC_LEVEL_FORECAST_TABLES_CONFIG_FILE = "/large_splits_fc_level_forecast_tables_config.json";
            public static final String HUB_LEVEL_FORECAST_TABLES_CONFIG_FILE = "/large_splits_hub_level_forecast_tables_config.json";
            public static final String ZONE_LEVEL_FORECAST_TABLES_CONFIG_FILE = "/large_splits_zone_level_forecast_tables_config.json";

        }
    }

    public static final String DEMAND_PLAN_RUN_VERSION_FORMAT = "1.%d.%d"; // <calendar>.<scenario>.<run>

    public static final String NATIONAL = "NATIONAL";
    public static final String REGIONAL = "REGIONAL";
    public static final String LARGE ="Large";
    public static final String REGIONAL_BASED = "REGIONAL_BASED";


    public static final String RETAIL_USE_CASES = "use_case_grocery,use_case_normal_iwit," +
            "use_case_normal_rp_fsn_cluster,use_case_event_normal_fsn_national,use_case_hyperlocal,use_case_hyperlocal_direct,use_case_hyperlocal_iwit_sm_ds,use_case_grocery_iwit_sm_ds";

    public static final String ONCALL_EMAIL = "demand-planning-oncall@flipkart.com";

    public static final String COPIED = "copied";

    public static final String COPY ="copy";

    public static final String EMPTY_DF="empty_df";

    public static final String DEFAULT_CLUSTER="KRIOS";

    public static final String JOB_IDENTIFIER_SUFFIX = "JOB_IDENTIFIER_SUFFIX";

    public static final String GLOBAL_CONFIG_IDENTIFIER = "spark.global.config";
    public static final String USER_NAME = "username";
    public static final String DOWNLOAD_URL = "download_url";
    public static final String EMAIL_SUFFIX = "@flipkart.com";
    public static final String PROCESS_SUCCESSFUL = "process_successful";
    public static final String DOWNLOAD_READY_SUBJECT = "Download ready";
    public static final String DOWNLOAD_PROCESSING_FAILED_SUBJECT = "Download processing failed";

    public static final Integer DEFAULT_VERSION_LIMIT = 10000;

    public static class DatasetConfigConstants {
        public static final String DATASET_NAME = "datasetName";
        public static final String CLUSTER_NAME = "cluster";
        public static final String COMPANY = "company";
        public static final String ORGANISATION = "organisation";
        public static final String NAMESPACE = "namespace";
        public static final String SCHEMA_VERSION = "schemaVersion";
        public static final String TABLE_NAME = "tableName";
        public static final String VERSION_COLUMN = "versionColumn";
        public static final String DATABASE_NAME = "databaseName";
        public static final String TEAM = "team";
        public static final String NAME = "name";
        public static final String USECASE = "usecase";
        public static final String VERSION = "version";
        public static final String TEMPLATE_ID = "templateId";
        public static final String SPREADSHEET_ID = "spreadSheetId";
        public static final String SPREADSHEET_TITLE = "spreadSheetTitle";
        public static final String SHEET_NAME = "sheetName";
        public static final String DATASTORE = "dataStore";
        public static final String INDEX_PREFIX = "indexPrefix";
        public static final String TYPE = "type";
        public static final String ALIAS = "alias";
        public static final String CREATE_INDEX_CONFIG = "createIndexConfig";
        public static final String COMPRESSION_FORMAT = "compressionFormat";
        public static final String FILE_PATH_FORMAT = "filePathFormat";
        public static final String BUCKET_FORMAT = "bucketFormat";
        public static final String KEY_FORMAT = "keyFormat";
    }


    public static class SellPlanConstants {
        //SellPlanUpload Constants
        public static final String DATA_COPY_AND_META_ANALYSIS_STEP = "DATA_COPY_AND_META_ANALYSIS";
        public static final String AUDIT_SELL_PLAN_STEP = "AUDIT_SELL_PLAN";
        public static final String TRIGGER_SELL_PLAN_WF_STEP = "TRIGGER_SELL_PLAN_WORKFLOWS";

        public static final String UPLOAD_DATASET_VAR = "Upload Dataset";
        public static final String DATA_COPY_OUTPUT_VAR = "DATA_COPY_OUTPUT";
        public static final String ANALYSIS_OUTPUT_VAR = "META_ANALYSIS_OUTPUT";
        public static final String[] DATA_REFRESH_OUTPUTS = {DATA_COPY_OUTPUT_VAR, ANALYSIS_OUTPUT_VAR};

        //SellPlan Constants
        public static final String PARSE_SELL_PLAN_STEP = "PARSE_SELL_PLAN";
        public static final String VALIDATE_SELL_PLAN_STEP = "VALIDATE_SELL_PLAN";
        public static final String INGEST_TO_SINKS_STEP = "INGEST_TO_SINKS";
        public static final String AZKABAN_INGEST_TO_SINKS_FLOW_NAME = "SELL_PLAN_POST_PROCESSING";
        public static final String FILTER_PARAM = "template_id_param";
        public static final String[] DYNAMIC_PARSE_PARAMS = {FILTER_PARAM};
        public static final String SELL_PLAN_ERROR_FILE_NAME = "SELL-PLAN-ERROR-REPORT-%s";
        public static final String SELL_PLAN_UPLOAD_FILE_NAME = "SELL-PLAN-UPLOAD-FILE-%s";
        public static final String SELL_PLAN_ID = "sell-plan-id";
        public static final String SELL_PLAN_FILE_NAME = "SELL-PLAN-FILE-%s";

        public static class ErrorMessageConstants {
            public static String INTERNAL_ERROR_MESSAGE = "Reach out to demand-planning-oncall@flipkart.com for resolution";
            public static String PARSE_FAILED_ERROR_MESSAGE = "Parsing the sell plan from uploaded data failed";
            public static String PROCESSING_FAILED_ERROR_MESSAGE = "Processing of the sell plan failed";
            public static String VALIDATION_FAILED_ERROR_MESSAGE = "Validation for the sell plan failed";
        }
    }

    public static final String DOWNLOAD_LINK = "download_link";
    public static final String DATASET_NAME = "dataset_name";
    public static final String OWNER = "owner";
    public static final String POC = "poc";
    public static final String VERSION = "version";
    public static final String DESCRIPTION = "description";
    public static final String IS_PRIMARY = "is_primary";
}
