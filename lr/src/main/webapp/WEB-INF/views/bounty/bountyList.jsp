<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>赏金任务列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<br/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>编号</th><th>标题</th><th>性别要求</th><th>任务地址</th>
		<th>任务时间</th><th>关闭时间</th><th>酬谢方式</th><th>金额</th><th>状态</th><th></th></tr></thead>
		<tbody>
		<c:forEach items="${bountyList.content}" var="bountyTask">
			<tr>
				<td>${bountyTask.id}</td>
				<td>${bountyTask.title}</td>
				<td>${bountyTask.gender}</td>
				<td>${bountyTask.taskLocation}</td>
				<td>${bountyTask.taskDate}</td>
				<td>${bountyTask.closeDate}</td>
				<td>${bountyTask.paymentType}</td>
				<td>${bountyTask.bounty}</td>
				<td>${bountyTask.sts}</td>
				<td><a href="${ctx}/bounty/update/${bountyTask.id}">详情</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${bountyList}" paginationSize="20"/>

	<div><a class="btn" href="${ctx}/bounty/create">发布赏金任务</a></div>
</body>
</html>
