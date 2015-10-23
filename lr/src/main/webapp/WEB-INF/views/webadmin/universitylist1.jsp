<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>大学列表</title>
</head>

<body>


		<div class="col-md-3">
		            <tags:pagination page="${universitys}" paginationSize="5"/>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              <thead>
		              <tr>
		                  <th>编号</th>
		                  <th>标题</th>
		                  <th>别名</th>
		                  <th>操作</th>
		              </tr>
	              </thead>
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>登录名</td>
	                    <td>注册日期</td>
	                    <td>查看详情</td>
	                </tr>
				
					<c:forEach items="${universitys.content}" var="university">
	              	<tr>
	                    <td>${university.id}</td>
	                    <td>${university.id}</td>
	                    <td>${university.id}</td>
	                    <td><a href="${ctx}/webadmin/showuserinfo?showuserId=${university.id}">详情</a>  
	                    <!--  | <a href="/admin.php?m=Admin&c=category&a=delete&id=1" style="color:red;" 
	                    	onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a>
	                     -->
	                    </td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->



</body>
</html>
