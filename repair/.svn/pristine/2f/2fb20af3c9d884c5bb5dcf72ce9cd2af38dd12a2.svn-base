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
 * @date 2016-10-21 19:29:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "RP_PROVINCE", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ProvinceEntity implements java.io.Serializable {
	/**id*/
	private java.lang.Long id;
	/**provinceid*/
	private java.lang.String provinceid;
	/**province*/
	private java.lang.String province;
	
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  id
	 */
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=11,scale=0)
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Long
	 *@param: java.lang.Long  id
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  provinceid
	 */
	@Column(name ="PROVINCEID",nullable=true,length=6)
	public java.lang.String getProvinceid(){
		return this.provinceid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  provinceid
	 */
	public void setProvinceid(java.lang.String provinceid){
		this.provinceid = provinceid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  province
	 */
	@Column(name ="PROVINCE",nullable=true,length=40)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  province
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
}
