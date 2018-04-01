<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="recommendDangerList" title="出险车维修管理  " actionUrl="dangerController.do?recommendDatagrid" idField="id" queryMode="group" fit="true" pagination="true" showRefresh="true">
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="理赔机构" field="claimDepartment" hidden="true" query="false" width="120"></t:dgCol>
			<t:dgCol title="车牌号" field="plateNo" query="true" width="100"></t:dgCol>
			<t:dgCol title="车架号" field="frmNo" query="true" width="100"></t:dgCol>
			<t:dgCol title="发动机号" field="engineNo" query="true" width="100"></t:dgCol>
			<t:dgCol title="车辆品牌" field="vehlcleName" query="true" width="100"></t:dgCol>
			<t:dgCol title="维修状态" field="maintenanceStatus" hidden="true" dictionary="repstatus" width="100"></t:dgCol>
			<t:dgCol title="创建时间" field="crtTm" query="false" width="100"></t:dgCol>
		</t:datagrid>
	</div>
</div>