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
	                    <td>赏金任务标题</td>
	                    <td>${bounty.title}</td>
	                </tr>
	                <tr>
	                	<td>详情说明</td>
	                	<td>${bounty.description}</td>
	                </tr>
	                <tr>
	                	<td>发布者</td>
	                	<td>${bounty.user.loginName}</td>
	                </tr>
	                
	                <tr>
	                	<td>性别要求</td>
	                	<td>${bounty.gender}</td>
	                </tr>
	                <tr>
	                	<td>发布时间</td>
	                	<td>${bounty.createDate}</td>
	                </tr>
	                <tr>
	                	<td>任务时间</td>
	                	<td>${bounty.taskDate}</td>
	                </tr>
	                <tr>
	                	<td>关闭时间</td>
	                	<td>${bounty.closeDate}</td>
	                </tr>
	                <tr>
	                	<td>任务地点</td>
	                	<td>${bounty.taskLocation}</td>
	                </tr>
	                <tr>
	                	<td>酬谢方式</td>
	                	<td>${bounty.paymentType}</td>
	                </tr>
	                <tr>
	                	<td>赏金额</td>
	                	<td>${bounty.bounty}</td>
	                </tr>
	                <tr>
	                	<td>审核状态</td>
	                	<td>
	                		<c:choose>
								<c:when test="${bounty.auditFlag==null||bounty.auditFlag==0}">待审核</c:when>
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
	          
			<tr>
			<td>
			<div>
			<%-- <div><a class="btn" href="${ctx}/webadmin/auditTask?taskId=${task.id}">通过审核</a></div> --%>
			<c:if test="${bounty.auditFlag==null||bounty.auditFlag==0}">
				<a class="btn" href="${ctx}/webadmin/auditBounty?bountyId=${bounty.id}">通过审核</a>
			</c:if>
			<%-- <input id="submit_btn" class="btn btn-primary" type="button" onclick="window.location.href('${ctx}/webadmin/auditTask?taskId=${task.id}')" value="通过审核"/>&nbsp; --%>	
			<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
			</td>
			</tr>
	    </div><!-- col-md-10 end-->
		


</body>
</html>
