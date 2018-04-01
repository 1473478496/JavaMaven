<!doctype html>
<html>

<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible"
	content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
<meta content=always name=referrer>
<meta name="viewport" content="width=device-width" />
<title>${message("admin.mechant.view")}-PoweredBywww.mgb.cn</title>
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

$(document).ready(function(){

if ($.validator) {
    //fix: when several input elements shares the same name, but has different id-ies....
    $.validator.prototype.elements = function() {
        
    	var validator = this,
            rulesCache = {};

	    // select all valid inputs inside the form (no submit or reset buttons)

	    // workaround $Query([]).add until http://dev.jquery.com/ticket/2114 is solved

		return $([]).add(this.currentForm.elements)
        .filter(":input")
        .not(":submit, :reset, :image, [disabled]")
        .not(this.settings.ignore)
        .filter(function () {
            var elementIdentification = this.id || this.name;

			!elementIdentification && validator.settings.debug && window.console && console.error("%o has no id nor name assigned",this);
            // select only the first element for each name, and only those with rules specified

			if (elementIdentification in rulesCache || !validator.objectLength($(this).rules()))
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
	    rules: {
	      name: "required",
	      addr: "required",
	      imgfiles: "required",
	      apply: "required",
	      apply_tel: "required",
	      cert_no: "required",
	      group_no: "required",
	      email: "required"
	    },
	    
	    messages: {
	      name: "请输入商户名称",
	      addr: "请输入商户地址",
	      imgfiles: "请选择商户logo、组织机构营业证照片上传",
	      apply: "请输入联系人",
	      apply_tel: "请输入联系方式",
	      cert_no: "请输入证件号码",
	      group_no: "请输入组织机构代码编号",
	      email: "请输入邮箱"
	    }
	});
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
			url: "${base}/admin/tradeAudit/approve.jhtml",
			type: "POST",
			data: {id:${trade.id},auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/tradeAudit/list.jhtml";
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
			url: "${base}/admin/tradeAudit/unapprove.jhtml",
			type: "POST",
			data: {id: ${trade.id}, auditDesc: auditDesc},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/tradeAudit/list.jhtml";
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a>
		&raquo; 商户审核
	</div>
	<input type="hidden" name="id" value="${trade.id}" />
	<div class="content">
		<div class="baseInfo">
			<p>
				<span>基本信息:</span>
			</p>
			<table>
				<tr>
					<th>*中文名：</th>
					<td>${trade.name}</td>
					<th>*越南名：</th>
					<td>${trade.alias_Name}</td>
				</tr>
				<tr>
					<th>*证件类型：</th>
					<td>[#if trade.cert_type == "identityCard"] 身份证 [#elseif
						trade.cert_type == "passport"] 护照 [#else ] 军官证 [/#if]</td>
					<th>*证件号码：</th>
					<td>${trade.cert_no }</td>
				</tr>
				<tr>
					<th>*性别：</th>
					<td>[#if trade.sex == "0"] 男 [#else] 女 [/#if]</td>
					<td>*手机号码：</td>
					<td>${trade.mobile}</td>
				</tr>
				<tr>
					<th>生日：</th>
					<td>${trade.birthday}</td>
					<th>住址：</th>
					<td>${trade.addr}</td>
				</tr>
			</table>
		</div>
		<div class="signInfo">
			<p>
				<span>签约信息:</span>
			</p>
			<table>
				<tr>
					<th>*商铺号：</th>
					<td>${trade.no}</td>
					<th>*商户类型：</th>
					<td>
					[#list trade.tradeProducts as tradeProducts] [#if tradeProducts??]
					 ${ tradeProducts.product_category_id} &nbsp;
					 [/#if] [/#list]
					</td>
					<th>*协议起止时间：</th>
					<td>${trade.beg_date}--${trade.end_date}</td>
				</tr>
			</table>
		</div>
		<div class="tradeQualifications">
			<table>
				<p>
					<span>商家资质:</span>
				</p>
				<tr>
					<th>*营业执行与法人身份证：</th>
					<td><img src="${base}/${trade.cert_photo}"></td>
				<tr>
			</table>
		</div>
		<div class="proxyInfo">
			<p>
				<span>代理人信息:</span>
			</p>
			<table>
				<tr>
					<th>*代理人姓名</th>
					<th>*手机号码</th>
					<th>*证件类型</th>
					<th>*证件号码</th>
				</tr>
				[#list trade.tradeProxy as tradeProxy] [#if tradeProxy??]
				<tr>
					<td>${tradeProxy.name }</td>
					<td>${tradeProxy.mobile }</td>
					<td>[#if tradeProxy.cert_type == "identityCard"] 身份证 [#elseif
						tradeProxy.cert_type == "passport"] 护照 [#else ] 军官证 [/#if]</td>
					<td>${tradeProxy.cert_no }</td>
				</tr>
				 [/#if] [/#list]
			</table>
		</div>
		<div class="bankInfo">
			<p>
				<span>银行信息:</span>
			</p>
			<table>
				<tr>
					<th>*账户名称</th>
					<th>*银行卡卡号</th>
					<th>*开户银行</th>
				</tr>
				[#list trade.tradeBanks as tradeBanks] [#if tradeBanks??]
				<tr>
					
					<td>${tradeBanks.card_name }</td>
					<td>${tradeBanks.card_no }</td>
					<td>${tradeBanks.bank_name }</td>
				</tr>
				 [/#if] [/#list]
			</table>
		</div>
	</div>
	<div class="audit_view">
		<div class="audit">
			<p>
				<span>处理</span>
			</p>
			<br/>
			<div class="audit_desc">
				<p>审核意见:</p>
			</div>
			<div class="audit_edit">
				<textarea class="textarea_input" id="auditDesc" rows="4" cols="25"
					onkeyup="check();"></textarea>
				<button id="approve">审核通过</button>
				<button id="unapprove">审核拒绝</button>
				<br/>
				<br/>
			</div>
		</div>
	</div>
</body>
</html>