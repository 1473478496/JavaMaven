package com.vivebest.mall.merchant.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_trade_admin")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "trade_admin_sequence")
public class TradeAdmin extends BaseEntity {

	private static final long serialVersionUID = 4394109768544058338L;

	/** 部门 */
	private String department;
	
	/** e-mail */
	private String email;

	/** 是否启用 */
	private Boolean is_enabled;
	
	/** 是否锁定 */
	private Boolean is_locked;
	
	/** 锁定日期 */
	private Date locked_date;
	
	/** 最后登录日期 */
	private Date login_date;
	
	/** 连续登录失败次数 */
	private int login_failure_count;
	
	/** 最后登录ip */
	private String login_ip;
	
	/** 姓名 */
	private String name;
	
	/** 密码 */
	private String password;
	
	/** 用户名 */
	private String username;
	
	/** 商户id */
	private Trade trade;
	
	/** 角色 */
	private Set<TradeRole> tradeRoles = new HashSet<TradeRole>();

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Boolean is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Boolean getIs_locked() {
		return is_locked;
	}

	public void setIs_locked(Boolean is_locked) {
		this.is_locked = is_locked;
	}

	public Date getLocked_date() {
		return locked_date;
	}

	public void setLocked_date(Date locked_date) {
		this.locked_date = locked_date;
	}

	public Date getLogin_date() {
		return login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public int getLogin_failure_count() {
		return login_failure_count;
	}

	public void setLogin_failure_count(int login_failure_count) {
		this.login_failure_count = login_failure_count;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	/**
	 * 获取角色
	 * 
	 * @return 角色
	 */
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "gbm_trade_admin_role")
	public Set<TradeRole> getTradeRoles() {
		return tradeRoles;
	}

	/**
	 * 设置角色
	 * 
	 * @param roles
	 *            角色
	 */
	public void setTradeRoles(Set<TradeRole> tradeRoles) {
		this.tradeRoles = tradeRoles;
	}
	
}
