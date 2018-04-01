package cn.sunline.repair.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import cn.sunline.core.beanvalidator.BeanValidators;
import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.hibernate.qbc.HqlQuery;
import cn.sunline.core.common.hibernate.qbc.PageList;
import cn.sunline.core.common.model.json.AjaxJson;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.constant.Globals;
import cn.sunline.core.util.MyBeanUtils;
import cn.sunline.core.util.ResourceUtil;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.DealerInfoEntity;
import cn.sunline.repair.service.MonitorServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.tag.vo.datatable.SortDirection;
import cn.sunline.web.system.pojo.base.TSUser;
import cn.sunline.web.system.service.SystemService;
import cn.sunline.ws.foreignInterface.dao.IRepairMainDao;
import cn.sunline.ws.foreignInterface.entity.RepairDealer;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.foreignInterface.entity.RepairStatistics;
import cn.sunline.ws.message.service.impl.MessageServiceImpl;
import cn.sunline.ws.message.vo.MessageStateVo;




/**   
 * @Title: Controller
 * @Description: 派修案件查询
 * @author zhangdaihao
 * @date 2016-10-18 10:35:35
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/monitorController")
public class MonitorController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MonitorController.class);

	@Autowired
	private MonitorServiceI monitorService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	@Autowired
	private IRepairMainDao repairMainDao;


	/**
	 * 派修案件查询列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/repair/monitor/monitorList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws ParseException 
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RepairMainInfo repairMain ,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws ParseException {
		HttpSession session = request.getSession();
		String dptCde = (String) session.getAttribute("orgId");
		dptCde = dptCde + "%";
		CriteriaQuery cq = new CriteriaQuery(RepairMainInfo.class, dataGrid);
		cq.like("claimDepartment",dptCde);
		cq.addOrder("crtTm", SortDirection.desc);
		if((repairMain.getEndTime()== null||"".equals(repairMain.getEndTime()))&&
				(repairMain.getStartTime()==null)||"".equals(repairMain.getStartTime())){
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(new Date());//把当前时间赋给日历
			calendar.add(Calendar.MONTH, -1);  //设置为1月 
			dBefore = calendar.getTime();   //得到前1月的时间
			cq.ge("crtTm", dBefore);
		}
		if (repairMain.getStartTime() != null&& !"".equals(repairMain.getStartTime())) {
			Date startDate= repairMain.getStartTime();
			cq.ge("crtTm",startDate);
			repairMain.setStartTime(null);
		}
		if(repairMain.getEndTime()!= null&& !"".equals(repairMain.getEndTime())){
			Date endDate=repairMain.getEndTime();
			cq.le("crtTm", endDate);
			repairMain.setEndTime(null);
		}
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,repairMain, request.getParameterMap());
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		//查询所有的车商代码
		String hql=" from DealerInfoEntity";
		List<DealerInfoEntity> deList=systemService.findByQueryString(hql);
		List<RepairMainInfo> cfeList = new ArrayList<RepairMainInfo>();
		for (Object o : dataGrid.getResults()) {	
			RepairMainInfo rd= (RepairMainInfo) o;
			for (int i = 0; i < deList.size(); i++) {
				DealerInfoEntity die=deList.get(i);
				if(rd.getRepairNo()!=null){
					if(rd.getRepairNo()==die.getDealerCode()||rd.getRepairNo().equals(die.getDealerCode())){
						rd.setRepairName(die.getDealerName());//推荐车商名称
					}
				}
				if(rd.getDealerNo()!=null){					
					if(rd.getDealerNo()==die.getDealerCode()||rd.getDealerNo().equals(die.getDealerCode())){
						rd.setDealerName(die.getDealerName());//实际车商名称
					}
				}
				cfeList.add(rd);
			}

		}
		TagUtil.datagrid(response, dataGrid);
		
//		StringBuffer sbhql=new StringBuffer();
//		sbhql.append("select count(a.id)");
//		sbhql.append(" from RP_MAIN_INFO a,RP_DEALER_INFO b");
//		sbhql.append("  where a.REPORT_NO=b.REPORT_NO(+) and a.FRM_NO=b.FRM_NO(+)");
//		if(repairMain.getReportNo()!=null&&!"".equals(repairMain.getReportNo())){
//			sbhql.append(" and a.report_No like '%").append(repairMain.getReportNo()).append("%'");
//		}
//		if(repairMain.getRepairSerialNo()!=null&&!"".equals(repairMain.getRepairSerialNo())){
//			sbhql.append(" and a.REPAIR_SERIAL_NO='").append(repairMain.getRepairSerialNo()).append("'");
//		}
//		if(repairMain.getPlateNo()!=null&&!"".equals(repairMain.getPlateNo())){
//			sbhql.append(" and a.Plate_No like '%").append(repairMain.getPlateNo()).append("%'");
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if (repairMain.getStartDate() != null&& !"".equals(repairMain.getStartDate())) {
//			sbhql.append(" and a.crt_tm>=to_date('").append(repairMain.getStartDate() ).append("','yyyy-mm-dd')");
//		}
//		if(repairMain.getEndDate()!= null&& !"".equals(repairMain.getEndDate())){
//			sbhql.append(" and a.crt_tm<=to_date('").append(repairMain.getEndDate()).append("','yyyy-mm-dd')");
//		}
//		if((repairMain.getEndDate()== null||"".equals(repairMain.getEndDate()))&&
//				(repairMain.getStartDate()==null)||"".equals(repairMain.getStartDate())){
//			Date dBefore = new Date();
//			Calendar calendar = Calendar.getInstance(); //得到日历
//			calendar.setTime(new Date());//把当前时间赋给日历
//			calendar.add(Calendar.MONTH, -1);  //设置为1月 
//			dBefore = calendar.getTime();   //得到前1月的时间
//			sbhql.append(" and to_char(a.crt_tm,'yyyy-MM-dd')>='").append(sdf.format(dBefore)).append("'");
//		}
//		sbhql.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
//		Long count=systemService.getCountForJdbc(sbhql.toString());
//		dataGrid.setTotal(count.intValue());
//		int number1=(dataGrid.getPage()-1)*dataGrid.getRows();
//		int number2=dataGrid.getPage()*dataGrid.getRows();
//		StringBuffer sbHqlList=new StringBuffer();
//		sbHqlList.append("select * from (");
//		sbHqlList.append("select 1 as id,a.report_No reportNo,a.REPAIR_SERIAL_NO repairSerialNo,a.plate_No plateNo,");
//		sbHqlList.append("a.frm_No frmNo,a.VEHICLE_TYPE,a.repair_No repairNo,b.dealer_Name dealerName,");
//		sbHqlList.append("a.reporter_Name reporterName,a.REPOR_TM reportTm,a.surveyor_Name surveyorName,a.assess_Name assessName,b.dealer_People dealerPeople,b.NUCLEAR_DAMAGE_NAME nuclearDamageName,a.REPAIR_STATE,rownum rowno");
//		sbHqlList.append(",sysdate startDate,sysdate endDate from RP_MAIN_INFO a,RP_DEALER_INFO b");
//		sbHqlList.append("  where a.REPORT_NO=b.REPORT_NO(+) and a.FRM_NO=b.FRM_NO(+)");
//		if(repairMain.getReportNo()!=null&&!"".equals(repairMain.getReportNo())){
//			sbHqlList.append(" and a.report_No like '%").append(repairMain.getReportNo()).append("%'");
//		}
//		if(repairMain.getRepairSerialNo()!=null&&!"".equals(repairMain.getRepairSerialNo())){
//			sbHqlList.append(" and a.REPAIR_SERIAL_NO='").append(repairMain.getRepairSerialNo()).append("'");
//		}
//		if(repairMain.getPlateNo()!=null&&!"".equals(repairMain.getPlateNo())){
//			sbHqlList.append(" and a.Plate_No like '%").append(repairMain.getPlateNo()).append("%'");
//		}
//		if (repairMain.getStartDate() != null&& !"".equals(repairMain.getStartDate())) {
//			sbHqlList.append(" and a.crt_tm>=to_date('").append(repairMain.getStartDate() ).append("','yyyy-mm-dd')");
//		}
//		if(repairMain.getEndDate()!= null&& !"".equals(repairMain.getEndDate())){
//			sbHqlList.append(" and a.crt_tm<=to_date('").append(repairMain.getEndDate()).append("','yyyy-mm-dd')");
//		}
//		if((repairMain.getEndDate()== null||"".equals(repairMain.getEndDate()))&&
//				(repairMain.getStartDate()==null)||"".equals(repairMain.getStartDate())){
//			Date dBefore = new Date();
//			Calendar calendar = Calendar.getInstance(); //得到日历
//			calendar.setTime(new Date());//把当前时间赋给日历
//			calendar.add(Calendar.MONTH, -1);  //设置为1月 
//			dBefore = calendar.getTime();   //得到前1月的时间
//			sbHqlList.append(" and to_char(a.crt_tm,'yyyy-MM-dd')>='").append(sdf.format(dBefore)).append("'");
//		}
//		sbHqlList.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
//		sbHqlList.append(" and rownum<=").append(number2);
//		sbHqlList.append(") c where c.rowno>").append(number1);
//		HqlQuery hq=new HqlQuery(RepairDealer.class,sbHqlList.toString(),dataGrid);
//		PageList rdList=systemService.getPageListBySql(hq, true);
//		List<RepairDealer> objList1=rdList.getResultList();
//		dataGrid.setResults(objList1);
	}

	/**
	 * 删除派修案件查询
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RepairMainInfo repairMain, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		repairMain = systemService.getEntity(MessageStateVo.class, repairMain.getId());
		message = "派修案件查询删除成功";
		monitorService.delete(repairMain);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加派修案件查询
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MessageStateVo monitor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(monitor.getId())) {
			message = "派修案件查询更新成功";
			MessageStateVo t = monitorService.get(MessageStateVo.class, monitor.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(monitor, t);
				monitorService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "派修案件查询更新失败";
			}
		} else {
			message = "派修案件查询添加成功";
			monitorService.save(monitor);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 派修案件查询列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MessageStateVo monitor, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String departId=(String) session.getAttribute("orgId");
		StringBuffer sb=new StringBuffer();
		sb.append("select ID from T_S_DEPART where 1=1");
		sb.append(" and ID='").append(departId).append("'");
		sb.append(" or PARENTDEPARTID='").append(departId).append("'");
		List<Object> departList=systemService.findListbySql(sb.toString());
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(monitor.getId())) {
			monitor = monitorService.getEntity(MessageStateVo.class, monitor.getId());
			req.setAttribute("monitorPage", monitor);
		}
		return new ModelAndView("system/repair/monitor/monitor");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<MessageStateVo> list() {
		List<MessageStateVo> listMonitors=monitorService.getList(MessageStateVo.class);
		return listMonitors;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		MessageStateVo task = monitorService.get(MessageStateVo.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody MessageStateVo monitor, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MessageStateVo>> failures = validator.validate(monitor);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		monitorService.save(monitor);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = monitor.getId();
		URI uri = uriBuilder.path("/rest/monitorController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody MessageStateVo monitor) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MessageStateVo>> failures = validator.validate(monitor);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		monitorService.saveOrUpdate(monitor);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		monitorService.deleteEntityById(MessageStateVo.class, id);
	}
	 @RequestMapping(params = "query")
	public ModelAndView query(MessageStateVo monitor, @RequestParam String id, HttpServletRequest request) {
	 	String hql=" from MessageStateVo where repairSerialNo='"+id+"'";
	 	List<MessageStateVo> msList=systemService.findByQueryString(hql);
	 	request.setAttribute("msList", msList);
		return new ModelAndView("system/repair/monitor/msgState");
	}
	//重新发送短信
	@RequestMapping(params = "sendMsg")
	@ResponseBody
	public AjaxJson sendMsg(HttpServletRequest request) throws RemoteException, ServiceException {
		//获取系统当前用户
		HttpSession session = request.getSession();
		TSUser user = (TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		AjaxJson j = new AjaxJson();
		String contect = StringUtil.getEncodePra(request.getParameter("contect"));
		String phoneNumber=request.getParameter("phoneCode");
		String id=request.getParameter("id");
		String Reflag=messageServiceImpl.sendSMS(phoneNumber, contect);
		String message = null;
		String msg="";
		//根据id查询要修改的数据
		MessageStateVo mse=systemService.get(MessageStateVo.class, id);
		if(Reflag=="0"||Reflag.equals("0")){
			msg = "短信状态更新失败！";
			mse.setState("0");
			mse.setUpdCde(user.getId());
			mse.setUpdTm(new Date());
			try {
				systemService.saveOrUpdate(mse);
				systemService.addLog(msg, Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				msg = "短信状态更新成功！";
			}
			message="短信发送成功！";
		}else{
			msg = "短信状态更新成功！";
			mse.setState("1");
			mse.setUpdCde(user.getId());
			mse.setUpdTm(new Date());
			try {
				systemService.saveOrUpdate(mse);
				systemService.addLog(msg, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				msg = "短信状态更新失败！";

			}
			message="短信发送失败！";
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 
	 * @描述: 统计查询跳转页面
	 * @参数： @param request
	 * @参数： @return   
	 * @返回类型： ModelAndView  
	 * @作者： 张永祥
	 * @日期： 2016年11月18日
	 */
	@RequestMapping(params = "statisticsList")
	public ModelAndView statisticsList(HttpServletRequest request) {
		return new ModelAndView("system/repair/monitor/statisticsList");
	}
	@RequestMapping(params = "statisticsDatagrid")
	public void statisticsDatagrid(RepairStatistics repairStatistics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String departId=(String) session.getAttribute("orgId");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sbhql=new StringBuffer();
		//查询出总条数
		sbhql.append("select count(1) from (");
		sbhql.append("select a.repair_no,c.dealer_name");
		sbhql.append(" from RP_MAIN_INFO a, RP_MESSAGE_STATE b, rp_dealer_maintenance c");
		sbhql.append(" where a.REPORT_NO(+) = b.REPORT_NO");
		sbhql.append(" and a.FRM_NO(+) = b.FRM_NO");
		sbhql.append(" and a.repair_no = c.dealer_code(+)");
		sbhql.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
		if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
				repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
			sbhql.append(" and b.template_type = '1'");
		}else{
			sbhql.append(" and b.template_type = '2'");
		}
		if(repairStatistics.getState()==null||repairStatistics.getState().equals("")){			
			sbhql.append(" and b.state = '1'");
		}else{
			sbhql.append(" and b.state = '").append(repairStatistics.getState()).append("'");
		}
		//判断车商代码是否为空
		if(repairStatistics.getDealerCode()!=null&&!"".equals(repairStatistics.getDealerCode())){
			sbhql.append(" and a.repair_no='").append(repairStatistics.getDealerCode()).append("'");
		}
		if(repairStatistics.getDealerName()!=null&&!"".equals(repairStatistics.getDealerName())){
			sbhql.append(" and c.dealer_name like'").append(repairStatistics.getDealerName()).append("%'");
		}
