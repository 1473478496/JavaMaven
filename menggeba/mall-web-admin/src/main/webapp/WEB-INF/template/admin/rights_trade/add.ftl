<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.brand.add")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $type = $("#type");
	var $logo = $("#logo");
	var $browserButton = $("#browserButton");
	var $picture = $("#picture");
	var $browserButton2 = $("#browserButton2");
	var $areaId = $("#areaId");
	
	[@flash_message /]
	
	$browserButton.browser();
	$browserButton2.browser();
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	$type.change(function() {
		if ($(this).val() == "text") {
			$logo.val("").prop("disabled", true);
			$browserButton.prop("disabled", true);
			$picture.val("").prop("disabled", true);
			$browserButton2.prop("disabled", true);
		} else {
			$logo.prop("disabled", false);
			$browserButton.prop("disabled", false);
			$picture.prop("disabled", false);
			$browserButton2.prop("disabled", false);
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			rightsBrandId: "required",
			status: "required",
			areaId: "required",
			rlogin: {
				required: true,
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.jhtml",
					cache: false
				}
			},
			order: "digits"
		},
		messages :{
			rlogin: {
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo;添加商户
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>商户名称:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>商户登录名:
				</th>
				<td>
					<input type="text" id="rlogin" name="rlogin" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>地址:</th>
				<td><span class="fieldSet">
					<input type="hidden" id="areaId" name="areaId" />
				</span></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>品牌:</th>
				<td><select name="rightsBrandId">
					<option value="">请选择...</option>
					[#list rightsBrands as rightsBrand]
						<option value="${rightsBrand.id }">${rightsBrand.name }</option>
					[/#list]
				</select></td>
			</tr>
			<tr>
				<th>商户logo:</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logoUrl" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					商户地址:
				</th>
				<td>
					<input type="text" id="addr" name="addr" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					商户电话:
				</th>
				<td>
					<input type="text" id="phone" name="phone" class="text" maxlength="200" />
				</td>
			</tr>
			<!-- <tr>
				<th>
					<span class="requiredField">*</span>状态:
				</th>
				<td>						
					<select name="status" style="width:190px;">	
						<option value="">请选择...</option>				
						<option value="using" >启用</option>
						<option value="unused">停用</option>					
					</select>
				</td>
			</tr> -->
			<tr>
				<th>是否置顶:</th>
				<td>
					<input type="checkbox" name="isTop" value="true" />
					<input type="hidden" name="_isTop" value="false" />
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					<input type="text" name="remark" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>