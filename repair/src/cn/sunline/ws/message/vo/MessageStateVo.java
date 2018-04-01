package cn.sunline.ws.message.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RP_MESSAGE_STATE")
public class MessageStateVo {
	private String id;
	private String msgContent; // 短信内容
	private String sendeeName; // 接收人姓名
	private String sendeePhone; // 接收人电话
	private String state; // 发送状态
	private String reportNo;//REPORT_NO;//报案号
	private String frmNo;///FRM_NO;//车架号
	private String repairSerialNo;// REPAIR_SERIAL_NO;//送修流水
	private String msgType;///短信类型，标示是给哪个角色发的
	private String plateNo ; ///车牌号
	private String dptCde; // 机构 ， 理赔机构
	private Integer sendTimes;//发送次数
	private String vehicleType ;//车辆所属类别 三者还是标的
	private String templateType;//送修 1 2 返修
	private Date sendStartTm;//短信发送时间
	private Date sendEndTm ; //接收返回时间
	
	private String crtCde;
	private Date crtTm; // 接收时间
	private String updCde;
	private Date updTm;
	
	@Id
	@Column(name = "ID")
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MSG_CONTENT", nullable = false)
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	@Column(name = "SENDEE_NAME")
	public String getSendeeName() {
		return sendeeName;
	}
	public void setSendeeName(String sendeeName) {
		this.sendeeName = sendeeName;
	}
	
	@Column(name = "SENDEE_PHONE", nullable = false)
	public String getSendeePhone() {
		return sendeePhone;
	}
	public void setSendeePhone(String sendeePhone) {
		this.sendeePhone = sendeePhone;
	}
	
	@Column(name = "STATE" )
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "REPORT_NO" )
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	@Column(name = "FRM_NO" )
	public String getFrmNo() {
		return frmNo;
	}
	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	@Column(name = "REPAIR_SERIAL_NO" )
	public String getRepairSerialNo() {
		return repairSerialNo;
	}
	public void setRepairSerialNo(String repairSerialNo) {
		this.repairSerialNo = repairSerialNo;
	}
	
	@Column(name = "MSG_TYPE" )
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Column(name = "CRT_CDE", length = 50)
	public String getCrtCde() {
		return crtCde;
	}
	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}

	@Column(name = "CRT_TM", length = 50)
	public Date getCrtTm() {
		return crtTm;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	@Column(name = "UPD_CDE", length = 50)
	public String getUpdCde() {
		return updCde;
	}
	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}
	@Column(name = "UPD_TM", length = 50)
	public Date getUpdTm() {
		return updTm;
	}
	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	@Column(name = "PLATE_NO")
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	@Column(name = "DPT_CDE")
	public String getDptCde() {
		return dptCde;
	}

	public void setDptCde(String dptCde) {
		this.dptCde = dptCde;
	}
	@Column(name = "SEND_TIMES")
	public Integer getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	@Column(name = "VEHICLE_TYPE")
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	@Column(name = "TEMPLATE_TYPE")
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	@Column(name = "SEND_STARTTM")
	public Date getSendStartTm() {
		return sendStartTm;
	}
	public void setSendStartTm(Date sendStartTm) {
		this.sendStartTm = sendStartTm;
	}
	@Column(name = "SEND_ENDTM")
	public Date getSendEndTm() {
		return sendEndTm;
	}
	public void setSendEndTm(Date sendEndTm) {
		this.sendEndTm = sendEndTm;
	}


}
