package com.vivebest.mall.wap.robot.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 微信助手信息回复
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_wechat_message")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_wechat_message_sequence")
public class RebotMessage extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2422519499287416425L;
	private static Logger logger = Logger.getLogger(RebotMessage.class);
	
	/**
	 * 回复内容状态
	 * 
	 * 
	 */
	public enum MessageState {
		// 有效
		valid,

		// 无效
		invalid
	}
	
	/**
	 * 规则名
	 */
	private String name;
	
	/**
	 * 关键字
	 */
	private Set<RobotKeyword> keyword;
	
	/**
	 * 回复内容
	 */
	private String content;
	
	/**
	 * 图片
	 */
	private String image;
	
	/**
	 * 消息状态
	 */
	private MessageState state;
	
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="rebotMessage")
	public Set<RobotKeyword> getKeyword() {
		return keyword;
	}

	public void setKeyword(Set<RobotKeyword> keyword) {
		this.keyword = keyword;
	}
	
	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@Column(nullable = false)
	public MessageState getState() {
		return state;
	}

	public void setState(MessageState state) {
		this.state = state;
	}

	
}
