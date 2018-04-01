package cn.sunline.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 监控中心
 * @author zhangdaihao
 * @date 2016-10-18 10:35:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_monitor_center", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MonitorEntity implements java.io.Serializable {
	/**车辆种类*/
	private java.lang.String modelType;
	/**联系人*/
	private java.lang.String contactsName;
	/**查勘员*/
	private java.lang.String surveyorName;
	/**推荐车商名称*/
	private java.lang.String dealerName;
	/**实际车商名称*/
	private java.lang.String relDealerName;
	/**三者车车牌*/
	private java.lang.String threecarPlate;
	/**三者车车架号*/
	private java.lang.String threecarFrm;
	/**机构号*/
	private java.lang.String dptCde;
	/**id*/
	private java.lang.String id;
	/**派修流水号*/
	private java.lang.String repairSerialNo;
	/**送修流程进行状态（1,送修中 2,送修成功）*/
	private java.lang.Integer repairSts;
	/**开始时间*/
	private java.util.Date strtTm;
	/**结束时间*/
	private java.util.Date endTm;
	/**创建时间*/
	private java.util.Date crtTm;
	/**创建人代码*/
	private java.lang.String crtCde;
	/**更新时间*/
	private java.util.Date updTm;
	/**更新人代码*/
	private java.lang.String updCde;
	/**报案号*/
	private java.lang.String reportNo;
	/**车牌号*/
	private java.lang.String plateNo;

	/**定损员*/
	private java.lang.String assessName;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆种类
	 */
	@Column(name ="MODEL_TYPE",nullable=true,length=50)
	public java.lang.String getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.String modelType) {
		this.modelType = modelType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACTS_NAME",nullable=true,length=50)
	public java.lang.String getContactsName() {
		return contactsName;
	}

	public void setContactsName(java.lang.String contactsName) {
		this.contactsName = contactsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  查勘员
	 */
	@Column(name ="SURVEYOR_NAME",nullable=true,length=50)
	public java.lang.String getSurveyorName() {
		return surveyorName;
	}

	public void setSurveyorName(java.lang.String surveyorName) {
		this.surveyorName = surveyorName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  推荐车商名称
	 */
	@Column(name ="DEALER_NAME",nullable=true,length=50)

	public java.lang.String getDealerName() {
		return dealerName;
	}

	public void setDealerName(java.lang.String dealerName) {
		this.dealerName = dealerName;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  实际车商名称
	 */
	@Column(name ="REL_DEALER_NAME",nullable=true,length=50)
	public java.lang.String getRelDealerName() {
		return relDealerName;
	}

	public void setRelDealerName(java.lang.String relDealerName) {
		this.relDealerName = relDealerName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三者车车牌
	 */
	@Column(name ="THREECAR_PLATE",nullable=true,length=50)
	public java.lang.String getThreecarPlate() {
		return threecarPlate;
	}

	public void setThreecarPlate(java.lang.String threecarPlate) {
		this.threecarPlate = threecarPlate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三者车车架号
	 */
	@Column(name ="THREECAR_FRM",nullable=true,length=50)
	public java.lang.String getThreecarFrm() {
		return threecarFrm;
	}

	public void setThreecarFrm(java.lang.String threecarFrm) {
		this.threecarFrm = threecarFrm;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构号
	 */
	@Column(name ="DPT_CDE",nullable=true,length=50)

	public java.lang.String getDptCde() {
		return dptCde;
	}

	public void setDptCde(java.lang.String dptCde) {
		this.dptCde = dptCde;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=200)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  派修流水号
	 */
	@Column(name ="REPAIR_SERIAL_NO",nullable=false,length=50)
	public java.lang.String getRepairSerialNo() {
		return repairSerialNo;
	}

	public void setRepairSerialNo(java.lang.String repairSerialNo) {
		this.repairSerialNo = repairSerialNo;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  送修流程进行状态（1,送修中 2,送修成功）
	 */
	@Column(name ="REPAIR_STS",nullable=true)
	public java.lang.Integer getRepairSts() {
		return repairSts;
	}

	public void setRepairSts(java.lang.Integer repairSts) {
		this.repairSts = repairSts;
	}

	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="STRT_TM",nullable=true)
	public java.util.Date getStrtTm() {
		return strtTm;
	}

	public void setStrtTm(java.util.Date strtTm) {
		this.strtTm = strtTm;
	}

	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="END_TM",nullable=true)
	public java.util.Date getEndTm() {
		return endTm;
	}

	public void setEndTm(java.util.Date endTm) {
		this.endTm = endTm;
	}

	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CRT_TM",nullable=true)
	public java.util.Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(java.util.Date crtTm) {
		this.crtTm = crtTm;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人代码
	 */
	@Column(name ="CRT_CDE",nullable=true,length=50)
	public java.lang.String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(java.lang.String crtCde) {
		this.crtCde = crtCde;
	}

	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPD_TM",nullable=true)
	public java.util.Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(java.util.Date updTm) {
		this.updTm = updTm;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人代码
	 */
	@Column(name ="UPD_CDE",nullable=true,length=50)
	

	public java.lang.String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(java.lang.String updCde) {
		this.updCde = updCde;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报案号
	 */
	@Column(name ="REPORT_NO",nullable=true,length=50)
	public java.lang.String getReportNo() {
		return reportNo;
	}

	public void setReportNo(java.lang.String reportNo) {
		this.reportNo = reportNo;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车牌号
	 */
	@Column(name ="PLATE_NO",nullable=true,length=50)
	
	public java.lang.String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(java.lang.String plateNo) {
		this.plateNo = plateNo;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  定损员
	 */
	@Column(name ="ASSESS_NAME",nullable=true,length=50)
	public java.lang.String getAssessName() {
		return assessName;
	}

	public void setAssessName(java.lang.String assessName) {
		this.assessName = assessName;
	}
	
	
	
}
