package cn.sunline.ws.foreignInterface.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sunline.ws.foreignInterface.entity.DealearInfo;
import cn.sunline.ws.foreignInterface.entity.Head;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.foreignInterface.exception.RepairException;
import cn.sunline.ws.foreignInterface.service.impl.RepairServiceImpl;

public class RepairUtils {

	private static Logger logger = LoggerFactory.getLogger(RepairServiceImpl.class);
	private static String strResult = null;

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 报价返回异常信息提示处理(不返回java异常代码)
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Exception e) {
		String msg = "";
		String error = "";
		if (e instanceof InvocationTargetException) {
			Exception tarExctption = (Exception) ((InvocationTargetException) e).getTargetException();
			error = tarExctption.getCause() == null ? tarExctption.getMessage() : tarExctption.getCause().getMessage();
		} else {
			error = e.getCause() == null ? e.getMessage() : e.getCause().getMessage();
		}
		if (error == null || "".equals(error) || error.indexOf("@") == -1) {
			logger.error("REPAIR_ERROR", e);
		}
		if (error != null) {
			msg = error;
		}
		if (msg == null || "".equals(msg)) {
			msg = "送修系统异常";
		}
		return msg;
	}

	// 校验手机号
	public static boolean ismobile(String reporterTelphone) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(reporterTelphone);
		b = m.matches();
		return b;
	}

	// 生成送修码(系统时间 + 车架号)
	public static String getRepairSerialNo(String frmNo) {

		String time = getgetCurrentTime();
		String repairSerialNo = time + frmNo;
		return repairSerialNo;
	}

	private static String getgetCurrentTime() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmssSSS");
		return fmt.format(new Date());
	}

	public Head getHeadInfo(String requsetXml, Head head, String selectNodes) throws RepairException {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(requsetXml);
			// 遍历节点
			List<Element> elementList = doc.selectNodes(selectNodes);
			for (int i = 0; i < elementList.size(); i++) {
				Element p = elementList.get(i);
				if (null == head) {
					head = new Head();
				}
				for (Iterator it = p.elementIterator(); it.hasNext();) {
					Element e = (Element) it.next();
					String nodeName = e.getName();// 节点名称
					String text = e.getText(); // 节点内容

					// 组装set方法
					String name = "";
					String[] nameArray = nodeName.toLowerCase().split("_"); //
					for (int j = 0; j < nameArray.length; j++) {
						String nameChild = nameArray[j];
						if (j > 0) {
							nameChild = nameChild.replaceFirst(nameChild.substring(0, 1),
									nameChild.substring(0, 1).toUpperCase());
						}
						name += nameChild;
					}
					name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
					String setMethodName = "set" + name; // 方法名
					Method setMethod = head.getClass().getDeclaredMethod(setMethodName, String.class);
					setMethod.invoke(head, text);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			throw new RepairException("请求报文解析错误");
		}
		return head;
	}

	// 将xml组装成 对象 body部分
	public RepairMainInfo getRpairMainInfo(String requsetXml, RepairMainInfo rpairClaimInfo, String selectNodes)
			throws RepairException {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(requsetXml);
			// 遍历节点
			List<Element> elementList = doc.selectNodes(selectNodes);
			for (int i = 0; i < elementList.size(); i++) {
				Element p = elementList.get(i);
				if (null == rpairClaimInfo) {
					rpairClaimInfo = new RepairMainInfo();
				}
				for (Iterator it = p.elementIterator(); it.hasNext();) {
					Element e = (Element) it.next();
					String nodeName = e.getName();// 节点名称
					String text = e.getText(); // 节点内容

					// 组装set方法
					String name = "";
					String[] nameArray = nodeName.toLowerCase().split("_"); //
					for (int j = 0; j < nameArray.length; j++) {
						String nameChild = nameArray[j];
						if (j > 0) {
							nameChild = nameChild.replaceFirst(nameChild.substring(0, 1),
									nameChild.substring(0, 1).toUpperCase());
						}
						name += nameChild;
					}
					name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
					String setMethodName = "set" + name; // 方法名
					Method setMethod = rpairClaimInfo.getClass().getDeclaredMethod(setMethodName, String.class);
					setMethod.invoke(rpairClaimInfo, text);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RepairException("请求报文解析错误");
		}
		return rpairClaimInfo;
	}

	// 将xml组装成对象集合
	public List<RepairMainInfo> getMainfoList(String requsetXml) throws RepairException {
		Document doc = null;
		List<Map<String, String>> list_Info_List = new ArrayList();
		List<RepairMainInfo> MainfoList = new ArrayList<RepairMainInfo>(); // 对象集合
		try {
			doc = DocumentHelper.parseText(requsetXml);
			Element root = doc.getRootElement();
			List<Element> firstLevelChildElements = root.elements();
			for (Element firstLevelChild : firstLevelChildElements) {
				if ("BODY".equals(firstLevelChild.getName())) { // 第一层 body层
					List<Element> secondLevelChildElements = firstLevelChild.elements();
					for (Element secondLevelChild : secondLevelChildElements) {
						if ("LIST".equals(secondLevelChild.getName())) {// 第二层
																		// List层
							Map<String, String> map = new HashMap<String, String>();
							List<Element> thirdLevelChildElements = secondLevelChild.elements();
							for (Element thirdLevelChild : thirdLevelChildElements) {
								// 第三层 3个信息层BASE_INFO，VEHICLE_INFO，CLAIM_INFO
								map = acqure_Map_Data(map, thirdLevelChild);
							}
							list_Info_List.add(map);
						}
					}
				}
			}
			MainfoList = transportObjectList(list_Info_List);
		} catch (Exception e) {
			throw new RepairException("请求报文解析错误");
		}
		return MainfoList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> acqure_Map_Data(Map<String, String> map, Element element) {

		if ("BASE_INFO".equals(element.getName())) {
			List<Element> thirdLevelChildElements = element.elements();
			for (Element thirdLevelChild : thirdLevelChildElements) {
				map.put(thirdLevelChild.getName(), thirdLevelChild.getText());
			}
		}
		if ("VEHICLE_INFO".equals(element.getName())) {
			List<Element> thirdLevelChildElements = element.elements();
			for (Element thirdLevelChild : thirdLevelChildElements) {
				map.put(thirdLevelChild.getName(), thirdLevelChild.getText());
			}
		}
		if ("CLAIM_INFO".equals(element.getName())) {
			List<Element> thirdLevelChildElements = element.elements();
			for (Element thirdLevelChild : thirdLevelChildElements) {
				map.put(thirdLevelChild.getName(), thirdLevelChild.getText());
			}
		}
		return map;
	}

	// 将 list 转化成 集合对象
	public List<RepairMainInfo> transportObjectList(List<Map<String, String>> list) {
		List<RepairMainInfo> mainList = new ArrayList<RepairMainInfo>();
		for (int i = 0; i < list.size(); i++) {
			RepairMainInfo info = new RepairMainInfo();
			info.setVehicleType(list.get(i).get("VEHICLE_TYPE"));
			info.setRepairSerialNo(list.get(i).get("REPAIR_SERIAL_NO"));
			info.setAssessCode(list.get(i).get("ASSESS_CODE"));
			info.setAssessName(list.get(i).get("ASSESS_NAME"));
			info.setAssessTelphone(list.get(i).get("ASSESS_TELPHONE"));
			info.setPlateNo(list.get(i).get("PLATE_NO"));
			info.setEngineNo(list.get(i).get("ENGINE_NO"));
			info.setFrmNo(list.get(i).get("FRM_NO"));
			info.setVehicleUsage(list.get(i).get("VEHICLE_USAGE"));
			info.setVehicleStyle(list.get(i).get("VEHICLE_STYLE"));
			info.setModelCode(list.get(i).get("MODEL_CODE"));
			info.setModelName(list.get(i).get("MODEL_NAME"));
			info.setVehlcleName(list.get(i).get("VEHICLE_NAME"));
			info.setVehlcleCode(list.get(i).get("VEHICLE_CODE"));
			info.setReportNo(list.get(i).get("REPORT_NO"));
			info.setClaimDepartment(list.get(i).get("CLAIM_DEPARTMENT"));
			info.setReporterName(list.get(i).get("REPORTER_NAME"));
			info.setReporterTelphone(list.get(i).get("REPORTER_TELPHONE"));
			info.setSurveyorName(list.get(i).get("SURVEYOR_NAME"));
			info.setSurveyorCode(list.get(i).get("SURVEYOR_CODE"));
			info.setSurveyorTelphone(list.get(i).get("SURVEYOR_TELPHONE"));
			info.setIsLocalDanger(list.get(i).get("IS_LOCAL_DANGER"));
			info.setProvinceCode(list.get(i).get("PROVINCE_CODE"));
			info.setLandCode(list.get(i).get("LAND_CODE"));
			info.setCountyCode(list.get(i).get("COUNTY_CODE"));
			info.setStreet(list.get(i).get("STREET"));
			info.setReportPlace(list.get(i).get("REPORT_PLACE"));
			mainList.add(info);
		}
		return mainList;
	}

	public List<DealearInfo> getDeInfoList(String requsetXml) throws RepairException {
		Document doc = null;
		List<Map<String, String>> Info_List = new ArrayList();
		List<DealearInfo> deInfoList = new ArrayList<DealearInfo>(); // 对象集合
		try {
			doc = DocumentHelper.parseText(requsetXml);
			Element root = doc.getRootElement();
			List<Element> firstLevelChildElements = root.elements();
			for (Element firstLevelChild : firstLevelChildElements) {
				if ("BODY".equals(firstLevelChild.getName())) { // 第一层 body层
					List<Element> secondLevelChildElements = firstLevelChild.elements();
					for (Element secondLevelChild : secondLevelChildElements) {
						if ("LIST".equals(secondLevelChild.getName())) {// 第二层
																		// //
																		// List层

							Map<String, String> map = new HashMap<String, String>();
							List<Element> thirdLevelChildElements = secondLevelChild.elements();
							for (Element thirdLevelChild : thirdLevelChildElements) {
								// 第三层 3个信息层BASE_INFO，VEHICLE_INFO，CLAIM_INFO
								map = acqure_Map_Data(map, thirdLevelChild);
							}
							Info_List.add(map);
						}
					}
				}
			}
			deInfoList = transportToDeInfoList(Info_List);
		} catch (Exception e) {
			throw new RepairException("请求报文解析错误");
		}
		return deInfoList;
	}

	public List<DealearInfo> transportToDeInfoList(List<Map<String, String>> list) {
		List<DealearInfo> deInfoList = new ArrayList<DealearInfo>();
		for (int i = 0; i < list.size(); i++) {
			DealearInfo info = new DealearInfo();
			info.setPolicyNo(list.get(i).get("POLICY_NO"));
			info.setReportNo(list.get(i).get("REPORT_NO"));
			info.setRepairSerialNo(list.get(i).get("REPAIR_SERIAL_NO"));
			info.setFrmNo(list.get(i).get("FRM_NO"));
			info.setNuclearDamageCode(list.get(i).get("NUCLEAR_DAMAGE_CODE"));
			info.setNuclearDamageName(list.get(i).get("NUCLEAR_DAMAGE_NAME"));
			info.setDealerNo(list.get(i).get("DEALER_NO"));
			info.setDealerName(list.get(i).get("DEALER_NAME"));
			info.setDealerPlace(list.get(i).get("DEALER_PLACE"));
			info.setProvinceCode(list.get(i).get("PROVINCE_CODE"));
			info.setLandCode(list.get(i).get("LAND_CODE"));
			info.setCountyCode(list.get(i).get("COUNTY_CODE"));
			info.setStreet(list.get(i).get("STREET"));
			info.setDealerTelphone(list.get(i).get("DEALER_TELPHONE"));
			info.setDealerPeople(list.get(i).get("DEALER_PEOPLE"));
			info.setCrtTm(new Date());
			deInfoList.add(info);
		}
		return deInfoList;
	}

	/**
	 * 交易类型码处理
	 * 
	 * @return
	 */
	public String errorXML(String msg, String requestType) throws Exception {
		byte[] responeXml = {};
		Map<String, Object> param = new HashMap<String, Object>();
		CommonXMLUtils xmlUtil = CommonXMLUtils.getInstance();
		String errorCode = "";
		String errorMessage = msg;
		if (msg.indexOf("@") != -1) {
			errorCode = msg.split("@")[0];
			errorMessage = msg.split("@")[1];
		}
		String templateName = "Error.xml";

		param.put("REQUEST_TYPE", requestType == null ? null : requestType);
		param.put("ERROR_CODE", errorCode);
		param.put("ERROR_MESSAGE", errorMessage);
		responeXml = xmlUtil.getXMLData("", templateName, null, param, "UTF-8");

		return new String(responeXml, "UTF-8");

	}

	public String getSuccessMsg() {
		String successMsg = "";
		successMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>          "
				+ "<PACKET type=\"RESPONSE\" version=\"1.0\" >      "
				+ "<HEAD>                                           "
				+ "<REQUEST_TYPE>S3</REQUEST_TYPE>                  "
				+ "<RESPONSE_CODE>1</RESPONSE_CODE>                 " + "<ERROR_CODE>0000</ERROR_CODE>             "
				+ "<ERROR_MESSAGE>成功</ERROR_MESSAGE>             " + "</HEAD>                                          "
				+ "</PACKET>  									  ";

		return successMsg;
	}

}
