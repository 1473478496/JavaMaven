<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑权益商品 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $type = $("#type");
	var $logo = $("#logo");
	var $isHome = $("#isHome");
	var $isPopularity = $("#isPopularity");
	var $homeImage = $("#homeImage");
	var $popularityImage = $("#popularityImage");
	var $browserButton = $("#browserButton");
	var $browserButton1 = $("#browserButton1");
	var $browserButton2 = $("#browserButton2");
	[@flash_message /]
	
	$browserButton.browser();
 	$browserButton1.browser();
 	$browserButton2.browser();
 	
	
	//是否首页展示
	$isHome.change(function() {	  
		if ($(this).prop("checked")) { 
			$homeImage.prop("readonly", false);
			$browserButton1.prop("disabled", false);
			$homeImage.css({"background-color":""});		
		} else {
			$homeImage.prop("readonly", true);
			$browserButton1.prop("disabled", true);
			$homeImage.css({"background-color":"#888"});
		}
	});
	//是否人气商品
	$isPopularity.change(function() {	  
		if ($(this).prop("checked")) { 
			$popularityImage.prop("readonly", false);
			$browserButton2.prop("disabled", false);
			$popularityImage.css({"background-color":""});		
		} else {
			$popularityImage.prop("readonly", true);
			$browserButton2.prop("disabled", true);
			$popularityImage.css({"background-color":"#888"});
		}
	});
	$("#hrefhome").click(function(){
		open($homeImage.val());
	});
	$("#hrefpopularity").click(function(){
		open($popularityImage.val());
	});
	
	$("#hreflogo").click(function(){
		open($logo.val());
	});
	
		// 表单验证
	$inputForm.validate({
		rules: {
			rightsCategoryId: "required",
			brandId: "required",
			status: "required",
			name: "required",
			pricePoint: "required",
			startDate: "required",
			endDate: "required",
			stock: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.jhtml",
					cache: false
				}
			},
			price: {
				required: true,
				pattern:/^\d+(\.\d{2})?$/,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			
			marketPrice: {
				required: true,
				pattern:/^\d+(\.\d{2})?$/,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			weight: "digits",
			stock: "digits",
			point: "digits"
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			}
		}
		
	});
	
});
	
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑权益
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${rights.id}" />
		<table class="input">
		<!--商品分类-->
		<tr>
			<th>
				<span class="requiredField">*</span>分类:
			</th>
			<td>
				<select id="rightsCategoryId" name="rightsCategoryId" >
					[#list rightCategoryTree as rightCategory]
						<option value="${rightCategory.id}"[#if rightCategory == rights.rightsCategory] selected="selected"[/#if]>
							${rightCategory.name}
						</option>
					[/#list]
				</select>
			</td>
		</tr>
		<!--名称-->
		<tr>
			<th>
				<span class="requiredField">*</span>${message("Brand.name")}:
			</th>
			<td>
				<input type="text" name="name" class="text" value="${rights.name}" maxlength="200" />
			</td>
		</tr>
		<tr>
				<th>
					<span class="requiredField">*</span>权益类型:
				</th>
				<td>
					<select id="type" name="rightsType">
						[#list types as type]
							<option value="${type}"[#if type == rights.rightsType] selected="selected"[/#if]>${message("Rights.Type." + type)}</option>
						[/#list]
					</select>
				</td>
			</tr>
		<!--是否置顶-->
		<tr>
			<th>
				<span class="requiredField">*</span> 是否置顶:
			</th>
			<td>
				<input type="checkbox" id="isIntegral" name="isTop" value="true"[#if rights.isTop] checked="checked"[/#if] />是
				<input type="hidden" name="_isTop" value="false" />
			</td>
		</tr>
		<!--logo-->
		<tr>
			<th>
				${message("Brand.logo")}:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="logo" name="logoUrl" class="text" value="${rights.logoUrl}" maxlength="200" />
					<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
					<a href="javascript:;" id="hreflogo">${message("admin.common.view")}</a>
				</span>
			</td>
		</tr>
		<tr>
			<th>
				 首页展示:
			</th>
			<td>
				<input type="checkbox" id="isHome"  name="isHome" value="true" [#if rights.isHome] checked="checked"[/#if]/>是
				<input type="hidden" name="_isHome" value="false" />
			</td>
		</tr>
		<tr>
			<th>
				 首页展示图片:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="homeImage" name="homeImage"value="${rights.homeImage}" class="text" maxlength="200" title="${message("admin.product.imageTitle")}"[#if rights.isHome==false] style="background-color:#888" [/#if]/>
					<input type="button" id="browserButton1" class="button" value="${message("admin.browser.select")}" [#if rights.isHome==false] disabled="disabled"[/#if]/>
					<a href="javascript:;" id="hrefhome">${message("admin.common.view")}</a>
				</span>
			</td>
		</tr>
		<tr>
			<th>
				 人气展示:
			</th>
			<td>
				<input type="checkbox" id="isPopularity"  name="isPopularity" value="true" [#if rights.isPopularity] checked="checked"[/#if]/>是
				<input type="hidden" name="_isPopularity" value="false" />
			</td>
		</tr>
		<tr>
			<th>
				人气展示图片:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="popularityImage" name="popularityImage" class="text" value="${rights.popularityImage}"maxlength="200" title="${message("admin.product.imageTitle")}"[#if rights.isPopularity==false] style="background-color:#888" [/#if]/>
					<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" [#if rights.isPopularity==false] style="background-color:#888" disabled="disabled"[/#if]/>
					<a href="javascript:;" id="hrefpopularity">${message("admin.common.view")}</a>
				</span>
			</td>
		</tr>
		<tr>	
			<th>
				<span class="requiredField">*</span>有效期:
			</th>
			<td>
				 <input type="text" id="beginDate" name="startDate" class="text Wdate" value="${rights.startDate}" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});" />
			          至 <input type="text" id="endDate" name="endDate" class="text Wdate" value="${rights.endDate}" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'});" />
			</td>
		</tr>
		<tr>
			<th>
				销售价:
			</th>
			<td>
				<input type="text" name="price" class="text" value="${rights.price}" maxlength="200" />
			</td>
		</tr>
		<tr>
			<th>
				原价:
			</th>
			<td>
				<input type="text" name="marketPrice" class="text" value="${rights.marketPrice}" maxlength="9" />
			</td>
		</tr>
		<tr>
			<th>
				所需积分:
			</th>
			<td>
				<input type="text" name="pricePoint" class="text" value="${rights.pricePoint}" maxlength="9" />
			</td>
		</tr>
		<tr>
			<th>
				库存:
			</th>
			<td>
				<input type="text" name="stock" class="text" value="${rights.stock}" maxlength="9" />
			</td>
		</tr>
		<!--商品品牌-->
		<tr>
			<th>
				<span class="requiredField">*</span>品牌:
			</th>
			<td>
				<select id="rightsBrandId" name="rightsBrandId" >
					[#list rightsBrands as rightBrand]
						<option value="${rightBrand.id}"[#if rightBrand == rights.rightsBrand] selected="selected"[/#if]>
							${rightBrand.name}
						</option>
					[/#list]
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="requiredField">*</span>商户:</th>
			<td>
				[#list rightsTrades as rightsTrade]
				<input type="checkbox" name="rightsTradeIds" value="${rightsTrade.id}" 
					[#list rights.rightsTrades as trades]
					[#if trades??]
					[#if rightsTrade == trades]checked="checked"[/#if] 
					[/#if]
					[/#list]
				/>${rightsTrade.name}
				[/#list]
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>状态:
			</th>
			<td>
				<select id="status" name="status" >
					<option value="">${message("admin.common.choose")}</option>
					<option value="0" [#if rights.status == false] selected="selected"[/#if]>
						未启用
					</option>
					<option value="1" [#if rights.status == true] selected="selected"[/#if]>
						启用
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				备注:
			</th>
			<td>
				<input type="text" name="remark" class="text" value="${rights.remark}" maxlength="9" />
			</td>
		</tr>
		<tr>
			<th>
				${message("Brand.introduction")}:
			</th>
			<td>
				<textarea id="editor" name="introduction" class="editor">${rights.introduction?html}</textarea>
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