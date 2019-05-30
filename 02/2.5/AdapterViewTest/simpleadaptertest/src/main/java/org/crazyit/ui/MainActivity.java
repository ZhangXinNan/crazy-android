package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
public class MainActivity extends Activity
{
	private String[] names = new String[]{"虎头", "弄玉", "李清照", "李白"};
	private String[] descs = new String[]{"可爱的小孩", "一个擅长音乐的女孩",
			"一个擅长文学的女性", "浪漫主义诗人"};
	private int[] imageIds = new int[]{R.drawable.tiger,
			R.drawable.nongyu, R.drawable.qingzhao, R.drawable.libai};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<>();
		for (int i = 0; i < names.length; i++)
		{
			Map<String, Object> listItem = new HashMap<>();
			listItem.put("header", imageIds[i]);
			listItem.put("personName", names[i]);
			listItem.put("desc", descs[i]);
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.simple_item, new String[]{"personName", "header", "desc"},
				new int[]{R.id.name, R.id.header, R.id.desc});
		ListView list = findViewById(R.id.mylist);
		// 为ListView设置Adapter
		list.setAdapter(simpleAdapter);
	// 为ListView的列表项的单击事件绑定事件监听器
	list.setOnItemClickListener((parent, view, position, id) -> {
		Log.i("-CRAZYIT-", names[position] + "被单击了");
	});
	// 为ListView的列表项的选中事件绑定事件监听器
	list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			Log.i("-CRAZYIT-", names[position] + "被选中了");
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	});
	}
}
