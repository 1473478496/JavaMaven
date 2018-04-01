<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>出险车维修管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="${webRoot }/plug-in/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${webRoot }/plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript">
 $(function () {
	var flag = false;
	var fileitem = "";
	var fileKey = "";
	var serverMsg = "上传成功";
	 $('#file_upload').uploadify({
         'debug'         : false,   
         'auto'          : false,             //是否自动上传,   
         'buttonClass'   : 'haha',           //按钮辅助class   
         'buttonText'    : '浏览',       //按钮文字   
         'height'        : 30,               //按钮高度   
         'width'         : 100,              //按钮宽度   
         'fileObjName'   : 'files',           //默认 Filedata, $_FILES控件名称   
         'fileSizeLimit' : '6MB',          //文件大小限制 0为无限制 默认KB   
         'fileTypeDesc'  : '文件格式',       //图片选择描述   
         'fileTypeExts'  : '*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',//文件后缀限制 默认：'*.*'   
         'formData'      : {'reportNo' : $("#reportNo").val(),'id' : $("#id").val(),'policyNo' : $("#policyNo").val()},//传输数据JSON格式   
         //'overrideEvents': ['onUploadProgress'],  // The progress will not be updated   
         'progressData'  : 'speed',             //默认percentage 进度显示方式   fileKey
         'queueID'       : 'fileQueue',              //默认队列ID   
         'queueSizeLimit': 20,                   //一个队列上传文件数限制   
         'removeCompleted' : true,               //完成时是否清除队列 默认true   
         'removeTimeout'   : 5,                  //完成时清除队列显示秒数,默认3秒   
         'requeueErrors'   : false,              //队列上传出错，是否继续回滚队列   
         'successTimeout'  : 5,                  //上传超时   
         'uploadLimit'     : 99,                 //允许上传的最多张数   
         'swf'  : '${webRoot }/plug-in/uploadify/uploadify.swf',
         'uploader': '${webRoot }/uploadController/upload.do', //服务器端脚本   

         //修改formData数据   
         'onUploadStart' : function(file) {
            // $("#file_upload").uploadify("settings", "someOtherKey", 2); 
             //$("#reportNo").uploadify("settings", "reportNo", reportNo);
         },   
         //删除时触发   
         'onCancel' : function(file) {
             //alert('The file ' + file.name + '--' + file.size + ' was cancelled.');   
         },   
         //清除队列   
         'onClearQueue' : function(queueItemCount) {   
             //alert(queueItemCount + ' file(s) were removed from the queue');   
         },   
         //调用destroy是触发   
         'onDestroy' : function() {   
             alert('我被销毁了');   
         },   
         //每次初始化一个队列是触发   
         'onInit' : function(instance){   
             //alert('The queue ID is ' + instance.settings.queueID);   
         },   
         //上传成功   
         'onUploadSuccess' : function(file, data, response) {
             //alert(file.name + ' | ' + response + ':' + data);
         },   
         //上传错误   
         'onUploadError' : function(file, errorCode, errorMsg, errorString) {   
             //alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
             switch (errorCode) {
					case -100:
						tip("您上传的文件数量已经超出系统限制的"+ $('#file_upload').uploadify('settings','queueSizeLimit') + "个文件！");
						break;
					case -110:
						tip("您上传的文件 ["+ file.name+ "] 大小超出系统限制的"+ $('#file_upload').uploadify('settings','fileSizeLimit')+ "大小！");
						break;
					case -120:
						tip("您上传的文件 [" + file.name + "] 大小异常！");
						break;
					case -130:
						tip("您上传的文件 [" + file.name + "] 类型不正确！");
						break;
					}
         },
         //上传汇总   
         'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {   
             $('#progress').html(totalBytesUploaded + ' bytes uploaded of ' + totalBytesTotal + ' bytes.');
             //tip(<span>文件上传成功:+totalBytesUploaded/1024 +KB 已上传 ,总数' + totalBytesTotal/1024 + ' KB.</span>');
         },   
         //上传完成   
         'onUploadComplete' : function(file) {
        	 if(file.filestatus=-4){//上传完成
        		 var win = frameElement.api.opener;
        		 win.reloadTable();
        	 }else if(file.filestatus=-3){//上传出现错误
        		 console.log("上传出现错误");
        	 }
             //alert('The file ' + file.name + ' finished processing.');   
         },
         onFallback : function(){alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");}
     }); 
 });
 function curDate(){
	 var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var second = date.getSeconds();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if(second >= 0 && second <=9){
	    	second = "0" + second;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + second;
	 $("#maintenanceTime").val(currentdate);
};
  </script>
</head>
<body style=" scroll=" no" onload="curDate()">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dangerController.do?update" beforeSubmit="a">
		<input id="id" name="id" type="hidden" value="${dangerPage.id}">
		<input id="reportNo" name="reportNo" value="${dangerPage.reportNo}" hidden="hidden" />
		<input id="policyNo" name="policyNo" value="${dangerPage.policyNo}" hidden="hidden" />
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 车牌号 </label></td>
				<td class="value"><input class="inputxt" id="plateNo" name="plateNo" value="${dangerPage.plateNo}" disabled="true" /> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车架号: </label></td>
				<td class="value"><input class="inputxt" id="frmNo" name="frmNo" value="${dangerPage.frmNo}" disabled="true" /> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 发动机号: </label></td>
				<td class="value"><input class="inputxt" id="engineNo" name="engineNo" value="${dangerPage.engineNo}" disabled="true" /> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车辆品牌: </label></td>
				<td class="value"><input class="inputxt" id="vehlcleName" name="vehlcleName" value="${dangerPage.vehlcleName}" disabled="disabled" /> <span class="Validform_checktip"></span></td>
			</tr>
			<c:if test="${dangerPage.maintenanceStatus=='01'}">
				<tr>
					<td align="right"><label class="Validform_label"> 维修状态: </label></td>
					<td class="value"><span class="Validform_checktip"></span> <select name="maintenanceStatus" id="maintenanceStatus">
							<option value="01" <c:if test="${dangerPage.maintenanceStatus=='01'}">selected="selected"</c:if>>维修中</option>
							<option value="02" <c:if test="${dangerPage.maintenanceStatus=='02'}">selected="selected"</c:if>>维修完成</option>
					</select></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 创建时间: </label></td>
					<td class="value"><input class="inputxt" id="crtTm" name="crtTm" value="${dangerPage.crtTm}" /> <span class="Validform_checktip"></span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 维修影像: </label></td>
					<td class="value">
						<div id="fileQueue">
							<span id="viewmsg"><input type="hidden" name="fileKey" id="fileKey" /> </span> <span id="file_upload"><input type="file" id="file_upload" name="maintenanceImage" /></span> <span class="Validform_checktip"></span> <a href="javascript:$('#file_upload').uploadify('upload','*')" class="easyui-linkbutton" id="btnUpload" data-options="plain:true,iconCls:'icon-save'">上传</a> <a href="javascript:$('#file_upload').uploadify('cancel','*')" class="easyui-linkbutton" id="btnCancelUpload" data-options="plain:true,iconCls:'icon-cancel'">取消</a>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${dangerPage.maintenanceStatus=='02'}">
				<tr>
					<td align="right"><label class="Validform_label"> 维修状态: </label></td>
					<td class="value"><span class="Validform_checktip"></span> <select name="maintenanceStatus" id="maintenanceStatus" disabled="true">
							<option value="01" <c:if test="${dangerPage.maintenanceStatus=='01'}">selected="selected"</c:if>>维修中</option>
							<option value="02" <c:if test="${dangerPage.maintenanceStatus=='02'}">selected="selected"</c:if>>维修完成</option>
					</select></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 创建时间: </label></td>
					<td class="value"><input class="inputxt" id="crtTm" name="crtTm" value="${dangerPage.crtTm}" disabled="true" /> <span class="Validform_checktip"></span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 维修影像: </label></td>
					<td class="value">
						<div id="fileQueue">
							<span id="viewmsg"><input type="hidden" name="fileKey" id="fileKey" /> <span id="file_upload"><input type="file" id="file_upload" name="maintenanceImage" /></span> <span class="Validform_checktip"></span> <a href="javascript:$('#file_upload').uploadify('upload','*')" class="easyui-linkbutton" id="btnUpload" data-options="plain:true,iconCls:'icon-save'">上传</a> <a href="javascript:$('#file_upload').uplaodify('cancel','*')" class="easyui-linkbutton" id="btnCancelUpload" data-options="plain:true,iconCls:'icon-cancel'">取消</a>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
		<input id="remarkIds" name="remarkIds" type="hidden" />
		<input id="maintenanceTimes" name="maintenanceTimes" type="hidden" />
		<input id="maintenanceInformations" name="maintenanceInformations" type="hidden" />
	</t:formvalid>
	<c:if test="${dangerPage.maintenanceStatus=='01'}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<input id="mainId" name="mainId" value="${dangerPage.id}" style="display: none" />
			<tr>
				<td align="right"><label class="Validform_label"> <input name="remarkId" type="hidden" /> 维修备注：
				</label></td>
				<td class="value"><span class="Validform_checktip"></span></td>
			</tr>

			<c:if test="${!empty mainList}">
				<c:forEach items="${mainList}" var="main" varStatus="i">
					<tr>
						<td align="right"><label class="Validform_label"> 日期： </label></td>
						<td class="value"><input class="inputxt" name="maintenanceTime1" type="text" value="${main.maintenanceTime}" /> <span class="Validform_checktip"></span></td>
					</tr>
					<tr>
						<td align="right"><label class="Validform_label"> 维修信息： </label></td>
						<td class="value"><input class="inputxt" name="maintenanceInformation1" value="${main.maintenanceInformation}" /> <span class="Validform_checktip"></span></td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td align="right"><label class="Validform_label">日期:</label></td>
				<td class="value"><input name="maintenanceTime" id="maintenanceTime" disabled="true" /> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">维修信息:</label></td>
				<td class="value"><textarea name="maintenanceInformation" id="maintenanceInformation" cols=40 rows=4 style="width:300px"></textarea> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"></label></td>
				<td class="value"><input type="button" value="新增维修信息" onclick="doAddRemark()"> <span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</c:if>
	<c:if test="${dangerPage.maintenanceStatus=='02'}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<input id="mainId" name="mainId" value="${dangerPage.id}" style="display: none" />
			<tr>
				<td align="right"><label class="Validform_label"> <input name="remarkId" type="hidden" /> 维修备注：
				</label></td>
				<td class="value"><span class="Validform_checktip"></span></td>
			</tr>
			<c:if test="${!empty mainList}">
				<c:forEach items="${mainList}" var="main" varStatus="i">

					<tr>
						<td align="right"><label class="Validform_label"> 日期： </label></td>
						<td class="value"><input class="inputxt" name="maintenanceTime1" type="text" value="${main.maintenanceTime}" disabled="true" /> <span class="Validform_checktip"></span></td>
					</tr>
					<tr>
						<td align="right"><label class="Validform_label"> 维修信息： </label></td>
						<td class="value"><input class="inputxt" name="

" value="${main.maintenanceInformation}" disabled="true" /> <span class="Validform_checktip"></span></td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td align="right"><label class="Validform_label">日期:</label></td>
				<td class="value"><input name="maintenanceTime" id="maintenanceTime" type="text" disabled="true" /> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">维修信息:</label></td>
				<td class="value"><textarea name="maintenanceInformation" id="maintenanceInformation" cols=40 rows=4 style="width:300px" disabled="true"></textarea> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"></label></td>
				<td class="value"><input type="button" value="新增维修信息" onclick="doAddRemark()" disabled="true"> <span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</c:if>

</body>
<script>
  	 function doAddRemark(){
  		 var mainId=$("#mainId").val();
  		 var maintenanceTime=$("#maintenanceTime").val();
  		 var maintenanceInformation=$("#maintenanceInformation").val();
  		 var url="dangerController.do?doAddRemark&mainId="+mainId+"&maintenanceTime="+maintenanceTime+"&maintenanceInformation="+encodeURIComponent(encodeURIComponent(maintenanceInformation));
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
				 setTimeout("window.location.reload()","500");
  				}
  				
  			}
  		});
  	 }
  	 
   	 function a(){
  		 var remarkIds="";
    	 var maintenanceTimes="";  
  		 var maintenanceInformations=""
  		 var remarkId=document.getElementsByName("remarkId");
    	 var maintenanceTime=document.getElementsByName("maintenanceTime1");  
  		 var maintenanceInformation =document.getElementsByName("maintenanceInformation1");
  		for (var i = 0, j = remarkId.length; i < j; i++){
  			remarkIds+=remarkId[i].value+",";
 		}
    	for(var i = 0, j = maintenanceTime.length; i < j; i++){
  			maintenanceTimes+=maintenanceTime[i].value+",";
  		}   
  		for (var i = 0, j = maintenanceInformation.length; i < j; i++){
  			maintenanceInformations+=maintenanceInformation[i].value+"*";
 		}
  	  $("#remarkIds").val(remarkIds);
      $("#maintenanceTimes").val(maintenanceTimes);   
  	  $("#maintenanceInformations").val(maintenanceInformations);
      return true;
  	 }
  </script>