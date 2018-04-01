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
 * @Description: 车辆品牌信息维护
 * @author zhangdaihao
 * @date 2016-10-13 17:22:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "RP_BRAND_MAINTENANCE")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class BrandEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**(代理机构代码)车商协议号*/
	private java.lang.String  dealerCode ;
	/**（代理机构名称）车商名称*/
	private java.lang.String dealerName;
	/**品牌名称*/
	private java.lang.String brandName;
	/**品牌编码**/
	private java.lang.String brandCode; 
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
	/**权重*/
	private java.lang.String weight;
	/**就近系数*/
	private java.lang.String ratio;
	
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
	 *@return: java.lang.String  (代理机构代码)车商协议号
	 */
	@Column(name ="dealer_Name",nullable=false,length=50)
	public java.lang.String getDealerName(){
		return this.dealerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  (代理机构代码)车商协议号
	 */
	public void setDealerName(java.lang.String dealerName){
		this.dealerName = dealerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  （代理机构名称）车商名称
	 */
	@Column(name ="dealer_Code",nullable=true,length=50)
	public java.lang.String getDealerCode(){
		return this.dealerCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  （代理机构名称）车商daima
	 */
	public void setDealerCode(java.lang.String dealerCode){
		this.dealerCode = dealerCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌bianma
	 */
	@Column(name ="brand_Code",nullable=true,length=50)
	public java.lang.String getBrandCode(){
		return this.brandCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌名称
	 */
	public void setBrandCode(java.lang.String brandCode){
		this.brandCode = brandCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌名称
	 */
	@Column(name ="BRAND_NAME",nullable=true,length=50)
	public java.lang.String getBrandName(){
		return this.brandName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌名称
	 */
	public void setBrandName(java.lang.String brandName){
		this.brandName = brandName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CRT_TM",nullable=true)
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
	@Column(name ="CRT_CDE",nullable=true,length=50)
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
	@Column(name ="UPD_TM",nullable=true)
	public java.util.Date getUpdTm(){
		return this.updTm;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdTm(java.util.Date tupdTm){
		this.updTm = updTm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人代码
	 */
	@Column(name ="UPD_CDE",nullable=true,length=50)
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
	 *@return: java.lang.String  机构号
	 */
	@Column(name ="DPT_CDE",nullable=true,length=200)
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
	
	
	@Column(name ="WEIGHT",nullable=true,length=50)
	public java.lang.String getWeight(){
		return this.weight;
	}
	
	/**
	 *方法：设置java.langString
	 *@param:java.lang.String   权重
	 */
	public void setWeight(java.lang.String weight){
		this.weight = weight;
	}
	@Column(name ="RATIO",nullable=true,length=50)
	public java.lang.String getRatio() {
		return ratio;
	}
	/**
	 *方法：设置java.langString
	 *@param:java.lang.String   就近系数
	 */
	public void setRatio(java.lang.String ratio) {
		this.ratio = ratio;
	}
}
