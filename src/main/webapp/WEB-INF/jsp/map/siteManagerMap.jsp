<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'map.jsp' starting page</title>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf8">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">

<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5TmZTw10oplDe4ZehEM6UjnY6rDgocd8"></script>

<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
.map {
	position: relative;
	color: #000;
	width: 100%;
	height: 530px;
	overflow: auto;
}

.selectMenu {
	position: absolute;
	right: 20px;
	top: 5px;
}

.bottom_button {
	position: absolute;
	right: 2px;
	bottom: 0px;
}

.tablehead td {
	height: 10px;
	width: 10%;
	text-align: center;
	font-size: 12px;
	font-weight: 700;
	color: #4A708;
}

.tablelist td {
	height: 20px;
	width: 10%;
	text-align: center;
	font-size: 11px;
	font-weight: 600;
	color: #4A708;
}

.tablelist tr {
	cursor: pointer;
}
</style>
</head>

<body>
	<div id="allmap" class="map"></div>
	<div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h6 style="font-size:16px;font-weight:bold;" id="destination" class="modal-title"></h6>
                    </div>
                    <div class="modal-body">
                        <p id="editContext"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                        <button id="submitBtn" type="button" class="btn btn-primary">确定</button>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>
<script type="text/javascript">
	var map = new BMap.Map("allmap");
	window.map = map;

	map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
	map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	//	map.addControl(new BMap.MapTypeControl());          //添加地图类型控件
	map.disable3DBuilding();
	map.centerAndZoom("深圳市", 11);
	map.setMapStyle({
		style : 'light'
	}); //设置地图样式
	var siteMarker = new Array();
	var sitePoint = new Array();
	var siteInfoWindow = new Array();
	var carMarker = new Array();
	var carPoint = new Array();
	var carInfoWindow = new Array();
	var siteId=${sessionScope.user.siteId};
	var carStatus=-1;
	show();
	
	function show(){
		$.ajax({
  			type : "POST",
  			url : "site/querySiteMapBySiteId",
  			data : "siteId="+siteId,
  			success : function(site) {
  				if(jQuery.isEmptyObject(site)){
  					showMap(0);
  				}
  				else{
  					showMap(site);
  				}
  			}
  		});
  	}
	
  	//setInterval("showMap()",3000);  //定时刷新map
  	//setInterval("showNum()",3000);  //定时刷新空闲车辆及待处理站点数量
	/***************************** 显示标注************************************* */
	function showMap(site) {
		map.clearOverlays(); //清除地图上所有覆盖物
		sitePoint=[];
		siteMarker=[];
		siteInfoWindow=[];
		carPoint=[];
		carMarker=[];
		carInfoWindow=[];
		
		$.ajax({
				type : "POST",
				url : "system/queryAllSite",
				dataType : "json",
				contentType : "application/json",
				async: false,
				success : function(siteList) {

					var myIcon;
					$.each(siteList,function(i, site) {
						if (site.status== "0") {
							myIcon = new BMap.Icon("img/factory(purple).png", new BMap.Size(40, 26), {
							imageSize : new BMap.Size(40, 26)});
						}
						else if (site.status== "1") {
							myIcon = new BMap.Icon("img/factory(yellow).png", new BMap.Size(40, 26), {
							imageSize : new BMap.Size(40, 26)});
						} 
						else if (site.status== "2") {
							myIcon = new BMap.Icon("img/factory(red).png", new BMap.Size(40, 26), {
							imageSize : new BMap.Size(40, 26)});
						} 
						sitePoint[site.id] = new BMap.Point(site.longitude,site.latitude);
						siteMarker[site.id] = new BMap.Marker(sitePoint[site.id],{icon:myIcon});
						
						map.addOverlay(siteMarker[site.id]);
						if(site.id==id){
							siteMarker[site.id].setAnimation(BMAP_ANIMATION_BOUNCE);
							map.centerAndZoom(sitePoint[site.id], 13);
							siteInfo(site,id);
						} 
						siteMarker[site.id].addEventListener("mouseover",function(){
						siteInfo(site,id)
						});
					});
				}
			});
	}
	
	/***************************** 站点信息框显示************************************* */
	function siteInfo(site,id){
			var value=-1;
			var opts = {width : 230, }// 信息窗口宽度
			$.ajax({
				type : "POST",
				url : "system/queryUltrasonicValueBySite",
				data : {"sensorIdSet" : site.sensorIdSet},
				success : function(sensors) {
					var status;
					if (site.status== "0")
						status="正常";
					else if (site.status== "1")
						status="处理中";
					else if (site.status== "2")
						status="待处理";
					if(site.id==id){
						if(carStatus==3)
							var lid = '<div><h5><a onclick="go(\''+site.siteName+'\');">'+site.siteName+'(点击出发)</a></h5><table style="font-size:12px;">';
						if(carStatus==1)
							var lid = '<div><h5><a onclick="arrive(\''+site.siteName+'\');">'+site.siteName+'(点击到达)</a></h5><table style="font-size:12px;">';
					}
					else
						var lid = '<div><h5>'+site.siteName+'</h5><table style="font-size:12px;">';
					if(!jQuery.isEmptyObject(sensors)){
						{
							$.each(sensors,function(i, sensor) {
								if(sensor.typeId==3){
									var v=sensor.sensorValue.value;
									//alert(result.value);
									//污泥量
									value=100*(site.depth-v)/site.depth;
									lid += '<tr><td style="width:40%;text-align: left;">污泥量：</td><td style="text-align: left; color: #1874CD; font-weight: bold;">'+value.toFixed(2)+'%</td></tr>';
									return false;
								}
							});
						}
					}
					else if(jQuery.isEmptyObject(sensors) || value==-1)
						lid += '<tr><td style="width:40%;text-align: left;">污泥量：</td><td style="text-align: left;">无数据</td></tr>';	
					lid += '<tr><td style="width:40%;text-align: left;">Tel:</td><td style="text-align: left;">'+site.telephone+'</td>'
						+ '</tr><tr style="color:#FF4500;">'
						+ '<td style="width:40%;text-align: left;">状态:</td><td style="text-align: left;">'+status+'</td>'
						+ '</tr>' + '</table>' + '</div>';
					siteInfoWindow[site.id] = new BMap.InfoWindow(lid,opts); // 创建信息窗口对象 
					map.openInfoWindow(siteInfoWindow[site.id], sitePoint[site.id]);
					}
				}); 
		}
	function go(siteName){
		$("#destination").text("目的地："+siteName);
		$("#editContext").text("确定出发?");
		$("#myModal").modal('show');
	}
	
	function arrive(siteName){
		$("#destination").text("目的地："+siteName);
		$("#editContext").text("确定到达?");
		$("#myModal").modal('show');
	}
	
	$("#submitBtn").click(function(){
		var editStatus=-1;
		if(carStatus==1) editStatus=2;
		if(carStatus==3) editStatus=1;
		$.ajax({
  			type : "POST",
  			url : "car/editWorkerCarStatus",
  			data : "userId="+userId+"&status="+editStatus,
  			success : function(result) {
  				if(result.result=="success"){
  					if(carStatus==1) alert("已到达");
  					if(carStatus==3) alert("已出发");
  					$("#myModal").modal('hide');
  					show();
  				}
  				else{
  					alert("状态更改失败");
  				}
  			}
  		});
	});
</script>

