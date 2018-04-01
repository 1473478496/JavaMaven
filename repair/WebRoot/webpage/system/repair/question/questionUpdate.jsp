<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>标题</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/tools/ckfinder.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery-1.7.1.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery.selection.js"></script>
  <script type="text/javascript">
  
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="questionController.do?doUpdate" tiptype="1">					
		<input id="id" name="id" value="${questionPage.id}" style="display: none"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>			
				<td align="right">
					<label class="Validform_label">
						问题标题:
					</label>
				</td>
				<td class="value">
						<input name="questionName" id="questionName" value="${questionPage.questionName}" style="width:300px;background-color: #FFFFFF"/>
				</td>
			</tr>
			<tr>			
				<td align="right">
					<label class="Validform_label">
						解答:
					</label>
				</td>
				<td class="value">
						<textarea id="questionContent" name="questionContent" style="width:600px;background-color: #FFFFFF" class="inputxt" rows="20" >${questionPage.questionContent}</textarea>
				</td>
			</tr>
		</table>
		</t:formvalid>
 </body>
  <script></script>		