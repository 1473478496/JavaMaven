<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.list")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
</head>
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
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.poster.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post"  enctype="multipart/form-data">
		<div class="tabContent">
			
			<table id="posterTable" class="input">
				<tr>
					<th>
						${message("Poster.name")}:
					</th>
					<td colspan="3">
							<input type="text" name="name" class="text" maxlength="200" />
						</span>
					</td>
				</tr>
				<tr>
					<th>
						${message("Poster.imgUrl")}:
					</th>
					<td colspan="3">
						<span class="fieldSet">
							<input type="text" name="imgUrl" class="text" maxlength="200" />
							<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						</span>
					</td>
				</tr>
				<tr>
					<th>
						${message("Poster.url")}:
					</th>
					<td colspan="3">
						<span class="fieldSet">
							<input type="file" name="file" class="text" maxlength="200" />
						</span>
					</td>
				</tr>
				
				<tr>
					<th>
						${message("Poster.ad")}:
					</th>
					<td colspan="3">
						<select name="ad.id">
						[#list ads as ad]
							<option value="${ad.id}">${ad.title}</option>
						[/#list]
						</select>
					</td>
				</tr>
				<tr>
					<th>
						${message("Poster.remark")}:
					</th>
					<td colspan="3">
						<input type="text" name="remark" class="text" maxlength="200" />
					</td>
				</tr>
			</table>
			<table class="input">
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
		</div>
	</form>
</body>
</html>