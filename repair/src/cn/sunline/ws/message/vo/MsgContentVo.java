package cn.sunline.ws.message.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RP_MESSAGE_CONTENT")
public class MsgContentVo {
	private String CPkId;
	private String CSendeeType;
	private String CMsgContent;
	private String CIssuDepartment;
	private String CIsValid;
	private String CCrtCde;
	private Date TCrtTm;
	private String CUpdCde;
	private Date TUpdTm;

	public MsgContentVo() {
		super();
	}

	public MsgContentVo(String cPkId, String cSendeeType, String cMsgContent,
			String cIssuDepartment, String cIsValid, String cCrtCde,
			Date tCrtTm, String cUpdCde, Date tUpdTm) {
		super();
		CPkId = cPkId;
		CSendeeType = cSendeeType;
		CMsgContent = cMsgContent;
		CIssuDepartment = cIssuDepartment;
		CIsValid = cIsValid;
		CCrtCde = cCrtCde;
		TCrtTm = tCrtTm;
		CUpdCde = cUpdCde;
		TUpdTm = tUpdTm;
	}

	@Id
	@Column(name = "C_PK_ID", nullable = false, length = 20)
	public String getCPkId() {
		return CPkId;
	}

	public void setCPkId(String cPkId) {
		CPkId = cPkId;
	}

	@Column(name = "C_SENDEE_TYPE", nullable = false, length = 20)
	public String getCSendeeType() {
		return CSendeeType;
	}

	public void setCSendeeType(String cSendeeType) {
		CSendeeType = cSendeeType;
	}

	@Column(name = "C_MSG_CONTENT", nullable = false, length = 500)
	public String getCMsgContent() {
		return CMsgContent;
	}

	public void setCMsgContent(String cMsgContent) {
		CMsgContent = cMsgContent;
	}

	@Column(name = "C_ISSU_DEPARTMENT", nullable = false, length = 30)
	public String getCIssuDepartment() {
		return CIssuDepartment;
	}

	public void setCIssuDepartment(String cIssuDepartment) {
		CIssuDepartment = cIssuDepartment;
	}

	@Column(name = "C_IS_VALID", length = 30)
	public String getCIsValid() {
		return CIsValid;
	}

	public void setCIsValid(String cIsValid) {
		CIsValid = cIsValid;
	}

	@Column(name = "C_CRT_CDE", length = 50)
	public String getCCrtCde() {
		return CCrtCde;
	}

	public void setCCrtCde(String cCrtCde) {
		CCrtCde = cCrtCde;
	}

	@Column(name = "T_CRT_TM", length = 50)
	public Date getTCrtTm() {
		return TCrtTm;
	}

	public void setTCrtTm(Date tCrtTm) {
		TCrtTm = tCrtTm;
	}

	@Column(name = "C_UPD_CDE", length = 50)
	public String getCUpdCde() {
		return CUpdCde;
	}

	public void setCUpdCde(String cUpdCde) {
		CUpdCde = cUpdCde;
	}

	@Column(name = "T_UPD_TM", length = 50)
	public Date getTUpdTm() {
		return TUpdTm;
	}

	public void setTUpdTm(Date tUpdTm) {
		TUpdTm = tUpdTm;
	}

}
