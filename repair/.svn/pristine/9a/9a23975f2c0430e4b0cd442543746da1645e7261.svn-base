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
@Table(name = "rp_area", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AreaEntity implements java.io.Serializable {
	/**id*/
	private java.lang.Long id;
	/**areaid*/
	private java.lang.String areaid;
	/**area*/
	private java.lang.String area;
	/**father*/
	private java.lang.String father;
	
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
	 *@return: java.lang.String  areaid
	 */
	@Column(name ="AREAID",nullable=true,length=50)
	public java.lang.String getAreaid(){
		return this.areaid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  areaid
	 */
	public void setAreaid(java.lang.String areaid){
		this.areaid = areaid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  area
	 */
	@Column(name ="AREA",nullable=true,length=60)
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  area
	 */
	public void setArea(java.lang.String area){
		this.area = area;
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
