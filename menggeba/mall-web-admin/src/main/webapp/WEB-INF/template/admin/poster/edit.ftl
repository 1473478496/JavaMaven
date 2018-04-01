<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.ad.edit")} - Powered By www.mgb.cn</title>
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
	var $contentTr = $("#contentTr");
	var $pathTr = $("#pathTr");
	var $path = $("#path");
	var $browserButton = $("#browserButton");
	
	[@flash_message /]
	
	
	$browserButton.browser();
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.poster.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${poster.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Poster.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${poster.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
					<th>
						<span class="requiredField">*</span>${message("Poster.imgUrl")}:
					</th>
					<td colspan="3">
						<span class="fieldSet">
							<input type="text" name="imgUrl" class="text" maxlength="220"  value="${poster.imgUrl}"/>
							<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						</span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Poster.url")}:
					</th>
					<td colspan="3">
						<span>${poster.url}<input type="hidden" name="url" maxlength="220" value="${poster.url}"></span>
						<input type="file" name="file" value="" class="text" maxlength="220" />
					</td>
				</tr>
				
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Poster.ad")}:
					</th>
					<td colspan="3">
						<input type = "hidden" name="adPosition.id" value="1">
						<select name="ad.id">
						[#list ads as ad]
							<option value="${ad.id}" [#if poster.ad.id == ad.id]selected = "selected"[/#if]>${ad.title}</option>
						[/#list]
						</select>
					</td>
				</tr>
				<tr>
					<th>
						${message("Poster.remark")}:
					</th>
					<td colspan="3">
						<input type="text" name="remark" class="text" maxlength="200" value="${poster.remark}"/>
					</td>
				</tr>
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