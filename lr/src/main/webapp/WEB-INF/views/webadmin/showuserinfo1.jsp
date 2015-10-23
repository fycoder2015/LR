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
		</div><!-- col-md-2 end-->


		<div class="col-md-3"><strong>用户详情 </strong><br><strong>&nbsp; </strong>
		</div>

		<div class="col-md-10">
			<table class="table table-hover table-striped">
    			<tr>
	                 <td>用户username:</td>
	                 <td>${showuser.loginName}</td>
                </tr>
				<tr>
	                 <td>昵称:</td>
	                 <td>${showuser.name}</td>
                </tr>
                <tr>
	                 <td>性别:</td>
	                 <td>${showuser.sexy}</td>
                </tr>
                <tr>
	                 <td>年龄:</td>
	                 <td>${showuser.userage}</td>
                </tr>
                <tr>
	                 <td>签名:</td>
	                 <td>${showuser.usersign}</td>
                </tr>
                <tr>
	                 <td>星级:</td>
	                 <td>${showuser.userstarss}</td>
                </tr>
                <tr>
	                 <td>角色:</td>
	                 <td>${showuser.roles}</td>
                </tr>
                <tr>
	                 <td>手机号:</td>
	                 <td>${showuser.phonenumber}</td>
                </tr>
                <tr>
	                 <td>所在大学:</td>
	                 <td>${showuser.university}</td>
                </tr>
                <tr>
	                 <td>所学专业:</td>
	                 <td>${showuser.subject}</td>
                </tr>
                <tr>
	                 <td>入学年份:</td>
	                 <td>${showuser.years}</td>
                </tr>
                <tr>
	                 <td>注册日期:</td>
	                 <td>${showuser.registerDate}</td>
                </tr>
                			
			</table>   
			
			<div class="col-md-3">            
            	<a href="javascript :;" onClick="javascript :history.back(-1);" class="btn btn-success">
            		返回
            	</a>
        	</div>
        	
	    </div><!-- col-md-10 end-->
   
	</div><!-- row end -->
	
</div>


</body>
</html>
