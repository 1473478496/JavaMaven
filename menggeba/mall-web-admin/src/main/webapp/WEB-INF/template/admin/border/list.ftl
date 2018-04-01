<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.list")}-PoweredBy www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
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
		<input type="hidden" id="checkState" name="checkState" value="${checkState}" />
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
							<li><a href="javascript:;" name="checkState" val="00" [#if 
							checkState== 00] class="checked"[/#if]>待审核</a></li>
							<li><a href="javascript:;" name="checkState" val="approved" [#if
								checkState== 10] class="checked"[/#if]>审核通过</a></li>
							<li><a href="javascript:;" name="checkState" val="unapprove"
								[#if checkState== "unapprove"] class="checked"[/#if]>审核拒绝</a></li>
							<li><a href="javascript:;" name="checkState" val="frozen" [#if
								checkState== 20] class="checked"[/#if]>冻结</a></li>
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
							"bmNum"] class="current" [/#if] val="bmNum">边民证号</a></li>
						<li><a href="javascript:;" [#if page.searchProperty==
							"trueName"] class="current" [/#if] val="trueName">真实姓名</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check"><input type="checkbox" id="selectAll" /></th>
				<th><a href="javascript:;" class="sort" name="bmNum">边民证号</a></th>
				<th><a href="javascript:;" class="sort" name="trueName">真实姓名</a></th>
				<th><a href="javascript:;" class="sort" name="idCardNum">身份证号</a></th>
				<th><a href="javascript:;" class="sort" name="goArea">前往地区</a></th>
				<th><a href="javascript:;" class="sort" name="comment">备注</a></th>
				<th><a href="javascript:;" class="sort" name="checkState">审核状态</a></th>
				<th><a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th><span>${message("admin.common.handle")}</span></th>
			</tr>
			[#list page.content as borderMan]
			<tr>
				<td><input type="checkbox" name="ids" value="${borderMan.id}" /></td>
				<td>${borderMan.bmNum}</td>
				<td>${borderMan.trueName}</td>
				<td>${borderMan.idCardNum}</td>
				<td>${borderMan.goArea}</td>
				<td>${borderMan.comment }</td>
				<td>
				[#if borderMan.checkState ==00] 
				待审核 
				[#elseif borderMan.checkState == 10]
				 审核通过
				[#else ] 
				 账户冻结
				[/#if]
				  </td>
				<td><span >${borderMan.createDate}</span>
				</td>
				<td>
				[#if borderMan.checkState ==00] 
				<a href="audit.jhtml?id=${borderMan.id}">【审核】</a>
				[#elseif borderMan.checkState==10] 
				<a href="view.jhtml?id=${borderMan.id}">【${message("admin.common.view")}】</a>
				[#elseif borderMan.checkState ==20] 
				<a href="view.jhtml?id=${borderMan.id}">【${message("admin.common.view")}】</a>
				[#else] 
				<a href="view.jhtml?id=${borderMan.id}"><span>【${message("admin.common.view")}】</span></a>
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