<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>店铺总览</title>
<link rel="stylesheet" href="${base}/resources/css/style.css">
</head>

<body>
  <div class="site-topbar">
    <div class="container-navfo">
      <div class="topbar-info">
        <span>你好,欢迎来到众慧商城!</span>
        <a href="#" class="color-nt">[请登录]</a>
        <a href="#">[免费注册]</a>
      </div>
      <div class="topbar-nav">
        <a href="#">我的商城</a>
        <a href="#">我的订单</a>
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
  <div class="wrap">
    <div class="container-setting fix">
      <div class="aside-nav">
        <h3>我的应用</h3>
        <div class="li-container">
          <ul>
           <li class="category">
             <h4 class="category-name tra-01">店铺管理</h4>
             <ol class="catgory-app">
               <li class="common-app">
                 <a href="#">店铺总览</a>
               </li>
               <li class="common-app">
                 <a href="#">店铺设置</a>
               </li>
             </ol>
           </li>
           <li class="category">
             <h4 class="category-name tra-02">交易管理</h4>
             <ol class="catgory-app">
               <li class="common-app">
                 <a href="#">订单管理</a>
               </li>
               <li class="common-app">
                 <a href="#">退货管理</a>
               </li>
               <li class="common-app">
                 <a href="#">发货管理</a>
               </li>
               <li class="common-app">
                 <a href="#">支付管理</a>
               </li>
               <li class="common-app">
                 <a href="#">快递管理</a>
               </li>
             </ol>
           </li>
           <li class="category">
             <h4 class="category-name tra-03">商品管理</h4>
             <ol class="catgory-app">
               <li class="common-app">
                 <a href="#">商品发布</a>
               </li>
               <li class="common-app">
                 <a href="#">商品分类</a>
               </li>
               <li class="common-app">
                 <a href="#">商品属性</a>
               </li>
               <li class="common-app">
                 <a href="#">商品导入</a>
               </li>
             </ol>
           </li>
           <li class="category">
             <h4 class="category-name tra-04">广告管理</h4>
             <ol class="catgory-app">
               <li class="common-app">
                 <a href="#">首页广告</a>
               </li>
               <li class="common-app">
                 <a href="#">内页广告</a>
               </li>
             </ol>
           </li>
          </ul>
        </div>
      </div>
      <div class="container-middle">
          
          <div class="shop-info">
            <div class="shop-logo"><img src="${base}/resources/images/sh_logo.jpg" alt="" class="logo"></div>
            <div class="shop-detail">
              <ul>
              	<li>
                  <dl>
              		<dt>商家名称 :</dt>
              		<dd><a href="#" class="shop-name">${tradeAdmin.trade.name}</a></dd>
              	  </dl>
                </li>
                <li>
                  <dl>
              		<dt>商家ID :</dt>
              		<dd>${tradeAdmin.trade.shop_no}</dd>
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
              		<dd>${tradeAdmin.username}</dd>
              	  </dl>
                </li>
                <li>
                  <dl>
              		<dt>登录时间 :</dt>
              		<dd>${tradeAdmin.login_date}</dd>
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
                <h4>收入总览</h4>
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
  <!--脚部-->
  <div class="footer">
        <!--绿色正品保障部分-->
        <div class="footer-top">
            <ul class="footer-mid">
                <li class="foot-protect">
                    <img src="${base}/resources/images/foot_top1.png">
                    <h3>正品保障</h3>
                    <span>全场正品，行货保障</span>    
                </li>
                <li class="foot-protect">
                    <img src="${base}/resources/images/foot_top2.png">
                    <h3>新手指南</h3>
                    <span>快速登录，无需注册</span>    
                </li>
                <li class="foot-protect">
                    <img src="${base}/resources/images/foot_top3.png">
                    <h3>维修保障</h3>
                    <span>服务保证，全国联保</span>    
                </li>
                <li class="foot-protect">
                    <img src="${base}/resources/images/foot_top4.png">
                    <h3>无忧退货</h3>
                    <span>无忧退货，7日尊享</span>    
                </li>
                <li class="foot-protect">
                    <img src="${base}/resources/images/foot_top5.png">
                    <h3>会员权贵</h3>
                    <span>会员权贵，尊贵特权</span>    
                </li>
            </ul>
        </div>

        <!--各种文章-->
        <div class="footer-main">
                <li>
                    <h3>购物指南</h3>
                    <a href="#">购物流程</a>
                    <a href="#">会员介绍</a>
                    <a href="#">生活旅行/团购</a>
                    <a href="#">常见问题</a>
                    <a href="#">联系客服</a>
                </li>
                <li>
                    <h3>配送方式</h3>
                    <a href="#">上门自提</a>
                    <a href="#">211限时达</a>
                    <a href="#">配送服务查询</a>
                    <a href="#">配送费收取标准</a>
                </li>
                <li>
                    <h3>支付方式</h3>
                    <a href="#">货到付款</a>
                    <a href="#">在线支付</a>
                    <a href="#">分期付款</a>
                    <a href="#">邮局汇款</a>
                    <a href="#">公司转账</a>
                </li>

                <li>
                    <h3>售后服务</h3>
                    <a href="#">售后政策</a>
                    <a href="#">价格保护</a>
                    <a href="#">退款说明</a>
                    <a href="#">返修/退换</a>
                    <a href="#">取消订单</a>
                </li>
        </div>

        <!--网站信息部分-->
        <div class="footer-bottom">
            <p class="foot-p1">有任何购物问题请联系客服 | 电话：400-052-6658 | 工作时间：周一到周五 8：00-18:00</p>
            <p>Copyright © 2016 东兴市京华实业有限公司 版权所有</p>
            <p>桂ICP备16002908号-1</p>
        </div>
        <!--安全认证-->
        <div>
          <ul class="ce-safe">
          	<li><img src="${base}/resources/images/icon_little01.jpg" alt=""></li>
            <li><img src="${base}/resources/images/icon_little02.png" alt=""></li>
          </ul>
        </div>
    </div>

</body>
</html>