<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车商维修品牌设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  function openCurrentDealer() {
		$.dialog.setting.zIndex = getzIndex(); 
		var brand="AA01";
		$.dialog({content: 'url:dealerInfoController.do?list&brand='+brand, zIndex: 2100, title: '车商列表', lock: true, width: '800px', height: '550px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>',callback: callbackCurrentDealer, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	function callbackCurrentDealer() {
		  var iframe = this.iframe.contentWindow;
		  var rowsData = iframe.$('#dealerInfoList').datagrid('getSelections');
		  if (!rowsData || rowsData.length==0) {
				tip('请选择车商');
				return;
			}
			if (rowsData.length>1) {
				tip('请选择一条车商信息');
				return;
			}
			var dealerCode=rowsData[0].dealerCode;
			$("#dealerCode").val(dealerCode);
			var dealerName=rowsData[0].dealerName;
			$("#dealerName").val(dealerName);
			var dptCde=rowsData[0].dptCde;
			$("#dptCde").val(dptCde);
			
	}
	
	
	
	function openLable() {
		$.dialog.setting.zIndex = getzIndex(); 
		$.dialog({content: 'url:lableController.do?lableList2', zIndex: 2100, title: '品牌列表', lock: true, width: '800px', height: '550px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>',callback: callbackLable, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	function callbackLable() {
        var iframe = this.iframe.contentWindow;
        var rows = iframe.$('#lableList1').datagrid('getSelections');
		var brandCodes=[];
		var moedlBrands=[];
        for (var i = 0; i < rows.length; i++) {
        	brandCodes.push(rows[i]['brandCode']);
        	moedlBrands.push(rows[i]['moedlBrand']);
        }
        brandCodes.join(',');
        moedlBrands.join(',');
		$("#brandCode").val(brandCodes);
	    $("#brandName").val(moedlBrands);		  	
	}
	function other(){
		 $("#brandCode").val("OTHER");
	
		 $("#brandName").val("其他");	
	} 
	
  </script>
 </head>
 <body style="overflow-y: scroll" scroll="yes">
  <t:formvalid formid="formobj" dialog="true"  usePlugin="password" layout="table" action="brandController.do?save">
			<input id="id" name="id" type="hidden" value="${brandPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dealerCode" name="dealerCode" 
							   value="${brandPage.dealerCode}" datatype="*">
					   <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openCurrentDealer()">选择</a>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dealerName" name="dealerName" ignore="ignore"
							   value="${brandPage.dealerName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="brandCode" name="brandCode" ignore="ignore"
							   value="${brandPage.brandCode}">
					    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="lableSearch" onclick="openLable()">选择</a>
					    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="lableSearch" onclick="other()">其他</a>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="brandName" name="brandName" ignore="ignore"
							   value="${brandPage.brandName}">
					   	<input type="text"  name="dptCde" id="dptCde" value="${brandPage.dptCde }"  style="display: none" />
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							权重:
						</label>
					</td>
					<td class="value">
				
						<input class="inputxt" id="weight" name="weight"
							   value="${brandPage.weight}" datatype="n1-10">
						<span class="Validform_checktip"> <span class="Validform_checktip"><t:mutiLang langKey="fill.weight"/></span></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							就近系数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="ratio" name="ratio" datatype="n1-10"
							   value="${brandPage.ratio}">
						<span class="Validform_checktip"> <span class="Validform_checktip"><t:mutiLang langKey="fill.ratio"/></span></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>