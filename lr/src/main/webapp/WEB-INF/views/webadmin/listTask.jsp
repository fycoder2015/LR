<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>
		<div class="col-md-3">
		            <tags:pagination page="${tasks}" paginationSize="5"/>
		</div>

		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              
	              <tbody>
	              	<tr>
	                    <td>序号</td>
	                    <td>兼职标题</td>
	                    <td>招聘人数</td>
	                    <td>发布者</td>
	                    <td>发布日期</td>
	                    <td>审核状态</td>
	                    <td>&nbsp;</td>
	                </tr>
				
					<c:forEach items="${tasks.content}" var="task">
	              	<tr>
	                    <td>${task.id}</td>
	                    <td>${task.title}</td>
	                    <td>${task.employeeCnt}</td>
	                    <td>${task.user.loginName}</td>
	                    <td>${task.createTime}</td>
	                    <td>
	                    	<c:choose>
								<c:when test="${task.auditFlag==null||task.auditFlag==0}">待审核</c:when>
								<c:otherwise>已审核</c:otherwise>
							</c:choose>
						</td>
	                    <td>
	                    	<c:choose>
								<c:when test="${task.auditFlag==null||task.auditFlag==0}">
									<a href="${ctx}/webadmin/taskDetail?taskId=${task.id}">详情审核</a>
								</c:when>
								<c:otherwise>
									<a href="${ctx}/webadmin/taskDetail?taskId=${task.id}">详情</a>
								</c:otherwise>
							</c:choose>
	                    </td>
	                 </tr>
					</c:forEach>			
		 		</tbody>
	          </table>
	    </div><!-- col-md-10 end-->



</body>
</html>
