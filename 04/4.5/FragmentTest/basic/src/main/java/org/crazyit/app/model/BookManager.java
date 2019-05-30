package org.crazyit.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class BookManager
{
	// 定义一个内部类，作为系统的业务对象
	public static class Book
	{
		public Integer id;
		public String title;
		public String desc;
		public Book(Integer id, String title, String desc)
		{
			this.id = id;
			this.title = title;
			this.desc = desc;
		}
		@Override
		public String toString()
		{
			return title;
		}
	}
	// 使用List集合记录系统所包含的Book对象
	public static List<Book> ITEMS = new ArrayList<>();
	// 使用Map集合记录系统所包含的Book对象
	public static Map<Integer, Book> ITEM_MAP
			= new HashMap<>();
	static
	{
		// 使用静态初始化代码，将Book对象添加到List集合、Map集合中
		addItem(new Book(1, "疯狂Java讲义",  "十年沉淀的必读Java经典，" +
				"已被包括北京大学在内的大量双一流高校选做教材。"));
		addItem(new Book(2, "疯狂Android讲义",
				"Android学习者的首选图书，常年占据京东、当当、 " +
						"亚马逊3大网站Android销量排行榜的榜首"));
		addItem(new Book(3, "轻量级Java EE企业应用实战",
				"全面介绍Java EE开发的Struts 2、Spring、Hibernate/JPA框架"));
	}
	private static void addItem(Book book)
	{
		ITEMS.add(book);
		ITEM_MAP.put(book.id, book);
	}
}
