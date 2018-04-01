package cn.sunline.repair.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import cn.sunline.repair.entity.BrandEntity;
import cn.sunline.repair.service.BrandServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;




/**   
 * @Title: Controller
 * @Description: 车辆品牌信息维护
 * @author zhangdaihao
 * @date 2016-10-13 17:22:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/brandController")
public class BrandController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrandController.class);

	@Autowired
	private BrandServiceI brandService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 车辆品牌信息维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/repair/brand/brandList");
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
	public void datagrid(BrandEntity brand,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId=(String) session.getAttribute("orgId");
		departId=departId+"%";
		CriteriaQuery cq = new CriteriaQuery(BrandEntity.class, dataGrid);
		cq.like("dptCde", departId);
		//cq.in("dptCde",obj);
		//查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brand, request.getParameterMap());
		this.brandService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除车辆品牌信息维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrandEntity brand, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		brand = systemService.getEntity(BrandEntity.class, brand.getId());
		message = "车辆品牌信息维护删除成功";
		brandService.delete(brand);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加车辆品牌信息维护
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "save")
//	@ResponseBody
//	public AjaxJson save(BrandEntity brand, HttpServletRequest request) {
//		String message = null;
//		AjaxJson j = new AjaxJson();
//		HttpSession session = request.getSession();
//		TSUser user=(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
//		String brandCodes[]=brand.getBrandCode().split(",");
//		String brandNames[]=brand.getBrandName().split(",");
//		if (StringUtil.isNotEmpty(brand.getId())&&brandCodes.length==1&&brandNames.length==1) {
//			StringBuffer sb=new StringBuffer();
//			sb.append(" from BrandEntity where 1=1");
//			sb.append(" and dealerCode ='").append(brand.getDealerCode()).append("'");
//			sb.append(" and dptCde='").append(brand.getDptCde()).append("'");
//			sb.append(" and brandCode ='").append(brandCodes[0]).append("'");
//			BrandEntity a=systemService.singleResult(sb.toString());
//			if(a==null){
//				message = "车辆品牌信息维护更新成功";
//				BrandEntity t = brandService.get(BrandEntity.class, brand.getId());
//				try {
//					t.setUpdTm(new Date());
//					t.setUpdCde(user.getUserName());
//					MyBeanUtils.copyBeanNotNull2Bean(brand, t);
//					brandService.saveOrUpdate(t);
//					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//				} catch (Exception e) {
//					e.printStackTrace();
//					message = "车辆品牌信息维护更新失败";
//				}
//			}else{
//				message = "该车商已维护品牌编码为"+brandCodes[0]+"品牌，请勿重复维护";
//			}
//		}
//		else {
////			String brandCode="";
////			brandCode=brand.getBrandCode();
////			brandCode="'"+brandCode+"'";
////			brandCode=brandCode.replaceAll(",", "','");
//		
//			for (int i = 0; i < brandCodes.length; i++) {
//				StringBuffer sb=new StringBuffer();
//				sb.append(" from BrandEntity where 1=1");
//				sb.append(" and dealerCode ='").append(brand.getDealerCode()).append("'");
//				sb.append(" and dptCde='").append(brand.getDptCde()).append("'");
//				sb.append(" and brandCode ='").append(brandCodes[i]).append("'");
//				BrandEntity a=systemService.singleResult(sb.toString());
//				if(a==null){
//					message = "车辆品牌信息维护添加成功";
//					try {
//						BrandEntity be =new BrandEntity();
//						be.setBrandCode(brandCodes[i]);
//						be.setBrandName(brandNames[i]);
//						be.setCrtCde(user.getUserName());
//						be.setCrtTm(new Date());
//						be.setDealerCode(brand.getDealerCode());
//						be.setDealerName(brand.getDealerName());
//						be.setDptCde(brand.getDptCde());
//						be.setRatio(brand.getRatio());
//						be.setWeight(brand.getWeight());
//						brandService.save(be);
//					} catch (Exception e) {
//						e.printStackTrace();
//						message = "车辆品牌信息维护添加失败";
//					}
//					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//				}else{
//					message = "车辆品牌信息维护更新成功";
//					try {
//						String id=a.getId();
//						a.setUpdTm(new Date());
//						a.setUpdCde(user.getUserName());
//						MyBeanUtils.copyBeanNotNull2Bean(brand, a);
//						a.setBrandCode(brandCodes[i]);
//						a.setBrandName(brandNames[i]);
//						a.setId(id);
//						brandService.saveOrUpdate(a);
//						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//					} catch (Exception e) {
//						e.printStackTrace();
//						message = "车辆品牌信息维护更新失败";
//					}
//				}
//				
//			}
//		}
//		j.setMsg(message);
//		return j;
//	}
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrandEntity brand, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		HttpSession session = request.getSession();
		TSUser user=(TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if (StringUtil.isNotEmpty(brand.getId())) {
			message = "车辆品牌信息维护更新成功";
			BrandEntity t = brandService.get(BrandEntity.class, brand.getId());
			try {
				t.setUpdTm(new Date());
				t.setUpdCde(user.getUserName());
				MyBeanUtils.copyBeanNotNull2Bean(brand, t);
				brandService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "车辆品牌信息维护更新失败";
			}
		} else {
			message = "车辆品牌信息维护添加成功";
			String brandCodes[]=brand.getBrandCode().split(",");
			String brandNames[]=brand.getBrandName().split(",");
			System.out.println(brandCodes.length);
			for (int i = 0; i <brandCodes.length; i++) {
				BrandEntity be =new BrandEntity();
				be.setBrandCode(brandCodes[i]);
				be.setBrandName(brandNames[i]);
				be.setCrtCde(user.getUserName());
				be.setCrtTm(new Date());
				be.setDealerCode(brand.getDealerCode());
				be.setDealerName(brand.getDealerName());
				be.setDptCde(brand.getDptCde());
				be.setRatio(brand.getRatio());
				be.setWeight(brand.getWeight());
				brandService.save(be);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 车辆品牌信息维护列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(BrandEntity brand, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String departId=(String) session.getAttribute("orgId");
		StringBuffer sb=new StringBuffer();
		sb.append("select ID from T_S_DEPART where 1=1");
		sb.append(" and ID='").append(departId).append("'");
		sb.append(" or PARENTDEPARTID like'").append(departId).append("%'");
		List<Object> departList=systemService.findListbySql(sb.toString());
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(brand.getId())) {
			brand = brandService.getEntity(BrandEntity.class, brand.getId());
			req.setAttribute("brandPage", brand);
		}
		return new ModelAndView("system/repair/brand/brandAdd");
	}
	@RequestMapping(params = "update")
	public ModelAndView update(BrandEntity brand, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String departId=(String) session.getAttribute("orgId");
		StringBuffer sb=new StringBuffer();
		sb.append("select ID from T_S_DEPART where 1=1");
		sb.append(" and ID='").append(departId).append("'");
		sb.append(" or PARENTDEPARTID like'").append(departId).append("%'");
		List<Object> departList=systemService.findListbySql(sb.toString());
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(brand.getId())) {
			brand = brandService.getEntity(BrandEntity.class, brand.getId());
			req.setAttribute("brandPage", brand);
		}
		return new ModelAndView("system/repair/brand/brandUpdate");
	}
	
}
