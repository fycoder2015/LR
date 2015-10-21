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
    <div class="jzleftDivs" onclick="news(2)">兼职订单</div>
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
                <div class="jzrightDiv" id="1" style="display:block;">
                    <div class="jzDiv">
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>兼职名称</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入兼职名称" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>行业分类</li>
                            </div>
                            <div class="jzDivsright">
                                <select class="jzs">
                                    <option>点击按钮选择行业分类</option>
                                    <option>IT</option>
                                    <option>餐饮</option>
                                </select>
                            </div>
                        </div>
                        <div class="jzDivs1">
                            <div class="jzDivs1left">
                                <li>特色标签</li>
                            </div>
                            <div class="jzDivs1right">
                                <select class="jzs">
                                    <option>点击按钮选择特色标签</option>
                                    <option>IT</option>
                                    <option>餐饮</option>
                                </select>
                                <input type="text" class="jzDivs1right-in" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>佣金</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入佣金数额" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>工作时间</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入工作时间" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>人数 / 性别</li>
                            </div>
                            <div class="jzDivsright">
                                <select class="jzs1">
                                    <option>请选择招聘人数</option>
                                    <option>10</option>
                                    <option>100</option>
                                </select>
                                <select class="jzs1">
                                    <option>请选择男女限制</option>
                                    <option>男</option>
                                    <option>女</option>
                                </select>
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>企业名称</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入企业名称" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>企业负责</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入企业负责" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>工作地点</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入工作地点" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>联系人</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入联系人姓名" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>企业邮箱</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入企业邮箱" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>联系电话</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" value="请输入联系电话" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                                <li>兼职详情</li>
                            </div>
                            <div class="jzDivs2right">
                                <textarea class="jzs2"></textarea>
                            </div>
                        </div>
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                            </div>
                            <div class="jzDivs2right">
                                <input class="jzs3" type="submit" value="发布" />
                                <input class="jzs4" type="submit" value="重置" />
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 发布兼职结束  -->

            </div>
        </div>
    </div>
</div>
<!-- 中间内容结束  -->

</body>
</html>