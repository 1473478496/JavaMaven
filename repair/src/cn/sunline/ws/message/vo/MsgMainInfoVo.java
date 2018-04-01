package cn.sunline.ws.message.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "RP_MSG_MAIN_INFO")
public class MsgMainInfoVo {

	private String id;//

	private String issuDepartment;// 出单机构

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
	
	private String random;

	private String crtCde;

	private Date crtTm;

	private String updCde;

	private Date updTm;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ISSU_DEPARTMENT", length = 50)
	public String getIssuDepartment() {
		return issuDepartment;
	}

	public void setIssuDepartment(String issuDepartment) {
		this.issuDepartment = issuDepartment;
	}

	@Column(name = "DEALER_NO", length = 50)
	public String getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}

	@Column(name = "PLATE_NO", length = 50)
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	
	@Column(name = "FRM_NO", length = 50)
	public String getFrmNo() {
		return frmNo;
	}

	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	
	@Column(name = "REPORT_NO", length = 50)
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

	@Column(name = "VEHICLE_TYPE", length = 50)
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Column(name = "VEHICLE_USAGE", length = 50)
	public String getVehicleUsage() {
		return vehicleUsage;
	}

	public void setVehicleUsage(String vehicleUsage) {
		this.vehicleUsage = vehicleUsage;
	}

	@Column(name = "REPORTER_NAME", length = 50)
	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	@Column(name = "REPORTER_TELPHONE", length = 50)
	public String getReporterTelphone() {
		return reporterTelphone;
	}

	public void setReporterTelphone(String reporterTelphone) {
		this.reporterTelphone = reporterTelphone;
	}

	@Column(name = "REPORTER_TYPE", length = 50)
	public String getReporterType() {
		return reporterType;
	}

	public void setReporterType(String reporterType) {
		this.reporterType = reporterType;
	}

	@Column(name = "REPORTER_STATE", length = 50)
	public String getReporterState() {
		return reporterState;
	}

	public void setReporterState(String reporterState) {
		this.reporterState = reporterState;
	}

	@Column(name = "REPORTER_SEND_TM", length = 50)
	public Date getReporterSendTm() {
		return reporterSendTm;
	}

	public void setReporterSendTm(Date reporterSendTm) {
		this.reporterSendTm = reporterSendTm;
	}

	@Column(name = "REPORTER_RECEIVE_TM", length = 50)
	public Date getReporterReceiveTm() {
		return reporterReceiveTm;
	}

	public void setReporterReceiveTm(Date reporterReceiveTm) {
		this.reporterReceiveTm = reporterReceiveTm;
	}

	@Column(name = "REPORTER_SENDTIMES", length = 50)
	public Integer getReporterSendtimes() {
		return reporterSendtimes;
	}

	public void setReporterSendtimes(Integer reporterSendtimes) {
		this.reporterSendtimes = reporterSendtimes;
	}

	@Column(name = "SURVEYOR_NAME", length = 50)
	public String getSurveyorName() {
		return surveyorName;
	}

	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}

	@Column(name = "SURVEYOR_CODE", length = 50)
	public String getSurveyorCode() {
		return surveyorCode;
	}

	public void setSurveyorCode(String surveyorCode) {
		this.surveyorCode = surveyorCode;
	}

	@Column(name = "SURVEYOR_TELPHONE", length = 50)
	public String getSurveyorTelphone() {
		return surveyorTelphone;
	}

	public void setSurveyorTelphone(String surveyorTelphone) {
		this.surveyorTelphone = surveyorTelphone;
	}

	@Column(name = "SURVEYOR_TYPE", length = 50)
	public String getSurveyorType() {
		return surveyorType;
	}

	public void setSurveyorType(String surveyorType) {
		this.surveyorType = surveyorType;
	}

	@Column(name = "SURVEYOR_STATE", length = 50)
	public String getSurveyorState() {
		return surveyorState;
	}

	public void setSurveyorState(String surveyorState) {
		this.surveyorState = surveyorState;
	}

	@Column(name = "SURVEYOR_SEND_TM", length = 50)
	public Date getSurveyorSendTm() {
		return surveyorSendTm;
	}

	public void setSurveyorSendTm(Date surveyorSendTm) {
		this.surveyorSendTm = surveyorSendTm;
	}

	@Column(name = "SURVEYOR_RECEIVE_TM", length = 50)
	public Date getSurveyorReceiveTm() {
		return surveyorReceiveTm;
	}

	public void setSurveyorReceiveTm(Date surveyorReceiveTm) {
		this.surveyorReceiveTm = surveyorReceiveTm;
	}

	@Column(name = "SURVEYOR_SENDTIMES", length = 50)
	public Integer getSurveyorSendtimes() {
		return surveyorSendtimes;
	}

	public void setSurveyorSendtimes(Integer surveyorSendtimes) {
		this.surveyorSendtimes = surveyorSendtimes;
	}

	@Column(name = "DEALER_NAME", length = 50)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Column(name = "DEALER_TELPHONE", length = 50)
	public String getDealerTelphone() {
		return dealerTelphone;
	}

	public void setDealerTelphone(String dealerTelphone) {
		this.dealerTelphone = dealerTelphone;
	}

	@Column(name = "DEALER_PEOPLE", length = 50)
	public String getDealerPeople() {
		return dealerPeople;
	}

	public void setDealerPeople(String dealerPeople) {
		this.dealerPeople = dealerPeople;
	}

	@Column(name = "DEALER_TYPE", length = 50)
	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	@Column(name = "DEALER_STATE", length = 50)
	public String getDealerState() {
		return dealerState;
	}

	public void setDealerState(String dealerState) {
		this.dealerState = dealerState;
	}

	@Column(name = "DEALER_SEND_TM", length = 50)
	public Date getDealerSendTm() {
		return dealerSendTm;
	}

	public void setDealerSendTm(Date dealerSendTm) {
		this.dealerSendTm = dealerSendTm;
	}

	@Column(name = "DEALER_RECEIVE_TM", length = 50)
	public Date getDealerReceiveTm() {
		return dealerReceiveTm;
	}

	public void setDealerReceiveTm(Date dealerReceiveTm) {
		this.dealerReceiveTm = dealerReceiveTm;
	}

	@Column(name = "DEALER_SENDTIMES", length = 50)
	public Integer getDealerSendtimes() {
		return dealerSendtimes;
	}

	public void setDealerSendtimes(Integer dealerSendtimes) {
		this.dealerSendtimes = dealerSendtimes;
	}

	@Column(name = "BUSINESSOR_CODE", length = 50)
	public String getBusinessorCode() {
		return businessorCode;
	}

	public void setBusinessorCode(String businessorCode) {
		this.businessorCode = businessorCode;
	}

	@Column(name = "BUSINESSOR_NAME", length = 50)
	public String getBusinessorName() {
		return businessorName;
	}

	public void setBusinessorName(String businessorName) {
		this.businessorName = businessorName;
	}

	@Column(name = "BUSINESSOR_TELPHONE", length = 50)
	public String getBusinessorTelphone() {
		return businessorTelphone;
	}

	public void setBusinessorTelphone(String businessorTelphone) {
		this.businessorTelphone = businessorTelphone;
	}

	@Column(name = "BUSINESSOR_TYPE", length = 50)
	public String getBusinessorType() {
		return businessorType;
	}

	public void setBusinessorType(String businessorType) {
		this.businessorType = businessorType;
	}

	@Column(name = "BUSINESSOR_STATE", length = 50)
	public String getBusinessorState() {
		return businessorState;
	}

	public void setBusinessorState(String businessorState) {
		this.businessorState = businessorState;
	}

	@Column(name = "BUSINESSOR_SEND_TM", length = 50)
	public Date getBusinessorSendTm() {
		return businessorSendTm;
	}

	public void setBusinessorSendTm(Date businessorSendTm) {
		this.businessorSendTm = businessorSendTm;
	}

	@Column(name = "BUSINESSOR_RECEIVE_TM", length = 50)
	public Date getBusinessorReceiveTm() {
		return businessorReceiveTm;
	}

	public void setBusinessorReceiveTm(Date businessorReceiveTm) {
		this.businessorReceiveTm = businessorReceiveTm;
	}

	@Column(name = "BUSINESSOR_SENDTIMES", length = 50)
	public Integer getBusinessorSendtimes() {
		return businessorSendtimes;
	}

	public void setBusinessorSendtimes(Integer businessorSendtimes) {
		this.businessorSendtimes = businessorSendtimes;
	}

	@Column(name = "ASSESS_CODE", length = 50)
	public String getAssessCode() {
		return assessCode;
	}

	public void setAssessCode(String assessCode) {
		this.assessCode = assessCode;
	}

	@Column(name = "ASSESS_NAME", length = 50)
	public String getAssessName() {
		return assessName;
	}

	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}

	@Column(name = "ASSESS_TELPHONE", length = 50)
	public String getAssessTelphone() {
		return assessTelphone;
	}

	public void setAssessTelphone(String assessTelphone) {
		this.assessTelphone = assessTelphone;
	}

	@Column(name = "ASSESS_TYPE", length = 50)
	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	@Column(name = "ASSESS_STATE", length = 50)
	public String getAssessState() {
		return assessState;
	}

	public void setAssessState(String assessState) {
		this.assessState = assessState;
	}

	@Column(name = "ASSESS_SEND_TM", length = 50)
	public Date getAssessSendTm() {
		return assessSendTm;
	}

	public void setAssessSendTm(Date assessSendTm) {
		this.assessSendTm = assessSendTm;
	}

	@Column(name = "ASSESS_RECEIVE_TM", length = 50)
	public Date getAssessReceiveTm() {
		return assessReceiveTm;
	}

	public void setAssessReceiveTm(Date assessReceiveTm) {
		this.assessReceiveTm = assessReceiveTm;
	}

	@Column(name = "ASSESS_SENDTIMES", length = 50)
	public Integer getAssessSendtimes() {
		return assessSendtimes;
	}

	public void setAssessSendtimes(Integer assessSendtimes) {
		this.assessSendtimes = assessSendtimes;
	}
	
	@Column(name = "RANDOM", length = 50)
	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	@Column(name = "CRT_TM", length = 50)
	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	@Column(name = "UPD_TM", length = 50)
	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	@Column(name = "CRT_CDE", length = 50)
	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	@Column(name = "UPD_CDE", length = 50)
	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}

	

	

}
