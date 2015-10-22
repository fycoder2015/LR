<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>网站管理员登录页</title>
</head>

<body>
	
	<div class="container">
	    <div class="row">
	        <div class="col-md-4 col-md-offset-4">
	            <h1>管理员登陆</h1>
				<!--  form id="loginForm" action="${ctx}/login/" method="post" class="form-horizontal"  
				<form id="loginForm" action="${ctx}/login/fromForm" method="post" class="form-horizontal" > -->
	            <form action="${ctx}/webadmin/fromForm" method="post">
	                <div class="form-group">
	                    <label for="exampleInputUser">用户名</label>
	                    <input type="text" name="username" class="form-control" id="exampleInputUser" placeholder="用户名">
	                </div>
	                <div class="form-group">
	                    <label for="exampleInputPassword">密码</label>
	                    <input type="password" name="password" class="form-control" id="exampleInputPassword" placeholder="密码">
	                </div>

	                <button type="submit" class="btn btn-default">登陆</button>
	            </form>
	        </div>
	    </div>
	</div>

</body>
</html>
