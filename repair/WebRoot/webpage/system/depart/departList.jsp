<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="departList" title="common.department.list"
                    actionUrl="departController.do?departgrid"
                    treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="归属机构代码" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.department.name" field="departname" treefield="text"></t:dgCol>
            <t:dgCol title="position.desc" field="description" treefield="src"></t:dgCol>
            <t:dgCol title="common.org.code" field="orgCode" treefield="fieldMap.orgCode"></t:dgCol>
            <t:dgCol title="common.org.type" field="orgType" dictionary="orgtype" treefield="fieldMap.orgType"></t:dgCol>
            <t:dgCol title="common.mobile" field="mobile" treefield="fieldMap.mobile"></t:dgCol>
            <t:dgCol title="common.fax" field="fax" treefield="fieldMap.fax"></t:dgCol>
            <t:dgCol title="common.address" field="address" treefield="fieldMap.address"></t:dgCol>
            <t:dgCol title="common.operation" field="opt"></t:dgCol>
            <t:dgDelOpt url="departController.do?del&id={id}" title="common.delete"></t:dgDelOpt>
            <t:dgFunOpt funname="queryUsersByDepart(id)" title="view.member"></t:dgFunOpt>
         <%--    <t:dgFunOpt funname="setRoleByDepart(id,text)" title="role.set"></t:dgFunOpt> --%>
            
        </t:datagrid>
        <div id="departListtb" style="padding: 3px; height: 25px">
            <div style="float: left;">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="addOrg()"><t:mutiLang langKey="common.add.param" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('<t:mutiLang langKey="common.edit.param" langArg="common.department"/>','departController.do?update','departList')"><t:mutiLang langKey="common.edit.param" langArg="common.department"/></a>
                <%-- <a href="#" class="easyui-linkbutton" plain="true" icon="icon-put" onclick="ImportXls()"><t:mutiLang langKey="excelImport" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXls()"><t:mutiLang langKey="excelOutput" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXlsByT()"><t:mutiLang langKey="templateDownload" langArg="common.department"/></a> --%>
            </div>
        </div>
    </div>
</div>
<div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
    $(function() {
        var li_east = 0;
    });
    function addOrg() {
        var id = "";
        var rowsData = $('#departList').datagrid('getSelections');
        if (rowsData.length == 1) {
            id = rowsData[0].id;
        }
        var url = "departController.do?add&id=" + id;
        add('<t:mutiLang langKey="common.add.param" langArg="common.department"/>', url, "departList");
    }

    function queryUsersByDepart(departid){
        var title = '<t:mutiLang langKey="member.list"/>';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 400});
        $('#userListpanel').panel("refresh", "departController.do?userList&departid=" + departid);
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'departController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("departController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("departController.do?exportXlsByT","departList");
    }
//-->
</script>