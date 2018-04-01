<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.returns.list")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $print = $("select[name='print']");

	[@flash_message /]

	// 确认收货
	/**
	$('#sure').click(function() {
		var id=$(this).attr('aitemId');
		if (confirm("${message("changes.sure.accept")}")) {
			$.ajax({
				url: "${base}/admin/changes/sureAccept.jhtml",
				type: "POST",
				data:{id:id},
				dataType: "json",
				cache: false,
				success: function(data) {
				    $.message(data.message);
				    if("success"==data.message.type){
						$("#handle"+data.changes.id).html('<a href="${base}/admin/changes/audite.jhtml?id='+data.changes.id+'">[${message("change.info.review")}]</a>');
				    }
				}
			});
		}
	});
	*/
});

function sureClick(itemId){
	if (confirm("${message("changes.sure.accept")}")) {
		$.ajax({
			url: "${base}/admin/changes/sureAccept.jhtml",
			type: "POST",
			data:{id:itemId},
			dataType: "json",
			cache: false,
			success: function(data) {
			    //$.message(data.message);
			    if("success"==data.message.type){
			         //$("#changesStatus_"+data.changes.id).html("${message("change.changeStatus.'+ data.changes.status+'")}");
			         $("#changesStatus_"+data.changes.id).html("换货完成");
				     $("#handle_"+data.changes.id).html('<a href="${base}/admin/changes/audite.jhtml?id='+data.changes.id+'">[${message("change.info.review")}]</a>');
			     }
			}
		});
	}
}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 
		${message("admin.changes.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<!--<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>-->
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
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">${message("change.sn")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "trackingNo"] class="current"[/#if] val="trackingNo">${message("Shipping.trackingNo")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "order_sn"] class="current"[/#if] val="orders_sn">订单号</a>
						</li>
					</ul>
				</div>
				
			</div>
		</div>

</head>
<body>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">${message("change.order.sn")}</a>
				</th>
				<th>
					<span>${message("change.sn")}</span>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="username">${message("change.member.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="consignee">${message("Order.paymentStatus")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="paymentMethodName">${message("change.product.state")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="shippingMethodName">${message("change.createTime")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="orderStatus">${message("change.deliveryCorp")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="trackingNo">${message("change.trackingNo")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
		
	[#list page.content as change]
		<tr>
			<td>
				<input type="checkbox" name="ids" value="${change.id}" />
			</td>
			<td>
				<a href="../order/view.jhtml?id=${change.orders.id}">${change.orders.sn}</a>
			</td>
			<td>
				${change.sn}
			</td>
			<td>
				${change.orders.member.username}
			</td>
			<td>
				${message("change.PaymentStatus." + change.orders.paymentStatus)}
			</td>
			<td id="changesStatus_${change.id}">
				${message("change.changeStatus." + change.status)}
				[#if change.expired]<span class="gray">(${message("admin.orders.hasExpired")})</span>[/#if]
			</td>
			<td>
				<span title="${change.createDate?string("yyyy-MM-dd HH:mm:ss")}">${change.createDate}</span>
			</td>
			<td>
				${change.deliveryCorp}
			</td>
			<td>
			    ${change.trackingNo}
			</td>
			<td>
				<span id="handle_${change.id}">
					[#if !change.expired && change.status == "pending"]
						<a href="audite.jhtml?id=${change.id}">[${message("change.state.pending")}]</a>
					[#elseif !change.expired && change.status == "delivered"]
						<a href="javascript:sureClick(${change.id});" name="sure" id="sure_${change.id}" aitemId="${change.id}">[${message("change.sure.receive")}]</a>
					[#else]
						<a href="audite.jhtml?id=${change.id}">[${message("change.info.review")}]</a>
					[/#if]
				</span>
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