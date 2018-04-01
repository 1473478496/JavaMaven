<!doctype html>
<html>

<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible"
	content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
<meta content=always name=referrer>
<meta name="viewport" content="width=device-width" />
<title>${message("admin.border.view")}-PoweredBy www.mgb.cn</title>

<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript"
	src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript"
	src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						if ($.validator) {
							//fix: when several input elements shares the same name, but has different id-ies....
							$.validator.prototype.elements = function() {

								var validator = this, rulesCache = {};

								// select all valid inputs inside the form (no submit or reset buttons)

								// workaround $Query([]).add until http://dev.jquery.com/ticket/2114 is solved

								return $([])
										.add(this.currentForm.elements)
										.filter(":input")
										.not(
												":submit, :reset, :image, [disabled]")
										.not(this.settings.ignore)
										.filter(
												function() {
													var elementIdentification = this.id
															|| this.name;

													!elementIdentification
															&& validator.settings.debug
															&& window.console
															&& console
																	.error(
																			"%o has no id nor name assigned",
																			this);
													// select only the first element for each name, and only those with rules specified

													if (elementIdentification in rulesCache
															|| !validator
																	.objectLength($(
																			this)
																			.rules()))
														return false;

													rulesCache[elementIdentification] = true;
													return true;

												});
							};
						}
					});

	$().ready(function() {
		// 在键盘按下并释放及提交后验证提交表单
		$("#trade_save_Form").validate({
			rules : {
				name : "required",
				addr : "required",
				imgfiles : "required",
				apply : "required",
				apply_tel : "required",
				cert_no : "required",
				group_no : "required",
				email : "required"
			},

			messages : {
				name : "请输入商户名称",
				addr : "请输入商户地址",
				imgfiles : "请选择商户logo、组织机构营业证照片上传",
				apply : "请输入联系人",
				apply_tel : "请输入联系方式",
				cert_no : "请输入证件号码",
				group_no : "请输入组织机构代码编号",
				email : "请输入邮箱"
			}
		});
	});
</script>

</head>

<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a>
		&raquo; 边民审核
	</div>
	<input type="hidden" name="id" value="${borderMan.id}" />
	<div class="content">
		<div class="baseInfo">
			<p>
				<span>基本信息:</span>
			</p>
			<table>
				<tr>
					<th>*边民证号：</th>
					<td>${borderMan.bmNum}</td>
					<th>*真实名：</th>
					<td>${borderMan.trueName}</td>
				</tr>
				<tr>
					<th>*身份证号：</th>
					<td>${borderMan.idCardNum }</td>
					<th>*前往地区：</th>
					<td>${borderMan.goArea }</td>
				</tr>
				<tr>
					<th>*发证机关：</th>
					<td>${borderMan.issuedAgencies }</td>
					<td>*发证日期：</td>
					<td>${borderMan.issuedDate}</td>
				</tr>
				<tr>
					<th>边民证有效期开始：</th>
					<td>${borderMan.startDate}</td>
					<th>边民证有效期介绍：</th>
					<td>${borderMan.endDate}</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td>${borderMan.comment}</td>
					<th>边民属性值1：</th>
					<td>${borderMan.attributeValue0}</td>
				</tr>
				<tr>
					<th>边民属性值2：</th>
					<td>${borderMan.attributeValue1}</td>
					<th>边民属性值3：</th>
					<td>${borderMan.attributeValue2}</td>
				</tr>
			</table>
		</div>
		<div class="signInfo">
			<p>
				<span>审核信息:</span>
			</p>
			<table>
				<tr>
					<th>*审核状态：</th>
					<td>${borderMan.checkState}</td>
					<th>*审核日期：</th>
					<td>${borderMan.checkDate}</td>
					<th>*审核人：</th>
					<td>${borderMan.checkPerson}</td>
				</tr>
			</table>
		</div>
		<div class="tradeQualifications">
			<table>
				<p>
					<span>对应会员信息:</span>
				</p>
				<tr>
					<th>*会员基本信息：</th>
					<td>${borderMan.member}</td>
				<tr>
			</table>
		</div>
	</div>
	<div class="audit_view">
		<div class="audit">
			<p>
				<span>处理</span>
			</p>
			<br />
			<div class="audit_view_desc">
				<table>
					<tr>
						<th>审核意见:</th>
						<td>${borderMan.checkDescription}</td>
					</tr>
					<tr>
						<td></td>
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