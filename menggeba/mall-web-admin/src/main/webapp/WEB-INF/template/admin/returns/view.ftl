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
	
	//审核通过
	$("#approve").click(function() {
		var auditDesc = $("#auditDesc").val();
		
		$.ajax({
			url: "${base}/admin/returns/approve.jhtml",
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
						退款商品金额:
					</th>
					<td>
						[#list returns.returnsItems as returnsItem]
	        				[#if returnsItem??]
	        					${returns.orderItem.price*returnsItem.quantity}
							[/#if]
					    [/#list]
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
						问题描述:
					</th>
					<td>
						${returns.returnDesc}
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
						退款运费:
					</th>
					<td>
						[#list returns.returnsItems as returnsItem]
	        				[#if returnsItem??]
	        					${returnsItem.price - returns.orderItem.price*returnsItem.quantity}
							[/#if]
					    [/#list]
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
								${returns.orderItem.price}
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
								${returns.orderItem.price*returnsItem.quantity}
							</td>
						[/#if]
				    [/#list]
				</tr>
			</table>
		</div>
	</div>
	<div class="audit_view">
	  <div class="audit">
			<p><span>处理</span></p>
			<br/>
		   <div class="audit_view_desc">
			<table>
				<tr>
					<th>
						审核意见:
					</th>
					<td>
						${returns.auditDesc}
					</td>
				</tr>
				<tr>
				<td>
				</td>
				<td>
					<button onclick="javascript:history.back()">${message("admin.common.back")}</button>
				</td>
				</tr>
			</table>
		</div>	
	 </div>
	</div>
</body>
</html>