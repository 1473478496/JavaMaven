/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.common;

/**
 * 公共参数
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** mall.xml文件路径 */
	public static final String SHOPXX_XML_PATH = "/mall.xml";

	/** mall.properties文件路径 */
	public static final String SHOPXX_PROPERTIES_PATH = "/mall.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}