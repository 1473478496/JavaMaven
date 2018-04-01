<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.returns.view")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	
</style>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript">
$().ready(function() {

var $areaId = $("#areaId");
	 // 地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
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
						${busniess.busniessName}
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
						联系人:
					</th>
					<td>
						${busniess.contacter}
					</td>
					<th>
						联系电话:
					</th>
					<td>
						${busniess.mobile}
					</td>
				</tr>
				<tr>
					<th>
						联系邮件:
					</th>
					<td>
						${busniess.email}
					</td>
					<th>
						申请时间:
					</th>
					<td>
						${busniess.createDate}
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
</body>
</html>