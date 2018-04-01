<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>结算确认</title>
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
            <li><a href="#">结算管理</a><span>></span></li>
            <li><a href="#">结算申请</a><span>></span></li>
            <li><a href="#">结算确认</a></li>
          </ul>
          <!--结算申请-->
           <form action="${base}/settle/settleSave.do" method="post">
            <input type="hidden"  name="totalAmt" value="${sum}" />
            <input type="hidden"  name="commission" value="${commissionSum}" />
            
            <div class="order-control settlecomfirm">
              <h4>结算确认信息</h4>
              <div class="form-container">
              	<p class="settletit">已选<i class="settnum">${num}</i>笔订单 <span>结算总金额<em>${sum}</em>元</span></p>
              	<div class="order-list settlescroll">
	                <table class="order-list-tb"> 
	                   <col width="20%" />
	  				   <col width="20%" />
	  				   <col width="20%" />
	  				   <col width="20%" />
	                  <thead>
	                    <tr>
	                      <th>订单号</th>
	                      <th>下单时间</th>
	                      <th>买家账号</th>
	                      <th>买家姓名</th>
	                      <th>商品总额</th>
	                    </tr>
	                  </thead>
	                  <tbody>
	                   [#list orderList as order]
	                    <tr>
	                    	<!-- 点击订单号可以查看订单详细  订单可以一次全导出来 通过样式控制超出最大高度显示滚动条-->
	                      <td><a href="#" class="see_details">${order.sn}</a></td>
	                      <td><span>${order.createDate}</span> <span>16:00:03</span></td>
	                      <td>${order.member.username}</td>
	                      <td>${order.member.name}</td>
	                      <td>¥${order.amountPaid}</td>
	                    </tr>
	                   [/#list]
	                  
	                  </tbody>
	                </table>
	              </div>
              	<!-- 结算人信息，结算银行信息 ,2个选项没有关联关系-->
              	<div class="slcomfir_info">
              		<div class="confirmone">
              			<span>结算人：</span>
              			<select name="operating">
              				<option value="">请选择</option>
              				[#list tradeBanks as tb]/
		                    <option value="${tb.card_name}">${tb.card_name} - ${tb.cert_no}</option>
		                    [/#list]
		                </select>
                  	</div>
                  	<div class="confirmone">
              			<span>结算银行信息：</span>
              			<select name="cardNo">
              				<option value="">请选择</option>
              				[#list tradeBanks as tb]
		                    <option value="${tb.card_no}">${tb.card_name} - ${tb.card_nos}</option>
		                    [/#list]
		                </select>
                  	</div>
                  	<!-- 结算总金额 -->
              		<p class="settletit"><span >结算总金额<em>${sum}</em>元</span></p>
              		<p class="settletit"><span >订单佣金<em>${commissionSum}</em>元</span></p>
              		
              		<!-- 按钮 取消按钮返回之前申请页面-->
	              	<div class="orderbtnbox">
	                  <input type="submit" value="确认提交">
	                  <input type="button" class="order_btnj" value="取消">
	                </div>
              	</div>
              	
              </div>
            </div>
          </form>
      </div>
    </div>
  </div>

  	 <!-- 底部 -->
	  [#include "/common/footer.ftl"]
	 

</body>
</html>
