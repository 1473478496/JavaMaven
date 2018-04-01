<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>结算详情</title>
<link rel="stylesheet" href="${base }/resources/css/style.css">
<script src="${base }/resources/js/jquery-1.8.3.min.js"></script>
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
            <li><a href="#">结算管理</a><span>></span></li>
            <li><a href="#">结算单详情</a></li>
          </ul>
          <!--结算申请-->
          <div>
            <div class="order-control">
                <h4>结算详情</h4>
				<div class="settle_container">
					<div class="settle_left">
						<table class="settle_table" width="100%"> 
		                   <col width="20%" />
		  				   <col width="30%" />
		  				   <col width="20%" />
		                  <tbody>
		                    <tr>
		                      <td class="sl_bg">结算编号</td>
		                      <td></td>
		                      <td class="sl_bg">结算单号</td>
		                      <td>${balance.balanceNo}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">提交时间</td>
		                      <td>${balance.createDate}</td>
		                      <td class="sl_bg">结算流水号</td>
		                      <td>${balance.serialNo}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">经营户姓名</td>
		                      <td>${trade.name}</td>
		                      <td class="sl_bg">结算银行账户</td>
		                      <td>${balance.cardName}</td>
		                    </tr>
		                    <tr>
		                      <td class="sl_bg">结算人</td>
		                      <td>${balance.operating}</td>
		                      <td class="sl_bg">结算银行账号</td>
		                      <td>${balance.cardNo}</td>
		                    </tr>
		                  </tbody>
		                </table>
					</div>
					<div class="settle_right">
					  [#if balance.status.ordinal()?? && balance.status.ordinal()==0]
						<p class="settle_title">结算状态:<i>待审核</i></p>
					  [/#if]
					  [#if balance.status.ordinal()?? && balance.status.ordinal()==1]
						<p class="settle_title">结算状态:<i>审核通过</i></p>
					  [/#if]
					  [#if balance.status.ordinal()?? && balance.status.ordinal()==2]
						<p class="settle_title">结算状态:<i>审核拒绝</i></p>
						<!-- 有就显示，没有就不显示，拒绝原因，失败原因 -->
						<p class="p_reson">拒绝原因：原因拒绝原因原因拒绝原因原因拒绝原因原因</p>
					  [/#if]
					  [#if balance.status.ordinal()?? && balance.status.ordinal()==3]
						<p class="settle_title">结算状态:<i>结算成功</i></p>
					  [/#if]	
					  [#if balance.status.ordinal()?? && balance.status.ordinal()==4]
						<p class="settle_title">结算状态:<i>结算失败</i></p>
						<!-- 有就显示，没有就不显示，拒绝原因，失败原因 -->
						<p class="p_reson">失败原因：原因拒绝原因原因拒绝原因原因拒绝原因原因</p>
					  [/#if]	
					  	
					</div>
				</div>
				
				<div class="settle_control">
	              <div class="order-list settlescroll">
	                <table class="order-list-tb"> 
	                   <col width="15%" />
	  				   <col width="15%" />
	  				   <col width="15%" />
	  				   <col width="15%" />
	  				   <col width="15%" />
	  				   <col width="15%" />
	                  <thead>
	                    <tr>
	                      <th>订单号</th>
	                      <th>下单时间</th>
	                      <th>买家账号</th>
	                      <th>买家姓名</th>
	                      <th>商品总额</th>
	                      <th>状态</th>
	                      <th>交易方式</th>
	                    </tr>
	                  </thead>
	                  <tbody>
	                     [#list orderList as order]
	                    <tr>
	                    	<!-- 点击订单号可以查看订单详细 -->
	                      <td><a href="${base}/settle/orderDetail.do?id=${order.id}" class="see_details">${order.sn}</a></td>
	                      <td><span>${order.createDate}</span> <span>16:00:03</span></td>
	                      <td>${order.member.username}</td>
	                      <td>${order.member.name}</td>
	                      <td>¥${order.amountPaid}</td>
	                       [#if order.orderStatus.ordinal()?? && order.orderStatus.ordinal()==3]
	                      <td class="stu-tre"><span class="stu-l">已支付</span><a href="${base}/settle/orderDetail.do?id=${order.id}" class="orddetialbtn">订单详情</a></td>
	                       [#else]
                          <td class="stu-tre"></td>
                           [/#if]
	                     <td>${order.paymentMethodName}</td>
	                    </tr>
	                    [/#list]
	                  
	                  </tbody>
	                </table>
	              </div>
	              
	              
	               <div class="slcomfir_info">
	                  	<!-- 结算总金额 -->
	              		<p class="settletit"><span>结算单总金额<em>${balance.totalAmt}</em>元</span></p>
	              		[#if balance.status.ordinal()?? && balance.status.ordinal()==3]
	              		<p class="settletit"><span>未结算金额   0  元</span></p>
	              		[#else]
	              		<p class="settletit"><span>未结算金额  ${balance.totalAmt} 元</span></p>
	              		[/#if]
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
