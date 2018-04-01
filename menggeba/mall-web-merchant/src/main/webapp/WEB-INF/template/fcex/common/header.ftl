<script type="text/javascript" src="${base }/resources/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${base }/resources/js/cookie.js"></script>
<script type="text/javascript" charset="utf-8">  
	$(function(){	
	 	var $headerLogin = $("#headerLogin");
		var $headerRegister = $("#headerRegister");
		var $headerUsername = $("#headerUsername");
		var $headerLogout = $("#headerLogout");
		
		var username = getCookie("username");
		if (username != null) {
			// 已登陆
			$headerUsername.text(decodeURIComponent(username)).show();
			$headerLogout.show();
			$headerLogin.hide();
			$headerRegister.hide();
			//$("#headerWelcome").hide();
		} else {
			// 未登陆
			$headerUsername.text(username).hide();
			$headerLogout.hide();
			$headerLogin.show();
			$headerRegister.show();
			//$("#headerWelcome").show();
		}
	
	});
			
</script>
	<!--begin 公用css-头部-->
   <div class="site-topbar">
    <div class="container-navfo">
      <div class="topbar-info">
        <span id="headerWelcome">你好,欢迎来到众慧商城!</span>
        <a href="${base }/login/trade.do" id="headerLogin" class="color-nt">[请登录]</a>
        <a href="#" id="headerUsername" style="display:none" class="color-nt">XXX</a>
        <a href="#" id="headerRegister">[商家入驻]</a>
        <a href="${base}/logout/trade.do" id="headerLogout" style="display:none">安全退出</a>
      </div>
      <div class="topbar-nav">
        <a href="#">我的商城</a>
        <a href="${base}/orderItem/list.do">我的订单</a>
        <a href="#">关注微信</a>
        <a href="#">关于商家</a>
      </div>
    </div>
  </div>
  <div class="site-header">
    <div class="container-header">
      <div class="header-logo">
        <h1>
          <a href="#" class="logo-png"><img src="${base}/resources/images/zhonghui_logo.png" alt=""></a>
          <span class="v-line"></span>
          <span>商家中心</span>
        </h1>
        <p>联系电话：4000555706</p>
      </div>
    </div>
  </div>