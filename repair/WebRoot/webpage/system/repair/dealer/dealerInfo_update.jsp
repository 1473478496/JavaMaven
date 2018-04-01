<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>车商信息维护</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<script type="text/javascript">
	var oldProvince = '';
	var oldCity = '';
	function getProvince() {
		var url = "dealerInfoController.do?getprovince";

		$.ajax({
			type : 'POST',
			url : url,
			success : function(data) {
				var d = $.parseJSON(data);
				var arr = eval(d.msg);
				var str = "";
				for (i = 0; i < eval(arr).length; i++) {
					str += "<option value='"+arr[i].provinceid+"'>" + arr[i].province + "</option>";
				}
				str += "";
				$("#province").html(str);
			}
		});
	}

	function getcity(province) {
		if (province == "undefined" || province == null || province == '') {
			province = $("#province").val();
		}

		if (province == "undefined" || province == null || province == '') {
			return;
		}

		if (oldProvince == province) {
			return;
		}

		$("#city").html("");
		$("#area").html("");
		var url = "dealerInfoController.do?getcity&province=" + province;

		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);

				var arr = eval(d.msg);
				var str = "";
				for (i = 0; i < eval(arr).length; i++) {
					str += "<option value='"+arr[i].cityid+"'>" + arr[i].city + "</option>";
				}
				$("#city").html(str);
			}
		});

		oldProvince = province;
		getarea();
	}

	function getarea(city) {
		if (city == "undefined" || city == null || city == '') {
			city = $("#city").val();
		}

		if (city == "undefined" || city == null || city == '') {
			return;
		}
		var url = "dealerInfoController.do?getarea&city=" + city;

		if (oldCity == city) {
			return;
		}
		$("#area").html("");
		$("#spareArea1").html("");
		$("#spareArea2").html("");
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);

				var arr = eval(d.msg);
				var str = "";
				for (i = 0; i < eval(arr).length; i++) {

					str += "<option value='"+arr[i].areaid+"'>" + arr[i].area + "</option>";
				}
				$("#area").html(str);
				$("#spareArea1").html(str);
				$("#spareArea2").html(str);

			}
		});

		oldCity = city;
	}

	function openCurrentDepart() {
		$.dialog.setting.zIndex = getzIndex();

		var orgIds = $("#orgIds").val();
		$.dialog({
			content : 'url:departController.do?currentDepart&orgIds=' + orgIds,
			zIndex : 2100,
			title : '组织机构列表',
			lock : true,
			width : '800px',
			height : '550px',
			opacity : 0.4,
			button : [ {
				name : '<t:mutiLang langKey="common.confirm"/>',
				callback : callbackCurrentDepart,
				focus : true
			}, {
				name : '<t:mutiLang langKey="common.cancel"/>',
				callback : function() {
				}
			} ]
		}).zindex();
	}
	function callbackCurrentDepart() {
		var iframe = this.iframe.contentWindow;
		var rowsData = iframe.$('#currentDepartList').datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择机构');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条机构');
			return;
		}
		var orgId = rowsData[0].id;
		$("#dptCde").val(orgId);
	}
