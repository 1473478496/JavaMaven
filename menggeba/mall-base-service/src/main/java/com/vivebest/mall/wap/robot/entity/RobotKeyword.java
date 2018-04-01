package com.vivebest.mall.wap.robot.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_wechat_keyword")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_wechat_keyword")
public class RobotKeyword extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2033169611917950399L;
	private static Logger logger = Logger.getLogger(RobotKeyword.class);
	
	/**
	 * 回复内容状态
	 * 
	 * 
	 */
	public enum KeywordState {
		// 有效
		valid,

		// 无效
		invalid
	}
	
	/**
	 * 关键字名称
	 */
	private String name;
	
	/**
	 * 关键字状态
	 */
	private KeywordState state;
	
	/**
	 * 所属规则
	 */
	private RebotMessage rebotMessage;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false)
	public KeywordState getState() {
		return state;
	}

	public void setState(KeywordState state) {
		this.state = state;
	}
	@ManyToOne(targetEntity=RebotMessage.class, cascade=CascadeType.ALL)
	@JoinColumn(name="mid")
	public RebotMessage getRebotMessage() {
		return rebotMessage;
	}
	
	public void setRebotMessage(RebotMessage rebotMessage) {
		this.rebotMessage = rebotMessage;
	}
	
	
}
