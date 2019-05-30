package org.crazyit.metalslug.comp;

import java.util.Random;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class Util
{
	public static Random random = new Random();
	// 返回一个0～range的随机数
	public static int rand(int range)
	{
		// 如果range为0，直接返回0
		if (range == 0)
			return 0;
		// 获取一个0～range之间的随机数
		return Math.abs(random.nextInt() % range);
	}
}
