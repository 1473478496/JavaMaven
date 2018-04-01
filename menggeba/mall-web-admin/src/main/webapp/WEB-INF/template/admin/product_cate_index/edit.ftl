<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.productCategory.edit")} - Powered By www.mgb.cn</title>
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
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
	var productIds = new Array();
	
	[@flash_message /]
	
	[#list productCateIndex.productRecommonds as productRecommond]
		productIds.push(${productRecommond.product.id});
	[/#list]
	
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
					<input type="text" name="orders" \/>
				<\/td>
				<td>
					<a href="${toView}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>
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
			name: {
				required: true,
				remote: {
					url: "check_name.jhtml?id=${productCateIndex.id}",
					cache: false
				}
			},
			order: "digits",
			orders: {
				required: true,
				digits: true
			}
		},
		messages: {
			name: {
				remote: "名称已存在"
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.productCateIndex.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${productCateIndex.id}" />
		<table id="productTable" class="input">
			<tr>
				<th>
					楼层商品:
				</th>
				<td colspan="3">
					<input type="text" id="productSelect" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
				</td>
			</tr>
			<tr id="productTitle" class="title [#if productCateIndex.productRecommonds??]hidden[/#if]">
				<th>
					&nbsp;
				</th>
				<td width="500">
					${message("DailySpecial.name")}
				</td>
				<td>
					排序
				</td>
				<td>
					${message("admin.common.handle")}
				</td>
			</tr>
			[#list productCateIndex.productRecommonds?sort_by(["orders"]) as productRecommond]
			<tr class="productTr">
				<th>
					<input type="hidden" name="productIds" value="${ productRecommond.product.id}" />
					&nbsp;
				</th>
				<td>
					<span>${ productRecommond.product.fullName}</span>
				</td>
				<td>
					<input type="text" name="orders" value="${productRecommond.orders }"/>
				</td>
				<td>
					<a href="${toView}${productRecommond.product.path}" target="_blank">[${message("admin.common.view")}]</a>
					<a href="javascript:;" class="deleteProduct">[${message("admin.common.delete")}]</a>
				</td>
			</tr>
			[/#list]
		</table>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ProductCateIndex.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" value="${productCateIndex.name}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCateIndex.indexType")}:
				</th>
				<td>
					<input type="text" id="name" name="indexType" value="${productCateIndex.indexType}" class="text" maxlength="200" />
				</td>
			</tr>
			[#--<tr>
				<th>
					${message("ProductCateIndex.proCate")}:
				</th>
				<td>
					<select name="productCateId">
						<option value="">${message("admin.productCategory.root")}</option>
						[#list productCategorys as category]
						<option value="${category.id}"[#if category.id == productCateIndex.productCategory.id] selected="selected"[/#if]>
								[#if category.grade != 0]
									[#list 1..category.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${category.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>--]
			<tr>
				<th>
					${message("ProductCateIndex.description")}:
				</th>
				<td>
					<input type="text" name="description" value="${productCateIndex.description}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCateIndex.ad")}:
				</th>
				<td>
					<select name="adId">
						[#list ads as ad]
								<option value="${ad.id}" [#if ad.id == productCateIndex.ad.id] selected = "selected" [/#if]>
									${ad.title}
								</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" value="${productCateIndex.order}" maxlength="9" />
				</td>
			</tr>
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