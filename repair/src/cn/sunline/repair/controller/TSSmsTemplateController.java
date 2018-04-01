package cn.sunline.repair.controller;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.exception.BusinessException;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.model.json.AjaxJson;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.constant.Globals;
import cn.sunline.core.util.BrowserUtils;
import cn.sunline.core.util.MyBeanUtils;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.repair.service.TSSmsTemplateServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSDepart;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;




/**   
 * @Title: Controller
 * @Description: 消息模本表
 * @author onlineGenerator
 * @date 2014-09-17 23:52:46
 * @version V1.0   
 *
 */
//@Scope("prototype")
@Controller
@RequestMapping("/tSSmsTemplateController")
public class TSSmsTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSSmsTemplateController.class);

	@Autowired
	private TSSmsTemplateServiceI tSSmsTemplateService;
	@Autowired
	private SystemService systemService;


	/**
	 * 消息模本表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSSmsTemplate")
	public ModelAndView tSSmsTemplate(HttpServletRequest request) {
		return new ModelAndView("system/repair/sms/tSSmsTemplateList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSSmsTemplateEntity tSSmsTemplate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId=(String) session.getAttribute("orgId");
		departId=departId+"%";
//		StringBuffer sb=new StringBuffer();
//		sb.append("select ID from T_S_DEPART where 1=1");
//		sb.append(" and ID='").append(departId).append("'");
//		sb.append(" or PARENTDEPARTID like'").append(departId).append("%'");
//		List<Object> departList=systemService.findListbySql(sb.toString());
//		Object[] obj =new Object[departList.size()];
//		
//		for (int i = 0; i < departList.size(); i++) {
//			obj[i]=departList.get(i);
//			
//		}
		CriteriaQuery cq = new CriteriaQuery(TSSmsTemplateEntity.class, dataGrid);
		cq.like("issuDepartment",departId);
		//cq.in("issuDepartment",obj);
		cq.add();
		//查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSSmsTemplate, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//cq.add();
		this.tSSmsTemplateService.getDataGridReturn(cq, true);
		List<TSSmsTemplateEntity> cfeList = new ArrayList<TSSmsTemplateEntity>();
	        for (Object o : dataGrid.getResults()) {
	            if (o instanceof TSSmsTemplateEntity) {
	            	TSSmsTemplateEntity tss = (TSSmsTemplateEntity) o;
	                if (tss.getIssuDepartment()!= null && !"".equals(tss.getIssuDepartment())) {
	                    TSDepart tsd = systemService.getEntity(TSDepart.class, tss.getIssuDepartment());
	                        tss.setOrgName(tsd.getDepartname());
	                }
	                cfeList.add(tss);
	            }
	        }

		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除消息模本表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSSmsTemplateEntity tSSmsTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSSmsTemplate = systemService.getEntity(TSSmsTemplateEntity.class, tSSmsTemplate.getId());
		message = "消息模本表删除成功";
		try{
			tSSmsTemplateService.delete(tSSmsTemplate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息模本表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除消息模本表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "消息模本表删除成功";
		try{
			for(String id:ids.split(",")){
				TSSmsTemplateEntity tSSmsTemplate = systemService.getEntity(TSSmsTemplateEntity.class, 
				id
				);
				tSSmsTemplateService.delete(tSSmsTemplate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "消息模本表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加消息模本表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSSmsTemplateEntity tSSmsTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "消息模本表添加成功";
		Date datetime=new Date();
		HttpSession session = request.getSession();
		TSUser user=(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		String orgId=(String) session.getAttribute("orgId");
		try{
			tSSmsTemplate.setCrtTm(datetime);
			tSSmsTemplate.setCrtName(user.getRealName());
			tSSmsTemplate.setCrtCde(user.getUserName());
			tSSmsTemplate.setIssuDepartment(orgId);
			tSSmsTemplate.setIsvalid("1");
			tSSmsTemplateService.save(tSSmsTemplate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息模本表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新消息模本表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSSmsTemplateEntity tSSmsTemplate, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "消息模本表更新成功";
		Date datetime=new Date();
		HttpSession session = request.getSession();
		TSUser user=(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		TSSmsTemplateEntity t = tSSmsTemplateService.get(TSSmsTemplateEntity.class, tSSmsTemplate.getId());
		try {
			t.setUpdTm(datetime);
			t.setUpdCde(user.getUserName());
			MyBeanUtils.copyBeanNotNull2Bean(tSSmsTemplate, t);
			tSSmsTemplateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "消息模本表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 消息模本表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSSmsTemplateEntity tSSmsTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSSmsTemplate.getId())) {
			tSSmsTemplate = tSSmsTemplateService.getEntity(TSSmsTemplateEntity.class, tSSmsTemplate.getId());
			req.setAttribute("tSSmsTemplatePage", tSSmsTemplate);
		}
		return new ModelAndView("system/repair/sms/tSSmsTemplate-add");
	}
	/**
	 * 消息模本表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSSmsTemplateEntity tSSmsTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSSmsTemplate.getId())) {
			tSSmsTemplate = tSSmsTemplateService.getEntity(TSSmsTemplateEntity.class, tSSmsTemplate.getId());
			req.setAttribute("tSSmsTemplatePage", tSSmsTemplate);
		}
		return new ModelAndView("system/repair/sms/tSSmsTemplate-update");
	}
}
