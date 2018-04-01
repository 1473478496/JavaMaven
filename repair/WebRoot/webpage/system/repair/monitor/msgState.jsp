<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>监控中心</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  	function sendMsg(phoneCode,contect,id){
  		
  		var url="monitorController.do?sendMsg&phoneCode="+phoneCode+"&contect="+encodeURIComponent(encodeURIComponent(contect))+"&id="+id;
  		$.ajax({
  			async : false,
  			cache : false,
  			type : 'POST',
  			url : url,// 请求的action路径
  			error : function() {// 请求失败处理函数
  			},
  			success : function(data) {
  				var d = $.parseJSON(data);
  				if (d.success) {
  				var msg = d.msg;
  				tip(msg);
				/* setTimeout("window.location.reload()","500");  */
  				}
  				
  			}
  		});
  	}
  </script>
 </head>
 <body style="overflow-y: scroll" scroll="yes">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="monitorController.do?save">
			<table style="width: 98%;TABLE-LAYOUT: fixed"  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="center">
						<label class="Validform_label">
							接收人姓名
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							接收人电话号码
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							短信类型
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							短信发送内容
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							短信发送状态
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							接收时间
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							报案号
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							车架号
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							操作
						</label>
					</td>
				</tr>
				<c:if test="${ empty msList}">
					<tr>
						<td colspan="9" align="center">
							<span class="Validform_checktip">暂无数据</span>
						</td>
					</tr>	
				</c:if>
				<c:forEach items="${msList }" var="ms">
				<tr style="height: 20px;">
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ms.sendeeName}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ms.sendeePhone}
						</span>
					</td>
						<td class="value" align="center">
						<span class="Validform_checktip" >
						<t:dictSelect field="sendType" type="list"
								typeGroupCode="sendType" 
								defaultVal="${ms.msgType}" 
								hasLabel="false"  title="短信类型" readonly="readonly"></t:dictSelect>
						</span>
					</td>
					<td class="value"  style="word-wrap:break-word;" align="left">
					
						<span class="Validform_checktip">
						${ms.msgContent}
						</span>
					</td>
					<td class="value" style="word-wrap:break-word;" align="center">
						<span class="Validform_checktip">
						<t:dictSelect field="sendState" type="list"
								typeGroupCode="sendState" 
								defaultVal="${ms.state}" 
								hasLabel="false"  title="发送状态" readonly="readonly">
						</t:dictSelect>
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">${ms.crtTm}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ms.reportNo}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						${ms.frmNo}
						</span>
					</td>
					<td class="value" align="center">
						<span class="Validform_checktip">
						<a href="#" onclick="sendMsg('${ms.sendeePhone}','${ms.msgContent}','${ms.id}')" class="Validform_checktip">重新发送</a>
						</span>
					</td>
				</tr>
				</c:forEach>
				
			</table>
		</t:formvalid>
 </body>