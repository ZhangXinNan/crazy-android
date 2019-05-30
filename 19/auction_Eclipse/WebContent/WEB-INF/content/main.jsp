<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>商业拍卖Java EE程序框架</title>
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
	<c:if test="${requestScope.tip != null}">
	<div class="alert alert-info" role="alert">${requestScope.tip}</div>
	</c:if>	
	<div class="container">
		<p>这仅仅是一个Java EE的框架程序。程序模拟一个简单电子拍卖站点的功能。</p>
		<p>
			程序的功能不是很复杂，但模拟了一个轻量级Java EE的程序结构， 技术包括：Bootstrap,Spring MVC,Spring,<br />
			Hibernate,JavaMail,整个应用使用Spring提供的DAO支持操作数据库,同时利用Spring的声明式事务,<br />
			Spring的邮件抽象层。程序中的权限检查使用Spring MVC的拦截器进行，也利用了Spring的任务调度抽象<br />
			Hibernate为底层的数据库访问提供支持,作为O/R Mapping框架使用。
		</p>
		<p>
			本程序的源代码随程序一起发布，版权属于疯狂Java联盟（http://www.crazyit.org）<br />
			任何个人可用来参考学习轻量级Java EE架构，规范，但请勿在本程序的基础上修改，用做任何商业用途。<br />
			本人保留依法追究相关责任的权利。转载和学习请保留此信息。
		</p>
	</div>
	</div>
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>