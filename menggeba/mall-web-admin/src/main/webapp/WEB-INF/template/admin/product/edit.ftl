<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.edit")} - Powered By www.mgb.cn</title>
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
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
</style>
<script type="text/javascript">

function deleteTieSuit(suitId,titleId){
	$.dialog({
		type: "warn",
		content: "${message("admin.dialog.deleteConfirm")}",
		onOk: function() {
			$.ajax({
				url: "${base}/admin/tieInSale/deleteSuit.jhtml",
				type: "POST",
				data: {suitId: suitId,titleId:titleId},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if(message.type=='success'){
						$('#showSuit_'+suitId).remove();
					}
				}
			});
		}
	});
}
 var proDIds3=new Array();
 var proIds3 = new Array();
 function editTieSuit(suitId,titleId){
        
	    $.dialog({
			title: "编辑套装商品",
			[@compress single_line = true]
				content: '
				<form id="editSuitForm" action="${base}/admin/tieInSale/editSuit.jhtml" method="post">
				    <input type="hidden" name="token" value="${token}" \/>
				    <input type="hidden" name="proId" value="${product.id}" \/>
				    
					<input type="hidden" id="proIds3" name="productId3" value="" />
					<input type="hidden" id="proDeleteIds3" name="productDeleteId3" value="" />
					<table class="input" id="productSaleTable3">
					<\/table>
				<\/form>
				',
				[/@compress]
			width: 1100,
			modal: true,
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onShow: function() {
				$.ajax({
					url: "${base}/admin/tieInSale/suit.jhtml",
					type: "POST",
					data: {id: suitId},
					dataType: "json",
					cache: false,
					success: function(data) {
				    	var trHtml = '';
				    	
					    $.each(data.tieInSales, function(i, tieInSale) {
							if(i==0){
							   proDIds3.push(tieInSale.telIn.id);
			                 	trHtml += '<tr>';
								trHtml += '	<th>优惠套装标题:<\/th>';
								trHtml += '	<td><input type="hidden" name="proSuitId" value="'+tieInSale.productSuit.id+'" \/>'
				   				trHtml += ' <input type="hidden" name="titleId" value="'+tieInSale.tieInSaleTitle.id+'" \/>';
								trHtml += '	  <input type="text" id="text1" name="title" class="text" value="'+tieInSale.tieInSaleTitle.title+'" maxlength="200" \/>';
								trHtml += '	<\/td>';
								trHtml += '	<td><\/td><td><\/td><td><\/td>';
								trHtml += '<\/tr>';
								trHtml += '<tr>';
								trHtml += '	<th>';
								trHtml += '		优惠套装名称:';
								trHtml += '	<\/th>';
								trHtml += '	<td colspan="2">';
								trHtml += '		<input type="text" id="text1" name="fullName" class="text" value="'+tieInSale.productSuit.fullName+'"  maxlength="200" \/>';
								trHtml += '	<\/td>';
								trHtml += '	<td><\/td><td><\/td>';
								trHtml += '<\/tr>';
								trHtml += '<tr>';
								trHtml += '	<th>';
								trHtml += '		参与商品:';
								trHtml += '	<\/th>';
								trHtml += '	<td colspan="2">';
								trHtml += '		<input type="text" id="productSelect3" name="productSelect" class="text" maxlength="200" title="message("admin.promotion.productSelectTitle")" \/>';
								trHtml += '	<\/td>';
								trHtml += '	<td><\/td><td><\/td>';
								trHtml += '<\/tr>';
								trHtml += '<tr>';
								trHtml += '	<th>';
								trHtml += '		价格合计:';
								trHtml += '	<\/th>';
								trHtml += '	<td>';
								trHtml += '	<input id="mainProductPrice" value="${product.price}" type="hidden"\/>';
								trHtml += '	<div id="proPrice">'+data.originalPrice+'<\/div> ';
								trHtml += '	<\/td>';
								trHtml += '	<th>萌值合计：<\/th>';
								trHtml += '	<td> ';
								trHtml += '	<input id="mainProductPricePoint" value="${product.pricePoint}" type="hidden"\/>';
								trHtml += '	<div id="proPricePoint">'+data.originalPointPrice+'<\/div><\/td><td><\/td>';
								trHtml += '<\/tr>';
								trHtml += '<tr>';
								trHtml += '	<th>';
								trHtml += '		套装价格：';
								trHtml += '	<\/th>';
								trHtml += '	<td>';
								trHtml += '	   <input type="text" id="price" name="price" value="'+tieInSale.productSuit.price+'" class="text" maxlength="200" \/>';
								trHtml += '	<\/td>';
								trHtml += '	<th>套装萌值：<\/th>';
								trHtml += '	<td>';
								trHtml += '	   <input type="text" id="pricePoint" name="pricePoint" value="'+tieInSale.productSuit.pricePoint+'" class="text" maxlength="200" \/>';
								trHtml += '	<\/td><td><\/td>';
								trHtml += '<\/tr>';
								trHtml += '<tr>';
								trHtml += '	<th width="450">';
								trHtml += '		${message("Product.name")}';
								trHtml += '	<\/th>';
								trHtml += '	<th><\/th><th><\/th>';
								trHtml += '	<th>';
								trHtml += '	${message("admin.common.handle")}';
								trHtml += '	<\/th>';
								trHtml += '	<th><\/th>';
								trHtml += '<\/tr>';
							    trHtml += ' <tr >';
								trHtml += '<input id="itemPrice_'+tieInSale.telIn.id+'" name="itemId" type="hidden" value="'+tieInSale.telIn.id+'"\/>';
								trHtml += '<input id="itemPrice_'+tieInSale.telIn.id+'" name="itemPrice" type="hidden" value="'+tieInSale.telIn.price+'"\/>';
								trHtml += '<input id="itemPricePoint_'+tieInSale.telIn.id+'" name="itemPricePoint" type="hidden" value="'+tieInSale.telIn.pricePoint+'"\/>';
								trHtml += '<th width="450">'+tieInSale.telIn.fullName+'<\/th>';		
								trHtml += '<th><input type="hidden" value=""+tieInSale.telIn.id+"" /><\/th><th><\/th>';
								trHtml += '<th>';
								trHtml += '	<a href="'+tieInSale.productSuit.path+'" target="_blank">[${message("admin.common.view")}]<\/a>';
								trHtml += '	<a href="javascript:;" class="deleteProduct3">[${message("admin.common.delete")}]<\/a>';
								trHtml += '<\/th>';
								trHtml += '<th><\/th>';
								trHtml += '<\/tr>';
					        }else{
					       		 proDIds3.push(tieInSale.telIn.id);
					    		trHtml += '<tr>';
								trHtml += '<input id="itemId_'+tieInSale.telIn.id+'" name="itemId" type="hidden" value="'+tieInSale.telIn.id+'"\/>';
								trHtml += '<input id="itemPrice_'+tieInSale.productSuit.id+'" name="itemPrice" type="hidden" value="'+tieInSale.telIn.price+'"\/>';
								trHtml += '<input id="itemPricePoint_'+tieInSale.telIn.id+'" name="itemPricePoint" type="hidden" value="'+tieInSale.telIn.pricePoint+'"\/>';
								trHtml += 	'<th width="450">'+tieInSale.telIn.fullName+'<\/th>';
								trHtml += 	'<th><input type="hidden" value=""+tieInSale.telIn.id+"" /><\/th><th><\/th>';
								trHtml += 	'<th>';
								trHtml += 		'<a href="'+tieInSale.productSuit.path+'" target="_blank">[${message("admin.common.view")}]<\/a>';
								trHtml += 		'<a href="javascript:;" class="deleteProduct3">[${message("admin.common.delete")}]<\/a>';
								trHtml += 	'<\/th>';
								trHtml += 	'<th><\/th>';
								trHtml += '<\/tr>';
					    	}
					  	});
				    	$("#productSaleTable3").html(trHtml);
						var $productSaleTable3 = $("#productSaleTable3");
						var $productSelect3 = $("#productSelect3");
						var $deleteProduct3 = $("a.deleteProduct3");
						var productIds3 = new Array();
						var proDeleteIds3=new Array();
						// 优惠套装
						// 商品选择
						bindEvent($productSaleTable3, $productSelect3, $deleteProduct3, productIds3,proDeleteIds3);
						sumPrice();
				    }
				});
				$("#editSuitForm").validate({
				  rules: {
			             title:"required",
			             fullName:"required",
			             price: {
								required: true,
								min: 0,
								decimal: {
									integer: 12,
									fraction: ${setting.priceScale}
								}
							},
						    pricePoint:{
								required: true,
								digits: true
						   },
						   proIds3:"required"
			        }
				});
			},
			onOk: function() {
			     if(proIds3.length>0||proDIds3.length>0) {
				     $("#editSuitForm").submit();
				 } else {
					$.message("warn", "${message("套装不能没有参与商品")}");
				 }
				 return false;
			}
		});
 }
 
