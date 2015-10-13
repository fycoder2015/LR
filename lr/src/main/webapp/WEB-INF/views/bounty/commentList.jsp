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
	
	<br/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>编号</th><th>任务标题</th><th>评论者</th>
		<th>被评论者</th><th>评论时间</th>
		<th>评论内容</th><th>星级</th><th>详情</th></tr></thead>
		<tbody>
		<c:forEach items="${commentList.content}" var="bountyComment">
			<tr>
				<td>${bountyComment.id}</td>
				<td>${bountyComment.apply.bountyTask.title}</td>
				<td>${bountyComment.commentUser.loginName}</td>
				<td>${bountyComment.byCommentUserId}</td>
				<td>${bountyComment.commentDate}</td>
				<td>${bountyComment.comment}</td>
				<td>${bountyComment.starLevel}</td>
				<td><a href="${ctx}/bountyComment/detail/${bountyComment.id}">详情</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${commentList}" paginationSize="20"/>

	<div><input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/></div>

</body>
</html>
