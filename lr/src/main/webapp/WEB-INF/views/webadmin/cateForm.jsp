<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>
<div class="col-md-10">
	<form method="post" action="${ctx}/webadmin/${action}">
		<input type="hidden" name="id" value="${cate.id}">
		<input type="hidden" name="groupId" value="${groupId}">
		
		<div class="form-group">
			<label for="aa">分类名称</label>
			<input type="text" name="categoryName" value="${cate.categoryName}" class="form-control" id="aa" placeholder="输入分类标题">
		</div>
		<div class="form-group">
			<label for="cc">分类别名</label>
			<input type="text" name="alias" value="${cate.alias}" class="form-control" id="cc" placeholder="输入分类别名">
		</div>
		<div class="form-group">
			<label for="cc">描述信息</label>
			<textarea name="explain" class="form-control" id="cc" placeholder="请输入分类描述(选填)">${cate.explain}</textarea>
		</div>
		<button type="submit" class="btn btn-default">
		<c:choose>
			<c:when test="${cate.id==null}">
				添加
			</c:when>
			<c:otherwise>
				保存
			</c:otherwise>
		</c:choose>
		</button>
		<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
	</form>
</div>



</body>
</html>