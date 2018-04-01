<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商户信息</title>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/webuploader.css" />
     <link rel="stylesheet" href="${base}/resources/css/style.css">
   
</head>

<body>
    <!-- 引入头部文件 -->
   [#include "/common/header.ftl"/]
  
  <div class="wrap">
    <div class="container-setting fix">
    
	  <!-- 菜单 -->
      [#include "/common/menu.ftl"/] 
      
      <div class="container-middle">
		 <ul class="b-nav">
            <li><a href="#">商户管理</a><span>></span></li>
            <li><a href="#">商户信息</a></li>
          </ul>

		 <div class="order-control shopermanges">
              <h4>商户信息</h4>

			  <div class="form-container">
			    <div class="cooperation-pro">
			      <div class="cop-info">
			   		<div class="order-control">
			           <h3 class="proimgtit">基本信息</h3>
			           <div class="proinfo">
			           	<form id="reviewForm"  action="${base }/trade/apply_update.do" method="post">
			           		<!--商家基本信息-->
			           		<ul class="proinfolist">
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>中文名</label>
			           					<input type="text"  value="${trade.name}" readonly/>
			           					<input type="hidden" name="tradeId"   value="${trade.id}"/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>越南名</label>
			           					<input type="text" value="${trade.alias_Name}" readonly/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>证件类型</label>
			           					[#if trade.cert_type=="identityCard" ]
			           					<input type="text"   value="身份证"  readonly/>
			           					 	 [#elseif trade.cert_type=="passport"]
			           					 	 <input type="text"  value="护照"  readonly/>
			           					 	 [#else]
			           					 	 <input type="text"   value="军官证"  readonly/>
			           					   [/#if] 
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>证件号码</label>
			           					<input type="text"   value="${trade.cert_nos}"  readonly/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>性别</label>
			           					[#if trade.sex=="female" ]
			           					 <input type="text"  value="女"   readonly/>
			           					 [#else]
			           					  <input type="text" value="男" readonly/>
			           					 [/#if] 
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>手机号码</label>
			           					<input type="text" value="${trade.mobiles}" readonly/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label>生日</label>
			           					<input type="text" id="birthDay" value="${trade.birthday}" readonly/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label>住址</label>
			           					<input type="text"  value="${trade.addr}" readonly/>
			           				</div>
			           			</li>
			           		</ul>
			           		
			           		<!-- 签约信息 -->
			           		<h3 class="proimgtit">签约信息</h3>
			           		<ul class="proinfolist contract_list">
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>商铺号</label>
			           					<input type="text"    value="${trade.shop_no}" readonly disabled/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>协议起止时间</label>
			           					<input type="text" id="beginDay" class="daytime"    value="${trade.end_date}" readonly/> — <input value="${trade.beg_date}" readonly type="text" class="daytime" id="endDay" name="trade.end_date" />
			           				</div>
			           			</li>
			           			<li>
			           				<div class="settfrom">
			           					<label><i>*</i>商户类型</label>
			           					<div class="busnistyle">
			           					[#list categorylist as a ]
	           								<label><input type="checkbox" readonly name="dd" value="${a.id}" >${a.name}</label>
	           							[/#list]
										</div>
			           				</div>
			           			</li>
			           		</ul>
			           		
			           		<!-- 商家资质 显示营业执照和法人身份证照片 -->
			           		<h3 class="proimgtit">商家资质</h3>
			           		<div class="business_img">
			           			<div class="settfrom">
			      					<label><i>*</i>营业执行与法人身份证</label>
				               		<span class="shopimages">
				               			<a class="fancybox" href="images/list_good_01.jpg" data-fancybox-group="gallery"><img src="${base}${trade.group_photo}" alt="" /></a>
										<a class="fancybox" href="images/sh_logo.jpg" data-fancybox-group="gallery"><img src="${base}${trade.cert_photos}" alt="" /></a>
				               		</span>
									
			      				</div>
			           		</div>
			           		
			           		<!-- 代理人可以不填 不必须 -->
			           		<h3 class="proimgtit">代理人信息</h3>
			           		<div class="agentbox">
			           			<!-- 添加代理人模块 -->
			           			<div class="orderbtnbox">
			           				<input type="button" class=" order_btnj agentbtn" value="添加代理人"/>
			           			</div>
			           			<!-- 添加代理人模块-->
			       				[#list tradeProxySet as tradProxyst]
				       			<div class="agentinfobox bankinfobox">
				       				<p>代理人1</p>
				       				<ul class="proinfolist agent_list">
				       					<li>
				       						<div class="settfrom">
				         					<label><i>*</i>代理人姓名</label>
				         					<input type="text" value="${tradProxyst.dname}" readonly/>
				         					<input type="hidden" id="ids" value="${tradProxyst.id}" />
				         				</div>
				       					</li>
				       					<li>
					         				<div class="settfrom">
					         					<label><i>*</i>手机号码</label>
					         					<input type="text" value="${tradProxyst.mobilet}" readonly />
					         				</div>
					         			</li>
					         		<li>
		           				<div class="settfrom">
		           					<label><i>*</i>证件类型</label>
					                   [#if tradProxyst.cert_types=="identityCard" ]
			           					<input type="text"   value="身份证"  readonly/>
			           					 	 [#elseif tradProxyst.cert_types=="passport"]
			           					 	 <input type="text"   value="护照"  readonly/>
			           					 	 [#else]
			           					 	 <input type="text"    value="军官证"  readonly/>
			           					   [/#if] 
		           				</div>
		           			</li>
					        <li>
		           				<div class="settfrom">
		           					<label><i>*</i>证件号码</label>
		           						<input type="text"  value="${tradProxyst.cert_not}" readonly/>
		           							</div>
		                    
		           					</li>
				       				</ul>
				       				<a class="bankdelbtn" onclick="bankDel(this);" href="${base}/trade/apply_update.do?proxyId=${tradProxyst.id}">删除代理人信息</a>
				       			</div>
				       				[/#list]
			           		</div>
			           		
			           		<!-- 银行信息至少填一个 -->
			       			<h3 class="proimgtit">银行信息</h3>
			       			<div class="agentbox">
			       				<div class="orderbtnbox">
			           				<input type="button" class="order_btnj bankbtn" value="添加银行卡"/>
			           			</div>
			       				<!-- 添加银行卡模块 -->
			       				[#list tradeBanksSet as tradeBanks]
				       			<div class="agentinfobox bankinfobox">
				       				<p>银行卡1</p>
				       				<ul class="proinfolist agent_list">
				       					
				       					<li>
				       						<div class="settfrom">
				         					<label><i>*</i>账户名称</label>
				         					<input type="text" value="${tradeBanks.card_name}" readonly />
				         					<input type="hidden" id="ids" value="${tradeBanks.id}" />
				         				</div>
				       					</li>
				       					<li>
					         				<div class="settfrom">
					         					<label><i>*</i>银行卡卡号</label>
					         					<input type="text" value="${tradeBanks.card_nos}" readonly/>
					         				</div>
					         			</li>
					         			<li>
					         				<div class="settfrom">
					         					<label><i>*</i>开户银行</label>
					         					<select >
								                   <option >${tradeBanks.bank_name}</option>
								                 </select>
								                 
					         				</div>
					         		
					         			</li>
					         			
				       				</ul>
				       				<a class="bankdelbtn" onclick="bankDel(this);" href="${base}/trade/apply_update.do?banksId=${tradeBanks.id}">删除银行卡</a>
				       			</div>
				       				[/#list]
			           		</div>
			           		  
			           		<!-- 备注 -->
			           		<ul class="proinfolist ">
			           			<li >
			           				<div class="settfrom">
			           					<label>备注</label>
			           					<input type="text" value="${trade.remarkv}" readonly />
			           				</div>
			           			</li>
			           		</ul>
			           		
			           		<div class="agreebox">
			   					<label><input type="checkbox" disabled name="" value="${trade.argee}" checked="checked"><a href="#">同意商家合作协议</a></label>
			   				</div>
			           		
			           		<!-- 按钮 -->
			           		<div class="orderbtnbox probtnbox">
				                <input type="submit" value="保存" id="updateForm"/>
				                <input type="reset" class="order_btnj"  value="取消"/>
				            </div> 
				            
			           	</form>
			           </div>
			         </div>
			            	
			      
			      </div>
			    </div>
			  </div>
  
         </div>
        
       </div>
    </div>
  </div>
   <!--脚部-->
    [#include "/common/footer.ftl"]
    
     <script src="${base}/resources/datepicker/WdatePicker.js"></script>
  <script src="${base}/resources/js/cookie.js"></script>
    <script src="${base}/resources/js/Validform.min.js"></script>
         <script src="${base}/resources/js/upload.js"></script>
       <script src="${base}/resources/js/upload_apply.js"></script>
 
  
  <script>
  
  $(document).ready(function() {
	 	$('.fancybox').fancybox({
			nextEffect:'fade',
			prevEffect:'fade',
		});
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
			}
		});
	});
   
  </script>
  
</body>
</html>