//		if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
//			sbhql.append(" and b.upd_tm>='").append(repairStatistics.getEndTime()).append("'");
//			sbhql.append(" and b.upd_tm<='").append(repairStatistics.getEndTime()).append("'");
//		}
		if(repairStatistics.getStartTime()!=null&&!"".equals(repairStatistics.getStartTime())){
			String startTime=sdf.format(repairStatistics.getStartTime());
			sbhql.append(" and to_char(b.upd_tm,'yyyy-MM-dd')>='").append(startTime).append("'");
		}
		if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
			String endTime=sdf.format(repairStatistics.getEndTime());
			sbhql.append(" and to_char(b.upd_tm,'yyyy-MM-dd')<='").append(endTime).append("'");
		}
		sbhql.append("  group by a.repair_no,c.dealer_name");
		sbhql.append(")");
		Object obj=systemService.findListbySql(sbhql.toString());
		String count=obj.toString();
		count=count.replace("[","");
		count=count.replace("]", "");
		dataGrid.setTotal(Integer.parseInt(count));
		int number1=(dataGrid.getPage()-1)*dataGrid.getRows();
		int number2=dataGrid.getPage()*dataGrid.getRows();
		
		StringBuffer sbHqlList=new StringBuffer();
		sbHqlList.append("select * from (select t.*,rownum rn from(");
		sbHqlList.append("select 1 id,a.repair_no dealerCode,c.dealer_name dealerName,count(1) countNum,");
		if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
				repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
			//sbHqlList.append("count(case when b.template_type = '1' then 'giveNum' end) giveNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'A') then 1 else 0 end end) giveAssessNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'B') then 1 else 0 end end) giveBussinessorNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'C') then 1 else 0 end end) giveCustomerNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'D') then 1 else 0 end end) giveDealerNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'S') then 1 else 0 end end) giveSurveyorNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'DI') then 1 else 0 end end) giveDirectorNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'U') then 1 else 0 end end) giveUsherNum,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_1') then 1 else 0 end end) giveOther1Num,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_2') then 1 else 0 end end) giveOther2Num,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_3') then 1 else 0 end end) giveOther3Num,");
			sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_4') then 1 else 0 end end) giveOther4Num,");
		}else{
			sbHqlList.append("count(case when b.template_type = '2' then 'giveNum' end) giveNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'A') then 1 else 0 end end) giveAssessNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'B') then 1 else 0 end end) giveBussinessorNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'C') then 1 else 0 end end) giveCustomerNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'D') then 1 else 0 end end) giveDealerNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'S') then 1 else 0 end end) giveSurveyorNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'DI') then 1 else 0 end end) giveDirectorNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'U') then 1 else 0 end end) giveUsherNum,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_1') then 1 else 0 end end) giveOther1Num,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_2') then 1 else 0 end end) giveOther2Num,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_3') then 1 else 0 end end) giveOther3Num,");
			sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_4') then 1 else 0 end end) giveOther4Num,");
		}
