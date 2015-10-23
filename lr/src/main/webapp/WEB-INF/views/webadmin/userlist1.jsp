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


		<div class="col-md-3">
		            <tags:pagination page="${users}" paginationSize="5"/>
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
	                    <td>编辑 | 删除</td>
	                </tr>
				
					<c:forEach items="${users.content}" var="user">
	              	<tr>
	                    <td>${user.id}</td>
	                    <td>${user.loginName}</td>
	                    <td>${user.registerDate}</td>
	                    <td><a href="/admin.php?m=Admin&c=category&a=update&id=${user.id}">编辑</a> | <a href="/admin.php?m=Admin&c=category&a=delete&id=1" style="color:red;" onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a></td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->
   
	</div><!-- row end -->
	
</div>


</body>
</html>
