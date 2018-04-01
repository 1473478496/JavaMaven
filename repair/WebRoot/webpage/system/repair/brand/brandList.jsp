<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="brandList" title="车商维修品牌设置 <font color=#FF0000> 为车商匹配可承修的品牌</font>" actionUrl="brandController.do?datagrid" idField="id" 
  queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车商代码" field="dealerCode" query="true" width="120"></t:dgCol>  
   <t:dgCol title="车商名称" field="dealerName" query="true"   width="350"></t:dgCol>
   <t:dgCol title="品牌名称" field="brandName" query="true" width="120"></t:dgCol>
   <t:dgCol title="品牌编码" field="brandCode" query="true" width="120"></t:dgCol>
   <t:dgCol title="权重" field="weight" width="80"></t:dgCol>
   <t:dgCol title="就近系数" field="ratio" width="80"></t:dgCol>
   <t:dgCol title="创建时间" field="crtTm" hidden="true" formatter="yyyy-MM-dd"  width="120"></t:dgCol>
   <t:dgCol title="创建人代码" field="crtCde" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="更新时间" field="updTm" hidden="true" formatter="yyyy-MM-dd"  width="120"></t:dgCol>
   <t:dgCol title="更新人代码" field="updCde" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="删除" url="brandController.do?del&id={id}" />
	<t:dgToolBar title="新增" icon="icon-add" url="brandController.do?add" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="brandController.do?update" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="brandController.do?update" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>