<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<div style="background-color: #f4a763; height: 70px; margin: 0 0 10px 0;">
    <div class="container">
        <nav class="navbar" >
            <a class="navbar-brand" style="margin-left: -25px;" href="#"><img src="${ctx}/static/images/logoadmin.png"><span style="color: #fff">指点精鹰管理系统</span> </a>
            <ul class="nav navbar-nav navbar-right navbar-user">
                <li class="dropdown user-dropdown">
                	<!--  
                	<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					-->
					<shiro:hasRole name="admin">
	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                    	<i class="fa fa-user"></i> 你好,admin <b class="caret"></b>
	                    </a>                    
	                    <ul class="dropdown-menu">
	                        <li><!--  <a href="#"><i class="fa fa-gear"></i> 设置</a>--></li>
	                        <li class="divider"></li>
	                        <li>
	                        <a href="${ctx}/logout"><i class="fa fa-power-off"></i>退出</a>
	                        <!-- 
	                        <a href="/admin.php?m=Admin&c=login&a=logout"><i class="fa fa-power-off"></i> 退出</a> 
	                        -->
	                        </li>
	                    </ul>                    
                    </shiro:hasRole>         

                </li>
            </ul>
        </nav>
    </div>
</div>



