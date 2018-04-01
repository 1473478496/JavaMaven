<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.input .powered {
	font-size: 11px;
	text-align: right;
	color: #cccccc;
}

td a{
	color:red;
}

</style>
</head>
<body>
	<div class="path">
		${message("admin.index.title")}
	</div>
	<table class="input">
		<tr>
			<th>
				${message("admin.index.systemName")}:
			</th>
			<td>
				${systemName}
			</td>
			<th>
				${message("admin.index.systemVersion")}:
			</th>
			<td>
				${systemVersion}
			</td>
		</tr>
		<tr>
		<#--
			<th>
				${message("admin.index.official")}:
			</th>
			<td>
					<a href="http://www.mgb.cn" target="_blank">http://www.mgb.cn</a>
			</td>
			<th>
				${message("admin.index.bbs")}:
			</th>
			<td>
				<a href="http://bbs.www.mgb.cn" target="_blank">http://bbs.www.mgb.cn</a>
			</td>
			-->
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.javaVersion")}:
			</th>
			<td>
				${javaVersion}
			</td>
			<th>
				${message("admin.index.javaHome")}:
			</th>
			<td>
				${javaHome}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.osName")}:
			</th>
			<td>
				${osName}
			</td>
			<th>
				${message("admin.index.osArch")}:
			</th>
			<td>
				${osArch}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.serverInfo")}:
			</th>
			<td>
				${serverInfo}
			</td>
			<th>
				${message("admin.index.servletVersion")}:
			</th>
			<td>
				${servletVersion}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.marketableProductCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/product/list.jhtml?isMarketable=true">${marketableProductCount}</a>
			</td>
			<th>
				${message("admin.index.notMarketableProductCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/product/list.jhtml?isMarketable=false">${notMarketableProductCount}</a>
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.stockAlertProductCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/product/list.jhtml?isStockAlert=true">${stockAlertProductCount}</a>
			</td>
			<th>
				${message("admin.index.outOfStockProductCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/product/list.jhtml?isOutOfStock=true">${outOfStockProductCount}</a>
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.waitingPaymentOrderCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/order/list.jhtml?paymentStatus=unpaid">${waitingPaymentOrderCount}</a>
			</td>
			<th>
				${message("admin.index.waitingShippingOrderCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/order/list.jhtml?shippingStatus=unshipped&paymentStatus=paid">${waitingShippingOrderCount}</a>
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.index.memberCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/member/list.jhtml">${memberCount}
			</td>
			<th>
				${message("admin.index.unreadMessageCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/message/list.jhtml">${unreadMessageCount}</a>
			</td>
		</tr>
		
		<tr>
			<th>
				${message("admin.index.unReviewCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/review/list.jhtml">${unReviewCount}</a>
			</td>
			<th>
				${message("admin.index.unChangesCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/changes/list.jhtml">${unChangesCount}</a></a>
			</td>
			
		</tr>
		
		<tr>
			<th>
				${message("admin.index.unReturnsCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/returns/list.jhtml">${unReturnsCount}</a>
			</td>
			<th>
				${message("admin.index.unRefundsCount")}:
			</th>
			<td>
				<a href="/mall-web-admin/admin/returns/list.jhtml">${unRefundsCount}</a>
			</td>
			
			
		</tr>
		
		<tr>
			<td class="powered" colspan="4">
				COPYRIGHT © 2005-${.now?string("yyyy")} www.mgb.cn ALL RIGHTS RESERVED. 
			</td>
		</tr>
	</table>
</body>
</html>