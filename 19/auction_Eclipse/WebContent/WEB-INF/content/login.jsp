<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>登录系统</title>
	<!-- 导入Bootstrap 3的CSS样式的样式  -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />	
	<!-- 导入Bootstrap 3的JS插件所依赖的jQuery库  -->
	<script src="jquery/jquery-3.1.1.js"></script>
	<!-- 导入Bootstrap 3的JS插件库 -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
	<div id="data" style="margin-top: 10px;">
	<c:if test="${requestScope.errorTip != null}">
	<div class="alert alert-warning" role="alert">${requestScope.errorTip}</div>
	</c:if>
	<div class="container">
		<!-- 登录界面  -->
		<div class="page-header">
			<h1>用户登录</h1>
		</div>
		<form class="form-horizontal" method="post" action="proLogin">
			<div class="form-group">
				<div class="col-sm-6">
					<input class="form-control" placeholder="用户名/邮箱" type="text"
						id="loginUser" name="username" 
						required minlength="3" maxlength="10"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-6">
					<input class="form-control" placeholder="密码" id="loginPass"
						type="password" name="userpass" 
						required minlength="3" maxlength="10"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-6">
					<div class="input-group">
						<input class="form-control " id="vercode" name="vercode"
							type="text" placeholder="验证码"
							required pattern="\w{6}">
							<span class="input-group-addon" id="basic-addon2">
							验证码：<img src="auth.jpg" name="d" id="d" alt="验证码"/>
						</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-6">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="...">
						<div class="btn-group" role="group">
							<button type="submit" id="loginBtn" class="btn btn-success">
								<span class="glyphicon glyphicon-log-in"></span>&nbsp;登录
							</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	</div>
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>