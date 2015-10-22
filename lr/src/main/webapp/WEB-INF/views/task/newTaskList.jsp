<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<body>

<!--  liuy 调整
<div class="banner-join"></div>
 -->
<!-- 中间内容开始  -->
<div class="jz"  style="height:1100px;">
    <div class="jzleft">
        <div class="jzleftDiv">
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="/index.php?m=&c=Company&a=index">发布兼职</a> </div>
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task">兼职列表</a> </div>
    <%--<div class="jzleftDivs" onclick="news(2)">兼职订单</div> --%>
    <div class="jzleftDivs" onclick="news(3)"><a style="color: #fff;" href="/index.php?m=&c=Company&a=info">企业信息</a></div>
    <div class="jzleftDivs1" onclick="news(3)"><a style="color: #fff;" href="/index.php?m=Admin&c=Login&a=logout">退出系统</a></div>
</div>
    </div>
    <div class="jzright">
        <div class="dd">
            <div class="ddDivtop">
                <div class="ddDiv1">正在招（10）</div>
                <div class="ddDiv2">待审核（4）</div>
                <div class="ddDiv3">已下架（1）</div>
                <div class="ddDiv4">已通过（3）</div>
            </div>
            <div class="ddDivfoot">
                <!-- 发布兼职开始  -->
                <table id="contentTable" width="100%">
					<thead align="left"><tr><th>Id</th><th>标题</th><th>发布时间</th><th>招聘人数</th><th>审核状态</th><th>开放状态</th><th></th><th>详情</th></tr></thead>
					<tbody>
						<c:forEach items="${tasks.content}" var="task">
							<tr>
								<td><a href="${ctx}/task/update/${task.id}">${task.id}</a></td>
								<td><a href="${ctx}/task/update/${task.id}">${task.title}</a></td>
								<td>${task.createTime}</td>
								<td>${task.employeeCnt}</td>
								
								<td>
									<c:choose>
										<c:when test="${task.auditFlag==null||task.auditFlag==0}">待审核</c:when>
										<c:otherwise>已审核</c:otherwise>
									</c:choose>
								
								</td>
								
								<td>${task.jobSts}</td>
								
								<td>
									<c:choose>
										<c:when test="${task.auditFlag==null||task.auditFlag==0}"><a href="${ctx}/task/update/${task.id}">编辑</a></c:when>
										<c:otherwise>编辑</c:otherwise>
									</c:choose>
									
								</td>
								
								<td>
									<c:if test="${task.jobSts.equals(\"开放\")}">
										<a href="${ctx}/task/close/${task.id}">
											停用
										</a>
									</c:if>
									<c:if test="${task.jobSts.equals(\"关闭\")}">
										<a href="${ctx}/task/open/${task.id}">
											开放
										</a>
									</c:if>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				
                <!-- 发布兼职结束  -->

            </div>
        </div>
    </div>
</div>
<tags:pagination page="${tasks}" paginationSize="5"/>
<!-- 中间内容结束  -->

</body>
</html>