<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>指点精鹰后台管理系统<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="keywords" content="指点精鹰校园平台" />
<meta name="description" content="指点精鹰校园平台" />
<meta name="author" content="Fycoder Team" />


<!-- Bootstrap core CSS -->
<link href="${ctx}/static/cssadmin/bootstrap.css" rel="stylesheet">
<!-- Add custom CSS here -->
<link href="${ctx}/static/cssadmin/sb-admin.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/cssadmin/font-awesome.min.css">

<sitemesh:head/>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/adminheader.jsp"%>
	
		<!-- liuy add 1
		<div id="content">
		 -->
		 <div class="container">
    		<div class="row">	
    			<div class="col-md-2" style="background-color: #f3f3f3;">
				    <ul class="nav" style="margin: 10px 0;">
						<li><a href=""><i class="fa fa-dashboard"></i> <strong> 兼职管理 <b class="caret"></b></strong></a></li>
					    <li><a href="${ctx}/webadmin/listTask"><i class="fa fa-file-text-o"></i> 兼职审核</a></li>
					    <li><a href="${ctx}/webadmin/listTaskCate"><i class="fa fa-reorder"></i> 兼职分类管理</a></li>
					    <li><a href=""><i class="fa fa-dashboard"></i> <strong> 赏金任务管理 <b class="caret"></b></strong></a></li>
					    <li><a href="${ctx}/webadmin/listBounty"><i class="fa fa-link"></i> 赏金任务审核</a></li>
					    <li><a href="${ctx}/webadmin/listBountyCate"><i class="fa fa-link"></i> 赏金任务分类管理</a></li>
					    <li><a href="/admin.php?m=Admin&c=index&a=index"><i class="fa fa-dashboard"></i> <strong> 用户管理 <b class="caret"></b></strong></a></li>
					    <li><a href="${ctx}/webadmin/userlist"><i class="fa fa-reorder"></i> 用户列表</a></li>
					    <li><a href="/admin.php?m=Admin&c=post&a=index"><i class="fa fa-edit"></i> 用户统计</a></li>
					    <li><a href="/admin.php?m=Admin&c=index&a=index"><i class="fa fa-dashboard"></i> <strong> 系统管理 <b class="caret"></b></strong></a></li>
					    <li><a href="${ctx}/webadmin/universitylist"><i class="fa fa-reorder"></i> 校区管理</a></li>
					    <li><a href="${ctx}/webadmin/enuserlist"><i class="fa fa-edit"></i> 企业管理</a></li>
					</ul>
	    		</div><!-- col-md-2 end -->	
	    		 
				<sitemesh:body/>
				
			</div>
		</div>	
		<!-- liuy add 2
		</div>
		-->
		<%@ include file="/WEB-INF/layouts/adminfooter.jsp"%>
	</div>
	<!-- old  有下面这句  -->
	<!-- 
	<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
	 -->
</body>
</html>