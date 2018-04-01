<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>订单管理</title>
<link rel="stylesheet" href="${base }/resources/css/style.css">
<script src="${base }/resources/js/jquery-1.8.3.min.js"></script>
<script src="${base }/resources/datepicker/WdatePicker.js"></script>
<script src="${base}/resources/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {
    var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $pageNumber = $("#pageNumber");
    });
    function pageChange(){
    	var vs = $('select option:selected').val();
    	$("#pageSize").val(vs);
    	$("#listForm").submit();
    }
    
    function exportData(){
        var url = '${base}/orderItem/exportData.do?orderStatus='+$('#orderStatus').val()+'&bDate='+$('#beginDate').val()+'&eDate='+$('#endDate').val();
		$('#exportForm').attr("action",url);
		$("#exportForm").submit();
    	
    }
    
	function orderStatusChange(v){
	    $("#orderStatus").val(v);
	    $("#listForm").submit();
	}
    
  
</script>
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
            <li><a href="#">交易管理</a><span>></span></li>
            <li><a href="#">订单管理</a></li>
          </ul>
          <!--订单管理-->
          <div>
            <div class="order-control">
              <h4>订单查询</h4>
              <div class="form-container">
              <form action="${base}/orderItem/list.do" class="order-control-form">
              	<div class="oh order_selbox">
	                <p>
	                  <label for="order-num">订单编号</label><input type="text" id="order-num" name="sn" value="${order.sn }"/>
	                </p>
	                <p>
	                  <label for="buyer-acc">买家账号</label><input type="text" id="buyer-acc" name="memberName" value="${memberName }"/>
	                </p>
	                <p>
	                  <label for="tracking-num">物流单号</label><input type="text" id="tracking-num" name="shippingSn" value="${shippingSn }"/>
	                </p>
	                <p>
	                  <label for="beginDate">下单时间</label><input type="text" id="beginDate" class="Wdate" name="bDate" value="[#if bDate??]${bDate}[/#if]" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')})" value="${bDate }"/>—<input type="text" id="endDate" class="Wdate" name="eDate" value="[#if eDate??]${eDate}[/#if]" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}' })" value="${eDate }"/>
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
            <form id="exportForm" method="post">
	          <input type="hidden" id="exportbeDate" name="exportbDate" value="[#if bDate??]${bDate}[/#if]" />
		      <input type="hidden" id="exportenDate" name="exporteDate" value="[#if eDate??]${eDate}[/#if]" />
	        </form>
           
              <div class="ordstu-nav">
                <a href="javascript:;" id="exportButton" class="button" onclick="exportData()">订单导出</a>
                <a href="javaScript:orderStatusChange()" [#if orderStatus == ""] class="o-active"[/#if]">全部订单</a>
                <a href="javaScript:orderStatusChange('unpayment')" name="orderStatus" [#if orderStatus == "unpayment"] class="o-active"[/#if]>待付款</a>
                <a href="javaScript:orderStatusChange('unsend')" name="orderStatus" [#if orderStatus == "unsend"] class="o-active"[/#if]>等待发货</a>
                <a href="javaScript:orderStatusChange('shipped')" name="orderStatus" [#if orderStatus == "shipped"] class="o-active"[/#if]>已发货</a>
                <a href="javaScript:orderStatusChange('closed')" name="orderStatus" [#if orderStatus == "closed"] class="o-active"[/#if]>关闭订单</a>
                <a href="javaScript:orderStatusChange('completed')" name="orderStatus" value="completed" [#if orderStatus == "completed"] class="o-active"[/#if]>完成订单</a>
                <p class="pg-limit">
                  <span>每页显示</span> 
                  <select name="limit-cot" id="limit-cot" onchange="pageChange()">
                    <option value="20" [#if page.pageSize==20]selected="selected"[/#if]>20条</option>
                    <option value="30" [#if page.pageSize==30]selected="selected"[/#if]>30条</option>
                  </select>
                </p>
              </div>
              <div class="order-list">
               <input type="hidden" name="order" value="${page.content }">
               <form id="listForm" action="list.do" method="get">
               <input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
               <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}"/>
               <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
               <input type="hidden" id="bDate" name="bDate" value="${bDate}"/>
               <input type="hidden" id="eDate" name="eDate" value="${eDate}"/>
               <input type="hidden" id="memberName" name="memberName" value="${memberName}"/>
               
                <table class="order-list-tb"> 
                   <col width="18%" />
  				   <col width="16%" />
  				   <col width="18%" />
  				   <col width="15%" />
  				   <col width="15%" />
                  <thead>
                    <tr>
                      <th><input type="checkbox">订单号</th>
                      <th>下单时间</th>
                      <th>买家账号</th>
                      <th>收货人</th>
                      <th>商品总额</th>
                      <th class="ord-stu">状态</th>
                    </tr>
                  </thead>
                  <tbody>
                  [#list page.content as p]
                    <tr>
                      <td><input type="checkbox">${p.sn }</td>
                      <td><span>${p.createDate }</span></td>
                      <td>${p.member.username }</td>
                      <td>${p.consignee }</td>
                      <td>${p.amountPaid }</td>
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==0]
                      <td class="stu-tre"><span class="stu-l">已完成</span></td>                                                         
                      [/#if]
                       [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==1]
                      <td class="stu-tre"><span class="stu-l">已取消</span></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==2]
                      <td class="stu-tre"><span class="stu-l">支付中</span></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==3]
                      <td class="stu-tre"><span class="stu-l">已支付</span></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==4]
                      <td class="stu-tre"><span class="stu-l">待发货</span><a href="#" class="stu-r">发货</a></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==5]
                      <td class="stu-tre"><span class="stu-l">待支付</span><a href="${base}/orderItem/orderAlter.do?id=${p.id}" class="stu-r">修改</a></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==6]
                      <td class="stu-tre"><span class="stu-l">已发货</span></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==7]
                      <td class="stu-tre"><span class="stu-l">已关闭</span></td>                                                         
                      [/#if]
                      [#if p.orderStatus.ordinal()?? && p.orderStatus.ordinal()==8]
                      <td class="stu-tre"><span class="stu-l">已删除</span></td>                                                         
                      [/#if]
                      </tr>
                    [/#list]
                
                  </tbody>
                </table>
                      
                </form>
              </div>
              
              <div class="ui-page">
              
                [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			       [#include "/common/pagination.ftl"]
		        [/@pagination]
                 
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>
  
	<!-- 底部 -->
	  [#include "/common/footer.ftl"]
 
	  <script>
	    $("#beginDate,#endDate").click(function(){
			WdatePicker();
		});
	   
	  </script>
</body>
</html>
