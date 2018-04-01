package cn.sunline.ws.message.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "RP_MESSAGE_MAIN_INFO")
public class MessageMainInfoVo {

	private String id;//

	private String issuDepartment;// 机构

	private String dealerNo;// 车商代码

	private String plateNo;// 车牌号
	
	private String frmNo; // 车架号
	
	private String reportNo;//报案号
	
	private String repairSerialNo;//REPAIR_SERIAL_NO;//送修流水号

	private String vehicleType; // 车辆所属类型

	private String vehicleUsage;// 使用性质

	private String reporterName;// 报案人名称

	private String reporterTelphone;// 报案人电话（手机号）

	private String reporterType; // 报案人类型 默认C

	private String reporterState; // 报案人发送短信状态

	private Date reporterSendTm; // 报案人发送短信时间

	private Date reporterReceiveTm; // 报案人接收短信时间

	private Integer reporterSendtimes; // 报案人 发送短信次数

	private String surveyorName;// 查勘员名称

	private String surveyorCode;// 查勘员代码

	private String surveyorTelphone;// 查勘员电话（手机号）

	private String surveyorType;// 查勘员类型 默认S

	private String surveyorState; // 查勘员发送短信状态

	private Date surveyorSendTm; // 查勘员发送短信时间

	private Date surveyorReceiveTm; // 查勘员接收短信时间

	private Integer surveyorSendtimes; // 查勘员发送短信次数

	private String dealerName; // 车商名称

	private String dealerTelphone;// 车商电话

	private String dealerPeople;// 车商联系人

	private String dealerType;// 车商类型 默认D
	
	private String dealerState; // 车商发送短信状态

	private Date dealerSendTm; // 车商发送短信时间

	private Date dealerReceiveTm; // 车商接收短信时间

	private Integer dealerSendtimes; // 车商发送短信次数

	private String businessorCode;// 业务员代码

	private String businessorName;// 业务员名称

	private String businessorTelphone;// 业务员电话

	private String businessorType;// 业务员类型 默认B

	private String businessorState; // 业务员发送短信状态

	private Date businessorSendTm; // 业务员发送短信时间

	private Date businessorReceiveTm; // 业务员接收短信时间

	private Integer businessorSendtimes; // 业务员发送短信次数

	private String assessCode;// 定损员代码

	private String assessName;// 定损员名称

	private String assessTelphone;// 定损员电话

	private String assessType;// 定损员类型 默认A

	private String assessState; // 定损员发送短信状态

	private Date assessSendTm; // 定损员发送短信时间

	private Date assessReceiveTm; // 定损员接收短信时间

	private Integer assessSendtimes; // 定损员发送短信次数
	
	///承保时的车商信息
	private String policyDealerNo;// 车商代码
	
	private String policyDealerName; // 车商名称

	private String policyDealerTelphone;// 车商电话

	private String policyDealerPeople;// 车商联系人

	private String policyDealerType;// 车商类型 默认PD

	private String policyDealerState; // 车商发送短信状态

	private Date policyDealerSendTm; // 车商发送短信时间

	private Date policyDealerReceiveTm; // 车商接收短信时间

	private Integer policyDealerSendtimes; // 车商发送短信次数

	private String policyBusinessorCode;// 业务员代码

	private String policyBusinessorName;// 业务员名称

	private String policyBusinessorTelphone;// 业务员电话

	private String policyBusinessorType;// 业务员类型 默认PB

	private String policyBusinessorState; // 业务员发送短信状态

	private Date policyBusinessorSendTm; // 业务员发送短信时间

	private Date policyBusinessorReceiveTm; // 业务员接收短信时间

	private Integer policyBusinessorSendtimes; // 业务员发送短信次数
	
	private String msgState;///短信发送状态

	private String random;

	private String crtCde;

	private Date crtTm;

	private String updCde;

	private Date updTm;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ISSU_DEPARTMENT")
	public String getIssuDepartment() {
		return issuDepartment;
	}

	public void setIssuDepartment(String issuDepartment) {
		this.issuDepartment = issuDepartment;
	}

