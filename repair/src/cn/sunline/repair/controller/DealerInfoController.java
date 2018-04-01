package cn.sunline.repair.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import cn.sunline.core.beanvalidator.BeanValidators;
import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.model.json.AjaxJson;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.constant.Globals;
import cn.sunline.core.util.MyBeanUtils;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.AreaEntity;
import cn.sunline.repair.entity.CityEntity;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.entity.ProvinceEntity;
import cn.sunline.repair.service.DealerInfoServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;

/**
 * @Title: Controller
 * @Description: 车商信息维护
 * @author zhangdaihao
 * @date 2016-10-14 11:54:24
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/dealerInfoController")
public class DealerInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DealerInfoController.class);

	@Autowired
	private DealerInfoServiceI dealerInfoService;
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
		String brand=request.getParameter("brand");
		if(brand==null||brand.equals("")){
			return new ModelAndView("system/repair/dealer/dealerInfoList");
		}else{
			return new ModelAndView("system/repair/dealer/dealerInfoList1");
		}
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
	public void datagrid(DealerInfoEntity dealerInfo,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId = (String) session.getAttribute("orgId");
		departId=departId+"%";
		CriteriaQuery cq = new CriteriaQuery(DealerInfoEntity.class, dataGrid);
		cq.like("dptCde", departId);
		//cq.in("dptCde", obj);
		cq.add();
		// 查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,dealerInfo, request.getParameterMap());
		this.dealerInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * AJAX 下拉框 -省
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getprovince")
	@ResponseBody
	public AjaxJson getProvince(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			List<ProvinceEntity> list =  systemService.loadAll(ProvinceEntity.class);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(list);
			j.setMsg(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}


	/**
	 * AJAX 示例下拉框
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getcity")
	@ResponseBody
	public AjaxJson getDemo(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
//			String id = StringUtil.getEncodePra(req.getParameter("id"));

//			String hql = "from CityEntity where codetype='CityAdjust' and codecode like ?";

//			String province = id.substring(0, id.length() - 2);
//			Object[] para = { province + "%" };

//			List cityList = systemService.findHql(hql, para);

			String province = req.getParameter("province");
			 List<CityEntity> list = systemService.findByProperty(CityEntity.class, "father", province);

			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = "";

			jsonStr = mapper.writeValueAsString(list);
			j.setMsg(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * AJAX 示例下拉框
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getarea")
	@ResponseBody
	public AjaxJson getarea(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
//		String id = StringUtil.getEncodePra(req.getParameter("id"));

//		String hql = "from AreaEntity where codetype='DistrictCode' and codecode like ?";
//
//		String city = id.substring(0, id.length() - 2);
//		Object[] para = { city + "%" };
//
//		List areaList = systemService.findHql(hql, para);
		String city = req.getParameter("city");

		List<AreaEntity> list = systemService.findByProperty(AreaEntity.class, "father", city);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		j.setMsg(jsonStr);
		return j;
	}

	/**
	 * 删除车商信息维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		dealerInfo = systemService.getEntity(DealerInfoEntity.class,
				dealerInfo.getId());
		message = "车商信息维护删除成功";
		dealerInfoService.delete(dealerInfo);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加车商信息维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		Date datetime = new Date();
		HttpSession session = request.getSession();
		TSUser user = (TSUser) session
				.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			message = "车商信息维护更新成功";
			DealerInfoEntity t = dealerInfoService.get(DealerInfoEntity.class,
					dealerInfo.getId());
			t.setUpdTm(datetime);
			t.setUpdCde(user.getUserName());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dealerInfo, t);
				dealerInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "车商信息维护更新失败";
			}
		} else {
			message = "车商信息维护添加成功";
			dealerInfo.setCrtTm(datetime);
			dealerInfo.setCrtCde(user.getUserName());
			dealerInfoService.save(dealerInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 车商信息维护列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		String sql="select dealer_Code_sequence.nextval from dual";
		Object obj = systemService.findListbySql(sql);
		String dealerCode=obj.toString();
		dealerCode=dealerCode.replace("[", "");
		dealerCode=dealerCode.replace("]", "");
		Integer num=dealerCode.length();
		switch(num){
			case 1:
				dealerCode="000000000"+dealerCode;
				break;
			case 2:
				dealerCode="00000000"+dealerCode;
				break;
			case 3:
				dealerCode="0000000"+dealerCode;
				break;
			case 4:
				dealerCode="000000"+dealerCode;
				break;
			case 5:
				dealerCode="00000"+dealerCode;
				break;
			case 6:
				dealerCode="0000"+dealerCode;
				break;
			case 7:
				dealerCode="000"+dealerCode;
				break;
			case 8:
				dealerCode="00"+dealerCode;
				break;
			case 9:
				dealerCode="0"+dealerCode;
				break;
		}
		req.setAttribute("dealerCode", dealerCode);
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class,dealerInfo.getId());
			req.setAttribute("dealerInfoPage", dealerInfo);
		}
		return new ModelAndView("system/repair/dealer/dealerInfo_add");
	}

	/**
	 * 车商信息维护列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView addorupdate(DealerInfoEntity dealerInfo,HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
			req.setAttribute("dealerInfoPage", dealerInfo);
		}
		return new ModelAndView("system/repair/dealer/dealerInfo_update");
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<DealerInfoEntity> list() {
		List<DealerInfoEntity> listDealerInfos = dealerInfoService
				.getList(DealerInfoEntity.class);
		return listDealerInfos;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		DealerInfoEntity task = dealerInfoService.get(DealerInfoEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody DealerInfoEntity dealerInfo,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<DealerInfoEntity>> failures = validator
				.validate(dealerInfo);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		dealerInfoService.save(dealerInfo);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = dealerInfo.getId();
		URI uri = uriBuilder.path("/rest/dealerInfoController/" + id).build()
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody DealerInfoEntity dealerInfo) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<DealerInfoEntity>> failures = validator
				.validate(dealerInfo);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		dealerInfoService.saveOrUpdate(dealerInfo);

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		dealerInfoService.deleteEntityById(DealerInfoEntity.class, id);
	}
}
