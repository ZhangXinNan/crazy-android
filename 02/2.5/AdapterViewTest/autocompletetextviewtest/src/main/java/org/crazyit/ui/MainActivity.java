package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

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
	// 定义字符串数组，作为提示文本
	private String[] books = new String[]{"疯狂Java讲义", "疯狂前端开发讲义",
			"疯狂XML讲义", "疯狂Workflow讲义"};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建一个ArrayAdapter，封装数组
		ArrayAdapter aa = new ArrayAdapter(this,
				android.R.layout.simple_dropdown_item_1line, books);
		AutoCompleteTextView actv = findViewById(R.id.auto);
		// 设置Adapter
		actv.setAdapter(aa);
		MultiAutoCompleteTextView mauto = findViewById(R.id.mauto);
		// 设置Adapter
		mauto.setAdapter(aa);
		// 为MultiAutoCompleteTextView设置分隔符
		mauto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
}
