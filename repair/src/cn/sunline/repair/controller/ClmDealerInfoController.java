package cn.sunline.repair.controller;



import java.util.ArrayList;
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
import cn.sunline.repair.entity.ClmDealerInfoEntity;
import cn.sunline.repair.entity.ClmPlyDealerEntity;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.entity.PlyDealerInfoEntity;
import cn.sunline.repair.service.ClmDealerInfoServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
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
@RequestMapping("/clmDealerInfoController")
public class ClmDealerInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ClmDealerInfoController.class);

	@Autowired
	private ClmDealerInfoServiceI clmDealerInfoService;
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
		return new ModelAndView("system/repair/clm_ply_dealer/clmDealerInfoList");
		
	}
	@RequestMapping(params = "list1")
	public ModelAndView list1(HttpServletRequest request) {
		String plyDealerCode=request.getParameter("plyDealerCode");
		request.setAttribute("plyDealerCode", plyDealerCode);
		return new ModelAndView("system/repair/clm_ply_dealer/clmDealerInfoList1");
		
	}
	@RequestMapping(params = "queryList")
	public ModelAndView queryList(HttpServletRequest request) {
		String plyDealerCode=request.getParameter("plyDealerCode");
		StringBuffer sb=new StringBuffer();
		sb.append("select a.dealer_Code,a.Dealer_Name,a.Dealer_Address,a.Contacts_Name, ");
		sb.append("a.Contacts_Tel,a.Director_Name,a.Director_Tel,a.AGENT,a.Agent_Tel,a.Dealer_Type,a.ISVALID");
		sb.append(" from rp_dealer_maintenance_clm a,RP_DEALER_CLM_PLY b,rp_dealer_maintenance_ply  c");
		sb.append(" where a.Dealer_Code=b.clm_dealer_code");
		sb.append(" and b.ply_dealer_code=c.Dealer_Code");
		sb.append(" and c.Dealer_Code='").append(plyDealerCode).append("'");
		List<Object> List=systemService.findListbySql(sb.toString());
		List<ClmDealerInfoEntity> clmList=new ArrayList<ClmDealerInfoEntity>(); //实体类
		for (int i = 0; i < List.size(); i++) {
			ClmDealerInfoEntity pde=new ClmDealerInfoEntity();
			Object[] objects=(Object[]) List.get(i);
			if(objects.length>0){
				if(objects[0]!=null){
					
					pde.setDealerCode(objects[0].toString());
				}
				if(objects[1]!=null){
					pde.setDealerName(objects[1].toString());				
									
				}
				if(objects[2]!=null){
					pde.setDealerAddress(objects[2].toString());
					
				}
				if(objects[3]!=null){
					pde.setContactsName(objects[3].toString());
					
				}
				if(objects[4]!=null){
					pde.setContactsTel(objects[4].toString());
				}
				if(objects[5]!=null){
					pde.setDirectorName(objects[5].toString());
				}
				if(objects[6]!=null){
					pde.setDirectorTel(objects[6].toString());
				}
				if(objects[7]!=null){
					pde.setAgent(objects[7].toString());
				}
				if(objects[8]!=null){
					pde.setAgentTel(objects[8].toString());
				}
				if(objects[9]!=null){
					pde.setDealerType(objects[9].toString());
				}
				if(objects[10]!=null){
					pde.setIsvalid(objects[10].toString());	
				}
				
				
				clmList.add(pde);
			}
		}
		request.setAttribute("clmList", clmList);
		return new ModelAndView("system/repair/clm_ply_dealer/queryPlyDealerInfoList");
		
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
	public void datagrid(ClmDealerInfoEntity dealerInfo,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId = (String) session.getAttribute("orgId");
		departId=departId+"%";
		CriteriaQuery cq = new CriteriaQuery(ClmDealerInfoEntity.class, dataGrid);
		cq.like("dptCde", departId);
		cq.add();
		// 查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,dealerInfo, request.getParameterMap());
		this.clmDealerInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(String plyDealerCode,String clmDealerCodes, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String clmDealerCodess=clmDealerCodes.replace(",", "','");
		clmDealerCodess="'"+clmDealerCodess+"'";
		message = "关联成功";
		try{
			String hql=" from ClmPlyDealerEntity where plyDealerCode='"+plyDealerCode+"' and  clmDealerCode in("+clmDealerCodess+") order by clmDealerCode";
			List<ClmPlyDealerEntity> cpdeList=systemService.findByQueryString(hql);
			List<String> str1=new ArrayList<String>();
			List<String> str2=new ArrayList<String>();
			if(cpdeList.size()>0&&cpdeList!=null){
				for (int i = 0; i < cpdeList.size(); i++) {
					str1.add(cpdeList.get(i).getclmDealerCode());
				}
				for(String clmDealerCode:clmDealerCodess.split(",")){
					str2.add(clmDealerCode);
				}
				str2.removeAll(str1);
			  for (int i = 0; i < str2.size(); i++) {
				  ClmPlyDealerEntity cpde=new ClmPlyDealerEntity();
				  cpde.setclmDealerCode(str2.get(i));
				  cpde.setplyDealerCode(plyDealerCode);
				  systemService.save(cpde);
				  systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				
			}
			}else{
				for(String clmDealerCode:clmDealerCodes.split(",")){
					ClmPlyDealerEntity cpde=new ClmPlyDealerEntity();
					cpde.setclmDealerCode(clmDealerCode);
					cpde.setplyDealerCode(plyDealerCode);
					systemService.save(cpde);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			message = "关联失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
