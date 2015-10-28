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
    <div class="jzleftDivs1" onclick="news(3)"><a style="color: #fff;" href="${ctx}/logout">退出系统</a></div>
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
                <!-- 发布兼职开始  -->
                <form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${task.id}"/>
                <div class="jzrightDiv" id="1" style="display:block;">
                    <div class="jzDiv">
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>兼职标题</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="title" class="jzs" value="${task.title}" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>行业分类</li>
                            </div>
                            <div class="jzDivsright">
                                <select class="jzs" name="jobClass">
                                    <option>点击按钮选择行业分类</option>
                                    <c:forEach items="${categories.content}" var="cate">
                                    	<option value="${cate.categoryName}" <c:if test="${task.jobClass.equals(cate.categoryName)}">selected</c:if>>${cate.categoryName}</option>
                                    </c:forEach>
                                    <%-- <option value="IT" <c:if test="${task.jobClass == \"IT\"}">selected</c:if>>IT</option>
                                    <option value="餐饮" <c:if test="${task.jobClass == \"餐饮\"}">selected</c:if>>餐饮</option> --%>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                                <li>详情描述</li>
                            </div>
                            <div class="jzDivs2right">
                                <textarea class="jzs2"  name="description" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">${task.description}</textarea>
                            </div>
                        </div>
                        
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                                <li>岗位要求</li>
                            </div>
                            <div class="jzDivs2right">
                                <textarea class="jzs2"  name="jobRequirements" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">${task.jobRequirements}</textarea>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>工作时间</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="timeRquirement" value="${task.timeRquirement}" class="jzs" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" />
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>人数 / 性别</li>
                            </div>
                            <div class="jzDivsright">
                            	<%--
                                <select class="jzs1">
                                    <option>请选择招聘人数</option>
                                    <option>10</option>
                                    <option>100</option>
                                </select>
                                 --%>
                                <input type="text" name="employeeCnt" value="${task.employeeCnt}" class="jzs" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#b5b5b5; font-size:13px; text-indent:1em;" /> 
                                <select class="jzs1" name="gender">
                                    <option>请选择男女限制</option>
                                    <option value="男" <c:if test="${task.gender == \"男\"}">selected</c:if>>男</option>
                                    <option value="女" <c:if test="${task.gender == \"女\"}">selected</c:if>>女</option>
                                    <option value="不限" <c:if test="${task.gender == \"不限\"}">selected</c:if>>不限</option>
                                </select>
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>所在区</li>
                            </div>
                            <div class="jzDivsright">
                                <select id="district" name="district"  class="jzs">
                                    <option value ="和平" <c:if test="${task.district == \"和平\"}">selected</c:if>>和平</option>
									<option value ="南开" <c:if test="${task.district == \"南开\"}">selected</c:if>>南开</option>
									<option value ="河西" <c:if test="${task.district == \"河西\"}">selected</c:if>>河西</option>
									<option value ="河北" <c:if test="${task.district == \"河北\"}">selected</c:if>>河北</option>
									<option value ="河东" <c:if test="${task.district == \"河东\"}">selected</c:if>>河东</option>
									<option value ="红桥" <c:if test="${task.district == \"红桥\"}">selected</c:if>>红桥</option>
									<option value ="西青" <c:if test="${task.district == \"西青\"}">selected</c:if>>西青</option>
									<option value ="东丽" <c:if test="${task.district == \"东丽\"}">selected</c:if>>东丽</option>
									<option value ="北辰" <c:if test="${task.district == \"北辰\"}">selected</c:if>>北辰</option>
									<option value ="津南" <c:if test="${task.district == \"津南\"}">selected</c:if>>津南</option>
									<option value ="塘沽" <c:if test="${task.district == \"塘沽\"}">selected</c:if>>塘沽</option>
									<option value ="汉沽" <c:if test="${task.district == \"汉沽\"}">selected</c:if>>汉沽</option>
									<option value ="大港" <c:if test="${task.district == \"大港\"}">selected</c:if>>大港</option>
									<option value ="宁河" <c:if test="${task.district == \"宁河\"}">selected</c:if>>宁河</option>
									<option value ="蓟县" <c:if test="${task.district == \"蓟县\"}">selected</c:if>>蓟县</option>
									<option value ="静海" <c:if test="${task.district == \"静海\"}">selected</c:if>>静海</option>
									<option value ="宝坻" <c:if test="${task.district == \"宝坻\"}">selected</c:if>>宝坻</option>
									<option value ="武清" <c:if test="${task.district == \"武清\"}">selected</c:if>>武清</option>
                                </select>
                            </div>
                        </div>
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>计算方式</li>
                            </div>
                            <div class="jzDivsright">
                                <select id="paymentCalcWay" name="paymentCalcWay"  class="jzs">
                                    <option value ="小时" <c:if test="${task.paymentCalcWay == \"小时\"}">selected</c:if>>小时</option>
									<option value ="天" <c:if test="${task.paymentCalcWay == \"天\"}">selected</c:if>>天</option>
									<option value ="周" <c:if test="${task.paymentCalcWay == \"周\"}">selected</c:if>>周</option>
									<option value ="月" <c:if test="${task.paymentCalcWay == \"月\"}">selected</c:if>>月</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>佣金</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="payment" value="${task.payment}" class="jzs"  onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>联系电话</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" name="phoneCall"  value="${task.phoneCall}" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>图片附件</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="file" class="jzs" name="imageFile" >
                            </div>
                        </div>
                        
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                            </div>
                            <div class="jzDivs2right">
                                <input class="jzs3" type="submit" value="修改" />
                                <input class="jzs3" type="button" onclick="history.back()" value="返回" />
                            </div>
                        </div>
                    </div>
                </div>
                </form>
                <!-- 发布兼职结束  -->

            </div>
        </div>
    </div>
</div>
<!-- 中间内容结束  -->

</body>
</html>