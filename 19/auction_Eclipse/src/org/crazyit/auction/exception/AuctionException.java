package org.crazyit.auction.exception;

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
public class AuctionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	// 定义一个无参数的构造器
	public AuctionException()
	{
	}
	// 定义一个带message参数的构造参数
	public AuctionException(String message)
	{
		super(message);
	}
}