// 优惠套装
// 商品选择

function bindEvent($productSaleTable3, $productSelect3, $deleteProduct3, productIds3,proDeleteIds3){
	$productSelect3.autocomplete("product_select.jhtml", {
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
			if ($.inArray(item.id, productIds3) < 0&&$.inArray(item.id, proDIds3)<0) {
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 20) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
		[@compress single_line = true]
	    	var trHtml = '';
    		trHtml += '<tr>';
			trHtml += '<input id="itemId_'+item.id+'" name="itemId" type="hidden" value="'+item.id+'"\/>';
			trHtml += '<input id="itemPrice_'+item.id+'" name="itemPrice" type="hidden" value="'+item.price+'"\/>';
			trHtml += '<input id="itemPricePoint_'+item.id+'" name="itemPricePoint" type="hidden" value="'+item.pricePoint+'"\/>';
			trHtml += 	'<th width="600">'+item.fullName.substring(0, 20)+'<\/th>';
			trHtml += 	'<th><\/th><th><\/th>';
			trHtml += 	'<th>';
			trHtml += 		'<a href="${toView}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>';
			trHtml += 		'<a href="javascript:;" class="deleteProduct3">[${message("admin.common.delete")}]<\/a>';
			trHtml += 	'<\/th>';
			trHtml += 	'<th><\/th>';
			trHtml += '<\/tr>';
		[/@compress]
		$productSaleTable3.append(trHtml);
		productIds3.push(item.id);
		proIds3=productIds3;
		 var ids="";
		 $.each(productIds3, function(i, id3) {
		  ids+=""+id3+",";
		 });
		 $("#proIds3").val(ids);
		 sumPrice();
	});
	// 优惠套装
	// 删除商品
	$deleteProduct3.live("click", function() {
		var $this = $(this);
		var id = parseInt($this.closest("tr").find("input[name='itemId']").val());
		 var ids="";
		 if($.inArray(id,productIds3)<0)
		  {   
		      proDIds3= $.grep(proDIds3, function(n, i) {
				return n != id;
			  });
			  proDeleteIds3.push(id);
		 	  $.each(proDeleteIds3, function(i, dId) {
		      	 ids+=""+dId+",";
		  	  });
		     $("#proDeleteIds3").val(ids);
		}else{
		  	productIds3 = $.grep(productIds3, function(n, i) {
				return n != id;
			});
			$.each(productIds3, function(i, id3) {
		    	 ids+=""+id3+",";
			 });
			 proIds3=productIds3;
		 	$("#proIds3").val(ids);
		}
		$this.closest("tr").remove();
		sumPrice();
	});
}

