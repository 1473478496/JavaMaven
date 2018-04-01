<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>运费维护 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {
	[@flash_message /]
	// 订单筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 运费维护 <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "consigneeArea_fullName"] class="current"[/#if] val="consigneeArea_fullName">目的地</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="">配送方式</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="">始发地</a>
				</th>
				
				<th>
					<a href="javascript:;" class="sort" name="">类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name=""> 目的地</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name=""> 首重(kg)</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name=""> 首重费用(元)</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name=""> 半公斤续重费用(元)</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name=""> 1公斤续重费用(元)</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as shippingCalculate]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${shippingCalculate.id}" />
					</td>
					<td>
						${shippingCalculate.shippingMethod.name}
					</td>
					<td>
						[#if shippingCalculate.shippingArea?has_content]${shippingCalculate.shippingArea.fullName}[#else]广东省深圳市[/#if]
					</td>
					<td>
					   ${message("shippingCalculate.areaType." + shippingCalculate.areaType)}
					</td>
					<td>
						${shippingCalculate.consigneeArea.fullName}
					</td>
					<td>
						${shippingCalculate.firstWeight}
					</td>
					<td>
						${shippingCalculate.firstPrice}
					</td>
					<td>
						${shippingCalculate.nextHalfWeight}
					</td>
					<td>
						${shippingCalculate.nextWeight}
					</td>
					<td>
						<a href="edit.jhtml?id=${shippingCalculate.id}">[${message("admin.common.edit")}]</a>
						
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>