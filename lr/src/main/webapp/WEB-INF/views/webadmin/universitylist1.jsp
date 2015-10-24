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
		            <tags:pagination page="${universitys}" paginationSize="5"/>&nbsp;&nbsp;&nbsp;
		            	<a href="${ctx}/webadmin/gotoaddUniversity" class="btn btn-success">
	            		添加学校
	            		</a>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              <thead>
		              <tr>
		                  <th>编号</th>
		                  <th>校名</th>
		                  <th>地址</th>
		                  <th>操作</th>
		              </tr>
	              </thead>
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>大学名称</td>
	                    <td>所在地区</td>
	                    <td>查看详情</td>
	                </tr>
				
					<c:forEach items="${universitys.content}" var="university">
	              	<tr>
	                    <td>${university.id}</td>
	                    <td>${university.university}</td>
	                    <td>${university.place}</td>
	                    <td><a href="${ctx}/webadmin/showuniversityinfo?showuniversityId=${university.id}">详情</a>|
	                    <a href="${ctx}/webadmin/subjectlist?universityId=${university.id}">查看学院</a>
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
