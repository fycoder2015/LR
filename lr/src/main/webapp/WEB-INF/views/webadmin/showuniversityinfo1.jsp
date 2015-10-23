<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>




		<div class="col-md-3"><strong>大学详情 </strong><br><strong>&nbsp; </strong>
		</div>

		<div class="col-md-10">
			<form id="inputForm" action="${ctx}/webadmin/updateUniversity" method="post"   enctype="multipart/form-data">
			
				<input type="hidden" name="id" value="${showuniversity.id}"/>
			
			
				<table class="table table-hover table-striped">
	    			<tr>
		                 <td>学校（必填）:</td>
		                 <td>
		                 <input type="text"   name="university"  value="${showuniversity.university}" class="input-large required" minlength="3"/>
		                 </td>
	                </tr>
					<tr>
		                 <td>所在城市（必填）:</td>
		                 <td>
		                 <input type="text"   name="city"  value="${showuniversity.city}" class="input-large required" minlength="2"/>	                 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点（必填）:</td>
		                 <td>
		                 <input type="text"   name="place" value="${showuniversity.place}" 	 class="input-large required" minlength="2"/>	                 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点经度:</td>
		                 <td>
		                 <input type="text"   name="longitude"  value="${showuniversity.longitude}"  />	 
		                 </td>
	                </tr>
	                <tr>
		                 <td>地点纬度:</td>
		                 <td>
		                 <input type="text"   name="latitude"  value="${showuniversity.latitude}"  />	 
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
