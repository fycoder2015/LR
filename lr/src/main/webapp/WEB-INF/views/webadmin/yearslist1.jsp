<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>入学年份列表</title>
</head>

<body>


		<div class="col-md-3">
		<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div><br>
		</c:if>
		<c:if test="${not empty yearslist}">
		            <tags:pagination page="${yearslist}" paginationSize="5"/>
		</c:if>            
		            &nbsp;&nbsp;&nbsp;
		            
		            	<a href="${ctx}/webadmin/gotoaddYears" class="btn btn-success">
	            		添加入学年份
	            		</a>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              <thead>
		              <tr>
		                  <th>编号</th>
		                  <th>入学年份</th>
		                  <!--<th>地址</th>-->
		                  <th>操作</th> 
		              </tr>
	              </thead>
	              <tbody>
	 
				
					<c:forEach items="${yearslist.content}" var="year">
	              	<tr>
	                    <td>${year.id}</td>
	                    <td>${year.year}</td> 
	                    <td>
		                    <a href="${ctx}/webadmin/toedityearsinfo?yearsId=${year.id}">修改</a>|
		                    <a href="${ctx}/webadmin/delyearsIdinfo?yearsId=${year.id}"
		                    style="color:red;" onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a>
	                    </td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->



</body>
</html>
