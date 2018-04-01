package cn.sunline.repair.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.model.json.AjaxJson;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.constant.Globals;
import cn.sunline.core.util.MyBeanUtils;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.entity.MsageEntity;
import cn.sunline.repair.service.MsageServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSDepart;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;


/**   
 * @Title: Controller
 * @Description: 发送短信角色维护
 * @author zhangdaihao
 * @date 2016-10-14 09:43:50
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/msageController")
public class MsageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MsageController.class);

	@Autowired
	private MsageServiceI msageService;
	@Autowired
	private SystemService systemService;
	/**
	 * 发送短信角色维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/repair/msage/msageList");
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
	public void datagrid(MsageEntity msage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId=(String) session.getAttribute("orgId");
		departId=departId+"%";
		//根据当前机构及下属机构查询发送短信角色维护信息
		CriteriaQuery cq = new CriteriaQuery(MsageEntity.class, dataGrid);
		cq.like("dptCde", departId);
		//查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, msage, request.getParameterMap());
		this.msageService.getDataGridReturn(cq, true);
		
		//查询当前机构及下属机构下的所有车商信息
		
		List<MsageEntity> cfeList = new ArrayList<MsageEntity>();
		StringBuffer sbDptCde=new StringBuffer();
		StringBuffer sbDealerNo=new StringBuffer();
    	for (int i = 0; i < dataGrid.getResults().size(); i++) {
    		int num=dataGrid.getResults().size()-1;
			if(dataGrid.getResults().get(i) instanceof MsageEntity){
				MsageEntity me = (MsageEntity) dataGrid.getResults().get(i);
				sbDptCde.append("'").append(me.getDptCde()).append("'");
				sbDealerNo.append("'").append(me.getDealerNo()).append("'");
				if(i!=num){
					sbDptCde.append(",");
					sbDealerNo.append(",");
				}
			}
        }
    	 String hql="";
    	 if("".equals(sbDptCde.toString())){
    		 hql=" from TSDepart where id in('')";
    	 }else{
    		 hql=" from TSDepart where id in("+sbDptCde.toString()+")";
    	 }
        List<TSDepart> tsdList=systemService.findByQueryString(hql);
        for (Object o : dataGrid.getResults()) {
        	  if (o instanceof MsageEntity) {
        		  MsageEntity me = (MsageEntity) o;
        			for (int i = 0; i < tsdList.size(); i++) {
            			TSDepart tsd=tsdList.get(i);
            		  if (me.getDptCde()== tsd.getId() ||  tsd.getId().equals(me.getDptCde())) {
                          me.setDptName(tsd.getDepartname());
                          break;
            		  }
        		}
            	cfeList.add(me);
        	  }
        }

		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除发送短信角色维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MsageEntity msage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		msage = systemService.getEntity(MsageEntity.class, msage.getId());
		message = "发送短信角色维护删除成功";
		msageService.delete(msage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加发送短信角色维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MsageEntity msage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		HttpSession session = request.getSession();
		TSUser user=(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if (StringUtil.isNotEmpty(msage.getId())) {
			message = "发送短信角色维护更新成功";
			MsageEntity t = msageService.get(MsageEntity.class, msage.getId());
			try {
				t.setUpdTm(new Date());
				t.setUpdCde(user.getUserName());
				MyBeanUtils.copyBeanNotNull2Bean(msage, t);
				msageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "发送短信角色维护更新失败";
			}
		} else {
			message = "发送短信角色维护添加成功";
			msage.setCrtTm(new Date());
			msage.setCrtCde(user.getUserName());
			msageService.save(msage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
	public ModelAndView addorupdate(MsageEntity msage, HttpServletRequest req) {
		
		if (StringUtil.isNotEmpty(msage.getId())) {
			msage = msageService.getEntity(MsageEntity.class, msage.getId());
			TSDepart tsd=systemService.get(TSDepart.class, msage.getDptCde());
			msage.setOrgName(tsd.getDepartname());
			DealerInfoEntity die=systemService.findUniqueByProperty(DealerInfoEntity.class,"dealerCode",msage.getDealerNo());
			if(die!=null){
				msage.setDealerName(die.getDealerName());
			}
			req.setAttribute("msagePage", msage);
		}
		return new ModelAndView("system/repair/msage/msage");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<MsageEntity> list() {
		List<MsageEntity> listMsages=msageService.getList(MsageEntity.class);
		return listMsages;
	}

	
//	@RequestMapping(params="updateRule", produces="application/json;charset=UTF-8")
	@RequestMapping(params="updateRule" )
	@ResponseBody
	public AjaxJson updateRule(@RequestBody MsageEntity[] obj) {
		AjaxJson j = new AjaxJson();
		try {
			if(obj != null && obj.length > 0 ){
				for(int i =0 ; i < obj.length ; i ++ ){
					obj[i].setUpdTm(new Date());
					obj[i].setUpdCde(ResourceUtil.getSessionUserName().getUserName());
					systemService.saveOrUpdate(obj[i]);									
				}
			}
		} catch (Exception e) {
			j.setMsg("更新失败" + e.getMessage());
			e.printStackTrace();
		}
		j.setMsg("更新成功");
		return j;
	}
	
}
