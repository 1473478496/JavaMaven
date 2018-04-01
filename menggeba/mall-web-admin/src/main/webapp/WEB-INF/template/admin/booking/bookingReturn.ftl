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
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	[@flash_message /]
		// 表单验证
	var $returnForm = $("#returnForm");
	var payPrice=parseFloat($("#payPrice").val());
	$returnForm.validate({
	    rules: {
	        returnPrice: {
				required: true,
				min: 0,
				max:payPrice,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			returnCause:{
			
			
			}
			
	    }
	});

});
</script>

</head>
<body>
    <form id="returnForm" action="confirm_return.jhtml" method="post">
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 团购退款
	</div>
	<input type="hidden" name="id" value="${booking.orders.id}"/>
	<input type="hidden" id="payPrice" value="${booking.orders.payPrice}"/>
 	<div class="content">
		<div class="returnsInfo">
			<p><span>团购信息:</span></p>
		 	<table>
				<tr>
					<th>
						订单号:
					</th>
					<td>
						<a href="../order/view.jhtml?id=${booking.orders.id}">${booking.orders.sn}</a>
					</td>
					<th>
						订单金额:
					</th>
					<td>
					￥${booking.orders.payPrice}+${orderItem.payPricePoint}萌值
					</td>
				</tr>
				<tr>
				    <th>
						活动编号
					</th>
					<td>
					    ${booking.paymentOrder}
					</td>
					<th>
						退款类型
					</th>
					<td>
					       团购退款
					</td>
				</tr>
				<tr>
				    <th>
						退款金额:
					</th>
					<td>
						<input type="text" id="returnPrice" name="returnPrice" class="text" value="" maxlength="200"placeholder="非负且小于订单金额" />
					</td>
					<th>
						退款原因:
					</th>
					<td>
						<input type="text" id="returnCause" name="returnCause" class="text" value="" maxlength="200" />
					</td>
				</tr>
				
			</table>
		</div>
		<div class="returnsItem">
			<p><span>商品信息:</span></p>
			<table>
				<tr>
					<th>
						商品编号
					</th>
					<th>
						商品名称
					</th>
					<th>
						商品价格
					</th>
					<th>
						数量
					</th>
					<th>
						当前价格
					</th>
					<th>
						已发货数量
					</th>
					<th>
						小计
					</th>
				</tr>
				<tr>
    				[#if booking.product??]
						<td>
							${booking.sn}
						</td>
						<td>
							<a href="${toView}${booking.product.path}" target="_blank">${booking.product.name}</a>
						</td>
						<td>
							${currency(booking.product.price, true)} +${booking.product.pricePoint}萌值
							
						</td>
						<td>
							${orderItem.quantity}
						</td>
						<td>
							￥${price!}
						</td>
						<td>
							[#if booking.orders.orderStatus=="paid"]
								0								
							[#elseif booking.orders.orderStatus=="shipped" ||booking.orders.orderStatus=="completed"]
								${orderItem.quantity}
							[/#if]
						</td>
						<td>
					    	  ${currency(orderItem.payPrice, true)}+${orderItem.payPricePoint}萌值
							
						</td>
					[/#if]
				</tr>
			</table>
		</div>
		    <div class="audit">
				<input type="submit" class="button" value="${message("admin.common.submit")}" />
				<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='${base}/admin/booking/search.jhtml'" />
			</div>
		</div>
	</div>
	</from>
</body>
</html>

