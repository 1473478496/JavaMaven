<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.memberRank.edit")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $amount = $("#amount");
	var $isSpecial = $("#isSpecial");
	
	[@flash_message /]
	
	// 特殊会员等级修改
	$isSpecial.click(function() {
		if ($(this).prop("checked")) {
			$amount.val("").prop("disabled", true);
		} else {
			$amount.prop("disabled", false);
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: {
				required: true,
				remote: {
					url: "check_name.jhtml?previousName=${memberRank.name?url}",
					cache: false
				}
			},
			amount: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				},
				remote: {
					url: "check_amount.jhtml?previousAmount=${memberRank.amount}",
					cache: false
				}
			},
			
			priceConcessions: "digits",		
			priceConcessions: {
				required: true,
				digits: true,
				max:100,
				decimal: {
					 integer: 12,
					fraction: ${setting.priceScale}		
				}
			}
			
		},
		messages: {
			name: {
				remote: "${message("admin.validate.exist")}"
			},
			amount: {
				remote: "${message("admin.validate.exist")}"
			}
			
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.memberRank.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${memberRank.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("MemberRank.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${memberRank.name}" maxlength="100" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>优惠比例:
				</th>
				<td>
					<input type="text" id="priceConcessions" name="priceConcessions" class="text" value="${priceConcessions}" maxlength="16" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("MemberRank.amount")}:
				</th>
				<td>
					<input type="text" id="amount" name="amount" class="text" value="${memberRank.amount}" maxlength="16"[#if memberRank.isSpecial] disabled="disabled"[/#if] />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isDefault" value="true"[#if memberRank.isDefault] checked="checked" disabled="disabled"[/#if] />${message("MemberRank.isDefault")}
						<input type="hidden" name="_isDefault" value="false" />
					</label>
					<label title="${message("admin.memberRank.isSpecialTitle")}">
						 [#--是否特殊默认为否
						 	<input type="checkbox" id="isSpecial" name="isSpecial" value="true"[#if memberRank.isSpecial] checked="checked"[/#if] />${message("MemberRank.isSpecial")}
						 --]
						<input type="hidden" name="_isSpecial" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					生日特权:
				</th>
				<td>
					<label>
						<input type="checkbox" name="birthdayPrivilege" value="1"[#if birthdayPrivilege?contains('1')] checked="checked"[/#if] />站内信
						<input type="hidden" name="birthdayPrivilege" value="0" />
					</label>
					<label>
						<input type="checkbox" name="birthdayPrivilege" value="2"[#if birthdayPrivilege?contains('2')] checked="checked"[/#if] />短信
						<input type="hidden" name="birthdayPrivilege" value="0" />
					</label>
					<label>
						<input type="checkbox" name="birthdayPrivilege" value="3"[#if birthdayPrivilege?contains('3')] checked="checked"[/#if] />邮件
						<input type="hidden" name="birthdayPrivilege" value="0" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					专属客服:
				</th>
				<td>
					<label>
						<input type="checkbox" name="customerService" value="1"[#if customerService == '1'] checked="checked"[/#if] />
						<input type="hidden" name="customerService" value="0" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					新品试用:
				</th>
				<td>
					<label>
						<input type="checkbox" name="newTrialArea" value="1"[#if newTrialArea == '1'] checked="checked"[/#if] />
						<input type="hidden" name="newTrialArea" value="0" />
					</label>
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