<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>评论列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
<!-- 	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>评论者</th><th>评论内容</th></tr></thead>
		<tbody>
		<c:forEach items="${comments.content}" var="comment">
			<tr>
				<td>${comment.user.name}</td>
				<td>${comment.comment}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	 -->
	 
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<!-- 
		<thead><tr><th>评论者</th><th>评论内容</th></tr></thead>
		 -->
		<thead><tr><th>任务详情</th></tr></thead>
		<tbody>
		<tr><td>任务标题</td><td>${comments.content[0].task.title}</td></tr>
		<tr><td>任务描述</td><td>${comments.content[0].task.description}</td></tr>
		<tr><td>性别要求</td><td>${comments.content[0].task.gender}</td></tr>
		<tr><td>工作性质</td><td>${comments.content[0].task.jobType}</td></tr>
		<tr><td>时间要求</td><td>${comments.content[0].task.timeRquirement}</td></tr>
		<tr><td>所在区域</td><td>${comments.content[0].task.district}</td></tr>
		<tr><td>工资计算方式</td><td>${comments.content[0].task.paymentCalcWay}</td></tr>
		<tr><td>薪酬</td><td>${comments.content[0].task.payment}</td></tr>
		<tr><td>岗位要求</td><td>${comments.content[0].task.jobRequirements}</td></tr>
		<tr><td>联系电话</td><td>${comments.content[0].task.phoneCall}</td></tr>
		</tbody>
	</table>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>评论者</th><th>评论内容</th></tr></thead>
		<tbody>
		<c:forEach items="${comments.content}" var="comment">
			<tr>
				<td>${comment.user.name}</td>
				<td>${comment.comment}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${comments}" paginationSize="5"/>
	
	<div class="form-actions">
		<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
	</div>

	<!-- 
	<div><a class="btn" href="${ctx}/task/create">创建任务</a></div>
	<div><a class="btn" href="${ctx}/comment/create">创建评论</a></div>
	 -->
</body>
</html>
