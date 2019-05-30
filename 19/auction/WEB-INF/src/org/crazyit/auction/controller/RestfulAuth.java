package org.crazyit.auction.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class RestfulAuth extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception
	{
		HttpSession sess = request.getSession();
		Integer userId = (Integer) sess.getAttribute("userId");
		if(userId != null && userId > 0)
		{
			// 继续向下执行
			return true;
		}
		else
		{
			response.setContentType("text/html; charset=utf-8");
			// 生成错误提示。
			response.getWriter().println("您还没有登录系统，请先登录系统！");
			return false;
		}
	}
}
