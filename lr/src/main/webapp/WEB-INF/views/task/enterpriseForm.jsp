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
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task/create">发布兼职</a> </div>
    <div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task">兼职列表</a> </div>
    <%--<div class="jzleftDivs" onclick="news(2)">兼职订单</div> --%>
    <div class="jzleftDivs" onclick="news(3)"><a style="color: #fff;" href="${ctx}/task/enterpriseForm">企业信息</a></div>
    <div class="jzleftDivs1" onclick="news(3)"><a style="color: #fff;" href="/index.php?m=Admin&c=Login&a=logout">退出系统</a></div>
</div>
    </div>
    <div class="jzright">
        <div class="dd">
        <%--
            <div class="ddDivtop">
                <div class="ddDiv1">正在招（10）</div>
                <div class="ddDiv2">待审核（4）</div>
                <div class="ddDiv3">已下架（1）</div>
                <div class="ddDiv4">已通过（3）</div>
            </div>
             --%>
            <div class="ddDivfoot">
                
                <form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal">
                <input type="hidden" value="${user.id}">
                <div class="jzrightDiv" id="1" style="display:block;">
                    <div class="jzDiv">
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>企业名称</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="entName" class="jzs" value="${user.enterprise.entName}" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>负责人</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="entManager" class="jzs" value="${user.enterprise.entManager}" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>地址</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="entAddress" class="jzs" value="${user.enterprise.entAddress}" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>联系电话</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="phoneCall" class="jzs" value="${user.enterprise.phoneCall}" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        
                        
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                            </div>
                            <div class="jzDivs2right">
                                <input class="jzs3" type="submit" value="修改" />
                                <input class="jzs3" type="button" value="返回" onclick="history.back()"/>
                                <!-- <input class="jzs4" type="submit" value="重置" /> -->
                            </div>
                        </div>
                    </div>
                </div>
                </form>


            </div>
        </div>
    </div>
</div>
<!-- 中间内容结束  -->

</body>
</html>