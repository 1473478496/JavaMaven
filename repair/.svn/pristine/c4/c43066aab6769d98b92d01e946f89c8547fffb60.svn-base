<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车商短信发送设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
	function openOrg() {
		$.dialog.setting.zIndex = getzIndex(); 
		$.dialog({content: 'url:openOrgController.do?orgList', zIndex: 2100, title: '机构列表', lock: true, width: '800px', height: '550px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>',callback: callbackOpenOrg, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	function callbackOpenOrg() {
		  var iframe = this.iframe.contentWindow;
		  var rowsData = iframe.$('#orgList').datagrid('getSelections');
		  if (!rowsData || rowsData.length==0) {
				tip('请选择机构');
				return;
			}
			var orgCode=rowsData[0].id;
			$("#orgCode").val(orgCode);
			var departName=rowsData[0].departname;
			$("#orgName").val(departName);	
	}
  </script>
 </head>
 <body style=" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="openOrgController.do?save">
			<input type="hidden" name="id" id="id" value="${openOrgPage.id }"/>
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
					<td align="right">
						<label class="Validform_label">
							机构码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt"  id="orgCode" name="orgCode" value="${openOrgPage.orgCode }" readonly="readonly" datatype="*">
						 <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="openOrgSearch" onclick="openOrg()">选择</a>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			<tr>
					<td align="right">
						<label class="Validform_label">
							机构名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orgName" name="orgName" 
							   value="${openOrgPage.orgName}" readonly="readonly" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							车商代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dealerNo" name="dealerNo" 
							   value="${msagePage.dealerNo}" readonly="readonly" datatype="*">
					   <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openCurrentDealer()">选择</a>
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
 </body>