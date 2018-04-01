package cn.sunline.ws.foreignInterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "RP_PACKET_INFO")
public class RepairpacketInfo {
	private String pkId; //主键
	
	private String frmNo ;// 车架号
	
	private String reprotNo;//报案号
	
	private String requestPacket; //请求报文
	
	private String responePacket;// 返回报文
	
	private String tradeType;// 返回报文
	
	private String  crtCde;
	
	private Date crtTm;
	
	private String  updCde;
	
	private Date updTm;
	
	@Id
	@Column(name = "PK_ID", unique = true, nullable = false, length = 50)
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	public String getpkId() {
		return pkId;
	}
	public void setpkId(String pkId) {
		this.pkId = pkId;
	}
	@Column(name = "FRM_NO", length =50)
	public String getFrmNo() {
		return frmNo;
	}
	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}
	@Column(name = "REPROT_NO", length =50)
	public String getReprotNo() {
		return reprotNo;
	}
	
	public void setReprotNo(String reprotNo) {
		this.reprotNo = reprotNo;
	}
	@Column(name = "REQUEST_PACKET")
	public String getRequestPacket() {
		return requestPacket;
	}
	
	public void setRequestPacket(String requestPacket) {
		this.requestPacket = requestPacket;
	}
	@Column(name = "RESPONE_PACKET")
	public String getResponePacket() {
		return responePacket;
	}
	public void setResponePacket(String responePacket) {
		this.responePacket = responePacket;
	}
	
	@Column(name = "TRADE_TYPE",length = 50)
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "CRT_TM",length = 50)
	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	@Column(name = "UPD_TM",length = 50)
	public Date getUpdTm() {
		return updTm;
	}
	
	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	
	@Column(name = "CRT_CDE",length = 50)
	public String getCrtCde() {
		return crtCde;
	}

	public void setCrtCde(String crtCde) {
		this.crtCde = crtCde;
	}
	@Column(name = "UPD_CDE",length = 50)
	public String getUpdCde() {
		return updCde;
	}

	public void setUpdCde(String updCde) {
		this.updCde = updCde;
	}
	
}
