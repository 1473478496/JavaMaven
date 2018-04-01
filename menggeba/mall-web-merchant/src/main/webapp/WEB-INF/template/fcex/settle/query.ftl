<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>结算列表</title>
<link rel="stylesheet" href="${base}/resources/css/style.css">
<script src="${base}/resources/js/jquery-1.8.3.min.js"></script>
<script src="${base}/resources/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/js/list.js"></script>
<script type="text/javascript">

	function pageChange(){
	    var vs = $("#limit").val();
	      $("#pageSize").val(vs);
	      $("#listForm").submit();
	}
	function statusChange(v){
	      $("#status").val(v);
	      $("#listForm").submit();
	}
	function statusOnChange(v){
    	$("#status").val(v);
    }
	function operatingChange(){
	    var vs = $('select option:selected').val();
    	$("#operating").val(vs);
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
            <li><a href="#">结算列表</a></li>
          </ul>
          <!--结算申请-->
          <div>
            <div class="order-control">
              <h4>结算查询</h4>
              <div class="form-container">
              <form id="listForm" action="${base }/settle/query.do" class="order-control-form" method="get">
                 <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}"/>
              	 <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
              	 <input type="hidden" id="status" name="status" value="${status}" />
              	 <input type="hidden" id="operating" name="operating" value="${operating}" />
              	<div class="oh order_selbox">
	                <p>
	                  <label for="order-num">结算编号</label><input type="text" id="order-num" name="balanceNo" value=""/>
	                </p>
	                <p>
	                  <label for="applyNum">结算人</label>
					   <select onchange="operatingChange()">
	                 	 <option value="" >全部</option>
	                 	 [#list balanceList as b]
		                  <option value="${b.operating}" >${b.operating}</option>
		                 [/#list]
		               </select>
	                </p>
	                <p>
	                  <label for="buyer-acc">银行账户名</label>
	                  <select name="cardName">
	                 	 <option value="">全部</option>
	                 	 [#list balanceList as b]
		                  <option value="${b.cardName}" [#if b.cardName == value]selected="selected"[/#if]>${b.cardName}</option>
		                 [/#list]
		               </select>
	                </p>
	                <p>
	                  <label for="tracking-name">银行卡号</label>
	                  <select name="cardNo">
	                 	 <option value="">全部</option>
	                 	 [#list balanceList as b]
		                  <option value="${b.cardNo}">${b.cardNo}</option>
		                 [/#list]
		               </select>
	                </p>
	                <p>
	                  <label for="tracking-name">结算状态</label>
	                  <select onchange="statusOnChange(value)">
	                 	 <option value="">全部</option>
		                  <option value="uncheck" [#if status=="uncheck"]selected="selected"[/#if]>待审核</option>
		                  <option value="approve" [#if status=="approve"]selected="selected"[/#if]>审核通过</option>
		                  <option value="unapprove" [#if status=="unapprove"]selected="selected"[/#if]>审核拒绝</option>
		                  <option value="settlesuccess" [#if status=="settlesuccess"]selected="selected"[/#if]>结算成功</option>
		                  <option value="settlefailures" [#if status=="settlefailures"]selected="selected"[/#if]>结算失败</option>
		               </select>
	                </p>
	                <p>
	                  <label for="ordered-time-star">结算时间</label><input type="text" id="ordered-time-star" class="Wdate" name="bDate" value="${bDate}" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'ordered-time-end\')})"/>—<input type="text" id="ordered-time-end" class="Wdate" name="eDate" value="${eDate}" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'ordered-time-star\')}' })"/>
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
              <div class="ordstu-nav settlenav">
                <a href="javaScript:statusChange('uncheck')" name="status" [#if status == "uncheck"]class="o-active"[/#if]>待审核</a>
                <a href="javaScript:statusChange('approve')" name="status" [#if status == "approve"]class="o-active"[/#if]>审核通过</a>
                <a href="javaScript:statusChange('unapprove')" name="status" [#if status == "unapprove"]class="o-active"[/#if]>审核拒绝</a>
                <a href="javaScript:statusChange('settlesuccess')" name="status" [#if status == "settlesuccess"]class="o-active"[/#if]>结算成功</a>
                <a href="javaScript:statusChange('settlefailures')" name="status" [#if status == "settlefailures"]class="o-active"[/#if]>结算失败</a>
                <p class="pg-limit">
                  <span>每页显示</span> 
                  <select id="limit" onchange="pageChange()">
                    <option value="20" [#if page.pageSize==20]selected="selected"[/#if]>20条</option>
                    <option value="50" [#if page.pageSize==50]selected="selected"[/#if]>50条</option>
                    <option value="100" [#if page.pageSize==100]selected="selected"[/#if]>100条</option>
                  </select>
                </p>
              </div>
              <div class="order-list">
                <table class="order-list-tb"> 
                   <col width="13%" />
  				   <col width="15%" />
  				   <col width="12%" />
  				   <col width="20%" />
  				   <col width="13%" />
  				   <col width="13%" />
                  <thead>
                    <tr>
                      <th>结算编号</th>
                      <th>申请时间</th>
                      <th>银行账户名</th>
                      <th>银行卡号</th>
                      <th>结算人</th>
                      <th>商品总额</th>
                      <th>状态</th>
                    </tr>
                  </thead>
                  <tbody>
                   [#list page.content as balance]
                    <tr>
                    	<!-- 点击结算号可以查看订单详细 -->
                      <td><a href="${base}/settle/settleDetail.do?id=${balance.id}" class="see_details">${balance.balanceNo}</a></td>
                      <td><span>${balance.applyDate}</span> <span>16:00:03</span></td>
                      <td>${balance.cardName}</td>
                      <td>${balance.cardNo}</td>
                      <td>${balance.operating}</td>
                      <td>${balance.totalAmt}</td>
                      [#if balance.status.ordinal()?? && balance.status.ordinal()==0]
                      <td class="stu-tre"><span class="stu-l">待审核</span></td>
                      [/#if]
                      [#if balance.status.ordinal()?? && balance.status.ordinal()==1]
                      <td class="stu-tre"><span class="stu-l">审核通过</span></td>
                      [/#if]
                      [#if balance.status.ordinal()?? && balance.status.ordinal()==2]
                      <td class="stu-tre"><span class="stu-l">审核拒绝</span><a href="#" class="orddetialbtn">拒绝原因</a></td>
                      [/#if]
                      [#if balance.status.ordinal()?? && balance.status.ordinal()==3]
                      <td class="stu-tre"><span class="stu-l">结算成功</span></td>
                      [/#if]
                      [#if balance.status.ordinal()?? && balance.status.ordinal()==4]
                      <td class="stu-tre"><span class="stu-l">结算失败</span></td>
                      [/#if]
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
