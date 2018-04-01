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
@Table(name = "RP_DEALER_CLM_PLY")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ClmPlyDealerEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**理赔车商代码*/
	private java.lang.String clmDealerCode;
	/**承包车商代码*/
	private java.lang.String plyDealerCode;
	
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
	 *@return: java.lang.String  理赔车商代码
	 */
	@Column(name ="clm_dealer_code",nullable=false,length=50)
	public java.lang.String getclmDealerCode(){
		return this.clmDealerCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  理赔车商代码
	 */
	public void setclmDealerCode(java.lang.String clmDealerCode){
		this.clmDealerCode = clmDealerCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承包车商代码
	 */
	@Column(name ="ply_dealer_code",nullable=false,length=50)
	public java.lang.String getplyDealerCode(){
		return this.plyDealerCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车商承包代码
	 */
	public void setplyDealerCode(java.lang.String plyDealerCode){
		this.plyDealerCode = plyDealerCode;
	}

}
