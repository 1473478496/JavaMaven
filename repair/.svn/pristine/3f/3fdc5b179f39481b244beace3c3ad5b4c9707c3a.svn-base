package cn.sunline.ws.foreignInterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

//报案信息
@Entity
@Table(name = "RP_MAIN_INFO")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RepairMainInfo implements java.io.Serializable{
	
	private String id;//
	
	private String policyNo;//保单号

	private String issuDepartment;//出单机构
	
	private String repairNo;//推荐车商代码
	
	private String policyRepairNo;//承保时车商代码
	
	private String repairSerialNo;//送修码
	
	private  String vehicleType ; // 车辆所属类型
	
	private String assessCode;//定损员代码
	
	private String assessName;//定损员名称
	
	private String assessTelphone;//定损员电话

	private String plateNo;//车牌号
	
	private String engineNo;//发动机号
	
	private String frmNo;//车架号
	
	private String vehicleUsage;//使用性质
	
	private String vehicleStyle;//车辆类型

	private String modelCode;//车型代码
	
	private String modelName;//厂牌车型
	
	private String vehlcleName;//车辆品牌名称setVehlcleName
	
	private String vehlcleCode;//车辆品牌编码
	
	private String reportNo;//报案号

	private String claimDepartment;//理赔机构
	
	private String reporterName;//报案人名称

	private String reporterTelphone;//报案人电话（手机号）
	
	private String surveyorName;//查勘员名称
	
	private String surveyorCode;//查勘员员工代码
	
	private String surveyorTelphone;//查勘员电话（手机号）
	
	private String isLocalDanger;//是否本地出险
	
	private String provinceCode;//出险地点（省代码）
	
	private String landCode;//出险地点（地市代码）

	private String countyCode;//出险地点（区县代码）
	
	private String  street;//出险地点（街道）
	
	private String  reportPlace;//出险地点（省+市+区县+街道的汉语名称）

	private String  crtCde;
	
	private Date crtTm;
	
	private String  updCde;
	
	private Date updTm;
	
	private String repairState;
	
	private String repairRule;
	
	private String dangerTm ; //出险时间
	
	private String reportTm  ; //报案时间
	
	private String maintenanceStatus;//维修状态
	
	private String maintenanceImage;//维修影像
	
	private String maintenanceRemark;//维修备注
	private String dealerNo;//实际车商代码
	private String pkId;
	private Date startTime;
	private Date endTime;
	
	private String repairName;//推荐车商代码名称
	private String dealerName;//实际车商代码名称
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
	
	@Column(name = "POLICY_NO", length = 50)
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	@Column(name = "ISSUDEPARTMENT", length = 50)
	public String getIssuDepartment() {
		return issuDepartment;
	}
	
	public void setIssuDepartment(String issuDepartment) {
		this.issuDepartment = issuDepartment;
	}
	
	@Column(name = "REPAIR_NO", length = 50)
	public String getRepairNo() {
		return repairNo;
	}
	
	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}
	
	@Column(name = "POLICY_REPAIR_NO", length = 50)
	public String getPolicyRepairNo() {
		return policyRepairNo;
	}
	
	public void setPolicyRepairNo(String policyRepairNo) {
		this.policyRepairNo = policyRepairNo;
	}
	
	@Column(name = "REPAIR_SERIAL_NO", length = 50)
	public String getRepairSerialNo() {
		return repairSerialNo;
	}

	public void setRepairSerialNo(String repairSerialNo) {
		this.repairSerialNo = repairSerialNo;
	}
	@Column(name = "VEHICLE_TYPE", length = 20)
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
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
	@Column(name = "PLATE_NO", length = 30)
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	
	@Column(name = "ENGINE_NO", nullable=false,length = 50)
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	@Column(name = "FRM_NO",nullable=false, length = 50)
	public String getFrmNo() {
		return frmNo;
	}

	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	@Column(name = "VEHICLE_USAGE",nullable=false, length = 50)
	public String getVehicleUsage() {
		return vehicleUsage;
	}

	public void setVehicleUsage(String vehicleUsage) {
		this.vehicleUsage = vehicleUsage;
	}
	@Column(name = "VEHICLE_STYLE",nullable=false, length = 50)
	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}
	@Column(name = "MODEL_CODE", length = 50)
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	@Column(name = "MODEL_NAME")
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Column(name = "VEHLCLE_NAME", length = 50)
	public String getVehlcleName() {
		return vehlcleName;
	}

	public void setVehlcleName(String vehlcleName) {
		this.vehlcleName = vehlcleName;
	}
	@Column(name = "VEHICLE_CODE", length = 50)
	public String getVehlcleCode() {
		return vehlcleCode;
	}

	public void setVehlcleCode(String vehlcleCode) {
		this.vehlcleCode = vehlcleCode;
	}
	@Column(name = "REPORT_NO", nullable=false,length = 50)
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	@Column(name = "CLAIM_DEPARTMENT",length = 50)
	public String getClaimDepartment() {
		return claimDepartment;
	}

	public void setClaimDepartment(String claimDepartment) {
		this.claimDepartment = claimDepartment;
	}
	@Column(name = "REPORTER_NAME",nullable=false,length = 50)
	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}
	@Column(name = "REPORTER_TELPHONE",nullable=false,length = 50)
	public String getReporterTelphone() {
		return reporterTelphone;
	}

	public void setReporterTelphone(String reporterTelphone) {
		this.reporterTelphone = reporterTelphone;
	}
	@Column(name = "SURVEYOR_NAME",nullable=false,length = 50)
	public String getSurveyorName() {
		return surveyorName;
	}

	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}
	@Column(name = "SURVEYOR_CODE",nullable=false,length = 50)
	public String getSurveyorCode() {
		return surveyorCode;
	}

	public void setSurveyorCode(String surveyorCode) {
		this.surveyorCode = surveyorCode;
	}
	@Column(name = "SURVEYOR_TELPHONE",nullable=false,length = 50)
	public String getSurveyorTelphone() {
		return surveyorTelphone;
	}

	public void setSurveyorTelphone(String surveyorTelphone) {
		this.surveyorTelphone = surveyorTelphone;
	}
	@Column(name = "IS_LOCAL_DANGER",nullable=false,length = 50)
	public String getIsLocalDanger() {
		return isLocalDanger;
	}

	public void setIsLocalDanger(String isLocalDanger) {
		this.isLocalDanger = isLocalDanger;
	}
	@Column(name = "PROVINCE_CODE",nullable=false,length = 50)
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	@Column(name = "LAND_CODE",nullable=false,length = 50)
	public String getLandCode() {
		return landCode;
	}

	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}
	@Column(name = "COUNTY_CODE",nullable=false,length = 50)
	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	@Column(name = "STREET",nullable=false,length = 50)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	@Column(name = "REPORT_PLACE",nullable=false,length = 50)
	public String getReportPlace() {
		return reportPlace;
	}
	
	public void setReportPlace(String reportPlace) {
		this.reportPlace = reportPlace;
	}

	@Column(name = "CRT_TM",length = 50)
	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	@Column(name = "UPD_TM",length = 50)
	public Date getUpdTm() {
		return updTm;
	}
	
	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	
	@Column(name = "CRT_CDE",length = 50)
	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}
	@Column(name = "UPD_CDE",length = 50)
	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}
	@Column(name = "REPAIR_STATE",length = 2)
	public String getRepairState() {
		return repairState;
	}

	public void setRepairState(String repairState) {
		this.repairState = repairState;
	}
	@Column(name = "REPAIR_RULE")
	public String getRepairRule() {
		return repairRule;
	}

	public void setRepairRule(String repairRule) {
		this.repairRule = repairRule;
	}
	@Column(name = "DANGER_TM")
	public String getDangerTm() {
		return dangerTm;
	}

	public void setDangerTm(String dangerTm) {
		this.dangerTm = dangerTm;
	}
	@Column(name = "REPOR_TM")
	public String getReportTm() {
		return reportTm;
	}

	public void setReportTm(String reportTm) {
		this.reportTm = reportTm;
	}
	@Column(name = "MAINTENANCE_STATUS")
	public String getMaintenanceStatus() {
		return maintenanceStatus;
	}

	public void setMaintenanceStatus(String maintenanceStatus) {
		this.maintenanceStatus = maintenanceStatus;
	}
	@Column(name = "MAINTENANCE_IMAGE")
	public String getMaintenanceImage() {
		return maintenanceImage;
	}

	public void setMaintenanceImage(String maintenanceImage) {
		this.maintenanceImage = maintenanceImage;
	}
	@Column(name = "MAINTENANCE_REMARK")
	public String getMaintenanceRemark() {
		return maintenanceRemark;
	}

	public void setMaintenanceRemark(String maintenanceRemark) {
		this.maintenanceRemark = maintenanceRemark;
	}
	@Column(name = "PK_ID")
	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	@Column(name = "dealer_No")
	public String getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}	
	@Transient
	public String getRepairName() {
		return repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	@Transient
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	@Transient
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Transient
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
}
