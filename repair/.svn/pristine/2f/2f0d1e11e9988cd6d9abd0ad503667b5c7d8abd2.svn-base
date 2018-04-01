<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<html>
 <head>
  <title>短信模板维护</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/tools/ckfinder.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery-1.7.1.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery.selection.js"></script>
  <script type="text/javascript">
  function myFunction(obj){
  if(obj.checked){
 // +=obj.value;
  var s=insert_flg(obj.form.templateContent.value,obj.value,$("#templateContent").selection().start);
  obj.form.templateContent.value=s;
  
  } else {
  obj.form.templateContent.value=obj.form.templateContent.value.replace("{"+obj.value+"}",'');
  }
  }
  
  
  function getTxt1CursorPosition(a){
            
            var cursurPosition=0;
            if(a.selectionStart){//非IE
                cursurPosition= a.selectionStart;
            }else{//IE
                try{
                var range = document.selection.createRange();
                range.moveStart("character",-a.value.length);
                cursurPosition=range.text.length;
                }catch(e){
                 cursurPosition = 0;
                }
            }
            alert(cursurPosition);//打印当前索引
        } 
  
  
  
  function insert_flg(str,flg,sn){
  	
	var a = "{";
	var b = "}";
    var newstr="";
    if(str.length==0)
    {
    	newstr=a+flg+b;
    }else
    {
     
        var tmp=str.substring(0, sn);
        var tmp1=str.substring(sn);
        
        newstr+=tmp+a+flg+b+tmp1;
    
    }
   
    return newstr;
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSSmsTemplateController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tSSmsTemplatePage.id }">
					<input id="crtName" name="crtName" type="hidden" value="${tSSmsTemplatePage.crtName }">
					<input id="crtCde" name="crtCde" type="hidden" value="${tSSmsTemplatePage.crtCde }">
					<input id="crtTm" name="crtTm" type="hidden" value="${tSSmsTemplatePage.crtTm }">
					<input id="updName" name="updName" type="hidden" value="${tSSmsTemplatePage.updName }">
					<input id="updCde" name="updCde" type="hidden" value="${tSSmsTemplatePage.updCde }">
					<input id="updTm" name="updTm" type="hidden" value="${tSSmsTemplatePage.updTm }">
					
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板类型:
						</label>
					</td>
					<td class="value">
							 <t:dictSelect field="templateType" type="list"
									typeGroupCode="msgTplType" 
									defaultVal="1" 
									hasLabel="false"  title="模板类型"
									extendJson="{\"datatype\":\"*\"}"
									></t:dictSelect>
							<span class="Validform_checktip">送修短信用于公共资源业务推荐到合作车商；返修短信用于车商渠道业务返回该车商</span>
							<label class="Validform_label" style="display: none;">模板类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							人员类别:
						</label>
					</td>
					<td class="value">
							<t:dictSelect field="sendType" type="list"
									typeGroupCode="sendType" 
									defaultVal="${tSSmsTemplatePage.sendType}" 
									hasLabel="false"  title="模板类型"></t:dictSelect>     
							 <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">人员类别</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="templateName" name="templateName" type="text" style="width: 150px" class="inputxt"  
								               datatype="*" value='${tSSmsTemplatePage.templateName}'>							
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板名称</label>
						</td>
				</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">模板类别:</label>
					</td>
					<td class="value">
							<span class="Validform_checktip"><input type="radio" checked="checked" name="vehicleType" value="01"></span>适用于三者
							<span class="Validform_checktip"><input type="radio" name="vehicleType" value="00"></span>适用于标的
							<label class="Validform_label" style="display: none;">是否是三者车</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							模板内容:
						</label>
					</td>
					<td class="value">
						  	 <textarea  style="width:600px;" class="inputxt" rows="6" id="templateContent" name="templateContent"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板内容</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							请选择:
						</label>
					</td>
					<td class="value" colspan="2">
					<input type="checkbox"  name="plateNo" onclick="myFunction(this)" value="plateNo" >车牌号
					<input type="checkbox"  name="vehlcleName" onclick="myFunction(this)" value="vehlcleName" >车辆类型名称
					<input type="checkbox"  name="dealerAddress" onclick="myFunction(this)" value="dealerAddress" >车商地址
					<input type="checkbox" name="dealerName" onclick="myFunction(this)" value="dealerName" >车商名称
					<input type="checkbox" name="dealerContact" onclick="myFunction(this)" value="dealerContact" >车商联系人姓名
					<input type="checkbox" name="contactTel" onclick="myFunction(this)" value="contactTel" >车商联系人手机
					<input type="checkbox" name="directorName" onclick="myFunction(this)" value="directorName" >车商售后经理
					<input type="checkbox" name="directorTel" onclick="myFunction(this)" value="directorTel" >车商售后经理电话
					<input type="checkbox" name="usherName" onclick="myFunction(this)" value="usherName" >车商理赔引导专员
					<input type="checkbox" name="usherTel" onclick="myFunction(this)" value="usherTel" >车商理赔引导专员电话
					<input type="checkbox" name="salesmanName" onclick="myFunction(this)" value="salesmanName" >保险公司车商维护专员
					<input type="checkbox" name="salesmanName" onclick="myFunction(this)" value="salesmanPhone" >保险公司车商维护专员手机<br/>
					<input type="checkbox" name="surveyorName" onclick="myFunction(this)" value="surveyorName" >查勘员
					<input type="checkbox" name="surveyorTel" onclick="myFunction(this)" value="surveyorTel" >查勘员电话
					<input type="checkbox" name="reportNo" onclick="myFunction(this)" value="reportNo" >报案号
					<input type="checkbox" name="reportName" onclick="myFunction(this)" value="reportName" >报案人
					<input type="checkbox" name="reportTel" onclick="myFunction(this)" value="reportTel" >报案人电话
					<!-- 报案时间 -->
					<input type="checkbox" name="reportTime" onclick="myFunction(this)" value="reportTime" >报案时间
					<!-- 出险时间 -->
					<input type="checkbox" name="dangerTime" onclick="myFunction(this)" value="dangerTime" >出险时间
					<!-- 出险地点 -->
					<input type="checkbox" name="reportPlace" onclick="myFunction(this)" value="reportPlace" >出险地点
					<input type="checkbox" name="assessName" onclick="myFunction(this)" value="assessName" >定损员
					<input type="checkbox" name="assessTelphone" onclick="myFunction(this)" value="assessTelphone" >定损员电话	
					<input type="checkbox" name="ownerOrCommon" onclick="myFunction(this)" value="ownerOrCommon" >自有或共有<br/>	
					<input type="checkbox" name="other1" onclick="myFunction(this)" value="other1" >其他1	
					<input type="checkbox" name="otherPhone1" onclick="myFunction(this)" value="otherPhone1" >其他人电话1	
					<input type="checkbox" name="other2" onclick="myFunction(this)" value="other2" >其他2	
					<input type="checkbox" name="otherPhone2" onclick="myFunction(this)" value="otherPhone2" >其他人电话2
					<input type="checkbox" name="other3" onclick="myFunction(this)" value="other3" >其他3	
					<input type="checkbox" name="otherPhone3" onclick="myFunction(this)" value="otherPhone3" >其他人电话3
					<input type="checkbox" name="other4" onclick="myFunction(this)" value="other4" >其他4	
					<input type="checkbox" name="otherPhone4" onclick="myFunction(this)" value="otherPhone4" >其他人电话4
					</td>
					
					
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/system/sms/tSSmsTemplate.js"></script>		