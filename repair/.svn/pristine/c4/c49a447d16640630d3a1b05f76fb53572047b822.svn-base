package cn.sunline.repair.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import cn.sunline.repair.entity.BrandEntity;
import cn.sunline.repair.entity.ClmPlyDealerEntity;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.entity.MsageEntity;
import cn.sunline.repair.entity.OpenOrgEntity;
import cn.sunline.repair.entity.PlyDealerInfoEntity;
import cn.sunline.repair.service.PlyDealerInfoServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSDepart;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;


@Controller
@RequestMapping("/openOrgController")
public class OpenOrgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OpenOrgController.class);

	@Autowired
	private SystemService systemService;

	/**
	 * 车商信息维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/repair/openOrg/openOrgList");
		
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
	public void datagrid(OpenOrgEntity openOrg,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		CriteriaQuery cq = new CriteriaQuery(OpenOrgEntity.class, dataGrid);
		// 查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,openOrg, request.getParameterMap());
		cq.eq("orgLevel","2");
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(OpenOrgEntity openOrg, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		OpenOrgEntity ooe=systemService.findUniqueByProperty(OpenOrgEntity.class, "orgCode", openOrg.getOrgCode());
		if(ooe!=null){
			message = "该机构已上线！";
		} else {
			message = "已上线机构添加成功";
			TSDepart tsd=systemService.get(TSDepart.class, openOrg.getOrgCode());
			OpenOrgEntity eo=new OpenOrgEntity();
			eo.setCreateTime(new Date());
			eo.setOrgCode(tsd.getId());
			eo.setOrgName(tsd.getDepartname());
			eo.setOrgLevel(tsd.getCompanylevel());
			systemService.save(eo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			String hql=" from TSDepart where id like '"+openOrg.getOrgCode()+"%' and companylevel<>'2'";
			List<TSDepart> tsdList=systemService.findByQueryString(hql);
		
			if(tsdList.size()>0&&tsdList!=null){
				for (int i = 0; i < tsdList.size(); i++) {
					TSDepart depart=tsdList.get(i);
					OpenOrgEntity o=new OpenOrgEntity();
					o.setOrgCode(depart.getId());
					o.setCreateTime(new Date());
					o.setOrgName(depart.getDepartname());
					o.setOrgLevel(depart.getCompanylevel());
					systemService.save(o);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 
	 * @描述: 删除上线机构维护
	 * @参数： @param brand
	 * @参数： @param request
	 * @参数： @return   
	 * @返回类型： AjaxJson  
	 * @作者： 张永祥
	 * @日期： 2016年12月7日
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(OpenOrgEntity openOrg, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//openOrg = systemService.getEntity(OpenOrgEntity.class, openOrg.getId());
		//查询选中机构及其下属机构
		message = "已上线机构删除成功";
		OpenOrgEntity o=systemService.findUniqueByProperty(OpenOrgEntity.class, "orgCode",openOrg.getOrgCode() );
		systemService.delete(o);
		String hql=" from OpenOrgEntity where orgCode like '"+openOrg.getOrgCode()+"%' and orgLevel <>'2'";
		List<OpenOrgEntity> ooeList=systemService.findByQueryString(hql);
		if(ooeList!=null&&ooeList.size()>0){
			for (int k = 0; k < ooeList.size(); k++) {
				systemService.delete(ooeList.get(k));
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}		
		j.setMsg(message);
		return j;
	}
	/**
	 * 发送短信角色维护列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(OpenOrgEntity openOrg, HttpServletRequest req) {
		
		if (StringUtil.isNotEmpty(openOrg.getId())) {
			openOrg = systemService.getEntity(OpenOrgEntity.class, openOrg.getId());
		
			req.setAttribute("openOrgPage", openOrg);
		}
		return new ModelAndView("system/repair/openOrg/openOrg");
	}
	/**
	 * 查询二级机构
	 * @描述: TODO
	 * @参数： @param request
	 * @参数： @return   
	 * @返回类型： ModelAndView  
	 * @作者： 张永祥
	 * @日期： 2016年12月7日
	 */
	@RequestMapping(params = "orgList")
	public ModelAndView orgList(HttpServletRequest request) {
		return new ModelAndView("system/repair/openOrg/orgList");
	}
	@RequestMapping(params = "orgDatagrid")
	public void orgDatagrid(TSDepart depart,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,depart, request.getParameterMap());
		cq.eq("TSPDepart.id", "02");
		// 查询条件组装器
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
