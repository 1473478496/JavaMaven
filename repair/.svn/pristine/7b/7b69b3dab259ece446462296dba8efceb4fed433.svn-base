<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="clmDealerInfoList" title="理赔车商关联承保车商信息维护 <font color=#FF0000>根据修理厂信息匹配对应的承保车商，用于保费及理赔的关联统计，矫正承保、理赔同厂不同名的问题</font>" nowrap = "false" actionUrl="clmDealerInfoController.do?datagrid" queryMode="group" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车商代码" field="dealerCode" query="true"  width="120"></t:dgCol>
   <t:dgCol title="车商名称" field="dealerName"  query="true" width="300"></t:dgCol>
   <t:dgCol title="车商具体地址" field="dealerAddress"   width="300"></t:dgCol>
   <t:dgCol title="省" field="province" hidden="true"  width="100"></t:dgCol>
   <!-- dictionary="rp_province,provinceid,province" -->
   <t:dgCol title="市" field="city" hidden="true" width="100"></t:dgCol>
   <t:dgCol title="区县" field="area" hidden="true" width="100"></t:dgCol>
   <t:dgCol title="车商联系人姓名" field="contactsName"   width="80"></t:dgCol>
   <t:dgCol title="车商联系人手机" field="contactsTel"   width="120"></t:dgCol>
   <t:dgCol title="车商全称" field="dealerAllName" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="业务归属地" field="businessHome" hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="车商售后经理姓名" field="directorName"   width="80"></t:dgCol>
   <t:dgCol title="车商售后经理电话" field="directorTel"   width="120"></t:dgCol>
   <t:dgCol title="保险公司车商维护专员姓名" field="agent"   width="80"></t:dgCol>
   <t:dgCol title="保险公司车商维护专员手机" field="agentTel"   width="120"></t:dgCol>
   <t:dgCol title="资质类型" field="dealerType" dictionary="clm_type"   query="true" width="120"></t:dgCol>
   <t:dgCol title="归属机构代码" field="dptCde" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="是否有效" field="isvalid" dictionary="sf_01"  width="80"></t:dgCol>
   <t:dgCol title="备注" field="note" hidden="true"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="80"></t:dgCol>
   <t:dgFunOpt funname="editPlyDealer(dealerCode)" title="编辑" ></t:dgFunOpt>
   <t:dgFunOpt funname="queryPlyDealer(dealerCode)" title="查看" ></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
<script>
 function editPlyDealer(dealerCode){
    	$.dialog({
			content: "url:plyDealerInfoController.do?list&clmDealerCode="+dealerCode,
			drag :false,
			lock : true,
			title:'查看承保车商信息',
			opacity : 0.3,
			width:800,
			height:480,
			drag:false,
			min:true,
			max:true
		}).zindex();
	}
 function queryPlyDealer(dealerCode){
 	$.dialog({
			content: "url:plyDealerInfoController.do?queryList&clmDealerCode="+dealerCode,
			drag :false,
			lock : true,
			title:'查看承保车商信息',
			opacity : 0.3,
			width:800,
			height:480,
			drag:false,
			min:true,
			max:true
		}).zindex();
	}
</script>