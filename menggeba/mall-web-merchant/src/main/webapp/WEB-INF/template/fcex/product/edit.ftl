<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品管理-商品编辑</title>
<link rel="stylesheet" type="text/css" href="${base}/resources/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/css/style.css">

<script src="${base}/resources/js/jquery-1.8.3.min.js"></script>
</head>


<body>
  
<!-- 需要引入头部公共文件 -->
 [#include "/common/header.ftl"/] 
  <div class="wrap">
    <div class="container-setting fix">
      <!-- 需要引入导航公共文件 -->
      [#include "/common/menu.ftl"/] 
       
      <div class="container-middle">
          <ul class="b-nav">
            <li><a href="#">商品管理</a><span>></span></li>
            <li><a href="#">商品编辑</a></li>
          </ul>
          <!--商品管理-编辑、发布-->
            <div class="order-control">
              <h4>商品信息</h4>
              <div class="proinfo">
              	<form>
              		<!--商品基本信息-->
              		<ul class="proinfolist">
              			<li>
              				<div class="settfrom">
              					<label>商品分类<i>*</i></label>
              					<select id="productCategoryId" name="productCategoryId">
              					[#list productCategoryTree as productCategory]
				                   <option value="${productCategory.id}" [#if productCategory == product.productCategory] selected="selected"[/#if]>
		                            ${productCategory.name}
				                   </option>
				                   [/#list]
				                 </select>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>商品品牌</label>
              					<select>
				                   <option value="">请选择...</option>
				                   [#list brands as brand]
				                   <option value="${brand.id}" [#if brand == product.brand] selected="selected" [/#if]>
				                     ${brand.name}
				                   </option>
				                   [/#list]
				                 </select>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>商品名称<i>*</i></label>
              					<input type="text" name="name" value="${product.name}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>商品编号<i>*</i></label>
              					<input type="text" name="sn" value="${product.sn}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>市场价</label>
              					<input type="text" name="marketPrice" value="${product.marketPrice}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>销售价<i>*</i></label>
              					<input type="text" name="price" value="${product.price}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>成本价</label>
              					<input type="text" name="cost" value="${product.cost}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>库存<i>*</i></label>
              					<input type="text" name="stock" value="${product.stock}"/>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              				<label>设置</label>
              					<label>
								<input type="checkbox" [#if product.isMarketable] checked="checked" [/#if]>是否上架
								</label>
								<label>
					    	     <input type="checkbox" id="proGGe"  checked="checked">商品规格
							   </label>
              				</div>
              			</li>
              			<li>
              				<div class="settfrom">
              					<label>备注</label>
              					<input type="text" name="memo" value="${product.memo}"/>
              				</div>
              			</li>
              		</ul>
              		<!--商品规格-->
              		<div class="proinfo_ggbox" style="display: none;">
	              		<h3 class="proimgtit">商品规格</h3>
	              		<div class="prorulebox">
	              				<p>请选择商品规格</p>
	              				<ul class="proinfoguige">
														<li>
															<label>
																<input type="checkbox" name="specificationIds" value="1" checked>颜色
															</label>
														</li>
														<li>
															<label>
																<input type="checkbox" name="specificationIds" value="2" checked>音响颜色
															</label>
														</li>
														<li>
															<label>
																<input type="checkbox" name="specificationIds" value="3">Hellokitty
															</label>
														</li>
														<li>
															<label>
																<input type="checkbox" name="specificationIds" value="4" checked>膳魔师
															</label>
														</li>
												</ul>
												
												<!--当选择了规格时候 显示proinfovalue区域  没有勾选不显示-->
												<div class="proinfovalue">
														<div class="ordstu-nav">
							                <a href="javascript:;" id="addSpecificationProduct" class="button">添加规格值</a>
							              </div>
													  <table class="order-list-tb prolisttable" id="proTablelist"> 
						                  <thead>
						                    <tr>
						                      <th>颜色</th>
						                      <th>音响颜色</th>
						                      <th>膳魔师</th>
						                      <th>操作</th>
						                    </tr>
						                  </thead>
						                  <tbody>
						                    <tr>
						                      <td>
						                      	<select>
									                    <option value="">对应的规格值</option>
									                  </select>
						                      </td>
						                      <td>
						                      	<select>
									                    <option value="">对应的规格值</option>
									                  </select>
						                      </td>
						                      <td>
						                      	<select>
									                    <option value="">对应的规格值</option>
									                  </select>
						                      </td>
						                      <td><a href="javascirpt:void(0);" class="proifclose">删除</a></td>
						                    </tr>
						                  </tbody>
								            </table>
												</div>
												
												
	              		</div>
              		</div>
              		
              		<h3 class="proimgtit">图片描述</h3>
              		<!--商品图片相册上传-->
              		<div class="proinfoimg">
              				<span class="showimgtit">商品图片</span>
              				<div class="showimgbox">
              					<!-- 图片上传使用的是webuploader 插件 可参考萌哥吧评价晒单	 -->
								            <div id="uploader">
								                <div class="queueList">
								                    <div id="dndArea" class="placeholder">
								                        <div id="filePicker"></div>
								                        <div id="btnContainer"></div>
								                        <p>或将照片拖到这里</p>
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
								            </div>
              				</div>
              		</div>
              		
              		<div class="proinfoimg">
              				<span class="showimgtit">商品描述</span>
              				<div class="showimgbox">
              					<textarea id="editor_id" name="content" class="editortext" >
							  </textarea>
              				</div>
              		</div>
              		
              		<div class="orderbtnbox probtnbox">
	                  <input type="submit" value="保存"/>
	                  <input type="reset" class="order_btnj"  value="取消"/>
	                </div> 
              		
              		
              	</form>
              </div>
            </div>
            
      </div>
    </div>
  </div>

	  <!-- 底部 -->
	  [#include "/common/footer.ftl"/] 
	  
	  <script src="${base}/resources/js/webuploader.js"></script>
    <script src="${base}/resources/js/upload.js"></script>
    
    <script src="${base}/resources/js/kindeditor/kindeditor-all-min.js"></script>
		<script src="${base}/resources/js/kindeditor/lang/zh-CN.js"></script>
		<script>
					//富文本初始化
	        KindEditor.ready(function(K) {
	                window.editor = K.create('#editor_id');
	        });
	        $(function(){
	        	//选择规格复选框时候显示
	        	if($('#proGGe').is(':checked')){
	        			$(".proinfo_ggbox").show();
	        	}else{
	        			$(".proinfo_ggbox").hide();
	        	}
	        	$('#proGGe').change(function(){
		        		if($('#proGGe').is(':checked')){
			        			$(".proinfo_ggbox").show();
			        	}else{
			        			$(".proinfo_ggbox").hide();
			        	}
	        	})
	        	
	        	
	        })
		</script>

</body>
</html>


 