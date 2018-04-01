<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="messageStateList" title="派修短信查询" nowrap = "false" actionUrl="messageStateController.do?datagrid" 
 idField="id" queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车商代码" field="repairSerialNo" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="报案号" field="reportNo" query="true"    width="120"></t:dgCol>
   <t:dgCol title="车架号" field="frmNo" query="false" hidden="true"  width="130"></t:dgCol>
   <t:dgCol title="车牌号" field="plateNo" query="true"   width="100"></t:dgCol>
   <t:dgCol title="短信类型" field="msgType" dictionary="sendType" width="80"></t:dgCol>
   <t:dgCol title="发送对象 " field="sendeeName"  width="100"></t:dgCol>
   <t:dgCol title="手机号码" field="sendeePhone" query="true"  width="100"></t:dgCol>
   <t:dgCol title="短信内容" field="msgContent" width="320"></t:dgCol>
   <t:dgCol title="发送状态" field="state" dictionary="sendState" query="true" width="60"></t:dgCol>
    <t:dgCol title="派修类型" field="templateType" dictionary="msgTplType" query="true" width="60"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="70"></t:dgCol>
   <t:dgFunOpt funname="sendMsg(sendeePhone,msgContent,id)" title="重新发送" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="messageStateController.do?detailed" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出短信内容" icon="icon-putout" url="messageStateController.do?exportXls" funname="expExcels"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
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
  	
  	function expExcels(title,url){
  			var i=0;
  			var reportNo="";
  			var plateNo="";
  			var	sendeePhone="";
  			var templateType="";
  			if($("#messageStateListForm").Validform({tiptype:3}).check()){
  				$('#messageStateListtb').find('*').each(function(){	  				
	  				i++;
	  				if(i==8){
	  					 reportNo=$(this).val();
	  				}
	  				if(i==11){
	  					 plateNo=$(this).val();
	  				}
	  				if(i==14){
	  					sendeePhone=$(this).val();
	  				}
	  				if(i==17){
	  					templateType=$(this).val();
	  				}
  				});
  			}
  			url=url+"&reportNo="+reportNo+"&plateNo="+encodeURIComponent(encodeURIComponent(plateNo))+"&sendeePhone="+sendeePhone+"&templateType="+templateType;
  			location.href=url;
  	}
	
  </script>