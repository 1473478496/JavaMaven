<!DOCTYPE html>
<html>
<head>
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE">
	<meta content=always name=referrer>
	<meta name="viewport" content="width=device-width"/>
	<title>商家中心</title>	
	<link type="text/css" rel="stylesheet" href="${base}/resources/css/style.css"/>
	<script src="${base}/resources/js/jquery-1.8.3.min.js"></script>
	<script src="${base}/resources/js/list.js"></script>
	<script src="${base}/resources/datepicker/WdatePicker.js"></script>
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
            <li><a href="#">商品管理</a><span>></span></li>
            <li><a href="#">商品列表</a></li>
          </ul>
          <!--商品管理-->
          
          <div>
            <div class="order-control">
              <h4>商品查询</h4>
              <div class="form-container">
               <form action="/mer/merchant/product/list.do" class="order-control-form" name="listForm" method="get">
              	<div class="oh order_selbox">
	                <p>
	                  <label for="productId">商品编号</label><input type="text" id="productId" name="productId" value="${productId}"/>
	                </p>
	                <p>
	                   <label>商品分类</label>
						<select id="productCategory" name="productCategory">
		                   <option value="">请选择分类</option>
		                   [#list productCategoryTree as productCategory]
		                   <option value="${productCategory.id}" [#if productCategory.id==productCategoryId]selected="selected" [/#if]>${productCategory.name}</option>
		                   [/#list]
		                 </select>
		             </p>
		             <p>
	                   <label>商品品牌</label>
						<select id="brand" name="brand">
		                   <option value="">请选择品牌</option>
		                   [#list brands as brand]
		                   <option value="${brand.id}" [#if brand.id==brandId]selected="selected"[/#if]>${brand.name}</option>
		                   [/#list]
		                 </select>
		             </p>
		             <p>
	                   <label>商品状态</label>
						<select id="isMarketable" name="isMarketable">
		                   <option value="">全部</option>
		                   <option value="true" [#if isMarketable=="true"]selected="selected"[/#if]>已上架</option>
		                   <option value="false" [#if isMarketable=="false"]selected="selected"[/#if]>已下架</option>
		                 </select>
		             </p>
		             <p>
	                  <label for="ordered-time-star">创建时间</label>
	                  <input type="text" id="ordered-time-star" class="Wdate" name="ordered-time-star" value=""/>
	                  —<input type="text" id="ordered-time-end" class="Wdate" name="ordered-time-end" value=""/>
	                </p>
	                <div class="orderbtnbox">
	                  <input type="submit" value="查询"/>
	                  <input type="reset" class="order_btnj"  value="重置"/>
	                </div>
	              </div>
             </form>
              </div>
            </div>
            <div class="order-l-content">
              <div class="ordstu-nav">
                <a href="#">添加商品</a>
                <a href="#" class="o-active">删除</a>
                <a href="#" class="o-active">上架</a>
                <a href="#" class="o-active">下架</a>
                <p class="pg-limit">
                  <span>每页显示</span> 
                  <select name="limit-cot" id="limit-cot">
                    <option value="">20条</option>
                    <option value="">30条</option>
                  </select>
                </p>
              </div>
              <div class="order-list">
                <table class="order-list-tb prolisttable"> 
                   <col width="12%" />
  				   <col width="33%" />
  				   <col width="10%" />
  				   <col width="8%" />
  				   <col width="10%" />
  				   <col width="8%" />
  				   <col width="10%" />
                  <thead>
                    <tr>
                      <th><input type="checkbox">商品编号</th>
                      <th>商品名称</th>
                      <th>商品分类</th>
                      <th>库存</th>
                      <th>销售价</th>
                      <th>是否上架</th>
                      <th>创建时间</th>
                      <th class="ord-stu">操作</th>
                    </tr>
                  </thead>
                  <tbody>
                  [#list page.content as product]
                    <tr>
                      <td><input type="checkbox" name="ids" value="${product.id}"/>${product.sn}</td>
                      <td><a href="#">${product.name}</a></td>
                      <td>${product.productCategory.name}</td>
                      <td>${product.stock}</td>
                      <td>${product.price}</td>
                      <td>
                      	<!--点击可以设置商品上下架  下架的商品添加on样式-->
                      	<span class="prouplode ${product.isMarketable?string("", "on")}"></span>
                      </td>
                      <td>${product.createDate}</td>
                      <td><a href="/mer/merchant/product/edit.do?id=${product.id}" >编辑</a></td>
                    </tr>
                    [/#list]
                  </tbody>
                </table>
              </div>
             [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]  
			 [#include "/common/pagination.ftl"]
			  [/@pagination] 
            </div>
          </div>
          
      </div>
    </div>
  </div>
      <!-- 底部 -->
	  [#include "/common/footer.ftl"]
 
	  <script>
	    $("#ordered-time-star").click(function(){
			WdatePicker();
			});
	    $("#ordered-time-end").click(function(){
			WdatePicker();
			});
	   
	  </script>
</body>
</html>