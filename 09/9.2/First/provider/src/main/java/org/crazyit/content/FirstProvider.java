package org.crazyit.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

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
public class FirstProvider extends ContentProvider
{
	// 第一次创建该ContentProvider时调用该方法
	@Override
	public boolean onCreate()
	{
		System.out.println("===onCreate方法被调用===");
		return true;
	}
	// 该方法的返回值代表了该ContentProvider所提供数据的MIME类型
	@Override
	public String getType(Uri uri)
	{
		return null;
	}
	// 实现查询方法，该方法应该返回查询得到的Cursor
	@Override
	public Cursor query(Uri uri, String[] projection, String where,
		String[] whereArgs, String sortOrder)
	{
		System.out.println(uri.toString() + "===query方法被调用===");
		System.out.println("where参数为：" + where);
		return null;
	}
	// 实现插入方法，该方法应该返回新插入的记录的Uri
	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		System.out.println(uri.toString() + "===insert方法被调用===");
		System.out.println("values参数为：" + values);
		return null;
	}
	// 实现更新方法，该方法应该返回被更新的记录条数
	@Override
	public int update(Uri uri, ContentValues values, String where,
		String[] whereArgs)
	{
		System.out.println(uri.toString() + "===update方法被调用===");
		System.out.println("where参数为：" + where + ",values参数为：" + values);
		return 0;
	}
	// 实现删除方法，该方法应该返回被删除的记录条数
	@Override
	public int delete(Uri uri, String where, String[] whereArgs)
	{
		System.out.println(uri.toString() + "===delete方法被调用===");
		System.out.println("where参数为：" + where);
		return 0;
	}
}
