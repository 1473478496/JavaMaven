<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>店铺设置</title>
<link rel="stylesheet" type="text/css" href="${base}/resources/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/css/style.css">
<script type="text/javascript" src="${base }/resources/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${base }/resources/js/cookie.js"></script>
<script src="${base}/resources/js/webuploader.js"></script>
<script src="${base}/resources/js/upload.js"></script>
<script src="${base}/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="${base}/resources/js/jquery.cxselect.js"></script>
</head>
<body>
  [#include "/common/header.ftl"/]
	
  <div class="wrap">
    <div class="container-setting fix">
      [#include "/common/menu.ftl"/]
      <div class="container-middle">
          <ul class="b-nav">
            <li><a href="#">店铺管理</a><span>></span></li>
            <li><a href="#">店铺设置</a></li>
          </ul>
          <div class="shop-info">
            <div class="shop-logo"><img src="${base}${trade.logo_url}" alt="" class="logo"></div>
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
              		<dd>${trade.shop_no}</dd>
              	  </dl>
                </li>
                <li>
                  <dl>
              		<dt>店铺综合评价 :</dt>
              		<dd class="grade-star"><span class="gs-inner"></span><span class="gs-inner"></span></dd>
              	  </dl>
                </li>
                <li>
                  <dl>
              		<dt>商家账户 :</dt>
              		<dd>${admin.username}</dd>
              	  </dl>
                </li>
                <li>
                  <dl>
              		<dt>登录时间 :</dt>
              		<dd>${admin.login_date}</dd>
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
            	<!-- 开店关店 -->
            	<div class="openshop">
            		<a href="javascript:void(0)" class="openspbtn on">开店</a>
            		<a href="javascript:void(0)" class="closespbtn on">关店</a>
            	</div>
              <form action="${base}/tradeShop/shopset_save.do" method="post" class="shopset-form" id="reviewForm">
              	<input type="hidden" value="${base}/tradeShop/upload_img.do" id="path">
              	<input type="hidden" value="${tradeShop.id}" name="id" id="shopId"/>
              	<input type="hidden" value="${(tradeShop.status=='close')?string('1','0')}" id="status">
              	<input type="hidden" value="${tradeShop.remark}" name="remark">
              	<input type="hidden" value="${tradeShop.score}" name="score">
              	<input type="hidden" value="${tradeShop.shop_board}" name="shop_board">
              	<input type="hidden" value="${(tradeShop.platStatus=='open')?string('0','1')}"  id="platStatus">
                <div class="settfrom">
                	<label>店铺名称 : </label><input type="text" name="name" id="name" value="${tradeShop.name}"/><span class="error"></span>
                </div>
                <div class="settfrom">
                	<label>店铺<span class="logo-char">LOGO</span> : </label>
                	<!--参考商品编辑页面上传图片 -->
               		<div class="wbupbox">
               			<!-- 图片上传使用的是webuploader 插件 可参考萌哥吧评价晒单	 -->
						<div id="uploader" class="wuploader">
			                <div class="queueList">
			                    <div id="dndArea" class="placeholder">
			                        <div id="filePicker"></div>
			                        <div id="btnContainer"></div>
			                        <p>请上传一张照片</p>
			                    </div>
			                </div>
			                <div class="statusBar" style="display:none;">
			                    <div class="progress">
			                        <span class="text">0%</span>
			                        <span class="percentage"></span>
			                    </div><div class="info"></div>
			                    <div class="btns">
			                        <div id="filePicker2"></div><div class="uploadBtn" id="uploadImage">开始上传</div>
			                    </div>
			                </div>
			                <div id="uplaodImage"></div>
			            </div>
			            
					</div>
                </div>
                <div class="settfrom">
                	<label>联系电话 : </label><input type="text"  name="phone" id="phone" value="${tradeShop.phone}"/>
                </div>
                <div class="settfrom">
                	<label>工作时间 : </label><input type="text" class="t-in tl0-in" name="begin_worktime" id="start-time" value="${tradeShop.begin_worktime}"/><span>至</span><input type="text" class="t-in" name="end_worktime" id="end-time" value="${tradeShop.end_worktime}"/><span>7*24小时</span>
                </div>
                <div class="settfrom" id="element_id">
	                <label>服务地址 : </label>
  					<select class="province server-site" name="addr"></select> 
  					<select class="city server-site" name="addr"></select> 
  					<select class="area server-site" name="addr"></select> 
	                <p><input type="text" class="ml-in" name="addr" id="addr" /></p>
                 </div>
                   <div class="settfrom">
                	<label>在线客服 : </label><input type="text" name="customer_service" id="customer_service" value="${tradeShop.customer_service}"/><span style="margin-left: 10px;">多个请使用","分隔</span>
                </div>
                 <div class="settfrom">
                	<label>关键词: </label><input type="text"  name="searchKey" id="searchKey" value="${tradeShop.searchKey}"/>
                </div>
                 <input type="button" class="save-all" name="add-detail" id="add-detail" value="保存"/>
              </form>
              
              
               <div  class="shopset-form" id="showForm" style="display:none;z-index:100;zoom:1;">
              	
                <div class="settfrom">
                	<label>店铺名称 : </label><span>${tradeShop.name}</span>
                </div>
                <div class="settfrom">
                	<label>店铺<span class="logo-char">LOGO</span> : </label>
                	<!--参考商品编辑页面上传图片 -->
               		<div class="wbupbox">
               			<img src="${base}/${tradeShop.logo_url}" width="180" height="110"/>
					</div>
                </div>
                <div class="settfrom">
                	<label>联系电话 : </label><span>${tradeShop.phone}</span>
                </div>
                <div class="settfrom">
                	<label>工作时间 : </label><span>${tradeShop.begin_worktime}</span><span>-----</span><span>${tradeShop.end_worktime}</span>
                </div>
                <div class="settfrom">
	                <label>服务地址 : </label><span>${tradeShop.addr}</span>
                 </div>
                  <div class="settfrom">
	                <label>店铺状态 : </label><span>${(tradeShop.status=='close')?string('关闭','开启')}</span>
                 </div>
                 <div class="settfrom">
                	<label>关键词: </label><span>${tradeShop.searchKey}</span>
                </div>
                 <input type="button" class="save-all" name="add-detail" id="update" value="修改"/>
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>

[#include "/common/footer.ftl"/]

<script type="text/javascript">
	var Estimate = function(){
		var that = this;

		this.submitForm = function(obj){
			var $form = $("#reviewForm");
			var $upload = $('.btns').find( '.uploadBtn' );
			var size=$('.filelist li').size();
			$upload.on('click', function() {
				uploader.upload();
	        });
	       	if(size != 1){
  				alert("请选择一张图片");
  				return false;
  		 	}
			if(size ==  1 && $("#reviewForm").valid()){
				$("#uploadImage").trigger("click");
			}
			uploader.onUploadFinished=function(){
		        $form.submit();
            }
            
		};
	};
	
	var estimate = new Estimate();
	
	$("#add-detail").click(function(){
		estimate.submitForm();	
  	});
	$(document).ready(function(){
		// 如果店铺信息不存在，则不显示开店关店按钮
		if(!$("#shopId").val()){
			$(".openshop").hide();
		}else{
			$("#showForm").show();
			$("#reviewForm").attr({'style':'position:absolute;left:-200%;'})
		}
		// 判断店铺信息的状态
		if($("#status").val() == '0' && $("#platStatus").val() == '0'){
			$(".openspbtn").hide();
		}else if($("#status").val() == '1' && $("#platStatus").val() == '0'){
			$(".closespbtn").hide();
		}else{
			$(".openspbtn").hide();
			$(".closespbtn").hide();
		}
		$("#update").click(function(){
			$("#reviewForm").removeAttr('style');
			$("#showForm").hide();
		});
		$('#element_id').cxSelect({ 
  			url: '${base}/resources/js/cityData.min.json',   // 提示：如果服务器不支持 .json 类型文件，请将文件改为 .js 文件 
  			selects: ['province', 'city', 'area'], 
  			nodata: 'none' 
		}); 
	});
	
	$().ready(function(){
		jQuery.validator.addMethod("phone",function(value,element){
			var length = value.length;
			return this.optional(element) || (length == 11 && /^(0|86|17951)?(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|170)\d{8}$/.test(value));
		},"请输入正确的手机号码");
		$("#reviewForm").validate({
			rules:{
				name:"required",
				phone:{
					required:true,
					phone:true
				},
				searchKey:{
					rangelength:[0,35]
				},
				customer_service:{
					required:true
				}
				
			},
			messages:{
				name:"请输入店铺名称",
				phone:{
					required:"请输入电话号码",
					phone:"请输入正确格式的手机号"
				},
				searchKey:{
					rangelength:"字数范围为35个以下"
				},
				customer_service:{
					required:"请输入客服号码"
				}
				
			},
			errorElement:"span",
			
		});
		$(".openshop a").on("click",function(){
			if(!$(this).hasClass('on')){
				return;
			}else{
				var r=confirm("确定"+$(this).html()+"吗？");
    			if(r && $(this).html() == '关店'){
    				$(this).removeClass('on').siblings().addClass('on');
    				location.href = "${base}/tradeShop/closeShop.do?id="+$("#shopId").val();
    			}else if(r && $(this).html() == '开店'){
    				$(this).removeClass('on').siblings().addClass('on');
    				location.href = "${base}/tradeShop/openShop.do?id="+$("#shopId").val();
    			}
			}
		})
	});
</script>

</body>
</html>
