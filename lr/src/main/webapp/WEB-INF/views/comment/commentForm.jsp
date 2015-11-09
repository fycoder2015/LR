<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/api/v1/taskComment/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${taskComment.id}"/>
		<input type="hidden" name="username" value="75401737a9ea48c7a9ea9e3e345ae8fb"/>
		<input type="hidden" name="digest" value="e60e633cd564e24bcc4bcf91b1c3d7ccb9966d9a"/>
		 
		<fieldset>
			<legend><small>评价信息</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">任务ID:</label>
				<div class="controls">
					<input type="text" id="apply.id" name="apply.id"  value="${taskComment.apply.id}" class="input-large required" />
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">评论内容:</label>
				<div class="controls">
					<textarea id="comment" name="comment" class="input-large">${taskComment.comment}</textarea>
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
			$("#task.id").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
