<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>企业详情管理</title>
</head>

<body>




		<div class="col-md-3"><strong>企业详情 </strong><br><strong>&nbsp; </strong>
		</div>

		<div class="col-md-10">
			<table class="table table-hover table-striped">
    			<tr>
	                 <td>用户username:</td>
	                 <td>${showenuser.loginName}</td>
                </tr>
				<tr>
	                 <td>企业名称:</td>
	                 <td>${showenuser.name}</td>
                </tr>
                <tr>
	                 <td>分配日期:</td>
	                 <td>${showenuser.registerDate}</td>
                </tr>
                <tr>
	                 <td>企业名称:</td>
	                 <td>${showenuser.enterprise.entName}</td>
                </tr>
                <tr>
	                 <td>企业经理:</td>
	                 <td>${showenuser.enterprise.entManager}</td>
                </tr>
                <tr>
	                 <td>地址:</td>
	                 <td>${showenuser.enterprise.entAddress}</td>
                </tr>
                <tr>
	                 <td>联系方式:</td>
	                 <td>${showenuser.enterprise.phoneCall}</td>
                </tr>
               
                			
			</table>   
			
			<div class="col-md-3">            
            	<a href="javascript :;" onClick="javascript :history.back(-1);" class="btn btn-success">
            		返回
            	</a>
        	</div>
        	
	    </div><!-- col-md-10 end-->
   



</body>
</html>
