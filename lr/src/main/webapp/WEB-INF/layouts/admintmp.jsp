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
			<sitemesh:body/>
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