<!doctype html>
<html>

<head>
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>商家中心</title>	
	<link type="text/css" rel="stylesheet" href="${base}/resources/css/style.css"></link>
</head>


<body>
	  <!-- 头部 -->
	  [#include "/common/header.ftl"/]
	  <div class="wrap">
	    <div class="container-setting fix">
		  <!-- 菜单 -->
		  [#include "/common/menu.ftl"/]
	      <div class="container-middle">
	      	
	          <div class="shop-info">
	            <div class="shop-logo"><img src="${base}/resources/images/sh_logo.jpg" alt="" class="logo"></div>
	            <div class="shop-detail">
	              <ul>
	              	<li>
	                  <dl>
	              		<dt>商家名称 :</dt>
	              		<dd><a href="#" class="shop-name">${trade.name}</a></dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>商家ID :</dt>
	              		<dd>${trade.no }</dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>店铺综合评价 :</dt>
	              		<dd class="grade-star"><span class="gs-inner"></span></dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>商家账户 :</dt>
	              		<dd>${tradeAdmin.username }</dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>登录时间 :</dt>
	              		<dd>${trade.login_date }</dd>
	              	  </dl>
	                </li>
	              </ul>
	            </div>
	            <div class="qr-code">
	              <a href="#">进入商店<img src="${base}/resources/images/qr_code.jpg" alt=""></a>
	            </div>
	          </div>
	          <!--店铺总览-->
	          <div class="shop-setting">
	            <h3>店铺总览</h3>
	            <div>
	              <div class="income-view zl-s">
	                <h4>收入总览</h4>
	                <div>
	                  <p class="zh_color1"><i>今日订单收入</i><span>524555元</span></p>
	                  <p class="zh_color2"><i>总订单收入</i><span>5555元</span></p>
	                </div>
	              </div>
	              <div class="order-view zl-s">
	                <h4>订单总览</h4>
	                <div>
	                  <p class="zh_color3"><em><img src="${base}/resources/images/bj_ordnum.png" /></em><span>111</span><i>今日订单总数</i></p>
	                  <p class="zh_color4"><em><img src="${base}/resources/images/bj_ordnum2.png" /></em><span>22</span><i>待付款订单数</i></p>
	                </div>
	              </div>
	            </div>
	          </div>
	      </div>
	    </div>
	  </div>
	 

  	  <!-- 底部 -->
	  [#include "/common/footer.ftl"/]
</body>
</html>