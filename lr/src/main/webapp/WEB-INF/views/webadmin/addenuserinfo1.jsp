<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>




		<div class="col-md-3"><strong>增加企业用户</strong><br><strong>&nbsp; </strong>
		</div>

		<div class="col-md-10">
			<form id="inputForm" action="${ctx}/webadmin/addEnuser" method="post"   enctype="multipart/form-data">			
			
			
				<table class="table table-hover table-striped">
	    			<tr>
		                 <td>登录名（loginName）:</td>
		                 <td>
		                 <input type="text"   name="loginName"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
           		    <tr>
		                 <td>企业名称:</td>
		                 <td>
		                 <input type="text"   name="name"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
           		    <tr>
		                 <td>密码:</td>
		                 <td>
		                 <input type="password"   name="plainPassword"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>

	           		<tr>
		                 <td>企业地址:</td>
		                 <td>
		                 <input type="text"   name="entAddress"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
	                <tr>
		                 <td>企业管理员名称:</td>
		                 <td>
		                 <input type="text"   name="entManager"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
	                <tr>
		                 <td>企业联系电话:</td>
		                 <td>
		                 <input type="text"   name="phoneCall"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>	                			
				</table>   
				
				<div class="col-md-3">   
					<input id="submit_btn" class="btn btn-success" type="submit" value="提交"/>&nbsp;	 &nbsp;	 
					        
	            	<a href="javascript :;" onClick="javascript :history.back(-1);" class="btn btn-success">
	            		返回
	            	</a>
	        	</div>
        	
        	</form>  
        	
        	
	    </div><!-- col-md-10 end-->
   



</body>
</html>