//		sbHqlList.append("count(case when b.template_type = '2' then 'ReturnNum'  end) ReturnNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'A') then 1  else 0 end end) ReturnAssessNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'B') then 1  else 0 end end) ReturnBussinessorNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'C') then 1  else 0 end end) ReturnCustomerNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'D') then 1  else 0 end end) ReturnDealerNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'S') then 1  else 0 end end) ReturnSurveyorNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'DI') then 1  else 0 end end) ReturnDirectorNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'U') then 1  else 0 end end) ReturnUsherNum,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_1') then 1  else 0 end end) ReturnOther1Num,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_2') then 1  else 0 end end) ReturnOther2Num,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_3') then 1  else 0 end end) ReturnOther3Num,");
//		sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_4') then 1  else 0 end end) ReturnOther4Num");
		//sbHqlList.append("");
		sbHqlList.append("b.state state,template_type pxType,1 startTime,1 endTime");
		sbHqlList.append(" from RP_MAIN_INFO a, RP_MESSAGE_STATE b, rp_dealer_maintenance c");
		sbHqlList.append(" where a.REPORT_NO(+) = b.REPORT_NO");
		sbHqlList.append(" and a.FRM_NO(+) = b.FRM_NO");
		sbHqlList.append(" and a.repair_no = c.dealer_code(+)");
		sbHqlList.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
		if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
				repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
			sbHqlList.append(" and b.template_type = '1'");
		}else{
			sbHqlList.append(" and b.template_type = '2'");
		}
		
		if(repairStatistics.getState()==null||repairStatistics.getState().equals("")){			
			sbHqlList.append(" and b.state = '1'");
		}else{
			sbHqlList.append(" and b.state = '").append(repairStatistics.getState()).append("'");
		}
		if(repairStatistics.getDealerCode()!=null&&!"".equals(repairStatistics.getDealerCode())){
			sbHqlList.append(" and a.repair_no='").append(repairStatistics.getDealerCode()).append("'");
		}
		if(repairStatistics.getDealerName()!=null&&!"".equals(repairStatistics.getDealerName())){
			sbHqlList.append(" and c.dealer_name like'%").append(repairStatistics.getDealerName()).append("%'");
		}
		if(repairStatistics.getStartTime()!=null&&!"".equals(repairStatistics.getStartTime())){
			String startTime=sdf.format(repairStatistics.getStartTime());
			sbHqlList.append(" and to_char(b.upd_tm,'yyyy-MM-dd')>='").append(startTime).append("'");
		}
		if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
			String endTime=sdf.format(repairStatistics.getEndTime());
			sbHqlList.append(" and to_char(b.upd_tm,'yyyy-MM-dd')<='").append(endTime).append("'");
		}
		sbHqlList.append(" group by a.repair_no, c.dealer_name,b.state,b.template_type");
		sbHqlList.append(") t)");
		sbHqlList.append(" where rn>").append(number1);		
		sbHqlList.append(" and rn<=").append(number2);	
		HqlQuery hq=new HqlQuery(RepairStatistics.class,sbHqlList.toString(),dataGrid);
		PageList rdList=systemService.getPageListBySql(hq, true);
		List<RepairDealer> objList1=rdList.getResultList();
		dataGrid.setResults(objList1);
		TagUtil.datagrid(response, dataGrid);
		}
	/**
	 * 导出监控信息到excel
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "exportXls")
	public void expExcel(HttpServletRequest request, 
			HttpServletResponse response,RepairStatistics repairStatistics) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    Date date=new Date();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");    
	    String str1 = sdf1.format(date);   
	    String fileNameDecode=str1+".xls";
	    response.setContentType("application/vnd.ms-excel");  
	    response.setHeader("Content-disposition", "attachment;filename="+new String(fileNameDecode.getBytes("utf-8"), "ISO-8859-1"));    
	    OutputStream ouputStream = response.getOutputStream();    
	    //创建一个新的excel
	    HSSFWorkbook wb = new HSSFWorkbook();  
	    //创建sheet页
        HSSFSheet sheet = wb.createSheet("监控中心详情");
        //创建header页
        HSSFHeader header = sheet.getHeader();
	    //设置头部
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        HSSFCell cell = row.createCell(0);    
        cell.setCellValue("车商代码");    
        cell.setCellStyle(style);    
        cell = row.createCell(1);    
        cell.setCellValue("车商名称");    
        cell.setCellStyle(style);  
        cell = row.createCell(2);    
        cell.setCellValue("总条数");    
        cell.setCellStyle(style);  
        cell = row.createCell(3);    
        cell.setCellValue("定损员发送次数");    
        cell.setCellStyle(style);  
        cell = row.createCell(4);    
        cell.setCellValue("保险公司车商维护专员发送次数");    
        cell.setCellStyle(style);  
        cell = row.createCell(5);    
        cell.setCellValue("客户发送次数");    
        cell.setCellStyle(style);  
        cell = row.createCell(6);    
        cell.setCellValue("车商联系人发送次数"); 
        cell.setCellStyle(style);  
        cell = row.createCell(7);    
        cell.setCellValue("查勘员发送次数");  
        cell.setCellStyle(style);  
        cell = row.createCell(8);    
        cell.setCellValue("车商理赔售后经理发送次数 ");    
        cell.setCellStyle(style);
        cell = row.createCell(9);    
        cell.setCellValue("车商理赔引导专员发送次数 ");    
        cell.setCellStyle(style);
        cell = row.createCell(10);    
        cell.setCellValue("其他1发送次数 ");    
        cell.setCellStyle(style);
        cell = row.createCell(11);    
        cell.setCellValue("其他2发送次数 ");    
        cell.setCellStyle(style);
        cell = row.createCell(12);    
        cell.setCellValue("其他3发送次数 ");    
        cell.setCellStyle(style);
        cell = row.createCell(13);    
        cell.setCellValue("其他4发送次数 ");    
        /* 自动调整宽度 */  
        for (int i = 0; i <14; i++) {  
            sheet.autoSizeColumn(i);  
        }  
        /* 实际宽度 */  
        int curColWidth = 0;  
          
        /* 默认宽度 */  
        int[] defaultColWidth = { 5000, 5000, 5000, 5000, 6000, 5000,5000,5000,6000,6000,5000,5000,5000,5000 };  
        /* 实际宽度 < 默认宽度的时候、设置为默认宽度 */  
        for (int i = 0; i < 14; i++) {  
            curColWidth = sheet.getColumnWidth(i);  
            if (curColWidth < defaultColWidth[i]) {  
                sheet.setColumnWidth(i, defaultColWidth[i]);  
            }  
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = request.getSession();
        String departId=(String) session.getAttribute("orgId");
        //获取车商代码
	    //String dealerCode =request.getAttribute("dealerCode").toString();
	    //获取车商全称
	    //String dealerName = StringUtil.getEncodePra(request.getAttribute("dealerName").toString());
	    StringBuffer sbhql=new StringBuffer();
		//查询出总条数
		sbhql.append("select count(1) from (");
		sbhql.append("select a.repair_no,c.dealer_name");
		sbhql.append(" from RP_MAIN_INFO a, RP_MESSAGE_STATE b, rp_dealer_maintenance c");
		sbhql.append(" where a.REPORT_NO(+) = b.REPORT_NO");
		sbhql.append(" and a.FRM_NO(+) = b.FRM_NO");
		sbhql.append(" and a.repair_no = c.dealer_code(+)");
		sbhql.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
		if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
				repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
			sbhql.append(" and b.template_type = '1'");
		}else{
			sbhql.append(" and b.template_type = '2'");
		}
		if(repairStatistics.getState()==null||repairStatistics.getState().equals("")){			
			sbhql.append(" and b.state = '1'");
		}else{
			sbhql.append(" and b.state = '").append(repairStatistics.getState()).append("'");
		}
		//判断车商代码是否为空
		if(repairStatistics.getDealerCode()!=null&&!"".equals(repairStatistics.getDealerCode())){
			sbhql.append(" and a.repair_no='").append(repairStatistics.getDealerCode()).append("'");
		}
		if(repairStatistics.getDealerName()!=null&&!"".equals(repairStatistics.getDealerName())){
			sbhql.append(" and c.dealer_name like'").append(repairStatistics.getDealerName()).append("%'");
		}
