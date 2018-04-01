<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>预约查询列表 - Powered By www.mgb.cn</title>
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
	});
	 //退款退货申请服务
	function returnService(id){
		var price=$("#price"+id).val();
		jQuery.ajax({
			url: "${base}/admin/booking/returnService.jhtml",
			type: "POST",
			data: {id: id},
	    	dataType: "json",
	    	success:function(data){
		    	if('warn'==data.message.type){
		    		$.message(data.message);
		    	}
	    		if('success'==data.message.type){
	    		 	 if(confirm(data.message.content)){
			    		window.location.href='${base}/admin/booking/bookingReturn.jhtml?id='+id+"&price="+price;
		    		}
			    }
		     }
		 });
	}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 预约查询列表 <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="search.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled" deleteCustom="true">
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
		[#-- 活动编号：<input type="text" id="promotionId" name="promotionId" value="${promotionId! }" /> --]
		[#-- 手机号：<input type="text" id="mobile" name="mobile" value="${mobile! }" />
		用户名：<input type="text" id="username" name="username" value="${username! }" /> --]
		查询时间：<input type="text" id="beginDate" name="beginDate" class="text Wdate" value="${(beginDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});" />
		— <input type="text" id="endDate" name="endDate" class="text Wdate" value="${(endDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'});" />
		<input type="submit" class="button" value="${message("admin.common.submit")}" />
		<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "paymentOrder"] class="current"[/#if] val="paymentOrder">活动编号</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "promotion_name"] class="current"[/#if] val="promotion_name">团购名称</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "member_mobile"] class="current"[/#if] val="member_mobile">手机号</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "member_username"] class="current"[/#if] val="member_username">用户名</a>
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
					<a href="javascript:;" class="sort" name="paymentOrder">预约编号</a>
				</th>
				
				<th>
					<a href="javascript:;" class="sort" name="promotion">团购名称</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="member">用户名</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="member">手机号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">预约时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="amount">预约付款</a>
				</th>
				<th>
					当前价格
				</th>
				<th>
					<a href="javascript:;" class="sort" name="status">状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="orders">对应订单号</a>
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
						${booking.paymentOrder}
					</td>
					
					<td>
						${booking.promotion.name}
					</td>
					<td>
						${booking.member.username}
					</td>
					<td>
						${booking.member.mobile}
					</td>
					<td>
						${booking.createDate}
					</td>
					<td>
						${booking.amount}
					</td>
					<td>
						<input type="hidden" id="price${booking.id}" name="price" value="${promotionPrices.get(booking.id)}"/>
						${promotionPrices.get(booking.id)}
					</td>
					<td>
						[#if booking.status == 'nonPayment']
							预约未付款
						[#elseif booking.status == 'payment']
							已预约
						[#elseif booking.status == 'Purchased']
							已购买
						[#elseif booking.status == 'directPurchase']
							直接购买
						[#else]
							已结束
						[/#if]
					</td>
					<td>
					[#if booking.orders??]<a href="${base }/admin/order/view.jhtml?id=${booking.orders.id}">${booking.orders.sn }</a>[/#if]
					</td>
					<td>
					   [#if booking.orders?has_content&&booking.orders.paymentStatus=="paid"]
					       <a href="javaScript:returnService(${booking.id});">
		      				   	  退款
		      				  </a>
					   [/#if]
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