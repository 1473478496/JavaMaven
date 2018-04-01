<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title> - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑权益分类
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="parentId" value="1" />
		<input type="hidden" name="id" value="${rightsCategory.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" value="${rightsCategory.name}" maxlength="200" />
				</td>
			</tr>
			
			<tr class="brands">
				<th>
					权益品牌:
				</th>
				<td>
					[#list brands as brand]
						<label>
							<input type="checkbox" name="brandIds" value="${brand.id}"[#if rightsCategory.rightsBrands?seq_contains(brand)] checked="checked"[/#if] />${brand.name}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>排序:</th>
				<td>
					<input type="text" class="text" id="order" name="order" value="${rightsCategory.order }"/>
				</td>
			</tr>
			<tr>
				<th>
					权益备注:
				</th>
				<td>
					<input type="text" name="remark" class="text" value="${rightsCategory.remark}" maxlength="200" />
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