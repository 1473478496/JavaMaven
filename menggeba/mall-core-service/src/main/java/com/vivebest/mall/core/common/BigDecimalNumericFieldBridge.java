/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.common;

import java.math.BigDecimal;

import org.apache.lucene.document.Document;

/**
 * BigDecimal类型转换
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public class BigDecimalNumericFieldBridge {//extends NumericFieldBridge {

	/**
	 * 获取BigDecimal对象
	 * 
	 * @param name
	 *            名称
	 * @param document
	 *            document
	 * @return BigDecimal对象
	 */
	public Object get(String name, Document document) {
		return new BigDecimal(document.getField(name).stringValue());
	}

	/**
	 * 设置BigDecimal对象
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @param document
	 *            document
	 * @param luceneOptions
	 *            luceneOptions
	 */
	//public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
	public void set(String name, Object value, Document document) {
		if (value != null) {
			BigDecimal decimalValue = (BigDecimal) value;
//			luceneOptions.addNumericFieldToDocument(name, decimalValue.doubleValue(), document);
		}
	}

}