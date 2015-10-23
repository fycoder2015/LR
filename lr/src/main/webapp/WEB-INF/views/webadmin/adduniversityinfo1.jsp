<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>




		<div class="col-md-3"><strong>增加大学详情 </strong><br><strong>&nbsp; </strong>
		</div>

		<div class="col-md-10">
			<form id="inputForm" action="${ctx}/webadmin/addUniversity" method="post"   enctype="multipart/form-data">			
			
			
				<table class="table table-hover table-striped">
	    			<tr>
		                 <td>学校（必填）:</td>
		                 <td>
		                 <input type="text"   name="university"  	 class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
					<tr>
		                 <td>所在城市（必填）:</td>
		                 <td>
		                 <input type="text"   name="city"  	 class="input-large required" minlength="2"/>	                 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点（必填）:</td>
		                 <td>
		                 <input type="text"   name="place"  	 class="input-large required" minlength="2"/>	                 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点经度:</td>
		                 <td>
		                 <input type="text"   name="longitude"    />	 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点纬度:</td>
		                 <td>
		                 <input type="text"   name="latitude"  />	 
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
