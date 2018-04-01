
$(function(){
	// 短信登录切换
	$('.login_method').toggle(function(){
		$("#loginForm").toggle();
		$("#loginForm2").toggle();
		$(this).addClass('on');
		$(this).html('账号密码登录');
	},function(){
		$(this).removeClass('on');
		$(this).html('手机动态码登录');
		$("#loginForm2").toggle();
		$("#loginForm").toggle();
	});
	
})

// 广告管理
$(function () {
    //初始化
    tabAni(0);
    //点击，获取第几位
    $('.top_nav li').click(function () {
        var int = $(this).index();      //index()获取当前元素第几位，0开始
        tabAni(int);
    });
    //遍历第几位
    function tabAni(int) {
        $('.top_nav li').removeClass('on').eq(int).addClass('on');
        $('.advert').hide().eq(int).show();
    };
});
