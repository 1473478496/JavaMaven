 [#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
 <div class="aside-nav">
  <h3>我的应用</h3>
  <div class="li-container">
    <ul>
     <li class="category">
       <h4 class="category-name tra-01">店铺管理</h4>
       <ol class="catgory-app">
          [@shiro.hasPermission name="shop:overview"]
	         <li class="common-app">
	           <a href="${base}/tradeShop/shop_scan.do" class="on">店铺总览</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="shop:set"]
	         <li class="common-app">
	           <a href="${base}/tradeShop/shop_setting.do">店铺设置</a>
	         </li>
	      [/@shiro.hasPermission]
       </ol>
     </li>
     <li class="category">
       <h4 class="category-name tra-sp">商户管理</h4>
       <ol class="catgory-app">
              <li class="common-app">
                <a href="${base}/trade/apply_info.do">商户信息</a>
              </li>
              <li class="common-app">
                <a href="#">消息中心</a>
              </li>
       </ol>
     </li>
     <li class="category">
       <h4 class="category-name tra-02">交易管理</h4>
       <ol class="catgory-app">
	      [@shiro.hasPermission name="transaction:order"]
	         <li class="common-app">
	           <a href="${base}/orderItem/list.do">订单管理</a>
	         </li>
	      [/@shiro.hasPermission]
	      <!-- [@shiro.hasPermission name="transaction:return"]
	         <li class="common-app">
	           <a href="#">退货管理</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="transaction:delivery"]
	         <li class="common-app">
	           <a href="#">发货管理</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="transaction:pay"]
	         <li class="common-app">
	           <a href="#">支付管理</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="transaction:courier"]
	         <li class="common-app">
	           <a href="#">快递管理</a>
	         </li>
	      [/@shiro.hasPermission] -->
       </ol>
     </li>
     <li class="category">
       <h4 class="category-name tra-03">商品管理</h4>
       <ol class="catgory-app">
          [@shiro.hasPermission name="product:list"]
          <li class="common-app">
               <a href="#">商品列表</a>
          </li>
          [/@shiro.hasPermission]
	      <!-- [@shiro.hasPermission name="product:publish"]
	         <li class="common-app">
	           <a href="#">商品发布</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="product:classify"]
	         <li class="common-app">
	           <a href="#">商品分类</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="product:property"]
	         <li class="common-app">
	           <a href="#">商品属性</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="product:import"]
	         <li class="common-app">
	           <a href="#">商品导入</a>
	         </li>
	      [/@shiro.hasPermission]-->
       </ol>
     </li>
     <li class="category">
             <h4 class="category-name tra-end">结算管理</h4>
             <ol class="catgory-app">
               <li class="common-app">
                 <a href="${base}/settle/index.do">结算总汇</a>
               </li>
               <li class="common-app">
                 <a href="${base}/settle/query.do">结算列表</a>
               </li>
               <li class="common-app">
                 <a href="${base}/settle/list.do">结算申请</a>
               </li>
             </ol>
           </li>
     <li class="category">
       <h4 class="category-name tra-04">广告管理</h4>
       <ol class="catgory-app">
	      [@shiro.hasPermission name="ad:home"]
	         <li class="common-app">
	           <a href="#">首页广告</a>
	         </li>
	      [/@shiro.hasPermission]
	      [@shiro.hasPermission name="ad:page"]
	         <li class="common-app">
	           <a href="#">内页广告</a>
	         </li>
	      [/@shiro.hasPermission]
       </ol>
     </li>
    </ul>
  </div>
</div>