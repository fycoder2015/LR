<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>企业管理-企业列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<br/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>编号</th><th>企业名称</th><th>企业经理</th><th>企业地址</th>
		<th>联系电话</th><th>注册时间</th><th></th><th></th></tr></thead>
		<tbody>
		<c:forEach items="${entList.content}" var="ent">
			<tr>
				<td>${ent.id}</td>
				<td>${ent.entName}</td>
				<td>${ent.entManager}</td>
				<td>${ent.entAddress}</td>
				<td>${ent.phoneCall}</td>
				<td>${ent.regDate}</td>
				<td><a href="${ctx}/enterprise/update/${ent.id}">编辑</a></td>
				<td><a href="${ctx}/enterprise/delete/${ent.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${entList}" paginationSize="20"/>

	<div><a class="btn" href="${ctx}/enterprise/create">新增企业</a></div>
</body>
</html>
