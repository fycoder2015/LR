<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>评论列表</title>
</head>

<body>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>字段</th><th>取值</th></tr></thead>
		<tbody>
			<tr>
				<td>揭榜申请ID</td>
				<td>${comment.id}</td>
			</tr>
			<tr>
				<td>任务标题</td>
				<td>${comment.apply.bountyTask.title}</td>
			</tr>
			<tr>
				<td>评论者</td>
				<td>${comment.commentUser.loginName}</td>
			</tr>
			<tr>
				<td>评论时间</td>
				<td>${comment.commentDate}</td>
			</tr>
			<tr>
				<td>评论内容</td>
				<td>${comment.comment}</td>
			</tr>
			<tr>
				<td>星级</td>
				<td>${comment.starLevel}</td>
			</tr>
			
		</tbody>
	</table>
	<c:if test="${comment.imageFileName1!=null}">
		<div class="control-group">
			<label for="task_image" class="control-label">图片1:</label>
			<div class="controls">
				<img src="/upload/${comment.imageFileName1}" />  
			</div>
		</div>
	</c:if>
	<c:if test="${comment.imageFileName2!=null}">
		<div class="control-group">
			<label for="task_image" class="control-label">图片2:</label>
			<div class="controls">
				<img src="/upload/${comment.imageFileName2}" />  
			</div>
		</div>
	</c:if>
	<c:if test="${comment.imageFileName3!=null}">
		<div class="control-group">
			<label for="task_image" class="control-label">图片3:</label>
			<div class="controls">
				<img src="/upload/${comment.imageFileName3}" />  
			</div>
		</div>
	</c:if>
	
	
	<div><input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/></div>

</body>
</html>
