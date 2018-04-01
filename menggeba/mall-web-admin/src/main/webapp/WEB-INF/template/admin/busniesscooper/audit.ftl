<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>合作商户- Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	[@flash_message /]
	
	var $areaId = $("#areaId");
	 // 地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	//限制审核意见长度
	$("#auditDesc").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 119){
	    $(this).val($(this).val().substring(0,120));
	   }
	   var num = 120 - len;
	   $("#word").text(num);
	 });


	//审核通过
	$("#approve").click(function() {
		var auditDesc = $("#auditDesc").val();
	 
		$.ajax({
			url: "${base}/admin/busniesscooper/approve.jhtml",
			type: "POST",
			data: {Id: ${busniess.id}, auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/busniesscooper/list.jhtml";
				}else{
					$.message(message);
				}
			}
		});
	});
	
	//审核拒绝
	$("#unapprove").click(function() {
		var auditDesc = $("#auditDesc").val();	
		$.ajax({
			url: "${base}/admin/busniesscooper/unapprove.jhtml",
			type: "POST",
			data: {Id: ${busniess.id}, auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/busniesscooper/list.jhtml";
				}else{
					$.message(message);
				}
			}
		});
	});
});
</script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 合作商户
	</div>
	<input type="hidden" name="id" value="${busniess.id}"/>
 	<div class="content">
		<div class="returnsInfo">
			<p><span>商户信息:</span></p>
		 	<table>
				<tr>
					<th>
						商户名称:
					</th>
					<td>
						<a href="../busniesscooper/view.jhtml?Id=${busniess.id}">${busniess.busniessName}</a>
					</td>
					<th>
						商户地区:
					</th>
					<td>
						<span class="fieldSet">
                             <input type="hidden" id="areaId" name="areaId" value="${(busniess.area.id)!}" treePath="${(busniess.area.treePath)!}" \/>
						</span>
					</td>
				
				</tr>
				<tr>
					<th>
						商品目录:
					</th>
					<td colspan=3>
					    ${busniess.busniessContents}
					</td>
				</tr>

			</table>
		</div>
		
		<div class="audit">
			<p><span>处理</span></p>
			<div class="audit_desc"><p>审核意见:</p></div>
			<div class="audit_edit">
				<textarea class="textarea_input" id="auditDesc" rows="4" cols="25" ></textarea>
				<button id="approve">审核通过</button><button id="unapprove">审核拒绝</button>
			</div>
			
		</div>
	</div>
</body>
</html>

