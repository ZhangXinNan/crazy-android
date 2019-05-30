package org.crazyit.app;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
public class MainActivity extends LauncherActivity
{
	// 定义两个Activity的名称
	private String[] names = new String[]{"设置程序参数", "查看星际兵种"};
	// 定义两个Activity对应的实现类
	private Class[] clazzs = new Class[]{PreferenceActivityTest.class,
			ExpandableListActivityTest.class};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
			android.R.layout.simple_list_item_1, names);
		// 设置该窗口显示的列表所需的Adapter
		setListAdapter(adapter);
	}

	// 根据列表项返回指定Activity对应的Intent
	@Override
	public Intent intentForPosition(int position)
	{
		return new Intent(MainActivity.this, clazzs[position]);
	}
}
