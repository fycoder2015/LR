<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<body>

<!-- 中间内容开始  -->
<div class="jz"  style="height:1100px;">
    <div class="jzleft">
        <div class="jzleftDiv">
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task/create">发布兼职</a> </div>
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task">兼职列表</a> </div>
    <div class="jzleftDivs" onclick="news(3)"><a style="color: #fff;" href="${ctx}/task/enterpriseForm">企业信息</a></div>
    <div class="jzleftDivs1" onclick="news(3)"><a style="color: #fff;" href="${ctx}/task/logoutt">退出系统</a></div>
</div>
    </div>
    <div class="jzright">
        <div class="dd">
            <div class="ddDivfoot">
                <!-- 发布兼职开始  -->
                <table id="contentTable" width="100%">
					<thead align="left"><tr><th>申请人</th><th>申请时间</th><th>申请状态</th><th>操作</th></tr></thead>
					<tbody>
						<c:forEach items="${applyList.content}" var="apply">
							<tr>
								<td>${apply.user.name}</td>
								<td>${apply.applyDate}</a></td>
								<td>
									<c:if test="${apply.sts.equals(\"C\")}">
										已取消
									</c:if>
									<c:if test="${apply.sts.equals(\"R\")}">
										已拒绝
									</c:if>
									<c:if test="${apply.sts.equals(\"W\")}">
										待通过
									</c:if>
									<c:if test="${apply.sts.equals(\"D\")}">
										已通过
									</c:if>
									<c:if test="${apply.sts.equals(\"P\")}">
										已到岗
									</c:if>
									<c:if test="${apply.sts.equals(\"M\")}">
										已结算
									</c:if>
								</td>
								<td>
									<c:if test="${apply.sts.equals(\"W\")}">
										<a href="${ctx}/task/confirmApply/${apply.id}">接受申请</a>/<a  href="${ctx}/task/refuseApply/${apply.id}">拒绝申请</a>
									</c:if>
									<c:if test="${apply.sts.equals(\"D\")}">
										<a href="${ctx}/task/inPosition/${apply.id}">确认到岗</a>
									</c:if>
									<c:if test="${apply.sts.equals(\"P\")}">
										<a href="${ctx}/task/confirmPayment/${apply.id}">确认已支付</a>
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
<tags:pagination page="${applyList}" paginationSize="5"/>
<!-- 中间内容结束  -->

</body>
</html>