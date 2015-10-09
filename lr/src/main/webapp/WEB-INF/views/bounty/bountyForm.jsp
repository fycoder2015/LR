<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>发布/修改赏金任务</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/bounty/${action}" method="post" class="form-horizontal" >
		<input type="hidden" name="id" value="${bountyTask.id}"/>
		<fieldset>
			<legend><small>新增/编辑赏金任务</small></legend>
			
			<div class="control-group">
				<label for="title" class="control-label">标题:</label>
				<div class="controls">
					<input type="text" id="title" name="title"  value="${bountyTask.title}" class="input-large required"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">详情描述:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${bountyTask.description}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="gender" class="control-label">性别要求：</label>
				<div class="controls">
					<input type="text" id="gender" name="gender"  value="${bountyTask.gender}" class="input-large"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="taskDate" class="control-label">赏金任务时间：</label>
				<div class="controls">
					<input type="text" id="taskDate" name="taskDate"  value="${bountyTask.taskDate}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="closeDate" class="control-label">任务关闭时间：</label>
				<div class="controls">
					<input type="text" id="closeDate" name="closeDate"  value="${bountyTask.closeDate}" class="input-large"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="taskLocation" class="control-label">任务地点：</label>
				<div class="controls">
					<input type="text" id="taskLocation" name="taskLocation"  value="${bountyTask.taskLocation}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="paymentType" class="control-label">酬谢方式：</label>
				<div class="controls">
					<input type="text" id="paymentType" name="paymentType"  value="${bountyTask.paymentType}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="bounty" class="control-label">金额：</label>
				<div class="controls">
					<input type="text" id="bounty" name="bounty"  value="${bountyTask.bounty}" class="input-large"/>
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
