<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.secKill.list")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.secKill.list")}
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
				<div class="menuWrap">
					<input type="hidden" id="timePoint" name="timePoint" value="${page.timePoint}" />
					<a href="javascript:;" id="secKillDateSelect" class="button">
						${message("SecKill.date")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="secKillDateOption">
							<li>
								<a href="javascript:;"[#if page.timePoint == "00:00:00"] class="current"[/#if] val="00:00:00">00:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "01:00:00"] class="current"[/#if] val="01:00:00">01:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "02:00:00"] class="current"[/#if] val="02:00:00">02:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "03:00:00"] class="current"[/#if] val="03:00:00">03:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "04:00:00"] class="current"[/#if] val="04:00:00">04:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "05:00:00"] class="current"[/#if] val="05:00:00">05:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "06:00:00"] class="current"[/#if] val="06:00:00">06:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "07:00:00"] class="current"[/#if] val="07:00:00">07:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "08:00:00"] class="current"[/#if] val="08:00:00">08:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "09:00:00"] class="current"[/#if] val="09:00:00">09:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "10:00:00"] class="current"[/#if] val="10:00:00">10:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "11:00:00"] class="current"[/#if] val="11:00:00">11:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "12:00:00"] class="current"[/#if] val="12:00:00">12:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "13:00:00"] class="current"[/#if] val="13:00:00">13:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "14:00:00"] class="current"[/#if] val="14:00:00">14:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "15:00:00"] class="current"[/#if] val="15:00:00">15:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "16:00:00"] class="current"[/#if] val="16:00:00">16:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "17:00:00"] class="current"[/#if] val="17:00:00">17:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "18:00:00"] class="current"[/#if] val="18:00:00">18:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "19:00:00"] class="current"[/#if] val="19:00:00">19:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "20:00:00"] class="current"[/#if] val="20:00:00">20:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "21:00:00"] class="current"[/#if] val="21:00:00">21:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "22:00:00"] class="current"[/#if] val="22:00:00">22:00:00</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.timePoint == "23:00:00"] class="current"[/#if] val="23:00:00">23:00:00</a>
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
							<a href="javascript:;"[#if page.searchProperty == "product_name"] class="current"[/#if] val="product_name">${message("Product.name")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "product_sn"] class="current"[/#if] val="product_sn">${message("Product.sn")}</a>
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
					<a href="javascript:;" class="sort">秒杀日期</a>
				</th>
				<th>
					<a href="javascript:;" class="sort">${message("SecKill.date")}</a>
				</th>
				<th width="500px">
					<a href="javascript:;" class="sort" name="product">${message("SecKill.shopName")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="promotionPrice">${message("SecKill.price")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="promotionQuantity">${message("SecKill.amount")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="promotionQuantity">购买数量</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="promotionPricePoint">秒杀萌值</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as secKill]
				<tr>
				
				<td>
						<input type="checkbox" name="ids" value="${secKill.id}" />
					</td>
					<td>
						${secKill.promotion.beginDate}
					</td>
					<td>
						${secKill.promotion.timePoint}
					</td>
					<td>
						${secKill.product.fullName}
					</td>
					<td>
						￥${secKill.promotionPrice}元
					<td>
						${secKill.promotionQuantity}
					</td>
					<td>
						${secKill.saleQuantity}
					</td>
					<td>
						${secKill.promotionPricePoint}萌值
					</td>
					<td>
						<a href="edit.jhtml?id=${secKill.id}">[${message("admin.common.edit")}]</a>
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