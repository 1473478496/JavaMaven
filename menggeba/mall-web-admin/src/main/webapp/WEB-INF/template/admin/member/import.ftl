<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.common.listimport")} - Powered By www.mgb.cn</title>
<meta name="renderer" content="webkit">
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/upload/jquery.upload.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/json/json.js"></script>
<script type="text/javascript">
$().ready(function() {

    [@flash_message /]
 
   	
    var $importBtn = $("#importBtn");
   	
   	var $exportBtn = $("#exportBtn");
   
    
    $exportBtn.click(function() {  
        var url = 'export.jhtml';
		$('#exportForm').attr("action",url);
		$("#exportForm").submit();
		  	
     });
    
    
      $importBtn.click(function() {
		  	
		  	var filepath=$("#uploadFile").val();  
		  	if(filepath == null || filepath == ''){
			      alert('请选择文件！'); 
   			 	  return;
   			}else{
				var fileArr=filepath.split("//");  
				var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");  
				var filetype=fileTArr[fileTArr.length-1];  
				if(filetype!="xls"&&filetype!="xlsx"){  
				alert('上传文件必须为Excel文件格式！');
			    return;
				}
				else
				{
				   importInformation();
				  
				    
				}
			}
		  	
		 });
		 
		 
		  var importInformation=function(){
			 $importBtn.attr("disabled","disabled");
				$.upload({
					// 上传地址
					 url: "import.jhtml",
					// 文件域名字
					 //secureuri : false,//安全协议  
                  	 fileElementId: 'uploadFile', //id  
                     type : 'POST',  
					// 上传完成后, 返回json, text
				    async : false,
					params:{},
					dataType: 'json',
					// 上传之前回调,return true表示可继续上传
					onSend: function() {
						return true;
					},
					// 上传之前回调,return true表示可继续上传
					// 上传之后回调
					onComplate: function(message) {
					 if (typeof (message.type) != 'undefined')
					  {
						if (message.type == "success")
						{
						 
							$("#importResult").val("上传成功！");
						 
							   $("#importDetails").empty();
							 
						}else{
							 
							$("#importResult").val("导入失败！请检查EXCLE数据格式和内容");
							 
							$("#importDetails").html('<pre>'+message.content+'</pre>');
							 
						}
					    $importBtn.attr("disabled",false);
					  }
					},
					error:function(jqXhr, textStatus, error) {
						$("#importResult").val("导入失败！，请检查EXCLE数据格式和内容");
						 
						$("#importDetails").html('<pre>'+message.content+'</pre>');
						$importBtn.attr("disabled",false);						 
					}
				});
				
				
		   };
	 
		 
		 
		 
	
		 
    
});






</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.common.listimport")}
	</div>
    <form id="exportForm" method="post">
	</form>
	<form id="uploadFileForm" >
	
	
	
		<table class="input">
		
		     
			   
				<tr>
				
				    <th>1 &raquo;会 员 数 据   导  入</th>
					<td><input type="file" id="uploadFile" name="uploadFile" ><span style="color:red;">*</span>
					<input type="button" id="importBtn"  name="importBtn" class="button" value="导入会员信息" />
					<input type="text"  style="width:300px;border:none;color:#FF0000" 
						name="importResult" id="importResult" readonly="true"/>
					</td>
				 
				</tr>
			 
		</table>
		<table class="input">
		      <tr>
				    <th>
				   &nbsp;
				    </th>
				    <td>
				    </td>				   
			    </tr>
			<tr>
				<th>
				 2 &raquo;返回会员列表页
				</th>
				<td>
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?suit=false'" />
				</td>
			</tr>
		</table>
		 
		
	       <div name="importDetails" id="importDetails" style="color:#FF0000" />
				  
		 	
					
				 
		
		
	</form>
</body>
</html>