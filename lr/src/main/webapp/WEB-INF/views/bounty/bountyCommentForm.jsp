<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>发表评论</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/rest/comment/${action}" method="post" class="form-horizontal" >
		<input type="hidden" name="apply.id" value="${applyId}"/>
		<fieldset>
			<legend><small>发表评论</small></legend>
			
			<div class="control-group">
				<label for="comment" class="control-label">评价内容:</label>
				<div class="controls">
					<textarea id="comment.comment" name="comment" class="input-large"></textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="star" class="control-label">星级：</label>
				<div class="controls">
					<input type="text" id="starLevel" name="starLevel"  value="${comment.starLevel}" class="input-large"/>
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
			$("#entName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
