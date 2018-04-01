

package cn.sunline.repair.entity;

import java.util.Date;

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
 * @Description: 出现车维修管理维修信息
 * @author wuhualin
 * @date 2016-11-22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "RP_MAINTENANCE_REMARK")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MaintenanceRemarkEntity implements java.io.Serializable{
	/**id*/
    private String id;
    /**维修日期*/
    private Date maintenanceTime;
    /**维修信息*/
    private String maintenanceInformation;
    /**mainID*/
    private String mainId;
    /**报案号id*/
    private String reportNoId;
    /**保单号id*/
    private String policyNoId;
    /**图片路径*/
    private String imgagePath;
    /**是否有效*/
    private String isvalId;
    /**更新时间*/
    private Date updTm;
    /**更新人*/
    private String updCode;
    /**创建时间*/
    private Date crtTm;
    /**创建人*/
    private String crtCode;
    
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=200)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 *方法: 取得String
	 *@return: String  维修日期
	 */
	@Column(name ="MAINTENANCE_TIME")
	public Date getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}
	
	/**
	 *方法: 取得String
	 *@return: String  维修信息
	 */
	@Column(name ="MAINTENANCE_INFORMATION")
	public String getMaintenanceInformation() {
		return maintenanceInformation;
	}
	public void setMaintenanceInformation(String maintenanceInformation) {
		this.maintenanceInformation = maintenanceInformation;
	}
	
	/**
	 *方法: 取得String
	 *@return: String  
	 */
	@Column(name ="MAIN_ID")
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	@Column(name ="REPORTNO_ID")
	public String getReportNoId() {
		return reportNoId;
	}
	public void setReportNoId(String reportNoId) {
		this.reportNoId = reportNoId;
	}
	@Column(name ="POLICYNO_ID")
	public String getPolicyNoId() {
		return policyNoId;
	}
	public void setPolicyNoId(String policyNoId) {
		this.policyNoId = policyNoId;
	}
	@Column(name ="IMGAGEPATH")
	public String getImgagePath() {
		return imgagePath;
	}
	public void setImgagePath(String imgagePath) {
		this.imgagePath = imgagePath;
	}
	@Column(name ="ISVALID")
	public String getIsvalId() {
		return isvalId;
	}
	public void setIsvalId(String isvalId) {
		this.isvalId = isvalId;
	}
	@Column(name ="UPD_TM")
	public Date getUpdTm() {
		return updTm;
	}
	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	@Column(name ="UPD_CODE")
	public String getUpdCode() {
		return updCode;
	}
	public void setUpdCode(String updCode) {
		this.updCode = updCode;
	}
	@Column(name ="CRT_TM")
	public Date getCrtTm() {
		return crtTm;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	@Column(name ="CRT_CODE")
	public String getCrtCode() {
		return crtCode;
	}
	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}
}
