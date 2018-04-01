package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_rights")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_sequence")
public class Rights extends BaseEntity {

	private static final long serialVersionUID = 2959782924914652772L;
	
	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/rights/content";
	
	/** wap访问路径前缀 */
	private static final String PATH_PREFIX_WAP = "/wap/right/content";
	
	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";
	
	public enum RightsType{
		/**线上权益*/
		onlineRights,
		/**线下权益*/
		offlineRights
	}

	/**
	 * 兑换数量类型
	 */
	public enum ConvertOrderType {

		/** 升序 */
		convertAsc,

		/** 降序 */
		convertDesc,
	}
	/**
	 * 最新上架类型
	 */
	public enum PutAwayOrderType {
		/** 升序 */
		putAwayAsc,

		/** 降序 */
		putAwayDesc,
	}
	/**
	 * 价格排序
	 *
	 */
	public enum PriceOrderType {
		/** 评分升序 */
		priceAsc,
		
		/** 评分降序 */
		priceDesc,
	}
	/**
	 * 权益类型
	 */
	private RightsType rightsType;
	/**
	 * 权益编号
	 */
	private String sn;

	/**
	 * 权益名称
	 */
	private String name;
	
	/**
	 * 权益logo
	 */
	private String logoUrl;
	
	/**
	 * 首页图片
	 */
	private String homeImage;
	
	/**
	 * 人气图片
	 */
	private String popularityImage;
	
	/**
	 * 权益提醒
	 */
	private String notify;
	
	/**
	 * 市场价
	 */
	private String marketPrice;
	
	/**
	 * 权益销售价
	 */
	private BigDecimal price;
	
	/**
	 * 权益所需萌值
	 */
	private Long pricePoint;
	
	/**
	 * 开始日期
	 */
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	/**
	 * 单位
	 */
	private String unit;
	
	 
	
	/**
	 * 点击数，就是热度
	 */
	private Long hits;
	
	/**
	 * 总评分
	 */
	private Long totalScore;
	
	/**
	 * 状态，0-未启用，1-已启用
	 */
	private Boolean status;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/** 是否置顶 */
	private Boolean isTop;
	
	/** 是否首页展示 */
	private Boolean isHome;
	
	/** 是否人气商品 */
	private Boolean isPopularity;
	
	/** 介绍 */
	private String introduction;
	
	/** 权益属性值0 */
	private String attributeValue0;

	/** 权益属性值1 */
	private String attributeValue1;

	/** 权益属性值2 */
	private String attributeValue2;
	
	private Long saleNumber;
	
	/** 是否允许使用优惠券 */
	private Boolean isCouponAllowed;
	
	/**是否集采*/
	private Boolean isJicai;
	
	/**
	 * 权益分类
	 */
	private RightsCategory rightsCategory;
	
	/**
	 * 权益品牌
	 */
	private RightsBrand rightsBrand;
	
	/**
	 * 权益商户
	 */
	private Set<RightsTrade> rightsTrades=new HashSet<RightsTrade>();
	
	/** 权益订单项 */
	private Set<RightOrderItem> orderItems = new HashSet<RightOrderItem>();
	
	
	
	private Set<RightsCode> rightsCode=new HashSet<RightsCode>();
	
	
	/** 权益标签 */
	private Set<RightsLabel> rightsLabel = new HashSet<RightsLabel>();
	

	/**
	 * @return 权益编号
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @return 权益编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return 权益名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 权益名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 权益logo
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl 权益logo
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	/**
	 * @return 首页展示图片
	 */
	public String getHomeImage() {
		return homeImage;
	}
	/**
	 * @param homeImage 首页展示图片
	 */
	public void setHomeImage(String homeImage) {
		this.homeImage = homeImage;
	}
	/**
	 * @return 人气图片
	 */
	public String getPopularityImage() {
		return popularityImage;
	}
	/**
	 * @param popularityImage 人气图片
	 */
	public void setPopularityImage(String popularityImage) {
		this.popularityImage = popularityImage;
	}
	/**
	 * @return 权益提醒
	 */
	public String getNotify() {
		return notify;
	}

	/**
	 * @param notify 权益提醒
	 */
	public void setNotify(String notify) {
		this.notify = notify;
	}

	/**
	 * @return 市场价
	 */
	public String getMarketPrice() {
		return marketPrice;
	}

	/**
	 * @param marketPrice 市场价
	 */
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * @return 权益销售价
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price 权益销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return 权益所需萌值
	 */
	public Long getPricePoint() {
		return pricePoint;
	}

	/**
	 * @param pricePoint 权益所需萌值
	 */
	public void setPricePoint(Long pricePoint) {
		this.pricePoint = pricePoint;
	}

