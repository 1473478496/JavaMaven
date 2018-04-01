<!doctype html>
<html>

<head>
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>商户信息</title>	
	
	<link type="text/css" rel="stylesheet" href="${base}/resources/css/style.css"></link>

    <script type="text/javascript" src="${base }/resources/js/jquery.js"></script>
    <script type="text/javascript" src="${base }/resources/js/jquery.validate.js"></script>

<script>

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
});
</script>

</head>

<body>
	<!-- 头部 -->
	[#include "/common/header.ftl"/]
	  
    <div>

		<table>
			<tr>
					<td width="300">*商户名称：</td>
					<td>${trade.name}</td>
			</tr>
			<tr>
					<td>*商户地址：</td>
					<td>${trade.addr}</td>
			</tr>
			<tr>
					<td>*商户logo：</td>
					<td><img src="${base}/${trade.logo_url}"></td>
			</tr>
			<tr>
					<td>*联系人：</td>
					<td>${trade.apply}</td>
			</tr>
			<tr>
					<td>*联系方式： </td>
					<td>${trade.apply_tel}</td>
			</tr>
			<tr>
					<td>*证件号码：</td>
					<td>${trade.cert_no}</td>
			</tr>
			<tr>
					<td>*组织机构代码编号：</td>
					<td>${trade.group_no}</td>
			</tr>
			<tr>
					<td>*组织机构营业证照片：</td>
					<td><img src="${base}/${trade.group_photo}"></td>
			</tr>
			<tr>
					<td>*邮箱：</td>
					<td>${trade.email}</td>
			</tr>
			<tr>
					<td>备注：</td>
					<td>${trade.remark}</td>
			</tr>
		</table>
	</div>
	  
    <!-- 底部 -->
	[#include "/common/footer.ftl"/]
</body>
</html>