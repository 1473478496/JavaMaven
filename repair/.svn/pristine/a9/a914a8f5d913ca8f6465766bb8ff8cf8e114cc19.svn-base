package cn.sunline.ws.message.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessageConstants {
	// 短信接收人类型定义
	public static final String CUSTOMER = "C"; // 客户（报案人）
	public static final String SURVEYOR = "S"; // 查勘员
	public static final String DEALER = "D"; // 车商
	public static final String ASSESS = "A"; // 定损员
	public static final String BUSSINESSOR = "B"; // 业务员
	
	//public static final String POLICYDEALER = "PD"; // 承保时车商
	//public static final String POLICYBUSSINESSOR = "PB"; // 承保时车商的业务员
	
	public static final String OTHER_4 ="OTHER_4"; //理赔替代人员 查勘员或定损员电话是空时给此人发信息
	
	public static final String OTHER_1 ="OTHER_1"; //其他1 
	public static final String OTHER_2 ="OTHER_2"; //其他2 
	public static final String OTHER_3 ="OTHER_3"; //其他3 
	
	public static final String DIRECTOR ="DI"; //车商售后经理
	public static final String USHER ="U"; //车商理赔引导专员 
	
	public static final String MSG_INIT="0";//短信初始化状态
	public static final String MSG_SUCCESS="1";//1 成功
	public static final String MSG_RETURN_FAILURE="2";//2短信接口返回失败
	public static final String MSG_NO_TEMPLATE="3";//3无模板
	public static final String MSG_FAILURE_TIMES="4";//4短信接口提示失败且查过设置的发送次数5次
	public static final String MSG_FREQUENCY="5";//
	public static final String MSG_PROGRAM_FAILURE="6";//6程序失败
	public static final String MSG_CONTENT_IS_EMPTY="7";//7发送内容为空或电话号码不正确 
	public static final String MSG_NO_STRATEGY="8";//8无策略
	public static final String MSG_NOT_SEND="9";//9 策略控制不发送 查勘员与定损员是同一个
	public static final Set<String> MSGROLE = new HashSet<String>();
	public static final Map<String, String> map=new HashMap<String, String>();
	static{
		MSGROLE.add(CUSTOMER);
		MSGROLE.add(SURVEYOR);
		MSGROLE.add(DEALER);
		MSGROLE.add(ASSESS);
		MSGROLE.add(BUSSINESSOR);
		//MSGROLE.add(POLICYDEALER);
		//MSGROLE.add(POLICYBUSSINESSOR);
		MSGROLE.add(OTHER_1);
		MSGROLE.add(OTHER_2);
		MSGROLE.add(OTHER_3);
		MSGROLE.add(OTHER_4);
		MSGROLE.add(DIRECTOR);
		MSGROLE.add(USHER);
		map.put(MSG_INIT, "未发送");
		map.put(MSG_SUCCESS, " 成功");
		map.put(MSG_RETURN_FAILURE, "短信接口返回失败");
		map.put(MSG_NO_TEMPLATE, "未配置模板");
		map.put(MSG_FAILURE_TIMES, "短信接口提示失败且查过设置的发送次数5次");
		map.put(MSG_FREQUENCY, "次");
		map.put(MSG_PROGRAM_FAILURE, "程序异常");
		map.put(MSG_CONTENT_IS_EMPTY, "短信内容为空或电话号码不正确 ");
		map.put(MSG_NO_STRATEGY, "未配置策略");
		map.put(MSG_NOT_SEND, "策略控制不发送");
	}
}
