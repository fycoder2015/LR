<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!-- 
<div id="header">
	<div id="title">
	    <h1><a href="${ctx}">赏金猎人</a><small>--服务端开发版</small>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/api">APIs</a></li>
					<li><a href="${ctx}/profile">Edit Profile</a></li>
					<li><a href="${ctx}/logout">Logout</a></li>
				</ul>
			</div>
		</shiro:user>
		</h1>
	</div>
</div>

-->

<!-- 新 -->
<div class="header">
    <div class="headerDiv">
        <div class="headerDivs">
            <div class="headerDivsleft"><img src="${ctx}/static/images/logo.png"></div>
            <div class="headerDivsright">
                <!-- 网站导航 -->
                <div class="nav">
                    <ul id="nav">
                        <li class="one on"><a title="返回首页" style="color:#FFF;background:#ff8829;"<%--  href="/index.php?m=&c=Index&a=index"  --%>>网站首页</a></li>
                        <li class="one"><a href="#" title="联系我们" target="">联系我们</a></li>
                        <li class="one"><a href="#" title="发布兼职" target="">发布兼职</a></li>
                        <li class="one"><a href="#" title="加入我们" target="">加入我们</a></li>
                    </ul>
                </div>
                <!-- 导航结束 -->
            </div>
        </div>
    </div>
</div>






