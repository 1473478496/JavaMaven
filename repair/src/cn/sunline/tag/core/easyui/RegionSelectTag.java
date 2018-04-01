package cn.sunline.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.sunline.core.util.ApplicationContextUtil;
import cn.sunline.core.util.MutiLangUtil;
import cn.sunline.web.system.service.SystemService;

import com.google.gson.Gson;

/**
 * 
 * 选择下拉框
 * 
 * @author: zhanghj
 * @date： 日期：2016-11-18
 * @version 1.0
 */
public class RegionSelectTag extends TagSupport {

	private static final long serialVersionUID = 1;
	private String typeGroupCode; // 数据字典类型
	private String field; // 选择表单的Name EAMPLE:<select name="selectName" id = ""
	private String id; // 选择表单ID EAMPLE:<select name="selectName" id = "" />
	private String defaultVal; // 默认值
	private String divClass; // DIV样式
	private String labelClass; // Label样式
	private String title; // label显示值
	private boolean hasLabel = true; // 是否显示label
	private String type;// 控件类型select|radio|checkbox
	private String dictTable;// 自定义字典表
	private String dictField;// 自定义字典表的匹配字段-字典的编码值
	private String dictText;// 自定义字典表的显示文本-字典的显示值
	private String extendJson;// 扩展参数
	private String dictCondition;
	private String value; // 当前值
	private String readonly;// 只读属性

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}


	public String getDictCondition() {
		return dictCondition;
	}

	public void setDictCondition(String dicCondition) {
		this.dictCondition = dicCondition;
	}

	private String datatype;

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	@Autowired
	private static SystemService systemService;

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = null;
		try {
			out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.clear();
				out.close();
			} catch (Exception e2) {
			}
		}
		return EVAL_PAGE;
	}
	@SuppressWarnings("unchecked")
	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (dictTable != null) {
			if ("detail".equals(this.pageContext.getRequest().getParameter("load"))) {// /查看时
				if (defaultVal != null && !"".equals(defaultVal)) {
					this.dictCondition = " where " + dictField + " = '"+ defaultVal + "'";
				}
			} else {
				if ("province".equals(field)) {
					if (defaultVal != null && !"".equals(defaultVal)) {
						this.pageContext.setAttribute("province", defaultVal);
					}
				}

				if ("city".equals(field)) {
					if (this.pageContext.getAttribute("province") != null) {
						String father = this.pageContext.getAttribute("province").toString();
						this.dictCondition = " where father = '" + father + "'";
					}

					if (defaultVal != null && !"".equals(defaultVal)) {
						this.pageContext.setAttribute("city", defaultVal);
					}
				}

				if ("area".equals(field)) {
					if (this.pageContext.getAttribute("city") != null) {
						String father = this.pageContext.getAttribute("city").toString();
						this.dictCondition = " where father = '" + father + "'";
					}
					if (defaultVal != null && !"".equals(defaultVal)) {
						this.pageContext.setAttribute("area", defaultVal);
					}
				}
				if ("spareArea1".equals(field)) {
					if (this.pageContext.getAttribute("city") != null) {
						String father = this.pageContext.getAttribute("city").toString();
						this.dictCondition = " where father = '" + father + "'";
					}
					if (defaultVal != null && !"".equals(defaultVal)) {
						this.pageContext.setAttribute("spareArea1", defaultVal);
					}
				}
				
				if ("spareArea2".equals(field)) {
					if (this.pageContext.getAttribute("city") != null) {
						String father = this.pageContext.getAttribute("city").toString();
						this.dictCondition = " where father = '" + father + "'";
					}
					if (defaultVal != null && !"".equals(defaultVal)) {
						this.pageContext.setAttribute("spareArea2", defaultVal);
					}
				}
			}

			List<Map<String, Object>> list = queryDic();

			sb.append("<select name=\"" + field + "\"");
			// 增加扩展属性
			if (!StringUtils.isBlank(this.extendJson)) {
				Gson gson = new Gson();
				Map<String, String> mp = gson.fromJson(extendJson, Map.class);
				for (Map.Entry<String, String> entry : mp.entrySet()) {
					sb.append(entry.getKey() + "=\"" + entry.getValue() + "\"");
				}
			}
			if (!StringUtils.isBlank(this.id)) {
				sb.append(" id=\"" + id + "\"");
			}
			sb.append(">");

			select("common.please.select", "", sb);

			for (Map<String, Object> map : list) {
				select(map.get("text").toString(), map.get("field").toString(),sb);
			}
			sb.append("</select>");

		}

		return sb;
	}

	/**
	 * 选择框方法
	 * 
	 * @作者：zhanghj
	 * 
	 * @param name
	 * @param code
	 * @param sb
	 */
	private void select(String name, String code, StringBuffer sb) {
		if (code.equals(this.value) || code.equals(this.defaultVal)) {
			sb.append(" <option value=\"" + code + "\" selected=\"selected\">");
		} else {
			sb.append(" <option value=\"" + code + "\">");
		}
		sb.append(MutiLangUtil.getMutiLangInstance().getLang(name));
		sb.append(" </option>");
	}

	/**
	 * 查询自定义
	 * 
	 * @作者：zhanghj
	 */
	private List<Map<String, Object>> queryDic() {
		String sql = "select " + dictField + " as field," + dictText + " as text from " + dictTable;

		if (dictCondition != null) {
			sql += dictCondition;
		}

		if ("city".equals(field) || "area".equals(field)) {
			if (dictCondition == null) {
				return new ArrayList<Map<String, Object>>();
			}
		}

		dictCondition = null;

		systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
		List<Map<String, Object>> list = systemService.findForJdbc(sql);
		return list;
	}

	public String getTypeGroupCode() {
		return typeGroupCode;
	}

	public void setTypeGroupCode(String typeGroupCode) {
		this.typeGroupCode = typeGroupCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}

	public String getLabelClass() {
		return labelClass;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHasLabel() {
		return hasLabel;
	}

	public void setHasLabel(boolean hasLabel) {
		this.hasLabel = hasLabel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDictTable() {
		return dictTable;
	}

	public void setDictTable(String dictTable) {
		this.dictTable = dictTable;
	}

	public String getDictField() {
		return dictField;
	}

	public void setDictField(String dictField) {
		this.dictField = dictField;
	}

	public String getDictText() {
		return dictText;
	}

	public void setDictText(String dictText) {
		this.dictText = dictText;
	}

	public String getExtendJson() {
		return extendJson;
	}

	public void setExtendJson(String extendJson) {
		this.extendJson = extendJson;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
