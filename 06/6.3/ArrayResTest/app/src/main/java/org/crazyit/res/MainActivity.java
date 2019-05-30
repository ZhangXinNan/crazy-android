package org.crazyit.res;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
	// 获取系统定义的数组资源
	private String[] texts;
	private TypedArray icons;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		texts = getResources().getStringArray(R.array.string_arr);
		icons = getResources().obtainTypedArray(R.array.plain_arr);
		// 创建一个BaseAdapter对象
		BaseAdapter ba = new BaseAdapter()
		{
			// 指定一共包含9个选项
			@Override
			public int getCount()
			{
				return texts.length;
			}

			@Override
			public Object getItem(int position)
			{
				// 返回指定位置的文本
				return texts[position];
			}

			@Override
			public long getItemId(int position)
			{
				return position;
			}
			// 重写该方法，该方法返回的View将作为GridView的每个格子
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				TextView tv;
				if (convertView == null)
				{
					tv = new TextView(MainActivity.this);
				}
				else
				{
					tv = (TextView) convertView;
				}
				Resources res = MainActivity.this.getResources();
				// 使用尺寸资源来设置文本框的高度、宽度
				tv.setWidth((int) res.getDimension(R.dimen.cell_width));
				tv.setHeight((int) res.getDimension(R.dimen.cell_height));
				// 使用字符串资源设置文本框的内容
				tv.setText(texts[position]);
				// 使用颜色资源来设置文本框的背景色
				tv.setBackground(icons.getDrawable(position));
				tv.setTextSize(20f);
				return tv;
			}
		};
		GridView grid = findViewById(R.id.grid01);
		// 为GridView设置Adapter
		grid.setAdapter(ba);
	}
}
