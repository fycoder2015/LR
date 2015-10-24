<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>
		<div class="col-md-3">
            <a href="${ctx}/webadmin/${action}" class="btn btn-success">添加分类</a>
        </div>
        <br></br>
		
		<%-- <div class="col-md-3">
		            <tags:pagination page="${categories}" paginationSize="5"/>
		</div> --%>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>分类名称</td>
	                    <td>分类别名</td>
	                    <td>操作</td>
	                </tr>
				
					<c:forEach items="${categories.content}" var="cate">
	              	<tr>
	                    <td>${cate.id}</td>
	                    <td>${cate.categoryName}</td>
	                    <td>${cate.alias}</td>
	                    <td><a href="${ctx}/webadmin/cateDetail?cateId=${cate.id}">编辑</a>|<a href="${ctx}/webadmin/deleteCate?cateId=${cate.id}&groupId=${cate.groupId}" style="color:red;" onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a></td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->



</body>
</html>
