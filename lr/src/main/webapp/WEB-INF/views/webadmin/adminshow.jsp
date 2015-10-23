<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>



<div class="container">
    <div class="row">
    
	    <div class="col-md-2" style="background-color: #f3f3f3;">
		    <ul class="nav" style="margin: 10px 0;">
			    <li><a href="/admin.php?m=Admin&c=post&a=index"><i class="fa fa-dashboard"></i> <strong> 兼职管理 <b class="caret"></b></strong></a></li>
			    <li><a href="/admin.php?m=Admin&c=category&a=index"><i class="fa fa-reorder"></i> 信息管理</a></li>
			    <li><a href="/admin.php?m=Admin&c=category&a=index"><i class="fa fa-edit"></i> 分类管理</a></li>
			    <li><a href="/admin.php?m=Admin&c=page&a=index"><i class="fa fa-file-text-o"></i> 兼职审核</a></li>
			    <li><a href="/admin.php?m=Admin&c=index&a=index"><i class="fa fa-dashboard"></i> <strong> 赏金任务管理 <b class="caret"></b></strong></a></li>
			    <li><a href="/admin.php?m=Admin&c=member&a=index"><i class="fa fa-users"></i> 任务管理</a></li>
			    <li><a href="/admin.php?m=Admin&c=links&a=index"><i class="fa fa-link"></i> 分类管理</a></li>
			    <li><a href="/admin.php?m=Admin&c=setting&a=index"><i class="fa fa-link"></i> 任务审核</a></li>
			    <li><a href="/admin.php?m=Admin&c=index&a=index"><i class="fa fa-dashboard"></i> <strong> 用户管理 <b class="caret"></b></strong></a></li>
			    <li><a href="${ctx}/webadmin/userlist"><i class="fa fa-reorder"></i> 用户列表</a></li>
			    <li><a href="/admin.php?m=Admin&c=post&a=index"><i class="fa fa-edit"></i> 用户统计</a></li>
			    <li><a href="/admin.php?m=Admin&c=index&a=index"><i class="fa fa-dashboard"></i> <strong> 系统管理 <b class="caret"></b></strong></a></li>
			    <li><a href="/admin.php?m=Admin&c=category&a=index"><i class="fa fa-reorder"></i> 校区管理</a></li>
			    <li><a href="/admin.php?m=Admin&c=post&a=index"><i class="fa fa-edit"></i> 企业管理</a></li>
			</ul>
	    </div><!-- col-md-2 end -->
	    
		<div class="col-md-10">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h2>欢迎使用管理后台</h2>
                    <p>1.系统基本功能如下,请根据实际需要来添加或者修改:</p>
                    <ul>
                        <li>用户管理</li>
                        <li>用户登陆注册</li>
                        <li>无限极分类</li>
                        <li>文章管理</li>
                        <li>单页管理</li>
                        <li>链接管理</li>
                    </ul>
                    <p>2.<code>Application/Extend</code>目录已内置微信第三方API,请根据实际情况调用</p>
                    <p>3.其他功能将在后续开发中慢慢完善,比如RBAC、插件扩展等...</p>
              	</div>
		</div><!-- col-md-10 end -->	

	</div><!-- row end -->
	
</div><!-- container end -->



</body>
</html>
