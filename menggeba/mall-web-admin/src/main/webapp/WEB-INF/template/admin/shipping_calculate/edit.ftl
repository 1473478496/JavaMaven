<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>运费编辑 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $areaId = $("#areaId");
	var $areaId2 = $("#areaId2");	
	[@flash_message /]
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	$areaId2.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	//限制备注长度
	 $("#memo").keyup(function(){
	  var len = $(this).val().length;
	   if(len > 199){
	   		$.message("warn", "备注信息不能超过200个字！");
	   		$(this).val($(this).val().substring(0,200));
	   }
	 });
	// 表单验证
	$inputForm.validate({
		rules: {
			shippingMethodId: "required",
			areaType: "required",
			areaId2: "required",
			firstWeight: {
				required: true,
				min: 0,
				integer: 12
			},
			firstPrice: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			nextHalfWeight: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			nextWeight: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}
		},
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo;运费编辑 
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
	<input type="hidden" name="id" value="${shippingCalculate.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>:配送方式
				</th>
				<td>
                	<select name="shippingMethodId" id="shippingMethodId" style="width:150px">
                		[#list shippingMethods as shippingMethod ]
                		    [#if  shippingMethod.id==shippingCalculate.shippingMethod.id]
                				<option value=${shippingMethod.id} selected = "selected">${shippingMethod.name}</option>
                			[#else]
                				<option value=${shippingMethod.id}>${shippingMethod.name}</option>
                			[/#if]
                		[/#list]
                	</select>
                </td>
			</tr>
			<tr>
				<th>
					始发地:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId" value="${(shippingCalculate.shippingArea.id)!}" treePath="${(shippingCalculate.shippingArea.treePath)!}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>类型:
				</th>
				<td>
                    <select id="areaType" name="areaType" value="selected">
 					 	<option value="sameCity"[#if shippingCalculate.areaType=="sameCity"]selected = "selected"[/#if]>同城</option>
 					 	<option value="sameProvince"[#if shippingCalculate.areaType=="sameProvince"]selected = "selected"[/#if]>省内</option>
 					 	<option value="transProvince"[#if shippingCalculate.areaType=="transProvince"]selected = "selected"[/#if]>省外</option>
  					 </select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>目的地:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="areaId2" name="areaId2" value="${(shippingCalculate.consigneeArea.id)!}" treePath="${(shippingCalculate.consigneeArea.treePath)!}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>首重(kg):
				</th>
				<td>
					 <input type="text" id="firstWeight" name="firstWeight"class="text" value="${shippingCalculate.firstWeight}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>首重费用(元):
				</th>
				<td>
					 <input type="text" id="firstPrice" name="firstPrice"class="text" value="${shippingCalculate.firstPrice}" />
				</td>
			</tr>
			<tr>
				<th>
				  <span class="requiredField">*</span>  半公斤续重费用(元):
			      </th>
				<td>
					 <input type="text" id="nextHalfWeight" name="nextHalfWeight"class="text" value="${shippingCalculate.nextHalfWeight}"/>
				</td>
			</tr>
			<tr>
				<th>
				  <span class="requiredField">*</span>  一公斤续重费用(元)：
				</th>
				<td>
					 <input type="text" id="nextWeight" name="nextWeight"class="text" value="${shippingCalculate.nextWeight}"/>
				</td>
			</tr>
			<tr>
				<th>
				  备注：
				</th>
				<td>
				 <textarea class="textarea_input" id="memo" name="memo"value="${(shippingCalculate.memo)!}" rows="4" cols="25"/></textarea>
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