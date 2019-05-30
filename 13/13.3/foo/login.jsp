<%--
网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
author  yeeku.H.lee kongyeeku@163.com
version  1.0
Copyright (C), 2001-2018, yeeku.H.Lee
This program is protected by copyright laws.
Program Name:
Date: 
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
String name = request.getParameter("name");
String pass = request.getParameter("pass");
if (name.equals("crazyit.org")
	&& pass.equals("leegang"))
{
	session.setAttribute("user" , name);
	out.println("恭喜您，登录成功！");
}
else
{
	out.println("对不起，用户名、密码不符合！");
}
%>

