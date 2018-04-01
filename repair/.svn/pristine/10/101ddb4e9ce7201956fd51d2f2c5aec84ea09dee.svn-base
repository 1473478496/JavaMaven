<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <input type="text" name="clmDealerCode" id="clmDealerCode" value="${clmDealerCode }"  style="display: none" />
  <t:datagrid name="plyDealerInfoList" nowrap = "false" title="承保车商基本信息" checkbox="true" actionUrl="plyDealerInfoController.do?datagrid" queryMode="group" idField="id" fit="true">
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
   <t:dgCol title="资质类型" field="dealerType" dictionary="dealer_tp"  width="120"></t:dgCol>
   <t:dgCol title="归属机构代码" field="dptCde" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="是否有效" field="isvalid" dictionary="sf_01"  width="80"></t:dgCol>
   <t:dgCol title="备注" field="note" hidden="true"   width="120"></t:dgCol>
   <t:dgToolBar title="关联承保车商信息"  icon="icon-save" url="plyDealerInfoController.do?save" funname="saveClmPlyDealer"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  <script>
  

  /**
  * 关联理赔承保车商信息
  * @param title
  * @param url
  * @param gname
  * @return
  */
 function saveClmPlyDealer(title,url,gname) {
 	 gridname=gname;
     var clmDealerCode=$("#clmDealerCode").val();
 	 var plyDealerCodes = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
     	$.dialog.setting.zIndex = getzIndex(true);
     	$.dialog.confirm('你确定关联理赔承保车商信息吗?', function(r) {
 		   if (r) {
 				for ( var i = 0; i < rows.length; i++) {
 					plyDealerCodes.push(rows[i].dealerCode);
 				}
 				$.ajax({
 					url : url,
 					type : 'get',
 					data : {
 						clmDealerCode : clmDealerCode,
 						plyDealerCodes : plyDealerCodes.join(',')
 					},
 					cache : false,
 					success : function(data) {
 						var d = $.parseJSON(data);
 						if (d.success) {
 							var msg = d.msg;
 							tip(msg);
 							reloadTable();
 							$("#"+gname).datagrid('unselectAll');
 							plyDealerCodes='';
 						}
 					}
 				});
 			}
 		}).zindex();
 	} else {
 		tip("请选择需要关联的承保数据");
 	}
 }

  </script>