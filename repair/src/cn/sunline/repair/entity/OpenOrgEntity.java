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
 * @date 2016-10-21 19:29:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_open_org", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class OpenOrgEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**areaid*/
	private java.lang.String orgName;
	private java.util.Date createTime;
	private java.lang.String orgCode;
	private java.lang.String orgLevel;
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=11,scale=0)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Long
	 *@param: java.lang.Long  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}

	@Column(name ="org_name",nullable=true,length=200)
	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  areaid
	 */
	@Column(name ="create_Time")
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	@Column(name ="org_Code",nullable=true,length=200)
	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name ="org_Level",nullable=true,length=200)
	public java.lang.String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(java.lang.String orgLevel) {
		this.orgLevel = orgLevel;
	}
	
	
}
