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
	//限制审核意见长度
	$("#auditDesc").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 119){
	    $(this).val($(this).val().substring(0,120));
	   }
	   var num = 120 - len;
	   $("#word").text(num);
	 });
	//金额
	$("#returnPrice").keyup(function(){
		var returnPrice=$("#returnPrice").val();
		var payPrice=$("#payPrice").val();
		//returnPrice= returnPrice.replace(/[^\d]/g,'');
		var p =/^[1-9](\d+(\.\d{1,2})?)?$/; 
		var p1=/^[0-9](\.\d{1,2})?$/;
		if(p.test(returnPrice)||p1.test(returnPrice)){
		   var price1=parseFloat(payPrice);
		   var price2=parseFloat(returnPrice);
		   if(price1<price2){
		   		$("#returnPrice").val("");
		   }else{
				$("#returnPrice").val(returnPrice);
			}
		}else{
			$("#returnPrice").val("");
			 
		}
		
		
	});
	//审核通过
	$("#approve").click(function() {
		var auditDesc = $("#auditDesc").val();
		var returnPrice=$("#returnPrice").val();
		$.ajax({
			url: "${base}/admin/returns/approve.jhtml",
			type: "POST",
			data: {id: ${returns.id}, returnPrice:returnPrice,auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/returns/list.jhtml";
				}else{
					$.message(message);
				}
			}
		});
	});
	
	//审核拒绝
	$("#unapprove").click(function() {
		var auditDesc = $("#auditDesc").val();	
		$.ajax({
			url: "${base}/admin/returns/unapprove.jhtml",
			type: "POST",
			data: {id: ${returns.id}, auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/returns/list.jhtml";
				}else{
					$.message(message);
				}
			}
		});
	});
});
</script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 退货处理
	</div>
	<input type="hidden" name="id" value="${returns.id}"/>
 	<div class="content">
		<div class="returnsInfo">
			<p><span>退货信息:</span></p>
		 	<table>
				<tr>
					<th>
						订单号:
					</th>
					<td>
						<a href="../order/view.jhtml?id=${returns.order.id}">${returns.order.sn}</a>
					</td>
					<th>
						退款金额:
					</th>
					<td>
						<input type="text" id="returnPrice" name="returnPrice" class="text" value="[#if returnsItem?has_content]${returnsItem.price}[/#if]" maxlength="200" placeholder="非负且小于订单金额"/>
					    <lable id="lable"  style="display:none;font-color:red"></lable>
					</td>
				</tr>
				<tr>
					<th>
						理由:
					</th>
					<td>
						${returns.returnCause}
					</td>
					<th>
						退款积分
					</th>
					<td>
						${returns.orderItem.payPricePoint}萌值
					</td>
				</tr>
				<tr>
					<th>
						凭证:
					</th>
					<td>
						[#if returns.returnVoucher?has_content]
							<a href="${returns.returnVoucher}"  target="_blank">点击查看</a>
						[#else]
						          点击查看(没有上传凭证)
						[/#if]
					</td>
					<th>
						问题描述:
					</th>
					<td>
						${returns.returnDesc}
					</td>
				</tr>
				<tr>
					<th>退款运费:</th>
					<td>[#list returns.returnsItems as returnsItem]
	        				[#if returnsItem??]
	        					${returnsItem.price - returns.orderItem.price*returnsItem.quantity}
							[/#if]
					    [/#list]
					</td>
					[#if returns.order.couponCode??&&returns.orderItem.couponDivide?? ]
					<th>
						优惠券信息备注:
					</th>
					<td colspan=3>
					 <span class="red"> 定单使用1张优惠券 ，共优惠${returns.order.couponDiscount}元 , 本商品需扣减 ${returns.orderItem.couponDivide}元。</span>
					</td>
					[/#if]
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
						已发货数量
					</th>
					<th>
						小计
					</th>
				</tr>
				<tr>
					[#list returns.returnsItems as returnsItem]
        				[#if returnsItem??]
							<td>
								${returnsItem.sn}
							</td>
							<td>
								<a href="${toView}${returns.orderItem.product.path}" target="_blank">${returnsItem.name}</a>
							</td>
							<td>
								
							  
								${currency(returns.orderItem.price, true)} +${returns.orderItem.pricePoint}萌值
								
							</td>
							<td>
								${returnsItem.quantity}
							</td>
							<td>
								[#if returns.returnType=="directlyRefund"]
									0								
								[#else]
									${returnsItem.quantity}
								[/#if]
							</td>
							<td>
							<input type="hidden" id="payPrice" value="${returns.orderItem.payPrice}"/>
						    	  ${currency(returns.orderItem.payPrice, true)}+${returns.orderItem.payPricePoint}萌值
								
							</td>
						[/#if]
				    [/#list]
				</tr>
			</table>
		</div>
		<div class="audit">
			<p><span>处理</span></p>
			<div class="audit_desc"><p>审核意见:</p></div>
			<div class="audit_edit">
				<textarea class="textarea_input" id="auditDesc" rows="4" cols="25" onkeyup="check();"></textarea>
				<button id="approve">审核通过</button><button id="unapprove">审核拒绝</button>
			</div>
			
		</div>
	</div>
</body>
</html>

