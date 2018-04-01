<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.consultation.list")} - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $listForm = $("#listForm");
	var $selectAll = $("#selectAll");
	var $ids = $("#listTable input[name='ids']");
	var $sendButton = $("#sendButton");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	
	[@flash_message /]
	
	
	// 发送通知
	$sendButton.click(function() {
		if ($sendButton.hasClass("disabled")) {
			return false;
		}
		var $checkedIds = $ids.filter(":enabled:checked");
		$.dialog({
			type: "warn",
			content: "${message("admin.memberBirthdayRights.sendConfirm")}",
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onOk: function() {
				$.ajax({
					url: "send.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							$checkedIds.closest("td").siblings(".hasSent").html('<span title="${message("admin.memberBirthdayRights.hasSent")}" class="trueIcon">&nbsp;<\/span>');
						}
					}
				});
			}
		});
	});
	
	// 全选
	$selectAll.click(function() {
		var $this = $(this);
		var $enabledIds = $ids.filter(":enabled");
		if ($this.prop("checked")) {
			if ($enabledIds.filter(":checked").size() > 0) {
				$sendButton.removeClass("disabled");
			} else {
				$sendButton.addClass("disabled");
			}
		} else {
			$sendButton.addClass("disabled");
		}
	});
	
	// 选择
	$ids.click(function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			$sendButton.removeClass("disabled");
		} else {
			if ($ids.filter(":enabled:checked").size() > 0) {
				$sendButton.removeClass("disabled");
			} else {
				$sendButton.addClass("disabled");
			}
		}
	});
	
	// 筛选
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
	
	// 筛选
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

	//单条发送
	function send_notified(id){
		var ids = new Array();
		ids.push(id);
		$.dialog({
			type: "warn",
			content: "${message("admin.memberBirthdayRights.sendConfirm")}",
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onOk: function() {
				$.ajax({
					type:'post',  
	                traditional :true,  
	                url:'send.jhtml',  
	                data:{'ids':ids},  
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							$checkedIds.closest("td").siblings(".hasSent").html('<span title="${message("admin.memberBirthdayRights.hasSent")}" class="trueIcon">&nbsp;<\/span>');
						}
					}
				});
			}
		});
	}

</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 权益列表 <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="sendButton" class="button disabled">
					批量发送
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			${message("admin.purchaseRanking.beginDate")}: <input type="text" id="beginDate" name="bDate" class="text Wdate" value="${(beginDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});" />
			${message("admin.purchaseRanking.endDate")}: <input type="text" id="endDate" name="eDate" class="text Wdate" value="${(endDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'});" />
			<input type="submit" class="button" value="${message("admin.common.submit")}" />
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "member_username"] class="current"[/#if] val="member_username">${message("Consultation.member")}</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="member">${message("Consultation.member")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="rightsDesc">描述</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="rightsValue">权益值</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="rightsDate">权益日期</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="rightsStatus">状态</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as mbr]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${mbr.id}" />
					</td>
					<td>
						${mbr.member.username}
					</td>
					<td>
						${mbr.rightsDesc}
					</td>
					<td>
						<span name="rightsValue" value="1" >[#if mbr.rightsValue?contains('1')]站内信[#else]&emsp;&emsp;&emsp;[/#if]</span>
						<span name="rightsValue" value="2" >[#if mbr.rightsValue?contains('2')]短信[#else]&emsp;&emsp;[/#if]</span>
						<span name="rightsValue" value="3" >[#if mbr.rightsValue?contains('3')]邮件[#else]&emsp;&emsp;[/#if]</span>
					</td>
					<td>
						<span title="">${mbr.rightsDate}</span>
					</td>
					<td>
						<span class="Icon">[#if mbr.rightsStatus]已发送[#else]未发送[/#if]</span>
					</td>
					<td>
						<a href="javascript:void(0)" onclick="send_notified(${mbr.id})">[发送]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>