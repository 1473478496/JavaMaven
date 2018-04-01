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
 * @date 2016-10-21 19:29:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_city", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CityEntity implements java.io.Serializable {
	/**id*/
	private java.lang.Long id;
	/**cityid*/
	private java.lang.String cityid;
	/**city*/
	private java.lang.String city;
	/**father*/
	private java.lang.String father;
	
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
	 *@return: java.lang.String  cityid
	 */
	@Column(name ="CITYID",nullable=true,length=6)
	public java.lang.String getCityid(){
		return this.cityid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cityid
	 */
	public void setCityid(java.lang.String cityid){
		this.cityid = cityid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  city
	 */
	@Column(name ="CITY",nullable=true,length=50)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  city
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  father
	 */
	@Column(name ="FATHER",nullable=true,length=6)
	public java.lang.String getFather(){
		return this.father;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  father
	 */
	public void setFather(java.lang.String father){
		this.father = father;
	}
}
