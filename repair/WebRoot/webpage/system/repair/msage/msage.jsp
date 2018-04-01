<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车商短信发送设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  function openCurrentDepart() {
		$.dialog.setting.zIndex = getzIndex(); 
		
		var orgIds = $("#orgIds").val();
		$.dialog({content: 'url:departController.do?currentDepart&orgIds='+orgIds, zIndex: 2100, title: '组织机构列表', lock: true, width: '600px', height: '420px', opacity: 0.4, button: [
		   {name: '<t:mutiLang langKey="common.confirm"/>',callback: callbackCurrentDepart, focus: true},
		   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	function callbackCurrentDepart() {
		  var iframe = this.iframe.contentWindow;
		  var rowsData = iframe.$('#currentDepartList').datagrid('getSelections');
		  if (!rowsData || rowsData.length==0) {
				tip('请选择机构');
				return;
			}
			if (rowsData.length>1) {
				tip('请选择一条机构');
				return;
			}
			var orgId=rowsData[0].id;
			 $("#dptCde").val(orgId);
			var orgName=rowsData[0].departname;
			 $("#orgName").val(orgName);
	}
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
			$("#dealerNo").val(dealerCode);
			var dealerName=rowsData[0].dealerName;
			$("#dealerName").val(dealerName);	
			var dptCde=rowsData[0].dptCde;
			$("#dptCde").val(dptCde);
	}
  </script>
 </head>
 <body style=" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="msageController.do?save">
			<input id="id" name="id" type="hidden" value="${msagePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
					<td align="right">
						<label class="Validform_label">
							派修类型:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="type" id="type">
						<option value="1" <c:if test="${msagePage.type=='1'}">selected="selected"</c:if>>送修</option>
			            <option value="2" <c:if test="${msagePage.type=='2'}">selected="selected"</c:if>>返修</option>
						</select>
					</td>
				</tr>
			<tr>
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
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dealerName"  readonly="readonly"  name="dealerName" ignore="ignore"
							   value="${msagePage.dealerName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label" >
							查勘员:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="surveyorName" name="surveyorName" ignore="ignore"
							   value="${msagePage.surveyorName}"/> --%>
						<span class="Validform_checktip"></span>
						
						
						  <select name="surveyorFlag" id="surveyorFlag">
				              <option value="0" <c:if test="${msagePage.surveyorFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
				              <option value="1" <c:if test="${msagePage.surveyorFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
	          			  </select>
						
						
						
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							定损员:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="assessName" name="assessName" ignore="ignore"
							   value="${msagePage.assessName}"/> --%>
						<span class="Validform_checktip"></span>
						<select name="assessFlag" id="assessFlag">
						<option value="0" <c:if test="${msagePage.assessFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.assessFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
			            </select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="customerName" name="customerName" ignore="ignore"
							   value="${msagePage.customerName}"/> --%>
						<span class="Validform_checktip"></span>
						<select name="customerFlag" id="customerFlag">
						<option value="0" <c:if test="${msagePage.customerFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.customerFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商联系人:
						</label>
					</td>
					<td class="value">
						<%-- input class="inputxt" id="contactsName" name="contactsName" ignore="ignore"
							   value="${msagePage.contactsName}"/> --%>
						<span class="Validform_checktip"></span>
						<select name="contactsFlag" id="contactsFlag">
						<option value="0" <c:if test="${msagePage.contactsFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.contactsFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商售后经理:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="directorName" name="directorName" ignore="ignore"
							   value="${msagePage.directorName}"/> --%>
						<span class="Validform_checktip"></span>
						<select name="directorFlag" id="directorFlag">
						<option value="0" <c:if test="${msagePage.directorFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.directorFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商理赔引导专员:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="usherFlag" id="usherFlag">
						<option value="0" <c:if test="${msagePage.usherFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.usherFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							保险公司车商维护专员:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="directorName" name="directorName" ignore="ignore"
							   value="${msagePage.directorName}"/> --%>
						<span class="Validform_checktip"></span>
						<select name="agentFlag" id="agentFlag">
						<option value="0" <c:if test="${msagePage.agentFlag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.agentFlag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					<input type="text"  name="dptCde" id="dptCde" value="${msagePage.dptCde}"  style="display: none"   readonly="readonly"/>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							机构名称:
						</label>
					</td>
					<td class="value">
						<input type="text"  name="dptCde" id="dptCde" value="${msagePage.dptCde}"  readonly="readonly"/>
						 <input type="text"  name="orgName" id="orgName" value="${msagePage.orgName}" readonly="readonly"/>
						<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openCurrentDepart()">选择</a>
          			  <span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							其他1:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="other1Flag" id="other1Flag">
						<option value="0" <c:if test="${msagePage.other1Flag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.other1Flag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							其他2:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="other2Flag" id="other2Flag">
						<option value="0" <c:if test="${msagePage.other2Flag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.other2Flag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							其他3:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="other3Flag" id="other3Flag">
						<option value="0" <c:if test="${msagePage.other3Flag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.other3Flag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							其他4:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="other4Flag" id="other4Flag">
						<option value="0" <c:if test="${msagePage.other4Flag=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
			            <option value="1" <c:if test="${msagePage.other4Flag=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否有效:
						</label>
					</td>
					<td class="value">
						<span class="Validform_checktip"></span>
						<select name="isvalid" id="isvalid">
						<option value="0" <c:if test="${msagePage.isvalid=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"/></option>
						<option value="1" <c:if test="${msagePage.isvalid=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"/></option>
						</select>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>