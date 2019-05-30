package org.crazyit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
	// 自动完成的列表
	private String[] mStrings = new String[]{"aaaaa", "bbbbbb", "cccccc"};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ListView lv = findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStrings));
		// 设置ListView启用过滤
		lv.setTextFilterEnabled(true);
		SearchView sv = findViewById(R.id.sv);
		// 设置该SearchView默认是否自动缩小为图标
		sv.setIconifiedByDefault(false);
		// 设置该SearchView显示搜索按钮
		sv.setSubmitButtonEnabled(true);
		// 设置该SearchView内默认显示的提示文本
		sv.setQueryHint("查找");
		// 为该SearchView组件设置事件监听器
		sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			// 用户输入字符时激发该方法
			@Override public boolean onQueryTextChange(String newText)
			{
				// 如果newText不是长度为0的字符串
				if (TextUtils.isEmpty(newText))
				{
					// 清除ListView的过滤
					lv.clearTextFilter();
				} else
				{
					// 使用用户输入的内容对ListView的列表项进行过滤
					lv.setFilterText(newText);
				}
				return true;
			}
			// 单击搜索按钮时激发该方法
			@Override public boolean onQueryTextSubmit(String query)
			{
				// 实际应用中应该在该方法内执行实际查询
				// 此处仅使用Toast显示用户输入的查询内容
				Toast.makeText(MainActivity.this, "您的选择是:" +
					query, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
}
