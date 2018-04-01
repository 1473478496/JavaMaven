package cn.sunline.repair.controller;



import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.sunline.core.common.controller.BaseController;
import cn.sunline.core.common.hibernate.qbc.CriteriaQuery;
import cn.sunline.core.common.model.json.DataGrid;
import cn.sunline.core.util.StringUtil;
import cn.sunline.repair.entity.TSSmsTemplateEntity;
import cn.sunline.repair.service.MessageStateServiceI;
import cn.sunline.tag.core.easyui.TagUtil;
import cn.sunline.tag.vo.datatable.SortDirection;
import cn.sunline.web.system.pojo.base.TSDepart;
import cn.sunline.ws.foreignInterface.entity.RepairMainInfo;
import cn.sunline.ws.message.common.MessageConstants;
import cn.sunline.ws.message.vo.MessageStateVo;

/**
 * @Title: Controller
 * @Description: 理赔车商信息维护
 * @author zhangdaihao
 * @date 2016-10-14 11:54:24
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/messageStateController")
public class MessageStateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageStateController.class);

	@Autowired
	private MessageStateServiceI messageStateService;

	/**
	 * 短信详情查询
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/repair/messageState/messageStateList");
		
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
	public void datagrid(MessageStateVo messageState, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HttpSession session = request.getSession();
		String dptCde = (String) session.getAttribute("orgId");
		dptCde = dptCde + "%";
		CriteriaQuery cq = new CriteriaQuery(MessageStateVo.class, dataGrid);
		cq.like("dptCde",dptCde);
		cq.addOrder("crtTm", SortDirection.desc);
		cq.add();
		// 查询条件组装器
		cn.sunline.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,messageState, request.getParameterMap());
		this.messageStateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "detailed")
	public ModelAndView addorupdate(MessageStateVo messageState, HttpServletRequest req) {
		messageState=messageStateService.getEntity(MessageStateVo.class, messageState.getId());
		String hql = "select a FROM RepairMainInfo a,MessageStateVo b WHERE a.reportNo=b.reportNo and a.frmNo = b.frmNo";
		List<RepairMainInfo> relist = messageStateService.findByQueryString(hql);
		for (RepairMainInfo repairMainInfo : relist) {
			req.setAttribute("repairMainInfo", repairMainInfo);
			break;
		}
		req.setAttribute("messageStatePage", messageState);
		return new ModelAndView("system/repair/messageState/messageState");
	}

	//导出excel
	@RequestMapping(params = "exportXls")
	public void expExcel(HttpServletRequest request, 
			HttpServletResponse response) throws IOException{
	    List<MessageStateVo> list = new ArrayList<MessageStateVo>();
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
        HSSFSheet sheet = wb.createSheet("短信详情");
        //创建header页
        HSSFHeader header = sheet.getHeader();
	    //设置头部
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        HSSFCell cell = row.createCell(0);    
        cell.setCellValue("报案号");    
        cell.setCellStyle(style);    
        cell = row.createCell(1);    
        cell.setCellValue("车牌号");    
        cell.setCellStyle(style);  
        cell = row.createCell(2);    
        cell.setCellValue("短信类型");    
        cell.setCellStyle(style);  
        cell = row.createCell(3);    
        cell.setCellValue("发送对象");    
        cell.setCellStyle(style);  
        cell = row.createCell(4);    
        cell.setCellValue("手机号码");    
        cell.setCellStyle(style);  
        cell = row.createCell(5);    
        cell.setCellValue("短信内容");    
        cell.setCellStyle(style);  
        cell = row.createCell(6);    
        cell.setCellValue("发送状态"); 
        cell.setCellStyle(style);  
        cell = row.createCell(7);    
        cell.setCellValue("派修类型");  
        cell.setCellStyle(style);  
        cell = row.createCell(8);    
        cell.setCellValue("机构名称");    
        cell.setCellStyle(style);  
        /* 自动调整宽度 */  
        for (int i = 0; i <9; i++) {  
            sheet.autoSizeColumn(i);  
        }  
        /* 实际宽度 */  
        int curColWidth = 0;  
          
        /* 默认宽度 */  
        int[] defaultColWidth = { 5000, 5000, 5000, 5000, 5000, 5000,5000,5000,5000 };  
        /* 实际宽度 < 默认宽度的时候、设置为默认宽度 */  
        for (int i = 0; i < 9; i++) {  
            curColWidth = sheet.getColumnWidth(i);  
            if (curColWidth < defaultColWidth[i]) {  
                sheet.setColumnWidth(i, defaultColWidth[i]);  
            }  
        }   
	    //获取报案号
	    String reportNo =request.getParameter("reportNo");
	    //获取车牌号
	    String plateNo = StringUtil.getEncodePra(request.getParameter("plateNo"));
	    //获取手机号
	    String sendeePhone= request.getParameter("sendeePhone");
	    //获取派修类型
	    String templateType=request.getParameter("templateType");
	    //查询出总条数
	    StringBuffer sb1= new StringBuffer();
	    sb1.append("select count(1) from RP_MESSAGE_STATE where 1=1");
	    sb1.append(" and STATE='1'");
	    if(reportNo!=null&&!"".equals(reportNo)){
	    	sb1.append(" and REPORT_NO='").append(reportNo).append("'");
	    }
	    if(plateNo!=null&&!"".equals(plateNo)){
	    	sb1.append(" and plate_No='").append(plateNo).append("'");
	    }
	    if(sendeePhone!=null&&!"".equals(sendeePhone)){
	    	sb1.append(" and sendee_Phone='").append(sendeePhone).append("'");
	    }if(templateType!=null&&!"".equals(templateType)){
	    	sb1.append(" and template_Type='").append(templateType).append("'");
	    }
	    Long count=messageStateService.getCountForJdbc(sb1.toString());
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
	    	StringBuffer sb2= new StringBuffer();
	    	sb2.append("from MessageStateVo  where 1=1");
	    	sb2.append(" and STATE='1'");
	    	if(reportNo!=null&&!"".equals(reportNo)){
	    		sb2.append(" and reportNo='").append(reportNo).append("'");
	    	}
	    	if(plateNo!=null&&!"".equals(plateNo)){
	    		sb2.append(" and plateNo='").append(plateNo).append("'");
	    	}
	    	if(sendeePhone!=null&&!"".equals(sendeePhone)){
	    		sb2.append(" and sendeePhone='").append(sendeePhone).append("'");
	    	}if(templateType!=null&&!"".equals(templateType)){
		    	sb2.append(" and template_Type='").append(templateType).append("'");
		    }
	    	//list=messageStateService.findObjForJdbc(sb2.toString(), currentPage, rwos,MessageStateVo.class);
	    	list=messageStateService.findByQueryString(sb2.toString(), currentPage, rwos);
	    	 for (int j = 0; j < list.size(); j++) { 
	             row = sheet.createRow(a); 
	             a++;
	             MessageStateVo mse = list.get(j);    
	             row.createCell(0).setCellValue(mse.getReportNo());      
	             row.createCell(1).setCellValue(mse.getPlateNo());     
	             if(mse.getMsgType()==MessageConstants.CUSTOMER||MessageConstants.CUSTOMER.equals(mse.getMsgType())){	            	 
	            	 row.createCell(2).setCellValue("报案人");        
	             }else if(mse.getMsgType()==MessageConstants.SURVEYOR||MessageConstants.SURVEYOR.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("勘察员");   
	             }else if(mse.getMsgType()==MessageConstants.DEALER||MessageConstants.DEALER.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("车商");   
	             }else if(mse.getMsgType()==MessageConstants.ASSESS||MessageConstants.ASSESS.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("定损员");   
	             }else if(mse.getMsgType()==MessageConstants.BUSSINESSOR||MessageConstants.BUSSINESSOR.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("业务员");   
	             }/*else if(mse.getMsgType()==MessageConstants.POLICYDEALER||MessageConstants.POLICYDEALER.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("承保时车商");   
	             }else if(mse.getMsgType()==MessageConstants.POLICYBUSSINESSOR||MessageConstants.POLICYBUSSINESSOR.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("承保时车商的业务员");   
	             }*/else if(mse.getMsgType()==MessageConstants.OTHER_4||MessageConstants.OTHER_4.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("其他4");   
	             }else if(mse.getMsgType()==MessageConstants.OTHER_1||MessageConstants.OTHER_1.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("其他1");   
	             }else if(mse.getMsgType()==MessageConstants.OTHER_2||MessageConstants.OTHER_2.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("其他2");   
	             }else if(mse.getMsgType()==MessageConstants.OTHER_3||MessageConstants.OTHER_3.equals(mse.getMsgType())){
	            	 row.createCell(2).setCellValue("其他3");   
	             }else{
	            	 row.createCell(2).setCellValue("");   
	             }
	             row.createCell(3).setCellValue(mse.getSendeeName());        
	             row.createCell(4).setCellValue(mse.getSendeePhone());     
	             row.createCell(5).setCellValue(mse.getMsgContent());   
	             for(Map.Entry<String, String> entry:MessageConstants.map.entrySet()){   
	            	 if(entry.getKey()==mse.getState()||entry.getKey().equals(mse.getState())){	            		 
	            		 row.createCell(6).setCellValue(entry.getValue());     
	            	 }
	             }
	             if(mse.getTemplateType()=="1"||mse.getTemplateType().equals("1")){
	            	 row.createCell(7).setCellValue("送修");
	             }else if(mse.getTemplateType()=="2"||mse.getTemplateType().equals("2")){
	            	 row.createCell(7).setCellValue("派修");
	             }else{
	            	 row.createCell(7).setCellValue("");
	             }
	              
	             //根据机构号查询机构名称
	             TSDepart tsd= messageStateService.getEntity(TSDepart.class, mse.getDptCde());
	             if(tsd!=null){	            	 
	            	 row.createCell(8).setCellValue(tsd.getDepartname());     
	             }else{
	            	 row.createCell(8).setCellValue("");   
	             }
	         }     	    	
		}
	    wb.write(ouputStream);    
	    ouputStream.flush();    
	    ouputStream.close();          
	}
	
}
