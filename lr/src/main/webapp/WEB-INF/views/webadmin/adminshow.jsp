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
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h2>欢迎使用管理后台</h2>
                    <p>1.系统基本功能如下,请根据实际需要来添加或者修改:</p>
                    <ul>
                        <li>兼职管理</li>
                        <li>赏金任务管理</li>
                        <li>用户管理</li>
                        <li>系统管理</li>     
                        <li></li>              
                    </ul>
                    <p>2.<code>Application/Extend</code>目录已内置微信第三方API,请根据实际情况调用</p>
                    <p>3.其他功能将在后面的维护中慢慢完善,比如RBAC、插件扩展等...</p>
              	</div>
		</div><!-- col-md-10 end -->	

</body>
</html>
