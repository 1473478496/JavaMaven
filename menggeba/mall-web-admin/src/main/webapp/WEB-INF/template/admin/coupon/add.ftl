<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.coupon.add")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<style type="text/css">
 .productCategory label, .brand label{
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;
}
</style>

<script type="text/javascript">


$().ready(function() {

	var $inputForm = $("#inputForm");
	var $isExchange = $("#isExchange");
	var $point = $("#point");
	
	var $productSelect = $("#productSelect");
	var productIds = new Array();
    var $productSelect = $("#productSelect");
    var $productTitle = $("#productTitle");
    var $productTable = $("#productTable");
    var $deleteProduct = $("a.deleteProduct");
	
   
	
	[@flash_message /]
	
 
	
	
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
			name: "required",
			prefix: "required",
			minimumQuantity: "digits",
			limtNums: {
				required: true,
				digits: true
			}
			maximumQuantity: {
				digits: true,
				compare: "#minimumQuantity"
			},
			minimumPrice: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			maximumPrice: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				},
				compare: "#minimumPrice"
			},
			priceExpression: {
			    required: true,
				remote: {
					url: "check_price_expression.jhtml",
					cache: false
				}
			},
			point: {
				required: true,
				digits: true
			}
		}
	});
	
	
	
	 
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.coupon.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.coupon.base")}" />
			</li>
			<li>
				<input type="button" value="${message("Coupon.introduction")}" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="input">
			   
			
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Coupon.name")}:
					</th>
					<td>
						<input type="text" name="name" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Coupon.prefix")}:
					</th>
					<td>
						<input type="text" name="prefix" class="text" maxlength="200" />
					</td>
				</tr>
				
				
			
			   <tr class="productCategory">
					<th>
						${message("Promotion.productCategories")}
					</th>
					<td>
						<select id="productCategoryIds" name="productCategoryIds">
						<option value="">
						</option>
						[#list productCategoryTree as productCategory]
							<option value="${productCategory.id}">
								[#if productCategory.grade != 0]
									[#list 1..productCategory.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${productCategory.name}
							</option>
						[/#list]
					</select>
					</td>
				</tr>
				<!--<tr class="brand">
					<th>
						${message("Promotion.brands")}
					</th>
					<td>
						[#list brands as brand]
							<label>
								<input type="checkbox" name="brandIds" value="${brand.id}" />${brand.name}
							</label>
						[/#list]
					</td>
				</tr>-->
			 
				<tr>
					<th>
						${message("Coupon.beginDate")}:
					</th>
					<td>
						<input type="text" id="beginDate" name="beginDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});" />
					</td>
				</tr>
				<tr>
					<th>
						${message("Coupon.endDate")}:
					</th>
					<td>
						<input type="text" id="endDate" name="endDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'beginDate\')}'});" />
					</td>
				</tr>
				<!--<tr>
					<th>
						${message("Coupon.minimumQuantity")}:
					</th>
					<td colspan="2">
						<input type="text" id="minimumQuantity" name="minimumQuantity" class="text" maxlength="9" />
					</td>
				</tr>
				<tr>
					<th>
						${message("Coupon.maximumQuantity")}:
					</th>
					<td colspan="2">
						<input type="text" name="maximumQuantity" class="text" maxlength="9" />
					</td>
				</tr>-->
				<tr>
					<th>
						${message("Coupon.minimumPrice")}:
					</th>
					<td colspan="2">
						<input type="text" id="minimumPrice" name="minimumPrice" class="text" maxlength="16" />
					</td>
				</tr> 
				
		      <!--<tr>
					<th>
						${message("Coupon.maximumPrice")}:
					</th>
					<td colspan="2">
						<input type="text" name="maximumPrice" class="text" maxlength="16" />
					</td>
				</tr>
				-->
				<tr>
					<th>
					<span class="requiredField">*</span>${message("Coupon.priceExpression")}:
						
					</th>
					<td colspan="2">
						<input type="text" name="priceExpression" class="text" maxlength="255" title="${message("admin.coupon.priceExpressionTitle")}" />
					</td>
				</tr>
				<tr>
					<th>
						${message("admin.common.setting")}:
					</th>
					<td>
						<label>
							<input type="checkbox" name="isEnabled" value="true" checked="checked" />${message("Coupon.isEnabled")}
							<input type="hidden" name="_isEnabled" value="false" />
						</label>
						<label>
							<input type="checkbox" id="isExchange" name="isExchange" value="true" checked="checked" />${message("Coupon.isExchange")}
							<input type="hidden" name="_isExchange" value="false" />
						</label>
						
						<label>
							<input type="checkbox" id="isMarketbelong" name="isMarketbelong" value="true" />营销人员专属
							<input type="hidden" name="_isMarketbelong" value="false" />
						</label>
						
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Coupon.point")}:
					</th>
					<td>
						<input type="text" id="point" name="point" class="text" maxlength="9" />
					</td>
				</tr>
				 
				 <tr>
					<th>
						<span class="requiredField">*</span>限定每人领取数:
					</th>
					<td>
						<input type="text" id="limtNums" name="limtNums" class="text" maxlength="2" value="0"  /> 【0,表示不做限制】
					</td>
				</tr>
				 
			 
			            <table id="productTable" class="input">
							<tr>
								<th>
									${message("Promotion.products")}:
								</th>
								<td>
									<input type="text" id="productSelect" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
								</td>
							</tr>
							<tr id="productTitle" class="title hidden">
								<th>
									&nbsp;
								</th>
								<td>
									${message("Product.name")}  	${message("admin.common.handle")}
								</td>
								 
							</tr>
			   			</table>
		           
				
				
				
				
			</table>
		</div>
		<div class="tabContent">
			<table class="input">
				<tr>
					<td>
						<textarea id="editor" name="introduction" class="editor" style="width: 100%;"></textarea>
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