<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
	<title>兼职信息发布</title>
    <meta name="keywords" content="指点精鹰校园平台">
    <meta name="description" content="指点精鹰校园平台">
    <meta name="author" content="CmsEasy Team">
	<link rel="stylesheet" href="../static/css/style.css">
	<%--
	<script type="text/javascript" src="./css/init.js"></script>
	 --%>
    <link rel="stylesheet" href="../static/css/base.css" type="text/css" media="all">
    <link rel="stylesheet" href="../static/css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="../static/css/style.css" type="text/css" media="all">
    <!-- 
    <script type="text/javascript" src="./css/jquery-1.8.3.min.js"></script>
     -->
    <script language="javascript" type="text/javascript">
        function killerrors()
        {
            return true;
        }
        window.onerror = killerrors;
    </script>
    <!-- 下载APP -->
    <script language="javascript" type="text/javascript">
        function showDiv(){
            document.getElementById('popDiv').style.display='block';
            document.getElementById('popIframe').style.display='block';
            document.getElementById('bg').style.display='block';
        }
        function closeDiv(){
            document.getElementById('popDiv').style.display='none';
            document.getElementById('bg').style.display='none';
            document.getElementById('popIframe').style.display='none';

        }
    </script>
</head>

<body>
<div class="header">
    <div class="headerDiv">
        <div class="headerDivs">
            <div class="headerDivsleft"><img src="../static/css/logo.png"></div>
            <div class="headerDivsright">
                <!-- 网站导航 -->
                <div class="nav">
                    <ul id="nav">
                        <li class="one on"><a title="返回首页" style="color:#FFF;background:#ff8829;" href="http://ts.zhidianjingying.com/index.php?m=&c=Index&a=index">网站首页</a></li>
                        <li class="one"><a href="http://ts.zhidianjingying.com/index.php?m=&c=Page&a=contact" title="联系我们" target="">联系我们</a></li>
                        <li class="one"><a href="./css/兼职信息发布.html" title="发布兼职" target="">发布兼职</a></li>
                        <li class="one"><a href="http://ts.zhidianjingying.com/index.php?m=&c=Page&a=join" title="加入我们" target="">加入我们</a></li>
                    </ul>
                </div>
                <!-- 导航结束 -->
            </div>
        </div>
    </div>
</div>
<%--
<div class="banner-join"></div>
 --%>
<!-- 中间内容开始  -->
<div class="jz" style="height:1100px;">
	
    <div class="jzleft">
        <div class="jzleftDiv">
    		<div class="jzleftDivs" onclick="news(1)"><a style="color: #fff;" href="${ctx}/task/pageUserTask/1">兼职列表</a></div>
    		<div class="jzleftDivs" onclick="news(2)"><a style="color: #fff;" href="${ctx}/task/${action}">发布兼职</a> </div>
    		<div class="jzleftDivs" onclick="news(3)">兼职订单</div>
    		<div class="jzleftDivs" onclick="news(4)"><a style="color: #fff;" href="http://ts.zhidianjingying.com/index.php?m=&c=Company&a=info">企业信息</a></div>
    		<div class="jzleftDivs1" onclick="news(5)"><a style="color: #fff;" href="http://ts.zhidianjingying.com/index.php?m=Admin&c=Login&a=logout">退出系统</a></div>
		</div>
    </div>
    
    <div class="jzright">
        <div class="dd">
        	<%--
            <div class="ddDivtop">
                <div class="ddDiv1">正在招收（20）</div>
                <div class="ddDiv2">待审核（4）</div>
                <div class="ddDiv3">已下架（1）</div>
                <div class="ddDiv4">已通过（3）</div>
            </div>
             --%>
            <div class="ddDivfoot">
                <!-- 发布兼职开始  -->
                <form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
                <div class="jzrightDiv" id="1" style="display:block;">
                    <div class="jzDiv">
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>兼职标题</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" name="title" value="请输入兼职标题" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>行业分类</li>
                            </div>
                            <div class="jzDivsright">
                                <select class="jzs" name="jobClass">
                                    <option>点击按钮选择行业分类</option>
                                    <option value ="IT">IT</option>
                                    <option value ="餐饮">餐饮</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                                <li>兼职详情</li>
                            </div>
                            <div class="jzDivs2right">
                                <textarea class="jzs2"  name="description"></textarea>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>性别要求</li>
                            </div>
                            <div class="jzDivsright">
                                <select class="jzs" name="gender">
                                    <option value ="男性">男性</option>
									<option value ="女性">女性</option>
									<option value ="不限">不限</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>工作性质</li>
                            </div>
                            <div class="jzDivsright">
                                <select id="jobType" name="jobType" class="jzs">
                                    <option value ="全职">全职</option>
									<option value ="兼职">兼职</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>工作时间</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" name="timeRquirement" value="输入工作日期时间范围" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>所在区</li>
                            </div>
                            <div class="jzDivsright">
                                <select id="district" name="district"  class="jzs">
                                    <option value ="和平">和平</option>
									<option value ="南开">南开</option>
									<option value ="河西">河西</option>
									<option value ="河北">河北</option>
									<option value ="河东">河东</option>
									<option value ="红桥">红桥</option>
									<option value ="西青">西青</option>
									<option value ="东丽">东丽</option>
									<option value ="北辰">北辰</option>
									<option value ="津南">津南</option>
									<option value ="塘沽">塘沽</option>
									<option value ="汉沽">汉沽</option>
									<option value ="大港">大港</option>
									<option value ="宁河">宁河</option>
									<option value ="蓟县">蓟县</option>
									<option value ="静海">静海</option>
									<option value ="宝坻">宝坻</option>
									<option value ="武清">武清</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>计算方式</li>
                            </div>
                            <div class="jzDivsright">
                                <select id="paymentCalcWay" name="paymentCalcWay"  class="jzs">
                                    <option value ="小时">小时</option>
									<option value ="天">天</option>
									<option value ="周">周</option>
									<option value ="月">月</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>薪酬</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" name="payment" class="jzs" value="请输入佣金数额" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        

                        <div class="jzDivs2">
                            <div class="jzDivs2left">
                                <li>岗位要求</li>
                            </div>
                            <div class="jzDivs2right">
                                <textarea class="jzs2"  name="jobRequirements"></textarea>
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>联系电话</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" name="phoneCall" value="请输入联系电话" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
                            </div>
                        </div>
                        
                        <div class="jzDivs">
                            <div class="jzDivsleft">
                                <li>雇佣人数</li>
                            </div>
                            <div class="jzDivsright">
                                <input type="text" class="jzs" name="employeeCnt" value="请输入雇佣人数" onfocus="if(value==defaultValue){value=&#39;&#39;;this.style.color=&#39;#000&#39;}" onblur="if(!value){value=defaultValue;this.style.color=&#39;#999&#39;}" style="color:#b5b5b5; font-size:13px; text-indent:1em;">
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
                                <input class="jzs3" type="submit" value="发布">
                                <input class="jzs4" type="submit" value="重置">
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
<!-- 底部开始  -->
  <div class="foot" style="margin: 10px 0 0 0;">
    <div class="footDiv">
	  <li>天津广川科技有限公司版权所有@2015</li>
	</div>
  </div>
<!-- 底部结束  -->

</body></html>