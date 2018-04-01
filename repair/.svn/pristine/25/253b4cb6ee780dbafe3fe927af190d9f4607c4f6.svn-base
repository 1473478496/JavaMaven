package cn.sunline.ws.foreignInterface.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import cn.sunline.core.util.SpringContextUtil;
import cn.sunline.repair.entity.MaintenanceRemarkEntity;
import cn.sunline.web.system.service.SystemService;
import cn.sunline.ws.foreignInterface.constants.RepairConstants;
import cn.sunline.ws.foreignInterface.dao.IRepairMainDao;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.foreignInterface.utils.CommonXMLUtils;
import cn.sunline.ws.foreignInterface.utils.RepairUtils;

public class RepairValidateService {

	public static RepairValidateService rvservice;

	public static RepairValidateService getInstance() {
		if (rvservice == null) {
			rvservice = new RepairValidateService();
		}
		return rvservice;
	}

	// 校驗報文
	public String validateError(String errorCode, String requestType)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		String errorMessage = "";
		byte[] responeXml = {};
		Map<String, Object> param = new HashMap<String, Object>();
		CommonXMLUtils xmlUtil = CommonXMLUtils.getInstance();
		if (errorCode.equals("1009")) {
			errorMessage = "报文内容为空！";
		} else if (errorCode.equals("1007")) {
			errorMessage = "请求类型错误！";
		} else if (errorCode.equals("1008")) {
			errorMessage = "用户名不存在或错误！";
		} else if (errorCode.equals("1010")) {
			errorMessage = "密码错误！";
		} else if (errorCode.equals("1005")) {
			errorMessage = "webSerive异常！";
		} else if (errorCode.equals("1011")) {
			errorMessage = "组装返回xml数据失败！";
		}
		String templateName = "Error.xml";
		param.put("REQUEST_TYPE", requestType == null ? null : requestType);
		param.put("ERROR_CODE", errorCode);
		param.put("ERROR_MESSAGE", errorMessage);
		responeXml = xmlUtil.getXMLData("", templateName, null, param, "GBK");
		return new String(responeXml);
	}

	// 调度提交返回报文
	public String resopneDispatchXml(RepairMainInfo rpairClaimInfo)
			throws Exception {

		CommonXMLUtils xmlUtil = CommonXMLUtils.getInstance();
		byte[] responeXml = {};
		String requestType = "";
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String repairSerialNo = rpairClaimInfo.getRepairSerialNo();// 派修码
			String frmNo = rpairClaimInfo.getFrmNo();// 车架号
			String reportNo = rpairClaimInfo.getReportNo();// 报案号

			if (repairSerialNo == null || "".equals(repairSerialNo)) {
				repairSerialNo = reportNo + frmNo;
			}
			param.put("REPAIR_SERIAL_NO", repairSerialNo);
			param.put("REPORT_NO", reportNo);
			param.put("FRM_NO", frmNo);
			String templateName = "dispatch_Respone.xml";
			responeXml = xmlUtil.getXMLData("", templateName, null, param,
					"UTF-8");
			return new String(responeXml, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return validateError(RepairConstants.RESPONSE_ERROR_CODE,
					requestType);
		}
	}

	// 查勘提交返回报文
	public String resopneSurveyXml(List<RepairMainInfo> rCInfoList)
			throws Exception {
		CommonXMLUtils xmlUtil = CommonXMLUtils.getInstance();
		byte[] responeXml = {};
		String requestType = "";
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			for (RepairMainInfo rcInfo : rCInfoList) {
				String repairSerialNo = rcInfo.getRepairSerialNo();
				String frmNo = rcInfo.getFrmNo();
				String reportNo = rcInfo.getReportNo();
				if (repairSerialNo == null || "".equals(repairSerialNo)) {
					repairSerialNo = RepairUtils.getRepairSerialNo(frmNo);
					rcInfo.setRepairSerialNo(repairSerialNo);
				}
			}
			param.put("RCInfoList", rCInfoList);
			String templateName = "survey_Respone.xml";
			responeXml = xmlUtil.getXMLData("", templateName, null, param,
					"UTF-8");
			return new String(responeXml, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return validateError(RepairConstants.RESPONSE_ERROR_CODE,
					requestType);
		}
	}

	// 返回维修信息提交
	public String resopneDangerInfoXml(RepairMainInfo rpairClaimInfo,
			List<MaintenanceRemarkEntity> rEmarkList) throws Exception {
		CommonXMLUtils xmlUtil = CommonXMLUtils.getInstance();
		byte[] responeXml = {};
		String requestType = "";
		Map<String, Object> param = new HashMap<String, Object>();
		SystemService service = SpringContextUtil.getBean("systemService");
		IRepairMainDao iRepairMainDao = SpringContextUtil
				.getBean("repairMainDaoImpl");
		String reportNo = rpairClaimInfo.getReportNo();// 报案号
		String repairSql = " From RepairMainInfo  where reportNo=?";
		String markSql = " From MaintenanceRemarkEntity where reportNoId=?";
		rpairClaimInfo = iRepairMainDao.getRCInfo(repairSql,
				new Object[] { reportNo });
		rEmarkList = service.findHql(markSql, new Object[] { reportNo });
		String plateNo = rpairClaimInfo.getPlateNo();// 车牌号
		String frmNo = rpairClaimInfo.getFrmNo();// 车架号
		String engineNo = rpairClaimInfo.getEngineNo();// 发动机号
		String vehicleName = rpairClaimInfo.getVehlcleName();// 车辆品牌名称
		String mainTenanceStatus = rpairClaimInfo.getMaintenanceStatus();// 维修状态
		String repairSerialNo = "";// 派修码
		try {
			if (rpairClaimInfo == null || "".equals(repairSerialNo)) {
				repairSerialNo = reportNo + frmNo;
			}
			param.put("FRM_NO", frmNo);
			param.put("PLATE_NO", plateNo);
			param.put("ENGINE_NO", engineNo);
			param.put("VEHICLE_NAME", vehicleName);
			param.put("MAINTENANCE_STATUS", mainTenanceStatus);
			param.put("reMarkInfo", rEmarkList);
			String templateName = "dangerRepair_Respone.xml";
			responeXml = xmlUtil.getXMLData("", templateName, null, param,
					"UTF-8");
			return new String(responeXml, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return validateError(RepairConstants.RESPONSE_ERROR_CODE,
					requestType);
		}
	}

	public String validateRepairRule(RepairMainInfo mainInfo) {
		Map<String, String> map = RepairConstants.repairRulesmap;
		// 拖拉机、摩托车、营运性质（使用性质） 不送修
		String usage = map.get(mainInfo.getVehicleUsage());
		String style = map.get(mainInfo.getVehicleStyle());
		if (null != usage && !"".equals(usage) && !"null".equals(usage)) {// 使用性質
			return "符合不送修規則,使用性质是:" + map.get(mainInfo.getVehicleUsage());
		}
		if (null != style && !"".equals(style) && !"null".equals(style)) {// 車輛類型
			return "符合不送修規則,车辆类型是:" + map.get(mainInfo.getVehicleStyle());
		}
		return "";
	}

	public String isVerified(RepairMainInfo rpaiMainInfo) {
		// 请求报文信息校验
		if (null == rpaiMainInfo.getFrmNo()
				|| "".equals(rpaiMainInfo.getFrmNo())) {
			return "车架号不能为空";
		}
		if (null == rpaiMainInfo.getVehicleUsage()
				|| "".equals(rpaiMainInfo.getVehicleUsage())) {
			return "车辆使用性质不能为空";
		}
		if (null == rpaiMainInfo.getReportNo()
				|| "".equals(rpaiMainInfo.getReportNo())) {
			return "报案号不能为空";
		}
		// if (null == rpaiMainInfo.getReporterTelphone()
		// || "".equals(rpaiMainInfo.getReporterTelphone())) {
		// return "报案人电话不能为空";
		// } else if (!RepairUtils.ismobile(rpaiMainInfo.getReporterTelphone()))
		// {
		// return "报案人手机号码错误,请重新填写";
		// }
		// if (null == rpaiMainInfo.getSurveyorTelphone()
		// || "".equals(rpaiMainInfo.getSurveyorTelphone())) {
		// return "查勘人员电话不能为空";
		// } else if (!RepairUtils.ismobile(rpaiMainInfo.getReporterTelphone()))
		// {
		// return "查勘人员手机号码错误,请重新填写";
		// }
		return "";

	}

	public String existVerified(RepairMainInfo repairMainInfo) {
		if (null == repairMainInfo.getReportNo()
				|| "".equals(repairMainInfo.getReportNo())) {
			return "报案号不能为空";
		}else if(null == repairMainInfo.getPolicyNo()||"".equals(repairMainInfo.getPolicyNo())){
			return "保单号不能为空";
		}
		return "";
	}

	// requestType = head.getRequestType();
	// if (!RepairConstants.REPAIR_S1.equals(requestType)) {
	// throw new Exception(RepairConstants.ERROR_REQUEST_TYPE
	// + "@错误的请求类型！");
	// }

	// 校验用户名、密码
	// String user = head.getUser();
	// String password = head.getPassword();
	// RepairServiceUser repairServiceUser =
	// repairServiceUserDao.getRepairServiceUser(user);
	// if(repairServiceUser==null){
	// throw new
	// Exception(RepairConstants.USER_NOT_EXISTS+"@"+"用户不存在！");
	// }else if (!password.equals(repairServiceUser.getPassword())){
	//
	// throw new
	// Exception(RepairConstants.ERROE_PASSWORD+"@"+"请求密码错误！");
	// }

}