</script>
<body style="overflow-y: scroll" scroll="yes">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dealerInfoController.do?save">
		<input id="id" name="id" type="hidden" value="${dealerInfoPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 车商代码: </label></td>
				<td class="value"><input class="inputxt" id="dealerCode" readonly="readonly" name="dealerCode"
					value="${dealerInfoPage.dealerCode }" datatype="*"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商全称: </label></td>
				<td class="value"><input class="inputxt" id="dealerName" name="dealerName" value="${dealerInfoPage.dealerName}" datatype="*">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商简称:</label></td>
				<td class="value"><input class="inputxt" id="dealerAllName" name="dealerAllName" ignore="ignore"
					value="${dealerInfoPage.dealerAllName}"> <spanclass="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">车商具体地址(省): </label></td>
				<td class="value">
				<t:regionSelect
						extendJson="{onchange:getcity(this.value)}" dictField="provinceid"
						dictTable="rp_province" dictText="province" field="province"
						type="list" id="province" defaultVal="${dealerInfoPage.province}"
						hasLabel="false" title="车商具体地址">
					</t:regionSelect>
					<span class="Validform_checktip"></span> </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">车商具体地址(市): </label></td>
				<td class="value">
				<t:regionSelect dictField="cityid" extendJson="{onchange:getarea(this.value)}" dictTable="rp_city"
						dictText="city" field="city" type="list" id="city"
						defaultVal="${dealerInfoPage.city}" hasLabel="false" title="车商具体地址"></t:regionSelect>
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">车商具体地址</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商具体地址(区县): </label></td>
				<td class="value">
				<t:regionSelect dictField="areaid" dictTable="rp_area" dictText="area" field="area" type="list"
						id="area" defaultVal="${dealerInfoPage.area}" hasLabel="false" title="车商具体地址">
				</t:regionSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">车商具体地址</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商备用送修地址1(区县): </label></td>
				<td class="value">
				<t:regionSelect dictField="areaid" dictTable="rp_area" dictText="area" field="spareArea1" type="list"
				id="spareArea1" defaultVal="${dealerInfoPage.spareArea1}" hasLabel="false" title="车商具体地址">
				</t:regionSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">车商具体地址</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商备用送修地址2(区县): </label></td>
				<td class="value">
				<t:regionSelect dictField="areaid" dictTable="rp_area" dictText="area" field="spareArea2" type="list"
				id="spareArea2" defaultVal="${dealerInfoPage.spareArea2}" hasLabel="false" title="车商具体地址">
				</t:regionSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">车商具体地址</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商具体地址:</label></td>
				<td class="value"><input class="inputxt" id="dealerAddress" name="dealerAddress" value="${dealerInfoPage.dealerAddress}"
					datatype="*"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商联系人姓名: </label></td>
				<td class="value"><input class="inputxt" id="contactsName" name="contactsName" ignore="ignore"
					value="${dealerInfoPage.contactsName}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商联系人手机: </label></td>
				<td class="value"><input class="inputxt" id="contactsTel" name="contactsTel" ignore="ignore"
					value="${dealerInfoPage.contactsTel}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商售后经理姓名: </label></td>
				<td class="value"><input class="inputxt" id="directorName" name="directorName" ignore="ignore"
					value="${dealerInfoPage.directorName}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商售后经理电话: </label></td>
				<td class="value"><input class="inputxt" id="directorTel" name="directorTel" ignore="ignore"
					value="${dealerInfoPage.directorTel}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商理赔引导专员姓名: </label></td>
				<td class="value"><input class="inputxt" id="usher" name="usher" ignore="ignore" value="${dealerInfoPage.usher}">
				<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 车商理赔引导专员电话: </label></td>
				<td class="value"><input class="inputxt" id="usherPhone" name="usherPhone" ignore="ignore" value="${dealerInfoPage.usherPhone}">
				<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 保险公司车商维护专员姓名: </label></td>
				<td class="value"><input class="inputxt" id="agent" name="agent" ignore="ignore" value="${dealerInfoPage.agent}">
				<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 保险公司车商维护专员手机: </label></td>
				<td class="value"><input class="inputxt" id="agentTel" name="agentTel" ignore="ignore" value="${dealerInfoPage.agentTel}">
				<span class="Validform_checktip"></span></td>
			</tr>
			<%-- <tr>
				<td align="right"><label class="Validform_label"> 业务归属地: </label></td>
				<td class="value"><input class="inputxt" id="businessHome" name="businessHome" ignore="ignore"
					value="${dealerInfoPage.businessHome}"> <span class="Validform_checktip"></span></td>
			</tr> --%>
			<tr>
				<td align="right"><label class="Validform_label"> 资质类型: </label></td>
				<td class="value"><t:dictSelect field="dealerType" type="list"
						typeGroupCode="dealer_tp" defaultVal="${dealerInfoPage.dealerType}" hasLabel="false"
						title="资质类型"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">资质类型</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 归属机构代码:
				</label></td>
				<td class="value"> <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}" /> <input type="text"
					name="dptCde" id="dptCde" value="${dealerInfoPage.dptCde}" readonly="readonly" /> <a href="#" class="easyui-linkbutton"
					plain="true" icon="icon-search" id="departSearch"
					onclick="openCurrentDepart()">选择</a> <span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 是否有效: </label></td>
				<td class="value"><t:dictSelect field="isvalid" type="list" typeGroupCode="sf_01" defaultVal="${dealerInfoPage.isvalid}"
						hasLabel="false" title="是否有效"></t:dictSelect> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label> </td>
				<td class="value"><input class="inputxt" id="note" name="note" ignore="ignore" value="${dealerInfoPage.note}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
					<td align="right">
						<label class="Validform_label">
							备用1:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="other1" name="other1" ignore="ignore" value="${dealerInfoPage.other1}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用手机号1:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="otherPhone1" name="otherPhone1" ignore="ignore" value="${dealerInfoPage.otherPhone1}"/>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用2:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="other2" name="other2" ignore="ignore" value="${dealerInfoPage.other2}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用手机号2:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="otherPhone2" name="otherPhone2" ignore="ignore" value="${dealerInfoPage.otherPhone2}"/>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用3:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="other3" name="other3" ignore="ignore" value="${dealerInfoPage.other3}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用手机号3:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="otherPhone3" name="otherPhone3" ignore="ignore" value="${dealerInfoPage.otherPhone3}"/>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用4:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="other4" name="other4" ignore="ignore" value="${dealerInfoPage.other4}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用手机号4:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="otherPhone4" name="otherPhone4" ignore="ignore" value="${dealerInfoPage.otherPhone4}"/>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
		</table>
	</t:formvalid>
</body>