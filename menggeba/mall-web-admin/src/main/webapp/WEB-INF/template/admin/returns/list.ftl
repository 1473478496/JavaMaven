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
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
	

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.returns.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="status" name="status" value="${status}" />
		<input type="hidden" id="returnType" name="returnType" value="${returnType}" />
		<input type="hidden" id="refundStatus" name="refundStatus" value="${refundStatus}" />
		<div class="bar">
			<div class="buttonWrap">
				<!-- <a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a> -->
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.order.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="status" val="waitingApprove"[#if status == "waitingApprove"] class="checked"[/#if]>待审核</a>
							</li>
							<li>
								<a href="javascript:;" name="status" val="approved"[#if status == "approved"] class="checked"[/#if]>审核通过</a>
							</li>
							<li>
								<a href="javascript:;" name="status" val="unapprove"[#if status == "unapprove"] class="checked"[/#if]>审核拒绝</a>
							</li>
							<li>
								<a href="javascript:;" name="status" val="delivered"[#if status == "delivered"] class="checked"[/#if]>客户已发货</a>
							</li>
							<li>
								<a href="javascript:;" name="status" val="received"[#if status == "received"] class="checked"[/#if]>已确认收货</a>
							</li>
							<li>
								<a href="javascript:;" name="status" val="dealwith"[#if status == "dealwith"] class="checked"[/#if]>已完成</a>
							</li>
							
							<li class="separator">
								<a href="javascript:;" name="returnType" val="directlyRefund"[#if returnType == "directlyRefund"] class="checked"[/#if]>直接退款</a>
							</li>
							<li>
								<a href="javascript:;" name="returnType" val="returnToRefund"[#if returnType == "returnToRefund"] class="checked"[/#if]>退货退款</a>
							</li>
							
							<li class="separator">
								<a href="javascript:;" name="refundStatus" val="refundSuccess"[#if refundStatus == "refundSuccess"] class="checked"[/#if]>退款成功</a>
							</li>
							<li>
								<a href="javascript:;" name="refundStatus" val="refundFail"[#if refundStatus == "refundFail"] class="checked"[/#if]>退款失败</a>
							</li>
							<li>
								<a href="javascript:;" name="refundStatus" val="refunding"[#if refundStatus == "refunding"] class="checked"[/#if]>等待退款</a>
							</li>
						</ul>
					</div>
				</div>
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
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">退货编号</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "trackingNo"] class="current"[/#if] val="trackingNo">${message("Returns.trackingNo")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "order_sn"] class="current"[/#if] val="order_sn">订单号</a>
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
					<a href="javascript:;" class="sort" name="sn">订单号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">退货编号</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">用户名</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">支付方式</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">退款金额</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">退款退货类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">退款退货状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">退款状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">物流公司</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sn">运单号</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>				
			</tr>
			[#list page.content as returns]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${returns.id}" />
					</td>
					<td>
						<a href="../order/view.jhtml?id=${returns.order.id}">${returns.order.sn}</a>
					</td>
					<td>
						${returns.sn}
					</td>
					<td>
						${returns.order.member.username}
					</td>
					<td>
						${returns.order.paymentMethod.name}
					</td>
					<td>
					   [#list returns.returnsItems as returnsItem] 
					      [#if returnsItem_index==0]
					         ${currency(returnsItem.price, true, false)}
					         [#if returns.returnType!="bookingRefund"]+${returns.orderItem.payPricePoint}萌值[/#if]
					    [/#if]
					    [/#list]
					</td>
					<td>
						[#if returns.returnType=="directlyRefund"]
							直接退款
						[#elseif  returns.returnType=="bookingRefund"]
						        团购退款
						[#else]
							退货退款
						[/#if]
					</td>
					<td>
					[#-- [#if returns.refunds??&&returns.refunds?size > 0]
	                 [#list returns.refunds as returnsItem]
		                [#if returnsItem.status=="refundSuccess"]
									已完成
						[#elseif returns.status=="waitingApprove"]
							待审核
						[#elseif returns.status=="delivered"&&returns.returnType=="returnToRefund"]
							客户已发货
						[#elseif returns.status=="received"&&returns.returnType=="returnToRefund"]
							已确认收货
						[#elseif returns.status=="approved"]
							审核通过
						[#elseif returns.status=="unapprove"]
							审核拒绝
						[#elseif returns.status=="dealwith"]
							已完成
						[/#if]			 					 
		             [/#list] 
		             [#else] --]
						[#if returns.status=="waitingApprove"]
							待审核
						[#elseif returns.status=="delivered"&&returns.returnType=="returnToRefund"]
							客户已发货
						[#elseif returns.status=="received"&&returns.returnType=="returnToRefund"]
							已确认收货
						[#elseif returns.status=="approved"]
							审核通过
						[#elseif returns.status=="unapprove"]
							审核拒绝
						[#elseif returns.status=="dealwith"]
							已完成
						[/#if]
				[#--	[/#if]	--]
					</td>
					<td>
					[#if returns.status=="dealwith"]
						[#list returns.refunds as returnsItem]
						 [#if returnsItem_index==0]
		                 [#if returnsItem.status=="refundSuccess"]
								线下退款退款成功
						 [#elseif returnsItem.status=="refundFail"]
								线下退款退款失败
						 [#else]
						 		线下退款等待退款				 
						 [/#if]		
						 [/#if]			 					 
		             [/#list] 
					[#elseif returns.refunds??&&returns.refunds?size > 0]
	                 [#list returns.refunds as returnsItem]
	                 [#if returnsItem_index==0]
		                 [#if returnsItem.status=="refundSuccess"]
									退款成功
						 [#elseif returnsItem.status=="refundFail"]
									退款失败
						 [#else]
						 		等待退款				 
						 [/#if]	
				 	 	[/#if]				 					 
		             [/#list] 
		             
		         	 [#else]
		         			-
	                 [/#if]								   
					</td>
					<td>
						<span title="${returns.createDate?string("yyyy-MM-dd HH:mm:ss")}">${returns.createDate}</span>
					</td>
					<td>
						${returns.deliveryCorp}
					</td>
					<td>
						${returns.trackingNo}
					</td>
					<td>
					
						[#if returns.status =="waitingApprove"]
							<a href="audit.jhtml?id=${returns.id}">【审核】</a>
						[#elseif returns.status=="delivered"]
							<a href="received.jhtml?id=${returns.id}">【确认收货】</a>
						[#elseif returns.status =="unapprove" || returns.status =="dealwith"]
							<a href="view.jhtml?id=${returns.id}">【${message("admin.common.view")}】</a>
						[#elseif returns.status =="approved"&& returns.returnType=="returnToRefund"]
							<a href="view.jhtml?id=${returns.id}"><span>等待发货...</span></a>
						[#else]
							[#list returns.refunds as returnsItem]
								[#if returnsItem.status =="refundFail"&& returns.status !="dealwith"]
									<a href="dealwith.jhtml?id=${returns.id}" id="fail"><span>【处理登记】</span></a>
									[#else]
									<a href="view.jhtml?id=${returns.id}">【${message("admin.common.view")}】</a>
								[/#if]
							[/#list]
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