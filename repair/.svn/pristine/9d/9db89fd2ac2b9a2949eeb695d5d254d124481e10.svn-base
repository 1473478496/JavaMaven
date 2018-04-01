package cn.sunline.ws.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.entity.MsageEntity;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.ws.foreignInterface.dao.IDealerInfoDao;
import cn.sunline.ws.foreignInterface.dao.IMessageContenDao;
import cn.sunline.ws.foreignInterface.dao.IMessageMainInfoDao;
import cn.sunline.ws.foreignInterface.dao.IMessageStateDao;
import cn.sunline.ws.foreignInterface.dao.IRepairMainDao;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.message.common.MessageConstants;
import cn.sunline.ws.message.service.MessageService;
import cn.sunline.ws.message.service.SmsSendService;
import cn.sunline.ws.message.vo.MessageMainInfoVo;
import cn.sunline.ws.message.vo.MessageStateVo;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	private Logger logger = Logger.getLogger(MessageServiceImpl.class);// 日志对象实例
	@Autowired
	private IMessageContenDao contenDao;
	@Autowired
	private IMessageStateDao stateDao;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private IMessageMainInfoDao mainDao;
	@Autowired
	private IRepairMainDao repairMainDao;
	@Autowired
	private IDealerInfoDao dealerInfoDao;

	// **************************************************************************

	/**
	 * 组装发送短信内容
	 */

	public void getMessageContent(String random) throws Exception {		
		String hql = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.set(Calendar.DATE, cl.get(Calendar.DATE) - 1);
		try {
			// 短信发送状态说明：0 初始化状态 1 成功 2 短信接口返回失败 3 无模板 4、短信接口提示失败且查过设置的发送次数 5 次 
			// 6 程序失败   7 发送内容为空或电话号码不正确  9 策略控制不发送 查勘员与定损员是同一个  8 没配置策略
			hql = "select a FROM MessageMainInfoVo a,OpenOrgEntity b WHERE a.issuDepartment=b.orgCode and  a.updTm >= to_date( ? ,'yyyymmddHH24miss') and a.msgState = '0'  and a.random = ? "; // 抽取10
			List<MessageMainInfoVo> msgMainInfoVoList = mainDao.getMsgMainList(hql, new Object[] { sdf.format(cl.getTime()), random });
 
			for (MessageMainInfoVo msgMainInfoVo : msgMainInfoVoList) {
				try {
					String frmNo = msgMainInfoVo.getFrmNo();
					String reportNo = msgMainInfoVo.getReportNo();
					String repairSerialNo = msgMainInfoVo.getRepairSerialNo();
					String dealerNo = msgMainInfoVo.getDealerNo();// 推荐车商编码
					String plateNo = msgMainInfoVo.getPlateNo();// 车牌号
					String policyDealerNo = msgMainInfoVo.getPolicyDealerNo();// /承保时的车商
					DealerInfoEntity deInfo = new DealerInfoEntity(); // 推荐车商的信息
					DealerInfoEntity PolicyDeInfo =  new DealerInfoEntity(); // 承保时车商的信息
					Map<String, String> telMap = new HashMap<String, String>();///角色的电话信息
					Map<String, String> nameMap = new HashMap<String, String>();///角色的名字信息
					
					String mainHql = " From RepairMainInfo  where  frmNo= ? and reportNo=?";
					RepairMainInfo mainInfo = repairMainDao.getRCInfo(mainHql, new Object[] { frmNo, reportNo });

					// /获取车商新（推荐的 、承保时的）
					String dealerHql = "FROM DealerInfoEntity where dealerCode = ?";
					if (policyDealerNo != null && !"".equals(policyDealerNo) && dealerNo != null && !"".equals(dealerNo)) {
						dealerHql = "FROM DealerInfoEntity where dealerCode = ? or dealerCode = ? ";
						List deInfoList = dealerInfoDao.getDealerInfoEntityList(dealerHql, new Object[] { dealerNo, policyDealerNo });
						if (deInfoList != null && deInfoList.size() > 0) {
							for (Object obj : deInfoList) {
								DealerInfoEntity tmp = (DealerInfoEntity) obj;
								if (dealerNo.equals(tmp.getDealerCode())) {
									deInfo = tmp;
								} 
								if (policyDealerNo != null) {
									PolicyDeInfo = tmp;
								}
							}
						}
					} else {
						if (dealerNo != null && !"".equals(dealerNo)) {
							deInfo = dealerInfoDao.getDealerInfoEntity(dealerHql, new Object[] { dealerNo });
						}
						if (policyDealerNo != null && !"".equals(policyDealerNo)) {
							PolicyDeInfo = dealerInfoDao.getDealerInfoEntity(dealerHql, new Object[] { policyDealerNo });
						}
					}

					String getVehicleType = mainInfo.getVehicleType();// /车辆所类别， // 3者车还是标的车

					String issuDepartment = msgMainInfoVo.getIssuDepartment();// 机构
					if (issuDepartment == null) {
						issuDepartment = "02";
					}
					String reporterName = mainInfo.getReporterName();// 报案人姓名
					String reporterTelphone = mainInfo.getReporterTelphone();// 报案人电话
					String reportTm=mainInfo.getReportTm();//报案时间
					String dangerTm=mainInfo.getDangerTm();//出险时间
					String reportPlace=mainInfo.getReportPlace();//出险地点
					String vehlcleName=mainInfo.getVehlcleName();//车辆类型名称
					
		
					telMap.put(MessageConstants.CUSTOMER , reporterTelphone);
					nameMap.put(MessageConstants.CUSTOMER , reporterName);
					
					String surveyorName = mainInfo.getSurveyorName();// 查勘员姓名
					String surveyorTelphone = mainInfo.getSurveyorTelphone();// 查勘员电话
					
					telMap.put(MessageConstants.SURVEYOR , surveyorTelphone);
					nameMap.put(MessageConstants.SURVEYOR , surveyorName);
					
					String dealerName = deInfo.getDealerName(); // 车商名称
					String dealerPeople = deInfo.getContactsName();// 车商联系人
					String dealerTelphone = deInfo.getContactsTel();// 车商联系电话
					String dealerAddress = deInfo.getDealerAddress();// 车商地址
					
					telMap.put(MessageConstants.DEALER , dealerTelphone);
					nameMap.put(MessageConstants.DEALER , dealerPeople);
					
					String directorName=deInfo.getDirectorName();//车商售后经理
					String directorTel=deInfo.getDirectorTel();//车商售后经理电话
					telMap.put(MessageConstants.DIRECTOR , directorTel);
					nameMap.put(MessageConstants.DIRECTOR , directorName);
					
					String usherName=deInfo.getUsher();//车商理赔引导专员
					String userPhone=deInfo.getUsherPhone();//车商理赔引导专员电话
					telMap.put(MessageConstants.USHER , userPhone);
					nameMap.put(MessageConstants.USHER , usherName);
					
					String businessorName = deInfo.getAgent();// 保险公司车商维护专员名称
					String businessorTelphone = deInfo.getAgentTel();// 保险公司车商维护专员电话
					telMap.put(MessageConstants.BUSSINESSOR , businessorTelphone);
					nameMap.put(MessageConstants.BUSSINESSOR , businessorName);
					
					String assessName = mainInfo.getAssessName();// 定损员名称
					String assessTelphone = mainInfo.getAssessTelphone();// 定损员电话
					telMap.put(MessageConstants.ASSESS , assessTelphone);
					nameMap.put(MessageConstants.ASSESS , assessName);
					
					String other1 = deInfo.getOther1();//备用1
					String otherPhone1 = deInfo.getOtherPhone1();//备用手机1
					telMap.put(MessageConstants.OTHER_1 , otherPhone1);
					nameMap.put(MessageConstants.OTHER_1 , other1);
					
					String other2 = deInfo.getOther2();
					String otherPhone2 = deInfo.getOtherPhone2();
					telMap.put(MessageConstants.OTHER_2 , otherPhone2);
					nameMap.put(MessageConstants.OTHER_2 , other2);
					
					String other3 = deInfo.getOther3();
					String otherPhone3 = deInfo.getOtherPhone3();
					telMap.put(MessageConstants.OTHER_3 , otherPhone3);
					nameMap.put(MessageConstants.OTHER_3 , other3);
					
					String other4 = deInfo.getOther4();
					String otherPhone4 = deInfo.getOtherPhone4();
					telMap.put(MessageConstants.OTHER_4 , otherPhone4);
					nameMap.put(MessageConstants.OTHER_4 , other4);
					
					
					String policyDealerName = PolicyDeInfo.getDealerName(); // 承保时车商名称
					String policyDealerPeople = PolicyDeInfo.getContactsName();// 车商联系人
					String policyDealerTelphone = PolicyDeInfo.getContactsTel();// 车商联系电话
					
					String policyBusinessorName = PolicyDeInfo.getAgent();// 承保时车商 业务员名称
					String policyBusinessorTelphone = PolicyDeInfo.getAgentTel();// 业务员电话
					
					Map<String, String> msgMap = new HashMap<String, String>();
					msgMap.put("{plateNo}", plateNo);//车牌号

					msgMap.put("{vehlcleName}", vehlcleName);//车辆类别
					msgMap.put("{dealerAddress}", dealerAddress);//车行地址
					msgMap.put("{dealerName}", dealerName);//车商名称
					msgMap.put("{dealerContact}", dealerPeople);//车商联系人姓名
					msgMap.put("{contactTel}", dealerTelphone);//车商联系人电话
					
					msgMap.put("{directorName}", directorName);//车商售后经理
					msgMap.put("{directorTel}", directorTel);//车商售后经理电话
					
					
					msgMap.put("{usherName}", usherName);//车商理赔引导专员
					msgMap.put("{usherTel}", userPhone);//车商理赔引导专员电话
					
					msgMap.put("{salesmanName}", businessorName);//保险公司车商维护专员姓名
					msgMap.put("{salesmanPhone}", businessorTelphone);//保险公司车商维护专员手机
					
					msgMap.put("{surveyorName}", surveyorName);//查勘员
					msgMap.put("{surveyorTel}", surveyorTelphone);//查勘员I电话

					msgMap.put("{reportNo}", reportNo);//报案号
					msgMap.put("{reportName}", reporterName);//报案人
					msgMap.put("{reportTel}", reporterTelphone);//报案人电话
					msgMap.put("{reportTime}", reportTm);//报案时间
					msgMap.put("{dangerTime}", dangerTm);//出险时间
					msgMap.put("{reportPlace}", reportPlace);//出险地点
					
					
					msgMap.put("{assessName}", assessName);//定损员
					msgMap.put("{assessTelphone}", assessTelphone);//定损员电话
					
					msgMap.put("{other1}", other1);//备用1
					msgMap.put("{otherPhone1}", otherPhone1);//备用手机1
					msgMap.put("{other2}", other2);//备用2
					msgMap.put("{otherPhone2}", otherPhone2);//备用手机2
					msgMap.put("{other3}", other3);//备用3
					msgMap.put("{otherPhone3}", otherPhone3);//备用手机3
					msgMap.put("{other4}", other4);//备用4
					msgMap.put("{otherPhone4}", otherPhone4);//备用手机4
					
					
					msgMap.put("{directorName}", directorName);//车商售后经理
					msgMap.put("{directorTel}", directorTel);//车商售后经理电话
					
					
					msgMap.put("{usherName}", usherName);//车商理赔引导专员
					msgMap.put("{usherTel}", userPhone);//车商理赔引导专员电话
		
					
					if((surveyorName == null || "".equals(surveyorName)) && (surveyorTelphone == null || "".equals(surveyorTelphone))){
						//如果查勘员或定损员的电话号码活姓名为空了， 则给理赔替代人员发送短信
						msgMap.put("{surveyorName}", deInfo.getOther4());
						msgMap.put("{surveyorTel}", deInfo.getOtherPhone4());
					}
					
					if((assessName == null || "".equals(assessName)) && (assessTelphone == null || "".equals(assessTelphone))){
						//如果查勘员或定损员的电话号码活姓名为空了， 则给理赔替代人员发送短信
						msgMap.put("{assessName}", deInfo.getOther4());
						msgMap.put("{assessTelphone}", deInfo.getOtherPhone4());
					}

					// 获取短信发送策略MsageEntity msageRules
					MsageEntity msageRules = new MsageEntity();
					MultiKeyMap rulesMap = new MultiKeyMap();// /多键key 的MAP 策略的map
					String msageRulesHql = "from MsageEntity where dealerNo = ? and  isvalid ='1'";
					List ruleList = stateDao.msgRulesInfo(msageRulesHql, new Object[] { msgMainInfoVo.getDealerNo() });
					if (ruleList != null && ruleList.size() > 0) {
//						msageRules = (MsageEntity) ruleList.get(0);
						for(Object obj : ruleList){
							msageRules = (MsageEntity)obj;
							rulesMap.put(msageRules.getType(), MessageConstants.ASSESS , msageRules.getAssessFlag());	
							rulesMap.put(msageRules.getType(), MessageConstants.BUSSINESSOR , msageRules.getAgentFlag());//业务员即保险公司车商维护专员
							rulesMap.put(msageRules.getType(), MessageConstants.CUSTOMER , msageRules.getCustomerFlag());
							rulesMap.put(msageRules.getType(), MessageConstants.DEALER , msageRules.getContactsFlag());
							rulesMap.put(msageRules.getType(), MessageConstants.SURVEYOR , msageRules.getSurveyorFlag());
							rulesMap.put(msageRules.getType(), MessageConstants.DIRECTOR , msageRules.getDirectorFlag());//车商售后经理
							rulesMap.put(msageRules.getType(), MessageConstants.USHER , msageRules.getUsherFlag());//车商理赔专员
							rulesMap.put(msageRules.getType(), MessageConstants.OTHER_1 , msageRules.getOther1Flag());
							rulesMap.put(msageRules.getType(), MessageConstants.OTHER_2 , msageRules.getOther2Flag());
							rulesMap.put(msageRules.getType(), MessageConstants.OTHER_3 , msageRules.getOther3Flag());
							rulesMap.put(msageRules.getType(), MessageConstants.OTHER_4 , msageRules.getOther4Flag());
						}
					} 
//					else {
//						// 根据车商未查到策略则根据机构查询
//						String dpt = "";
//						for (int i = 2; i <= issuDepartment.length(); i = i + 2) {
//							dpt += "'" + issuDepartment.substring(0, i) + "',";
//						}
//						dpt = dpt + "''";
//						msageRulesHql = "from MsageEntity where dptCde in (" + dpt + ") and  isvalid ='1' order by dptCde desc";
//						ruleList = stateDao.msgRulesInfo(msageRulesHql, null);
//					}

					// 获取模板
					MultiKeyMap TemplateMap = new MultiKeyMap();// /多键key 的MAP
					
					if ((dealerNo == null || "".equals(dealerNo)) && (policyDealerNo == null || "".equals(policyDealerNo)) ){
						
					}else{
						List<TSSmsTemplateEntity> msgTemplateList = this.findMsgTempListByArgs(issuDepartment);
						String dptCde = null; // 记录有模板的机构
						if (msgTemplateList == null || msgTemplateList.size() < 1) {
							// 没找到模板
							return;
						} else {
							for (TSSmsTemplateEntity tmp : msgTemplateList) {
								if (dptCde == null) {
									dptCde = tmp.getIssuDepartment();
								} else {
									if (!dptCde.equals(tmp.getIssuDepartment())) {// /标示机构号是dptCde的模板已循环完毕，停止循环,f防止被上级机构的模板覆盖
										break;
									}
								}
								// /模板类型 ，车辆所属类型 ，发送的角色，模板内容
								TemplateMap.put(tmp.getTemplateType(), tmp.getVehicleType(), tmp.getSendType(), tmp.getTemplateContent());
							}
						}
					}
					
					if (dealerNo == null || "".equals(dealerNo)) {// 送修的未匹配到车商的模板
						TemplateMap.put("1", "00", MessageConstants.ASSESS, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");
						TemplateMap.put("1", "01", MessageConstants.ASSESS, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");
						TemplateMap.put("1", null, MessageConstants.ASSESS, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");

						TemplateMap.put("1", "00", MessageConstants.SURVEYOR, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");
						TemplateMap.put("1", "01", MessageConstants.SURVEYOR, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");
						TemplateMap.put("1", null, MessageConstants.SURVEYOR, "车牌号：{plateNo}的车辆未获取到车商，请跟进！");
					}

					boolean msgStateFlag = false; // 默认无记录
//					Map<String, MessageStateVo> msgStateMap = new HashMap<String, MessageStateVo>();
					MultiKeyMap msgStateMap = new MultiKeyMap();// /多键key 的MAP
					Set<String> resultSet = new HashSet<String>();
					// 发送短信
					for(int i= 1 ; i < 3 ; i ++){ ///1 送修  ，2 返修 ，所以只循环两次
						String msgStatehql = "from MessageStateVo where reportNo = ? and frmNo = ? ";
						List msgStateList = stateDao.msgStateInfoList(msgStatehql, new Object[] { reportNo, frmNo });
						if (msgStateList != null && msgStateList.size() > 0) {
							msgStateFlag = true;
							for (Object obj : msgStateList) {
								MessageStateVo tmp = (MessageStateVo) obj;
								msgStateMap.put(tmp.getTemplateType() , tmp.getMsgType(), tmp);
							}
						}
						if(2 == i && (( policyDealerNo == null || "".equals(policyDealerNo)|| PolicyDeInfo.getId() == null))){//有承保时的车商代码，才是返修的
							break ; 
						}
						if(2 == i ){//返修
							telMap.put(MessageConstants.DEALER , policyDealerTelphone);
							nameMap.put(MessageConstants.DEALER , policyDealerPeople);
							telMap.put(MessageConstants.BUSSINESSOR , policyBusinessorTelphone);
							nameMap.put(MessageConstants.BUSSINESSOR , policyBusinessorName);
							
							msgMap.put("{dealerName}", policyDealerName);
							msgMap.put("{dealerContact}", policyDealerPeople);
							msgMap.put("{contactTel}", policyDealerTelphone);
							
							msgMap.put("{salesmanName}", policyBusinessorName);
							msgMap.put("{salesmanPhone}", policyBusinessorTelphone);
							
							if((surveyorName == null || "".equals(surveyorName)) && (surveyorTelphone == null || "".equals(surveyorTelphone))){
								//如果查勘员或定损员的电话号码活姓名为空了， 则给理赔替代人员发送短信
								msgMap.put("{surveyorName}", PolicyDeInfo.getOther4());
								msgMap.put("{surveyorTel}", PolicyDeInfo.getOtherPhone4());
							}
							
							if((assessName == null || "".equals(assessName)) && (assessTelphone == null || "".equals(assessTelphone))){
								//如果查勘员或定损员的电话号码活姓名为空了， 则给理赔替代人员发送短信
								msgMap.put("{assessName}", PolicyDeInfo.getOther4());
								msgMap.put("{assessTelphone}", PolicyDeInfo.getOtherPhone4());
							}
							
						}
						//第一遍进入循环，判断承包车商代码是否为空，如果不为空则为返修，跳过本次循环进入下次循环
						if(1==i){
							if(PolicyDeInfo.getId() != null&&!"".equals(PolicyDeInfo.getId())){
								continue;
							}
						}
						for (String role : MessageConstants.MSGROLE) {// 根据定义好的角色来发送短信
							// 1、 获取短信模板
							// 2、组装模板
							// 3、 判断策略
							// 4、发送
							// 5、更新状态
							// /模板类型 ，车辆所属类型 ，发送的角色，模板内容
							Object obj = TemplateMap.get(i + "" , mainInfo.getVehicleType(), role);
							String templateContent = null;
							String contect = null;
							String sendResult = null ; 
							Date sendStartTm = null ; 
							if (obj != null && !"".equals(obj.toString())) {// /有模板内容
								templateContent = obj.toString();
								if (templateContent != null && !"".equals(templateContent)) {
									StringBuffer sb = new StringBuffer(templateContent);
									String msgReplace = sb.toString();
									for (Map.Entry<String, String> entry : msgMap.entrySet()) {
										if (null == entry.getValue()) {
											msgReplace = msgReplace.replace(entry.getKey(), "");
										} else {
											msgReplace = msgReplace.replace(entry.getKey(),entry.getValue());
										}
									}

									contect = msgReplace.toString();
								}
							}else{
								sendResult = "3" ;///无模板
							}
							
							Object ruleObj = rulesMap.get(i + "", role);
							String rulesFlag = null ; 
							
							if(ruleObj != null && !"".equals(ruleObj.toString())){
								rulesFlag = ruleObj.toString();
							}else{
								///没配置策略的默认处理
							}
							
							if(surveyorName != null && surveyorName.equals(assessName) && surveyorTelphone!= null && surveyorTelphone.equals(assessTelphone)){
								//查勘员和定损员是同一个人只给一人发
								///两个角色的策略都配置允许你发送则个查勘员发送，否则配置谁允许发就给谁发
								Object assessObj = rulesMap.get(i + "", MessageConstants.ASSESS );
								Object surveyorObj = rulesMap.get(i + "", MessageConstants.SURVEYOR );
								String assessRule = "";
								String surveyorRule = "";
								if(assessObj != null ){
									assessRule = assessObj.toString();
								}
								if(surveyorObj != null ){
									surveyorRule = surveyorObj.toString();
								}
								if("1".equals(surveyorRule) && "1".equals(assessRule)){
									if(role.equals(MessageConstants.ASSESS)){
										sendResult = "9";
									}
								}
							}
							
							//发过的则不在发送
							if (msgStateFlag && msgStateMap.get(i + "" ,role) != null) {
								MessageStateVo msgState = (MessageStateVo)msgStateMap.get(i + "" ,role) ;
								
								if("9".equals(msgState.getState())){
									sendResult = "9" ;
								}
								
								if("1".equals(msgState.getState()) && msgState.getSendeeName() != null && msgState.getSendeeName().equals(nameMap.get(role)) 
										&& msgState.getSendeePhone() != null && msgState.getSendeePhone().equals(telMap.get(role))){///姓名和电话相同则不在发送
									sendResult = "1" ;
								}
							}
							
							sendStartTm = new Date();
							// 发送短信 策略配置发送，有模板
							if("1".equals(rulesFlag) && !"3".equals(sendResult) && !"9".equals(sendResult) && !"1".equals(sendResult)){
								if(contect != null && !"".equals(contect) && telMap.get(role) != null && !"".equals(telMap.get(role)) && telMap.get(role).length() == 11 ){
									
									sendResult = sendSMS(telMap.get(role), contect);
									if("0".equals(sendResult)){
										sendResult="99";
									}
									if("1".equals(sendResult)){
										sendResult = "2" ;//如果短信平台返回错误则状态标识为 2
									}									
								}else{
									sendResult = "7";//发送内容为空活电话号码错误
								}								
							}else{
								if(!"1".equals(rulesFlag)){
									sendResult = "8"; //没配置策略
								}
								if("9".equals(sendResult)){//9 标示重复发送， 实际已发过 ，状态不更改
									sendResult = "9" ;
								}
							}
							if (msgStateFlag && msgStateMap.get(i + "" , role) != null) {// 有记录则更新
								if("1".equals(sendResult)){//以前发送过的不在更新状态
									continue;
								}
								MessageStateVo msgState = (MessageStateVo)msgStateMap.get(i + "" , role) ;
								msgState.setMsgContent(contect);
								msgState.setSendeeName(nameMap.get(role));
								msgState.setSendeePhone(telMap.get(role));
								if(sendResult.equals("99")){									
									msgState.setState("1");//1表示成功
								}else{
									msgState.setState(sendResult);
								}
								msgState.setFrmNo(frmNo);
								msgState.setRepairSerialNo(repairSerialNo);
								msgState.setReportNo(reportNo);
								msgState.setMsgType(role);
								msgState.setUpdTm(new Date());
								msgState.setDptCde(msgMainInfoVo.getIssuDepartment());
								msgState.setPlateNo(plateNo);
								if(msgState.getSendTimes() == null){
									msgState.setSendTimes(0);									
								}else{
									msgState.setSendTimes(msgState.getSendTimes() + 1);///次数 + 1	
								}
								msgState.setSendStartTm(sendStartTm);
								msgState.setSendEndTm(new Date());
								msgState.setTemplateType(i + "");
								msgState.setVehicleType(mainInfo.getVehicleType());
								
								if(msgState.getSendTimes() > 4){
									sendResult = "4";
									msgState.setState("4");
									///次数大于 5 次 则不在发送
								}
								
								stateDao.updateMsgStateInfo(msgState);
							} else {
								MessageStateVo msgState = new MessageStateVo();
								msgState.setMsgContent(contect);
								msgState.setSendeeName(nameMap.get(role));
								msgState.setSendeePhone(telMap.get(role));
								if(sendResult.equals("99")){									
									msgState.setState("1");//1表示成功
								}else{
								msgState.setState(sendResult);
								}
								msgState.setFrmNo(frmNo);
								msgState.setRepairSerialNo(repairSerialNo);
								msgState.setReportNo(reportNo);
								msgState.setMsgType(role);
								msgState.setCrtTm(new Date());
								msgState.setUpdTm(new Date());
								msgState.setDptCde(msgMainInfoVo.getIssuDepartment());
								msgState.setPlateNo(plateNo);
								msgState.setSendTimes(0);
								msgState.setSendStartTm(sendStartTm);
								msgState.setSendEndTm(new Date());
								msgState.setTemplateType(i + "");
								msgState.setVehicleType(mainInfo.getVehicleType());
								stateDao.addMsgState(msgState);
							}
							
							resultSet.add(sendResult);
						}
					}
					
					if(resultSet.contains("2")){//短信平台返回失败，可以重新发送
						msgMainInfoVo.setMsgState("0");						
					}else{
						msgMainInfoVo.setMsgState("1");	
					}
					mainDao.updateMsmInfo(msgMainInfoVo);
				} catch (Exception e) {
					// 非成功的状态设置状态为失败，等待下次发送 , 2 标示 短信接口返回的失败，6 标示程序问题导致的失败
					msgMainInfoVo.setMsgState("6");
					mainDao.updateMsmInfo(msgMainInfoVo);

					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信接口
	 * 
	 * @param phoneCode
	 * @param contect
	 */
	@SuppressWarnings("unchecked")
	public String sendSMS(String phoneCode, String contect) {

		// 必填数据组装
		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String> ruturnMap = new HashMap<String, String>();
		String Reflag = "";
		try {
			paramMap.put("phoneNumber", phoneCode);// 接收人号码
			paramMap.put("content", contect.toString());// 发送文本
			ruturnMap = smsSendService.smsSend(paramMap);
			Reflag = ruturnMap.get("flag");// /1 失败， 0成功
		} catch (Exception e) {
			logger.info("短信平台返回结果:" + ruturnMap);
			return e.getMessage();
		}
		return Reflag;
	}

	// 查出对应的短信模板
	public TSSmsTemplateEntity findMsgTempByArgs(String issuDepartment,
			String sendType) throws Exception {
		TSSmsTemplateEntity msgTemplate = null;
		try {
			issuDepartment = issuDepartment.substring(0, 2);
			String hql = "FROM TSSmsTemplateEntity WHERE issuDepartment=? and sendType = ? ";
			Object[] parameter = { issuDepartment, sendType };
			List<TSSmsTemplateEntity> msgContentVoList = contenDao
					.getMsgContent(hql, parameter);
			if (msgContentVoList != null && msgContentVoList.size() > 0) {
				msgTemplate = msgContentVoList.get(0);
			} else {
				if ("02".equals(issuDepartment)) {
					// throw new Exception("没有对应的短信模板");
					logger.info("机构" + issuDepartment + ":没有对应的短信模板" + sendType);
				} else {
					msgTemplate = findMsgTempByArgs("02", sendType);// 02 总公司
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return msgTemplate;
	}

	// 获取本级机构及其上级的机构 模板
	public List<TSSmsTemplateEntity> findMsgTempListByArgs(String issuDepartment) {
		List<TSSmsTemplateEntity> list = null;
		try {
			String dpt = "";
			for (int i = 2; i <= issuDepartment.length(); i = i + 2) {
				dpt += "'" + issuDepartment.substring(0, i) + "',";
			}
			dpt = dpt + "''";

			String hql = "FROM TSSmsTemplateEntity WHERE issuDepartment in ("
					+ dpt + ") and isvalid ='1' order by issuDepartment desc ";
			List<TSSmsTemplateEntity> msgContentVoList = contenDao
					.getMsgContent(hql, null);
			if (msgContentVoList != null && msgContentVoList.size() > 0) {
				return msgContentVoList;
			} else {
				logger.info("机构" + issuDepartment + ":没有对应的短信模板");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return list;
	}
}