	@Column(name = "DEALER_NO")
	public String getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}

	@Column(name = "PLATE_NO")
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	
	@Column(name = "FRM_NO")
	public String getFrmNo() {
		return frmNo;
	}

	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	
	@Column(name = "REPORT_NO")
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(name = "REPAIR_SERIAL_NO")
	public String getRepairSerialNo() {
		return repairSerialNo;
	}

	public void setRepairSerialNo(String repairSerialNo) {
		this.repairSerialNo = repairSerialNo;
	}

	@Column(name = "VEHICLE_TYPE")
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Column(name = "VEHICLE_USAGE")
	public String getVehicleUsage() {
		return vehicleUsage;
	}

	public void setVehicleUsage(String vehicleUsage) {
		this.vehicleUsage = vehicleUsage;
	}

	@Column(name = "REPORTER_NAME")
	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	@Column(name = "REPORTER_TELPHONE")
	public String getReporterTelphone() {
		return reporterTelphone;
	}

	public void setReporterTelphone(String reporterTelphone) {
		this.reporterTelphone = reporterTelphone;
	}

	@Column(name = "REPORTER_TYPE")
	public String getReporterType() {
		return reporterType;
	}

	public void setReporterType(String reporterType) {
		this.reporterType = reporterType;
	}

	@Column(name = "REPORTER_STATE")
	public String getReporterState() {
		return reporterState;
	}

	public void setReporterState(String reporterState) {
		this.reporterState = reporterState;
	}

	@Column(name = "REPORTER_SEND_TM")
	public Date getReporterSendTm() {
		return reporterSendTm;
	}

	public void setReporterSendTm(Date reporterSendTm) {
		this.reporterSendTm = reporterSendTm;
	}

	@Column(name = "REPORTER_RECEIVE_TM")
	public Date getReporterReceiveTm() {
		return reporterReceiveTm;
	}

	public void setReporterReceiveTm(Date reporterReceiveTm) {
		this.reporterReceiveTm = reporterReceiveTm;
	}

	@Column(name = "REPORTER_SENDTIMES")
	public Integer getReporterSendtimes() {
		return reporterSendtimes;
	}

	public void setReporterSendtimes(Integer reporterSendtimes) {
		this.reporterSendtimes = reporterSendtimes;
	}

	@Column(name = "SURVEYOR_NAME")
	public String getSurveyorName() {
		return surveyorName;
	}

	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}

	@Column(name = "SURVEYOR_CODE")
	public String getSurveyorCode() {
		return surveyorCode;
	}

	public void setSurveyorCode(String surveyorCode) {
		this.surveyorCode = surveyorCode;
	}

	@Column(name = "SURVEYOR_TELPHONE")
	public String getSurveyorTelphone() {
		return surveyorTelphone;
	}

	public void setSurveyorTelphone(String surveyorTelphone) {
		this.surveyorTelphone = surveyorTelphone;
	}

	@Column(name = "SURVEYOR_TYPE")
	public String getSurveyorType() {
		return surveyorType;
	}

	public void setSurveyorType(String surveyorType) {
		this.surveyorType = surveyorType;
	}

	@Column(name = "SURVEYOR_STATE")
	public String getSurveyorState() {
		return surveyorState;
	}

	public void setSurveyorState(String surveyorState) {
		this.surveyorState = surveyorState;
	}

	@Column(name = "SURVEYOR_SEND_TM")
	public Date getSurveyorSendTm() {
		return surveyorSendTm;
	}

	public void setSurveyorSendTm(Date surveyorSendTm) {
		this.surveyorSendTm = surveyorSendTm;
	}

	@Column(name = "SURVEYOR_RECEIVE_TM")
	public Date getSurveyorReceiveTm() {
		return surveyorReceiveTm;
	}

	public void setSurveyorReceiveTm(Date surveyorReceiveTm) {
		this.surveyorReceiveTm = surveyorReceiveTm;
	}

	@Column(name = "SURVEYOR_SENDTIMES")
	public Integer getSurveyorSendtimes() {
		return surveyorSendtimes;
	}

	public void setSurveyorSendtimes(Integer surveyorSendtimes) {
		this.surveyorSendtimes = surveyorSendtimes;
	}

	@Column(name = "DEALER_NAME")
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Column(name = "DEALER_TELPHONE")
	public String getDealerTelphone() {
		return dealerTelphone;
	}

	public void setDealerTelphone(String dealerTelphone) {
		this.dealerTelphone = dealerTelphone;
	}

	@Column(name = "DEALER_PEOPLE")
	public String getDealerPeople() {
		return dealerPeople;
	}

	public void setDealerPeople(String dealerPeople) {
		this.dealerPeople = dealerPeople;
	}

	@Column(name = "DEALER_TYPE")
	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	@Column(name = "DEALER_STATE")
	public String getDealerState() {
		return dealerState;
	}

	public void setDealerState(String dealerState) {
		this.dealerState = dealerState;
	}

	@Column(name = "DEALER_SEND_TM")
	public Date getDealerSendTm() {
		return dealerSendTm;
	}

	public void setDealerSendTm(Date dealerSendTm) {
		this.dealerSendTm = dealerSendTm;
	}

	@Column(name = "DEALER_RECEIVE_TM")
	public Date getDealerReceiveTm() {
		return dealerReceiveTm;
	}

	public void setDealerReceiveTm(Date dealerReceiveTm) {
		this.dealerReceiveTm = dealerReceiveTm;
	}

	@Column(name = "DEALER_SENDTIMES")
	public Integer getDealerSendtimes() {
		return dealerSendtimes;
	}

	public void setDealerSendtimes(Integer dealerSendtimes) {
		this.dealerSendtimes = dealerSendtimes;
	}

	@Column(name = "BUSINESSOR_CODE")
	public String getBusinessorCode() {
		return businessorCode;
	}

	public void setBusinessorCode(String businessorCode) {
		this.businessorCode = businessorCode;
	}

	@Column(name = "BUSINESSOR_NAME")
	public String getBusinessorName() {
		return businessorName;
	}

	public void setBusinessorName(String businessorName) {
		this.businessorName = businessorName;
	}

	@Column(name = "BUSINESSOR_TELPHONE")
	public String getBusinessorTelphone() {
		return businessorTelphone;
	}

	public void setBusinessorTelphone(String businessorTelphone) {
		this.businessorTelphone = businessorTelphone;
	}

	@Column(name = "BUSINESSOR_TYPE")
	public String getBusinessorType() {
		return businessorType;
	}

	public void setBusinessorType(String businessorType) {
		this.businessorType = businessorType;
	}

	@Column(name = "BUSINESSOR_STATE")
	public String getBusinessorState() {
		return businessorState;
	}

	public void setBusinessorState(String businessorState) {
		this.businessorState = businessorState;
	}

	@Column(name = "BUSINESSOR_SEND_TM")
	public Date getBusinessorSendTm() {
		return businessorSendTm;
	}

	public void setBusinessorSendTm(Date businessorSendTm) {
		this.businessorSendTm = businessorSendTm;
	}

	@Column(name = "BUSINESSOR_RECEIVE_TM")
	public Date getBusinessorReceiveTm() {
		return businessorReceiveTm;
	}

	public void setBusinessorReceiveTm(Date businessorReceiveTm) {
		this.businessorReceiveTm = businessorReceiveTm;
	}

	@Column(name = "BUSINESSOR_SENDTIMES")
	public Integer getBusinessorSendtimes() {
		return businessorSendtimes;
	}

	public void setBusinessorSendtimes(Integer businessorSendtimes) {
		this.businessorSendtimes = businessorSendtimes;
	}

	@Column(name = "ASSESS_CODE")
	public String getAssessCode() {
		return assessCode;
	}

	public void setAssessCode(String assessCode) {
		this.assessCode = assessCode;
	}

	@Column(name = "ASSESS_NAME")
	public String getAssessName() {
		return assessName;
	}

	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}

	@Column(name = "ASSESS_TELPHONE")
	public String getAssessTelphone() {
		return assessTelphone;
	}

	public void setAssessTelphone(String assessTelphone) {
		this.assessTelphone = assessTelphone;
	}

	@Column(name = "ASSESS_TYPE")
	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	@Column(name = "ASSESS_STATE")
	public String getAssessState() {
		return assessState;
	}

	public void setAssessState(String assessState) {
		this.assessState = assessState;
	}

	@Column(name = "ASSESS_SEND_TM")
	public Date getAssessSendTm() {
		return assessSendTm;
	}

	public void setAssessSendTm(Date assessSendTm) {
		this.assessSendTm = assessSendTm;
	}

	@Column(name = "ASSESS_RECEIVE_TM")
	public Date getAssessReceiveTm() {
		return assessReceiveTm;
	}

	public void setAssessReceiveTm(Date assessReceiveTm) {
		this.assessReceiveTm = assessReceiveTm;
	}

	@Column(name = "ASSESS_SENDTIMES")
	public Integer getAssessSendtimes() {
		return assessSendtimes;
	}

	public void setAssessSendtimes(Integer assessSendtimes) {
		this.assessSendtimes = assessSendtimes;
	}
	@Column(name = "POLICY_DEALER_NO")
	public String getPolicyDealerNo() {
		return policyDealerNo;
	}

	public void setPolicyDealerNo(String policyDealerNo) {
		this.policyDealerNo = policyDealerNo;
	}
	@Column(name = "POLICY_DEALER_NAME")
	public String getPolicyDealerName() {
		return policyDealerName;
	}

	public void setPolicyDealerName(String policyDealerName) {
		this.policyDealerName = policyDealerName;
	}
	@Column(name = "POLICY_DEALER_TELPHONE")
	public String getPolicyDealerTelphone() {
		return policyDealerTelphone;
	}

	public void setPolicyDealerTelphone(String policyDealerTelphone) {
		this.policyDealerTelphone = policyDealerTelphone;
	}
	@Column(name = "POLICY_DEALER_PEOPLE")
	public String getPolicyDealerPeople() {
		return policyDealerPeople;
	}

	public void setPolicyDealerPeople(String policyDealerPeople) {
		this.policyDealerPeople = policyDealerPeople;
	}
	@Column(name = "POLICY_DEALER_TYPE")
	public String getPolicyDealerType() {
		return policyDealerType;
	}

	public void setPolicyDealerType(String policyDealerType) {
		this.policyDealerType = policyDealerType;
	}
	@Column(name = "POLICY_DEALER_STATE")
	public String getPolicyDealerState() {
		return policyDealerState;
	}

	public void setPolicyDealerState(String policyDealerState) {
		this.policyDealerState = policyDealerState;
	}
	@Column(name = "POLICY_DEALER_SEND_TM")
	public Date getPolicyDealerSendTm() {
		return policyDealerSendTm;
	}

	public void setPolicyDealerSendTm(Date policyDealerSendTm) {
		this.policyDealerSendTm = policyDealerSendTm;
	}
	@Column(name = "POLICY_DEALER_RECEIVE_TM")
	public Date getPolicyDealerReceiveTm() {
		return policyDealerReceiveTm;
	}

	public void setPolicyDealerReceiveTm(Date policyDealerReceiveTm) {
		this.policyDealerReceiveTm = policyDealerReceiveTm;
	}
	@Column(name = "POLICY_DEALER_SEND_TIMES")
	public Integer getPolicyDealerSendtimes() {
		return policyDealerSendtimes;
	}

	public void setPolicyDealerSendtimes(Integer policyDealerSendtimes) {
		this.policyDealerSendtimes = policyDealerSendtimes;
	}
	@Column(name = "POLICY_BUSINESSOR_CODE")
	public String getPolicyBusinessorCode() {
		return policyBusinessorCode;
	}

	public void setPolicyBusinessorCode(String policyBusinessorCode) {
		this.policyBusinessorCode = policyBusinessorCode;
	}
	@Column(name = "POLICY_BUSINESSOR_NAME")
	public String getPolicyBusinessorName() {
		return policyBusinessorName;
	}

	public void setPolicyBusinessorName(String policyBusinessorName) {
		this.policyBusinessorName = policyBusinessorName;
	}
	@Column(name = "POLICY_BUSINESSOR_TELPHONE")
	public String getPolicyBusinessorTelphone() {
		return policyBusinessorTelphone;
	}

	public void setPolicyBusinessorTelphone(String policyBusinessorTelphone) {
		this.policyBusinessorTelphone = policyBusinessorTelphone;
	}
	@Column(name = "POLICY_BUSINESSOR_TYPE")
	public String getPolicyBusinessorType() {
		return policyBusinessorType;
	}

	public void setPolicyBusinessorType(String policyBusinessorType) {
		this.policyBusinessorType = policyBusinessorType;
	}
	@Column(name = "POLICY_BUSINESSOR_STATE")
	public String getPolicyBusinessorState() {
		return policyBusinessorState;
	}

	public void setPolicyBusinessorState(String policyBusinessorState) {
		this.policyBusinessorState = policyBusinessorState;
	}
	@Column(name = "POLICY_BUSINESSOR_SEND_TM")
	public Date getPolicyBusinessorSendTm() {
		return policyBusinessorSendTm;
	}

	public void setPolicyBusinessorSendTm(Date policyBusinessorSendTm) {
		this.policyBusinessorSendTm = policyBusinessorSendTm;
	}
	@Column(name = "POLICY_BUSINESSOR_RECEIVE_TM")
	public Date getPolicyBusinessorReceiveTm() {
		return policyBusinessorReceiveTm;
	}

	public void setPolicyBusinessorReceiveTm(Date policyBusinessorReceiveTm) {
		this.policyBusinessorReceiveTm = policyBusinessorReceiveTm;
	}
	@Column(name = "POLICY_BUSINESSOR_SENDTIMES")
	public Integer getPolicyBusinessorSendtimes() {
		return policyBusinessorSendtimes;
	}

	public void setPolicyBusinessorSendtimes(Integer policyBusinessorSendtimes) {
		this.policyBusinessorSendtimes = policyBusinessorSendtimes;
	}
	@Column(name = "msg_State")
	public String getMsgState() {
		return msgState;
	}

	public void setMsgState(String msgState) {
		this.msgState = msgState;
	}

	@Column(name = "RANDOM")
	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	@Column(name = "CRT_TM")
	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	@Column(name = "UPD_TM")
	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	@Column(name = "CRT_CDE")
	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	@Column(name = "UPD_CDE")
	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	

	

}
