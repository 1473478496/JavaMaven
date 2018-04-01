<!doctype html>
<html>

<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible"
	content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
<meta content=always name=referrer>
<meta name="viewport" content="width=device-width" />
<title>${message("admin.border.view")}-PoweredBywww.mgb.cn</title>
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
	    	trueName: "required",
	    	bmNum: "required",
	    	idCardNum: "required",
	    	goArea: "required",
	    	issuedAgencies: "required",
	    	issuedDate: "required"
	    },
	    
	    messages: {
	    	trueName: "请输入正确输入边民真实姓名",
	    	bmNum: "请输入正确输入边名证号",
	    	idCardNum: "请检查证件号是否正确",
	    	goArea: "请正确输入地区名",
	    	issuedAgencies: "请正确输入发证机关",
	    	issuedDate: "请正确输入发证日期"
	    }
	});
//限制审核意见长度
	$("#checkDescription").keyup(function(){
	   var len = $(this).val().length;
	   if(len > 119){
	    $(this).val($(this).val().substring(0,120));
	   }
	   var num = 120 - len;
	   $("#word").text(num);
	 });	
	//审核通过
	$("#approve").click(function() {
		var checkDescription = $("#checkDescription").val();
		$.ajax({
			url: "${base}/admin/border/approve.jhtml",
			type: "POST",
			data: {id:${borderMan.id},checkDescription: checkDescription},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/border/list.jhtml";
				}else{
					$.message(message);
				}
			}
		});
	});
	
	//审核拒绝
	$("#unapprove").click(function() {
		var checkDescription = $("#checkDescription").val();	
		$.ajax({
			url: "${base}/admin/border/unapprove.jhtml",
			type: "POST",
			data: {id: ${borderMan.id}, checkDescription: checkDescription},
			dataType: "json",
			cache: false,
			success: function(message) {
				if(message.type == "success"){
					$.message(message);
					window.location.href="${base}/admin/border/list.jhtml";
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
			<br/>
			<div class="audit_desc">
				<p>审核意见:</p>
			</div>
			<div class="audit_edit">
				<textarea class="textarea_input" id="checkDescription" rows="4" cols="25"
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