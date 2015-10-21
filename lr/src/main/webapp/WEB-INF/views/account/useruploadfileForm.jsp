<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户上传文件</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/api/v1/usertools/uploaduserpic" method="post" class="form-horizontal"  enctype="multipart/form-data">
		
		<fieldset>
			<legend><small>上传文件</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">username：</label>
				<div class="controls">
					<input type="text" id="username" name="username"   class="input-large required" minlength="3"/-->
				</div>
			</div>	
			<div class="control-group">
				<label for="task_title" class="control-label">digest：</label>
				<div class="controls">
					<input type="text" id="digest" name="digest"   class="input-large required" minlength="3"/-->
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">imageFile：</label>
				<div class="controls">
					<input type="file" id="imageFile" name="imageFile" class="input-large"/>					
				</div>
			</div>	
			
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
