<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}"/>
		<fieldset>
			<legend><small>管理任务</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">任务名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"  value="${task.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">任务描述:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${task.description}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="gender" class="control-label">性别:</label>
				<div class="controls">
					<select id="gender" name="gender" class="input-large" value="${task.gender}">
						<option value ="1">男性</option>
						<option value ="2">女性</option>
						<option value ="3">不限</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="timeRquirement" class="control-label">时间要求:</label>
				<div class="controls">
					<textarea id="timeRquirement" name="timeRquirement" class="input-large">${task.timeRquirement}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="district" class="control-label">区域:</label>
				<div class="controls">
					<select id="district" name="district" class="input-large" value="${task.district}">
						<option value ="1">和平</option>
						<option value ="2">南开</option>
						<option value ="3">河西</option>
						<option value ="4">河北</option>
						<option value ="5">河东</option>
						<option value ="6">红桥</option>
						<option value ="7">西青</option>
						<option value ="8">东丽</option>
						<option value ="9">北辰</option>
						<option value ="10">津南</option>
						<option value ="11">塘沽</option>
						<option value ="12">汉沽</option>
						<option value ="13">大港</option>
						<option value ="14">宁河</option>
						<option value ="15">蓟县</option>
						<option value ="16">静海</option>
						<option value ="17">宝坻</option>
						<option value ="18">武清</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="paymentCalcWay" class="control-label">工资计算方式:</label>
				<div class="controls">
					<select id="paymentCalcWay" name="paymentCalcWay" class="input-large" value="${task.paymentCalcWay}">
						<option value ="1">按小时</option>
						<option value ="2">按天</option>
						<option value ="3">按周</option>
						<option value ="4">按月</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="payment" class="control-label">薪酬:</label>
				<div class="controls">
					<textarea id="payment" name="payment" class="input-large">${task.payment}</textarea>
				</div>
			</div>	
			<div class="control-group">
				<label for="jobRequirements" class="control-label">岗位要求:</label>
				<div class="controls">
					<textarea id="jobRequirements" name="jobRequirements" class="input-large">${task.jobRequirements}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label for="phoneCall" class="control-label">联系电话:</label>
				<div class="controls">
					<textarea id="phoneCall" name="phoneCall" class="input-large">${task.phoneCall}</textarea>
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
