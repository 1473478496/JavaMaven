<!doctype html>
<html>

<head>
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>商家中心</title>	
	<link rel="stylesheet" type="text/css" href="${base}/resources/css/webuploader.css" />
	<link rel="stylesheet" type="text/css" href="${base}/resources/css/style.css">
	<script src="${base}/resources、js/jquery-1.8.3.min.js"></script>
</head>

<body>
	  <!-- 头部 -->
	  [#include "/common/header.ftl"/]
	  <div class="wrap">
	    <div class="container-setting fix">
		  <!-- 菜单 -->
		  [#include "/common/menu.ftl"/]
		  
	      <div class="container-middle">
	          <ul class="b-nav">
	            <li><a href="#">店铺管理</a><span>></span></li>
	            <li><a href="#">店铺设置</a></li>
	          </ul>
	          <div class="shop-info">
	            <div class="shop-logo"><img src="${base}/resources/images/sh_logo.jpg" alt="" class="logo"></div>
	            <div class="shop-detail">
	              <ul>
	              	<li>
	                  <dl>
	              		<dt>商家名称 :</dt>
	              		<dd><a href="#" class="shop-name">奥斯提娜山旗舰店</a></dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>商家ID :</dt>
	              		<dd>10001</dd>
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
	              		<dd>15986608704</dd>
	              	  </dl>
	                </li>
	                <li>
	                  <dl>
	              		<dt>登录时间 :</dt>
	              		<dd>2016/8/3 18:27:13</dd>
	              	  </dl>
	                </li>
	              </ul>
	            </div>
	            <div class="qr-code">
	              <a href="#">进入商店<img src="${base}/resources/images/qr_code.jpg" alt=""></a>
	            </div>
	          </div>
	          <!--店铺设置-->
	          <div class="shop-setting">
	            <h3>店铺设置</h3>
	            <div>
	             <!-- 
	            <form action="${base }/trade/shopset_save.do" method="post" class="shopset-form" enctype="multipart/form-data">
                <label>店铺名称 : </label><input type="text" name="name" id="name"/><br>
                <label>店铺<span class="logo-char">LOGO</span> : </label>  
                <label class="upload-pre" for="logo_url"></label><input type="file" id="logo_url" name="imgfiles" class="upload-img"/><br>
                <label>在线客服 : </label><input type="text"  name="customer_service" id="customer_service"/><br>
                <label>工作时间 : </label><input type="text" class="t-in tl0-in" name="begin_worktime" id="begin_worktime"/><span>至</span><input type="text" class="t-in" name="end_worktime" id="end_worktime"/><span>7*24小时</span><br>
                <label>服务地址 : </label><input type="text"  name="addr" id="addr"/><br>
                <label>店铺公告 : </label><input type="text"  name="shop_board" id="shop_board"/><br>
                <label>备注 : </label><input type="text"  name="remark" id="remark"/><br>

                <input type="submit" class="save-all" name="add-detail" id="add-detail" value="保存"/>
                </form>
                
                -->
                
                <form action="${base }/trade/shopset_save.do" method="post" class="shopset-form" enctype="multipart/form-data">
	                <div class="settfrom">
	                	<label>店铺名称 : </label><input type="text" name="name" id="name"/>
	                </div>
	                <div class="settfrom">
	                	<label>店铺<span class="logo-char">LOGO</span> : </label>
	                	<!--参考商品编辑页面上传图片 -->
	               		<div class="wbupbox">
	               			<!-- 图片上传使用的是webuploader 插件 可参考萌哥吧评价晒单	 -->
							<div id="uploader">
				                <div class="queueList">
				                    <div id="dndArea" class="placeholder">
				                        <div id="filePicker"></div>
				                        <div id="btnContainer"></div>
				                        <p>或将照片拖到这里</p>
				                    </div>
				                </div>
				                <div class="statusBar" style="display:none;">
				                    <div class="progress">
				                        <span class="text">0%</span>
				                        <span class="percentage"></span>
				                    </div><div class="info"></div>
				                    <div class="btns">
				                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
				                    </div>
				                </div>
				            </div>
						</div>
	                </div>
	                <div class="settfrom">
	                	<label>在线客服 : </label><input type="text"  name="customer_service" id="customer_service"/>
	                </div>
	                <div class="settfrom">
	                	<label>工作时间 : </label><input type="text" class="t-in tl0-in" name="begin_worktime" id="begin_worktime"/><span>至</span><input type="text" class="t-in" name="end_worktime" id="end_worktime"/><span>7*24小时</span>
	                </div>
	                <div class="settfrom">
		                 <label>服务地址 : </label><input type="text"  name="addr" id="addr"/>
	                 </div>
	                 <div class="settfrom">
		                 <label>店铺公告 : </label><input type="text"  name="shop_board" id="shop_board"/>
	                 </div>
	                 <div class="settfrom">
		                 <label>备注 : </label><input type="text"  name="remark" id="remark"/>
	                 </div>
	                 <input type="submit" class="save-all" name="add-detail" id="add-detail" value="保存"/>
	              </form>
	              
	            </div>
	          </div>
	      </div>
	    </div>
	  </div>
	 

  	  <!-- 底部 -->
	  [#include "/common/footer.ftl"/]
	<script src="${base}/resources/js/webuploader.js"></script>
    <script src="${base}/resources/js/upload.js"></script>
</body>
</html>