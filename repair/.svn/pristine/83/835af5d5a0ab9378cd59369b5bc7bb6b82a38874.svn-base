<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前组织机构的用户列表--%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="user_depart_role_list" class="easyui-layout" fit="true">
    <div region="center"  style="padding:0px;border:0px">
        <t:datagrid name="departRoleList" title="common.operation"
                    actionUrl="departController.do?addUserAndOrgToRoleList&orgId=${orgId}&userId=${userId}" fit="true" fitColumns="true"
                    idField="id" checkbox="true" queryMode="group">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="角色编码" sortable="false" field="roleCode" query="true"></t:dgCol>
            <t:dgCol title="角色名称" field="roleName" query="true"></t:dgCol>
        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="departController.do?doAddRoleToUserAndOrg&orgId=${orgId}&userId=${userId}" beforeSubmit="setUserIds">
        <input id="roleId" name="roleId">
    </t:formvalid>
</div>
<script>
    function setUserIds() {
        $("#roleId").val(getRoleListSelections('id'));
        return true;
    }

    function getRoleListSelections(field) {
        
        var rows = $('#departRoleList').datagrid('getSelections');
       	var id=rows[0][field];
     
        return id;
    }
</script>
