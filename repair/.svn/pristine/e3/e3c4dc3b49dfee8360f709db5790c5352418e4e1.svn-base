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
 * @Description: 1
 * @author zhangdaihao
 * @date 2016-10-24 13:28:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "RP_MESSAGE_TEMPLATE")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TSSmsTemplateEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String crtName;
	/**创建人代码*/
	private java.lang.String crtCde;
	/**创建时间*/
	private java.util.Date crtTm;
	/**更新人名称*/
	private java.lang.String updName;
	/**更新人代码*/
	private java.lang.String updCde;
	/**更新时间*/
	private java.util.Date updTm;
	/**模板类型*/
	private java.lang.String templateType;///1 送修、2 返修
	/**模板名称*/
	private java.lang.String templateName;
	/**模板内容*/
	private java.lang.String templateContent;
	/***机构*/
	private java.lang.String issuDepartment ; 
	/**人员类别*/
	private java.lang.String sendType;///人员角色
	private java.lang.String orgName;
	private  String vehicleType ; // 车辆所属类型
	private String isvalid;//是否可用
	
	/**
	 *方法: 取得java.lang.String	
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="Crt_Name",nullable=true,length=50)
	public java.lang.String getCrtName(){
		return this.crtName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCrtName(java.lang.String crtName){
		this.crtName = crtName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人代码
	 */
	@Column(name ="Crt_Cde",nullable=true,length=50)
	public java.lang.String getCrtCde(){
		return this.crtCde;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人代码
	 */
	public void setCrtCde(java.lang.String crtCde){
		this.crtCde = crtCde;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="Crt_Tm",nullable=true)
	public java.util.Date getCrtTm(){
		return this.crtTm;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCrtTm(java.util.Date crtTm){
		this.crtTm = crtTm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="Upd_Name",nullable=true,length=50)
	public java.lang.String getUpdName(){
		return this.updName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdName(java.lang.String updName){
		this.updName = updName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人代码
	 */
	@Column(name ="Upd_Cde",nullable=true,length=50)
	public java.lang.String getUpdCde(){
		return this.updCde;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人代码
	 */
	public void setUpdCde(java.lang.String updCde){
		this.updCde = updCde;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="Upd_Tm",nullable=true)
	public java.util.Date getUpdTm(){
		return this.updTm;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdTm(java.util.Date updTm){
		this.updTm = updTm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板类型
	 */
	@Column(name ="Template_Type",nullable=true,length=1)
	public java.lang.String getTemplateType(){
		return this.templateType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板类型
	 */
	public void setTemplateType(java.lang.String templateType){
		this.templateType = templateType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板名称
	 */
	@Column(name ="Template_Name",nullable=true,length=50)
	public java.lang.String getTemplateName(){
		return this.templateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板名称
	 */
	public void setTemplateName(java.lang.String templateName){
		this.templateName = templateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模板内容
	 */
	@Column(name ="Template_Content",nullable=true,length=1000)
	public java.lang.String getTemplateContent(){
		return this.templateContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模板内容
	 */
	public void setTemplateContent(java.lang.String templateContent){
		this.templateContent = templateContent;
	}
	@Column(name ="issuDepartment",nullable=true,length=50)
	public java.lang.String getIssuDepartment() {
		return issuDepartment;
	}

	public void setIssuDepartment(java.lang.String issuDepartment) {
		this.issuDepartment = issuDepartment;
	}
	@Column(name ="send_Type",nullable=true,length=2)
	public java.lang.String getSendType() {
		return sendType;
	}

	public void setSendType(java.lang.String sendType) {
		this.sendType = sendType;
	}

	@Transient
	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "VEHICLE_TYPE", length = 20)
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	@Column(name = "IS_VALID")
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	

}
