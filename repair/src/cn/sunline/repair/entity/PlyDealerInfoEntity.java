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
 * @Description: 1
 * @author zhangdaihao
 * @date 2016-10-24 10:54:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_dealer_maintenance_ply")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class PlyDealerInfoEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**送修代码*/
	private java.lang.String dealerCode;
	/**名称*/
	private java.lang.String dealerName;
	/**车商地址*/
	private java.lang.String dealerAddress;
	/**联系人电话号码*/
	private java.lang.String contactsTel;
	/**创建时间*/
	private java.util.Date crtTm;
	/**创建人代码*/
	private java.lang.String crtCde;
	/**更新时间*/
	private java.util.Date updTm;
	/**更新人代码*/
	private java.lang.String updCde;
	/**车商全称*/
	private java.lang.String dealerAllName;
	/**业务归属地*/
	private java.lang.String businessHome;
	/**车商地址(市)*/
	private java.lang.String city;
	/**主管电话*/
	private java.lang.String directorTel;
	/**主管名称*/
	private java.lang.String directorName;
	/**联系人*/
	private java.lang.String contactsName;
	/** 是否有效（0有效，1失效）*/
	private java.lang.String isvalid;
	/**车商地址(省)*/
	private java.lang.String province;
	/**车商地址(区县)*/
	private java.lang.String area;
	/**机构号*/
	private java.lang.String dptCde;
	/**机构经办人*/
	private java.lang.String agent;
	/**经办人电话*/
	private java.lang.String agentTel;
	/**车商类型（04S店，1一级车商，2二级车商...）*/
	private java.lang.String dealerType;
	/**备注*/
	private java.lang.String note;
	
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
	 *@return: java.lang.String  送修代码
	 */
	@Column(name ="Dealer_Code",nullable=false,length=50)
	public java.lang.String getDealerCode(){
		return this.dealerCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  送修代码
	 */
	public void setDealerCode(java.lang.String dealerCode){
		this.dealerCode = dealerCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="Dealer_Name",nullable=false,length=50)
	public java.lang.String getDealerName(){
		return this.dealerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setDealerName(java.lang.String dealerName){
		this.dealerName = dealerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商地址
	 */
	@Column(name ="Dealer_Address",nullable=false,length=100)
	public java.lang.String getDealerAddress(){
		return this.dealerAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商地址
	 */
	public void setDealerAddress(java.lang.String dealerAddress){
		this.dealerAddress = dealerAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人电话号码
	 */
	@Column(name ="Contacts_Tel",nullable=true,length=50)
	public java.lang.String getContactsTel(){
		return this.contactsTel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人电话号码
	 */
	public void setContactsTel(java.lang.String contactsTel){
		this.contactsTel = contactsTel;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商全称
	 */
	@Column(name ="Dealer_All_Name",nullable=true,length=50)
	public java.lang.String getDealerAllName(){
		return this.dealerAllName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商全称
	 */
	public void setDealerAllName(java.lang.String dealerAllName){
		this.dealerAllName = dealerAllName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务归属地
	 */
	@Column(name ="Business_Home",nullable=true,length=50)
	public java.lang.String getBusinessHome(){
		return this.businessHome;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务归属地
	 */
	public void setBusinessHome(java.lang.String businessHome){
		this.businessHome = businessHome;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商地址(市)
	 */
	@Column(name ="CITY",nullable=true,length=50)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商地址(市)
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主管电话
	 */
	@Column(name ="Director_Tel",nullable=true,length=50)
	public java.lang.String getDirectorTel(){
		return this.directorTel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主管电话
	 */
	public void setDirectorTel(java.lang.String directorTel){
		this.directorTel = directorTel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主管名称
	 */
	@Column(name ="Director_Name",nullable=true,length=50)
	public java.lang.String getDirectorName(){
		return this.directorName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主管名称
	 */
	public void setDirectorName(java.lang.String directorName){
		this.directorName = directorName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="Contacts_Name",nullable=true,length=50)
	public java.lang.String getContactsName(){
		return this.contactsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContactsName(java.lang.String contactsName){
		this.contactsName = contactsName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer   是否有效（0有效，1失效）
	 */
	@Column(name ="ISVALID",nullable=false,length=2)
	public java.lang.String getIsvalid(){
		return this.isvalid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer   是否有效（0有效，1失效）
	 */
	public void setIsvalid(java.lang.String isvalid){
		this.isvalid = isvalid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商地址(省)
	 */
	@Column(name ="PROVINCE",nullable=true,length=50)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商地址(省)
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商地址(区县)
	 */
	@Column(name ="AREA",nullable=true,length=50)
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商地址(区县)
	 */
	public void setArea(java.lang.String area){
		this.area = area;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构号
	 */
	@Column(name ="Dpt_Cde",nullable=true,length=200)
	public java.lang.String getDptCde(){
		return this.dptCde;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构号
	 */
	public void setDptCde(java.lang.String dptCde){
		this.dptCde = dptCde;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构经办人
	 */
	@Column(name ="AGENT",nullable=true,length=50)
	public java.lang.String getAgent(){
		return this.agent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构经办人
	 */
	public void setAgent(java.lang.String agent){
		this.agent = agent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人电话
	 */
	@Column(name ="Agent_Tel",nullable=true,length=50)
	public java.lang.String getAgentTel(){
		return this.agentTel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人电话
	 */
	public void setAgentTel(java.lang.String agentTel){
		this.agentTel = agentTel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车商类型（04S店，1一级车商，2二级车商...）
	 */
	@Column(name ="Dealer_Type",nullable=true,length=2)
	public java.lang.String getDealerType(){
		return this.dealerType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商类型（04S店，1一级车商，2二级车商...）
	 */
	public void setDealerType(java.lang.String dealerType){
		this.dealerType = dealerType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=200)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
}
