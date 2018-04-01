<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.brand.add")} - Powered By www.mgb.cn</title>
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
	var $picture = $("#picture");
	var $browserButton2 = $("#browserButton2");
	
	[@flash_message /]
	
	$browserButton.browser();
	$browserButton2.browser();
	
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
			logo: "required",
			picture: "required",
			order: "digits",
			url: {
				url:true
			}
		},
		messages: {
			url: {
				url: "必须以 http:// 或  https:// 开头"
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.brand.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Brand.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Brand.type")}:
				</th>
				<td>
					<select id="type" name="type">
						[#list types as type]
							<option value="${type}">${message("Brand.Type." + type)}</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Brand.logo")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id="logo" name="logo" class="text" maxlength="200" disabled="disabled" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" disabled="disabled" />
					</span>
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.picture")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id="picture" name="picture" class="text" maxlength="200" disabled="disabled" />
						<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" disabled="disabled" />
					</span>
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.wurl")}:
				</th>
				<td>
					<input type="text" name="wurl" class="text" maxlength="200" />
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.url")}:
				</th>
				<td>
					<input type="text" name="url" class="text" maxlength="200" />
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.isPopularity")}:
				</th>
				<td>
					<input type="checkbox" name="isPopularity"/>
				</td>
			</tr>
			
			
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="sorts" class="text" maxlength="9" />
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Brand.introduction")}:
				</th>
				<td>
					<textarea id="editor" name="introduction" class="editor"></textarea>
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