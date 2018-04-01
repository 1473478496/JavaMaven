<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>订单详情</title>
<link rel="stylesheet" href="${base}/resources/css/style.css">
<script src="${base}/resources/js/jquery-1.8.3.min.js"></script>
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
            <li><a href="#">订单管理</a><span>></span></li>
            <li><a href="#">订单详情</a></li>
          </ul>
          <!--结算申请-->
          <div>
            <div class="order-control">
                <h4>订单详情</h4>
				<div class="settle_container">
					<div class="settle_left">
						<table class="settle_table" width="100%"> 
		                   <col width="20%" />
		  				   <col width="30%" />
		  				   <col width="20%" />
		                  <tbody>
		                    <tr>
		                      <td class="sl_bg">订单编号</td>
		                      <td>${order.sn}</td>
		                      <td class="sl_bg">互市地点</td>
		                      <td>东兴</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">提交时间</td>
		                      <td>${order.createDate}</td>
		                      <td class="sl_bg">贸易类型</td>
		                      <td>进口</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">顾客姓名</td>
		                      <td>${order.member.name}</td>
		                      <td class="sl_bg">结算单号</td>
		                      <td>${balance.balanceNo}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">国籍</td>
		                      <td>中国</td>
		                      <td class="sl_bg">结算流水号</td>
		                      <td>${balance.serialNo}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">证件名称</td>
		                      [#if trade.cert_type.ordinal()?? && trade.cert_type.ordinal()==0]
		                      <td>身份证</td>
		                      [/#if]
		                      [#if trade.cert_type.ordinal()?? && trade.cert_type.ordinal()==1]
		                      <td>护照</td>
		                      [/#if]
		                      [#if trade.cert_type.ordinal()?? && trade.cert_type.ordinal()==2]
		                      <td>军官证</td>
		                      [/#if]
		                      <td class="sl_bg">经营户姓名</td>
		                      <td>${trade.name}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">证件号码</td>
		                      <td>${trade.cert_no}</td>
		                      <td class="sl_bg">商铺号</td>
		                      <td>${trade.shop_no}</td>
		                    </tr>
		                  </tbody>
		                </table>
					</div>
					<div class="settle_right">
					    [#if order.orderStatus.ordinal()??&& order.orderStatus.ordinal()==3]
						<p class="settle_title">订单状态:<i>已支付</i></p>
						[/#if]
						<p class="p_reson">交易方式：${order.paymentMethodName}</p>
						[#if balance.status.ordinal()??&& balance.status.ordinal()==0]
						<p class="p_reson">结算状态：待审核</p>
						[/#if]
						[#if balance.status.ordinal()??&& balance.status.ordinal()==1]
						<p class="p_reson">结算状态：审核通过</p>
						[/#if]
						[#if balance.status.ordinal()??&& balance.status.ordinal()==2]
						<p class="p_reson">结算状态：审核拒绝</p>
						[/#if]
						[#if balance.status.ordinal()??&& balance.status.ordinal()==3]
						<p class="p_reson">结算状态：结算成功</p>
						[/#if]
						[#if balance.status.ordinal()??&& balance.status.ordinal()==4]
						<p class="p_reson">结算状态：结算失败</p>
						[/#if]
					</div>
				</div>
				
				<div class="settle_control">
	              <div class="order-list settlescroll">
	               <table class="order-list-tb"> 
	                   <col width="10%" />
	  				   <col width="35%" />
	  				   <col width="15%" />
	  				   <col width="15%" />
	  				   <col width="10%" />
	                  <thead>
	                    <tr>
	                      <th></th>
	                      <th>商品名称</th>
	                      <th>产地</th>
	                      <th>单价</th>
	                      <th>购买数量</th>
	                      <th>商品金额</th>
	                    </tr>
	                  </thead>
	                  <tbody>
	                  [#list orderItems as orderItem]
	                    <tr>
	                    	<!-- 点击图片与商品名称可以查看商品详细 -->
	                      <td><a href="#"><img src="${base}/resources/images/list_good_01.jpg" class="order_img" /></a></td>
	                      <td><a href="#">${orderItem.name}</a></td>
	                      <td>越南</td>
	                      <td>${orderItem.price}</td>
	                      <td>${orderItem.quantity}</td>
	                      <td>${orderItem.payPrice}</td>
	                    </tr>
	                   [/#list]
	                    
	                  </tbody>
	                </table>
	              </div>
	              
	               <div class="slcomfir_info">
	                  	<!-- 结算总金额 -->
	              		<p class="settletit"><span>订单总金额<em>${order.amountPaid}</em>元</span></p>
	              		<p class="settletit"><span>实付   ${order.offsetAmount}  元</span></p>
	              		
	              		<!-- 如果是点击修改价格按钮,出现-金额变成可修改 
	              		<p class="settletit"><span>订单总金额<input type="text" class="order_input" value="256"/>元</span></p>
	              		
	              		<!--点击修改出现   按钮 取消按钮返回之前页面
		              	<div class="orderbtnbox">
		                  <input type="submit" value="确认修改">
		                  <input type="button" class="order_btnj" value="取消">
		                </div>
		                -->
	              	</div>
	            </div>
	             
            </div>
          </div>
      </div>
    </div>
  </div>

  	<!-- 底部 -->
	  [#include "/common/footer.ftl"]

</body>
</html>
