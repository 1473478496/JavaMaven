<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tSSmsTemplateList"  nowrap = "false" checkbox="true" fitColumns="false" title="短信模板维护  <font color=#FF0000>设置具体的派修短信内容</font>" actionUrl="tSSmsTemplateController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="common.isId"  field="id"  hidden="true"   queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.createName"  field="createName"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.createCde"  field="crtCde"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.createDate"  field="crtTm" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.updateName"  field="updName"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.updateByName"  field="updCde"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.updateDate"  field="updTm" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="common.templateName"  field="templateName"  query="true" queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="common.templateType"  field="templateType" query="true" queryMode="single" dictionary="msgTplType" width="100"></t:dgCol>
   <t:dgCol title="适用于三者"  field="vehicleType" dictionary="car_Type"   queryMode="single" width="80"></t:dgCol>
   <t:dgCol title="common.templateContent"  field="templateContent"  queryMode="single" width="350" ></t:dgCol>
   <t:dgCol title="归属机构代码"  field="issuDepartment" hidden="true" query="false" ></t:dgCol>
   <t:dgCol title="机构名称"  field="orgName"  queryMode="single" width="300" query="true"  ></t:dgCol>
   <t:dgCol title="人员类别"  field="sendType"  queryMode="single"  dictionary="sendType" width="100"></t:dgCol>
   <t:dgCol title="common.opt" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="common.deleteTo" url="tSSmsTemplateController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="tSSmsTemplateController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="common.icon.edit" icon="icon-edit" url="tSSmsTemplateController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="common.icon.remove"  icon="icon-remove" url="tSSmsTemplateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="common.view" icon="icon-search" url="tSSmsTemplateController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script>
	
	
</script>