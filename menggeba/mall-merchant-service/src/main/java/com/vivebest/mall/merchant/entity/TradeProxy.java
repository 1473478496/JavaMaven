package com.vivebest.mall.merchant.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;
/**
 * 代理人 
 * @author admin
 *
 */
@Entity
@Table(name="gbm_trade_proxy")
@SequenceGenerator(name="sequenceGenerator",sequenceName="trade_proxy_sequence")
public class TradeProxy extends BaseEntity{

	private static final long serialVersionUID = 2551949296923872799L;

	/**
	 * 证件类型
	 * 
	 * @author Administrator
	 *
	 */
	public enum CertTypes{
	
		identityCard, 	/** 身份证 */
		passport, /** 护照 */
		militaryID /** 军官证 */
	}	
	/**商户id */
	private Trade trade;
	
	/**代理人姓名 */
	private String dname;
	/**联系人方式 */
	private String mobilet;
	/**证件类型  */
	private CertTypes cert_types;
	/**证件号 */
	private String cert_not;
	/** 备注*/
	private String  remarkd;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getMobilet() {
		return mobilet;
	}
	public void setMobilet(String mobilet) {
		this.mobilet = mobilet;
	}
	
	public String getCert_not() {
		return cert_not;
	}
	public void setCert_not(String cert_not) {
		this.cert_not = cert_not;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getRemarkd() {
		return remarkd;
	}
	public void setRemarkd(String remarkd) {
		this.remarkd = remarkd;
	}
	public CertTypes getCert_types() {
		return cert_types;
	}
	public void setCert_types(CertTypes cert_types) {
		this.cert_types = cert_types;
	}

}
