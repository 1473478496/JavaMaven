<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.auditing")}-PoweredBywww.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript"
	src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $print = $("select[name='print']");
	
	// 订单筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
	
	$("a[id='close']").click(function(){
		if(confirm("确认关闭吗")){
			$.ajax({
				url:"${base}/admin/tradeShop/close.jhtml",
				type:"POST",
				data: {id:$(this).attr("shopId")},
				dataType:"json",
				cache:false,
				success:function(message){
					if(message.type == "success"){
						window.location.href="${base}/admin/tradeShop/list.jhtml";
						$.message(message);
					}else{
						$.message(message);
				    }
			     }
			})
		}else{
			
		}
	});
	$("a[id='open']").click(function(){
		if(confirm("确认打开么")){
			$.ajax({
				url:"${base}/admin/tradeShop/open.jhtml",
				type:"POST",
				data: {id:$(this).attr("shopId")},
				dataType:"json",
				cache:false,
				success:function(message){
					if(message.type == "success"){
						window.location.href="${base}/admin/tradeShop/list.jhtml";
						$.message(message);
					}else{
						$.message(message);
				    }
			     }
			})
		}else{
			
		}
	})
});
</script>
</head>
<body>
	<!-- 
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a>
		&raquo; ${message("admin.returns.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	 -->
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="status" name="status" value="${status}" />
		<div class="bar">
			<div class="buttonWrap">
				<!-- <a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a> -->
				<a href="javascript:;" id="refreshButton" class="iconButton"> <span
					class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.shop.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li><a href="javascript:;" name="status" val="open" [#if
								status== "open"] class="checked"[/#if]>商家开启状态</a></li>
							<li><a href="javascript:;" name="status" val="close" [#if
								status== "close"] class="checked"[/#if]>商家关闭状态</a></li>

							<li class="separator"><a href="javascript:;"
								name="platStatus" val="open" [#if returnType==
								"open"] class="checked"[/#if]>平台开启状态</a></li>
							<li><a href="javascript:;" name="platStatus" val="close"
								[#if returnType== "close"] class="checked"[/#if]>平台关闭状态</a></li>
						</ul>
					</div>
				</div>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li><a href="javascript:;" [#if page.pageSize==
								10] class="current" [/#if] val="10">10</a></li>
							<li><a href="javascript:;" [#if page.pageSize==
								20] class="current" [/#if] val="20">20</a></li>
							<li><a href="javascript:;" [#if page.pageSize==
								50] class="current" [/#if] val="50">50</a></li>
							<li><a href="javascript:;" [#if page.pageSize==
								100] class="current" [/#if] val="100">100</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span> <input
						type="text" id="searchValue" name="searchValue"
						value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li><a href="javascript:;" [#if page.searchProperty==
							"name"] class="current" [/#if] val="name">店铺名称</a></li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><a href="javascript:;" class="sort" name="name">店铺名称</a></th>
				<th><a href="javascript:;" class="sort" name="phone">联系电话</a></th>
				<th><a href="javascript:;" class="sort" name="begin_worktime">工作时间</a></th>
				<th><a href="javascript:;" class="sort" name="status">商家管理状态</a></th>
				<th><a href="javascript:;" class="sort" name="platStatus">平台管理状态</a></th>
				<th><a href="javascript:;" class="sort" name="remark">标记</a></th>
				<th><span>${message("admin.common.handle")}</span></th>
			</tr>
			[#list page.content as tradeShop]
			<tr>
				<td><input type="checkbox" name="ids" value="${tradeShop.id}" /></td>
				<td>${tradeShop.name}</td>
				<td>${tradeShop.phone }</td>
				<td>${tradeShop.begin_worktime}至${tradeShop.end_worktime }</td>

				<td>[#if tradeShop.status == "open"] 开启 [#else ] 关闭 [/#if]</td>
				<td>[#if tradeShop.platStatus == "open"] 开启 [#else ] 关闭 [/#if]
				</td>
				<td>${tradeShop.remark }</td>
				<td>[#if tradeShop.platStatus =="open"] <a id="close"
					href="javascript:;" shopId="${tradeShop.id }">【关闭】</a> [#else] <a
					id="open" href="javascript:;" shopId="${tradeShop.id }">【开启】</a> [/#if]
				</td>
			</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages =
		page.totalPages] [#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>