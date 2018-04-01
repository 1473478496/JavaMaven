<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.order.changeAudite")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>

<script type="text/javascript">
$().ready(function() {
	[@flash_message /]
	auditDesc: "required",
	 //限制审核意见长度
	 $("#auditDesc").keyup(function(){
	  var len = $(this).val().length;
	   if(len > 199){
	   		$.message("warn", "审核意见不能超过200个字！");
	   		$(this).val($(this).val().substring(0,200));
	   }
	 });
	 
	 //限制备注长度
	 $("#memo").keyup(function(){
	  var len = $(this).val().length;
	   if(len > 199){
	   		$.message("warn", "备注信息不能超过200个字！");
	   		$(this).val($(this).val().substring(0,200));
	   }
	 });
});
	
	//审核操作
	function btnClick(value){
		var memo = $("#memo").val();
		var auditDesc = $("#auditDesc").val();
		var id=$("#id").val();
		
		if($.trim(auditDesc) == ""){
			$.message("warn", "审核意见不能为空！");
			return false;
		}
		
		$.ajax({
			url: "${base}/admin/changes/audite.jhtml",
			type: "POST",
			data: {id: id,
				   memo: memo,
				   value:value,
				   auditDesc:auditDesc
			 },
			dataType: "json",
			cache: false,
			success: function(message) {
				$.message(message);
				if('success'==message.type){
					window.location.href='${base}/admin/changes/list.jhtml';
				}
			}
		});
	}
</script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 换货处理
	</div>
	<input type="hidden" id="id" value="${changes.id}"/>
 	<div class="content">
		<div class="returnsInfo">
			<p><span>换货信息:</span></p>
		 	<table>
				<tr>
					<th>
						订单号:
					</th>
					<td>
						<a href="../order/view.jhtml?id=${changes.orders.id}">${changes.orders.sn}</a>
					</td>
					<th>
					
					</th>
					<td>
						
					</td>
				</tr>
				<tr>
					<th>
						理由:
					</th>
					<td>
						${changes.changeCause}
					</td>
					<th>
						问题描述:
					</th>
					<td>
						${changes.changeDesc}
					</td>
				</tr>
				<tr>
					<th>
						凭证:
					</th>
					<td>
					[#if changes.changeVoucher?has_content]
						<a href="${changes.changeVoucher}"  target="_blank">点击查看</a>
					[#else]
					        点击查看(没有上传凭证)
					[/#if]
					</td>
					<th>
						
					</th>
					<td>
						
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
				[#if changes.orderItem??]
				<tr>
					<td>
						${changes.orderItem.sn}
					</td>
					<td>
						<a href="${toView}${changes.orderItem.product.path}" target="_blank">${changes.orderItem.name}</a>
					</td>
					<td>
						${changes.orderItem.price}
					</td>
					<td>
						${changes.orderItem.quantity}
					</td>
					<td>
						${changes.orderItem.shippedQuantity}
					</td>
					<td>
						${changes.orderItem.subtotal}
					</td>
				</tr>
				[/#if]
			</table>
		</div>
		<div class="audit">
			<p><span>处理</span></p>
			<div class="audit_desc"><p>审核意见:</p><div class="audit_desc"><p>备注 :</p></div></div>
			<div class="audit_edit">
				<textarea class="textarea_input" id="auditDesc" name="auditDesc" rows="4" cols="25"/></textarea>
				<br/>
				<br/>
				<textarea class="textarea_input" id="memo" rows="4" cols="25"></textarea>
				<button id="approve" onclick="btnClick(true);">审核通过</button>
				<button id="unapprove" onclick="btnClick(false);">审核拒绝</button>
			</div>
	  </div>
</body>
</html>

