<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.order.view")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $completeForm = $("#completeForm");
	var $cancelForm = $("#cancelForm");
	var $shippingButton = $("#shippingButton");
	var $completeButton = $("#completeButton");
	var $cancelButton = $("#cancelButton");
	var isLocked = false;
	
	[@flash_message /]
	
	// 检查锁定
	function checkLock() {
		if (!isLocked) {
			$.ajax({
				url: "check_lock.jhtml",
				type: "POST",
				data: {id: ${order.id}},
				dataType: "json",
				cache: false,
				success: function(message) {
					if (message.type != "success") {
						$.message(message);
						$shippingButton.prop("disabled", true);
						$completeButton.prop("disabled", true);
						$cancelButton.prop("disabled", true);
						isLocked = true;
					}
				}
			});
		}
	}
	
	// 完成
	$completeButton.click(function() {
		var $this = $(this);
		 [#if !flag]
				$.dialog({
                    closeOnEscape: false,
					type: "warn",
		    		cache: false,
		    		width: 400,
		    		margin:200,
				    modal: true,
				    ok: "${message("admin.dialog.ok")}",
				    cancel: "${message("admin.dialog.cancel")}",
					content: "${message("该订单存在退货商品正在审核，不能进行完成操作！")}",
                 // $(".dialogButton button").eq(1).hide(); 
                     open: function(event, ui) { $(".dialogButton button").hide(); }
				});
		   [#else]
				$.dialog({
					type: "warn",
					content: "${message("admin.order.completeDialog")}",
					onOk: function() {
						$completeForm.submit();
					}
				});
		[/#if]
	});
	
	// 取消
	$cancelButton.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.order.cancelDialog")}",
			onOk: function() {
				$cancelForm.submit();
			}
		});
	});

	[#if order.orderStatus == "paymented"]
		// 发货
		$shippingButton.click(function() {
		   [#if !flag]
				$.dialog({
                    closeOnEscape: false,
					type: "warn",
		    		cache: false,
		    		width: 400,
		    		margin:200,
				    modal: true,
				    ok: "${message("admin.dialog.ok")}",
				    cancel: "${message("admin.dialog.cancel")}",
					content: "${message("该订单存在退款商品正在审核，不能进行发货操作！")}",
                                         
                 // $(".dialogButton button").eq(1).hide(); 
                     open: function(event, ui) { $(".dialogButton button").hide(); }
				});
		   [#else]
			  $.dialog({
				title: "${message("admin.order.shipping")}",
				[@compress single_line = true]
					content: '
					<form id="shippingForm" action="shipping.jhtml" method="post">
						<input type="hidden" name="token" value="${token}" \/>
						<input type="hidden" name="orderId" value="${order.id}" \/>
						<div style="height: 380px; overflow-x: hidden; overflow-y: auto;">
							<table class="input" style="margin-bottom: 30px;">
								<tr>
									<th>
										${message("Order.sn")}:
									<\/th>
									<td width="300">
										${order.sn}
									<\/td>
									<th>
										${message("admin.common.createDate")}:
									<\/th>
									<td>
										${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
									<\/td>
								<\/tr>
								<tr>
									<th>
										${message("Shipping.shippingMethod")}:
									<\/th>
									<td>
										<select name="shippingMethodId">
											<option value="">${message("admin.common.choose")}<\/option>
											[#list shippingMethods as shippingMethod]
												<option value="${shippingMethod.id}"[#if shippingMethod == order.shippingMethod] selected="selected"[/#if]>${shippingMethod.name}<\/option>
											[/#list]
										<\/select>
									<\/td>
									<th>
										${message("Shipping.deliveryCorp")}:
									<\/th>
									<td>
										<select name="deliveryCorpId">
											[#list deliveryCorps as deliveryCorp]
												<option value="${deliveryCorp.id}"[#if order.shippingMethod?? && deliveryCorp == order.shippingMethod.defaultDeliveryCorp] selected="selected"[/#if]>${deliveryCorp.name}<\/option>
											[/#list]
										<\/select>
									<\/td>
								<\/tr>
								<tr>
									<th>
										${message("Shipping.trackingNo")}:
									<\/th>
									<td>
										<input type="text" name="trackingNo" class="text" maxlength="200" \/>
									<\/td>
									<th>
										${message("Shipping.freight")}:
									<\/th>
									<td>
										<input type="text" name="freight" class="text" maxlength="16" \/>
									<\/td>
								<\/tr>
								<tr>
									<th>
										${message("Shipping.consignee")}:
									<\/th>
									<td>
										<input type="text" name="consignee" class="text" value="${order.consignee}" maxlength="200" \/>
									<\/td>
									<th>
										${message("Shipping.zipCode")}:
									<\/th>
									<td>
										<input type="text" name="zipCode" class="text" value="${order.zipCode}" maxlength="6" \/>
									<\/td>
								<\/tr>
								<tr>
									<th>
										${message("Shipping.area")}:
									<\/th>
									<td>
										<span class="fieldSet">
											<input type="hidden" id="areaId" name="areaId" value="${(order.area.id)!}" treePath="${(order.area.treePath)!}" \/>
										<\/span>
									<\/td>
									<th>
										${message("Shipping.address")}:
									<\/th>
									<td>
										<input type="text" name="address" class="text" value="${order.address}" maxlength="200" \/>
									<\/td>
								<\/tr>
								<tr>
									<th>
										手机/电话：[#--${message("Shipping.phone")}:--]
									<\/th>
									<td>
										<input type="text" name="phone" class="text" value="${order.phone}" maxlength="13" \/>
									<\/td>
									<th>
										${message("Shipping.memo")}:
									<\/th>
									<td>
										<input type="text" name="memo" class="text" maxlength="200" \/>
									<\/td>
								<\/tr>
							<\/table>
							<table class="input">
								<tr class="title">
									<th>
										${message("ShippingItem.sn")}
									<\/th>
									<th>
										${message("ShippingItem.name")}
									<\/th>
									<th>
										${message("admin.order.productStock")}
									<\/th>
									<th>
										${message("admin.order.productQuantity")}
									<\/th>
									<th>
										${message("OrderItem.shippedQuantity")}
									<\/th>
									<th>
										${message("admin.order.shippingQuantity")}
									<\/th>
								<\/tr>
								[#list orderItems as orderItem]
								    
									<tr>
										<td>
											<input type="hidden" name="shippingItems[${orderItem_index}].sn" value="${orderItem.sn}" \/>
											${orderItem.sn}
										<\/td>
										<td width="300">
											<span title="${orderItem.fullName}">${abbreviate(orderItem.fullName, 50, "...")}<\/span>
											[#if orderItem.isGift]
												<span class="red">[${message("admin.order.gift")}]<\/span>
											[/#if]
										<\/td>
										<td>
											${(orderItem.product.stock)!"-"}
										<\/td>
										<td>
											${orderItem.quantity}
										<\/td>
										<td>
											${orderItem.shippedQuantity}
										<\/td>
										<td>
											[#if orderItem.product?? && orderItem.product.stock??]
												[#if orderItem.product.stock <= 0 || orderItem.quantity - orderItem.shippedQuantity <= 0]
													<input type="text" name="shippingItems[${orderItem_index}].quantity" class="text" value="0" style="border:0px; width:20px;" disabled="disabled" \/>
												[#elseif orderItem.product.stock < orderItem.quantity - orderItem.shippedQuantity]
													<input type="text"  class="text shippingItemsQuantity" value="${orderItem.product.stock}" style="border:0px; width:20px;" disabled="disabled"\/>
												    <input type="hidden" name="shippingItems[${orderItem_index}].quantity" value="${orderItem.product.stock}"\/>
												[#else]
													<input type="text"   class="text shippingItemsQuantity" value="${orderItem.quantity - orderItem.shippedQuantity}" style="border:0px; width:20px;" disabled="disabled" \/>
												    <input type="hidden" name="shippingItems[${orderItem_index}].quantity" value="${orderItem.quantity - orderItem.shippedQuantity}"\/>
												[/#if]
											[#else]
												<input type="text" class="text shippingItemsQuantity" value="${orderItem.quantity - orderItem.shippedQuantity}" style="border:0px; width:20px;" disabled="disabled"\/>
											    <input type="hidden" name="shippingItems[${orderItem_index}].quantity" value="${orderItem.quantity - orderItem.shippedQuantity}"\/>
											[/#if]
										<\/td>
									<\/tr>
								[/#list]
								<tr>
									<td colspan="6" style="border-bottom: none;">
										&nbsp;
									<\/td>
								<\/tr>
							<\/table>
						<\/div>
					<\/form>',
				[/@compress]
				width: 900,
				modal: true,
				ok: "${message("admin.dialog.ok")}",
				cancel: "${message("admin.dialog.cancel")}",
				onShow: function() {
					$("#areaId").lSelect({
						url: "${base}/admin/common/area.jhtml"
					});
					$.validator.addClassRules({
						shippingItemsQuantity: {
							required: true,
							digits: true
						}
					});
					$("#shippingForm").validate({
						rules: {
							shippingMethodId: "required",
							deliveryCorpId: "required",
							freight: {
								min: 0,
								decimal: {
									integer: 12,
									fraction: ${setting.priceScale}
								}
							},
							consignee: "required",
							areaId: "required",
							address: "required",
							trackingNo: {
								required: true,
								pattern: /^[a-zA-Z0-9]*$/
							},
							zipCode: {
								pattern: /^[1-9]\d{5}$/
							},
							phone: {
								required: true,
								pattern: /(^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$)|(^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$)/
							}
						}
					});
				},
				onOk: function() {
					var total = 0;
					$("#shippingForm input.shippingItemsQuantity").each(function() {
						var quantity = $(this).val();
						if ($.isNumeric(quantity)) {
							total += parseInt(quantity);
						}
					});
					if (total <= 0) {
						$.message("warn", "${message("admin.order.shippingQuantityPositive")}");
					} else {
						$("#shippingForm").submit();
					}
					return false;
				}
			});
		   [/#if]
		});
	[/#if]
	
});
</script>
</head>
<body>
	<form id="confirmForm" action="confirm.jhtml" method="post">
		<input type="hidden" name="id" value="${order.id}" />
	</form>
	<form id="completeForm" action="complete.jhtml" method="post">
		<input type="hidden" name="id" value="${order.id}" />
	</form>
	<form id="cancelForm" action="cancel.jhtml" method="post">
		<input type="hidden" name="id" value="${order.id}" />
	</form>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.view")}
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="${message("admin.order.orderInfo")}" />
		</li>
		<li>
			<input type="button" value="${message("admin.order.productInfo")}" />
		</li>
	
		<li>
			<input type="button" value="${message("admin.order.shippingInfo")}" />
		</li>
		<li>
			<input type="button" value="${message("admin.order.orderLog")}" />
		</li>
	</ul>
	<table class="input tabContent">
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				<input type="button" id="shippingButton" class="button" value="${message("admin.order.shipping")}"[#if order.orderStatus != "paymented" ] disabled="disabled"[/#if] />
				<input type="button" id="completeButton" class="button" value="${message("admin.order.complete")}"[#if order.orderStatus != "shipped"] disabled="disabled"[/#if] />
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<input type="button" id="cancelButton" class="button" value="${message("admin.order.cancel")}"[#if order.expired || order.orderStatus != "unconfirmed"] disabled="disabled"[/#if] />
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.sn")}: 
			</th>
			<td width="360">
				${order.sn}
			</td>
			<th>
				${message("admin.common.createDate")}:
			</th>
			<td>
				${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.orderStatus")}:
			</th>
			<td>
				${message("Order.OrderStatus." + order.orderStatus)}
			<!--	[#if order.expired]-->
			<!--		<span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("admin.order.hasExpired")})</span>-->
			<!--	[#elseif order.expire??]-->
			<!--		<span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("Order.expire")}: ${order.expire})</span>-->
			<!--	[/#if]-->
			</td>
			<th>
				${message("Order.paymentStatus")}:
			</th>
			<td>
				${message("Order.PaymentStatus." + order.paymentStatus)}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.shippingStatus")}:
			</th>
			<td>
				${message("Order.ShippingStatus." + order.shippingStatus)}
			</td>
			<th>
				${message("Member.username")}:
			</th>
			<td>
				${order.member.username}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.amount")}:
			</th>
			<td>
				${currency(order.amount, true)}+${order.pricePoint}萌值
			</td>
			<th>
				${message("Order.amountPaid")}:
			</th>
			<td>
				${currency(order.amountPaid, true)}+${order.pricePoint}萌值
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.weight")}:
			</th>
			<td>
				${order.weight}
			</td>
			<th>
				${message("Order.quantity")}:
			</th>
			<td>
				${order.quantity}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.promotion")}:
			</th>
			<td>
				${(order.promotion)!"-"}
			</td>
			<th>
				${message("admin.order.coupon")}:
			</th>
			<td>
				${(order.couponCode.coupon.name)!"-"}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.promotionDiscount")}:
			</th>
			<td>
				${currency(order.promotionDiscount, true)}
			</td>
			<th>
				${message("Order.couponDiscount")}:
			</th>
			<td>
				${currency(order.couponDiscount, true)}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.offsetAmount")}:
			</th>
			<td>
				${currency(order.offsetAmount, true)}
			</td>
			<th>
				${message("Order.point")}:
			</th>
			<td>
				${order.point}萌值
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.freight")}:
			</th>
			<td>
				${currency(order.freight, true)}
			</td>
			<th>
				${message("Order.fee")}:
			</th>
			<td>
				${currency(order.fee, true)}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.paymentMethod")}:
			</th>
			<td>
				${order.paymentMethodName}
			</td>
			<th>
				${message("Order.shippingMethod")}:
			</th>
			<td>
				${order.shippingMethodName}
			</td>
		</tr>
		[#if order.shippings?has_content]
           [#list order.shippings as shipping]
             [#if shipping_index==0]
	              <tr>
	                <th>物流公司：</th>
	                <td>${shipping.deliveryCorpCode}</td>
	                <th>运单号：</th>
	                <td>${shipping.trackingNo}</td>
	              </tr>
	              [#break]
              [/#if]
          	[/#list]
        [/#if]
		[#if order.isInvoice]
			<tr>
				<th>
					${message("Order.invoiceTitle")}:
				</th>
				<td>
					${order.invoiceTitle}
				</td>
				<th>
					${message("Order.tax")}:
				</th>
				<td>
					${currency(order.tax, true)}
				</td>
			</tr>
		[/#if]
		<tr>
			<th>
				${message("Order.consignee")}:
			</th>
			<td>
				${order.consignee}
			</td>
			<th>
				${message("Order.area")}:
			</th>
			<td>
				${order.areaName}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.address")}:
			</th>
			<td>
				${order.address}
			</td>
			<th>
				${message("Order.zipCode")}:
			</th>
			<td>
				${order.zipCode}
			</td>
		</tr>
		<tr>
			<th>
				${message("Order.phone")}:
			</th>
			<td>
				${order.phone}
			</td>
			<th>
				${message("Order.memo")}:
			</th>
			<td>
				${order.buyRemark}
			</td>
		</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				${message("OrderItem.sn")}
			</th>
			<th>
				${message("OrderItem.name")}
			</th>
			<th>
				${message("OrderItem.price")}
			</th>
			<th>
				${message("OrderItem.quantity")}
			</th>
			<th>
				${message("OrderItem.shippedQuantity")}
			</th>
			<th>
				${message("OrderItem.returnQuantity")}
			</th>
			<th>
				${message("OrderItem.subtotal")}
			</th>
		</tr>
		[#list order.orderItems as orderItem]
		  [#if orderItem.product??]
			[#if orderItem.product.suit]
			    [#list tieInSaleMap.get(orderItem.id) as tieInSale]
			       [#if tieInSale_index == 0]
						<tr>
							<td>
								${orderItem.sn}
							</td>
							<td width="400">
								<span title="${orderItem.fullName}">${abbreviate(orderItem.fullName, 50, "...")}</span>
								[#if orderItem.isGift]
									<span class="red">[${message("admin.order.gift")}]</span>
								[/#if]
							</td>
							<td>
								${currency(orderItem.price, true)}+${orderItem.pricePoint}萌值
							</td>
						
							<td rowspan="${(tieInSaleMap.get(orderItem.id)?size)+2}">
								${orderItem.quantity}
							</td>
							<td rowspan="${(tieInSaleMap.get(orderItem.id)?size)+2}">
								${orderItem.shippedQuantity}
							</td>
							<td rowspan="${(tieInSaleMap.get(orderItem.id)?size)+2}">
								${orderItem.returnQuantity}
							</td>
							<td rowspan="${(tieInSaleMap.get(orderItem.id)?size)+2}">
								${currency(orderItem.subtotal, true)}+${orderItem.payPricePoint}萌值
							</td>
					    </tr>
					    <tr>
							<td>
								<a href="http://nici.mgb.cn/product/content/${tieInSale.product.id}.do" target="_blank">${tieInSale.product.sn}</a>
							</td>
							<td width="400">
								<span title="${tieInSale.product.fullName}"><a href="http://nici.mgb.cn/product/content/${tieInSale.product.id}.do" target="_blank">${abbreviate(tieInSale.product.fullName, 50, "...")}</a></span>
								[#if orderItem.isGift]
									<span class="red">[${message("admin.order.gift")}]</span>
								[/#if]
							</td>
							<td>
								${currency(tieInSale.product.price, true)}+${tieInSale.product.pricePoint}萌值
							</td>
						</tr>
						<tr>
							<td>
								<a href="http://nici.mgb.cn/product/content/${tieInSale.telIn.id}.do" target="_blank">${tieInSale.telIn.sn}</a>
							</td>
							<td width="400">
								<span title="${tieInSale.telIn.fullName}"><a href="http://nici.mgb.cn/product/content/${tieInSale.telIn.id}.do" target="_blank">${abbreviate(tieInSale.telIn.fullName, 50, "...")}</a></span>
								[#if orderItem.isGift]
									<span class="red">[${message("admin.order.gift")}]</span>
								[/#if]
							</td>
							<td>
								${currency(tieInSale.telIn.price, true)}+${tieInSale.telIn.pricePoint}萌值
							</td>
						</tr>
					[#else]
						<tr>
							<td>
								<a href="http://nici.mgb.cn/product/content/${tieInSale.telIn.id}.do" target="_blank">${tieInSale.telIn.sn}</a>
							</td>
							<td width="400">
								<span title="${tieInSale.telIn.fullName}"><a href="http://nici.mgb.cn/product/content/${tieInSale.telIn.id}.do" target="_blank">${abbreviate(tieInSale.telIn.fullName, 50, "...")}</a></span>
								[#if orderItem.isGift]
									<span class="red">[${message("admin.order.gift")}]</span>
								[/#if]
							</td>
							<td>
								${currency(tieInSale.telIn.price, true)}+${tieInSale.telIn.pricePoint}萌值
							</td>
						</tr>
					[/#if]
				[/#list]
			[#else]
				<tr>
					<td>
						<a href="http://nici.mgb.cn/product/content/${orderItem.product.id}.do" target="_blank">${orderItem.sn}</a>
					</td>
					<td width="400">
						<span title="${orderItem.fullName}"><a href="http://nici.mgb.cn/product/content/${orderItem.product.id}.do" target="_blank">${abbreviate(orderItem.fullName, 50, "...")}</a></span>
						[#if orderItem.isGift]
							<span class="red">[${message("admin.order.gift")}]</span>
						[/#if]
					</td>
					<td>
						[#if !orderItem.isGift]
							${currency(orderItem.price, true)}+${orderItem.pricePoint}萌值
						[#else]
							-
						[/#if]
					</td>
					<td>
						${orderItem.quantity}
					</td>
					<td>
						${orderItem.shippedQuantity}
					</td>
					<td>
						${orderItem.returnQuantity}
					</td>
					<td>
						[#if !orderItem.isGift]
							${currency(orderItem.subtotal, true)}+${orderItem.payPricePoint}萌值
						[#else]
							-
						[/#if]
					</td>
				</tr>
			[/#if]
		  [/#if]
		[/#list]
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				${message("Shipping.sn")}
			</th>
			<th>
				${message("Shipping.shippingMethod")}
			</th>
			<th>
				${message("Shipping.deliveryCorp")}
			</th>
			<th>
				${message("Shipping.trackingNo")}
			</th>
			<th>
				${message("Shipping.consignee")}
			</th>
			<th>
				${message("admin.common.createDate")}
			</th>
		</tr>
		[#list order.shippings as shipping]
			<tr>
				<td>
					${shipping.sn}
				</td>
				<td>
					${shipping.shippingMethod}
				</td>
				<td>
					${shipping.deliveryCorp}
				</td>
				<td>
					<input type="text" id="trackingNo" shippingId="${shipping.id }" class="text" maxlength="50" style="width: 100px" value="${shipping.trackingNo }" onblur="javascript:shipping_edit.edit();" />
				</td>
				<td>
					${shipping.consignee}
				</td>
				<td>
					<span title="${shipping.createDate?string("yyyy-MM-dd HH:mm:ss")}">${shipping.createDate}</span>
				</td>
			</tr>
		[/#list]
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				${message("OrderLog.type")}
			</th>
			<th>
				${message("OrderLog.operator")}
			</th>
			<th>
				${message("OrderLog.content")}
			</th>
			<th>
				${message("admin.common.createDate")}
			</th>
		</tr>
		[#list order.orderLogs as orderLog]
			<tr>
				<td>
				fdfd	${message("OrderLog.Type." + orderLog.type)}
				</td>
				<td>
					[#if orderLog.operator??]
						${orderLog.operator}
					[#else]
						<span class="green">${message("admin.order.member")}</span>
					[/#if]
				</td>
				<td>
					[#if orderLog.content??]
						<span title="${orderLog.content}">${abbreviate(orderLog.content, 50, "...")}</span>
					[/#if]
				</td>
				<td>
					<span title="${orderLog.createDate?string("yyyy-MM-dd HH:mm:ss")}">${orderLog.createDate}</span>
				</td>
			</tr>
		[/#list]
	</table>
	<table class="input">
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="button" class="button" value="${message("admin.common.back")}" onclick="javascript:history.back()" />
			</td>
		</tr>
	</table>
	
<script type="text/javascript">
var Shipping_Edit = function(){
	this.edit = function(){
		$.ajax({
			url: "trackingNo.jhtml",
			type: "POST",
			data: {id: $("#trackingNo").attr("shippingId"), trackingNo: $("#trackingNo").val()},
			dataType: "json",
			cache: false,
			success: function(data) {
				$.message(data.message);
			}
		});
	}
};

var shipping_edit = new Shipping_Edit();
</script>
</body>
</html>