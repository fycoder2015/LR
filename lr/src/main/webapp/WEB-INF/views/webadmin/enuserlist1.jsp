<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>企业用户</title>
</head>

<body>
		<div class="col-md-3">
		<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div><br>
		</c:if>
		
		 企业用户列表 &nbsp; &nbsp; &nbsp;<tags:pagination page="${users}" paginationSize="5"/>&nbsp;&nbsp;&nbsp;
		 <a href="${ctx}/webadmin/gotoaddEnuser" class="btn btn-success">添加 企业用户</a>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              <thead>
		              <tr>
		                  <th>编号</th>
		                  <th>登录名</th>
		                  <th>企业名称</th>
		                  <th>企业联系方式</th>
		                  <th>注册日期</th>
		                  <th>操作</th>
		              </tr>
	              </thead>
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>登录名</td>
	                    <td>企业名称</td>
	                    <td>企业联系方式</td>
	                    <td>注册日期</td>
	                    <td>查看详情</td>
	                </tr>
				
					<c:forEach items="${users.content}" var="user">
	              	<tr>
	                    <td>${user.id}</td>
	                    <td>${user.loginName}</td>
	                    <td>${user.enterprise.entName}</td>
	                    <td>${user.enterprise.phoneCall}</td>
	                    <td>${user.registerDate}</td>
	                    <td><a href="${ctx}/webadmin/showenuserinfo?enuserId=${user.id}">详情</a>|
	                    <a href="${ctx}/webadmin/delenuserinfo?enuserId=${user.id}" style="color:red;" 
	                    	onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a>
	                    
	                    </td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->
</body>
</html>
