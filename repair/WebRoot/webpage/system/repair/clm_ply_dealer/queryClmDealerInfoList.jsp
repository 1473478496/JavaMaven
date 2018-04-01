<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>承保车商信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body style="overflow-y: scroll" scroll="yes">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
			<table style="width: 100%;TABLE-LAYOUT: fixed;word-break:break-all;"  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="center">
						<label class="Validform_label">
							车商代码
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商名称
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商具体地址
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商联系人姓名
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商联系人手机
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商售后经理姓名
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车商售后经理电话
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							保险公司车商维护专员姓名
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							保险公司车商维护专员手机
						</label>
					</td>
					<!-- <td align="center">
						<label class="Validform_label">
							资质类型
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							是否有效
						</label>
					</td> -->
					
				</tr>
				 <c:if test="${ empty plyList}">
					<tr>
						<td colspan="11" align="center">
							<span class="Validform_checktip">暂无数据</span>
						</td>
					</tr>	
				</c:if>
			<c:forEach items="${plyList }" var="ply">
				<tr style="height: 20px;">
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ply.dealerCode}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ply.dealerName}
						</span>
					</td>
						<td class="value" align="center">
						<span class="Validform_checktip" >
						${ply.dealerAddress}
						
						</span>
					</td>
					<td class="value"  align="center">
						<span class="Validform_checktip">
						${ply.contactsName}
						</span>
					</td>
					<td class="value"  align="center">
						<span class="Validform_checktip">
						${ply.contactsTel}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">${ply.directorName}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ply.directorTel}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ply.agent}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ply.agentTel }
						</span>
					</td>
				<%-- 	<td class="value" align="center">
						<span class="Validform_checktip">
						 <c:if test="${ply.dealerType==1 }">
						 	一级经销商（含4S店）
						 </c:if>
						 <c:if test="${ply.dealerType==2 }">
						 	二级经销商
						 </c:if>
						 <c:if test="${ply.dealerType==3 }">
						 	修理厂
						 </c:if>
						</span>
					</td> --%>
				<%-- 	<td class="value" align="center">
						<span class="Validform_checktip">
						<c:if test="${ply.isvalid==0}">
						 	是
						 </c:if>
						 <c:if test="${ply.isvalid==1 }">
						 	否
						 </c:if>
						</span>
					</td> --%>
				</tr>
				</c:forEach>
			</table>
	</t:formvalid>
 </body>