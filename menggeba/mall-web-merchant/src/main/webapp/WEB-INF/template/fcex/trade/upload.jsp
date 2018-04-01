<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>会员中心-评价晒单</title>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/css/mgb_style.css">
<link rel="stylesheet" type="text/css" href="${base }/resources/css/webuploader.css" />
<script type="text/javascript" src="${base }/resources/js/jquery.js"></script>

<script type="text/javascript" src="${base }/resources/js/webuploader.js"></script>
<script type="text/javascript" src="${base }/resources/js/upload.js"></script>

</head>

<body>
	<!--正文内容开始，正文写在这里面-->
	<div class="wd_content">
		<!--商品订单-->
		<div class="wd_mbmain">
	
			<div class="wd_mbgoodsli">

				  <form id="reviewForm" action="${base}/trade/trade_save.do" method="post">
				  	<input type="hidden" value="${base}/trade/upload_img.do" id="path">
				  	
				  	<input type="hidden" name="id" value="130" />
					<input type="hidden" name="sn" value="140" />
					<input type="hidden" id="score" name="score" value="0">
					
					
					<div class="bj_commentmain">
						<div class="fl commentimgbox">
				            <div id="uploader">
				                <div class="queueList">
				                    <div id="dndArea" class="placeholder">
				                        <div id="filePicker"></div>
				                        <p>单次最多可选2张</p>
				                    </div>
				                </div>
				                
				                <div class="statusBar" style="display:none;">
				                    <div class="progress">
				                        <span class="text">0%</span>
				                        <span class="percentage"></span>
				                    </div><div class="info"></div>
				                    <div class="btns">
				                        <div id="filePicker2"></div><div class="uploadBtn" hidden="hidden">开始上传</div>
				                    </div>
				                </div>
				                
				                <div id="uplaodImage"></div>
				            </div>
						</div>
					</div>
										
				  </form>
				
			</div>

		</div>


	</div>

<script type="text/javascript">
	var Estimate = function(){
		var that = this;
		
		
		this.submitForm = function(obj){
			var $form = $("#reviewForm");
			var $upload = $('.btns').find( '.uploadBtn' );
			$upload.on('click', function() {
				uploader.upload();
	        });
	        
		};
		
		//绑定事件
		this.bindEvent = function(){
		};
		
		// 初始化
		this.init = function(){
			that.bindEvent();
		};
	};
	
	var estimate = new Estimate();

	
</script>

</body>
</html>