<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>学院列表</title>
</head>

<body>


		<div class="col-md-3">
		<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div><br>
		</c:if>
		<c:if test="${not empty subjectlists}">
		            <tags:pagination page="${subjectlists}" paginationSize="5"/>
		</c:if>            
		            &nbsp;&nbsp;&nbsp;
		            
		            	<a href="${ctx}/webadmin/gotoaddSubject?universityId=${universityId}" class="btn btn-success">
	            		添加学院
	            		</a>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              <thead>
		              <tr>
		                  <th>编号</th>
		                  <th>校名</th>
		                  <!--<th>地址</th>-->
		                  <th>操作</th> 
		              </tr>
	              </thead>
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>学院名称</td>
	                     <!--<td>所在地区</td>-->
	                    <td>查看详情</td>
	                </tr>
				
					<c:forEach items="${subjectlists.content}" var="subject">
	              	<tr>
	                    <td>${subject.id}</td>
	                    <td>${subject.subject}</td> 
	                    <td>
		                    <a href="${ctx}/webadmin/toeditsubjectinfo?subjectId=${subject.id}&universityId=${universityId}">修改</a>|
		                    <a href="${ctx}/webadmin/delsubjectinfo?subjectId=${subject.id}&universityId=${universityId}"
		                    style="color:red;" onclick="javascript:return del('您真的确定要删除吗？\n\n删除后将不能恢复!');">删除</a>
	                    </td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->



</body>
</html>
