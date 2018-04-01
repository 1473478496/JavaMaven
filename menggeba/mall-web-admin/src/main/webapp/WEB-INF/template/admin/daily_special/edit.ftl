<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.dailySpecial.edit")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $isExchange = $("#isExchange");
	var $point = $("#point");
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
	var $isStick = $("#isStick");
	var $stickImage = $("#stickImage");
	var $image = $("#stickImage input");
	var $browserButton = $("#browserButton");
	var productIds = new Array();
	
	[@flash_message /]
	
	$browserButton.browser();
	
	// 置顶
	$isStick.click(function() {
		if ($(this).prop("checked")) {
			$stickImage.show();
			$image.prop("disabled", false);
		} else {
			$stickImage.hide();
			$image.prop("disabled", true);
		}
	});
	
	// 商品选择
	$productSelect.autocomplete("product_select.jhtml", {
		dataType: "json",
		max: 20,
		width: 600,
		scrollHeight: 300,
		parse: function(data) {
			return $.map(data, function(item) {
				return {
					data: item,
					value: item.fullName
				}
			});
		},
		formatItem: function(item) {
			if ($.inArray(item.id, productIds) < 0) {
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
		[@compress single_line = true]
			var trHtml = 
			'<tr class="productTr">
				<th>
					<input type="hidden" name="productIds" value="' + item.id + '" \/>
					&nbsp;
				<\/th>
				<td>
					<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>
				<\/td>
				<td>
					<span title="' + item.fullName + '">￥' + item.price + '+'+ item.mg +'萌值<\/span>
				<\/td>
				<td>
					<a href="${base}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>
					<a href="javascript:;" class="deleteProduct">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productTitle.show();
		$productTable.append(trHtml);
		productIds.push(item.id);
	});
	
	// 删除商品
	$deleteProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				var id = parseInt($this.closest("tr").find("input:hidden").val());
				productIds = $.grep(productIds, function(n, i) {
					return n != id;
				});
				$this.closest("tr").remove();
				if ($productTable.find("tr.productTr").size() <= 0) {
					$productTitle.hide();
				}
			}
		});
	});
	
	// 是否允许积分兑换
	$isExchange.click(function() {
		if ($(this).prop("checked")) {
			$point.prop("disabled", false);
		} else {
			$point.val("").prop("disabled", true);
		}
	});
	
	$.validator.addMethod("compare", 
		function(value, element, param) {
			var parameterValue = $(param).val();
			if ($.trim(parameterValue) == "" || $.trim(value) == "") {
				return true;
			}
			try {
				return parseFloat(parameterValue) <= parseFloat(value);
			} catch(e) {
				return false;
			}
		},
		"${message("admin.coupon.compare")}"
	);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			stickImage: {
				required: true
			},
			promotionQuantity: {
				required: true,
				min: 0,
				integer: 12,
			},
			promotionPrice: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			promotionPricePoint: {
				required: true,
				digits: true
			}
		}
	});
	
	$("#hrefpicture").click(function(){
		open($image.val());
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.dailySpecial.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${special.id}" />
		<div class="tabContent">
			<table id="productTable" class="input">
				<tr>
					<th>
						${message("DailySpecial.specialName")}:
					</th>
					<td colspan="3">
						<input type="text" disabled="disabled" id="productSelect" name="productSelect" class="text" maxlength="200" value="${special.product.fullName }" title="${message("admin.promotion.productSelectTitle")}" />
					</td>
				</tr>
				<tr id="productTitle" class="title hidden">
					<th>
						&nbsp;
					</th>
					<td width="500">
						${message("DailySpecial.name")}
					</td>
					<td width="200">
						${message("DailySpecial.salesPrice")}
					</td>
					<td>
						${message("admin.common.handle")}
					</td>
				</tr>
			</table>
			<table class="input">
				<tr>
					<th><span class="requiredField">*</span>${message("DailySpecial.promotion")}:</th>
					<td><select id="promotionId" name="promotionId">
						<option value="">${message("admin.common.choose")}</option>
						[#list promotions as promotion]
							<option value="${promotion.id}" id="optionId${promotion.id}" [#if promotion.id == special.promotion.id]selected="selected"[/#if]>
								${promotion.name}--(${promotion.beginDate }至${promotion.endDate })
							</option>
						[/#list]
					</select></td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("DailySpecial.price")}:
					</th>
					<td colspan="2">
						<input type="text" id="promotionPrice" name="promotionPrice" class="text" value="${special.promotionPrice }" maxlength="9" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("DailySpecial.giftMOE")}:
					</th>
					<td colspan="2">
						<input type="text" name="promotionPricePoint" class="text" value="${special.promotionPricePoint }" maxlength="12" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("DailySpecial.amount")}:
					</th>
					<td colspan="2">
						<input type="text" id="promotionQuantity" name="promotionQuantity" class="text" value="${special.promotionQuantity }" maxlength="12" />
					</td>
				</tr>
				<tr>
					<th>
						${message("DailySpecial.isStick") }:
					</th>
					<td colspan="2">
						<label>
							<input type="checkbox" id="isStick" name="isStick" value="true" [#if special.isStick == true] checked="checked"[/#if]/>${message("admin.dailySpecial.stick") }
						</label>
					</td>
				</tr>
				<tr id="stickImage" [#if special.isStick == false] class="hidden"[/#if]>
					<th>
						<span class="requiredField">*</span>${message("DailySpecial.stickImage") }:
					</th>
					<td colspan="2">
						<span class="fieldSet">
							<input type="text" name="stickImage" class="text" value="${special.stickImage }" [#if special.isStick == false] disabled="disabled"[/#if] maxlength="200" title="${message("admin.dailySpecial.imageTitle")}" />
							<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" [#if special.isStick == false] disabled="disabled"[/#if] />
							<a href="javascript:;" id="hrefpicture">${message("admin.common.view")}</a>
						</span>
					</td>
				</tr>
			</table>
		</div>
		<table class="input">
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