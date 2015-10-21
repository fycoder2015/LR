<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>指点精鹰校园平台<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="keywords" content="指点精鹰校园平台" />
<meta name="description" content="指点精鹰校园平台" />
<meta name="author" content="Fycoder Team" />

<!--  
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
-->

<!--  -->    
<link rel="stylesheet" href="${ctx}/static/css/zdstyle.css">
<script type="text/javascript" src="${ctx}/static/js/init.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/zdbase.css" type="text/css" media="all"  />
<link rel="stylesheet" href="${ctx}/static/css/zdreset.css" type="text/css" media="all"  />
<link rel="stylesheet" href="${ctx}/static/css/zdstyle.css" type="text/css" media="all"  />
<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
<script language="javascript" type="text/javascript">
        function killerrors()
        {
            return true;
        }
        window.onerror = killerrors;
</script>
<!-- 下载APP -->
    <script language="javascript" type="text/javascript">
        function showDiv(){
            document.getElementById('popDiv').style.display='block';
            document.getElementById('popIframe').style.display='block';
            document.getElementById('bg').style.display='block';
        }
        function closeDiv(){
            document.getElementById('popDiv').style.display='none';
            document.getElementById('bg').style.display='none';
            document.getElementById('popIframe').style.display='none';

        }
</script>

<sitemesh:head/>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div class="banner-join"></div>
		<!-- liuy add 1
		<div id="content">
		 -->
			<sitemesh:body/>
		<!-- liuy add 2
		</div>
		-->
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<!-- old  有下面这句  -->
	<!-- 
	<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
	 -->
</body>
</html>