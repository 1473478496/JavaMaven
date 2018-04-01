package cn.sunline.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 发送短信角色维护
 * @author zhangdaihao
 * @date 2016-10-14 09:43:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "RP_MSAGE_MAINTENANCE")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MsageEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**车商*/
	private java.lang.String dealerFlag;
	/**查勘员*/
	private java.lang.String surveyorFlag;
	/**定损员*/
	private java.lang.String assessFlag;
	/**客户*/
	private java.lang.String customerFlag;
	/**车商联系人*/
	private java.lang.String contactsFlag;
	/**车商售后经理*/
	private java.lang.String directorFlag;
	
	/**车商理赔引导专员*/
	private java.lang.String usherFlag;
	/**保险公司车商维护专员*/
	private java.lang.String agentFlag;
	
	private java.lang.String policyDealerFlag;
	
	private java.lang.String policyDirectorFlag;
	
	private String type;//送修、返修
	/**创建时间*/
	private java.util.Date crtTm;
	/**创建人代码*/
	private java.lang.String crtCde;
	/**更新时间*/
	private java.util.Date updTm;
	/**更新人代码*/
	private java.lang.String updCde;
	/**机构号*/
	private java.lang.String dptCde;
	/**是否有效**/
	private String isvalid;
	
	private String dptName ;
	
	private String dealerNo ; //车商代码
	private String orgName;
	private java.lang.String dealerName;
	

	private java.lang.String other1Flag;
	private java.lang.String other2Flag;
	private java.lang.String other3Flag;
	private java.lang.String other4Flag;
	private String state ; 
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=200)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商名称
	 */
	@Column(name ="DEALER_FLAG",nullable=false)
	public java.lang.String getDealerFlag() {
		return dealerFlag;
	}

	public void setDealerFlag(java.lang.String dealerFlag) {
		this.dealerFlag = dealerFlag;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  查勘员
	 */
	@Column(name ="SURVEYOR_FLAG")
	public java.lang.String getSurveyorFlag() {
		return surveyorFlag;
	}

	public void setSurveyorFlag(java.lang.String surveyorFlag) {
		this.surveyorFlag = surveyorFlag;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  定损员
	 */
	@Column(name ="ASSESS_FLAG")
	public java.lang.String getAssessFlag() {
		return assessFlag;
	}

	public void setAssessFlag(java.lang.String assessFlag) {
		this.assessFlag = assessFlag;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */
	@Column(name ="CUSTOMER_FLAG")
	public java.lang.String getCustomerFlag() {
		return customerFlag;
	}

	public void setCustomerFlag(java.lang.String customerFlag) {
		this.customerFlag = customerFlag;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  4S店联系人
	 */
	@Column(name ="CONTACTS_FLAG")
	public java.lang.String getContactsFlag() {
		return contactsFlag;
	}

	public void setContactsFlag(java.lang.String contactsFlag) {
		this.contactsFlag = contactsFlag;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  4S店经纪人
	 */
	@Column(name ="DIRECTOR_FLAG")
	public java.lang.String getDirectorFlag() {
		return directorFlag;
	}

	public void setDirectorFlag(java.lang.String directorFlag) {
		this.directorFlag = directorFlag;
	}

	public java.lang.String getPolicyDealerFlag() {
		return policyDealerFlag;
	}

	public void setPolicyDealerFlag(java.lang.String policyDealerFlag) {
		this.policyDealerFlag = policyDealerFlag;
	}

	public java.lang.String getPolicyDirectorFlag() {
		return policyDirectorFlag;
	}

	public void setPolicyDirectorFlag(java.lang.String policyDirectorFlag) {
		this.policyDirectorFlag = policyDirectorFlag;
	}
	@Column(name ="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CRT_TM")
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
	@Column(name ="CRT_CDE")
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
	@Column(name ="UPD_TM")

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
	@Column(name ="UPD_CDE")
	public java.lang.String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(java.lang.String updCde) {
		this.updCde = updCde;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构号
	 */
	@Column(name ="DPT_CDE")
	public java.lang.String getDptCde() {
		return dptCde;
	}

	public void setDptCde(java.lang.String dptCde) {
		this.dptCde = dptCde;
	}

	@Column(name ="IS_VALID")
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	@Column(name ="DPT_NAME")
	public String getDptName() {
		return dptName;
	}

	public void setDptName(String dptName) {
		this.dptName = dptName;
	}
	@Column(name ="DEALER_NO")
	public String getDealerNo() {
		return dealerNo;
	}

	public void setDealerNo(String dealerNo) {
		this.dealerNo = dealerNo;
	}
	@Transient
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name ="DEALERNAME")
	public java.lang.String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Column(name ="OTHER_FLAG1")
	public java.lang.String getOther1Flag() {
		return other1Flag;
	}

	public void setOther1Flag(java.lang.String other1Flag) {
		this.other1Flag = other1Flag;
	}
	@Column(name ="OTHER_FLAG2")
	public java.lang.String getOther2Flag() {
		return other2Flag;
	}
	
	public void setOther2Flag(java.lang.String other2Flag) {
		this.other2Flag = other2Flag;
	}
	
	@Column(name ="OTHER_FLAG3")
	
	public java.lang.String getOther3Flag() {
		return other3Flag;
	}
	
	public void setOther3Flag(java.lang.String other3Flag) {
		this.other3Flag = other3Flag;
	}
	
	@Column(name ="OTHER_FLAG4")
	
	public java.lang.String getOther4Flag() {
		return other4Flag;
	}
	
	public void setOther4Flag(java.lang.String other4Flag) {
		this.other4Flag = other4Flag;
	}
	

	@Transient
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name ="USHER_FLAG")
	public java.lang.String getUsherFlag() {
		return usherFlag;
	}

	public void setUsherFlag(java.lang.String usherFlag) {
		this.usherFlag = usherFlag;
	}
	@Column(name ="AGENT_FLAG")
	public java.lang.String getAgentFlag() {
		return agentFlag;
	}

	public void setAgentFlag(java.lang.String agentFlag) {
		this.agentFlag = agentFlag;
	}	
	
}
