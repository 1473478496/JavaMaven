<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.edit")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $areaId = $("#areaId");
	
	[@flash_message /]
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
				pattern: /^[^\s&\"<>]+$/,
				minlength: ${setting.passwordMinLength},
				maxlength: ${setting.passwordMaxLength}
			},
			rePassword: {
				equalTo: "#password"
			},
			[#--
			email: {
				required: true,
				pattern: /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/,
				email: true
				[#if !setting.isDuplicateEmail]
					,remote: {
						url: "check_email.jhtml?previousEmail=${member.email?url}",
						cache: false
					}
				[/#if]
			},
			--]
			modifyPoint: {
				integer: true,
				min: -${member.point}
			},
			modifyBalance: {
				min: -${member.balance},
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}
			[#list memberAttributes as memberAttribute]
				[#if memberAttribute.isRequired]
					,memberAttribute_${memberAttribute.id}: {
						required: true
					}
				[/#if]
				[#if memberAttribute.type=="mobile"]
					,memberAttribute_${memberAttribute.id}: {
							required: function(){
									if(String(${memberAttribute.isRequired})=="true"){
										return true;
									}else{
										return false;
									}
								},
							pattern: /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
					}
				[/#if]
				[#if memberAttribute.type=="zipCode"]
					,memberAttribute_${memberAttribute.id}: {
							required: function(){
									if(String(${memberAttribute.isRequired})=="true"){
										return true;
									}else{
										return false;
									}
								},
							pattern: /^[1-9]\d{5}$/
					}
				[/#if]
				[#--
				[#if memberAttribute.type=="phone"]
					,memberAttribute_${memberAttribute.id}: {
							required: function(){
									if(String(${memberAttribute.isRequired})=="true"){
										return true;
									}else{
										return false;
									}
								},
							pattern: /^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$/
					}
				[/#if]
				--]
			[/#list]
		},
		messages: {
			password: {
				pattern: "${message("admin.validate.illegal")}"
			}
			[#if !setting.isDuplicateEmail]
				,email: {
					remote: "${message("admin.validate.exist")}"
				}
			[/#if]
			[#list memberAttributes as memberAttribute]
				[#if memberAttribute.type=="zipCode"]
					,memberAttribute_${memberAttribute.id}: {
							pattern: "邮编格式 深圳:518000"
					}
				[/#if]
				[#if memberAttribute.type=="phone"]
					,memberAttribute_${memberAttribute.id}: {
							pattern: "电话格式:0755-XXXXXXXX"
					}
				[/#if]
			[/#list]
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.member.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${member.id}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.member.base")}" />
			</li>
			[#if memberAttributes?has_content]
				<li>
					<input type="button" value="${message("admin.member.profile")}" />
				</li>
			[/#if]
			[#--
			<li>
				<input type="button" value="${message("admin.member.point")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.member.deposit")}" />
			</li>
			--]
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					${message("Member.username")}:
				</th>
				<td>
					${member.username}
				</td>
			</tr>
			[#--
			<tr>
				<th>
					${message("Member.password")}:
				</th>
				<td>
					<input type="password" id="password" name="password" class="text" maxlength="20" title="${message("admin.member.passwordTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.member.rePassword")}:
				</th>
				<td>
					<input type="password" name="rePassword" class="text" maxlength="20" />
				</td>
			</tr>
			--]
			<tr>
				<th>
					${message("Member.email")}:
				</th>
				<td>
					${member.email}
					<input type="hidden" name="email" class="text" value="${member.email}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Member.mobile")}:
				</th>
				<td>
					${member.mobile}
					<input type="hidden" name="mobile" class="text" value="${member.mobile}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Member.memberRank")}:
				</th>
				<td>				
					[#list memberRanks as memberRank]
						[#if memberRank == member.memberRank] 
							${memberRank.name}
							<input type="hidden" name="memberRankId" class="text" value="${memberRank.id}" maxlength="200" />
						[/#if]
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isEnabled" value="true"[#if member.isEnabled] checked="checked"[/#if] />${message("Member.isEnabled")}
						<input type="hidden" name="_isEnabled" value="false" />
					</label>
					[#if member.isLocked]
						<label>
							<input type="checkbox" name="isLocked" value="true" checked="checked" />${message("Member.isLocked")}
							<input type="hidden" name="_isLocked" value="false" />
						</label>
					[/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("Member.amount")}:
				</th>
				<td>
					${currency(member.amount, true)}
				</td>
			</tr>
			<tr>
				<th>
					${message("Member.pointBalance")}:
				</th>
				<td>
					[#if accBalList?exists]
	             	<span class="price">${accBalList.totalBal}</span>
	                [#else]
					<span class="price">0.0</span>
					[/#if]
				</td>
			</tr>
			<tr>
			<th>
				${message("admin.common.createDate")}:
			</th>
			<td>
				${member.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
		</tr>
		<tr>
			<th>
				${message("Member.loginDate")}:
			</th>
			<td>
				${(member.loginDate?string("yyyy-MM-dd HH:mm:ss"))!"-"}
			</td>
		</tr>
		<tr>
			<th>
				${message("Member.registerIp")}:
			</th>
			<td>
				${member.registerIp}
			</td>
		</tr>
		<tr>
			<th>
				${message("Member.loginIp")}:
			</th>
			<td>
				${(member.loginIp)!"-"}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.member.reviewCount")}:
			</th>
			<td>
				${member.reviews?size}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.member.consultationCount")}:
			</th>
			<td>
				${member.consultations?size}
			</td>
		</tr>
		<tr>
			<th>
				${message("admin.member.favoriteProductCount")}:
			</th>
			<td>
				${member.favoriteProducts?size}
			</td>
		</tr>
		</table>
		[#if memberAttributes?has_content]
			<table class="input tabContent">
				[#list memberAttributes as memberAttribute]
					<tr>
						<th>
							[#if memberAttribute.isRequired]<span class="requiredField">*</span>[/#if]${memberAttribute.name}:
						</th>
						<td>
							[#if memberAttribute.type == "name"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.name}" maxlength="200" />
							[#elseif memberAttribute.type == "gender"]
								<span class="fieldSet">
									[#list genders as gender]
										<label>
											<input type="radio" name="memberAttribute_${memberAttribute.id}" value="${gender}"[#if gender == member.gender] checked="checked"[/#if] />${message("Member.Gender." + gender)}
										</label>
									[/#list]
								</span>
							[#elseif memberAttribute.type == "birth"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text Wdate" value="${member.birth}" onfocus="WdatePicker();" />
							[#elseif memberAttribute.type == "area"]
								<span class="fieldSet">
									<input type="hidden" id="areaId" name="memberAttribute_${memberAttribute.id}" value="${(member.area.id)!}" treePath="${(member.area.treePath)!}" />
								</span>
							[#elseif memberAttribute.type == "address"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.address}" maxlength="200" />
							[#elseif memberAttribute.type == "zipCode"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.zipCode}" maxlength="6" />
							[#elseif memberAttribute.type == "phone"]
								${member.phone}
								<input type="hidden" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.phone}" maxlength="13" />
							[#elseif memberAttribute.type == "mobile"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.mobile}" maxlength="11" />
							[#elseif memberAttribute.type == "hobby"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.hobby}" maxlength="200" />
							[#elseif memberAttribute.type == "text"]
								<input type="text" name="memberAttribute_${memberAttribute.id}" class="text" value="${member.getAttributeValue(memberAttribute)}" maxlength="200" />
							[#elseif memberAttribute.type == "select"]
								<select name="memberAttribute_${memberAttribute.id}">
									<option value="">${message("admin.common.choose")}</option>
									[#list memberAttribute.options as option]
										<option value="${option}"[#if option == member.getAttributeValue(memberAttribute)] selected="selected"[/#if]>
											${option}
										</option>
									[/#list]
								</select>
							[#elseif memberAttribute.type == "checkbox"]
								<span class="fieldSet">
									[#list memberAttribute.options as option]
										<label>
											<input type="checkbox" name="memberAttribute_${memberAttribute.id}" value="${option}"[#if (member.getAttributeValue(memberAttribute)?seq_contains(option))!] checked="checked"[/#if] />${option}
										</label>
									[/#list]
								</span>
							[/#if]
						</td>
					</tr>
				[/#list]
			</table>
		[/#if]
		<table class="input tabContent">
			<tr>
				<th>
					${message("admin.member.currentPoint")}:
				</th>
				<td>
					${member.point}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.member.modifyPoint")}:
				</th>
				<td>
					<input type="text" name="modifyPoint" class="text" maxlength="9" placeholder="${message("admin.member.modifyPointTitle")}" />
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<th>
					${message("admin.member.currentBalance")}:
				</th>
				<td>
					${currency(member.balance, true)}
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.member.modifyBalance")}:
				</th>
				<td>
					<input type="text" name="modifyBalance" class="text" maxlength="16" placeholder="${message("admin.member.modifyBalanceTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.member.depositMemo")}:
				</th>
				<td>
					<input type="text" name="depositMemo" class="text" maxlength="200" />
				</td>
			</tr>
		</table>
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