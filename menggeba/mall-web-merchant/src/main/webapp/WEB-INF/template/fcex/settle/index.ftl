<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>结算总汇</title>
<link rel="stylesheet" href="${base }/resources/css/style.css">
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
            <li><a href="#">结算总汇</a></li>
          </ul>
          
          <!--结算总汇-->
          <div class="order-control settlemain">
              <h4>结算总汇</h4>
              <div class="settle_see">
              	请选择查看期限 
              	<select>
                  <option value="">今日</option>
                  <option value="">一周</option>
                  <option value="">一个月</option>
                  <option value="">一年</option>
                </select>
              </div>
              <div class="income-view zl-s">
                <h5>已结算</h5>
                <div>
                  <p class="zh_color1"><i>已结算订单数</i><span>524555</span></p>
                  <p class="zh_color2"><i>已结算金额</i><span>5555元</span></p>
                </div>
              </div>
              <div class="income-view zl-s">
                <h5>待提交</h5>
                <div>
                  <p class="zh_color1-1"><i>已支付订单数</i><span>524555</span></p>
                  <p class="zh_color1-2"><i>已支付金额</i><span>5555元</span></p>
                </div>
              </div>
              <div class="income-view zl-s">
                <h5>待审核</h5>
                <div>
                  <p class="zh_color1-3"><i>待审核结算订单数</i><span>524555</span></p>
                  <p class="zh_color1-4"><i>待审核结算金额</i><span>5555元</span></p>
                </div>
              </div>
          </div>
          
      </div>
    </div>
  </div>
  
  <!--脚部-->
  [#include "/common/footer.ftl"]

</body>
</html>