function sumPrice(){
	var sum = parseFloat($('#mainProductPrice').val());
	$("#productSaleTable3").find("input[name='itemPrice']").each(function() {
		if ($.trim($(this).val()) != "") {
			sum = floatAdd(sum, parseFloat($(this).val()));
		}
	});
	$('#proPrice').html(sum.toFixed(2));
	sum = parseFloat($('#mainProductPricePoint').val());
	$("#productSaleTable3").find("input[name='itemPricePoint']").each(function() {
		if ($.trim($(this).val()) != "") {
			sum = floatAdd(sum, parseFloat($(this).val()));
		}
	});
	$('#proPricePoint').html(sum);
}

function floatAdd(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)), (floatMul(a, e) + floatMul(b, e)) / e;
}
function floatMul(a, b) {
    var c = 0;
    var d = a.toString();
    var e = b.toString();
    try {
        c += d.split(".")[1].length;
    } catch (f) {}
    try {
        c += e.split(".")[1].length;
    } catch (f) {}
    return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
}

$().ready(function() {
     var length=0;
	var $inputForm = $("#inputForm");
	var $productCategoryId = $("#productCategoryId");
	var $isMemberPrice = $("#isMemberPrice");
	var $memberPriceTr = $("#memberPriceTr");
	var $memberPrice = $("#memberPriceTr input");
	var $browserButton = $("#browserButton");
	var $productImageTable = $("#productImageTable");
	var $addProductImage = $("#addProductImage");
	var $deleteProductImage = $("a.deleteProductImage");
	var $parameterTable = $("#parameterTable");
	var $attributeTable = $("#attributeTable");
	var $specificationIds = $("#specificationSelect :checkbox");
	var $specificationProductTable = $("#specificationProductTable");
	var $addSpecificationProduct = $("#addSpecificationProduct");
	var $deleteSpecificationProduct = $("a.deleteSpecificationProduct");
	var productImageIndex = ${(product.productImages?size)!"0"};
	var previousProductCategoryId = "${product.productCategory.id}";
	
	var $productSaleTable1 = $("#productSaleTable1");
	var $productSelect1 = $("#productSelect1");
	var $productTitle1 = $("#productTitle1");
	var $deleteProduct1 = $("a.deleteProduct1");
	var productIds1 = [#if tieRecs?has_content][[#list tieRecs as tieRec]${tieRec.product.id}[#if tieRec.product_has_next], [/#if][/#list]][#else]new Array()[/#if];
	
	var $productSaleTable2 = $("#productSaleTable2");
	var $productSelect2 = $("#productSelect2");
	var $productTitle2 = $("#productTitle2");
	var $deleteProduct2 = $("a.deleteProduct2");
	var productIds2 = [#if tieBess?has_content][[#list tieBess as tieBes]${tieBes.product.id}[#if tieBes.product_has_next], [/#if][/#list]][#else]new Array()[/#if];
	
	var $addButton=$("#addButton");
	
	var $isIntegral= $("#isIntegral");
 
	
	var $price=$("#price");
	var oldprice=$price.val();
	
	var $image=$("#image");
    
    
    
	[@flash_message /]
		
	$("#imageUrl").click(function(){
		open($image.val());
	});
 
	//是否纯积分
	$isIntegral.click(function() {	  
		if ($(this).prop("checked")) { 
			$price.prop("readonly", true);
			$price.css({"background-color":"#888"});
			$price.val(0);
		
		} else {
			$price.prop("readonly", false);
			$price.css({"background-color":""});
			$price.val(oldprice);
		}
	});
	
		
	// 优惠套装
	$addButton.click(function() {
	    $.dialog({
			title: "添加套装商品",
			[@compress single_line = true]
				content: '
				<form id="addSuitForm" action="${base}/admin/tieInSale/addSuit.jhtml" method="POST">
				    <input type="hidden" name="token" value="${token}" \/>
				     <input type="hidden" name="proId" value="${product.id}" \/>
					<table class="input" id="productSaleTable3">
						<tr>
							<th>优惠套装标题:</th>
							<td>
							    <input type="hidden" id="proIds3" name="productId3" value="" />
								<input type="text" id="text1" name="title" class="text" maxlength="200" />
							</td>
							<td></td><td></td><td></td>
						</tr>
						<tr>
							<th>
								优惠套装名称:
							</th>
							<td colspan="2">
								<input type="text" id="text1" name="fullName" class="text" maxlength="200" />
							</td>
							<td></td><td></td>
						</tr>
						<tr>
							<th>
								参与商品:
							</th>
							<td colspan="2">
								<input type="text" id="productSelect3" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" \/>
							</td>
							<td></td><td></td>
						</tr>
						<tr>
							<th>
								价格合计:
							</th>
							<td>
								<input id="mainProductPrice" value="${product.price}" type="hidden"/>
							    <div id="proPrice">${product.price}</div> 
							</td>
							<th>萌值合计：</th>
							<td> <input id="mainProductPricePoint" value="${product.pricePoint}" type="hidden"/>
							<div id="proPricePoint"> ${product.pricePoint}</div></td><td></td>
						</tr>
						<tr>
							<th>
								套装价格：
							</th>
							<td>
							   <input type="text" id="price" name="price" class="text" maxlength="200" />
							</td>
							
							<th>套装萌值：</th>
							<td>
							   <input type="text" id="pricePoint" name="pricePoint" class="text" maxlength="200" />
							</td>
							<td>
							</td>
						</tr>
						<tr >
							<th width="400">
								${message("Product.name")}
							</th>
							<th></th><th></th>
							<th>
								${message("admin.common.handle")}
							</th>
							<th></th>
						</tr>
					</table>
				<\/form>
				',
				[/@compress]
			width: 1000,
			modal: true,
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}"	,
			onShow: function() {						
				var $productSaleTable3 = $("#productSaleTable3");
				var $productSelect3 = $("#productSelect3");
				var $deleteProduct3 = $("a.deleteProduct3");
				var productIds3 = new Array();
				var proDeleteIds3=new Array();
				// 优惠套装
				// 商品选择

				//bindEvent($productSaleTable3, $productSelect3, $deleteProduct3, productIds3,null,pri,priPoint);
				bindEvent($productSaleTable3, $productSelect3, $deleteProduct3, productIds3,proDeleteIds3);
			    $("#addSuitForm").validate({
			        rules: {
			             title:"required",
			             fullName:"required",
			             price: {
								required: true,
								min: 0,
								decimal: {
									integer: 12,
									fraction: ${setting.priceScale}
								}
							},
						    pricePoint:{
								required: true,
								digits: true,
								decimal: {
									integer: 12,
									fraction: ${setting.priceScale}
								}
						   },
						   proIds3:"required"
			        }
			    
			    });
			  
			},
			onOk: function() {
			   if(proIds3.length>0) {
				     $("#addSuitForm").submit();
				 } else {
					$.message("warn", "${message("套装组合的参与商品的个数不能为零！")}");
				 }
				 return false;
			}
		});
	});

	// 推荐配件
	// 商品选择
	$productSelect1.autocomplete("product_select.jhtml", {
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
			if ($.inArray(item.id, productIds1) < 0) {
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 20) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
		[@compress single_line = true]
			var trHtml = 
			'<tr class="productTr">
				<th>
					<input type="hidden" name="productIds1" value="' + item.id + '" \/>
					&nbsp;
				<\/th>
				<td>
					<span name="titleCate1" value="'+$("#text1").val()+'_'+item.id+'">' + $("#text1").val() + '<\/span>
					<input type="hidden" name="titleCate1" value="'+$("#text1").val()+'_'+item.id+'" \/>
				<\/td>
				<td>
					<span title="' + item.fullName + '">' + item.fullName.substring(0, 20) + '<\/span>
				<\/td>
				<td>
					<a href="${toView}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>
					<a href="javascript:;" class="deleteProduct1">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productTitle1.show();
		$productSaleTable1.append(trHtml);
		productIds1.push(item.id);
	});
	// 推荐配件
	// 删除商品
	$deleteProduct1.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				var id = parseInt($this.closest("tr").find("input:hidden").val());
				productIds1 = $.grep(productIds1, function(n, i) {
					return n != id;
				});
				$this.closest("tr").remove();
				if ($productSaleTable1.find("tr.productTr").size() <= 0) {
					$productTitle1.hide();
				}
			}
		});
	});
	// 最佳组合
	// 商品选择
	$productSelect2.autocomplete("product_select.jhtml", {
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
			if ($.inArray(item.id, productIds2) < 0) {
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 20) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
		[@compress single_line = true]
			var trHtml = 
			'<tr class="productTr">
				<th>
					<input type="hidden" name="productIds2" value="' + item.id + '" \/>
					&nbsp;
				<\/th>
				<td>
					<span title="' + item.fullName + '">' + item.fullName.substring(0, 20) + '<\/span>
				<\/td>
				<td>
					<a href="${toView}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>
					<a href="javascript:;" class="deleteProduct2">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productTitle2.show();
		$productSaleTable2.append(trHtml);
		productIds2.push(item.id);
	});
	// 最佳组合
	// 删除商品
	$deleteProduct2.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				var id = parseInt($this.closest("tr").find("input:hidden").val());
				productIds2 = $.grep(productIds2, function(n, i) {
					return n != id;
				});
				$this.closest("tr").remove();
				if ($productSaleTable2.find("tr.productTr").size() <= 0) {
					$productTitle2.hide();
				}
			}
		});
	});

	
	$browserButton.browser();
	// 会员价
	$isMemberPrice.click(function() {
		if ($(this).prop("checked")) {
			$memberPriceTr.show();
			$memberPrice.prop("disabled", false);
		} else {
			$memberPriceTr.hide();
			$memberPrice.prop("disabled", true);
		}
	});
	
	// 增加商品图片
	$addProductImage.click(function() {
		[@compress single_line = true]
			var trHtml = 
			'<tr>
				<td>
					<input type="file" name="productImages[' + productImageIndex + '].file" class="productImageFile" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].title" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productImageTable.append(trHtml);
		productImageIndex ++;
	});

	// 删除商品图片
	$deleteProductImage.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	// 修改商品分类
	$productCategoryId.change(function() {
		var hasValue = false;
		$parameterTable.add($attributeTable).find(":input").each(function() {
			if ($.trim($(this).val()) != "") {
				hasValue = true;
				return false;
			}
		});
		if (hasValue) {
			$.dialog({
				type: "warn",
				content: "${message("admin.product.productCategoryChangeConfirm")}",
				width: 450,
				onOk: function() {
					loadParameter();
					loadAttribute();
					previousProductCategoryId = $productCategoryId.val();
				},
				onCancel: function() {
					$productCategoryId.val(previousProductCategoryId);
				}
			});
		} else {
			loadParameter();
			loadAttribute();
			previousProductCategoryId = $productCategoryId.val();
		}
	});
	
	// 加载参数
	function loadParameter() {
		$.ajax({
			url: "parameter_groups.jhtml",
			type: "GET",
			data: {id: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$parameterTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, parameterGroup) {
					trHtml += '<tr><td style="text-align: right;"><strong>' + parameterGroup.name + ':<\/strong><\/td><td>&nbsp;<\/td><\/tr>';
					$.each(parameterGroup.parameters, function(i, parameter) {
						[@compress single_line = true]
							trHtml += 
							'<tr>
								<th>' + parameter.name + ': <\/th>
								<td>
									<input type="text" name="parameter_' + parameter.id + '" class="text" maxlength="200" \/>
								<\/td>
							<\/tr>';
						[/@compress]
					});
				});
				$parameterTable.append(trHtml);
			}
		});
	}
	
	// 加载属性
	function loadAttribute() {
		$.ajax({
			url: "attributes.jhtml",
			type: "GET",
			data: {id: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$attributeTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, attribute) {
					var optionHtml = '<option value="">${message("admin.common.choose")}<\/option>';
					$.each(attribute.options, function(j, option) {
						optionHtml += '<option value="' + option + '">' + option + '<\/option>';
					});
					[@compress single_line = true]
						trHtml += 
						'<tr>
							<th>' + attribute.name + ': <\/th>
							<td>
								<select name="attribute_' + attribute.id + '">
									' + optionHtml + '
								<\/select>
							<\/td>
						<\/tr>';
					[/@compress]
				});
				$attributeTable.append(trHtml);
			}
		});
	}
	
	// 修改商品规格
	$specificationIds.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$specificationProductTable.find("tr:gt(1)").remove();
		}
		var $this = $(this);
		if ($this.prop("checked")) {
			$specificationProductTable.find("td.specification_" + $this.val()).show().find("select").prop("disabled", false);
		} else {
			$specificationProductTable.find("td.specification_" + $this.val()).hide().find("select").prop("disabled", true);
		}
	});
	
	// 增加规格商品
	$addSpecificationProduct.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$.message("warn", "${message("admin.product.specificationRequired")}");
			return false;
		}
		if ($specificationProductTable.find("tr:gt(1)").size() == 0) {
			$tr = $specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
			$tr.find("td:first").text("${message("admin.product.currentSpecification")}");
			$tr.find("td:last").text("-");
		} else {
			$specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
		}
	});
	
	// 删除规格商品
	$deleteSpecificationProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	$.validator.addClassRules({
		memberPrice: {
			min: 0,
			decimal: {
				integer: 12,
				fraction: ${setting.priceScale}
			}
		},
		productImageFile: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
		productImageOrder: {
			digits: true
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.jhtml?previousSn=${product.sn}",
					cache: false
				}
			},
			price: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			cost: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			marketPrice: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			weight:  {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			len: "digits",
			width: "digits",
			height: "digits",
			stock: "digits",
			point: "digits",
			pricePoint: "digits"
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			}
		},
		submitHandler: function(form) {
			if ($specificationIds.filter(":checked").size() > 0 && $specificationProductTable.find("tr:gt(1)").size() == 0) {
				$.message("warn", "${message("admin.product.specificationProductRequired")}");
				return false;
			} else {
				var isRepeats = false;
				var parameters = new Array();
				$specificationProductTable.find("tr:gt(1)").each(function() {
					var parameter = $(this).find("select").serialize();
					if ($.inArray(parameter, parameters) >= 0) {
						$.message("warn", "${message("admin.product.specificationValueRepeat")}");
						isRepeats = true;
						return false;
					} else {
						parameters.push(parameter);
					}
				});
				if (!isRepeats) {
					$specificationProductTable.find("tr:eq(1)").find("select").prop("disabled", true);
					addCookie("previousProductCategoryId", $productCategoryId.val(), {expires: 24 * 60 * 60});
					form.submit();
				}
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${product.id}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.introduction")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.productImage")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.parameter")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.attribute")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.specification")}" />
			</li>
			<li>
				<input type="button" value="推荐配件" />
			</li>
			<li>
				<input type="button" value="最佳组合" />
			</li>
			<li>
				<input type="button" value="优惠套装" />
			</li>
		</ul>
		<table class="input tabContent">
			[#if product.specifications?has_content]
				<tr>
					<th>
						${message("Product.specifications")}:
					</th>
					<td>
						[#list product.specificationValues as specificationValue]
							${specificationValue.name}
						[/#list]
					</td>
				</tr>
			[/#if]
			[#if product.validPromotions?has_content]
				<tr>
					<th>
						${message("Product.promotions")}:
					</th>
					<td>
						[#list product.validPromotions as promotion]
							<p>
								${promotion.name}
								[#if promotion.beginDate?? || promotion.endDate??]
									[${promotion.beginDate} ~ ${promotion.endDate}]
								[/#if]
							</p>
						[/#list]
					</td>
				</tr>
			[/#if]
			<tr>
				<th>
					${message("Product.productCategory")}:
				</th>
				<td>
					<select id="productCategoryId" name="productCategoryId">
						[#list productCategoryTree as productCategory]
							<option value="${productCategory.id}"[#if productCategory == product.productCategory] selected="selected"[/#if]>
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
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${product.name}" maxlength="200" />
				</td>
			</tr>
			
			<!-- 是否纯积分商品  add by songwt 20151030 -->
			<tr>
				<th>
					 ${message("Product.isIntegral")}:
				</th>
				<td>
					<input type="checkbox" id="isIntegral" name="isIntegral" value="true"[#if product.isIntegral] checked="checked"[/#if] />${message("Product.isIntegral")}
					<input type="hidden" name="_isIntegral" value="false" />
				</td>
			</tr>
			
			
			<tr>
				<th>
					${message("Product.sn")}:
				</th>
				<td>
					<input type="text" name="sn" class="text" value="${product.sn}" maxlength="100" title="${message("admin.product.snTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.price")}:
				</th>
				<td>
					<input type="text"   id="price" name="price" class="text"  value="${product.price}" maxlength="16" [#if product.price==0]  readonly="true" style="background-color: #888"  [/#if]  />
				</td>
			</tr>
			
			<!-- 所需积分  add by huangjinhua 20150724 -->
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.price.point")}:
				</th>
				<td>
					<input type="text" name="pricePoint" class="text" value="${product.pricePoint}" maxlength="16" />
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Product.memberPrice")}:
				</th>
				<td>
					<label>
						<input type="checkbox" id="isMemberPrice" name="isMemberPrice" value="true"[#if product.memberPrice?has_content] checked="checked"[/#if] />${message("admin.product.isMemberPrice")}
					</label>
				</td>
			</tr>
			<tr id="memberPriceTr"[#if !product.memberPrice?has_content] class="hidden"[/#if]>
				<th>
					&nbsp;
				</th>
				<td>
					[#list memberRanks as memberRank]
						${memberRank.name}: <input type="text" name="memberPrice_${memberRank.id}" class="text memberPrice" value="${product.memberPrice.get(memberRank)}" maxlength="16" style="width: 60px; margin-right: 6px;"[#if !product.memberPrice?has_content] disabled="disabled"[/#if] />
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.cost")}:
				</th>
				<td>
					<input type="text" name="cost" class="text" value="${product.cost}" maxlength="16" title="${message("admin.product.costTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.marketPrice")}:
				</th>
				<td>
					<input type="text" name="marketPrice" class="text" value="${product.marketPrice}" maxlength="16" title="${message("admin.product.marketPriceTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.image")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" id="image" class="text" value="${product.image}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						[#if product.image??]
							<a href="javascript:;" id="imageUrl">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.unit")}:
				</th>
				<td>
					<input type="text" name="unit" class="text" maxlength="200" value="${product.unit}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.weight")}(kg):
				</th>
				<td>
					<input type="text" name="weight" class="text" value="${product.weight}" maxlength="9" />
				</td>
			</tr>
			
			<tr>
				<th>
					${message("Product.len")}
				</th>
				<td>
					<input type="text" name="len" class="text"value="${product.len}" maxlength="9" title="" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.width")}
				</th>
				<td>
					<input type="text" name="width" class="text"value="${product.width}" maxlength="9" title="" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.height")}
				</th>
				<td>
					<input type="text" name="height" class="text" value="${product.height}" maxlength="9" title="" />
				</td>
			</tr>
			
			
			<tr>
				<th>
					${message("Product.stock")}:
				</th>
				<td>
					<input type="text" name="stock" class="text" value="${product.stock}" maxlength="9" title="${message("admin.product.stockTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.stockMemo")}:
				</th>
				<td>
					<input type="text" name="stockMemo" class="text" value="${product.stockMemo}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.point")}:
				</th>
				<td>
					<input type="text" name="point" class="text" value="${product.point}" maxlength="9" title="${message("admin.product.pointTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.brand")}:
				</th>
				<td>
					<select name="brandId">
						<option value="">${message("admin.common.choose")}</option>
						[#list brands as brand]
							<option value="${brand.id}"[#if brand == product.brand] selected="selected"[/#if]>
								${brand.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.tags")}:
				</th>
				<td>
					[#list tags as tag]
						<label>
							<input type="checkbox" name="tagIds" value="${tag.id}"[#if product.tags?seq_contains(tag)] checked="checked"[/#if] />${tag.name}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isMarketable" value="true"[#if product.isMarketable] checked="checked"[/#if] />${message("Product.isMarketable")}
						<input type="hidden" name="_isMarketable" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isList" value="true"[#if product.isList] checked="checked"[/#if] />${message("Product.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true"[#if product.isTop] checked="checked"[/#if] />${message("Product.isTop")}
						<input type="hidden" name="_isTop" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isGift" value="true"[#if product.isGift] checked="checked"[/#if] />${message("Product.isGift")}
						<input type="hidden" name="_isGift" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isNewFirst" value="true"[#if product.isNewFirst] checked="checked"[/#if] />${message("Product.isNewFirst")}
						<input type="hidden" name="_isNewFirst" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.memo")}:
				</th>
				<td>
					<input type="text" name="memo" class="text" value="${product.memo}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.keyword")}:
				</th>
				<td>
					<input type="text" name="keyword" class="text" value="${product.keyword}" maxlength="200" title="${message("admin.product.keywordTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoTitle")}:
				</th>
				<td>
					<input type="text" name="seoTitle" class="text" value="${product.seoTitle}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoKeywords")}:
				</th>
				<td>
					<input type="text" name="seoKeywords" class="text" value="${product.seoKeywords}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoDescription")}:
				</th>
				<td>
					<input type="text" name="seoDescription" class="text" value="${product.seoDescription}" maxlength="200" />
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
		<table class="input tabContent">
			<tr>
				<td>
					<textarea id="editor" name="introduction" class="editor" style="width: 100%;">${product.introduction?html}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
				
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<td>
					<table id="productImageTable" class="input">
						<tr>
							<td colspan="4">
								<a href="javascript:;" id="addProductImage" class="button">${message("admin.product.addProductImage")}</a>
							</td>
						</tr>
						<tr class="title">
							<th>
								${message("ProductImage.file")}
							</th>
							<th>
								${message("ProductImage.title")}
							</th>
							<th>
								${message("admin.common.order")}
							</th>
							<th>
								${message("admin.common.delete")}
							</th>
						</tr>
						[#list product.productImages as productImage]
						<tr>
							<td>
								<input type="hidden" name="productImages[${productImage_index}].source" value="${productImage.source}" />
								<input type="hidden" name="productImages[${productImage_index}].large" value="${productImage.large}" />
								<input type="hidden" name="productImages[${productImage_index}].medium" value="${productImage.medium}" />
								<input type="hidden" name="productImages[${productImage_index}].thumbnail" value="${productImage.thumbnail}" />
								<input type="file" name="productImages[${productImage_index}].file" class="productImageFile ignore" />
								<a href="${productImage.large}" target="_blank">${message("admin.common.view")}</a>
							</td>
							<td>
								<input type="text" name="productImages[${productImage_index}].title" class="text" maxlength="200" value="${productImage.title}" />
							</td>
							<td>
								<input type="text" name="productImages[${productImage_index}].order" class="text productImageOrder" value="${productImage.order}" maxlength="9" style="width: 50px;" />
							</td>
							<td>
								<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
						[/#list]
					</table>
				</td>
			</tr>
			<tr>
				<td>
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
				</td>
			</tr>
		</table>
		<table id="parameterTable" class="input tabContent">
			[#list product.productCategory.parameterGroups as parameterGroup]
				<tr>
					<td style="text-align: right; padding-right: 10px;">
						<strong>${parameterGroup.name}:</strong>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				[#list parameterGroup.parameters as parameter]
					<tr>
						<th>${parameter.name}:</th>
						<td>
							<input type="text" name="parameter_${parameter.id}" class="text" value="${product.parameterValue.get(parameter)}" maxlength="200" />
						</td>
					</tr>
				[/#list]
			[/#list]
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
		<table id="attributeTable" class="input tabContent">
			[#list product.productCategory.attributes as attribute]
				<tr>
					<th>${attribute.name}:</th>
					<td>
						<select name="attribute_${attribute.id}">
							<option value="">${message("admin.common.choose")}</option>
							[#list attribute.options as option]
								<option value="${option}"[#if option == product.getAttributeValue(attribute)] selected="selected"[/#if]>${option}</option>
							[/#list]
						</select>
					</td>
				</tr>
			[/#list]
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
		<table class="input tabContent">
			<tr class="title">
				<th>
					${message("admin.product.selectSpecification")}:
				</th>
			</tr>
			<tr>
				<td>
					<div id="specificationSelect" class="specificationSelect">
						<ul>
							[#list specifications as specification]
								<li>
									<label>
										<input type="checkbox" name="specificationIds" value="${specification.id}"[#if product.specifications?seq_contains(specification)] checked="checked"[/#if] />${specification.name}
										[#if specification.memo??]
											<span class="gray">[${specification.memo}]</span>
										[/#if]
									</label>
								</li>
							[/#list]
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<a href="javascript:;" id="addSpecificationProduct" class="button">${message("admin.product.addSpecificationProduct")}</a>
				</td>
			</tr>
			<tr>
				<td>
					<table id="specificationProductTable" class="input">
						<tr class="title">
							<td width="60">
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
									${specification.name}
									[#if specification.memo??]
										<span class="gray">[${specification.memo}]</span>
									[/#if]
								</td>
							[/#list]
							<td>
								${message("admin.common.handle")}
							</td>
						</tr>
						<tr class="hidden">
							<td>
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
									<select name="specification_${specification.id}"[#if !product.specifications?seq_contains(specification)] disabled="disabled"[/#if]>
										[#list specification.specificationValues as specificationValue]
											<option value="${specificationValue.id}">${specificationValue.name}</option>
										[/#list]
									</select>
								</td>
							[/#list]
							<td>
								<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
						[#if product.specifications?has_content]
							<tr>
								<td>
									${message("admin.product.currentSpecification")}
									<input type="hidden" name="specificationProductIds" value="${product.id}" />
								</td>
								[#list specifications as specification]
									<td class="specification_${specification.id}[#if !product.specifications?seq_contains(specification)] hidden[/#if]">
										<select name="specification_${specification.id}"[#if !product.specifications?seq_contains(specification)] disabled="disabled"[/#if]>
											[#list specification.specificationValues as specificationValue]
												<option value="${specificationValue.id}"[#if product.specificationValues?seq_contains(specificationValue)] selected="selected"[/#if]>${specificationValue.name}</option>
											[/#list]
										</select>
									</td>
								[/#list]
								<td>
									-
								</td>
							</tr>
						[/#if]
						[#list product.siblings as specificationProduct]
							<tr>
								<td>
									&nbsp;
									<input type="hidden" name="specificationProductIds" value="${specificationProduct.id}" />
								</td>
								[#list specifications as specification]
									<td class="specification_${specification.id}[#if !specificationProduct.specifications?seq_contains(specification)] hidden[/#if]">
										<select name="specification_${specification.id}"[#if !specificationProduct.specifications?seq_contains(specification)] disabled="disabled"[/#if]>
											[#list specification.specificationValues as specificationValue]
												<option value="${specificationValue.id}"[#if specificationProduct.specificationValues?seq_contains(specificationValue)] selected="selected"[/#if]>${specificationValue.name}</option>
											[/#list]
										</select>
									</td>
								[/#list]
								<td>
									<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
									<!--<a href="edit.jhtml?id=${specificationProduct.id}">[${message("admin.common.edit")}]</a>-->
								</td>
							</tr>
						[/#list]
					</table>
				</td>
			</tr>
			<tr>
			<td>
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
			</td>
			</tr>
		</table>
		
		<!--推荐配件b-->
		<table id="productSaleTable1" class="input tabContent">
			<tr class="title">
				<th>
					推荐配件:
				</th>
				<td></td><td></td><td></td><td></td>
			</tr>
			<tr>
				<th>推荐标题:</th>
				<td>
					<input type="text" id="text1" name="" class="text" maxlength="200" />
				</td>
				<td></td><td></td><td></td>
			</tr>
			<tr>
				<th>
					推荐商品:
				</th>
				<td colspan="2">
					<input type="text" id="productSelect1" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
				</td>
				<td></td><td></td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
				<th>
					&nbsp;
				</th>
				<th>
					&nbsp;
				</th>
			</tr>
			<tr id="productTitle1" class="title">
				<th>
					&nbsp;
				</th>
				<td width="25%">
					推荐标题
				</td>
				<td width="25%">
					${message("Product.name")}
				</td>
				<td	width="25%">
					${message("admin.common.handle")}
				</td>
				<td></td>
			</tr>
			[#list tieRecs as tieRec]
				<tr class="productTr">
					<th>
						<input type="hidden" name="productIds1" value="${tieRec.telIn.id}" />
						&nbsp;
					</th>
					<td>
						<span name="titleCate1" value="${tieRec.tieInSaleTitle.title}">${tieRec.tieInSaleTitle.title}</span>
						<input type="hidden" name="titleCate1" value="${tieRec.tieInSaleTitle.title}_${tieRec.telIn.id}"/>
					</td>
					<td>
						<span title="${tieRec.telIn.fullName}">${abbreviate(tieRec.telIn.fullName, 30)}</span>
					</td>
					<td>
						<a href="${toView}${tieRec.telIn.path}" target="_blank">[${message("admin.common.view")}]</a>
						<a href="javascript:;" class="deleteProduct1">[${message("admin.common.delete")}]</a>
					</td>
				</tr>
			[/#list]
			
		</table>
		<!--推荐配件e-->
		<!--最佳组合b-->
		<table id="productSaleTable2" class="input tabContent">
			<tr class="title">
				<th>
					最佳组合:
				</th>
				<td></td><td></td><td></td><td></td>
			</tr>
			<tr>
				<th>
					推荐商品:
				</th>
				<td colspan="2">
					<input type="text" id="productSelect2" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
				</td>
				<td></td><td></td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
				<th>
					&nbsp;
				</th>
			</tr>
			<tr id="productTitle2" class="title">
				<th>
					&nbsp;
				</th>
				<td width="30%">
					${message("Product.name")}
				</td>
				<td width="30%">
					${message("admin.common.handle")}
				</td>
				<td></td>
				<td></td>
			</tr>
			[#list tieBess as tieBes]
				<tr class="productTr">
					<th>
						<input type="hidden" name="productIds2" value="${tieBes.telIn.id}" />
						&nbsp;
					</th>
					<td>
						<span title="${tieBes.telIn.fullName}">${abbreviate(tieBes.telIn.fullName, 30)}</span>
					</td>
					<td>
						<a href="${toView}${tieBes.telIn.path}" target="_blank">[${message("admin.common.view")}]</a>
						<a href="javascript:;" class="deleteProduct2">[${message("admin.common.delete")}]</a>
					</td>
				</tr>
			[/#list]
				
		</table>
		<!--最佳组合e-->
		
		
		
		<!--套装商品b-->
		<table class="input tabContent" id="tableContent3">
			<tr class="title">
				<th>
					<input type="button" id="addButton" class="iconButton" value="添加" />
				</th>
				<td></td><td></td><td></td><td></td><td></td><td></td>

			</tr>
			<tr>
				<th>
					优惠套装标题
				</th>
				<th>
					优惠套装名称
				</th>
				<th>
					套装价格
				</th>

				<th>
					原价
				</th>
				<th>
					优惠价
				</th>
				<th>
					操作
				</th>
				<th></th>
			</tr>
			[#if tieInSaleCatetories?has_content]
				[#list tieInSaleCatetories as tieInSaleCatetory]
				    [#if tieInSaleCatetory.category=="preferentialSuit"]
				        [#if tieTitles.get(tieInSaleCatetory.id)??]
							[#list tieTitles.get(tieInSaleCatetory.id) as tieTitle]
							   [#assign  tieInSale=tieInSales.get(tieTitle.id).get(0)]
								<tr id="showSuit_${tieInSale.productSuit.id}">
								    <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" >  ${tieTitle.title} </td>
								    <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" >  ${abbreviate(tieInSale.productSuit.fullName, 15)}</td>
								    <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" >  ${tieInSale.productSuit.price}+${tieInSale.productSuit.pricePoint}萌值</td>
								    <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" > ${originalPrices.get(tieTitle.id)}+${originalPointPrices.get(tieTitle.id)}萌值</td>
								    <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" >  ${originalPrices.get(tieTitle.id)-tieInSale.productSuit.price}+${originalPointPrices.get(tieTitle.id)-tieInSale.productSuit.pricePoint}萌值</td>
								   <td style="width:120px;text-align: right; padding: 5px 10px 5px 0;" >
										<a href="javascript:editTieSuit(${tieInSale.productSuit.id},${tieTitle.id});"id="editSuit target="_blank">[编辑]</a>
										<a href="javascript:deleteTieSuit(${tieInSale.productSuit.id},${tieTitle.id});" class="deleteProducts3">[${message("admin.common.delete")}]</a>
									</td>
									<td align="center"></td>
								</tr>
							[/#list]
						[/#if]
						[#break]
					[/#if]
				[/#list]
			[/#if]
			<th>
				</th>
				<td><input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" /></td><td></td><td></td><td></td><td></td><td></td>
		</table>
		<!--套装商品e-->
		
	</form>
</body>
</html>