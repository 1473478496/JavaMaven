package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 换货订单 --实体类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_changes")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_changes_sequence")
public class Changes extends BaseEntity{

	private static final long serialVersionUID = 8370942500343156156L;

	/**
	 * 审核类型
	 * 
	 * @author junly
	 *
	 */
	public enum Status {
		// 0-待审核
		pending,
		// 1-审核通过
		approve,
		// 2-审核拒绝
		unapprove,
		//3-客户已发货
		delivered,
		// 4-换货完成
		completeRep
	}

	// 地址
	private String address;
	/** 到期时间 */
	private Date expire;
	/** 地区 */
	private Area area;

	// 物流公司
	private String deliveryCorp;

	// 物流费用
	private BigDecimal freight;

	// 备注
	private String memo;

	// 操作员
	private Admin operator;

	// 电话
	private String phone;

	// 发货人
	private String shipper;

	/** 配送方式 */
	private ShippingMethod shippingMethod;

	// 编号
	private String sn;

	// 运单号
	private String trackingNo;

	// 邮编
	private String zipCode;

	// 商户id
	private BigDecimal tradeId;

	// 换货状态
	private Status status;

	// 换货理由
	private String changeCause;

	// 换货问题描述
	private String changeDesc;

	// 换货日期
	private Date changeDate;

	// 换货凭证
	private String changeVoucher;

	// 审核日期
	private Date auditDate;

	// 审核人
	private Admin auditApply;

	// 审核描述
	private String auditDesc;

	/** 换货单项 */
	private List<ChangesItem> changesItems = new ArrayList<ChangesItem>();

	/** 订单项 */
	private OrderItem orderItem;
	/** 订单 */
	private Order orders;
	/** 会员 */
	private Member member;
	/**
	 * 获取 换货单项
	 * 
	 * @return
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "changes", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<ChangesItem> getChangesItems() {
		return changesItems;
	}

	/**
	 * 设置 换货单项
	 * 
	 * @return
	 */
	public void setChangesItems(List<ChangesItem> changesItems) {
		this.changesItems = changesItems;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置到期时间
	 * 
	 * @param expire
	 *            到期时间
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 获取地址
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取物流公司
	 * 
	 * @return
	 */
	// @Column(nullable = false)
	@JsonProperty
	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	/**
	 * 设置物流公司
	 * 
	 * @param deliveryCorp
	 */
	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}
	/**
	 * 获取会员信息
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员信息
	 * @param member
	 */
	public void setMember(Member member) {
		this.member = member;
	}
	/**
	 * 获取物流费用
	 * 
	 * @return
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * 设置物流费用
	 * 
	 * @param freight
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * 获取备注
	 * 
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取操作员
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 */
	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	/**
	 * 获取电话号码
	 * 
	 * @return
	 */
	// @Column(nullable = false)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话号码
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取发货人
	 * 
	 * @return
	 */
	// @Column(nullable = false)
	public String getShipper() {
		return shipper;
	}

	/**
	 * 设置发货人
	 * 
	 * @param shipper
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	/**
	 * 获取配送方式
	 * 
	 * @return 配送方式
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	/**
	 * 获取编号
	 * 
	 * @return
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置订单号
	 * 
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取运单号
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public String getTrackingNo() {
		return trackingNo;
	}

	/**
	 * 设置运单号
	 * 
	 * @param trackingNo
	 */
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	/**
	 * 获取 邮编
	 * 
	 * @return
	 */
	// @Column(nullable = false)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取商户id
	 * 
	 * @return
	 */
	// @Column(nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getTradeId() {
		return tradeId;
	}

	/**
	 * 设置商户id
	 * 
	 * @param tradeId
	 */
	public void setTradeId(BigDecimal tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * 获取换货状态
	 * 
	 * @return
	 */
	// @Column(nullable = false)
	@JsonProperty
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置换货状态
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 换货理由
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public String getChangeCause() {
		return changeCause;
	}

	/**
	 * 设置换货理由
	 * 
	 * @param returnCause
	 */
	public void setChangeCause(String changeCause) {
		this.changeCause = changeCause;
	}

	/**
	 * 获取换货问题描述
	 * 
	 * @return
	 */
	// @Column(nullable = true)
	public String getChangeDesc() {
		return changeDesc;
	}

	/**
	 * 设置换货问题描述
	 * 
	 * @param returnDesc
	 */
	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	/**
	 * 获取换货日期
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * 设置换货日期
	 * 
	 * @param returnDate
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * 获取 换货凭证
	 * 
	 * @return
	 */
	@JsonProperty
//	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getChangeVoucher() {
		return changeVoucher;
	}

	/**
	 * 设置换货凭证
	 * 
	 * @param returnVoucher
	 */
	public void setChangeVoucher(String changeVoucher) {
		this.changeVoucher = changeVoucher;
	}

	/**
	 * 获取审核日期
	 * 
	 * @return
	 */

	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * 设置审核日期
	 * 
	 * @param auditDate
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 获取审核人
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getAuditApply() {
		return auditApply;
	}

	/**
	 * 设置审核人
	 * 
	 * @param auditApply
	 */
	public void setAuditApply(Admin auditApply) {
		this.auditApply = auditApply;
	}

	/**
	 * 获取审核描述
	 * 
	 * @return
	 */
	// @Column(nullable = true)
	public String getAuditDesc() {
		return auditDesc;
	}

	/**
	 * 设置 审核描述
	 * 
	 * @param auditDesc
	 */
	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	/**
	 * 获取订单项
	 * 
	 * @return
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_item", nullable = false, updatable = false)
	public OrderItem getOrderItem() {
		return orderItem;
	}

	/**
	 * 设置订单项
	 * 
	 * @return
	 */
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	public Order getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @return 订单
	 */
	public void setOrders(Order orders) {
		this.orders = orders;
	}

	/**
	 * 是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean isExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}
}
