<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑权益商品 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $type = $("#type");
	var $logo = $("#logo");
	var $isHome = $("#isHome");
	var $isPopularity = $("#isPopularity");
	var $homeImage = $("#homeImage");
	var $popularityImage = $("#popularityImage");
	var $browserButton = $("#browserButton");
	[@flash_message /]
	
	$browserButton.browser();
 	
	
	//是否首页展示
	$isHome.change(function() {	  
		if ($(this).prop("checked")) { 
			$homeImage.prop("readonly", false);
			$browserButton1.prop("disabled", false);
			$homeImage.css({"background-color":""});		
		} else {
			$homeImage.prop("readonly", true);
			$browserButton1.prop("disabled", true);
			$homeImage.css({"background-color":"#888"});
		}
	});
	//是否人气商品
	$isPopularity.change(function() {	  
		if ($(this).prop("checked")) { 
			$popularityImage.prop("readonly", false);
			$browserButton2.prop("disabled", false);
			$popularityImage.css({"background-color":""});		
		} else {
			$popularityImage.prop("readonly", true);
			$browserButton2.prop("disabled", true);
			$popularityImage.css({"background-color":"#888"});
		}
	});
	$("#hrefhome").click(function(){
		open($homeImage.val());
	});
	$("#hrefpopularity").click(function(){
		open($popularityImage.val());
	});
	
	$("#hreflogo").click(function(){
		open($logo.val());
	});
	
		// 表单验证
	$inputForm.validate({
		rules: {
			rightsCategoryId: "required",
			brandId: "required",
			status: "required",
			name: "required",
			pricePoint: "required",
			startDate: "required",
			endDate: "required",
			stock: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.jhtml",
					cache: false
				}
			},
			price: {
				required: true,
				pattern:/^\d+(\.\d{2})?$/,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			
			marketPrice: {
				required: true,
				pattern:/^\d+(\.\d{2})?$/,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			weight: "digits",
			stock: "digits",
			point: "digits"
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			}
		}
		
	});
	
});
	
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 生成权益码
	</div>
	<form id="inputForm" action="create.jhtml" method="post">
		<input type="hidden" name="rightsId" value="${rights.id}" />
		<table class="input">
			<tr>
				<th>名称:</th>
				<td>${rights.name }</td>
			</tr>
			<tr>
				<th>以生成数量:</th>
				<td>${rights.rightsCode?size }</td>
			</tr>
			<tr>
				<th>已使用数量:</th>
				<td>
					[#assign num=0]
					[#list rights.rightsCode as code]
						[#if code.isUsed]
							[#assign num=num+1]
						[/#if]
					[/#list]
					${num }
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>生成数量:</th>
				<td><input type="text" id="count" name="count" class="text" value="10" maxlength="6" /></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>