<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商家入驻申请</title>
     <link rel="stylesheet" type="text/css" href="${base}/resources/css/webuploader.css" />
     <link rel="stylesheet" href="${base}/resources/css/style.css">
     
</head>

<body>
  <!-- 引入头部文件 -->
   [#include "/common/header.ftl"/] 
  
  <!-- 流程广告图 -->
  <div class="bander-pic">
  	<!-- 后台可以传图片 -->
    <img src="${base}/resources/images/shop_apply_banner01.jpg" alt=""/>
  </div>
  <div class="wrap">
    <div class="cooperation-pro">
      <div class="info-sub">
         <h3>商家合作信息</h3>
         <span>|</span>
         <em>联系电话 0755-25884663</em>
       </div>
      <div class="cop-info">
   		<div class="order-control">
           <h3 class="proimgtit">基本信息</h3>
           <div class="proinfo">
           
          <form id="reviewForm"  action="${base }/trade/trade_save.do" method="post" >
          <input type="hidden" value="${base}/trade/upload_img.do" id="path"/>
          <input type="hidden" value="${base}/trade/upload_img.do" id="wpath"/>
           		<!--商家基本信息   -->
           		<ul class="proinfolist">
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>中文名</label>
           					<input type="text" name="trade.name" datatype="s2-16" nullmsg="请输入您的中文名！" errormsg="中文名至少2个字符,最多16个字符 ！"/>
           					<div class="info"><span class="Validform_checktip">中文名至少2个字符,最多16个字符！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           					<input type="hidden" name="trade.status" id="status"  value="waitingApprove"/>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>越南名</label>
           					<input type="text" name="trade.alias_Name" datatype="s2-16" nullmsg="请输入您的越南名！" errormsg="越南名至少2个字符,最多16个字符！" />
           			<div class="info"><span class="Validform_checktip">越南名至少2个字符,最多16个字符！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>证件类型</label>
           						<select name="trade.cert_type" datatype="*" nullmsg="请选择证件类型">
           					     <option value="">请选择</option>
			                   <option value="identityCard" >身份证</option>
			                   <option value="passport" >护照</option>
			                   <option value="militaryID" >军官证</option>
			                 </select>
			           	<div class="info"><span class="Validform_checktip">请选择证件类型</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
			                 
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>证件号码</label>
           				<input type="text" name="trade.cert_nos"  datatype="xx" nullmsg="请输入您的证件号！"  errormsg="您填写的证件号码不对！" />
           					<div class="info"><span class="Validform_checktip">您填写的证件号码不对！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>性别</label>
           				<select name="trade.sex" datatype="*" nullmsg="请选择性别" >
           					   <option value="">请选择</option>
			                   <option value="male" >男</option>
			                   <option value="female" >女</option>
			                 </select>
			      	<div class="info"><span class="Validform_checktip">请选择性别</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>手机号码</label>
           				<input type="text" name="trade.mobiles" datatype="m" nullmsg="请输入您的手机号！"  errormsg="输入的手机号格式不对！" />
           			<div class="info"><span class="Validform_checktip">输入的手机号格式不对！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label>生日</label>
           					<input type="text" id="birthDays" class="Wdate" name="trade.birthday"/>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label>住址</label>
           					<input type="text" name="trade.addr" datatype="s2-100"  ignore="ignore"  errormsg="住址至少2个字符,最多100个字符！" />
           			<div class="info"><span class="Validform_checktip">住址至少2个字符,最多100个字符！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           		</ul>
           		
           		<!-- 签约信息 -->
           		<h3 class="proimgtit">签约信息</h3>
           		<ul class="proinfolist contract_list">
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>商铺号</label>
           					<input type="text" name="trade.shop_no" datatype="n2-20" nullmsg="请输入您的商铺号！" errormsg="商铺号至少2个字符,最多30个字符!" />
           			<div class="info"><span class="Validform_checktip">商铺号至少2个字符,最多30个字符！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>协议起止时间</label>
           					<input type="text" id="beginDay" class="Wdate" name="trade.beg_date" datatype="*" nullmsg="请输入协议起止时间！" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDay\')})"/>
           					<div class="info"><span class="Validform_checktip">请输入协议起止时间！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           					 — <input type="text" id="endDay" class="Wdate" name="trade.end_date" datatype="*" nullmsg="请输入协议终止时间！" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDay\')}' })" />
           					 <div class="info"><span class="Validform_checktip">请输入协议终止时间！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           			<li>
           				<div class="settfrom">
           					<label><i>*</i>商户类型</label>
           					<div class="busnistyle">
           						<!-- 可多选 -->
           					[#list lists as a ]
	           					<label><input type="checkbox" id="qx" name="dd" datatype="need2" nullmsg="请选择常商户类型！" value="${a.id}" >${a.name}</label>
	           				[/#list]
							</div>
				    		<div class="info"><span class="Validform_checktip">商户类型至少选择一项！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           				</div>
           			</li>
           		</ul>
           		
           		
           		
           			<!-- 商家资质 必须上传营业执照和法人身份证照片 至少2张图片  可以在本页面实例化 -->
           		<h3 class="proimgtit">商家资质</h3>
           		<div class="business_img">
           			<div class="settfrom">
      					<label><i>*</i>请上传营业执行</label>
	               		<div class="wbupbox">
	               			<!-- 图片上传使用的是webuploader 插件 可参考萌哥吧评价晒单	 -->
							<div id="uploader" class="wuploader">
				                <div class="queueList">
				                    <div id="dndArea" class="placeholder">
				                        <div id="filePicker"></div>
				                        <div id="btnContainer"></div>
				                        <p>请上传营业执行</p>
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
				                <div id="uplaodImage"></div>
				            </div>
					
						</div>
      				</div>
      				
      				<div class="settfrom">
      					<label><i>*</i>请上传法人身份证</label>
	               		<div class="wbupbox">
	               			<!-- 图片上传使用的是webuploader 插件 可参考萌哥吧评价晒单	 -->
							<div id="wuploader" class="wuploader">
				                <div class="queueList">
				                    <div id="wdndArea" class="placeholder">
				                        <div id="wfilePicker"></div>
				                        <div id="wbtnContainer"></div>
				                        <p>请上传法人身份证</p>
				                    </div>
				                </div>
				                <div class="statusBar" style="display:none;">
				                    <div class="progress">
				                        <span class="text">0%</span>
				                        <span class="percentage"></span>
				                    </div><div class="info"></div>
				                    <div class="btns">
				                        <div id="wfilePicker2"></div><div class="uploadBtn">开始上传</div>
				                    </div>
				                </div>
				                <div id="wuplaodImage"></div>
				            </div>
						</div>
      				</div>
           		</div>
           		
           		
           		
           		<!-- 代理人可以不填 不必须 -->
           		<h3 class="proimgtit">代理人信息</h3>
           		<div class="agentbox">
           			<!-- 添加代理人模块 -->
           			<div class="orderbtnbox">
           				<input type="button" class=" order_btnj agentbtn" value="添加代理人"/>
           			</div>
           		</div>
           		
           		<!-- 银行信息至少填一个 -->
       			<h3 class="proimgtit">银行信息</h3>
       			<div class="agentbox">
       				<div class="orderbtnbox">
           				<input type="button" class="order_btnj bankbtn" value="添加银行卡"/>
           			</div>
       				<!-- 添加银行卡模块 -->
	       			<div class="agentinfobox bankinfobox">
	       				<p>银行卡1</p>
	       				<ul class="proinfolist agent_list">
	       					<li>
	       						<div class="settfrom">
	         					<label><i>*</i>账户名称</label>
	         					<input type="text"  name="banks.card_name" datatype="s2-16" nullmsg="请输入您的银行账户名称！" errormsg="账户名称至少2个字符,最多16个字符！" />
	         	  				<div class="info"><span class="Validform_checktip">账户名称至少2个字符,最多16个字符</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
	         				
	         				</div>
	       					</li>
	       					<li>
		         				<div class="settfrom">
		         					<label><i>*</i>银行卡卡号</label>
		         			<input type="text" name="banks.card_nos" datatype="hh" nullmsg="请输入您的银行卡卡号！" errormsg="输入的银行卡卡号银行格式不对！卡号16位数,或者19位数！" />
           			<div class="info"><span class="Validform_checktip">输入的银行卡卡号银行格式不对！卡号16位数,或者19位数！</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
           					
		         				</div>
		         			</li>
		         			<li>
		         				<div class="settfrom">
		         					<label><i>*</i>开户银行</label>
		         					<select name="banks.bank_name" datatype="*" nullmsg="请选择开户银行" >
					                   <option value="">请选择</option>
					                   <option value="中国银行">中国银行</option>
					                   <option value="招商银行">招商银行</option>
					                   <option value="平安银行">平安银行</option>
					                 </select>
	   	  				<div class="info"><span class="Validform_checktip">请选择开户银行</span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
					                 
		         				</div>
		         			</li>
	       				</ul>
	       				<a class="bankdelbtn" onclick="bankDel(this);" href="javascript:;">删除银行卡</a>
	       			</div>
	       			
           		</div>
           		
           		<!-- 备注 -->
           		<ul class="proinfolist">
           			<li>
           				<div class="settfrom">
           					<label>备注</label>
           					<input type="text" name="trade.remarkv" id="remarkv"/>
           				</div>
           			</li>
           		</ul>
           		
           		<div class="agreebox">
   					<label><input type="checkbox" name="trade.agree" datatype="need2" nullmsg="请勾选！" value="agree" ><a href="#">同意商家合作协议</a></label>
   				</div>
           		
           		<!-- 按钮 -->
           		<div class="orderbtnbox probtnbox">
	                <input type="button" value="提交" id="add-detail"/>
	                <input type="reset" class="order_btnj"  value="取消"/>
	            </div> 
	            
           	</form>
           </div>
         </div>
            	
      
      </div>
    </div>
  </div>
  <script src="${base}/resources/js/webuploader.js"></script>
     <script src="${base}/resources/js/upload.js"></script>
       <script src="${base}/resources/js/upload_apply.js"></script>
     <script src="${base}/resources/datepicker/WdatePicker.js"></script>
   <script src="${base}/resources/js/Validform.min.js"></script>
 
  <script>
  
  var Estimate = function(){
		var that = this;
		
		this.submitForm = function(obj){
			var $form = $("#reviewForm");
			var $upload = $('#uploader .btns').find( '.uploadBtn' );
			var $wupload = $('#wuploader .btns').find( '.uploadBtn' );
			var size=$('#uploader .filelist li').size();
			var wsize=$('#wuploader .filelist li').size();
			
	       	if(size != 1 || wsize != 1){
				alert("请各上传一张图片");
				return false;
		 	}
			if(size ==  1  && wsize == 1 && $form.Validform().check()){
				$upload.trigger("click");
				$wupload.trigger("click");
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
	
$("#birthDays,#beginDay,#endDay").click(function(){
		WdatePicker();
	});
	
	
	
    var agentIndex=1;
    var listIndex=-1;
    $(".agentbtn").on('click',function(){
    	listIndex++;
    	var html='';
    		html+='<div class="agentinfobox">'
           				+'<p>代理人'+(agentIndex++)+'</p>'
           				+'<ul class="proinfolist agent_list">'
           					+'<li>'
           						+'<div class="settfrom">'
		           					+'<label><i>*</i>代理人姓名</label>'
		           					+'<input type="text" name="tradeProxy['+(listIndex)+'].dname" datatype="s2-16" nullmsg="请输入您的代理人姓名！" errormsg="代理人姓名至少2位,最多16个字符！" />'
		               				+'<div class="info">'+'<span class="Validform_checktip">代理人姓名至少2位,最多16个字符！</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
		           					+'</div>'
           					+'</li>'
           					+'<li>'
		           				+'<div class="settfrom">'
		           					+'<label><i>*</i>手机号码</label>'
		           					+'<input type="text" name="tradeProxy['+(listIndex)+'].mobilet" datatype="m" nullmsg="请输入您的手机号！" errormsg="手机号码格式不对！"/>'
		               				+'<div class="info">'+'<span class="Validform_checktip">手机号码格式不对！</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
		           					+'</div>'
		           			+'</li>'
		           			+'<li>'
		           				+'<div class="settfrom">'
		           					+'<label><i>*</i>证件类型</label>'
		           					+'<select name="tradeProxy['+(listIndex)+'].cert_types" datatype="*" nullmsg="请选择证件类型！">'
					                   +'<option value="">请选择</option>'
					                   +'<option value="identityCard">身份证</option>'
					                   +'<option value="passport">护照</option>'
					                   +'<option value="militaryID">军官证</option>'
					                 +'</select>'
			               				+'<div class="info">'+'<span class="Validform_checktip">请选择证件类型！</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
					                 +'</div>'
		           			+'</li>'
		           			+'<li>'
		           				+'<div class="settfrom">'
		           					+'<label><i>*</i>证件号码</label>'
		           					+'<input type="text" name="tradeProxy['+(listIndex)+'].cert_not" datatype="xx" nullmsg="请输入您的证件号！"  errormsg="您填写的证件号码不对！" />'
		               				+'<div class="info">'+'<span class="Validform_checktip">您填写的证件号码不对！</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
		           					+'</div>'
		           			+'</li>'
           				+'</ul>'
           				+'<a class="agentdel" onclick="agentInfoDel(this)" href="javascript:;">删除代理人</a>'
           			+'</div>';
           	$(this).parents('.agentbox').append(html);
    });
    
    
    var bankIndex=2;
    var blist=-1;
    $(".bankbtn").on('click',function(){
    	blist++;
    	var html='';
    	html+='<div class="agentinfobox bankinfobox">'
				+'<p>银行卡'+(bankIndex++)+'</p>'
				+'<ul class="proinfolist agent_list">'
					+'<li>'
						+'<div class="settfrom">'
       					+'<label><i>*</i>账户名称</label>'
       					+'<input type="text" id="card_name" name="tradeBanks['+(blist)+'].card_name" datatype="s2-16" nullmsg="请输入您的银行账户名称！" errormsg="账户名称至少2个字符,最多16个字符！"/>'
       				+'<div class="info">'+'<span class="Validform_checktip">账户名称至少2个字符,最多16个字符</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
       				+'</div>'
					+'</li>'
					+'<li>'
       				+'<div class="settfrom">'
       					+'<label><i>*</i>银行卡卡号</label>'
       					+'<input type="text"  id="card_nos" name="tradeBanks['+(blist)+'].card_nos" datatype="hh" nullmsg="请输入您的银行卡卡号！" errormsg="输入的银行卡卡号银行格式不对！卡号16位数,或者19位数！" />'
           				+'<div class="info">'+'<span class="Validform_checktip">输入的银行卡卡号银行格式不对！卡号16位数,或者19位数！</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'

       					+'</div>'
       			+'</li>'
       			+'<li>'
       				+'<div class="settfrom">'
       					+'<label><i>*</i>开户银行</label>'
       					+'<select name="tradeBanks['+(blist)+'].bank_name" datatype="*" nullmsg="请选择开户银行" >'
		                   +'<option value="">请选择</option>'
		                   +'<option value="中国银行">中国银行</option>'
		                   +'<option value="招商银行">招商银行</option>'
		                   +'<option value="农业银行">农业银行</option>'
		                 +'</select>'
	           				+'<div class="info">'+'<span class="Validform_checktip">请选择开户银行</span>'+'<span class="dec">'+'<s class="dec1">&#9670;</s>'+'<s class="dec2">&#9670;</s>'+'</span>'+'</div>'
		                 +'</div>'
       			+'</li>'
				+'</ul>'
				+'<a class="bankdelbtn" onclick="bankDel(this);" href="javascript:;">删除银行卡</a>'
			+'</div>';
    	$(this).parents('.agentbox').append(html);
    	if($('.bankinfobox').length>1){
    		$('.bankdelbtn').show();
    	}
    });
    
    function agentInfoDel(obj){
    	$(obj).parents('.agentinfobox').remove();
    }
    
    function bankDel(obj){
    	if($('.bankinfobox').length==2){
    		$(obj).parents('.agentinfobox').siblings('.agentinfobox').find('a').hide();
    		$(obj).parents('.agentinfobox').remove();
    	}else if($('.bankinfobox').length==1){
    		$(obj).hide();
    	}else{
    		$(obj).parents('.agentinfobox').remove();
    	}
    	
    }
    
    $(function(){
    	if($('.bankinfobox').length==1){
    		$('.bankdelbtn').hide();
    	}
    });
    
    $(function(){
		$("#reviewForm").Validform({
			/* btnSubmit:"#add-detail", 
			label:".label",
			showAllError:true, */
			tiptype:function(msg,o,cssctl){
				if(!o.obj.is("form")){
					var objtip=o.obj.siblings().find(".Validform_checktip");
					cssctl(objtip,o.type);
					objtip.text(msg);
					
					var infoObj=o.obj.siblings(".info");
					if(o.type==2){
						infoObj.fadeOut(200);
					}else{
						if(infoObj.is(":visible")){return;}
						var left=o.obj.offset().left,
							top=o.obj.offset().top;
		
						infoObj.css({
							left:left+o.obj.width()-50,
							top:top-45
						}).show().animate({
							top:top-35	
						},200);
					}
				}
				
			},
			datatype:{//传入自定义datatype类型，可以是正则，也可以是函数（函数内会传入一个参数）;
				"ss": /^[^\s]{6,20}$/,
				"xx" : /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/,
				"hh":/^\d{16}|\d{19}$/
			},
			addRule:{
				"need2":function(gets,obj,curform,regxp){
					var need=1,
						numselected=curform.find("input[name='"+obj.attr("name")+"']:checked").length;
					return  numselected >= need ? true : "请至少选择"+need+"项！";
				}
			}
			}
		});
	});
	
  </script>
  
</body>
</html>
