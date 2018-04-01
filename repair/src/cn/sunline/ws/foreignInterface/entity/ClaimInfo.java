package cn.sunline.ws.foreignInterface.entity;

public class ClaimInfo {
	

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


	
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getClaimDepartment() {
		return claimDepartment;
	}

	public void setClaimDepartment(String claimDepartment) {
		this.claimDepartment = claimDepartment;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getReporterTelphone() {
		return reporterTelphone;
	}

	public void setReporterTelphone(String reporterTelphone) {
		this.reporterTelphone = reporterTelphone;
	}

	public String getSurveyorName() {
		return surveyorName;
	}

	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}

	public String getSurveyorCode() {
		return surveyorCode;
	}

	public void setSurveyorCode(String surveyorCode) {
		this.surveyorCode = surveyorCode;
	}

	public String getSurveyorTelphone() {
		return surveyorTelphone;
	}

	public void setSurveyorTelphone(String surveyorTelphone) {
		this.surveyorTelphone = surveyorTelphone;
	}

	public String getIsLocalDanger() {
		return isLocalDanger;
	}

	public void setIsLocalDanger(String isLocalDanger) {
		this.isLocalDanger = isLocalDanger;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getLandCode() {
		return landCode;
	}

	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getReportPlace() {
		return reportPlace;
	}

	public void setReportPlace(String reportPlace) {
		this.reportPlace = reportPlace;
	}


}