//		if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
//			sbhql.append(" and b.upd_tm>='").append(repairStatistics.getEndTime()).append("'");
//			sbhql.append(" and b.upd_tm<='").append(repairStatistics.getEndTime()).append("'");
//		}
		if(repairStatistics.getStartTime()!=null&&!"".equals(repairStatistics.getStartTime())){
			String startTime=sdf.format(repairStatistics.getStartTime());
			sbhql.append(" and to_char(b.upd_tm,'yyyy-MM-dd')>='").append(startTime).append("'");
		}
		if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
			String endTime=sdf.format(repairStatistics.getEndTime());
			sbhql.append(" and to_char(b.upd_tm,'yyyy-MM-dd')<='").append(endTime).append("'");
		}
		sbhql.append("  group by a.repair_no,c.dealer_name");
		sbhql.append(")");
		Object obj=systemService.findListbySql(sbhql.toString());
		String substring = obj.toString();
		substring = substring.replace("[","");
		substring = substring.replace("]", "");
		int count = Integer.parseInt(substring);
		//总页数
	    int  PageCount=1;
	    //每页显示行数
	    int rwos=500;
	    //当前页
	    int currentPage=1;
	    int a=1;
	    if(count>500){
	    	PageCount=(int) (count/500);
	    	PageCount++;
	    }
	    for (int i = 0; i < PageCount; i++) {
	    	currentPage=i+1;
	    	StringBuffer sbHqlList=new StringBuffer();
			sbHqlList.append("select t.*,rownum rn from(");
			sbHqlList.append("select 1 id,a.repair_no dealerCode,c.dealer_name dealerName,count(1) countNum,");
			if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
					repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
				//sbHqlList.append("count(case when b.template_type = '1' then 'giveNum' end) giveNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'A') then 1 else 0 end end) giveAssessNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'B') then 1 else 0 end end) giveBussinessorNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'C') then 1 else 0 end end) giveCustomerNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'D') then 1 else 0 end end) giveDealerNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'S') then 1 else 0 end end) giveSurveyorNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'DI') then 1 else 0 end end) giveDirectorNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'U') then 1 else 0 end end) giveUsherNum,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_1') then 1 else 0 end end) giveOther1Num,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_2') then 1 else 0 end end) giveOther2Num,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_3') then 1 else 0 end end) giveOther3Num,");
				sbHqlList.append("sum(case when (b.template_type = '1') then case when (b.msg_type = 'OTHER_4') then 1 else 0 end end) giveOther4Num,");
			}else{
				sbHqlList.append("count(case when b.template_type = '2' then 'giveNum' end) giveNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'A') then 1 else 0 end end) giveAssessNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'B') then 1 else 0 end end) giveBussinessorNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'C') then 1 else 0 end end) giveCustomerNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'D') then 1 else 0 end end) giveDealerNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'S') then 1 else 0 end end) giveSurveyorNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'DI') then 1 else 0 end end) giveDirectorNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'U') then 1 else 0 end end) giveUsherNum,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_1') then 1 else 0 end end) giveOther1Num,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_2') then 1 else 0 end end) giveOther2Num,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_3') then 1 else 0 end end) giveOther3Num,");
				sbHqlList.append("sum(case when (b.template_type = '2') then case when (b.msg_type = 'OTHER_4') then 1 else 0 end end) giveOther4Num,");
			}
			sbHqlList.append("b.state state,template_type pxType,1 startTime,1 endTime");
			sbHqlList.append(" from RP_MAIN_INFO a, RP_MESSAGE_STATE b, rp_dealer_maintenance c");
			sbHqlList.append(" where a.REPORT_NO(+) = b.REPORT_NO");
			sbHqlList.append(" and a.FRM_NO(+) = b.FRM_NO");
			sbHqlList.append(" and a.repair_no = c.dealer_code(+)");
			sbHqlList.append(" and a.CLAIM_DEPARTMENT like '").append(departId).append("%'");
			if(repairStatistics.getPxType()==null||repairStatistics.getPxType().equals("")||
					repairStatistics.getPxType()=="1"||repairStatistics.getPxType().equals("1")){
				sbHqlList.append(" and b.template_type = '1'");
			}else{
				sbHqlList.append(" and b.template_type = '2'");
			}
			if(repairStatistics.getState()==null||repairStatistics.getState().equals("")){			
				sbHqlList.append(" and b.state = '1'");
			}else{
				sbHqlList.append(" and b.state = '").append(repairStatistics.getState()).append("'");
			}
			if(repairStatistics.getDealerCode()!=null&&!"".equals(repairStatistics.getDealerCode())){
				sbHqlList.append(" and a.repair_no='").append(repairStatistics.getDealerCode()).append("'");
			}
			if(repairStatistics.getDealerName()!=null&&!"".equals(repairStatistics.getDealerName())){
				sbHqlList.append(" and c.dealer_name like'%").append(repairStatistics.getDealerName()).append("%'");
			}
			if(repairStatistics.getStartTime()!=null&&!"".equals(repairStatistics.getStartTime())){
				String startTime=sdf.format(repairStatistics.getStartTime());
				sbHqlList.append(" and to_char(b.upd_tm,'yyyy-MM-dd')>='").append(startTime).append("'");
			}
			if(repairStatistics.getEndTime()!=null&&!"".equals(repairStatistics.getEndTime())){
				String endTime=sdf.format(repairStatistics.getEndTime());
				sbHqlList.append(" and to_char(b.upd_tm,'yyyy-MM-dd')<='").append(endTime).append("'");
			}
			sbHqlList.append(" group by a.repair_no, c.dealer_name,b.state,b.template_type");
			sbHqlList.append(") t");
			list=systemService.findForJdbc(sbHqlList.toString(), currentPage, rwos);
			for (Map<String, Object> m : list) {
				for (String k : m.keySet()) {
					row = sheet.createRow(a);
					a++;
					row.createCell(0).setCellValue(
							m.get("DEALERCODE").toString());
					if (m.get("DEALERNAME") == null||"".equals(m.get("DEALERNAME").toString())) {
						m.put("DEALERNAME", "");
						row.createCell(1).setCellValue(
								m.get("DEALERNAME").toString());
					}else
						row.createCell(1).setCellValue(
								m.get("DEALERNAME").toString());
					row.createCell(2)
							.setCellValue(m.get("COUNTNUM").toString());
					row.createCell(3).setCellValue(
							m.get("GIVEASSESSNUM").toString());
					row.createCell(4).setCellValue(
							m.get("GIVEBUSSINESSORNUM").toString());
					row.createCell(5).setCellValue(
							m.get("GIVECUSTOMERNUM").toString());
					row.createCell(6).setCellValue(
							m.get("GIVEDEALERNUM").toString());
					row.createCell(7).setCellValue(
							m.get("GIVESURVEYORNUM").toString());
					row.createCell(8).setCellValue(
							m.get("GIVEDIRECTORNUM").toString());
					row.createCell(9).setCellValue(
							m.get("GIVEUSHERNUM").toString());
					row.createCell(10).setCellValue(
							m.get("GIVEOTHER1NUM").toString());
					row.createCell(11).setCellValue(
							m.get("GIVEOTHER2NUM").toString());
					row.createCell(12).setCellValue(
							m.get("GIVEOTHER3NUM").toString());
					row.createCell(13).setCellValue(
							m.get("GIVEOTHER4NUM").toString());
					break;
				}
			}
	    }
	    wb.write(ouputStream);
	    ouputStream.flush();
	    ouputStream.close();
	}
}
