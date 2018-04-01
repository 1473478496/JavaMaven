package com.vivebest.mall.core.common;

/**
 * 
 * @author liu.jch
 *
 */
public enum SmsBusType {
	/**
	 * 1-注册、2-找回密码、100-自定义
	 */
	REGISTER(1,"register"),
	FIND_PWD(2,"findPwd"),
	SHIPPING(201,"shipping"),
	RECEIVE(202,"receive"),
	RIGHTS(203,"rights"),
	OTHER(100,"other");
	
	/**
	 * 
	 * @param value
	 * @param name
	 */
	private SmsBusType(int value , String name){
		this.value = value ;
		this.name = name ;
	}
	
	private int value ;
	
	private String name ;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
