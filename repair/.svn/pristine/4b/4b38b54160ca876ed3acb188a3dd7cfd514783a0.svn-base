<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>出险车维修管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.checkbox_false {
	border: 1px solid red !important;
	width: 100px;
	height: 100px;
	padding: 5px;
	margin: 5px;
}

.checkbox_true {
	border: 1px solid #ccc !important;
	width: 100px;
	height: 100px;
	padding: 5px;
	margin: 5px;
}
</style>
<script type="text/javascript">
	var DELAY = 400, count = 0, timer = null;
	function doClickImg(index) {
		count++;
		if (count == 1) {
			timer = setTimeout(
					function() {
						var _ipt = $("#ipt_" + index).is(":checked");
						if (_ipt) {
							$("#img_" + index).addClass("checkbox_true")
									.removeClass("checkbox_false");
							$("#ipt_" + index).attr({
								"checked" : false
							});
							var checkboxed = $("input[type='checkbox']:checked").length;
							if (checkboxed == 0) {
								var img = document.getElementById("img_"+ index);
								img.style.height = "100px";
								img.style.width = "100px";
								$("#deleteImgBtn").attr("disabled", true);
								$("#enlargeImgBtn").attr("disabled", true);
								$("#recoveryImgBtn").attr("disabled", true);
								/* document.getElementById("enlargeImgBtn").style.display = "none";
								document.getElementById("deleteImgBtn").style.display = "none"; */
							}
						} else {
							$("#img_" + index).addClass("checkbox_false")
									.removeClass("checkbox_true");
							$("#ipt_" + index).attr({
								"checked" : true
							});
							var checkboxed = $("input[type='checkbox']:checked").length;
							if (checkboxed > 0) {
								var img = document.getElementById("img_"+ index);
								img.style.height = "100px";
								img.style.width = "100px";
								$("#deleteImgBtn").attr("disabled", false);
								$("#enlargeImgBtn").attr("disabled", false);
								$("#recoveryImgBtn").attr("disabled", false);
								/* document.getElementById("enlargeImgBtn").style.display = "block";
								document.getElementById("deleteImgBtn").style.display = "block"; */
							}
						}
						count = 0;
					}, DELAY)
		} else {
			clearTimeout(timer);
			var img = document.getElementsByTagName("img")[index - 1];
			img.style.height = "700px";
			img.style.width = "900px";
			count = 0;
		}
	}
	function recoveryImgButton(){
		if ($("input[type='checkbox']:checked").length > 1) {
			alert("复原只能选取一张");
			return;
		} else {
			$("input[type='checkbox']:checked").each(function(index, obj) {
				var idIndex = obj.id.replace(/ipt_/g, "");
				var img = document.getElementsByTagName("img")[idIndex - 1];
				img.style.height = "100px";
				img.style.width = "100px";
			});
		}
	}
	function enlargeImgButton() {
		if ($("input[type='checkbox']:checked").length > 1) {
			alert("查看图片只能选取一张");
			return;
		} else {
			$("input[type='checkbox']:checked").each(function(index, obj) {
				var idIndex = obj.id.replace(/ipt_/g, "");
				var img = document.getElementsByTagName("img")[idIndex - 1];
				img.style.height = "700px";
				img.style.width = "900px";
			});
		}
	}
	function delImgButton() {
		var imgs = "";
		//获取所有已选图片数据ID值
		$("input[type='checkbox']:checked").each(function(index, obj) {
			if ("" == imgs || null == imgs || typeof (undefined) == imgs) {
				imgs += $(obj).val();
			} else {
				imgs += "," + $(obj).val();
			}
		});
		if ("" == imgs || null == imgs || typeof (undefined) == imgs) {
			alert("请选择图片！");
			return false;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : {
				imgIds : imgs
			},
			url : "${webRoot }/uploadController/delImgAgeData.do",// 请求的action路径
			success : function(result) {
				var data = $.parseJSON(result);
				var msg = data.msg;//处理消息
				if ("success" == data.status) {
					//正确处理逻辑  删除成功后刷新当前窗口
					window.location.reload();
				} else {
					//错误处理逻辑
					console.log(msg);
				}
			},
			error : function() {
				console.log(msg);
			}
		});
	}
</script>
</head>
<body>
<!-- 使用Block会换行 所以用空代替 -->
	<button type="button" id="deleteImgBtn" onclick="delImgButton()" style="display: "";">删除</button>
	<button type="button" id="enlargeImgBtn" onclick="enlargeImgButton()" style="display: "";">放大</button>
	<button type="button" id="recoveryImgBtn" onclick="recoveryImgButton()" style="display: "";">恢复</button>
	<form>
		<c:forEach var="imgObj" items="${objList }" varStatus="idx">
			<img id="img_${idx.count }" name="imgBoxs" onclick="doClickImg('${idx.count}')" src="${webRoot }/uploadController/export.do?id=${imgObj.id }" style="width:100px;height:100px;padding: 5px;margin: 5px;border: 1px solid #ccc;">
			<input type="checkbox" id="ipt_${idx.count }" name="iptName" value="${imgObj.id }" style="display: "";" />
		</c:forEach>
	</form>
</body>
</html>