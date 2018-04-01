<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="msageList" title="车商短信发送设置  <font color=#FF0000>配置具体接收派修短信的人员</font>" actionUrl="msageController.do?datagrid" idField="id" 
  queryMode="group"  fit="true"  nowrap = "false" onLoadSuccess ="startEdit" editCell ="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="归属机构代码" field="dptCde" hidden="true" query="false" width="100"></t:dgCol>
   <t:dgCol title="机构名称" field="dptName" width="100"></t:dgCol> 
   <t:dgCol title="车商代码" field="dealerNo" query="true" width="90"></t:dgCol>
   <t:dgCol title="车商全称" field="dealerName" query="true"  width="100"></t:dgCol> 
   <t:dgCol title="查勘员" field="surveyorFlag"  dictionary="sf_01" width="85" sortable="false"></t:dgCol>
   <t:dgCol title="定损员" field="assessFlag"  dictionary="sf_01" width="85" sortable="false"></t:dgCol> 
   <t:dgCol title="客户" field="customerFlag" dictionary="sf_01"  width="85" sortable="false"></t:dgCol>
   <t:dgCol title="车商联系人" field="contactsFlag" dictionary="sf_01"  width="100" sortable="false"></t:dgCol>
   <t:dgCol title="车商售后经理" field="directorFlag" dictionary="sf_01"  width="100" sortable="false"></t:dgCol>
   <t:dgCol title="车商理赔引导专员" field="usherFlag" dictionary="sf_01"  width="100" sortable="false"></t:dgCol>
   <t:dgCol title="保险公司车商维护专员" field="agentFlag" dictionary="sf_01"  width="100" sortable="false"></t:dgCol>
 <%--   <t:dgCol title="其他1" field="other1Flag" dictionary="sf_01" hidden="true"  width="85" sortable="false"></t:dgCol>
   <t:dgCol title="其他2" field="other2Flag"  dictionary="sf_01" hidden="true" width="85" sortable="false"></t:dgCol> 
   <t:dgCol title="其他3" field="other3Flag" dictionary="sf_01" hidden="true" width="85" sortable="false"></t:dgCol>
   <t:dgCol title="其他4" field="other4Flag" dictionary="sf_01" hidden="true" width="85" sortable="false"></t:dgCol> --%>
   <t:dgCol title="派修类型" field="type" dictionary="msgTplType"  query="true" width="85" sortable="false"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="删除" url="msageController.do?del&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="msageController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="msageController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="msageController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="保存" icon="icon-save" url="msageController.do?updateRule" funname="save()"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <script type="text/javascript">
	var allFlag = false ; 
	function save() {
		endEdit();
		var updated = $('#msageList').datagrid('getChanges', "updated");
		var json = '';
		if (updated && updated.length > 0 ) {//有修改的行才操作
			json = JSON.stringify(updated);
		}
		if(true == allFlag){
			var pagesize=  $('#msageList').datagrid('getPager').data("pagination").options.pageSize ;
			var editRows = new Object();
			for(var i = 0 ; i < pagesize ; i ++ ){
				var rows = $('#msageList').datagrid("getRows");
				json = JSON.stringify(rows);
			}
		}
		
		if(json && json.length > 0){
			var url = "msageController.do?updateRule" ;
			$.ajax({
				type : 'POST',
				url : url,
				contentType: 'text/html',
				data : json ,
				success : function(data) {
				//成功之后状态更改
					allFlag = false ; 
					var d = $.parseJSON(data);
					tip(d.msg);
				},
	            error:function (data) {
	            	var d = $.parseJSON(data);
	            	tip(d.msg);
	           }
		});
			
		}else{
			tip("请选择要更改的记录！");
		}
	
	}
	//批量处理的方法
	 function allDealRules(obj){
		 var id = obj.id;
		//  得到rows对象
		var rows = $('#msageList').datagrid("getRows"); // 这段代码是// 对某个单元格赋值
		//得到本业显示的数量
		var pagesize=  $('#msageList').datagrid('getPager').data("pagination").options.pageSize ;
		for(var i = 0 ; i < pagesize ; i ++ ){
			if(1 == obj.value){
				rows[i][id] = '1' ;
				$('#msageList').datagrid('refreshRow', i);
			}else if(0 == obj.value){
				rows[i][id] = '0' ;
				$('#msageList').datagrid('refreshRow', i);
			}
			allFlag = true ; 
		}	
	} 
	
	function startEdit(){
		///修改表格显示样式，否则显示不出批量处理的请选择
		$(".datagrid-view2 div").each(function(i){
			if(Number(i)  > 6 && Number(i) < 15){
				$(this).height("48");
			}
			if(Number(i) > 14){
				return false ; 
			}
		});
		//解决行号错位的问题
		$('#msageList').datagrid('fixRowHeight')  
		
		//进入页面时未行编辑模式
		/* var pagesize=  $('#msageList').datagrid('getPager').data("pagination").options.pageSize ;
		for(var i = 0 ; i < pagesize ; i ++ ){
			$('#msageList').datagrid('beginEdit',i);
		} */
		
		/*
		修改表头字体的
		$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		
		alert(i +  ":" + val);
		 $(th).html("<label style='font-size: 24px;'>"+val+"</label>");
		 if(i > 10){
			return true ; 
		}
		});*/
	}
	

	function endEdit() {
		var pagesize = $('#msageList').datagrid('getPager').data("pagination").options.pageSize;
		for ( var i = 0; i < pagesize; i++) {
			$('#msageList').datagrid('endEdit', i);
		}
	}
</script>