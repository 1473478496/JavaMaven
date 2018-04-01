<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>结算申请</title>
<link rel="stylesheet" href="${base}/resources/css/style.css">
<script src="${base}/resources/js/jquery-1.8.3.min.js"></script>
<script src="${base}/resources/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<script type="text/javascript">
 function pageChange(){
    	var vs = $('select option:selected').val();
    	$("#pageSize").val(vs);
    	$("#listForm").submit();
    }
  	
	function doSelectAll(){
  		
		$("input[name=ids]").prop("checked", $("#selAll").is(":checked"));		
	}
	
	 function doDelete(){
	   document.forms[0].action="${base }/settle/confirm.do";
	   document.forms[0].submit();
    }
</script>
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
            <li><a href="#">结算申请</a></li>
          </ul>
          <!--结算申请-->
          <div>
            <div class="order-control">
              <h4>结算申请</h4>
              <div class="form-container">
              <form id="listForm" action="${base }/settle/list.do" class="order-control-form" method="get">
              	 <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}"/>
              	 <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
              	<div class="oh order_selbox">
	                <p>
	                  <label for="order-num">订单编号</label><input type="text" id="order-num" name="sn" value="${order.sn }"/>
	                </p>
	                <p>
	                  <label for="buyer-acc">买家账号</label><input type="text" id="buyer-acc" name="memberUsername" value="${memberUsername }"/>
	                </p>
	                <p>
	                  <label for="tracking-name">买家姓名</label><input type="text" id="tracking-name" name="memberName" value="${memberName }"/>
	                </p>
	                <p>
	                  <label for="ordered-time-star">下单时间</label><input type="text" id="ordered-time-star" class="Wdate" name="bDate" value="${bDate}" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'ordered-time-end\')})"/>—<input type="text" id="ordered-time-end" class="Wdate" name="eDate" value="${eDate}" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'ordered-time-star\')}' })"/>
	                </p>
	                <div class="orderbtnbox">
	                  <input type="submit" value="查询"/>
	                  <input type="reset" class="order_btnj"  value="重置"/>
	                </div> 
                </div>
              
              </div>
            </div>
            <div class="order-l-content">
              <div class="ordstu-nav">
                <a href="javaScript:;" onclick="doDelete()">提交结算</a>
                
               
                
                <p class="pg-limit">
                  <span>每页显示</span> 
                  <select name="limit-cot" id="limit-cot" onchange="pageChange()">
                    <option value="20" [#if page.pageSize==20]selected="selected"[/#if]>20条</option>
                    <option value="50" [#if page.pageSize==50]selected="selected"[/#if]>50条</option>
                    <option value="100" [#if page.pageSize==100]selected="selected"[/#if]>100条</option>
                  </select>
                </p>
              </div>
              <div class="order-list">
                <table class="order-list-tb"> 
                   <col width="15%" />
  				   <col width="15%" />
  				   <col width="15%" />
  				   <col width="15%" />
  				   <col width="15%" />
  				   <col width="15%" />
                  <thead>
                    <tr>
                      <th><input type="checkbox" id="selAll" onclick="doSelectAll()">订单号</th>
                      <th>下单时间</th>
                      <th>买家账号</th>
                      <th>买家姓名</th>
                      <th>商品总额</th>
                      <th class="ord-stu">状态</th>
                      <th>交易方式</th>
                    </tr>
                  </thead>
                  <tbody>
                    [#list page.content as order]
                    <tr>
                    	<!-- 点击订单号可以查看订单详细 -->
                      <td><input type="checkbox" name="ids" value="${order.id}"><a href="${base}/settle/orderDetail.do?id=${order.id}">${order.sn}</a></td>
                      <td><span>${order.createDate}</span>
                      <td>${order.member.username}</td>
                      <td>${order.member.name}</td>
                      <td>${order.amountPaid}</td>
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
              <div class="ui-page">
              	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			       [#include "/common/pagination.ftl"]
		        [/@pagination]
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

  	<!-- 底部 -->
	 <!--  [#include "/common/footer.ftl"] -->
	 
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
