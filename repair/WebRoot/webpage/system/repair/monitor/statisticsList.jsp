<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px;"  >
  <t:datagrid name="statisticsList" title="监控中心"  actionUrl="monitorController.do?statisticsDatagrid" idField="id" 
  queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车商代码" field="dealerCode"   query="true" width="80"></t:dgCol>
   <t:dgCol title="车商名称" field="dealerName"  query="true"  width="80"></t:dgCol>
   <t:dgCol title="总条数" field="countNum" width="80"></t:dgCol>
   <%-- <t:dgCol title="送修发送次数" field="giveNum"    width="80"></t:dgCol> --%>
   <t:dgCol title="定损员发送次数" field="giveAssessNum" width="80"></t:dgCol>
   <t:dgCol title="保险公司车商维护专员发送次数" field="giveBussinessorNum"  width="80"></t:dgCol>
   <t:dgCol title="客户发送次数" field="giveCustomerNum" width="80"></t:dgCol>
   <t:dgCol title="车商联系人发送次数" field="giveDealerNum"  width="80"></t:dgCol>
   <t:dgCol title="查勘员发送次数" field="giveSurveyorNum" width="80"></t:dgCol>
   <t:dgCol title="车商理赔售后经理发送次数" field="giveDirectorNum" width="80"></t:dgCol>
   <t:dgCol title="车商理赔引导专员发送次数" field="giveUsherNum" width="80"></t:dgCol>
   <t:dgCol title="其他1发送次数" field="giveOther1Num" width="80"></t:dgCol>
   <t:dgCol title="其他2发送次数" field="giveOther2Num" width="80"></t:dgCol>
   <t:dgCol title="其他3发送次数" field="giveOther3Num" width="80"></t:dgCol>
   <t:dgCol title="其他4发送次数" field="giveOther4Num" width="80"></t:dgCol>
   
   <%-- <t:dgCol title="返修发送次数" field="ReturnNum" width="80"></t:dgCol>
   <t:dgCol title="返修定损员发送次数" field="ReturnAssessNum"  width="80"></t:dgCol>
   <t:dgCol title="返修保险公司车商维护专员发送次数" field="ReturnBussinessorNum" width="80"></t:dgCol>
   <t:dgCol title="返修客户发送次数" field="ReturnCustomerNum" width="80"></t:dgCol>
   <t:dgCol title="返修车商联系人发送次数" field="ReturnDealerNum"  width="80"></t:dgCol>
   <t:dgCol title="返修查勘员发送次数" field="ReturnSurveyorNum" width="80"></t:dgCol>
   <t:dgCol title="返修车商理赔售后经理发送次数" field="ReturnDirectorNum" width="80"></t:dgCol>
   <t:dgCol title="返修车商理赔引导专员发送次数" field="ReturnUsherNum" width="80"></t:dgCol>
   <t:dgCol title="返修其他1发送次数" field="ReturnOther1Num" width="80"></t:dgCol>
   <t:dgCol title="返修其他2发送次数" field="ReturnOther2Num" width="80"></t:dgCol>
   <t:dgCol title="返修其他3发送次数" field="ReturnOther3Num" width="80"></t:dgCol>
   <t:dgCol title="返修其他4发送次数" field="ReturnOther4Num" width="80"></t:dgCol> --%>
   <t:dgCol title="短信发送状态" field="state" dictionary="sendState" query="true" hidden="true" width="80"></t:dgCol>
   <t:dgCol title="派修类型" field="pxType" dictionary="msgTplType" query="true" hidden="true" width="80"></t:dgCol>
   <t:dgCol title="开始日期" field="startTime"  query="true" hidden="true" datebox="true" width="80"></t:dgCol>
   <t:dgCol title="结束日期" field="endTime"  query="true" hidden="true" datebox="true" width="80"></t:dgCol>
   <t:dgToolBar title="导出监控信息" icon="icon-putout" url="monitorController.do?exportXls" funname="expExcels"></t:dgToolBar>
  <%--  <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgFunOpt funname="query(repairSerialNo)" title="查看" ></t:dgFunOpt> --%>
  <%--  <t:dgToolBar title="新增" icon="icon-add" url="dealerInfoController.do?add" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dealerInfoController.do?update" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dealerInfoController.do?update" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script>
 function query(id){
    	$.dialog({
			content: "url:monitorController.do?query&id="+id,
			drag :false,
			lock : true,
			title:'查看短信详情',
			opacity : 0.3,
			width:600,
			height:480,
			drag:false,
			min:true,
			max:true
		}).zindex();
	}
 function expExcels(title,url){
		var i=0;
		var dealerCode="";
		var dealerName="";
		if($("#statisticsListForm").Validform({tiptype:3}).check()){
			$('#statisticsListtb').find('*').each(function(){	  				
				i++;
				if(i==8){
					dealerCode=$(this).val();
				}
				if(i==11){
					dealerName=$(this).val();
				}
			});
		}
		url=url+"&dealerCode="+dealerCode+"&dealerName="+encodeURIComponent(encodeURIComponent(dealerName));
		location.href=url;
 }
</script>