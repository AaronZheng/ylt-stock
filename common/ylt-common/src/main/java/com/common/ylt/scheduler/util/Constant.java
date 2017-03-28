package com.common.ylt.scheduler.util;

/**
 * 
 * @description 常量信息
 * 
 * @author zhengchenglei
 *
 */
public class Constant {
	
	/** ES 服务器地址  **/
	public final static String ES_SERVER_IP = "ES_SERVER_IP";
	/** ES http 端口名称 **/
	public final static String HTTP_PORT = "HTTP_PORT";
	/** ES TCP 端口 **/
	public final static String TCP_PORT = "TCP_PORT";
	/** 集群自动关联 **/
	public final static String TRANSPORT= "TRANSPORT";
	/** 最大活动链接数 **/
	public final static String CONN_MAX_ACTIVE = "CONN_MAX_ACTIVE";
	/** 链接最长等待时间  **/
	public final static String CONN_MAX_WAIT = "CONN_MAX_WAIT";
	/** 链接最长空闲数量 **/
	public final static String CONN_MAX_IDLE_NUM = "CONN_MAX_IDLE_NUM";
	/**最大空闲时间**/
	public final static String CONN_MAX_IDLE_TIME = "CONN_MAX_IDLE_TIME";
	/** 频率 **/
	public final static String PERIOD = "PERIOD";
	/** 初始化链接数 **/
	public final static String CONN_INIT_SIZE = "CONN_INIT_SIZE";
	/** 集群名称 **/
	public final static String CLUSTER_NAME = "cluster.name";
	
	public final static String CONN_READ_TIME = "CONN_READ_TIME";
	/** 超时回收 **/
	public final static String TIMEOUT_RECOVERY = "TIMEOUT_RECOVERY";
	/** 承运商时效 **/
	public final static String CARRIER_TM_EFFECT = "carrier_tm_effect";
	/** 承运商编码 **/
	public final static String CARRIER_CODE = "carrier_code";
	/** 承运商名称 **/
	public final static String CARRIER_NAME = "carrier_name";
	/** 行业平均时效 **/
	public final static String BIZ_AVG_TM_EFFECT = "biz_avg_tm_effect";
	/** 上三月路由综合得分  **/
	public final static String PRE_ONE_MONTH_ROUTE_SYN_SCORE = "pre_one_month_route_syn_score";
	/** 上两月路由综合得分  **/
	public final static String PRE_TOW_MONTH_ROUTE_SYN_SCORE = "pre_two_month_route_syn_score";
	/** 上月路由综合得分  **/
	public final static String PRE_THREE_MONTH_ROUTE_SYN_SCORE = "pre_three_month_route_syn_score";
	/** 发货地址编码 **/
	public final static String SEND_AREA_ID = "send_area_id";
	/** 货号地址 **/
	public final static String SEND_AREA_NAME = "send_area_NAME";
	/** 收货地址编码 **/
	public final static String DESC_AREA_ID = "desc_area_id";
	/** 收货地址ID **/
	public final static String DESC_AREA_NAME = "desc_area_name";
	/** 分隔符 **/
	public final static String SPLIT_TAG = "_";
	/** 发货一级地址 **/
	public final static String SEND_PROVINCE_ID = "send_area_first_id";
	public final static String SEND_PROVINCE_NAME = "send_area_first_name";
	/** 发货二级地址 **/
	public final static String SEND_CITY_ID = "send_area_second_id";
	/** 发货二期地址名称 **/
	public final static String SEND_CITY_NAME = "send_area_second_name";
	/** 发货三级地址 **/
	public final static String SEND_COUNTY_ID = "send_area_third_id";
	/** 发货三期地址名称  **/
	public final static String SEND_COUNTY_NAME = "send_area_third_name";
	/** 发货四级地址 **/
	public final static String SEND_TOWN_ID = "send_area_fourth_id";
	/** 发货四级地址名称 **/
	public final static String SEND_TOWN_NAME = "send_area_fourth_name";
	/** 收货一级地址 **/
	public final static String DES_PROVINCE_ID = "desc_area_first_id";
	/** 收货一级地址名称 **/
	public final static String DES_PROVINCE_NAME = "desc_area_first_name";
	/** 收货二级地址 **/
	public final static String DES_CITY_ID = "desc_area_second_id";
	/** 收货二级地址名称 **/
	public final static String DES_CITY_NAME = "desc_area_second_name";
	/** 收货三级地址 **/
	public final static String DES_COUNTY_ID = "desc_area_third_id";
	/** 收货三级地址名称 **/
	public final static String DES_COUNTY_NAME = "desc_area_third_name";
	/** 收货四级地址 **/
	public final static String DES_TOWN_ID = "desc_area_fourth_id";
	/** 收货四级地址名称 **/
	public final static String DES_TOWN_NAME = "desc_area_fourth_name";
	/** 总是 **/
	public final static String TOTAL_SIZE = "total";
	
