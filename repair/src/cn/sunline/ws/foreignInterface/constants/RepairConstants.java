package cn.sunline.ws.foreignInterface.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 接口交互中所用到的 静态常量
 * @return
 * @throws String
 */
public class RepairConstants {

	/** 接口类型定义 **/
	public static final String REPAIR_S1 = "S1"; // 调度提交
	public static final String REPAIR_S2 = "S2"; // 查勘提交
	public static final String REPAIR_S3 = "S3"; // 核损通过告知

	/** 业务类型返回码定义 **/
	public static final String CHECK_FAILED_CODE = "1010"; // 数据校验失败
	public static final String ERROR_REQUEST_TYPE = "1007"; // 错误的请求类型
	public static final String USER_NOT_EXISTS = "1008"; // 用户不存在
	public static final String ERROE_PASSWORD = "1009"; // 密码错误

	public static final String TIME_OUT_CODE = "0002"; // 请求连接超时
	public static final String NETWORK_EXCEPTION_CODE = "0003"; // 网络连接异常
	public static final String SERVICE_EXCEPTION_CODE = "1005"; // webservice异常
	public static final String RESPONSE_ERROR_CODE = "1011"; // 组装返回xml数据失败
	public static final String NO_INFO_ERROR_CODE = "0006 "; // 未查到该保单信息
	public static final String VHL_OTHER_CODE = "0100"; // 其他异常
	
	
	public static final Map<String,String> repairRulesmap = new HashMap<String,String>();
	static{
		repairRulesmap.put("J1","兼用型拖拉机");
		repairRulesmap.put("J2","运输型拖拉机");
		repairRulesmap.put("M0","摩托车");
		repairRulesmap.put("M3"," 侧三轮摩托车");
		
		repairRulesmap.put("90","营业企业-出租");
		repairRulesmap.put("91","营业企业-租赁");
		repairRulesmap.put("92","营业企业-公交");
		repairRulesmap.put("93","营业企业-客运");
		repairRulesmap.put("94","营业企业-其他");
		repairRulesmap.put("95","营业个人-出租");
		repairRulesmap.put("96","营业个人-租赁");
		repairRulesmap.put("97","营业个人-公交");
		repairRulesmap.put("98","营业个人-客运");
		repairRulesmap.put("99","营业个人-其他");
		
	}
	
	public static final String REPAIR_STATUS_1 = "1";///送修成功
	public static final String REPAIR_STATUS_2 = "2";///不需要送修
	public static final String REPAIR_STATUS_3 = "3";///送修是吧
	
	//送修策略
	public static final String REPAIRRULE_1 ="1";//承保优先原则
	public static final String REPAIRRULE_2 ="2";///不送修
	public static final String REPAIRRULE_3 ="3"; //公共资源- 区县匹配
	public static final String REPAIRRULE_8 ="8"; //公共资源- 区县匹配--附近区1
	public static final String REPAIRRULE_9 ="9"; //公共资源- 区县匹配-- 附近区2
	public static final String REPAIRRULE_4 ="4";//公共资源- 市匹配
	public static final String REPAIRRULE_5 ="5";//公共资源- 省匹配
	public static final String REPAIRRULE_6 ="6";//公共资源- 权重系数
	public static final String REPAIRRULE_7 ="7";//公共资源- 其他车
	
	
	public static final String MSGSENDSTATE_1 = "1";///短信发送状态 1 成功 0 默认状态
	public static final String MSGSENDSTATE_0 = "0";///短信发送状态 1 成功 0 默认状态
	
	
	/**
	 * 维修状态
	 */
	public static final String Maintenanceing = "01";//维修中
	public static final String Completemaintenance = "02";//维修完成
}