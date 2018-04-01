<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>团购管理列表 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]
	var $selectAll = $("#selectAll");
	var $ids = $("#listTable input[name='ids']");
	var $sendInfoButton = $("#sendInfoButton");
	// 发送通知
	$sendInfoButton.click(function() {
		if ($sendInfoButton.hasClass("disabled")) {
			return false;
		}
		var $checkedIds = $ids.filter(":enabled:checked");
		$.dialog({
			type: "warn",
			content: "你确定要发送短信通知？",
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onOk: function() {
				$.ajax({
					url: "sendInfoCustom.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
					}
				});
			}
		});
	});
	
	// 全选
	$selectAll.click(function() {
		var $this = $(this);
		var $enabledIds = $ids.filter(":enabled");
		if ($this.prop("checked")) {
			if ($enabledIds.filter(":checked").size() > 0) {
				$sendInfoButton.removeClass("disabled");
			} else {
				$sendInfoButton.addClass("disabled");
			}
		} else {
			$sendInfoButton.addClass("disabled");
		}
	});
	
	// 选择
	$ids.click(function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			$sendInfoButton.removeClass("disabled");
		} else {
			if ($ids.filter(":enabled:checked").size() > 0) {
				$sendInfoButton.removeClass("disabled");
			} else {
				$sendInfoButton.addClass("disabled");
			}
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 团购管理 <span>(${message("admin.page.total", page.total)})</span>
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
				<a href="javascript:;" id="sendInfoButton" class="iconButton disabled">
					<span class=""></span>短信通知
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
		活动开始时间: <input type="text" id="beginDate" name="beginDate" class="text Wdate" value="${(beginDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});" />
		— <input type="text" id="endDate" name="endDate" class="text Wdate" value="${(endDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'});" />
		<select id="status" name="status">
			<option value="">请选择..</option>
			<option value="true" [#if status?? && status]selected[/#if]>启用</option>
			<option value="false" [#if status?? && !status]selected[/#if]>关闭</option>
		</select>
		<input type="submit" class="button" value="${message("admin.common.submit")}" />
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="id">编号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">团购活动</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="beginDate">开始时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="endDate">预约结束时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="purEndDate">活动结束时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="minimumPrice">商品最低价格</a>
				</th>
				<th>
					<span>预约订金</span>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="status">活动状态</a>
				</th>
				<th>
					<span>最终价格</span>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as booking]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${booking.id}" />
					</td>
					<td>
						${booking.id}
					</td>
					<td>
						${booking.name}
					</td>
					<td>
						${booking.beginDate}
					</td>
					<td>
						${booking.endDate}
					</td>
					<td>
						${booking.purEndDate}
					</td>
					<td>
						${booking.minimumPrice}
					</td>
					<td>
						[#list booking.promotionProducts as promotionProducts]
						[#if promotionProducts_index == 0]
							${promotionProducts.groupBuyingDeposit}
						[/#if]
						[/#list]
					</td>
					<td>
						[#if booking.status?? && booking.status]启用[/#if]
						[#if booking.status?? && !booking.status]关闭[/#if]
					</td>
					<td>
						[#list booking.promotionProducts as promotionProducts]
						[#if promotionProducts_index == 0]
							${promotionProducts.promotionPricePoint}
						[/#if]
						[/#list]
					</td>
					<td>
						<a href="edit.jhtml?id=${booking.id}">[${message("admin.common.edit")}]</a>
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