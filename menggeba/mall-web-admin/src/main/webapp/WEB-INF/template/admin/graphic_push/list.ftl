<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>首页猜你喜欢列表 - Powered By www.mgb.cn</title>
<meta name="author" content="vivebest Team" />
<meta name="copyright" content="www.mgb.cn" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]


});
var Push = function(){
	this.doPush = function(id, type){
		$.ajax({
			url: "push.jhtml",
			type: "POST",
			data: {"mediaId":id, "type":type},
			dataType: "json",
			cache: false,
			success: function(data) {
				if(data.mes == "success"){
					$.dialog({
						type: "success",
						content: "推送成功"});
				}else{
					$.dialog({
						type: "warn",
						content: "推送失败"});
				}
			}
		});
	};
};

var push = new Push();
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 图文推送
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" name="type" id="type" value="" />
		<div class="bar">
			<a href="javascript:;" type="image" class="iconButton">图片</a>
			<a href="javascript:;" type="video" class="iconButton">视频</a>
			<a href="javascript:;" type="voice" class="iconButton">语音</a>
			<a href="javascript:;" type="news" class="iconButton">图文</a>
		</div>
		<table id="listTable" class="list">
		  [#if type=="news"]
			<tr>
				<th><span>素材ID</span></th>
		  		<th><span>标题</span></th>
		  		<th><span>URL</span></th>
		  		<th><span>最后更新时间</span></th>
		  		<th><span>操作</span></th>
			</tr>
			[#list materialList as material]
			  [#list material.content.news_item as item]
			    [#if item_index == 0]
					<tr>
						<td rowspan="${material.content.news_item?size }">${material.media_id }</td>
						<td>${item.title }</td>
						<td><a href="${item.thumb_url }" target="_black"><img src="${item.thumb_url }"/></a></td>
						<td rowspan="${material.content.news_item?size }" id="t${material.media_id }">${material.update_time }</td>
						<td rowspan="${material.content.news_item?size }"><a href="javascript:push.doPush('${material.media_id}','${type}');">推送</a></td>
					</tr>
				[#else]
					<tr>
						<td>${item.title }</td>
						<td><a href="${item.thumb_url }" target="_black"><img src="${item.thumb_url }"/></a></td>
					</tr>
			    [/#if]
			  [/#list]
			[/#list]
		  [#else]
		  	<tr>
		  		<th><span>素材ID</span></th>
		  		<th><span>文件名称</span></th>
		  		<th><span>URL</span></th>
		  		<th><span>最后更新时间</span></th>
		  		<th><span>操作</span></th>
		  	</tr>
			[#list materialList as material]
				<tr>
					<td>${material.media_id }</td>
					<td>${material.name }</td>
					<td>[#if material.url?has_content]${material.url }[#else]-[/#if]</td>
					<td id="t${material.media_id }">${material.update_time }</td>
					<td><a href="javascript:push.doPush('${material.media_id}','${type}');">推送</a></td>
				</tr>
			[/#list]
		  [/#if]
		</table>
	</form>
	
<script type="text/javascript">
Date.prototype.format = function(f){
    var o ={
    		"M+" : this.getMonth()+1, //月份        
    	    "d+" : this.getDate(), //日        
    	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    	    "H+" : this.getHours(), //小时        
    	    "m+" : this.getMinutes(), //分        
    	    "s+" : this.getSeconds(), //秒        
    	    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    	    "S" : this.getMilliseconds() //毫秒   
    }
    if(/(y+)/.test(f))f=f.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(f))f = f.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));return f
}

$().ready(function() {
	[#list materialList as material]
		var d = new Date(${material.update_time} * 1000).format('yyyy-MM-dd HH:mm:ss');
		$("#t${material.media_id}").text(d);
	[/#list]
	
	$(".iconButton").click(function(){
		$("#type").val($(this).attr("type"));
		$("#listForm").submit();
	});
});
</script>
</body>
</html>