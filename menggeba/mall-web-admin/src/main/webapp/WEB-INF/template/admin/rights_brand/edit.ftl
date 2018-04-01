<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.brand.edit")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
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
	var $adButton = $("#adButton");
	[@flash_message /]
	
	$adButton.browser();
	$browserButton.browser();
 	
	
	$type.change(function() {
		if ($(this).val() == "text") {
			$logo.val("").prop("disabled", true);
			$browserButton.prop("disabled", true);
		} else {
			$logo.prop("disabled", false);
			$browserButton.prop("disabled", false);
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			advertised: "required",
			logo: "required",
			status: "required",
			order: "digits"
		}
	});
	
});
function openImg(url){
	open(url);
}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.brand.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${brand.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Brand.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${brand.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>宣传图:</th>
				<td>
					<span class="fieldSet">
						<input type="text" id="advertised" name="advertised" value="${brand.advertised}" class="text" maxlength="200" readonly="readonly" />
						<input type="button" id="adButton" class="button" value="${message("admin.browser.select")}"/>
						[#if brand.advertised??]
						<a href="javascript:openImg('${brand.advertised}');">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					${message("Brand.type")}:
				</th>
				<td>
					<select id="type" name="type">
						[#list types as type]
							<option value="${type}"[#if type == brand.type] selected="selected"[/#if]>${message("Brand.Type." + type)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Brand.logo")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id="logo" name="logo" class="text" value="${brand.logo}" maxlength="200"[#if brand.type == "text"] disabled="disabled"[/#if] />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}"[#if brand.type == "text"] disabled="disabled"[/#if] />
						[#if brand.logo??]
						<a href="javascript:openImg('${brand.logo}');">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.url")}:
				</th>
				<td>
					<input type="text" name="url" class="text" value="${brand.url}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>状态:
				</th>
				<td>						
					<select id="status" name="status" class="text">	
						<option value="">${message("admin.common.choose")}</option>		
						<option value="0" [#if !brand.status]selected[/#if]>停用</option>					
						<option value="1" [#if brand.status]selected[/#if]>启用</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Brand.introduction")}:
				</th>
				<td>
					<textarea id="editor" name="remark" class="editor">${brand.remark?html}</textarea>
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