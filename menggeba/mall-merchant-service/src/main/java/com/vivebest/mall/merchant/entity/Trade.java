package com.vivebest.mall.merchant.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_trade")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "trade_sequence")
public class Trade extends BaseEntity {

	private static final long serialVersionUID = -802243610844989819L;

	/**
	 * 性别
	 * 
	 * @author Administrator
	 *
	 */
	public enum Sex {
		
		male, /** 男 */
		female /** 女 */
	
	}
	/**
	 * 商户申请状态
	 * 
	 * @author Administrator
	 *
	 */
	public enum Status {
		
		waitingApprove,/** 待审核 */
		approved,  /** 审核通过 */
		unapprove, /** 审核拒绝 */
		frozen/** 冻结 */
	}

	/**
	 * 证件类型
	 * 
	 * @author Administrator
	 *
	 */
	public enum CertType {
		
		identityCard, /** 身份证 */
		passport, /** 护照 */
		militaryID/** 军官证 */
	}
	
	/**
	 * 同意商家合作协议
	 * 
	 *
	 */ 
	public enum Agree {
		
		 disagree, /** 不同意 */
		 agree/** 同意 */
	}
	/** 商户编号 */
	private String no;
	/** 申请日期 */
	private Date apply_date;
	/** 商户名称 */
	private String name;
	/** 别名-越南名 */
	private String alias_Name;
	/** 性别 */
	private Sex sex;
	/** 证件类型 */
	private CertType cert_type;

	/** 证件号码 */
	private String cert_nos;

	/** 法人身份证照片 */
	private String cert_photos;
	
	/** 商戶照片 */
	private String logo_url;

	/** 联系方式 -电话 */
	private String mobiles;

	/** 联系人 */
	private String apply;

	/** 邮箱 */
	private String email;

	/** 生日 */
	private Date birthday;

	/** 商户地址 */
	private String addr;
	/** 商铺号 */
	private String shop_no;

	/** 协议开始时间 */
	private Date beg_date;
	/** 协议结束时间 */
	private Date end_date;

	/** 组织机构代码编号 */
	private String group_no;

	/** 组织机构营业证照片 */
	private String group_photo;

	/** 审核 */
	private Status status;
	/**
	 * 申请状态说明
	 */
	private String status_desc;

	/** 审核人 */
	private String auditApply;

	/** 审核日期 */
	private Date auditDate;

	/** 备注 */
	private String remarkv;
	/** 同意商家合作协议  */
	private Agree agree;

	
	/** TradeProxy */
	private Set<TradeProxy> tradeProxy = new HashSet<TradeProxy>();

	/** TradeShop */
	private Set<TradeShop> tradeShops = new HashSet<TradeShop>();

	/** TradeAdmin */
	private Set<TradeAdmin> tradeAdmins = new HashSet<TradeAdmin>();

	/** TradeBanks */
	private Set<TradeBanks> tradeBanks = new HashSet<TradeBanks>();
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias_Name() {
		return alias_Name;
	}

	public void setAlias_Name(String alias_Name) {
		this.alias_Name = alias_Name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public CertType getCert_type() {
		return cert_type;
	}

	public void setCert_type(CertType cert_type) {
		this.cert_type = cert_type;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCert_nos() {
		return cert_nos;
	}

	public void setCert_nos(String cert_nos) {
		this.cert_nos = cert_nos;
	}

	public String getCert_photos() {
		return cert_photos;
	}

	public void setCert_photos(String cert_photos) {
		this.cert_photos = cert_photos;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getRemarkv() {
		return remarkv;
	}

	public void setRemarkv(String remarkv) {
		this.remarkv = remarkv;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getShop_no() {
		return shop_no;
	}

	public void setShop_no(String shop_no) {
		this.shop_no = shop_no;
	}

	public Date getBeg_date() {
		return beg_date;
	}

	public void setBeg_date(Date beg_date) {
		this.beg_date = beg_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}

	public String getGroup_photo() {
		return group_photo;
	}

	public void setGroup_photo(String group_photo) {
		this.group_photo = group_photo;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trade", cascade = CascadeType.ALL)
	public Set<TradeProxy> getTradeProxy() {
		return tradeProxy;
	}

	public void setTradeProxy(Set<TradeProxy> tradeProxy) {
		this.tradeProxy = tradeProxy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trade", cascade = CascadeType.ALL)
	public Set<TradeShop> getTradeShops() {
		return tradeShops;
	}

	public void setTradeShops(Set<TradeShop> tradeShops) {
		this.tradeShops = tradeShops;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trade", cascade = CascadeType.ALL)
	public Set<TradeAdmin> getTradeAdmins() {
		return tradeAdmins;
	}

	public void setTradeAdmins(Set<TradeAdmin> tradeAdmins) {
		this.tradeAdmins = tradeAdmins;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trade", cascade = CascadeType.ALL)
	public Set<TradeBanks> getTradeBanks() {
		return tradeBanks;
	}

	public void setTradeBanks(Set<TradeBanks> tradeBanks) {
		this.tradeBanks = tradeBanks;
	}
	
	public String getAuditApply() {
		return auditApply;
	}

	public void setAuditApply(String auditApply) {
		this.auditApply = auditApply;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Status getStatus() {
		return status;
	}

	public Agree getAgree() {
		return agree;
	}

	public void setAgree(Agree agree) {
		this.agree = agree;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}


}
