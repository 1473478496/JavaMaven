<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="questionList"  nowrap = "false" fitColumns="false" title="帮助文档" 
  		actionUrl="questionController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="标题"  field="questionName" query="true" width="1000"   queryMode="single"  ></t:dgCol>
   <t:dgCol title="内容"  field="questionContent" hidden="true" width="1000"   queryMode="single"  ></t:dgCol>
   <c:if test="${roleCode=='admin'  }">
   <t:dgCol title="common.opt" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="common.deleteTo" url="questionController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="questionController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-add" url="questionController.do?goUpdate" funname="update"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-add" url="questionController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script>
	/* function addContent(){
		var url="questionController.do?addContent";
		var rows = $("#questionList").datagrid('getSelections');
		 if (rows.length > 0) {
			var id=rows[0].id;
			var questionName=rows[0].questionName;
			var questionContent=rows[0].questionContent;
			url+="&id="+id+"&questionName="+encodeURIComponent(encodeURIComponent(questionName))+"&questionContent="+encodeURIComponent(encodeURIComponent(questionContent));
		} 
		 $.dialog({
			content: "url:"+url,
			drag :false,
			lock : true,
			title:'问答区',
			opacity : 0.3,
			width:800,
			height:480,
			drag:false,
			min:true,
			max:true
		}).zindex(); 
	}
	 */
</script>