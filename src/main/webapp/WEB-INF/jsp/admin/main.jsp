<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>污泥车处理系统</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="favicon.ico">
	<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow:hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<a data-toggle="dropdown"> <span class="clear"> <span
								class="block m-t-xs" style="font-size:20px;"> <i
								class="fa fa-area-chart"></i> <strong class="font-bold">DSSys</strong>
							</span>
						</span>
						</a>
					</div>
					<div class="logo-element">DSSys</div>
				</li>

				<!-- 主页 -->
				<c:if test="${fn:contains(sessionScope.authos, '1') && !fn:contains(sessionScope.authos, '10') && !fn:contains(sessionScope.authos, '14')}">
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="main/map/map"> <i
							class="fa fa-home"></i> <span class="nav-label">主页</span>
					</a></li>
				</c:if>

				<!-- 司机主页 -->
				<c:if test="${fn:contains(sessionScope.authos, '10')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="main/map/workerMap"> <i
							class="fa fa-home"></i> <span class="nav-label">主页(司机)</span>
					</a></li>
				</c:if>
				
								<!-- 司机主页 -->
				<c:if test="${fn:contains(sessionScope.authos, '14')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="main/map/siteManagerMap"> <i
							class="fa fa-home"></i> <span class="nav-label">主页(工厂人员)</span>
					</a></li>
				</c:if>

				<!-- 个人信息 -->
				<c:if test="${fn:contains(sessionScope.authos, '2')}" >
					<li class="line dk"></li>
					<li><a href="#"><i class="fa fa-industry"></i> <span
							class="nav-label">个人信息</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="user/private/modifyUserInfo"><i
									class="fa fa-truck"></i>个人信息</a></li>
							<li><a class="J_menuItem" href="user/private/modifypwd"><i
									class="fa fa-recycle"></i>修改密码</a></li>
						</ul></li>
				</c:if>

				<!-- 系统管理-->
				<c:if test="${fn:contains(sessionScope.authos, '3')}" >
					<li class="line dk"></li>
					<li><a href="mailbox.html"><i class="fa fa-spin fa-cog"></i>
						<span class="nav-label">系统管理 </span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="system/jumpToStaff"><i
									class="fa fa-user"></i>人员信息</a></li>
							<li><a class="J_menuItem" href="car/carManage"><i
									class="fa fa-car"></i>车队信息</a></li>
							<li><a class="J_menuItem" href="system/siteManage"><i
									class="fa fa-institution"></i>站点管理</a></li>
							<li><a class="J_menuItem" href="sensor/jumpToDeviceManage"><i
									class="fa fa-gears"></i>设备管理</a></li>
						</ul></li>
				</c:if>
				
				<!-- 智慧泥仓 -->
				<c:if test="${fn:contains(sessionScope.authos, '12')}" >
					<li class="line dk"></li>
					<li><a href="#"><i class="fa fa-industry"></i> <span
						class="nav-label">智慧泥仓</span><span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="mudWareHouse/jumpTomudwarehouse"> <i
							class="fa fa fa-bar-chart-o"></i> <span class="nav-label">智慧泥仓</span>
					</a></li>
						<li><a class="J_menuItem" href="sludge/jumpToSludge"><i
								class="fa fa-recycle"></i>污泥出入记录</a></li>
					</ul></li>
					
				</c:if>

				<!-- 监控 -->
				<c:if test="${fn:contains(sessionScope.authos, '4')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="monitor/jumpToVideo"> <i
							class="fa fa fa-bar-chart-o"></i> <span class="nav-label">监控</span>
					</a></li>
				</c:if>

				<!-- 工作记录(司机) -->
				<c:if test="${fn:contains(sessionScope.authos, '5')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="record/recordOfOneDriver"> <i
							class="fa fa-book"></i> <span class="nav-label">工作记录(司机)</span>
					</a></li>
				</c:if>
				<!-- 一键报警(工厂) -->
				<c:if test="${fn:contains(sessionScope.authos, '6')}" >
				<li class="line dk"></li>
					<li><a class="J_menuItem" href="record/alert?siteId=${sessionScope.user.siteId }"> <i
							class="fa fa-book"></i> <span class="nav-label">污泥处理申请</span>
					</a></li>
				<li class="line dk"></li>
				</c:if>
				<!-- 污泥处理记录(工厂) -->
				<c:if test="${fn:contains(sessionScope.authos, '6')}" >				
				<li><a href="#"><i class="fa fa-industry"></i> <span
						class="nav-label">污泥处理记录(工厂)</span><span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="record/recordOfOneFactory?siteId=${sessionScope.user.siteId }"><i
								class="fa fa-truck"></i>处理记录</a></li>
						<li><a class="J_menuItem" href="sludge/jumpToSludgeOfOneFactory?siteId=${sessionScope.user.siteId }"><i
								class="fa fa-recycle"></i>污泥记录</a></li>
					</ul></li>
				</c:if>

				<!-- 记录(所有的处理记录) -->
				<c:if test="${fn:contains(sessionScope.authos, '7')}" >
					<li class="line dk"></li>
					<li><a href="#"><i class="fa fa-edit"></i> <span
							class="nav-label">记录</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a class="J_menuItem" href="record/jumpToRecord"><i
									class="fa fa-truck"></i>处理记录</a></li>
							<li><a class="J_menuItem" href="sludge/jumpToSludge"><i
									class="fa fa-recycle"></i>污泥记录</a></li>
						</ul></li>
				</c:if>

				<!-- 数据分析 -->
				<c:if test="${fn:contains(sessionScope.authos, '8')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="analyse/jumpToAnalyse"><i
							class="fa fa-database"></i> <span class="nav-label">数据分析</span></a>
					</li>
				</c:if>

				<!-- 收费系统 -->
				<c:if test="${fn:contains(sessionScope.authos, '9')}" >
					<li class="line dk"></li>
					<li><a class="J_menuItem" href="charge/jumpToCharge"> <i
							class="fa fa-book"></i> <span class="nav-label">费用清单</span>
					</a></li>
				</c:if>

				<!-- 退出系统 -->
				<li class="line dk"></li>
				<li><a class="J_menuItem" href="user/private/login"> <i
						class="fa fa-book"></i> <span class="nav-label">退出系统</span>
				</a></li>
				<li class="line dk"></li>

			</ul>
		</div>
	</nav>
	<!--左侧导航结束-->
	<!--右侧部分开始-->
	<div id="page-wrapper" class="gray-bg dashbard-1">
		<div class="row border-bottom">
			<nav class="navbar navbar-static-top" role="navigation"
				 style="margin-bottom: 0">
				<div class="navbar-header" style="width:90%; " >
					<a class="navbar-minimalize minimalize-styl-2 btn btn-info"><i
							class="fa fa-bars"></i> </a>
					<h1 class="text-info" style="text-align: center;font-family:Microsoft Yahei;">基于物联网的车载式污泥干化处理系统</h1>
				</div>
			</nav>
		</div>
		<div class="row J_mainContent" id="content-main">
		
		<c:if test="${fn:contains(sessionScope.authos, '1') && !fn:contains(sessionScope.authos, '10') && !fn:contains(sessionScope.authos, '14')}" >
			<iframe id="J_iframe" width="100%" height="100%" src="main/map/map"
					frameborder="0" data-id="index_v1.html" seamless></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionScope.authos, '10')}">
			<iframe id="J_iframe" width="100%" height="100%" src="main/map/workerMap"
					frameborder="0" data-id="index_v1.html" seamless></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionScope.authos, '14')}">
			<iframe id="J_iframe" width="100%" height="100%" src="main/map/siteManagerMap"
					frameborder="0" data-id="index_v1.html" seamless></iframe>
		</c:if>
		</div>
	</div>
	<!--右侧部分结束-->
</div>

<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="js/hAdmin.js?v=4.1.0"></script>
<script type="text/javascript" src="js/index.js"></script>

<!-- 第三方插件 -->
<script src="js/plugins/pace/pace.min.js"></script>

</body>

</html>