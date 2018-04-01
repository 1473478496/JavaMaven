<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="currentDepartList" fitColumns="false" title="组织机构列表" actionUrl="departController.do?currentDepartGrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="归属机构代码" field="id"   query="true"></t:dgCol>
   <t:dgCol title="机构名称" width="200" field="departname" query="true"></t:dgCol>
   <t:dgCol title="机构描述" field="description" treefield="src"></t:dgCol>
   <t:dgCol title="common.org.type" field="orgType" dictionary="orgtype" treefield="fieldMap.orgType"></t:dgCol>
   <t:dgCol title="common.mobile" field="mobile" treefield="fieldMap.mobile"></t:dgCol>
   <t:dgCol title="common.fax" field="fax" treefield="fieldMap.fax"></t:dgCol>
   <t:dgCol title="common.address" field="address" treefield="fieldMap.address"></t:dgCol>
  </t:datagrid>
  </div>
 </div>	
 <script type="text/javascript">
 </script>