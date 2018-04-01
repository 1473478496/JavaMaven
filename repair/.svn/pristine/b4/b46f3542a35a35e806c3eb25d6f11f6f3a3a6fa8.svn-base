<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="openOrgList"  nowrap = "false" title="已开通机构维护" actionUrl="openOrgController.do?datagrid" queryMode="group" idField="id" fit="true">
   <t:dgCol title="id" field="id"  hidden="true"  width="200"></t:dgCol>
   <t:dgCol title="机构码" field="orgCode" query="true"  width="200"></t:dgCol>
   <t:dgCol title="机构名称" field="orgName" query="true"  width="420"></t:dgCol>
   <t:dgCol title="开通时间" field="createTime"  width="300"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="删除" url="openOrgController.do?del&orgCode={orgCode}" />
   <t:dgToolBar title="新增" icon="icon-add" url="openOrgController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="openOrgController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>