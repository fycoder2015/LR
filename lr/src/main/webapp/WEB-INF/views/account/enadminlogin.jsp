<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
    

<!DOCTYPE html>
<html lang="en">

<body>

<!-- 幻灯开始 -->
<div class="banner-join"></div>
<!-- 幻灯结束 -->
<!-- 中间内容开始  -->
<div class="log">
    <div class="logDiv">
        <div class="logDivs">
            <div class="log1">
                <li>请登录已有账号</li>
            </div>
            <form action="${ctx}/login/enfromForm" method="post">
                <div class="log2">
                    <div class="log2left">
                        <img src="${ctx}/static/images/tel.png" />
                    </div>
                    <div class="log2right">
                        <input type="text" name="username" class="log2-1">
                    </div>
                </div>
                <div class="log2">
                    <div class="log2left">
                        <img src="${ctx}/static/images/pwd.png" />
                    </div>
                    <div class="log2right">
                        <input type="password" name="password" class="log2-1">
                    </div>
                </div>
                <div class="log2">
                    <div class="log3">
                        <input type="submit" value="登录" class="bt1">
                    </div>
                    <div class="log4">
                        <a href="/index.php?m=&c=Login&a=register" style="color:#ff8829;">点击注册</a> | <!--<a href="" style="color:#6e6e6e; text-decoration:underline;">忘记密码？</a>   -->
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 中间内容结束  -->

</body>
</html>