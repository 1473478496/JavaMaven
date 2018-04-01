package com.vivebest.mall.merchant.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;
@Entity
@Table(name="gbm_trade_banks")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "trade_banks_sequence")
public class TradeBanks extends BaseEntity {

	private static final long serialVersionUID = 1100191751185009794L;
	
	
	 private Trade  trade; //'商户类对象 id',
	 private String  card_name;//'开户名',
	 private String card_nos;// '卡号',
	 private String bank_name;//'开户行名称',
	 private String cert_no;//'证件号',
	 private String remarks ;// '备注',
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	@Column(unique=true)
	public String getCard_nos() {
		return card_nos;
	}
	public void setCard_nos(String card_nos) {
		this.card_nos = card_nos;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

}