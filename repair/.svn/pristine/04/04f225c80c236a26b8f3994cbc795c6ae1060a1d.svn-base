<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>监控中心</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body  scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="monitorController.do?save">
			<input id="id" name="id" type="hidden" value="${monitorPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆种类:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="modelType" name="modelType" ignore="ignore"
							   value="${monitorPage.modelType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商联系人姓名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="contactsName" name="contactsName" ignore="ignore"
							   value="${monitorPage.contactsName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							查勘员:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="surveyor" name="surveyor" ignore="ignore"
							   value="${monitorPage.surveyor}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							推荐车商名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dealerName" name="dealerName" ignore="ignore"
							   value="${monitorPage.dealerName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							实际车商名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="relDealerName" name="relDealerName" ignore="ignore"
							   value="${monitorPage.relDealerName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三者车车牌:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="threecarPlate" name="threecarPlate" ignore="ignore"
							   value="${monitorPage.threecarPlate}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三者车车架号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="threecarFrm" name="threecarFrm" ignore="ignore"
							   value="${monitorPage.threecarFrm}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							归属机构代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dptCde" name="dptCde" ignore="ignore"
							   value="${monitorPage.dptCde}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车商代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="repairSerialNo" name="repairSerialNo" 
							   value="${monitorPage.repairSerialNo}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							操作人代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cOperatorCde" name="cOperatorCde" ignore="ignore"
							   value="${monitorPage.cOperatorCde}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							操作人机构:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cOperatorDpt" name="cOperatorDpt" ignore="ignore"
							   value="${monitorPage.cOperatorDpt}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							送修流程进行状态（1,送修中 2,送修成功）:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="repairSts" name="repairSts" ignore="ignore"
							   value="${monitorPage.repairSts}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							开始时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="tStrtTm" name="tStrtTm" ignore="ignore"
							   value="<fmt:formatDate value='${monitorPage.tStrtTm}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结束时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="tEndTm" name="tEndTm" ignore="ignore"
							   value="<fmt:formatDate value='${monitorPage.tEndTm}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							创建时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="tCrtTm" name="tCrtTm" ignore="ignore"
							   value="<fmt:formatDate value='${monitorPage.tCrtTm}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建人代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cCrtCde" name="cCrtCde" ignore="ignore"
							   value="${monitorPage.cCrtCde}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							更新时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="tUpdTm" name="tUpdTm" ignore="ignore"
							   value="<fmt:formatDate value='${monitorPage.tUpdTm}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							更新人代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cUpdCde" name="cUpdCde" ignore="ignore"
							   value="${monitorPage.cUpdCde}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							报案号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="reportId" name="reportId" ignore="ignore"
							   value="${monitorPage.reportId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车牌号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="plateNo" name="plateNo" ignore="ignore"
							   value="${monitorPage.plateNo}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							定损员:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="assessmenter" name="assessmenter" ignore="ignore"
							   value="${monitorPage.assessmenter}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>