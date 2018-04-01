<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.role.add")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.authorities label {
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
	var $selectAll = $("#inputForm .selectAll");
	
	[@flash_message /]
	
	$selectAll.click(function() {
		var $this = $(this);
		var $thisCheckbox = $this.closest("tr").find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.prop("checked", false);
		} else {
			$thisCheckbox.prop("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			authorities: "required"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.role.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Role.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Role.description")}:
				</th>
				<td>
					<input type="text" name="description" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.productGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:product" />${message("admin.role.product")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:productCategory" />${message("admin.role.productCategory")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:parameterGroup" />${message("admin.role.parameterGroup")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:attribute" />${message("admin.role.attribute")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:specification" />${message("admin.role.specification")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:brand" />${message("admin.role.brand")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:productNotify" />${message("admin.role.productNotify")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:productCateIndex" />${message("admin.role.productCateIndex")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.orderGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:order" />${message("admin.role.order")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:print" />${message("admin.role.print")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:payment" />${message("admin.role.payment")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:refunds" />${message("admin.role.refunds")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:shipping" />${message("admin.role.shipping")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:returns" />${message("admin.role.returns")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:changes" />${message("admin.role.changes")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:deliveryCenter" />${message("admin.role.deliveryCenter")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:deliveryTemplate" />${message("admin.role.deliveryTemplate")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.memberGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:member" />${message("admin.role.member")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:memberRank" />${message("admin.role.memberRank")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:memberBirthdayRights" />${message("admin.role.memberBirthdayRights")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:memberAttribute" />${message("admin.role.memberAttribute")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:review" />${message("admin.role.review")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:consultation" />${message("admin.role.consultation")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:signInRule" />${message("admin.role.signInRule")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.contentGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:navigation" />${message("admin.role.navigation")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:article" />${message("admin.role.article")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:articleCategory" />${message("admin.role.articleCategory")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:tag" />${message("admin.role.tag")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:friendLink" />${message("admin.role.friendLink")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:adPosition" />${message("admin.role.adPosition")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:ad" />${message("admin.role.ad")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:template" />${message("admin.role.template")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:cache" />${message("admin.role.cache")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:static" />${message("admin.role.static")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:index" />${message("admin.role.index")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.marketingGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:promotion" />${message("admin.role.promotion")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:coupon" />${message("admin.role.coupon")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:seo" />${message("admin.role.seo")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:sitemap" />${message("admin.role.sitemap")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:onSale" />${message("admin.role.dailySpecial")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:secKill" />${message("admin.main.secKill")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:poster" />${message("admin.main.poster")}
						</label>												
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.statisticsGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:statistics" />${message("admin.role.statistics")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:sales" />${message("admin.role.sales")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:salesRanking" />${message("admin.role.salesRanking")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:purchaseRanking" />${message("admin.role.purchaseRanking")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:deposit" />${message("admin.role.deposit")}
						</label>
					</span>
				</td>
			</tr>
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">${message("admin.role.systemGroup")}</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:setting" />${message("admin.role.setting")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:area" />${message("admin.role.area")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:paymentMethod" />${message("admin.role.paymentMethod")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:shippingMethod" />${message("admin.role.shippingMethod")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:shippingCalculate" />运费维护
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:deliveryCorp" />${message("admin.role.deliveryCorp")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:paymentPlugin" />${message("admin.role.paymentPlugin")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:storagePlugin" />${message("admin.role.storagePlugin")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:admin" />${message("admin.role.admin")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:role" />${message("admin.role.role")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:message" />${message("admin.role.message")}
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:log" />${message("admin.role.log")}
						</label>
					</span>
				</td>
			</tr>
			<!--权益-->
			<tr class="authorities">
				<th>
					<a href="javascript:;" class="selectAll" title="${message("admin.role.selectAll")}">权益管理</a>
				</th>
				<td>
					<span class="fieldSet">
						<label>
							<input type="checkbox" name="authorities" value="admin:rights" />权益管理
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:rProduct" />权益商品
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:rOrder" />权益订单
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:rBrand" />权益品牌
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:rCategory" />权益分类
						</label>
						<label>
							<input type="checkbox" name="authorities" value="admin:rTrade" />权益商户
						</label>
					</span>
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