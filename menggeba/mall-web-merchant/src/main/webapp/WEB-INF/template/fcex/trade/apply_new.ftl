<!doctype html>
<html>

<head>
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>商户入驻</title>	
	
	<link type="text/css" rel="stylesheet" href="${base}/resources/css/style.css"></link>

    <script type="text/javascript" src="${base }/resources/js/jquery-1.8.3.min.js"></script>
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
	  
  <div class="bander-pic">
    <img src="${base}/resources/images/shop_apply_banner01.jpg" alt="">
  </div>
  <div class="wrap">
    <div class="cooperation-pro">
      <div class="process-info">
        <h2>合作入驻流程</h2>
        <div class="cantainer-pic"><img src="${base}/resources/images/process-pic.png" alt=""></div>
      </div>
      <div class="cop-info">
        <div class="info-sub">
          <h3>提交合作信息</h3>
          <span>|</span>
          <em>我们的电话:0755-25884663</em>
        </div>
        <div class="sub-form">
          <form id="trade_save_Form" action="${base }/trade/apply_save.do" method="post" enctype="multipart/form-data">
            <label><span>*</span>商户名称 : </label><input type="text" class="company-name" id="name" name="name"/></br>
            <label><span>*</span>商户地址 : </label><input type="text" class="company-name" id="addr" name="addr"></br>
            <label><span>*</span>联系人 :   </label><input type="text" class="company-name" id="apply" name="apply"/></br>
            <label><span>*</span>联系方式 : </label><input type="text" class="contacts-m" id="apply_tel" name="apply_tel"/></br>
            <label><span>*</span>证件号码 :     </label><input type="text" class="tel-num" id="cert_no" name="cert_no"/></br>
            <label><span>*</span>组织机构代码编号 :   </label><input type="text" class="email-add" id="group_no" name="group_no"/></br>
            <label><span>*</span>邮箱 :     </label><input type="text" class="tel-num" id="email" name="email"/></br>
            <label><span>*</span>销售商品说明 :     </label><input type="text" class="tel-num" id="product_desc" name="product_desc"/></br>
            <label><span>*</span>备注 :     </label><input type="text" class="tel-num" id="remark" name="remark"/></br>
            
            
            <label><span>*</span>商户logo :</label>  
            <label class="upload-pre" for="logo_url"></label>
            <input type="file" name="imgfiles" id="logo_url" class="upload-img"/>

            <label><span>*</span>组织机构营业证照片 : </label>  
            <label class="upload-pre" for="group_photo"></label>
            <input type="file" name="imgfiles" id="group_photo" class="upload-img"/></br>
            <input type="submit" class="apply-all"/>
          </form>
        </div>
      </div>
    </div>
  </div>
	  
    <!-- 底部 -->
	[#include "/common/footer.ftl"/]
</body>
</html>