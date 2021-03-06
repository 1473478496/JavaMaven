<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="lableList" title="品牌型号信息查询"  nowrap = "false" actionUrl="lableController.do?datagrid" 
 idField="id" queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="品牌名称" field="moedlBrand" query="true"  width="530" sortable="asc"></t:dgCol>
   <t:dgCol title="品牌编码" field="brandCode" width="530" query="true"></t:dgCol>
   <t:dgCol title="车型代码" field="modelCode" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="车型名称" field="modelName" hidden="true" width="300"></t:dgCol>
   <t:dgCol title="注释" field="notes"  hidden="true"  width="400"></t:dgCol>
   <t:dgCol title="创建时间" field="crtTm" hidden="true" formatter="yyyy-MM-dd"  width="120"></t:dgCol>
   <t:dgCol title="创建人代码" field="crtCde" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="更新时间" field="updTm" hidden="true" formatter="yyyy-MM-dd"  width="120"></t:dgCol>
   <t:dgCol title="更新人代码" field="updCde" hidden="true"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>