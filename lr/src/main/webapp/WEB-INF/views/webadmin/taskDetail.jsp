<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理员管理</title>
</head>

<body>
		<div class="col-md-10">
	          <table class="table table-hover table-striped">
	              
	              <tbody>
	              	<tr>
	                    <td>项目</td>
	                    <td>内容</td>
	                </tr>
					<tr>
	                    <td>任务标题</td>
	                    <td>${task.title}</td>
	                </tr>
	                <tr>
	                	<td>详情说明</td>
	                	<td>${task.description}</td>
	                </tr>
	                <tr>
	                	<td>性别要求</td>
	                	<td>${task.gender}</td>
	                </tr>
	                <tr>
	                	<td>时间要求</td>
	                	<td>${task.timeRquirement}</td>
	                </tr>
	                <tr>
	                	<td>工作区域</td>
	                	<td>${task.district}</td>
	                </tr>
	                <tr>
	                	<td>工资计算方式</td>
	                	<td>${task.paymentCalcWay}</td>
	                </tr>
	                <tr>
	                	<td>工资</td>
	                	<td>${task.payment}</td>
	                </tr>
	                <tr>
	                	<td>岗位要求</td>
	                	<td>${task.jobRequirements}</td>
	                </tr>
	                <tr>
	                	<td>联系电话</td>
	                	<td>${task.phoneCall}</td>
	                </tr>
	                <tr>
	                	<td>创建时间</td>
	                	<td>${task.createTime}</td>
	                </tr>
	                <tr>
	                	<td>兼职分类</td>
	                	<td>${task.jobClass}</td>
	                </tr>
	                <tr>
	                	<td>审核状态</td>
	                	<td>
	                		<c:choose>
								<c:when test="${task.auditFlag==null||task.auditFlag==0}">待审核</c:when>
								<c:otherwise>已审核</c:otherwise>
							</c:choose>
	                	</td>
	                </tr>
	                
	                <%-- <tr>
	                	<td></td>
	                	<td>${task.}</td>
	                </tr>
	                <tr>
	                	<td></td>
	                	<td>${task.}</td>
	                </tr> --%>
		 		</tbody>
	          </table>
	          <c:if test="${task.imageFileName!=null}">
						
				<img src="/upload/${task.imageFileName}" />
			</c:if>
			<tr>
			<td>
			<div>
			<%-- <div><a class="btn" href="${ctx}/webadmin/auditTask?taskId=${task.id}">通过审核</a></div> --%>
			<c:if test="${task.auditFlag==null||task.auditFlag==0}">
				<a class="btn" href="${ctx}/webadmin/auditTask?taskId=${task.id}">通过审核</a>
			</c:if>
			<%-- <input id="submit_btn" class="btn btn-primary" type="button" onclick="window.location.href('${ctx}/webadmin/auditTask?taskId=${task.id}')" value="通过审核"/>&nbsp; --%>	
			<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
			</td>
			</tr>
	    </div><!-- col-md-10 end-->
		


</body>
</html>