	public final static String REGX = "\\d{1,3}(.\\d{1,3}){3}\\:\\d{1,5}(,\\d{1,3}(.\\d{1,3}){3}\\:\\d{1,5})*";
	/** 行 **/
	public final static String ROW = "rows";
	
	public final static String CARRIER_TAG_REGX = "ALPHACARRIER_*";
	
	public final static String CARRIER_TAG = "ALPHACARRIER_";
	
	public final static String ADDRESS_TAG = "ALPHAADDRESS_";

	public final static String DEFAULT_DATASOURCE = "default_dataSource";


	//////////////////////////////////////////// ES CONFIGRURATION CONSTANT ///////////////////////////////////////



	public final static String PERFORM_COLL = "performance-collection";
	public final static String CLUSTER_X = "cluster";
	public final static String JIM_URL_X = "jimUrl";
	public final static String ANALYSER_QUEUE_DEEP_X = "analyserQueueDeep";
	public final static String INDEX_X = "index";
	public final static String KEY_X = "key";
	public final static String NAME_X ="name";
	public final static String VALUE_X ="value";
	public final static String IS_COLLECTION_X = "isCollection";
	public final static String TYPE_X = "type";
	public final static String COLLE_TYPE_X = "collectType";
	public final static String ES_CACHE_X  ="es-cache";
	public final static String DATA_SOURCE_CONF_X  ="dataSource-conf";
	public final static String DSNAME_X = "dsname";
	public final static String CACHE_TYPE_X = "cacheType";
	public final static String REFRESH_FREQUENCY_X = "refreshFrequency";
	public final static String EXPIRE_X = "expire";
	public final static String CACHE_STRATEGY_X = "cacheStrategy";
	public final static String CACHE_NUM_X = "cacheNum";
	public final static String IS_DEFAULT_X = "isDefault";
	public final static String SERVER_IP_X = "serverIp";
	public final static String TRANSPORT_X = "transport";
	public final static String CONN_MAX_ACTIVE_X = "connMaxActive";
	public final static String CONN_MAX_WAIT_X = "connMaxWait";
	public final static String CONN_INIT_SIZE_X = "connInitSize";
	public final static String CONN_MAX_IDLE_NUM_X = "connMaxIdleNum";
	public final static String CONN_MAX_IDLE_TIME_X = "connMaxIdleTime";
	public final static String PERIOD_X = "period";
	public final static String NEXT_SIZE_X = "nextSize";
	public final static String IS_ENABLE_PROBE_X = "isEnableProbe";
	public final static String CONN_READ_TIME_X = "connReadTime";
	public final static String IS_DUMP_STACK_X = "isDumpStack";
	public final static String DB_PROBLE_X = "dbProble";
	public final static String TIME_OUT_RECOVERY_X = "timeoutRecovery";
	public final static String DATA_SOURCE_X = "dataSource";
	public final static String ENABLE_X = "enable";


	// obligate word
	public final static String GET_METHOD = "get";
	public final static String SET_METHOD = "set";
	public final static int DEFAULT_PAGE_SEIZE = 5000;

	public final static String JOB_GROUP = "JOB_GROUP";
	public final static String JOB_NAME = "JOB_NAME";
	public final static String CUSTOM_JOB_DATA = "CUSTOM_JOB_DATA";
	public final static String BEAN_NAME = "BEAN_NAME";
	public final static String ALPHA_SCHEDULER_FILE = "alpha-scheduler.properties";
	public final static String SCHEDULER_CONFIG = "/META-INF/resources/alpha-scheduler.properties";
	public final static String ALPHA_SCHEDULER_DAEMON= "alpha_scheduler_daemon";



}
