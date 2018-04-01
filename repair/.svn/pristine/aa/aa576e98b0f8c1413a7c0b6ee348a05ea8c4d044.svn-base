<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>消息模板</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" action="" tiptype="1">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								车商代码:
							</label>
						</td>
						<td class="value">
							<input id="repairSerialNo" name="repairSerialNo" type="text" style="width: 150px" class="inputxt"  
  							value='${messageStatePage.repairSerialNo}'>  
						</td>
					</tr> --%>
			<tr>
				<td align="right"><label class="Validform_label"> 报案号: </label></td>
				<td class="value"><input id="reportNo" name="reportNo" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.reportNo}'></td>
			</tr>

				<tr style="height: 20px;">
				<td align="right"><label class="Validform_label"> 报案时间: </label></td>
				<td class="value"><input id="reportTm" name="reportTm" type="text" style="width: 150px" class="inputxt" value='${repairMainInfo.reportTm}'></td>
					<%-- <td class="value" align="center"><span class="Validform_checktip"> ${repairMainInfo.reportTm}</span></td> --%>
				</tr>
			

			<tr>
				<td align="right"><label class="Validform_label"> 车架号: </label></td>
				<td class="value"><input id="frmNo" name="frmNo" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.frmNo}'></td>
			</tr>
			<tr>
				<tr>
				<td align="right">
							<label class="Validform_label">
								车牌号:
							</label>
						</td>
						<td class="value">
						     <input id="plateNo" name="" plateNo"" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.plateNo}'>  
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">短信类型:</label>
					</td>
					<td class="value">     
				
					<t:dictSelect field="sendType" type="list" typeGroupCode="sendType" defaultVal="${messageStatePage.msgType}" hasLabel="false" title="短信类型"></t:dictSelect>
				</td>
			</tr>
			
					<tr>
					<td align="right">
						<label class="Validform_label">接收人:</label>
					</td>
					<td class="value">     
				      <input id="sendeeName" name="sendeeName" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.sendeeName}'>  
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">接收人电话号码:</label>
					</td>
					<td class="value">     
				      <input id="sendeePhone" name="sendeePhone" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.sendeePhone}'>  
					</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模板内容:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="msgContent" style="width:550px;" class="inputxt" rows="6" name="msgContent">${messageStatePage.msgContent}</textarea>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">发送状态:</label>
					</td>
					<td class="value">     
				<%-- 	<c:if test=""><input id="state" name="state" type="text" style="width: 150px" class="inputxt"   value="成功">  </c:if>
					<c:if test="${messageStatePage.state=='1'}"><input id="state" name="state" type="text" style="width: 150px" class="inputxt"   value="失败">  </c:if> --%>
					<t:dictSelect field="sendState" type="list" typeGroupCode="sendState" defaultVal="${messageStatePage.state}" hasLabel="false" title="发送状态"></t:dictSelect>   
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">发送时间:</label>
					</td>
					<td class="value">     
						<input id="sendStartTm" name="sendStartTm" type="text" style="width: 150px" class="inputxt" value='${messageStatePage.sendStartTm}'>
					</td>
				</tr>
			
		</table>
		</t:formvalid>
 </body>