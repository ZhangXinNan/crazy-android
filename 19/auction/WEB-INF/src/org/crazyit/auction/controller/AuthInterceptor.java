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
public class AuthInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception
	{
		HttpSession sess = request.getSession();
		Integer userId = (Integer) sess.getAttribute("userId");
		if(userId != null && userId > 0)
		{
			return true;
		}
		else
		{
			request.setAttribute("errorTip", "请先登录本系统，然后才能使用电子拍卖系统");
			request.getRequestDispatcher("/WEB-INF/content/login.jsp").forward(request, response);
			return false;
		}
	}
}
