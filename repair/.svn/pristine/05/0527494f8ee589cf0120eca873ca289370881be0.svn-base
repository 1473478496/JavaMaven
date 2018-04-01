<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="monitorList" title="派修案件查询" actionUrl="monitorController.do?datagrid" idField="id" 
  queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="报案号" field="reportNo"  query="true"  width="150"></t:dgCol>
   <t:dgCol title="推荐车商代码" field="repairSerialNo" hidden="true"  query="false" width="180"></t:dgCol>
   <t:dgCol title="车牌号" field="plateNo" query="true"  width="100"></t:dgCol>
   <t:dgCol title="车架号" field="frmNo" hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="是否三者车" field="vehicleType"  dictionary="car_Type"   width="120"></t:dgCol>
   <t:dgCol title="推荐车商" field="repairName"     width="250"></t:dgCol>
   <t:dgCol title="实际车商" field="dealerName"  width="250"></t:dgCol>
   <t:dgCol title="报案人" field="reporterName"   width="80"></t:dgCol>
   <t:dgCol title="报案时间" field="reportTm"   width="100"></t:dgCol>
   <t:dgCol title="查勘员" field="surveyorName"    width="80"></t:dgCol>
   <t:dgCol title="定损员" field="assessName"    width="80"></t:dgCol>
   <t:dgCol title="车商联系人姓名" field="dealerPeople" hidden="true"     width="80"></t:dgCol>
   <t:dgCol title="核损员" field="nuclearDamageName" hidden="true"   width="80"></t:dgCol>
   <t:dgCol title="送修状态" field="repairState" dictionary="repairS"   width="80"></t:dgCol>
   <t:dgCol title="开始时间" field="startTime"  query="true" hidden="true" datebox="true"  width="100"></t:dgCol>
   <t:dgCol title="结束时间" field="endTime"  query="true"  hidden="true" datebox="true" width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgFunOpt funname="query(repairSerialNo)" title="查看" ></t:dgFunOpt>
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
</script>