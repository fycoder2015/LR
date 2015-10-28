<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>联系我们</title>
    <meta name="keywords" content="指点精鹰校园平台" />
    <meta name="description" content="指点精鹰校园平台" />
    <meta name="author" content="CmsEasy Team" />
	<link rel="stylesheet" href="/Application/Home/View/Public/static/css/style.css">
	<script type="text/javascript" src="/Application/Home/View/Public/static/js/init.js"></script>
    <link rel="stylesheet" href="/Application/Home/View/Public/static/css/base.css" type="text/css" media="all"  />
    <link rel="stylesheet" href="/Application/Home/View/Public/static/css/reset.css" type="text/css" media="all"  />
    <link rel="stylesheet" href="/Application/Home/View/Public/static/css/style.css" type="text/css" media="all"  />
    <script type="text/javascript" src="/Application/Home/View/Public/static/js/jquery-1.8.3.min.js"></script>
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
<!--  
-->
<!-- 中部开始 -->
<div class="mapbg">
    <div class="mapbgDiv">
        <div class="mapgbDivs">
            <div class="map">
                <style type="text/css">
                    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
                    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
                </style>
                <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
                <div style="width:960px;height:293px;border:#ccc solid 1px;" id="dituContent"></div>
                <script type="text/javascript">
                    //创建和初始化地图函数：
                    function initMap(){
                        createMap();//创建地图
                        setMapEvent();//设置地图事件
                        addMapControl();//向地图添加控件
                        addMarker();//向地图中添加marker
                    }

                    //创建地图函数：
                    function createMap(){
                        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
                        var point = new BMap.Point(117.127195,39.14169);//定义一个中心点坐标
                        map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中
                        window.map = map;//将map变量存储在全局
                    }

                    //地图事件设置函数：
                    function setMapEvent(){
                        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
                        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
                        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
                        map.enableKeyboard();//启用键盘上下左右键移动地图
                    }

                    //地图控件添加函数：
                    function addMapControl(){
                        //向地图中添加缩放控件
                        var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_SMALL});
                        map.addControl(ctrl_nav);
                    }

                    //标注点数组
                    var markerArr = [{title:"天津广川科技有限公司",content:"地址：天津市南开区南泥湾路世贸电商城A座408",point:"117.127195|39.14169",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}
                    ];
                    //创建marker
                    function addMarker(){
                        for(var i=0;i<markerArr.length;i++){
                            var json = markerArr[i];
                            var p0 = json.point.split("|")[0];
                            var p1 = json.point.split("|")[1];
                            var point = new BMap.Point(p0,p1);
                            var iconImg = createIcon(json.icon);
                            var marker = new BMap.Marker(point,{icon:iconImg});
                            var iw = createInfoWindow(i);
                            var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
                            marker.setLabel(label);
                            map.addOverlay(marker);
                            map.openInfoWindow(iw, map.getCenter());
                            label.setStyle({
                                borderColor:"#808080",
                                color:"#333",
                                cursor:"pointer"
                            });

                            (function(){
                                var index = i;
                                var _iw = createInfoWindow(i);
                                var _marker = marker;
                                _marker.addEventListener("click",function(){
                                    this.openInfoWindow(_iw);
                                });
                                _iw.addEventListener("open",function(){
                                    _marker.getLabel().hide();
                                })
                                _iw.addEventListener("close",function(){
                                    _marker.getLabel().show();
                                })
                                label.addEventListener("click",function(){
                                    _marker.openInfoWindow(_iw);
                                })
                                if(!!json.isOpen){
                                    label.hide();
                                    _marker.openInfoWindow(_iw);
                                }
                            })()
                        }
                    }
                    //创建InfoWindow
                    function createInfoWindow(i){
                        var json = markerArr[i];
                        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
                        return iw;
                    }
                    //创建一个Icon
                    function createIcon(json){
                        var icon = new BMap.Icon("/images/map/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
                        return icon;
                    }

                    initMap();//创建和初始化地图
                </script>  </div>
            <div class="liq">
                <li>地址：天津市 南开区 南泥湾路 世贸电商城A座408</li>
            </div>
        </div>
    </div>
</div>
<!-- 内容 -->
<div class="relation">
    <div class="relation-left">
        <div class="relations">
            <div class="title">
                <ul>
                    <li>客服服务</li>
                </ul>
            </div>
            <ul>
                <li style="padding-top:18px;">电话：400-808-6232</li>
                <li>邮箱：guangcd@aliyun.com</li>
            </ul>
        </div>
        <div class="relations">
            <div class="title">
                <ul>
                    <li>广告业务</li>
                </ul>
            </div>
            <ul>
                <li style="padding-top:18px;">联系人：赵女士</li>
                <li>电话：15122268631</li>
            </ul>
        </div>
    </div>
    <div class="relation-right">
        <div class="relations">
            <div class="title">
                <ul>
                    <li>电商业务</li>
                </ul>
            </div>
            <ul>
                <li style="padding-top:18px;">联系人：阚女士</li>
                <li>电话：15222872022</li>
            </ul>
        </div>
        <div class="relations">
            <div class="title">
                <ul>
                    <li>网络营销业务</li>
                </ul>
            </div>
            <ul>
                <li style="padding-top:18px;">联系人：张先生</li>
                <li>电话：18920502603</li>
            </ul>
        </div>
    </div></div>
<!-- 中部结束 -->

<!-- 底部开始  -->
<!-- 下载弹出框  -->

<div class="loadDiv-content" id="popDiv" style="display:none;">
    <div class="loadDiv1">
        <div class="loadDiv1-left">
            <li>下载指点精鹰</li>
        </div>
        <div class="loadDiv1-right">
            <a href="javascript:closeDiv()"><img src="/template/default/skin/images/cha.png" /></a>
        </div>
    </div>
    <div class="loadDiv2">
        <div class="loadDiv2te">
            <div class="te1">
                <img src="/template/default/skin/images/erweima.png" />
                <li>(扫一扫，立即安装求职版)</li>
            </div>
            <div class="te2">
                <img src="/template/default/skin/images/lode-shu.png" />
            </div>
            <div class="te3">
                <div class="te3-1">
                    <img src="/template/default/skin/images/azxz.png" />
                    <img src="/template/default/skin/images/app-lode.png" class="ts" />
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>