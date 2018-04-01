package cn.sunline.repair.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.exception.BusinessException;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.model.json.AjaxJson;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.constant.Globals;
import cn.sunline.core.util.MyBeanUtils;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.PlyDealerInfoEntity;
import cn.sunline.repair.entity.QuestionEntity;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.repair.service.QuestionInfoServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSRole;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;

/**
 * @Title: Controller
 * @Description: 理赔车商信息维护
 * @author zhangdaihao
 * @date 2016-10-14 11:54:24
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/questionController")
public class QuestionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(QuestionController.class);

	@Autowired
	private QuestionInfoServiceI questionInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 车商信息维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		//获取系统当前用户
		HttpSession session = request.getSession();
		TSUser user = (TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		//获取当前机构
		String orgId=(String) session.getAttribute("orgId");
		String hql=" from TSRole where id in (select tsRole.id from TSUserOrg where tsUser.id='"+user.getId()+"' and tsDepart.id='"+orgId+"')";
		List<TSRole> roleList=systemService.findByQueryString(hql);
		String roleCode="";
		for (int i = 0; i < roleList.size(); i++) {
			TSRole role=roleList.get(i);
			if(role.getRoleCode()=="admin"||role.getRoleCode().equals("admin")){
				roleCode=role.getRoleCode();
			}
		}
		request.setAttribute("roleCode", roleCode);
		return new ModelAndView("system/repair/question/questionList");
		
	}
	
	@RequestMapping(params = "datagrid")
	public void datagrid(QuestionEntity question,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
	
		CriteriaQuery cq = new CriteriaQuery(QuestionEntity.class, dataGrid);
		cq.add();
		//查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, question, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//cq.add();
		this.questionInfoService.getDataGridReturn(cq, true);
		
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除消息模本表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(QuestionEntity question, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		question = systemService.getEntity(QuestionEntity.class, question.getId());
		message = "问题删除成功";
		try{
			questionInfoService.delete(question);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "问题删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(QuestionEntity question, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(question.getId())) {
			question = systemService.getEntity(QuestionEntity.class, question.getId());
			req.setAttribute("questionPage", question);
		}
		return new ModelAndView("system/repair/question/question");
	}
	/**
	 * 添加问题
	 * @描述: TODO
	 * @参数： @param question
	 * @参数： @param request
	 * @参数： @return   
	 * @返回类型： AjaxJson  
	 * @作者： 张永祥
	 * @日期： 2016年11月21日
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(QuestionEntity question, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "问题添加成功";
		try{
			questionInfoService.save(question);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "问题添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(QuestionEntity question, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(question.getId())) {
			question = questionInfoService.getEntity(QuestionEntity.class, question.getId());
			req.setAttribute("questionPage", question);
		}
		return new ModelAndView("system/repair/question/questionUpdate");
	}
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(QuestionEntity question, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		//QuestionEntity t = questionInfoService.get(QuestionEntity.class, question.getId());
		try {
			
			questionInfoService.saveOrUpdate(question);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
//	@RequestMapping(params = "addContent")
//	public ModelAndView addContent(HttpServletRequest request) {
//		String id=request.getParameter("id");
//		String questionName=StringUtil.getEncodePra(request.getParameter("questionName"));
//		String questionContent=StringUtil.getEncodePra(request.getParameter("questionContent"));
//		request.setAttribute("id", id);
//		request.setAttribute("questionName", questionName);
//		request.setAttribute("questionContent", questionContent);
//		String hql=" from AnswerEntity where questionId='"+id+"'";
//		List<AnswerEntity> aeList=systemService.findByQueryString(hql);
//		request.setAttribute("aeList", aeList);
//		return new ModelAndView("system/repair/question/content");
//		
//	}
//	@RequestMapping(params = "doAddContent")
//	@ResponseBody
//	public AjaxJson doAddContent(AnswerEntity answer, HttpServletRequest request) {
//		String questionId=request.getParameter("questionId");
//		String answerName=StringUtil.getEncodePra(request.getParameter("answerName"));
//		answer.setQuestionId(questionId);
//		answer.setAnswerName(answerName);
//		String message = null;
//		AjaxJson j = new AjaxJson();
//		message = "消息模本表添加成功";
//		try{
//			questionInfoService.save(answer);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "消息模本表添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
}
