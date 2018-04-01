[#if totalPages > 1]
	<!--页码，所在页面a添加on-->
	<div class="wd_page">
		[#if isFirst]
			<a href="javascript:;">首页</a>
		[#else]			
			<a href="javascript: $.pageSkip(${firstPageNumber});">首页</a>
		[/#if]
		
		[#if hasPrevious]			
			<a href="javascript: $.pageSkip(${previousPageNumber});" class="prevpage">上一页</a>
		[#else]
			<a href="javascript:;" class="prevpage">上一页</a>
		[/#if]	
		
		[#list segment as segmentPageNumber]
			[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
				<a href="javascript:;">...</a>
			[/#if]
			[#if segmentPageNumber != pageNumber]
				<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
			[#else]				
				<a href="javascript:;" class="ui-page-curr">${segmentPageNumber}</a>
			[/#if]
			[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
				<a href="javascript:;">...</a>
			[/#if]
		[/#list]			
		
		[#if hasNext]			
			<a href="javascript: $.pageSkip(${nextPageNumber});" class="nextpage">下一页</a>
		[#else]
			<a href="javascript:;" class="nextpage">下一页</a>
		[/#if]		
						
		[#if isLast]
			<a href="javascript:;">尾页</a>
		[#else]				
			<a href="javascript: $.pageSkip(${lastPageNumber});">尾页</a>
		[/#if]
		
		<span>共${page.totalPages}页</span>
		<!--到第<input type="text" id = "pagination_number">页 <a href="#" onclick="pageinationGoPage()">确认</a>-->
	</div>
[/#if]