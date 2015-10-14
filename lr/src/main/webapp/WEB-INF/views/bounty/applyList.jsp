<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>揭榜申请列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<br/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>编号</th><th>用户名</th><th>申请时间</th><th>申请状态</th><th></th><th></th></tr></thead>
		<tbody>
		<c:forEach items="${applyList.content}" var="bountyApply">
			<tr>
				<td>${bountyApply.id}</td>
				<td>${bountyApply.applyUser.loginName}</td>
				<td>${bountyApply.applyDate}</td>
				<td>${bountyApply.sts}</td>
				<td><a href="${ctx}/bountyComment/create/${bountyApply.id}">评论</a></td>
				<td><a href="${ctx}/bountyComment/listByApply/${bountyApply.id}_1">评论列表</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${applyList}" paginationSize="20"/>

	<div><input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/></div>

</body>
</html>
