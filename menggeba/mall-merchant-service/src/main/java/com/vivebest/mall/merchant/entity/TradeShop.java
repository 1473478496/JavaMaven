package com.vivebest.mall.merchant.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_trade_shop")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "trade_shop_sequence")
public class TradeShop extends BaseEntity {

	private static final long serialVersionUID = 5522270054903623318L;
	
	/**
	 * 商家管理状态
	 * @author Administrator
	 *
	 */
	public enum Status{
		/**开启状态*/
		open,
		/**关闭状态*/
		close
	}
	
	/**
	 * 平台管理状态
	 * @author Administrator
	 *
	 */
	public enum PlatStatus{
		/**开启状态*/
		open,
		/**关闭状态*/
		close
	}

	/** 开通日期 */
	private Date apply_date;
	
	/** 商户id */
	private Trade trade;
	
	/** 店铺名称 */
	private String name;
	
	/** 店铺logo */
	private String logo_url;
	
	/** 店铺评分 */
	private String score;
	
	/** 开始工作时间 */
	private String begin_worktime;
	
	/** 结束工作时间 */
	private String end_worktime;
	
	/** 在线客服，多个｜分隔 */
	private String customer_service;
	
	/** 服务地址 */
	private String addr;
	
	/** 店铺公告 */
	private String shop_board;
	
	/** 商家管理状态 */
	private Status status;
	
	/**平台管理状态*/
	private PlatStatus platStatus;
	
	/** 备注 */
	private String remark;
	/** 电话号码 */
	private String phone;
	/** 搜索信息*/
	private String searchKey;
	
	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getBegin_worktime() {
		return begin_worktime;
	}

	public void setBegin_worktime(String begin_worktime) {
		this.begin_worktime = begin_worktime;
	}

	public String getEnd_worktime() {
		return end_worktime;
	}

	public void setEnd_worktime(String end_worktime) {
		this.end_worktime = end_worktime;
	}

	public String getCustomer_service() {
		return customer_service;
	}

	public void setCustomer_service(String customer_service) {
		this.customer_service = customer_service;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getShop_board() {
		return shop_board;
	}

	public void setShop_board(String shop_board) {
		this.shop_board = shop_board;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PlatStatus getPlatStatus() {
		return platStatus;
	}

	public void setPlatStatus(PlatStatus platStatus) {
		this.platStatus = platStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	@Override
	public String toString() {
		return "TradeShop [apply_date=" + apply_date + ", trade=" + trade + ", name=" + name + ", logo_url=" + logo_url
				+ ", score=" + score + ", begin_worktime=" + begin_worktime + ", end_worktime=" + end_worktime
				+ ", customer_service=" + customer_service + ", addr=" + addr + ", shop_board=" + shop_board
				+ ", status=" + status + ", remark=" + remark + ", phone=" + phone + ", searchKey=" + searchKey + "]";
	}
	
	
}
