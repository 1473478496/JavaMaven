<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌型号信息查询</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lableController.do?save">
			<input id="id" name="id" type="hidden" value="${lablePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车型代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="modelCode" name="modelCode" 
							   value="${lablePage.modelCode}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车型名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="modelName" name="modelName" ignore="ignore"
							   value="${lablePage.modelName}">
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
						<input class="inputxt" id="moedlBrand" name="moedlBrand" ignore="ignore"
							   value="${lablePage.moedlBrand}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌编码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="moedlBrand" name="moedlBrand" ignore="ignore"
							   value="${lablePage.brandCode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							注释:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="notes" name="notes" ignore="ignore"
							   value="${lablePage.notes}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			
			</table>
		</t:formvalid>
 </body>