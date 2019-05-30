package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.StackView;

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
	private StackView stackView;
	private int[] imageIds = new int[]{R.drawable.bomb5, R.drawable.bomb6,
			R.drawable.bomb7, R.drawable.bomb8, R.drawable.bomb9,
			R.drawable.bomb10, R.drawable.bomb11, R.drawable.bomb12,
			R.drawable.bomb13, R.drawable.bomb14, R.drawable.bomb15,
			R.drawable.bomb16};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		stackView = findViewById(R.id.mStackView);
		// 创建一个List对象，List对象的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<>();
		for (int i = 0; i < imageIds.length; i++)
		{
			Map<String, Object> listItem = new HashMap<>();
			listItem.put("image", imageIds[i]);
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.cell, /* /layout/cell.xml文件作为单元格布局 */
				new String[]{"image"}, new int[]{R.id.image1});
		stackView.setAdapter(simpleAdapter);
	}
	public void prev(View view)
	{
		// 显示上一个组件
		stackView.showPrevious();
	}
	public void next(View view)
	{
		// 显示下一个组件
		stackView.showNext();
	}
}
