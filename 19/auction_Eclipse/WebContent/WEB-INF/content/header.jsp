<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#menu">
			<span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<!-- 标题栏  -->
		<a class="navbar-brand" href="#">
			<div>
				<img alt="图书管理" src="images/fklogo.gif"
				style="width:52px;height:52px">&nbsp;<strong>电子拍卖系统</strong>
			</div>
		</a>			
	</div>
	<div class="collapse navbar-collapse" id="menu">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/login">登录</a></li>
			<li><a href="${pageContext.request.contextPath}/viewSuccs">查看竞得物品</a></li>
			<li><a href="${pageContext.request.contextPath}/viewFails">浏览流拍物品</a></li>
			<li><a href="${pageContext.request.contextPath}/viewCategories">管理种类</a></li>
			<li><a href="${pageContext.request.contextPath}/viewOwnerItems">管理物品</a></li>
			<li><a href="${pageContext.request.contextPath}/viewInBid">浏览拍卖物品</a></li>
			<li><a href="${pageContext.request.contextPath}/viewBids">查看自己竞标</a></li>
			<li><a href="${pageContext.request.contextPath}/main">返回首页</a></li>
		</ul>
	</div>
</nav>
<div style="height: 50px;"></div>