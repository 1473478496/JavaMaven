<!DOCTYPE html>
<html>
	<head>

	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>用户登录</title>
	<link type="text/css" rel="stylesheet" href="${base}/resources/css/style.css"></link>
	<script type="text/javascript" src="${base}/resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/js/index.js"></script>
    <script type="text/javascript" src="${base}/resources/js/cookie.js"></script>
    
		<script type="text/javascript">
		$().ready(function() {
		    var $dialogOverlay = $("#dialogOverlay");
			var $loginForm = $("#loginForm");
			var $username = $("#username");
			var $password = $("#password");
			var $captchaImage = $("#captchaImage");
			var $isRememberUsername = $("#isRememberUsername");
			var $submit = $(":submit");
			
			// 记住用户名
			if (getCookie("memberUsername") != null) {
				$isRememberUsername.prop("checked", true);
				$username.val(getCookie("memberUsername"));
				$password.focus();
			} else {
				$isRememberUsername.prop("checked", false);
				$username.focus();
			} 
			
			// 表单验证、记住用户名
			$loginForm.validate({
				errorClass: "error",
                errorPlacement: function(error, element) {
                    $( element ).closest( ".field" ).after( error );
                },
                errorElement: "p",
				rules: {
					username: {
						 required: true,						 
					},
					password: "required"
				},
				submitHandler: function(form) {											
						$.ajax({
							url: $loginForm.attr("action"),
							type: "POST",
							data: $loginForm.serialize(),
							dataType: "json",
							cache: false,
							success: function(message) {									
								$submit.prop("disabled", false);
								if (message.type == "success") {
									[#if redirectUrl??]
										location.href = "${redirectUrl}";
									[#else]
										location.href = "${base}/";
									[/#if]
								} else {
									$(".login_error").text(message.content);
									$(".login_error").show();
									$("#loginTitle").hide();
								}
							}
						});
					}
				});				
		});
		
		</script>
	</head>
	<body class="login_body">
        <div class="header-main wrap">
            <div class="header-logo"><a href="${base }"><img src="${base }/resources/images/zhonghui_logo.png"></a></div>
        </div>
        <div class="login_bg">
            <div class="wrap pos_re">
                <div class="login-box-warp">
                    <div class="login-main">
                        <h3 class="login_tit">
                            <span class="fl login_name">账户登录</span>                            
                        </h3>                        
                        <form id="loginForm" action="${base}/login/submit.do" method="post">
                            <div class="errorbox">
                                <p class="login_tip login_error" style="display: none;">账号和密码不匹配，请重新输入</p>
                                <p class="login_tip" id="loginTitle">公共场所请勿记住密码</p>
                            </div>
                            <div class="field username-field">
                                <label class="log_label" for="username"><i class="log_iconame"></i></label>
                                <input type="text" name="username" id="username" class="login-text" placeholder="请输入手机号" >
                            </div>
                            <div class="field pw-field">
                                <label class="log_label" for="password"><i class="log_iconame"></i></label>
                                <input type="password" name="password" id="password" class="login-text" placeholder="请输入密码" >
                            </div>
                            <div class="login-links">
                                <div class="remeberfield fl">
                                    <input type="checkbox" id="isRememberUsername" name="isRememberUsername" value="true" class="x-check action-remember-account" checked="checked">
			              			<label for="isRememberUsername" class="form-sub-label">记住用户名</label>
                                </div>
                                <a href="${base}/password/mobilefind.do" class="forget-pwd fr" target="_blank">忘记密码</a>
                            </div>
                            <div class="submitbox">
                                <button type="submit" class="login_submit" id="SubmitStatic">登　录</button>
                            </div>
                            <div class="login_way2"><span class="login_rtname"> 没有账号？<a href="${base }/register.do">立即签约></a></span></div>
                        </form>                                              	                        
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
