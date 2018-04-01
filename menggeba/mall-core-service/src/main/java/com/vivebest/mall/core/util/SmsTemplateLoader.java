package com.vivebest.mall.core.util;

import java.util.Map;
import java.util.Map.Entry;

import freemarker.cache.StringTemplateLoader;

/**
 * 
 * SMS模板加载器
 * 
 * 由于${XXXX}与Spring的占位符有冲突，所以模板先配置成@{XXXX}，在加载模板再进行替换
 * 
 * @author mo.xf
 *
 */
public class SmsTemplateLoader extends StringTemplateLoader {
	/**
	 * 
	 * @param templates
	 */
	public void setTemplates(Map<String , String> templates){
		for(Entry<String, String> entry : templates.entrySet()){
			String value = entry.getValue() ;
			//将模板中的@{XXXX}替换成${XXXX}
			value = RegexUtils.replacePart(entry.getValue(), "@\\{\\s*[a-zA-Z]{1}\\w*\\s*\\}", "@", "$") ;
			this.putTemplate(entry.getKey(),value ) ;
		}
	}
}
