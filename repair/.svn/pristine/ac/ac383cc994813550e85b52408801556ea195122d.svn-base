<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="common.operation"
            actionUrl="departController.do?userDatagrid&departid=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="tsUser.id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="tsUser.userName" query="false"></t:dgCol>
	<t:dgCol title="common.real.name" field="tsUser.realName" query="false"></t:dgCol>
	<t:dgCol title="角色" field="tsRole.roleName" query="false"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="tsUser.status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgDelOpt title="common.delete" url="departController.do?delUserOrgRole&id={id}" />
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="departController.do?goAddUserToOrg&orgId=${departid}" funname="add" width="500"></t:dgToolBar>
	<t:dgToolBar title="role.set" icon="icon-add" url="departController.do?goAddUserAndOrgToRole&orgId=${departid}" funname="findRoleList" width="500"></t:dgToolBar>
</t:datagrid>

<script type="text/javascript">
function findRoleList(title,addurl,gname,width,height) {
	gridname=gname;
	var rows = $("#"+gname).datagrid('getSelections');
	if (rows.length > 0) {
		var id=(rows[0]);
		var userid=id['tsUser.' + 'id'];
		addurl+="&userid="+userid;
		createwindow(title, addurl,width,height);
	}else{
		tip("请先选择用户");
	}
}
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			zIndex: getzIndex(),
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			zIndex:getzIndex(),
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}
	
}

</script>
