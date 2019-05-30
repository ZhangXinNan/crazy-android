package org.crazyit.resolver;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
public class MainActivity extends Activity
{
	private Uri uri = Uri.parse("content://org.crazyit.providers.firstprovider/");
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void query(View source)
	{
		// 调用ContentResolver的query()方法
		// 实际返回的是该Uri对应的ContentProvider的query()的返回值
		Cursor c = getContentResolver().query(uri, null, "query_where", null, null);
		Toast.makeText(this, "远程ContentProvider返回的Cursor为：" +
				c, Toast.LENGTH_SHORT).show();
	}
	public void insert(View source)
	{
		ContentValues values = new ContentValues();
		values.put("name", "fkjava");
		// 调用ContentResolver的insert()方法
		// 实际返回的是该Uri对应的ContentProvider的insert()的返回值
		Uri newUri = getContentResolver().insert(uri, values);
		Toast.makeText(this, "远程ContentProvider新插入记录的Uri为：" +
				newUri, Toast.LENGTH_SHORT).show();
	}
	public void update(View source)
	{
		ContentValues values = new ContentValues();
		values.put("name", "fkjava");
		// 调用ContentResolver的update()方法
		// 实际返回的是该Uri对应的ContentProvider的update()的返回值
		int count = getContentResolver().update(uri, values, "update_where", null);
		Toast.makeText(this, "远程ContentProvider更新记录数为：" +
				count, Toast.LENGTH_SHORT).show();
	}
	public void delete(View source)
	{
		// 调用ContentResolver的delete()方法
		// 实际返回的是该Uri对应的ContentProvider的delete()的返回值
		int count = getContentResolver().delete(uri, "delete_where", null);
		Toast.makeText(this, "远程ContentProvider删除记录数为：" +
				count, Toast.LENGTH_SHORT).show();
	}
}

