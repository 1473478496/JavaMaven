<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.auditing")}-PoweredBy www.mgb.cn</title>
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

	[@flash_message /]
	
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
						${message("admin.audit.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li><a href="javascript:;" name="status"
								val="waitingApprove" [#if status==
								"waitingApprove"] class="checked"[/#if]>待审核</a></li>
							<li><a href="javascript:;" name="status" val="approved" [#if
								status== "approved"] class="checked"[/#if]>审核通过</a></li>
							<li><a href="javascript:;" name="status" val="unapprove"
								[#if status== "unapprove"] class="checked"[/#if]>审核拒绝</a></li>
							<li><a href="javascript:;" name="status" val="frozen" [#if
								status== "frozen"] class="checked"[/#if]>账户冻结</a></li>
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
							"no"] class="current" [/#if] val="no">商户编号</a></li>
						<li><a href="javascript:;" [#if page.searchProperty==
							"name"] class="current" [/#if] val="name">商户名称</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><a href="javascript:;" class="sort" name="no">商户编号</a></th>
				<th><a href="javascript:;" class="sort" name="name">商户名称</a></th>
				<th><a href="javascript:;" class="sort" name="cert_type">证件类型</a></th>
				<th><a href="javascript:;" class="sort" name="cert_no">证件号码</a></th>
				<th><a href="javascript:;" class="sort" name="mobile">联系电话</a></th>
				<th><a href="javascript:;" class="sort" name="status">审核状态</a></th>
				<th><a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th><span>${message("admin.common.handle")}</span></th>
			</tr>
			[#list page.content as trade]
			<tr>
				<td><input type="checkbox" name="ids" value="${trade.id}" /></td>
				<td>${trade.no}</td>
				<td>${trade.name}</td>
				<td>
				[#if trade.cert_type == "identityCard"]
				 身份证
				[#elseif trade.cert_type == "passport"] 
				护照
			    [#else ]
			           军官证
			    [/#if]  
			    </td>
				<td>${trade.cert_no}</td>
				<td>${trade.mobile }</td>
				<td>
				[#if trade.status == "waitingApprove"] 
				待审核 
				[#elseif trade.status == "approved"]
				 审核通过
				[#elseif trade.status == "unapprove"] 
				 审核拒绝
				[#else ] 
				 账户冻结
				[/#if]
				  </td>
				<td><span >${trade.createDate}</span>
				</td>
				<td>
				[#if trade.status =="waitingApprove"] 
				<a href="audit.jhtml?id=${trade.id}">【审核】</a>
				[#elseif trade.status=="approved"] 
				<a href="view.jhtml?id=${trade.id}">【${message("admin.common.view")}】</a>
				[#elseif trade.status =="unapprove"] 
				<a href="view.jhtml?id=${trade.id}">【${message("admin.common.view")}】</a>
				[#else] 
				<a href="view.jhtml?id=${trade.id}"><span>【${message("admin.common.view")}】</span></a>
                [/#if]
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