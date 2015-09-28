<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>企业管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/enterprise/${action}" method="post" class="form-horizontal" >
		<input type="hidden" name="id" value="${ent.id}"/>
		<fieldset>
			<legend><small>新增/编辑企业</small></legend>
			
			<div class="control-group">
				<label for="entName" class="control-label">企业名称:</label>
				<div class="controls">
					<input type="text" id="entName" name="entName"  value="${ent.entName}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="entManager" class="control-label">企业负责人:</label>
				<div class="controls">
					<textarea id="entManager" name="entManager" class="input-large">${ent.entManager}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="entAddress" class="control-label">企业地址:</label>
				<div class="controls">
					<textarea id="entAddress" name="entAddress" class="input-large">${ent.entAddress}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="phoneCall" class="control-label">联系电话:</label>
				<div class="controls">
					<textarea id="phoneCall" name="phoneCall" class="input-large">${ent.phoneCall}</textarea>
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
