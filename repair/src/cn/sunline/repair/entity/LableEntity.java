package cn.sunline.repair.entity;

import java.beans.Transient;

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
 * @Description: 厂牌信息维护
 * @author zhangdaihao
 * @date 2016-10-13 18:05:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "VHL")
@DynamicUpdate(true)
@DynamicInsert(true)
public class LableEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**id*/
	private java.lang.String id;
	/**车型品牌*/
	private java.lang.String moedlBrand;
	private java.lang.String brandCode; //品牌编码
	/**车型代码*/
	private java.lang.String modelCode;
	/**车型名称*/
	private java.lang.String modelName;
	/**注释*/
	private java.lang.String notes;
	private java.lang.String crtTm;
	private java.lang.String crtCde;
	private java.lang.String updTm;
	private java.lang.String updCde;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车型代码
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="MODELCODE",nullable=false,length=50)
	public java.lang.String getModelCode() {
		return modelCode;
	}

	public void setModelCode(java.lang.String modelCode) {
		this.modelCode = modelCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车型名称
	 */
	@Column(name ="MODELCNAME",nullable=true,length=50)
	public java.lang.String getModelName() {
		return modelName;
	}

	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车型品牌
	 */
	@Column(name ="CARBRAND",nullable=true,length=50)
	public java.lang.String getMoedlBrand() {
		return moedlBrand;
	}

	public void setMoedlBrand(java.lang.String moedlBrand) {
		this.moedlBrand = moedlBrand;
	}
	
	@Column(name ="BRANDCODE")
	public java.lang.String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(java.lang.String brandCode) {
		this.brandCode = brandCode;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注释
	 */
	@Column(name ="REMARK",nullable=true,length=50)
	public java.lang.String getNotes() {
		return notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}
	@Transient
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	@Column(name ="CREATETIME")
	public java.lang.String getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(java.lang.String crtTm) {
		this.crtTm = crtTm;
	}
	@Column(name ="CREATORCODE")
	public java.lang.String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(java.lang.String crtCde) {
		this.crtCde = crtCde;
	}
	@Column(name ="UPDATETIME")
	public java.lang.String getUpdTm() {
		return updTm;
	}

	public void setUpdTm(java.lang.String updTm) {
		this.updTm = updTm;
	}
	@Column(name ="UPDATERCODE")
	public java.lang.String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(java.lang.String updCde) {
		this.updCde = updCde;
	}
	
}
