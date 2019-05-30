package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView list1 = findViewById(R.id.list1);
		// 定义一个数组
		String[] arr1 = new String[]{"孙悟空", "猪八戒", "牛魔王"};
		// 将数组包装为ArrayAdapter
		ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.array_item, arr1);
		// 为ListView设置Adapter
		list1.setAdapter(adapter1);
		ListView list2 = findViewById(R.id.list2);
		// 定义一个数组
		String[] arr2 = new String[]{"Java", "Hibernate", "Spring", "Android"};
		// 将数组包装为ArrayAdapter
		ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.checked_item, arr2);
		// 为ListView设置Adapter
		list2.setAdapter(adapter2);
	}
}
