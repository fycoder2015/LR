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