	/**
	 * @return 开始日期
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate 开始日期
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return 结束日期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate 结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return 单位
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit 单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

 

	/**
	 * @return 点击数，就是热度
	 */
	public Long getHits() {
		return hits;
	}

	/**
	 * @param hits 点击数，就是热度
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * @return 总评分
	 */
	public Long getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore 总评分
	 */
	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return 状态，0-未启用，1-已启用
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status 状态，0-未启用，1-已启用
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
//	@Field(store = Store.YES, index = Index.NO)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsTop() {
		return isTop;
	}
	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	/**
	 * 获取是否首页展示
	 * 
	 * @return 是否首页展示
	 */
//	@Field(store = Store.YES, index = Index.NO)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsHome() {
		return isHome;
	}
	/**
	 * 设置是否首页展示
	 * @param IsHome
	 * 
	 */
	public void setIsHome(Boolean isHome) {
		this.isHome = isHome;
	}
	/**
	 * 获取是否人气商品
	 * @param IsPopularity
	 * 
	 */
	public Boolean getIsPopularity() {
		return isPopularity;
	}
	/**
	 * 设置是否人气商品
	 * @param IsPopularity
	 * 
	 */
	public void setIsPopularity(Boolean isPopularity) {
		this.isPopularity = isPopularity;
	}
	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	/*@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob*/
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return 权益品牌
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public RightsBrand getRightsBrand() {
		return rightsBrand;
	}

	/**
	 * @param rightsBrand 权益品牌
	 */
	public void setRightsBrand(RightsBrand rightsBrand) {
		this.rightsBrand = rightsBrand;
	}

	/**
	 * @return 权益分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public RightsCategory getRightsCategory() {
		return rightsCategory;
	}

	/**
	 * @param rightsCategory 权益分类
	 */
	public void setRightsCategory(RightsCategory rightsCategory) {
		this.rightsCategory = rightsCategory;
	}

	/**
	 * @return 权益商户
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinTable(name = "gbm_rights_trade_rights",
	joinColumns = {@JoinColumn(name = "rightses", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "rights_trades", referencedColumnName = "id")})
	public Set<RightsTrade> getRightsTrades() {
		return rightsTrades;
	}

	/**
	 * @param rightsTrade 权益商户
	 */
	public void setRightsTrades(Set<RightsTrade> rightsTrades) {
		this.rightsTrades = rightsTrades;
	}
	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	@OneToMany(mappedBy = "rights", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<RightOrderItem> getOrderItems() {
		return orderItems;
	}
	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(Set<RightOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}
	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getWapPath() {
		if (getId() != null) {
			return PATH_PREFIX_WAP + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}
	/**
	 * 获取权益类型
	 * @return
	 */
	@Column(name = "rights_type")
	@JsonProperty
	public RightsType getRightsType() {
		return rightsType;
	}
	/**
	 * 设置权益类型
	 * @param rightsType
	 */
	public void setRightsType(RightsType rightsType) {
		this.rightsType = rightsType;
	}
	public String getAttributeValue0() {
		return attributeValue0;
	}
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}
	public String getAttributeValue1() {
		return attributeValue1;
	}
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}
	public String getAttributeValue2() {
		return attributeValue2;
	}
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}
	@OneToMany(mappedBy = "rights", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<RightsCode> getRightsCode() {
		return rightsCode;
	}
	
	
	@OneToMany(mappedBy = "rights", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<RightsLabel> getRightsLabel() {
		return rightsLabel;
	}
	
	public void setRightsLabel(Set<RightsLabel> rightsLabel) {
		this.rightsLabel = rightsLabel;
	}
	
	public void setRightsCode(Set<RightsCode> rightsCode) {
		this.rightsCode = rightsCode;
	}
	
	

	/**
	 * 设置是否允许使用优惠券
	 * 
	 * @param isCouponAllowed
	 *            是否允许使用优惠券
	 */
	public void setIsCouponAllowed(Boolean isCouponAllowed) {
		this.isCouponAllowed = isCouponAllowed;
	}
	public Boolean getIsCouponAllowed() {
		return isCouponAllowed;
	}
	public Long getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(Long saleNumber) {
		this.saleNumber = saleNumber;
	}
	
	/**
	 * 获取集采
	 * @return
	 */
	public Boolean getIsJicai() {
		return isJicai;
	}
	/**
	 * 设置集采
	 * @param isJicai
	 */
	public void setIsJicai(Boolean isJicai) {
		this.isJicai = isJicai;
	}
	@Transient
	public Long getAmount(){
		Long num = 0L;
		if(!getRightsCode().isEmpty()){
			for (RightsCode rightsCode : getRightsCode()) {
				if(rightsCode.getMember() != null && rightsCode.getRightOrder() != null){
					num++;
				}
			}
		}
		getRightsCode().clear();
		return num;
	}
	
}
