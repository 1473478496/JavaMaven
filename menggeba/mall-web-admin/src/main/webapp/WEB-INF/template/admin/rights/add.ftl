<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.add")} - Powered By www.mgb.cn</title>
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
$().ready(function() {
	var $inputForm = $("#inputForm");
	var $browserButton = $("#browserButton");
	var $browserButton1 = $("#browserButton1");
	var $browserButton2 = $("#browserButton2");
	var $isHome = $("#isHome");
	var $isPopularity = $("#isPopularity");
	var $homeImage = $("#homeImage");
	var $popularityImage = $("#popularityImage");
	
	[@flash_message /]

	$browserButton.browser();
	$browserButton1.browser();
	$browserButton2.browser(); 
	$browserButton1.prop("disabled", true);
	$browserButton2.prop("disabled", true);
	
	//是否首页展示
	$isHome.click(function() {	  
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
	$isPopularity.click(function() {	  
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
	// 表单验证
	$inputForm.validate({
		rules: {
			rightsCategoryId: "required",
			brandId: "required",
			rightsTradeIds: "required",
			status: "required",
			name: "required",
			startDate: "required",
			endDate: "required",
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
			pricePoint: "digits",
			point: "digits"
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			},
			price: {
				pattern: "${message("admin.validate.price")}"			
			},
			marketPrice: {
				pattern: "${message("admin.validate.price")}"
			},
			rightsTradeIds: {
				required: "至少勾选一个"
			}
		},
		errorPlacement: function (error, element) { //指定错误信息位置
			if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
			var eid = element.attr('name'); //获取元素的name属性
			error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
			} else {
			error.insertAfter(element);
			}
		}
	});	
});

</script>
</head>
<body>
<div class="path">
	<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.add")}
</div>
<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="${message("admin.product.base")}" />
		</li>
		<li>
			<input type="button" value="权益详情" />
		</li>
		<!-- <li>
			<input type="button" value="使用商户详情" />
		</li> -->
	</ul>
	
	<table class="input tabContent">
	<!--权益基本信息-->
		<!--商品分类-->
		<tr>
			<th>
				<span class="requiredField">*</span>分类:
			</th>
			<td>
				<select id="rightsCategoryId" name="rightsCategoryId">
					[#list rightCategoryTree as rightCategory]
						<option value="${rightCategory.id}">
							${rightCategory.name}
						</option>
					[/#list]
				</select>
			</td>
		</tr>
		<!--商品名称-->
		<tr>
			<th>
				<span class="requiredField">*</span>${message("Product.name")}:
			</th>
			<td>
				<input type="text" name="name" class="text" maxlength="200" />
			</td>
		</tr>
		<tr>
				<th>
					<span class="requiredField">*</span>权益类型:
				</th>
				<td>
					<select id="type" name="rightsType">
						[#list types as type]
							<option value="${type}">${message("Rights.Type." + type)}</option>
						[/#list]
					</select>
				</td>
			</tr>
		<!--编号-->
		<tr>
			<th>
				</span>编号:
			</th>
			<td>
				<input type="text" name="sn" class="text" maxlength="100" title="${message("admin.product.snTitle")}" />
			</td>
		</tr>
		<!--是否置顶-->
		<tr>
			<th>
				 是否置顶:
			</th>
			<td>
				<input type="checkbox" id="isIntegral"  name="isTop" value="true" />是
				<input type="hidden" name="_isTop" value="false" />
			</td>
		</tr>
		<!--销售价-->
		<tr>
			<th>
				<span class="requiredField">*</span>销售价:
			</th>
			<td>
				<input type="text" id="price" name="price" class="text" maxlength="16" />
			</td>
		</tr>
		<!--所需积分-->
		<tr>
			<th>
				所需积分:
			</th>
			<td>
				<input type="text" name="pricePoint" class="text" maxlength="16" title="空留系统计为0"/>
			</td>
		</tr>
		<!--原价-->
		<tr>
			<th>
				<span class="requiredField">*</span>原价:
			</th>
			<td>
				<input type="text" name="marketPrice" class="text" maxlength="16" />					
			</td>
		</tr>	
		<!--库存-->
		<tr>
			<th>
				库存:
			</th>
			<td>
				<input type="text" name="stock" class="text" maxlength="16" title="空留系统计为0"/>
			</td>
		</tr>
		<!--有效期-->
		<tr>
			<th>
				<span class="requiredField">*</span>有效期:
			</th>
			<td>
				 <input type="text" id="beginDate" name="startDate" class="text Wdate"  onfocus="WdatePicker({minDate:'%y-%M-%d',maxDate: '#F{$dp.$D(\'endDate\')}'});" />
			          至<input type="text" id="endDate" name="endDate" class="text Wdate"  onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')||\'%y-%M-%d\'}'});" />
			</td>
		</tr>		
		<!--展示图片-->
		<tr>
			<th>
				${message("Product.image")}:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="logo" name="logoUrl" class="text" maxlength="200" title="${message("admin.product.imageTitle")}"/>
					<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				 自有:
			</th>
			<td>
				<input type="checkbox" id="isJicai"  name="isJicai" value="true" />是
				<input type="hidden" name="_isJicai" value="false" />
			</td>
		</tr>
		<!--品牌-->
		<tr>
			<th>
				<span class="requiredField">*</span>${message("Product.brand")}:
			</th>
			<td>
				<select name="brandId">
					<option value="">${message("admin.common.choose")}</option>
					[#list rightsBrands as rightsBrand]
						<option value="${rightsBrand.id}">
							${rightsBrand.name}
						</option>
					[/#list]
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="requiredField">*</span>商户:</th>
			<td>
				[#list rightsTrades as rightsTrade]
				<input type="checkbox" name="rightsTradeIds" value="${rightsTrade.id}" />${rightsTrade.name}
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
					<option value="0">未启用</option>
					<option value="1">启用</option>
				</select>
			</td>
		</tr>
		<!--是否首页展示-->
		<tr>
			<th>
				 首页展示:
			</th>
			<td>
				<input type="checkbox" id="isHome"  name="isHome" value="true" />是
				<input type="hidden" name="_isHome" value="false" />
			</td>
		</tr>
		<tr>
			<th>
				 首页展示图片:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="homeImage"style="background-color:#888" name="homeImage" class="text" maxlength="200" title="${message("admin.product.imageTitle")}"/>
					<input type="button" id="browserButton1" class="button" value="${message("admin.browser.select")}" />
				</span>
			</td>
		</tr>
		<!--是否人气商品-->
		<tr>
			<th>
				 人气展示:
			</th>
			<td>
				<input type="checkbox" id="isPopularity"  name="isPopularity" value="true" />是
				<input type="hidden" name="_isPopularity" value="false" />
			</td>
		</tr>
		<tr>
			<th>
				人气展示图片:
			</th>
			<td>
				<span class="fieldSet">
					<input type="text" id="popularityImage" style="background-color:#888"name="popularityImage" class="text"  maxlength="200" title="${message("admin.product.imageTitle")}"/>
					<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				备注:
			</th>
			<td>
				<input type="text" name="remark" class="text"  maxlength="20" />
			</td>
		</tr>
		
	</table>
	<!--权益详情-->
	<table class="input tabContent">
		<tr>
			<td>
				<textarea id="editor" name="introduction" class="editor" style="width: 100%;"></textarea>
			</td>
		</tr>
	</table>
	<!--使用商户信息-->
	<!-- <table class="input tabContent">
		<tr class="title">
				<th>
					添加商户信息:
				</th>
			</tr>
			<tr>
				<td>
					<div id="specificationSelect" class="specificationSelect">
						<ul>
							[#list rightsTrades as rightsTrade]
								<li>
									<label>
										<input type="checkbox" name="rightsTradeIds" value="${rightsTrade.id}" />${rightsTrade.name}
									</label>
								</li>
							[/#list]
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<a href="javascript:;" id="addSpecificationProduct" class="button">增加商户</a>
				</td>
			</tr>
			<tr>
				<td>
					<table id="specificationProductTable" class="input">
						<tr class="title">
						
							<td width="60">
								&nbsp;
							</td>
							
							<td>
								商户名称：
							</td>
							<td>
								商户编号：
							</td>
							<td>
								商户地址：
							</td>	
							<td>
								${message("admin.common.handle")}
							</td>
						</tr>
						[#list rightsTrades as rightsTrade]
						<tr class="hidden">
							<td>
								&nbsp;
							</td>
							<td>
								${rightsTrade.name}
							</td>
							<td>
								${rightsTrade.rlogin}
							</td>
							<td>
								${rightsTrade.addr}
							</td>	
							<td>
								<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
						[/#list]
					</table>
				</td>
			</tr>
	</table> -->
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