<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.productNotify.mailTitle")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
</head>
<body>
	<p>
		${message("admin.productNotify.welcome")}:
	</p>
	<p>
		${message("admin.productNotify.content", productNotify.product.name)}
	</p>
	<p>
		<a href="${toView}${productNotify.product.path}">${toView}${productNotify.product.path}</a>
	</p>
</body>
</html>